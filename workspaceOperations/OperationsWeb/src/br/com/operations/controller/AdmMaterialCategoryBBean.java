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

import org.primefaces.context.RequestContext;

import br.com.operations.entity.TbAdmMaterialCategory;
import br.com.operations.entity.TbSysSystemPermission;
import br.com.operations.entity.TbSysUser;
import br.com.operations.facade.impl.MaterialCategoryFacadeImpl;
import br.com.operations.facade.impl.SystemPermissionFacadeImpl;


@ManagedBean(name="materialCategoryBBean")
@ViewScoped
public class AdmMaterialCategoryBBean {
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	@EJB
	private MaterialCategoryFacadeImpl facadeImpl;
	
	private List<TbAdmMaterialCategory> listaCategory;
	private TbAdmMaterialCategory category;
	
	
	private ResourceBundle bundle;
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "materialCategory";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	private TbSysUser userLogged;
	
	@PostConstruct
	public void init(){
		
		listaCategory = new ArrayList<TbAdmMaterialCategory>();
		setCategory(new TbAdmMaterialCategory());
		
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		checkSecurity();
		
		preparaLista();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		setBundle(ResourceBundle.getBundle("br.com.operations.bundle.messages", Locale.getDefault()));
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
		listaCategory = facadeImpl.findListNotDeleted();
	}
	
	public void newMaterialCategory(){
		category = new TbAdmMaterialCategory();
	}
	
	
	public void save(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
			if(category.getsHashCode() == null || category.getsHashCode().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), "HashCode"), ""));
				return;
								
			}else if(category.getsDescription() == null || category.getsDescription().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), "Description"), ""));
				return;
				
			}else{
				
				try{
					
					facadeImpl.save(category);
					
					preparaLista();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					category = new TbAdmMaterialCategory();
					
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
			
			if(category.getsHashCode() == null || category.getsHashCode().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), "HashCode"), ""));
				return;
								
			}else if(category.getsDescription() == null || category.getsDescription().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), "Description"), ""));
				return;
				
			}else{
				
				try{
					
					facadeImpl.alter(category);
					
					preparaLista();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					category = new TbAdmMaterialCategory();
					
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
				category.setfDeleted(true);
				
				facadeImpl.alter(category);
				
				preparaLista();
				
				category= new TbAdmMaterialCategory();
				
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

	public List<TbAdmMaterialCategory> getListaCategory() {
		return listaCategory;
	}

	public void setListaCategory(List<TbAdmMaterialCategory> listaCategory) {
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

	public TbAdmMaterialCategory getCategory() {
		return category;
	}

	public void setCategory(TbAdmMaterialCategory category) {
		this.category = category;
	}
	
}
