package br.com.saboia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.com.saboia.entity.TbAdmDestination;
import br.com.saboia.entity.TbAdmQuoteDiscountLevel;
import br.com.saboia.entity.TbAdmQuoteParameters;
import br.com.saboia.entity.TbAdmRate;
import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.DestinationFacade;
import br.com.saboia.facade.QuoteDiscountLevelFacade;
import br.com.saboia.facade.QuoteParametersFacade;
import br.com.saboia.facade.RateFacade;
import br.com.saboia.facade.impl.SystemPermissionFacadeImpl;

@ManagedBean(name="parametersBBean")
@ViewScoped
public class QuoteParametersBBean {
	
	@EJB
	private QuoteParametersFacade quoteParametersFacade;
	
	@EJB
	private DestinationFacade destinationFacade;
	
	@EJB
	private QuoteDiscountLevelFacade discountLevelFacade;
	
	@EJB
	private RateFacade rateFacade;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	private TbAdmQuoteParameters quoteParameters;
	
	private TbAdmQuoteDiscountLevel quoteDiscountLevel;
	
	private List<TbAdmDestination> listaDestination;
	
	private TbAdmRate rate;
	
	private ResourceBundle bundle;
	
	private boolean enable;
	
	private String destinationSelecionado;
	
	private String destinationSelecionadoRate;
	
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "quoteSettings";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	private TbSysUser userLogged;
	
	/**
	 * metodos
	 */
	
