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

import br.com.saboia.entity.TbAdmManufacturerStateOfOrigin;
import br.com.saboia.entity.TbAdmMaterialManufacturer;
import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.ManufacturerFacade;
import br.com.saboia.facade.ManufacturerStateOfOriginFacade;
import br.com.saboia.facade.impl.SystemPermissionFacadeImpl;


@ManagedBean(name="manufactureBBean")
@ViewScoped
public class AdmManufactureBBean {
	
	@EJB
	private ManufacturerFacade manufactureFacade;
	
	@EJB
	private ManufacturerStateOfOriginFacade stateOfOriginFacade;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	private List<TbAdmMaterialManufacturer> listaManufacture;
	
	private TbAdmMaterialManufacturer admManufactureSelecionado;
	
	private TbAdmMaterialManufacturer NovoAdmManufactureSelecionado;
	
	private List<TbAdmManufacturerStateOfOrigin> listaStateOrigin;
	
	private String stateOfOriginSelecionado;
	
	private ResourceBundle bundle;
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "manufacturer";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	private TbSysUser userLogged;
	
	@PostConstruct
    public void init(){
		admManufactureSelecionado = new TbAdmMaterialManufacturer();
		NovoAdmManufactureSelecionado = new TbAdmMaterialManufacturer();
		listaManufacture = new ArrayList<TbAdmMaterialManufacturer>();
		listaStateOrigin = new ArrayList<>();
		stateOfOriginSelecionado = "";
		
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		checkSecurity();
		
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
	
	
	/**
	 * Métodos de controle
	 * @return
	 */
	
	public void pesquisaTodos(){
		
		this.listaManufacture = manufactureFacade.findAll();
				
	}
	
	public void pesquisaTodosStateOfOrigin(){
		
		List<TbAdmManufacturerStateOfOrigin> lista = stateOfOriginFacade.findAll();
		
		if(lista.size() > 0){
			
			TbAdmManufacturerStateOfOrigin origin = new TbAdmManufacturerStateOfOrigin();
			origin.setsCode("");
			origin.setId(-1);
			this.listaStateOrigin.add(origin);
			
			for(TbAdmManufacturerStateOfOrigin o : lista){
				this.listaStateOrigin.add(o);				
			}
			
		}	
		
	}
	
	public void saveAdmManufacture(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(!stateOfOriginSelecionado.equals("-1")){
				TbAdmManufacturerStateOfOrigin origin = stateOfOriginFacade.find(Long.parseLong(stateOfOriginSelecionado));
				NovoAdmManufactureSelecionado.setTbAdmManufacturerStateOfOrigin(origin);
			}
			
			if(NovoAdmManufactureSelecionado.getsName() == null || NovoAdmManufactureSelecionado.getsName().equals("")){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_name")), ""));
				
			}else{
				try{
				
					manufactureFacade.save(NovoAdmManufactureSelecionado);
					this.listaManufacture = new ArrayList<TbAdmMaterialManufacturer>();			
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					this.listaManufacture = new ArrayList<TbAdmMaterialManufacturer>();
					
					NovoAdmManufactureSelecionado = new TbAdmMaterialManufacturer();
					
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
	
	public void alterAdmManufacture(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(!stateOfOriginSelecionado.equals("-1")){
				TbAdmManufacturerStateOfOrigin origin = stateOfOriginFacade.find(Integer.parseInt(stateOfOriginSelecionado));
				admManufactureSelecionado.setTbAdmManufacturerStateOfOrigin(origin);
			}else{
				admManufactureSelecionado.setTbAdmManufacturerStateOfOrigin(null);
			}
			
			
			if(admManufactureSelecionado.getsName() == null || admManufactureSelecionado.getsName().equals("")){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_name")), ""));
				
				this.listaManufacture = new ArrayList<TbAdmMaterialManufacturer>();
				
			}else{
				try{
			
					manufactureFacade.alter(admManufactureSelecionado);
					this.listaManufacture = new ArrayList<TbAdmMaterialManufacturer>();
					
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
	
	public void deleteManufacturer(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			try{
				
				manufactureFacade.delete(admManufactureSelecionado);
				this.listaManufacture = new ArrayList<TbAdmMaterialManufacturer>();
				
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
	
	
	public void preparaState(){
		
		if(admManufactureSelecionado.getTbAdmManufacturerStateOfOrigin() != null){
			stateOfOriginSelecionado = String.valueOf(admManufactureSelecionado.getTbAdmManufacturerStateOfOrigin().getId());
		}
		 
	}
	
	
	
	/**
	 * Getters and Setters
	 * @return
	 */
	
	public List<TbAdmMaterialManufacturer> getListaManufacture() {
		
		if(listaManufacture.size() == 0){
			pesquisaTodos();
		}
		
		return listaManufacture;
	}

	public void setListaManufacture(List<TbAdmMaterialManufacturer> listaManufacture) {
		this.listaManufacture = listaManufacture;
	}

	public TbAdmMaterialManufacturer getAdmManufactureSelecionado() {
		return admManufactureSelecionado;
	}

	public void setAdmManufactureSelecionado(
			TbAdmMaterialManufacturer admManufactureSelecionado) {
		this.admManufactureSelecionado = admManufactureSelecionado;
	}


	public TbAdmMaterialManufacturer getNovoAdmManufactureSelecionado() {
		return NovoAdmManufactureSelecionado;
	}


	public void setNovoAdmManufactureSelecionado(
			TbAdmMaterialManufacturer novoAdmManufactureSelecionado) {
		NovoAdmManufactureSelecionado = novoAdmManufactureSelecionado;
	}


	public List<TbAdmManufacturerStateOfOrigin> getListaStateOrigin() {
		
		if(listaStateOrigin.size() == 0){			
			pesquisaTodosStateOfOrigin();
		}
		
		return listaStateOrigin;
	}


	public void setListaStateOrigin(
			List<TbAdmManufacturerStateOfOrigin> listaStateOrigin) {
		this.listaStateOrigin = listaStateOrigin;
	}


	public String getStateOfOriginSelecionado() {
		
		if(admManufactureSelecionado.getId() != 0){
			preparaState();
		}
		
		return stateOfOriginSelecionado;
	}


	public void setStateOfOriginSelecionado(String stateOfOriginSelecionado) {
		this.stateOfOriginSelecionado = stateOfOriginSelecionado;
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
