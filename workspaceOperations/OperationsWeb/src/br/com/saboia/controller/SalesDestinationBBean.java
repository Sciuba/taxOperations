package br.com.saboia.controller;

import java.text.MessageFormat;
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
import br.com.saboia.entity.TbAdmOrigin;
import br.com.saboia.entity.TbAdmOriginXTbAdmDestinaProtSt;
import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.DestinationFacade;
import br.com.saboia.facade.OriginFacade;
import br.com.saboia.facade.OriginXDestinaProtocolStFacade;
import br.com.saboia.facade.impl.SystemPermissionFacadeImpl;

@ManagedBean(name="destinationBBean")
@ViewScoped
public class SalesDestinationBBean {
	
	@EJB
	private DestinationFacade destinationFacade;
	
	@EJB
	private OriginFacade originFacade;
	
	@EJB
	private OriginXDestinaProtocolStFacade originXDestinaProtocolStFacade;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	private TbAdmDestination novoDestination;
	
	private TbAdmDestination destinationSelecionado;
	
	private List<TbAdmDestination> listaDestination;
	
	private List<TbAdmOrigin> listaOriginHW;
	private List<TbAdmOrigin> listaOriginSW;
	private List<TbAdmOrigin> listaOriginSV;
	private List<TbAdmOrigin> listaOriginMT;
	
	private ResourceBundle bundle;
	
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "salesDestination";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	private TbSysUser userLogged;
	
	/**
	 * métodos
	 */
	