	@PostConstruct
	public void init(){
		listaDestination = new ArrayList<>();
		destinationSelecionado = "";
		quoteParameters = new TbAdmQuoteParameters();
		quoteDiscountLevel = new TbAdmQuoteDiscountLevel();
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		checkSecurity();
		
		rate = new TbAdmRate();
		
		try{
			rate = rateFacade.findAll().get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			quoteDiscountLevel = discountLevelFacade.findAll().get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			quoteParameters = quoteParametersFacade.findAll().get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
		
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
	
	
	public void preparaListaDestination(){
		listaDestination = destinationFacade.findOrderByCode();
	}
	
	
	public void save(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(!destinationSelecionadoRate.equals("-1")){
				TbAdmDestination destination = destinationFacade.find(Long.parseLong(destinationSelecionadoRate));
				rate.setTbAdmDestination(destination);
			}
			
			if((rate.getrCofins() < 0) || (rate.getrPis() < 0) || (quoteParameters.getrFatorRelevanciaMaxima() < 0) || (quoteParameters.getrIcmsEntradaPadrao() < 0) || (quoteParameters.getrIcmsSaidaPadrao() < 0) ||
					(quoteParameters.getrIssStandardMtesp() < 0) || (quoteParameters.getrIssStandard() < 0) || (quoteParameters.getrAdditionalPercentRateDollar() < 0) || (quoteParameters.getrSuggestedMargin() < 0)){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not have negative values!", ""));
				
				return;
				
			}
			
			if((rate.getrCofins() > 100) || (rate.getrPis() > 100) || (quoteParameters.getrFatorRelevanciaMaxima() > 100) || (quoteParameters.getrIcmsEntradaPadrao() > 100) || (quoteParameters.getrIcmsSaidaPadrao() > 100) ||
					(quoteParameters.getrIssStandardMtesp() > 100) || (quoteParameters.getrIssStandard() > 100) || (quoteParameters.getrAdditionalPercentRateDollar() > 100) || (quoteParameters.getrSuggestedMargin() > 100)){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not be greater than 100!", ""));
				
				return;
			}
			
			if(!destinationSelecionado.equals("-1")){
				TbAdmDestination admDestination = destinationFacade.find(Long.parseLong(destinationSelecionado));
				quoteParameters.setTbAdmDestination(admDestination);
			}
			
			try{
				rateFacade.save(rate);
				quoteParametersFacade.save(quoteParameters);
				discountLevelFacade.save(quoteDiscountLevel);
				
				enable = false;
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
							
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
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
		
			if(!destinationSelecionadoRate.equals("-1")){
				TbAdmDestination destination = destinationFacade.find(Long.parseLong(destinationSelecionadoRate));
				rate.setTbAdmDestination(destination);
			}else{
				rate.setTbAdmDestination(null);
			}
			
			if((rate.getrCofins() < 0) || (rate.getrPis() < 0) || (quoteParameters.getrFatorRelevanciaMaxima() < 0) || (quoteParameters.getrIcmsEntradaPadrao() < 0) || (quoteParameters.getrIcmsSaidaPadrao() < 0) ||
					(quoteParameters.getrIssStandardMtesp() < 0) || (quoteParameters.getrIssStandard() < 0) || (quoteParameters.getrAdditionalPercentRateDollar() < 0) || (quoteParameters.getrSuggestedMargin() < 0)){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not have negative values!", ""));
				
				return;
				
			}
			
			if((rate.getrCofins() > 100) || (rate.getrPis() > 100) || (quoteParameters.getrFatorRelevanciaMaxima() > 100) || (quoteParameters.getrIcmsEntradaPadrao() > 100) || (quoteParameters.getrIcmsSaidaPadrao() > 100) ||
					(quoteParameters.getrIssStandardMtesp() > 100) || (quoteParameters.getrIssStandard() > 100) || (quoteParameters.getrAdditionalPercentRateDollar() > 100) || (quoteParameters.getrSuggestedMargin() > 100)){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not be greater than 100!", ""));
				
				return;
			}
			
			
			if(!destinationSelecionado.equals("-1")){
				TbAdmDestination admDestination = destinationFacade.find(Long.parseLong(destinationSelecionado));
				quoteParameters.setTbAdmDestination(admDestination);
			}else{
				quoteParameters.setTbAdmDestination(null);
			}
			
			try{
				rateFacade.alter(rate);
				quoteParametersFacade.alter(quoteParameters);
				discountLevelFacade.alter(quoteDiscountLevel);
				
				enable = false;
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
			}
		
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}	
	}
	
	public void update(){
		
		if(quoteParameters.getTbAdmDestination() != null){
			destinationSelecionado = String.valueOf(quoteParameters.getTbAdmDestination().getId());
		}else{
			destinationSelecionado = "-1";
		}
		
		if(rate.getTbAdmDestination() != null){
			destinationSelecionadoRate = String.valueOf(rate.getTbAdmDestination().getId());
		}else{
			destinationSelecionadoRate = "-1";
		}
		
		enable = true;
	}
	
	public void cancel(){
		enable = false;
	}

	
	/**
	 * getters and setters
	 * @return
	 */
	
	
	public TbAdmQuoteParameters getQuoteParameters() {
		return quoteParameters;
	}

	public void setQuoteParameters(TbAdmQuoteParameters quoteParameters) {
		this.quoteParameters = quoteParameters;
	}

	public List<TbAdmDestination> getListaDestination() {
		
		if(listaDestination.size() == 0){
			preparaListaDestination();
		}
		
		return listaDestination;
	}

	public void setListaDestination(List<TbAdmDestination> listaDestination) {
		this.listaDestination = listaDestination;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getDestinationSelecionado() {
		return destinationSelecionado;
	}

	public void setDestinationSelecionado(String destinationSelecionado) {
		this.destinationSelecionado = destinationSelecionado;
	}


	public TbAdmQuoteDiscountLevel getQuoteDiscountLevel() {
		return quoteDiscountLevel;
	}


	public void setQuoteDiscountLevel(TbAdmQuoteDiscountLevel quoteDiscountLevel) {
		this.quoteDiscountLevel = quoteDiscountLevel;
	}


	public String getDestinationSelecionadoRate() {
		return destinationSelecionadoRate;
	}


	public void setDestinationSelecionadoRate(String destinationSelecionadoRate) {
		this.destinationSelecionadoRate = destinationSelecionadoRate;
	}
	
	public TbAdmRate getRate() {
		return rate;
	}

	public void setRate(TbAdmRate rate) {
		this.rate = rate;
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
