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
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.operations.entity.TbSysOrganization;
import br.com.operations.entity.TbSysOrganizationRole;
import br.com.operations.entity.TbSysSystemPermission;
import br.com.operations.entity.TbSysUser;
import br.com.operations.facade.UserFacade;
import br.com.operations.facade.impl.OrganizationFacadeImpl;
import br.com.operations.facade.impl.OrganizationRoleFacadeImpl;
import br.com.operations.facade.impl.SystemPermissionFacadeImpl;

@ManagedBean(name="organizationBBean")
@ViewScoped
public class OrganizationBBean {

	@EJB
	private OrganizationFacadeImpl organizationFacade;
	
	@EJB
	private OrganizationRoleFacadeImpl roleFacadeImpl;
	
	@EJB
	private UserFacade userFacade;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	private TbSysOrganization novoOrganization;
	
	private TbSysOrganization organizationSelecionado;
	
	private List<TbSysOrganization> listaOrganization;
	
	private List<SelectItem> listaOrganizationRole;
	
	private ResourceBundle bundle;
	
	private TbSysUser userLogged;
	
	private boolean main;
	
	private String roleSelecionado;
	
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "organization";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * metodos
	 */
	
	@PostConstruct
	public void init(){
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		checkSecurity();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.operations.bundle.messages", Locale.getDefault());
		
		novoOrganization = new TbSysOrganization();
		organizationSelecionado = new TbSysOrganization();
		listaOrganization = new ArrayList<TbSysOrganization>();
		listaOrganizationRole = new ArrayList<>();
		
		main = organizationFacade.simpleQuery().isfMain();
		
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
	
	
	public void prepararListaOrganization(){
		listaOrganization = organizationFacade.findAllOrder();
		
		for(TbSysOrganization org : listaOrganization){
			
			List<TbSysUser> users = userFacade.findByOrganization(org.getId());
			
			org.setUsers(users.size());
			
			for(TbSysUser sysUser : users){
				
				if(sysUser.isfActive()){
					org.setUsersActive(org.getUsersActive()+1);
				}
				
			}
			
		}
		
	}

	public void prepararListaRole(){
		List<TbSysOrganizationRole> lista = roleFacadeImpl.findOrder();
		
		for(TbSysOrganizationRole role : lista){
			listaOrganizationRole.add(new SelectItem(role.getId(), role.getsDisplayName()));
		}
	}
	
	
	
	public String redirectOrganization(){
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idOrganization", organizationSelecionado.getId());
		
		return "success";
	}
	
	
	
	public String save(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(roleSelecionado == null || roleSelecionado.isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_organization_role")),""));						
				
				novoOrganization = new TbSysOrganization();
				listaOrganization = new ArrayList<>();
				
				return "ERROR";
			}else{
				novoOrganization.setTbSysOrganizationRole(roleFacadeImpl.finOneResult(Long.valueOf(roleSelecionado)));
			}
	
	
			if(novoOrganization.getsName() == null || novoOrganization.getsName().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_name")),""));						
				
				novoOrganization = new TbSysOrganization();
				listaOrganization = new ArrayList<>();
				
				return "ERROR";
				
			}
			
			if(novoOrganization.getsDescription() == null || novoOrganization.getsDescription().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")),""));						
				
				novoOrganization = new TbSysOrganization();
				listaOrganization = new ArrayList<>();
				
				return "ERROR";
				
			}
			
			try{
				
				novoOrganization = organizationFacade.saveReturn(novoOrganization);
				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idOrganization", novoOrganization.getId());
				
				novoOrganization = new TbSysOrganization();
				listaOrganization = new ArrayList<>();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('organizationDialog').hide();");
				
				listaOrganization = new ArrayList<>();
				novoOrganization = new TbSysOrganization();
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
				return "success";
				
			}catch(Exception e){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				novoOrganization = new TbSysOrganization();
				listaOrganization = new ArrayList<>();
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('organizationDialog').hide();");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
				return "ERROR";
			}
		
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
			
			return "ERROR";
			
		}else{
			return "ERROR";
		}
			
	}
	
	
	public void update(){
		
		if(roleSelecionado == null || roleSelecionado.isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_organization_role")),""));						
			
			novoOrganization = new TbSysOrganization();
			listaOrganization = new ArrayList<>();
			
			return;
		}else{
			organizationSelecionado.setTbSysOrganizationRole(roleFacadeImpl.finOneResult(Long.valueOf(roleSelecionado)));
		}


		if(organizationSelecionado.getsName() == null || organizationSelecionado.getsName().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_name")),""));						
			
			novoOrganization = new TbSysOrganization();
			listaOrganization = new ArrayList<>();
			
			return;
			
		}
		
		if(organizationSelecionado.getsDescription() == null || organizationSelecionado.getsDescription().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")),""));						
			
			novoOrganization = new TbSysOrganization();
			listaOrganization = new ArrayList<>();
			
			return;
			
		}
		
		try{
			
			organizationFacade.update(organizationSelecionado);
			
			novoOrganization = new TbSysOrganization();
			listaOrganization = new ArrayList<>();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
			
			//Fecho o modal da tela
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('orgEditDialog').hide();");
			
			listaOrganization = new ArrayList<>();
			novoOrganization = new TbSysOrganization();
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}catch(Exception e){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
			
			novoOrganization = new TbSysOrganization();
			listaOrganization = new ArrayList<>();
			
			//Fecho o modal da tela
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('orgEditDialog').hide();");
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
		}
		
	}
	
	/**
	 * getters and setters
	 */
	
	
	public TbSysOrganization getNovoOrganization() {
		return novoOrganization;
	}

	public void setNovoOrganization(TbSysOrganization novoOrganization) {
		this.novoOrganization = novoOrganization;
	}

	public TbSysOrganization getOrganizationSelecionado() {
		return organizationSelecionado;
	}

	public void setOrganizationSelecionado(TbSysOrganization organizationSelecionado) {
		this.organizationSelecionado = organizationSelecionado;
	}

	public List<TbSysOrganization> getListaOrganization() {
		
		if(listaOrganization == null || listaOrganization.size() == 0){
			prepararListaOrganization();
		}
		
		return listaOrganization;
	}

	public void setListaOrganization(List<TbSysOrganization> listaOrganization) {
		this.listaOrganization = listaOrganization;
	}

	public TbSysUser getUserLogged() {
		return userLogged;
	}

	public void setUserLogged(TbSysUser userLogged) {
		this.userLogged = userLogged;
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public List<SelectItem> getListaOrganizationRole() {
		
		if(listaOrganizationRole == null || listaOrganizationRole.size() == 0){
			prepararListaRole();
		}
		
		return listaOrganizationRole;
	}

	public void setListaOrganizationRole(
			List<SelectItem> listaOrganizationRole) {
		this.listaOrganizationRole = listaOrganizationRole;
	}

	public String getRoleSelecionado() {
		
		if(organizationSelecionado != null || organizationSelecionado.getId() != 0){
			try{
				roleSelecionado = String.valueOf(organizationSelecionado.getTbSysOrganizationRole().getId());
			}catch(NullPointerException e){
				roleSelecionado = "";
			}
		}
		
		return roleSelecionado;
	}

	public void setRoleSelecionado(String roleSelecionado) {
		this.roleSelecionado = roleSelecionado;
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
