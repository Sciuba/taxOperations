package br.com.operations.controller;

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

import br.com.operations.entity.TbAdmManufacturerStateOfOrigin;
import br.com.operations.entity.TbAdmManufacturerStateXTbAdmOrigin;
import br.com.operations.entity.TbAdmOrigin;
import br.com.operations.entity.TbSysSystemPermission;
import br.com.operations.entity.TbSysUser;
import br.com.operations.facade.ManufacturerStateOfOriginFacade;
import br.com.operations.facade.ManufacturerStateXOriginFacade;
import br.com.operations.facade.OriginFacade;
import br.com.operations.facade.impl.SystemPermissionFacadeImpl;


@ManagedBean(name="stateOriginBBean")
@ViewScoped
public class AdmManufactureStateOfOriginBBean {
	
	@EJB
	private ManufacturerStateOfOriginFacade stateOfOriginFacade;
	
	@EJB
	private OriginFacade originFacade;
	
	@EJB
	private ManufacturerStateXOriginFacade stateXOriginFacade;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	private List<TbAdmManufacturerStateOfOrigin> listaManufacture;
	private List<TbAdmOrigin> listaOrigin;
	
	private TbAdmManufacturerStateOfOrigin manufacturerStateOfOriginSelecionado;
	
	private TbAdmManufacturerStateOfOrigin novoManufacturerStateOfOriginSelecionado;
	
