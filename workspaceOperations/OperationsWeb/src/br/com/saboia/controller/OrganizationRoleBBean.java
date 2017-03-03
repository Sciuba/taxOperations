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

import br.com.saboia.entity.TbSysOrganizationRole;
import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.impl.OrganizationRoleFacadeImpl;
import br.com.saboia.facade.impl.SystemPermissionFacadeImpl;

@ManagedBean(name="roleBBean")
@ViewScoped
public class OrganizationRoleBBean {
	
	@EJB
	private OrganizationRoleFacadeImpl roleFacadeImpl;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	private TbSysOrganizationRole novoRole;
	
	private TbSysOrganizationRole roleSelecionado;
	
	private List<TbSysOrganizationRole> listaRole;
	
	private ResourceBundle bundle;
	
	private final String SMODULE = "organizationRole";
	
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	private List<TbSysSystemPermission> permissionList;
	
	private TbSysUser userLogged;
	
	/**
	 * métodos
	 */
	
	@PostConstruct
	public void init(){
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		checkSecurity();
		
		novoRole = new TbSysOrganizationRole();
		roleSelecionado = new TbSysOrganizationRole();
		listaRole = new ArrayList<>();		
		
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
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
			
		}
		
	}
	
	
	public void preparaLista(){
		listaRole = roleFacadeImpl.findOrder(); 
	}
	
	
	public void salvar(){
		
	
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
		
			if(novoRole.getsKey() == null || novoRole.getsKey().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("Key")),""));
				
				novoRole = new TbSysOrganizationRole();
				roleSelecionado = new TbSysOrganizationRole();
				listaRole = new ArrayList<>();	
				
				return;
			}
			
			if(novoRole.getsDisplayName() == null || novoRole.getsDisplayName().isEmpty()){
	
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_display_name")),""));
				
				novoRole = new TbSysOrganizationRole();
				roleSelecionado = new TbSysOrganizationRole();
				listaRole = new ArrayList<>();	
				
				return;			
			}
			
			
			try{
				
				TbSysOrganizationRole organizationRole = roleFacadeImpl.findKeyExists(novoRole.getsKey());
				
				if(organizationRole != null){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
							bundle.getString("msg_warn_organization_role_module"),""));
					
					novoRole = new TbSysOrganizationRole();
					roleSelecionado = new TbSysOrganizationRole();
					listaRole = new ArrayList<>();	
					
					return;
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
				
			}
			
			
			try{
				
				roleFacadeImpl.save(novoRole);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				novoRole = new TbSysOrganizationRole();
				roleSelecionado = new TbSysOrganizationRole();
				listaRole = new ArrayList<>();
				
			}catch (Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				novoRole = new TbSysOrganizationRole();
				roleSelecionado = new TbSysOrganizationRole();
				listaRole = new ArrayList<>();
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
		
	}
	
	
	public void update(){
		

		checkSecurity();
		
		if(!disabled && !disabledAll){
			
			if(roleSelecionado.getsKey() == null || roleSelecionado.getsKey().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("Key")),""));
				
				novoRole = new TbSysOrganizationRole();
				roleSelecionado = new TbSysOrganizationRole();
				listaRole = new ArrayList<>();	
				
				return;
			}
			
			if(roleSelecionado.getsDisplayName() == null || roleSelecionado.getsDisplayName().isEmpty()){
	
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_display_name")),""));
				
				novoRole = new TbSysOrganizationRole();
				roleSelecionado = new TbSysOrganizationRole();
				listaRole = new ArrayList<>();	
				
				return;			
			}
			
			try{
				
				roleFacadeImpl.update(roleSelecionado);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('roleEditDialog').hide();");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");  
				
				novoRole = new TbSysOrganizationRole();
				roleSelecionado = new TbSysOrganizationRole();
				listaRole = new ArrayList<>();
				
			}catch (Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				novoRole = new TbSysOrganizationRole();
				roleSelecionado = new TbSysOrganizationRole();
				listaRole = new ArrayList<>();
			}
		
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
			
	}
	
	public String redirect(){
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("roleId", roleSelecionado.getId());
		
		return "success";
		
	}
	
	
	/**
	 * Getters and Setters
	 * @return
	 */
	
	public TbSysOrganizationRole getNovoRole() {
		return novoRole;
	}

	public void setNovoRole(TbSysOrganizationRole novoRole) {
		this.novoRole = novoRole;
	}

	public TbSysOrganizationRole getRoleSelecionado() {
		return roleSelecionado;
	}

	public void setRoleSelecionado(TbSysOrganizationRole roleSelecionado) {
		this.roleSelecionado = roleSelecionado;
	}

	public List<TbSysOrganizationRole> getListaRole() {
		
		if(listaRole == null || listaRole.isEmpty()){
			preparaLista();
		}
		
		return listaRole;
	}

	public void setListaRole(List<TbSysOrganizationRole> listaRole) {
		this.listaRole = listaRole;
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
