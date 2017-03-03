package br.com.operations.controller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.com.operations.entity.TbAdmDollarRate;
import br.com.operations.entity.TbSysSystemPermission;
import br.com.operations.entity.TbSysUser;
import br.com.operations.facade.DollarRateFacade;
import br.com.operations.facade.impl.SystemPermissionFacadeImpl;

@ManagedBean(name="priorDollarBBean")
@ViewScoped
public class PriorDollarBBean {
	
	@EJB
	private DollarRateFacade dollarRateFacade;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	private List<TbAdmDollarRate>  listaDollar;
	
	private TbAdmDollarRate novoDollarRate;
	
	private TbAdmDollarRate dollarRateSelecionado;
	
	private Map<String, String> listaMeses;
	
	private SimpleDateFormat sdf;
	
	private ResourceBundle bundle;
	
	private boolean disabledBean;
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "priorDollar";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	private TbSysUser userLogged;
	
	/**
	 * metodos
	 */

	@PostConstruct
	public void init(){
		listaDollar = new ArrayList<>();
		listaMeses = new LinkedHashMap<>();
		
		novoDollarRate = new TbAdmDollarRate();
		dollarRateSelecionado = new TbAdmDollarRate();
		
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		checkSecurity();
		
		sdf = new SimpleDateFormat("MMMM");
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		sdf = new SimpleDateFormat("MMMM", Locale.getDefault());
		
		bundle = ResourceBundle.getBundle("br.com.operations.bundle.messages", Locale.getDefault());
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
	
	
	public void preparaLista(){
		listaDollar = dollarRateFacade.findOrderBy();
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.DAY_OF_MONTH, 1);		
		
		for(TbAdmDollarRate rate: listaDollar){
			gc.set(Calendar.MONTH, (rate.getiMonth()-1));
			rate.setMes(sdf.format(gc.getTime()));
		}
		
	}
	
	public void preparaMeses(){
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.DAY_OF_MONTH, 1);		
		
		for(int i = 0; i < 12; i++){
			gc.set(Calendar.MONTH, i);
			listaMeses.put(sdf.format(gc.getTime()), String.valueOf((i+1)));
		}
		
	}
	
	public void save(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(novoDollarRate.getiMonth() == null || novoDollarRate.getiMonth() == 0){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_month")), ""));
				
				listaDollar = new ArrayList<>();
				
			}else if(novoDollarRate.getiYear() == null || novoDollarRate.getiYear() == 0){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_year")), ""));
				
				listaDollar = new ArrayList<>();
				
			}else if(novoDollarRate.getrDollar() == null){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_dollar_rate")), ""));
				
				listaDollar = new ArrayList<>();
				
			}else if(novoDollarRate.getrDollar() <= 0){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"The dollar rate can not be '0' or have negative values", ""));
				
				listaDollar = new ArrayList<>();
				
			}else{
				
				boolean ok = false;
				
				for(TbAdmDollarRate rate: listaDollar){
					if(novoDollarRate.getiYear().equals(rate.getiYear()) && novoDollarRate.getiMonth().equals(rate.getiMonth())){
						ok = true;
						break;
					}
				}
				
				if(!ok){
				
					try{
						dollarRateFacade.save(novoDollarRate);
						listaDollar = new ArrayList<>();
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
								bundle.getString("save_success"), ""));
						
						novoDollarRate = new TbAdmDollarRate();
						
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("PF('dollarDialog').hide();");
						
						//Atualizo o Form da tela
						FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
						
					}catch(Exception e){
						e.printStackTrace();
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								bundle.getString("save_error"), ""));
						
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("PF('dollarDialog').hide();");
						
						//Atualizo o Form da tela
						FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
						
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("msg_day_dollar_quote_month"), ""));
					
				}
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dollarDialog').hide();");
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}
	}
	
	
	
	public void alter(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(dollarRateSelecionado.getiMonth() == null || dollarRateSelecionado.getiMonth() == 0){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_month")), ""));
				
				listaDollar = new ArrayList<>();
				
			}else if(dollarRateSelecionado.getiYear() == null || dollarRateSelecionado.getiYear() == 0){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_year")), ""));
				
				listaDollar = new ArrayList<>();
				
			}else if(dollarRateSelecionado.getrDollar() == null){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_dollar_rate")), ""));
				
				listaDollar = new ArrayList<>();
				
			}else if(dollarRateSelecionado.getrDollar() <= 0){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
							"The dollar rate can not be '0' or have negative values", ""));
					
					listaDollar = new ArrayList<>();
					
			}else{
				
				try{
					dollarRateFacade.alter(dollarRateSelecionado);
					listaDollar = new ArrayList<>();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('dollarEditDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
				
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('dollarEditDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
				}
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dollarEditDialog').hide();");
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}
	}
	
	
	public void delete(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			try{
				
				dollarRateFacade.delete(dollarRateSelecionado);
				listaDollar = new ArrayList<>();
				
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
	 * Getters and setters
	 * @return
	 */
	
	public List<TbAdmDollarRate> getListaDollar() {
		
		if(listaDollar.size() == 0){
			preparaLista();
		}
		
		return listaDollar;
	}


	public void setListaDollar(List<TbAdmDollarRate> listaDollar) {
		this.listaDollar = listaDollar;
	}


	public TbAdmDollarRate getNovoDollarRate() {
		return novoDollarRate;
	}


	public void setNovoDollarRate(TbAdmDollarRate novoDollarRate) {
		this.novoDollarRate = novoDollarRate;
	}


	public TbAdmDollarRate getDollarRateSelecionado() {
		return dollarRateSelecionado;
	}


	public void setDollarRateSelecionado(TbAdmDollarRate dollarRateSelecionado) {
		this.dollarRateSelecionado = dollarRateSelecionado;
	}


	public Map<String, String> getListaMeses() {
		
		if(listaMeses.isEmpty()){
			preparaMeses();
		}
		
		return listaMeses;
	}


	public void setListaMeses(Map<String, String> listaMeses) {
		this.listaMeses = listaMeses;
	}


	public boolean isDisabledBean() {
		

		GregorianCalendar gc = new GregorianCalendar();
		
		int ano = gc.get(Calendar.YEAR);
		int mes = gc.get(Calendar.MONTH);
		
		if( (dollarRateSelecionado != null && dollarRateSelecionado.getiYear() != null && dollarRateSelecionado.getiMonth() != null) && ano == dollarRateSelecionado.getiYear() && (mes+1) == dollarRateSelecionado.getiMonth()){
			
			disabledBean = false;
			
		}else{
			
			disabledBean = true;
			
		}		
		
		return disabledBean;
	}


	public void setDisabledBean(boolean disabledBean) {
		this.disabledBean = disabledBean;
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
