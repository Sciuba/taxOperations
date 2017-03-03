package br.com.saboia.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.UserFacade;
import br.com.saboia.utils.SendEmail;


@ManagedBean(name="loginBBean")
@SessionScoped
public class LoginBBean {
	
	@EJB
	private UserFacade userFacade;
	
	@EJB
	private SendEmail email;
	
	private TbSysUser user;
	
	private String locale = "en_US";
	
	private String language = "en";
	
	private String country = "US";
	
	private String loginRemember;
	
	private ResourceBundle bundle;
	
	private String idUser;
	
	private String newPassword;
	
	private String retypeNewPassword;
	
	private TbSysUser userGet;
	
	private boolean alterPassOk;
	
	private boolean alterPassDefault;
	
	@PostConstruct
	public void init(){
		
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("locale", locale);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("language", language);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("country", country);
		
		user = new TbSysUser();
		
		Locale.setDefault(new Locale(language, country));
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
		
	}
	
	public void loadUserGet(){
		
		try{
			userGet = userFacade.recoveryIdUserCodePassword(idUser);
		}catch(Exception e){
			e.printStackTrace();
			userGet = null;
		}
		
	}
	
	public String login(){
		
		try{
			user = userFacade.login(user.getsLogon(), user.getsPassword());
			
			if(user != null && user.getId() != 0){
				
				//Verificação se senha padrão
				if(user.getsPassword().equals("oracle")){
					
					alterPassDefault = true;
					
					return "error";
					
				}else{

					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", user);
					
					return "success";
					
				}
				
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("msg_login_error"), ""));
				
				return "error";
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("msg_login_error"), ""));
			
			return "error";
		}		
			
	}
	
	public void logout(){
		
		user.setDtLastAccess(new Date());
		
		userFacade.alter(user);
		
		FacesContext fc = FacesContext.getCurrentInstance();  
	       HttpSession session = (HttpSession)fc.getExternalContext().getSession(false);  
	       session.invalidate();  
		
	       try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../../pages/public/login.xhtml");
			} catch (IOException e) {
				
				e.printStackTrace();
			}	       
	       
	}
	
	
	public void sendPassword(){
		
		if(loginRemember == null || loginRemember.isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_user")), ""));
			
			return;
		}
		
		try{
			
			String sCodeSend = "";
			
			Random gerador = new Random(); 
			
			for (int i = 0; i < 5; i++) {
				sCodeSend += gerador.nextInt();
			}
			
			TbSysUser user = userFacade.rememberPassword(loginRemember);
			
			if(user != null){
				
				user.setDtForgotPassword(new Date());
				user.setsCodePasswordSend(sCodeSend);
				
				user = userFacade.alter(user);
				
				List<String> mails = new ArrayList<>();
				
				mails.add(user.getsEmail());
				
				email.sendHtmlEmail(sCodeSend, null, mails, "Forgot Password", null, "PASSWORD");
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('passRemember').hide();");

				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"Your Password is sent.", ""));
				
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form"); 
				
				loginRemember = "";
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public String alterPasswordDefault(){
		
		if(!newPassword.equals(retypeNewPassword)){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"Password fields must be identical.", ""));
			
			return "error";
			
		}else if(newPassword.equalsIgnoreCase("oracle")){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"Please choose another password.", ""));
			
			return "error";
			
		}else{
			
			user.setsPassword(newPassword);
			user.setDtPasswordUpdate(new Date());
			
			user.setsCodePasswordSend(null);
			
			try{
				
				user = userFacade.alter(user);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"The Password has been changed. ", ""));
				
				newPassword = "";
				retypeNewPassword = "";

				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", user);
				
				return "success";
				
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_error"), ""));
				
				return "error";
			}
			
		}
	}
	
	public void alterPassword(){
		
		if(!newPassword.equals(retypeNewPassword)){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"Password fields must be identical.", ""));
			
		}else if(newPassword.equalsIgnoreCase("oracle")){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"Please choose another password.", ""));
		}else{
			
			userGet.setsPassword(newPassword);
			userGet.setDtPasswordUpdate(new Date());
			
			userGet.setsCodePasswordSend(null);
			
			try{
				
				userFacade.alter(userGet);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"The Password has been changed. ", ""));
				
				alterPassOk = true;
				
				newPassword = "";
				retypeNewPassword = "";
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_error"), ""));
			}
			
		}
		
	}
	
	
	/**
	 * getters and setters
	 */
	
	public TbSysUser getUser() {
		return user;
	}

	public void setUser(TbSysUser user) {
		this.user = user;
	}


	public String getLocale() {
		return locale;
	}


	public void setLocale(String locale) {
		this.locale = locale;
	}


	public String getLoginRemember() {
		return loginRemember;
	}


	public void setLoginRemember(String loginRemember) {
		this.loginRemember = loginRemember;
	}


	public String getIdUser() {
		return idUser;
	}


	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


	public String getRetypeNewPassword() {
		return retypeNewPassword;
	}


	public void setRetypeNewPassword(String retypeNewPassword) {
		this.retypeNewPassword = retypeNewPassword;
	}


	public TbSysUser getUserGet() {
		return userGet;
	}


	public void setUserGet(TbSysUser userGet) {
		this.userGet = userGet;
	}

	public boolean isAlterPassOk() {
		return alterPassOk;
	}

	public void setAlterPassOk(boolean alterPassOk) {
		this.alterPassOk = alterPassOk;
	}

	public boolean isAlterPassDefault() {
		return alterPassDefault;
	}

	public void setAlterPassDefault(boolean alterPassDefault) {
		this.alterPassDefault = alterPassDefault;
	}
	
	
	
	
}
