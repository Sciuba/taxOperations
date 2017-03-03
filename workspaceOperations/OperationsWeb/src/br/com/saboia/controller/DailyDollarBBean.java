package br.com.saboia.controller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.saboia.entity.TbAdmDailyDollar;
import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.DailyDollarFacade;
import br.com.saboia.facade.impl.SystemPermissionFacadeImpl;

@ManagedBean(name="dailyDollarBBean")
@ViewScoped
public class DailyDollarBBean {
	
	@EJB
	private DailyDollarFacade dailyDollarFacade;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	private List<TbAdmDailyDollar> listaDailyDollar; 
	
	private TbAdmDailyDollar novoDailyDollar;
	
	private TbAdmDailyDollar dailyDollarSelecionado;
	
	private ResourceBundle bundle;
	
	private SimpleDateFormat sdf;
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do m�dulo conforme o usu�rio logado
	 */
	private final String SMODULE = "dailyDollar";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	private TbSysUser userLogged;
	
	
	/**
	 * Metodos
	 */
	
	@PostConstruct
	public void init(){
		listaDailyDollar = new ArrayList<>();
		novoDailyDollar = new TbAdmDailyDollar();
		dailyDollarSelecionado = new TbAdmDailyDollar();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
		
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		checkSecurity();
		
	}
	
	public void checkSecurity(){
		
		sKey = "";
		disabled = false;
		disabledAll = false;
		
		permissionList = systemPermissionFacadeImpl.findPermissionByUserIdAndModule(userLogged.getId(), SMODULE);
		
		if(permissionList != null && !permissionList.isEmpty()){
			for(TbSysSystemPermission permission : permissionList){
				if(sKey.isEmpty() || !sKey.equals("WRITE")){
					sKey = permission.getsKey();
				}			
			}
		}
		
		if(sKey.equals("READ")){
			disabled = true;
		}else if(sKey.isEmpty()){
			disabledAll = true;
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not allowed to access this module. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}
		
	}
	
	
	public void preparaList(){
		listaDailyDollar = dailyDollarFacade.findOrderBy();
		
		for(TbAdmDailyDollar dollar: listaDailyDollar){
			dollar.setDataFormat(sdf.format(dollar.getdtDollar()));
		}
		
	}
	
	public void save(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(novoDailyDollar.getdtDollar() == null){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_date")), ""));
				
				listaDailyDollar = new ArrayList<>();
				
			}else if(novoDailyDollar.getrDollar() == null){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_dollar_rate")), ""));
				
				listaDailyDollar = new ArrayList<>();
				
			}else{
			
				boolean ok  = false;
				
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(novoDailyDollar.getdtDollar());
				
				gc.set(Calendar.HOUR, gc.getActualMinimum(Calendar.HOUR));  
				gc.set(Calendar.HOUR_OF_DAY, gc.getActualMinimum(Calendar.HOUR_OF_DAY));  
				gc.set(Calendar.MINUTE, gc.getActualMinimum(Calendar.MINUTE));  
				gc.set(Calendar.SECOND, gc.getActualMinimum(Calendar.SECOND));  
				gc.set(Calendar.MILLISECOND, gc.getActualMinimum(Calendar.MILLISECOND));
				
				novoDailyDollar.setdtDollar(gc.getTime());
				
				for(TbAdmDailyDollar dollar : listaDailyDollar){
					if(novoDailyDollar.getdtDollar().equals(dollar.getdtDollar())){
						ok = true;
						break;
					}
				}
				
				if(!ok){
						try{
												
							dailyDollarFacade.save(novoDailyDollar);
							listaDailyDollar = new ArrayList<>();
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
									bundle.getString("save_success"), ""));
							
							novoDailyDollar = new TbAdmDailyDollar();
						
						}catch(Exception e){
							e.printStackTrace();
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
									bundle.getString("save_error"), ""));
						}
				}else{
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("msg_day_dollar_quote"), ""));
					
				}
				
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
									
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}
	}
	
	public void alter(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(dailyDollarSelecionado.getdtDollar() == null){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_date")), ""));
				
				listaDailyDollar = new ArrayList<>();
				
			}else if(dailyDollarSelecionado.getrDollar() == null){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_dollar_rate")), ""));
				
				listaDailyDollar = new ArrayList<>();
				
			}else{
				try{
					dailyDollarFacade.alter(dailyDollarSelecionado);
					listaDailyDollar = new ArrayList<>();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
				
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
				}
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
									
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}
	}
	
	public void delete(){
		

		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			try{
				dailyDollarFacade.delete(dailyDollarSelecionado);
				listaDailyDollar = new ArrayList<>();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("delete_success"), ""));
			
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("delete_error"), ""));
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
									
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}
	}

	
	
	/**
	 * Getters and Setters
	 */
	
	
	public List<TbAdmDailyDollar> getListaDailyDollar() {
		
		if(listaDailyDollar.size() == 0){
			preparaList();
		}
		
		return listaDailyDollar;
	}

	public void setListaDailyDollar(List<TbAdmDailyDollar> listaDailyDollar) {
		this.listaDailyDollar = listaDailyDollar;
	}

	public TbAdmDailyDollar getNovoDailyDollar() {
		return novoDailyDollar;
	}

	public void setNovoDailyDollar(TbAdmDailyDollar novoDailyDollar) {
		this.novoDailyDollar = novoDailyDollar;
	}

	public TbAdmDailyDollar getDailyDollarSelecionado() {
		return dailyDollarSelecionado;
	}

	public void setDailyDollarSelecionado(TbAdmDailyDollar dailyDollarSelecionado) {
		this.dailyDollarSelecionado = dailyDollarSelecionado;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isDisabledAll() {
		return disabledAll;
	}

	public void setDisabledAll(boolean disabledAll) {
		this.disabledAll = disabledAll;
	}
	
	
	
	
					

}
