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

import br.com.saboia.entity.TbAdmMaterial;
import br.com.saboia.entity.TbAdmMaterialCategorySupport;
import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.MaterialFacade;
import br.com.saboia.facade.impl.MaterialCategorySupportFacadeImpl;
import br.com.saboia.facade.impl.SystemPermissionFacadeImpl;


@ManagedBean(name="materialCategorySupBBean")
@ViewScoped
public class AdmMaterialCategorySupportBBean {
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	@EJB
	private MaterialCategorySupportFacadeImpl facadeImpl;
	
	@EJB
	private MaterialFacade materialFacade;
	
	private List<TbAdmMaterialCategorySupport> listaCategory;
	private TbAdmMaterialCategorySupport support;
	private String material;
	
	private ResourceBundle bundle;
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "materialCategorySupport";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	private TbSysUser userLogged;
	
	@PostConstruct
	public void init(){
		
		listaCategory = new ArrayList<TbAdmMaterialCategorySupport>();
		support = new TbAdmMaterialCategorySupport();
		
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		checkSecurity();
		
		preparaLista();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		setBundle(ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault()));
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
		listaCategory = facadeImpl.findNotDeleted();
	}
	
	public void newMaterialCategorySupport(){
		support = new TbAdmMaterialCategorySupport();
		support.setfAvailable(true);
		material = "";
	}
	
	public void editMateriaSupport(){
		material = support.getTbAdmMaterial().getsInternalModel();
	}
	
	public void save(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
			if(support.getsName() == null || support.getsName().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), "Name"), ""));
				return;
								
			}else if(support.getrRate() == null){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), "Rate"), ""));
				return;
				
			}else if(support.getrRate() != null && support.getrRate() > 100F){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"The Rate cannot be greater than 100.0", ""));
				return;
				
			}else if(material == null || material.isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), "Material"), ""));
				return;
				
			}else{
				
				try{
					
					TbAdmMaterial m = materialFacade.findQuery(material);
					
					if(m != null){
						support.setTbAdmMaterial(m);
					}else{
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
								"The Part Number not found.", ""));
						return;
					}
					
					facadeImpl.save(support);
					
					preparaLista();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					support = new TbAdmMaterialCategorySupport();
					
					RequestContext context = RequestContext.getCurrentInstance();
					
					context.execute("PF('matCatDialog').hide();");
					
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
				}catch(Exception e){
					
					e.printStackTrace();
					
					RequestContext context = RequestContext.getCurrentInstance();
					
					context.execute("PF('matCatDialog').hide();");
					
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
	
	public void alter(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
			if(support.getsName() == null || support.getsName().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), "Name"), ""));
				return;
								
			}else if(support.getrRate() == null){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), "Rate"), ""));
				return;
				
			}else if(support.getrRate() != null && support.getrRate() > 100F){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"The Rate cannot be greater than 100.0", ""));
				return;
				
			}else if(material == null || material.isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), "Material"), ""));
				return;
				
			}else{
				
				try{
					
					TbAdmMaterial m = materialFacade.findQuery(material);
					
					if(m != null){
						support.setTbAdmMaterial(m);
					}else{
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
								"The Part Number not found.", ""));
						return;
					}
					
					facadeImpl.alter(support);
					
					preparaLista();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					support = new TbAdmMaterialCategorySupport();
					
					RequestContext context = RequestContext.getCurrentInstance();
					
					context.execute("PF('matCatEditDialog').hide();");
					
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
				}catch(Exception e){
					
					e.printStackTrace();
					
					RequestContext context = RequestContext.getCurrentInstance();
					
					context.execute("PF('matCatEditDialog').hide();");
					
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
				support.setfDeleted(true);
				
				facadeImpl.alter(support);
				
				preparaLista();
				
				support= new TbAdmMaterialCategorySupport();
				
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

	public List<TbAdmMaterialCategorySupport> getListaCategory() {
		return listaCategory;
	}

	public void setListaCategory(List<TbAdmMaterialCategorySupport> listaCategory) {
		this.listaCategory = listaCategory;
	}
	
	public String getsKey() {
		return sKey;
	}

	public void setsKey(String sKey) {
		this.sKey = sKey;
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

	public TbSysUser getUserLogged() {
		return userLogged;
	}

	public void setUserLogged(TbSysUser userLogged) {
		this.userLogged = userLogged;
	}

	public String getSMODULE() {
		return SMODULE;
	}

	public List<TbSysSystemPermission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<TbSysSystemPermission> permissionList) {
		this.permissionList = permissionList;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public TbAdmMaterialCategorySupport getSupport() {
		return support;
	}

	public void setSupport(TbAdmMaterialCategorySupport support) {
		this.support = support;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
}