	private ResourceBundle bundle;
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "stateOfOrigin";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	private TbSysUser userLogged;
	
	
	@PostConstruct
	public void init(){
		this.manufacturerStateOfOriginSelecionado = new TbAdmManufacturerStateOfOrigin();
		this.novoManufacturerStateOfOriginSelecionado = new TbAdmManufacturerStateOfOrigin();
		this.listaManufacture  = new ArrayList<TbAdmManufacturerStateOfOrigin>();
		listaOrigin = new ArrayList<>();
		
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		checkSecurity();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
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
	
	public List<TbAdmManufacturerStateOfOrigin> pesquisaTodos(){
		
		this.listaManufacture = stateOfOriginFacade.findAll();
		
		return listaManufacture;
	}
	
	
	public void preparaListaOrigin(){
		listaOrigin = originFacade.findAll();
		
		if(manufacturerStateOfOriginSelecionado.getAdmManufacturerStateXTbAdmOrigins() != null && 
				manufacturerStateOfOriginSelecionado.getAdmManufacturerStateXTbAdmOrigins().size() > 0){
			for(TbAdmOrigin origin: listaOrigin){
				for(TbAdmManufacturerStateXTbAdmOrigin protSt: manufacturerStateOfOriginSelecionado.getAdmManufacturerStateXTbAdmOrigins()){
					if(origin.getId() == protSt.getTbAdmOrigin().getId()){
						origin.setSelecionado(true);						
					}
				}
			}
		}
		
	}
	
	
	public void saveAdmManufacture(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(novoManufacturerStateOfOriginSelecionado.getRicms() == null || novoManufacturerStateOfOriginSelecionado.getRicms().equals("")){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_icms")), ""));
				
				this.listaManufacture = new ArrayList<TbAdmManufacturerStateOfOrigin>();
				
			}else if(novoManufacturerStateOfOriginSelecionado.getRicmsMaterialImportado() == null || 
							novoManufacturerStateOfOriginSelecionado.getRicmsMaterialImportado().equals("")){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_icms_import")), ""));
				
				this.listaManufacture = new ArrayList<TbAdmManufacturerStateOfOrigin>();
				
			}else if(novoManufacturerStateOfOriginSelecionado.getsCode() == null || novoManufacturerStateOfOriginSelecionado.getsCode().equals("")){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_code")), ""));
				
				this.listaManufacture = new ArrayList<TbAdmManufacturerStateOfOrigin>();
				
			}else if(novoManufacturerStateOfOriginSelecionado.getsDescription() == null || novoManufacturerStateOfOriginSelecionado.getsDescription().equals("")){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")), ""));
				
				this.listaManufacture = new ArrayList<TbAdmManufacturerStateOfOrigin>();
				
			}else{
				
				try{
					novoManufacturerStateOfOriginSelecionado = stateOfOriginFacade.saveReturn(novoManufacturerStateOfOriginSelecionado);
									
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					return;
				}
				
				List<TbAdmManufacturerStateXTbAdmOrigin> lista = new ArrayList<>();
				
				for(TbAdmOrigin origin: listaOrigin){
					
					TbAdmManufacturerStateXTbAdmOrigin protSt = new TbAdmManufacturerStateXTbAdmOrigin();
					
					if(origin.isSelecionado()){				
						
						protSt.setTbAdmOrigin(origin);
						protSt.setTbAdmManufacturerStateOfOrigin(novoManufacturerStateOfOriginSelecionado);
						
						try{
							
							protSt = stateXOriginFacade.saveReturn(protSt);
							
						}catch(Exception e){
							e.printStackTrace();
											
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
									bundle.getString("save_error"), ""));
							
							listaOrigin = new ArrayList<>();
							
							return;
						}
						
						lista.add(protSt);
					}
				}
				
				novoManufacturerStateOfOriginSelecionado.setAdmManufacturerStateXTbAdmOrigins(lista);
				
				try{
					
					stateOfOriginFacade.alter(novoManufacturerStateOfOriginSelecionado);
					
					this.listaManufacture = new ArrayList<TbAdmManufacturerStateOfOrigin>();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					novoManufacturerStateOfOriginSelecionado = new TbAdmManufacturerStateOfOrigin();
				
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
		
			if(manufacturerStateOfOriginSelecionado.getRicms() == null || manufacturerStateOfOriginSelecionado.getRicms().equals("")){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_icms")), ""));
				
				this.listaManufacture = new ArrayList<TbAdmManufacturerStateOfOrigin>();
				
			}else if(manufacturerStateOfOriginSelecionado.getRicmsMaterialImportado() == null || 
							manufacturerStateOfOriginSelecionado.getRicmsMaterialImportado().equals("")){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_icms_import")), ""));
				
				this.listaManufacture = new ArrayList<TbAdmManufacturerStateOfOrigin>();
				
			}else if(manufacturerStateOfOriginSelecionado.getsCode() == null || manufacturerStateOfOriginSelecionado.getsCode().equals("")){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_code")), ""));
				
				this.listaManufacture = new ArrayList<TbAdmManufacturerStateOfOrigin>();
				
			}else if(manufacturerStateOfOriginSelecionado.getsDescription() == null || manufacturerStateOfOriginSelecionado.getsDescription().equals("")){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")), ""));
				
				this.listaManufacture = new ArrayList<TbAdmManufacturerStateOfOrigin>();
				
			}else{
				
				List<TbAdmManufacturerStateXTbAdmOrigin> listaAdd = new ArrayList<>();
				List<TbAdmManufacturerStateXTbAdmOrigin> listaRemove = new ArrayList<>();
				
				boolean contain;
				
				for(TbAdmOrigin origin: listaOrigin){
					
					TbAdmManufacturerStateXTbAdmOrigin protSt = new TbAdmManufacturerStateXTbAdmOrigin();
					
					if(origin.isSelecionado()){		
						
						contain = false;
						
						for(TbAdmManufacturerStateXTbAdmOrigin st : manufacturerStateOfOriginSelecionado.getAdmManufacturerStateXTbAdmOrigins()){					
							if(origin.getId() != st.getTbAdmOrigin().getId()){						
								contain = false;					
							}else{
								contain = true;
								break;
							}					
						}
						
						if(!contain){
							protSt.setTbAdmOrigin(origin);
							protSt.setTbAdmManufacturerStateOfOrigin(manufacturerStateOfOriginSelecionado);
							
							try{
								protSt = stateXOriginFacade.saveReturn(protSt);
								
								listaAdd.add(protSt);
								
							}catch(Exception e){
								e.printStackTrace();
								
								FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
										bundle.getString("save_error"), ""));
								
								listaOrigin = new ArrayList<>();
								
								return;
								
							}
							
													
						}else{
							
							contain = false;
							
							for(TbAdmManufacturerStateXTbAdmOrigin st : manufacturerStateOfOriginSelecionado.getAdmManufacturerStateXTbAdmOrigins()){					
								if(origin.getId() != st.getTbAdmOrigin().getId()){						
									contain = false;					
								}else{
									contain = true;
									listaRemove.add(st);
									break;
								}					
							}
							
						}
						
					}			
					
				}
				
				for(TbAdmManufacturerStateXTbAdmOrigin st : listaRemove){
					manufacturerStateOfOriginSelecionado.getAdmManufacturerStateXTbAdmOrigins().remove(st);
					
					try{
						stateXOriginFacade.delete(st);
						
					}catch(Exception e){
						e.printStackTrace();
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								bundle.getString("save_error"), ""));
						
						listaOrigin = new ArrayList<>();
						
						return;					
					}
				}
				
	
				for(TbAdmManufacturerStateXTbAdmOrigin st : listaAdd){
					manufacturerStateOfOriginSelecionado.getAdmManufacturerStateXTbAdmOrigins().add(st);
				}
			
				try{
					stateOfOriginFacade.alter(manufacturerStateOfOriginSelecionado);
				
					this.listaManufacture = new ArrayList<TbAdmManufacturerStateOfOrigin>();
					
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
		
			if(manufacturerStateOfOriginSelecionado.getAdmManufacturerStateXTbAdmOrigins().size() > 0){
				
				List<TbAdmManufacturerStateXTbAdmOrigin> lista = new ArrayList<>();
				
				for(TbAdmManufacturerStateXTbAdmOrigin st : manufacturerStateOfOriginSelecionado.getAdmManufacturerStateXTbAdmOrigins()){
					lista.add(st);
				}
				
				for(int i = 0 ; i < lista.size() ; i++){
					manufacturerStateOfOriginSelecionado.getAdmManufacturerStateXTbAdmOrigins().remove(lista.get(i));
					
					try{
						stateXOriginFacade.delete(lista.get(i));
						
					}catch(Exception e){
						e.printStackTrace();
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								bundle.getString("save_error"), ""));
						
						listaOrigin = new ArrayList<>();
						
						return;					
					}
					
					lista.remove(i);
					
					i--;
				}
			}		
			
			try{
				stateOfOriginFacade.delete(manufacturerStateOfOriginSelecionado);
				this.listaManufacture = new ArrayList<TbAdmManufacturerStateOfOrigin>();
				
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
	
	
	
	
	
	public List<TbAdmManufacturerStateOfOrigin> getListaManufacture() {
		
		if(this.listaManufacture.size() == 0){
			pesquisaTodos();
		}
		
		return listaManufacture;
	}

	public void setListaManufacture(List<TbAdmManufacturerStateOfOrigin> listaManufacture) {
		this.listaManufacture = listaManufacture;
	}


	public TbAdmManufacturerStateOfOrigin getManufacturerStateOfOriginSelecionado() {
		return manufacturerStateOfOriginSelecionado;
	}


	public void setManufacturerStateOfOriginSelecionado(
			TbAdmManufacturerStateOfOrigin manufacturerStateOfOriginSelecionado) {
		this.manufacturerStateOfOriginSelecionado = manufacturerStateOfOriginSelecionado;
	}


	public TbAdmManufacturerStateOfOrigin getNovoManufacturerStateOfOriginSelecionado() {
		return novoManufacturerStateOfOriginSelecionado;
	}


	public void setNovoManufacturerStateOfOriginSelecionado(
			TbAdmManufacturerStateOfOrigin novoManufacturerStateOfOriginSelecionado) {
		this.novoManufacturerStateOfOriginSelecionado = novoManufacturerStateOfOriginSelecionado;
	}


	public List<TbAdmOrigin> getListaOrigin() {
		
		if(listaOrigin.size() == 0){
			preparaListaOrigin();
		}
		
		return listaOrigin;
	}


	public void setListaOrigin(List<TbAdmOrigin> listaOrigin) {
		this.listaOrigin = listaOrigin;
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