	@PostConstruct
	public void init(){
		novoDestination = new TbAdmDestination();
		destinationSelecionado = new TbAdmDestination();
		
		listaDestination = new ArrayList<>();
		listaOriginHW = new ArrayList<>();
		listaOriginSW = new ArrayList<>();
		listaOriginSV = new ArrayList<>();
		listaOriginMT = new ArrayList<>();
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		checkSecurity();
		
		preparaListaOrigins();
		
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
	
	
	public void preparaLista(){
		listaDestination = destinationFacade.findOrderBy();
	}
	
	public void preparaListaOrigins(){
		
		List<TbAdmOrigin> lista = originFacade.findOrderBy();
		
		TbAdmOrigin origin = new TbAdmOrigin();
		origin.setId(-1);
		origin.setsCode("");
		
		listaOriginHW.add(origin);
		listaOriginSW.add(origin);
		listaOriginSV.add(origin);
		listaOriginMT.add(origin);
		
		for(TbAdmOrigin o: lista){
			listaOriginHW.add(o);
			listaOriginSW.add(o);
			listaOriginSV.add(o);
			listaOriginMT.add(o);
		}
	}
	
	
	public void save(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(novoDestination.getsCode() == null || novoDestination.getsCode().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_code")), ""));
				
				listaDestination = new ArrayList<>();
				
				return;
				
			}else if(novoDestination.getsLocale() == null || novoDestination.getsLocale().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_location")), ""));
				
				listaDestination = new ArrayList<>();
				
				return;
				
			}else if((novoDestination.getrIcmsInterEstadual() < 0) || (novoDestination.getrIcms() < 0) || (novoDestination.getrIcmsInterEstadualMatImport() < 0) ){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not have negative values!", ""));
				
				listaDestination = new ArrayList<>();
				
				return;
				
			}else if((novoDestination.getrIcmsInterEstadual() > 100) || (novoDestination.getrIcms() > 100) || (novoDestination.getrIcmsInterEstadualMatImport() > 100) ){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not be greater than 100!", ""));
				
				listaDestination = new ArrayList<>();
				
				return;
				
			}else{
				
				
				try{
					
					TbAdmDestination admDestination = destinationFacade.findByCode(novoDestination.getsCode());
					
					if(admDestination != null){
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"There is already registered with this source code.", ""));
						
						listaDestination = new ArrayList<>();
						
						return;
						
					}
					
				}catch(Exception e){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					listaDestination = new ArrayList<>();
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('destinationDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					return;
					
				}
				
				
				if(!novoDestination.getOriginHW().equals("-1")){
					TbAdmOrigin origin = originFacade.find(Long.parseLong(novoDestination.getOriginHW()));
					novoDestination.setTbAdmOriginHw(origin);
				}
				
				if(!novoDestination.getOriginSW().equals("-1")){
					TbAdmOrigin origin = originFacade.find(Long.parseLong(novoDestination.getOriginSW()));
					novoDestination.setTbAdmOriginSw(origin);
				}
				
				if(!novoDestination.getOriginSV().equals("-1")){
					TbAdmOrigin origin = originFacade.find(Long.parseLong(novoDestination.getOriginSV()));
					novoDestination.setTbAdmOriginSv(origin);
				}
				
				if(!novoDestination.getOriginMT().equals("-1")){
					TbAdmOrigin origin = originFacade.find(Long.parseLong(novoDestination.getOriginMT()));
					novoDestination.setTbAdmOriginMt(origin);
				}
				
				try{
					
					destinationFacade.save(novoDestination);
					listaDestination = new ArrayList<>();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					novoDestination = new TbAdmDestination();
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('destinationDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					listaDestination = new ArrayList<>();
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('destinationDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					return;
					
				}			
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('destinationDialog').hide();");
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");			
		}
	}
	
	
	
	public void alter(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(destinationSelecionado.getsCode() == null || destinationSelecionado.getsCode().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_code")), ""));
				 
				listaDestination = new ArrayList<>();
				
				return;
				
			}else if(destinationSelecionado.getsLocale() == null || destinationSelecionado.getsLocale().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_location")), ""));
				
				listaDestination = new ArrayList<>();
				
				return;
				
			}else if((destinationSelecionado.getrIcmsInterEstadual() < 0) || (destinationSelecionado.getrIcms() < 0) || (destinationSelecionado.getrIcmsInterEstadualMatImport() < 0) ){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not have negative values!", ""));
				
				listaDestination = new ArrayList<>();
				
				return;
				
			}else if((destinationSelecionado.getrIcmsInterEstadual() > 100) || (destinationSelecionado.getrIcms() > 100) || (destinationSelecionado.getrIcmsInterEstadualMatImport() > 100) ){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not be greater than 100!", ""));
				
				listaDestination = new ArrayList<>();
				
				return;
				
			}else{
				
				
				try{
					
					TbAdmDestination admDestination = destinationFacade.findByCode(destinationSelecionado.getsCode());
					
					if(admDestination.getId() != destinationSelecionado.getId()){
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"There is already registered with this source code.", ""));
						
						listaDestination = new ArrayList<>();
						
						return;
						
					}
					
				}catch(Exception e){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('destinationEditDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					listaDestination = new ArrayList<>();
					
					return;
					
				}
				
				if(!destinationSelecionado.getOriginHW().equals("-1")){
					TbAdmOrigin origin = originFacade.find(Long.parseLong(destinationSelecionado.getOriginHW()));
					destinationSelecionado.setTbAdmOriginHw(origin);
				}else{
					destinationSelecionado.setTbAdmOriginHw(null);
				}
				
				if(!destinationSelecionado.getOriginSW().equals("-1")){
					TbAdmOrigin origin = originFacade.find(Long.parseLong(destinationSelecionado.getOriginSW()));
					destinationSelecionado.setTbAdmOriginSw(origin);
				}else{
					destinationSelecionado.setTbAdmOriginSw(null);
				}
				
				if(!destinationSelecionado.getOriginSV().equals("-1")){
					TbAdmOrigin origin = originFacade.find(Long.parseLong(destinationSelecionado.getOriginSV()));
					destinationSelecionado.setTbAdmOriginSv(origin);
				}else{
					destinationSelecionado.setTbAdmOriginSv(null);
				}
				
				if(!destinationSelecionado.getOriginMT().equals("-1")){
					TbAdmOrigin origin = originFacade.find(Long.parseLong(destinationSelecionado.getOriginMT()));
					destinationSelecionado.setTbAdmOriginMt(origin);
				}else{
					destinationSelecionado.setTbAdmOriginMt(null);
				}
				
				try{
					
					destinationFacade.alter(destinationSelecionado);
					
					listaDestination = new ArrayList<>();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('destinationEditDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					listaDestination = new ArrayList<>();
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('destinationEditDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					return;			
				}
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('destinationEditDialog').hide();");
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");			
		}
	}
	
	
	
	public void delete(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(destinationSelecionado.getTbAdmOriginXTbAdmDestinaProSt().size() > 0){
				List<TbAdmOriginXTbAdmDestinaProtSt> lista = new ArrayList<>();
				
				for(TbAdmOriginXTbAdmDestinaProtSt l : destinationSelecionado.getTbAdmOriginXTbAdmDestinaProSt()){
					lista.add(l);
				}
				
				for(int i = 0 ; i < lista.size() ; i++){
					destinationSelecionado.getTbAdmOriginXTbAdmDestinaProSt().remove(lista.get(i));
					
					try{
						originXDestinaProtocolStFacade.delete(lista.get(i));
						
						lista.remove(i);
						
						i--;
						
					}catch(Exception e){
						e.printStackTrace();
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"This record is in use.", ""));
						
						return;
					}
				}
			}
			
			try{
				destinationFacade.delete(destinationSelecionado);
				listaDestination = new ArrayList<>();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("delete_success"), ""));
			
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"This record is in use.", ""));
				
				return;
			}
		
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");			
		}
		
	}
	
	
	/**
	 * Getters ans Setters
	 * @return
	 */
	
	public TbAdmDestination getNovoDestination() {
		return novoDestination;
	}

	public void setNovoDestination(TbAdmDestination novoDestination) {
		this.novoDestination = novoDestination;
	}

	public TbAdmDestination getDestinationSelecionado() {
		return destinationSelecionado;
	}

	public void setDestinationSelecionado(TbAdmDestination destinationSelecionado) {
		this.destinationSelecionado = destinationSelecionado;
	}

	public List<TbAdmDestination> getListaDestination() {
		
		if(listaDestination.size() == 0){
			preparaLista();
		}
		
		return listaDestination;
	}

	public void setListaDestination(List<TbAdmDestination> listaDestination) {
		this.listaDestination = listaDestination;
	}


	public List<TbAdmOrigin> getListaOriginHW() {
		return listaOriginHW;
	}


	public void setListaOriginHW(List<TbAdmOrigin> listaOriginHW) {
		this.listaOriginHW = listaOriginHW;
	}


	public List<TbAdmOrigin> getListaOriginSW() {
		return listaOriginSW;
	}


	public void setListaOriginSW(List<TbAdmOrigin> listaOriginSW) {
		this.listaOriginSW = listaOriginSW;
	}


	public List<TbAdmOrigin> getListaOriginSV() {
		return listaOriginSV;
	}


	public void setListaOriginSV(List<TbAdmOrigin> listaOriginSV) {
		this.listaOriginSV = listaOriginSV;
	}


	public List<TbAdmOrigin> getListaOriginMT() {
		return listaOriginMT;
	}


	public void setListaOriginMT(List<TbAdmOrigin> listaOriginMT) {
		this.listaOriginMT = listaOriginMT;
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
