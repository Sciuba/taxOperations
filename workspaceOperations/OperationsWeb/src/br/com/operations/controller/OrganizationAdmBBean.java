package br.com.operations.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.validation.ConstraintViolationException;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.primefaces.context.RequestContext;

import br.com.operations.entity.TbAdmCategory;
import br.com.operations.entity.TbSysAccessProfile;
import br.com.operations.entity.TbSysAccessProfileUser;
import br.com.operations.entity.TbSysOrganization;
import br.com.operations.entity.TbSysOrganizationRole;
import br.com.operations.entity.TbSysSystemPermission;
import br.com.operations.entity.TbSysUser;
import br.com.operations.entity.TbSysWorkGroup;
import br.com.operations.entity.TbSysWorkGroupUser;
import br.com.operations.facade.CategoryFacade;
import br.com.operations.facade.UserFacade;
import br.com.operations.facade.impl.AccessProfileFacadeImpl;
import br.com.operations.facade.impl.AccessProfileUserFacadeImpl;
import br.com.operations.facade.impl.OrganizationFacadeImpl;
import br.com.operations.facade.impl.OrganizationRoleFacadeImpl;
import br.com.operations.facade.impl.SystemPermissionFacadeImpl;
import br.com.operations.facade.impl.WorkGroupFacadeImpl;
import br.com.operations.facade.impl.WorkGroupUserFacadeImpl;

@ManagedBean(name = "orgAdmBBean")
@ViewScoped
public class OrganizationAdmBBean {
	
	
	@EJB
	private OrganizationFacadeImpl organizationFacadeImpl;
	
	@EJB
	private OrganizationRoleFacadeImpl roleFacadeImpl;
	
	@EJB
	private UserFacade userFacade;
	
	@EJB
	private AccessProfileFacadeImpl profileFacadeImpl;
	
	@EJB
	private AccessProfileUserFacadeImpl profileUserFacadeImpl; 
	
	@EJB
	private WorkGroupFacadeImpl groupFacadeImpl;
	
	@EJB
	private WorkGroupUserFacadeImpl groupUserFacadeImpl;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	//TODO: remover posteriormente
	@EJB
	private CategoryFacade categoryFacade;
	//
	
	private List<TbAdmCategory> listaCategory;
	
	private TbSysOrganization organizationSelecionado;
	
	private List<TbSysWorkGroup> listaWorkGroup;
	
	private List<TbSysUser> listaUsers;
	
	private List<TbSysUser> listaUsersGrupoSelecionado;
	
	private List<TbSysUser> listaUsersProfileSelecionado;
	
	private List<TbSysUser> listaUsersAddProfile;
	
	private List<TbSysUser> listaUsersAddGrupo;
	
	private TbSysWorkGroup grupoSelecionado;
	
	private TbSysWorkGroup novoGrupo;
	
	private TbSysUser novoUsuario;
	
	private TbSysUser usuarioSelecionado;
	
	private TbSysUser usuarioGrupoSelecionado;
	
	private List<TbSysAccessProfile> listaAccessProfile;
	
	private TbSysAccessProfile profileSelecionado;
	
	private ResourceBundle bundle;
	
	private int activeIndex;
	
	private int countWork;
	
	private int countProfile;
	
	private int posUsuarioGrupoSelecionado;
	
	private String password;
	private String rePassword;
	private String roleSelecionado;
	
	private List<SelectItem> listaOrganizationRole;
	
	private final String SMODULE = "organization";
	
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
		
		bundle = ResourceBundle.getBundle("br.com.operations.bundle.messages", Locale.getDefault());
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		checkSecurity();
		
		listaAccessProfile = new ArrayList<>();
		listaUsers = new ArrayList<>();
		listaWorkGroup = new ArrayList<>();
		listaCategory = new ArrayList<>();
		listaOrganizationRole = new ArrayList<>();
		profileSelecionado = new TbSysAccessProfile();
		grupoSelecionado = new TbSysWorkGroup();
		novoGrupo = new TbSysWorkGroup();
		novoUsuario = new TbSysUser();
		usuarioSelecionado = new TbSysUser();
		
		countProfile = -1;
		countWork = -1;
		
		Long id = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idOrganization");
		
		organizationSelecionado = organizationFacadeImpl.find(id);
		
		roleSelecionado = String.valueOf(organizationSelecionado.getTbSysOrganizationRole().getId());
		
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
	
	public void prepararListaRole(){
		List<TbSysOrganizationRole> lista = roleFacadeImpl.findOrder();
		
		for(TbSysOrganizationRole role : lista){
			listaOrganizationRole.add(new SelectItem(role.getId(), role.getsDisplayName()));
		}
	}
	
	public void preparaListaProfile(){
		
		try{
			listaAccessProfile = profileFacadeImpl.findByOrganizationRole(organizationSelecionado.getTbSysOrganizationRole().getId());
		}catch(Exception e){
			listaAccessProfile = new ArrayList<>();
		}
		
	}
	
	public void preparaListaWorkGroup(){
		
		try{
			List<TbSysWorkGroup> lista = groupFacadeImpl.findOrderByOrganization(organizationSelecionado.getTbSysOrganizationRole().getId());
			
			TbSysWorkGroup sysWorkGroup = new TbSysWorkGroup();
			sysWorkGroup.setsName(organizationSelecionado.getsGroupEveryOneName());
			sysWorkGroup.setId(0);
			sysWorkGroup.setsComments("Grupo padrão do Sistema.");
			
			listaWorkGroup.add(sysWorkGroup);
			
			for(int i = 0; i < lista.size(); i++){
				listaWorkGroup.add(lista.get(i));
			}
			
		}catch(Exception e){
			listaWorkGroup = new ArrayList<>();
		}
		
	}
	
	public void preparaListaSysUser(){
		
		try{
			listaUsers = userFacade.findByOrganization(organizationSelecionado.getId());
		}catch(Exception e){
			listaUsers = new ArrayList<>();
		}
		
	}
	
	public void preparaListaAddGrupo(){
		
		try{
			List<TbSysUser> lista = userFacade.findByOrganization(organizationSelecionado.getId());
			
			if(listaUsersGrupoSelecionado == null || listaUsersGrupoSelecionado.isEmpty()){
				listaUsersAddGrupo = lista;
			}else{
				
				listaUsersAddGrupo = new ArrayList<>();
				
				for(TbSysUser l : lista){
					
					for(TbSysUser u : listaUsersGrupoSelecionado){
						if(l.getId() == u.getId()){
							l.setCkecked(true);
							break;
						}
					}
					
					listaUsersAddGrupo.add(l);
				}
				
			}
			
		}catch(Exception e){
			listaUsersAddGrupo = new ArrayList<>();
		}
		
	}
	
	public void preparaListaAddProfile(){
		
		try{
			List<TbSysUser> lista = userFacade.findByOrganization(organizationSelecionado.getId());
			
			if(listaUsersProfileSelecionado == null || listaUsersProfileSelecionado.isEmpty()){
				listaUsersAddProfile = lista;
			}else{
				
				listaUsersAddProfile = new ArrayList<>();
				
				for(TbSysUser l : lista){
					
					for(TbSysUser u : listaUsersProfileSelecionado){
						if(l.getId() == u.getId()){
							l.setCkecked(true);
							break;
						}
					}
					
					listaUsersAddProfile.add(l);
				}
				
			}
			
		}catch(Exception e){
			listaUsersAddProfile = new ArrayList<>();
		}
		
	}
	
	public void preparaListaCategory(){
		
		List<TbAdmCategory> lista = categoryFacade.findAll();
		
		TbAdmCategory cat = new TbAdmCategory();
		cat.setId(-1);
		cat.setsCategory(" ");
		
		if(lista.size() > 0){
			for(TbAdmCategory category : lista){
				listaCategory.add(category);
			}
		}
	}

	
	public void update(){
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
		
			if((roleSelecionado == null || roleSelecionado.isEmpty()) || roleSelecionado.equals("-1")){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_organization_role")),""));						
				
				return;
				
			}else{
				organizationSelecionado.setTbSysOrganizationRole(roleFacadeImpl.finOneResult(Long.valueOf(roleSelecionado)));
			}
	
	
			if(organizationSelecionado.getsName() == null || organizationSelecionado.getsName().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_name")),""));						
				
				return;
				
			}
			
			if(organizationSelecionado.getsDescription() == null || organizationSelecionado.getsDescription().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")),""));						
						
				return;
				
			}
			
			try{
				
				organizationFacadeImpl.update(organizationSelecionado);
				
						
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
						
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}catch(Exception e){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			}
		
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
			
	}
	
	
	public void saveUser(){
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
		
			if(novoUsuario.getsLogon() == null || novoUsuario.getsLogon().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_logon")),""));
				
				listaUsers = new ArrayList<>();
				
				return;
				
			}
			
			if(novoUsuario.getsPassword() == null || novoUsuario.getsPassword().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_password")),""));
				
				listaUsers = new ArrayList<>();
				
				return;
				
			}
			
			if(!novoUsuario.getsPassword().equals(password)){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Passwords must be identical",""));
				
				listaUsers = new ArrayList<>();
				
				return;
				
			}
			
			if(novoUsuario.getsFullName() == null || novoUsuario.getsFullName().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_name")),""));
				
				listaUsers = new ArrayList<>();
				
				return;
				
			}
			
			if(novoUsuario.getsEmail() == null || novoUsuario.getsEmail().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_email")),""));
				
				listaUsers = new ArrayList<>();
				
				return;
				
			}
			
			if(novoUsuario.getsDepartment() == null || novoUsuario.getsDepartment().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_departament")),""));
				
				listaUsers = new ArrayList<>();
				
				return;
				
			}
			
			
			try{
				
				TbSysUser user = userFacade.find(novoUsuario.getsLogon());
				
				if(user != null){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
							"There is already a registered user with this login.",""));
					
					listaUsers = new ArrayList<>();
					
					return;
					
				}
							
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				
				novoUsuario.setTbSysOrganization(organizationSelecionado);			
				novoUsuario.setDtRegister(new Date());
				
				novoUsuario = userFacade.saveReturn(novoUsuario);			
				
				
				TbSysAccessProfile accessProfile = profileFacadeImpl.findProfileDefault();
				
				if(accessProfile != null){
					
					TbSysAccessProfileUser accessProfileUser = new TbSysAccessProfileUser();
					accessProfileUser.setTbSysAccessProfile(accessProfile);
					accessProfileUser.setTbSysUser(novoUsuario);
					
					profileUserFacadeImpl.save(accessProfileUser);
					
				}
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"),""));
				
				listaUsers = new ArrayList<>();
				novoUsuario = new TbSysUser();
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('addUser').hide();");
				
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("newUser");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}catch(Exception e){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
						bundle.getString("save_error"),""));
				
				listaUsers = new ArrayList<>();
				novoUsuario = new TbSysUser();
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('addUser').hide();");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}	
	}
	
	
	public void updateUser(){
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
			if(usuarioSelecionado.getsLogon() == null || usuarioSelecionado.getsLogon().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_logon")),""));
				
				listaUsers = new ArrayList<>();
				
				return;
				
			}
			
			if(usuarioSelecionado.getsFullName() == null || usuarioSelecionado.getsFullName().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_name")),""));
				
				listaUsers = new ArrayList<>();
				
				return;
				
			}
			
			if(usuarioSelecionado.getsEmail() == null || usuarioSelecionado.getsEmail().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_email")),""));
				
				listaUsers = new ArrayList<>();
				
				return;
				
			}
			
			if(usuarioSelecionado.getsDepartment() == null || usuarioSelecionado.getsDepartment().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_departament")),""));
				
				listaUsers = new ArrayList<>();
				
				return;
				
			}
			
			
			try{
				
				TbSysUser user = userFacade.find(usuarioSelecionado.getsLogon());
				
				if(user != null && user.getId() != usuarioSelecionado.getId()){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
							"There is already a registered user with this login.",""));
					
					listaUsers = new ArrayList<>();
					
					return;
					
				}
							
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				
				if(password != null && !password.isEmpty()){
					
					if(!password.equals(rePassword)){
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
								"Passwords must be identical",""));
						
						listaUsers = new ArrayList<>();
						
						return;
						
					}else{
					
						usuarioSelecionado.setsPassword(password);
						usuarioSelecionado.setDtPasswordUpdate(new Date());
						
						if(userLogged.getId() == usuarioSelecionado.getId()){
							userLogged.setsPassword(password);
							
							LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
							
							login.setUser(userLogged);
							
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("loginBBean");
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loginBBean", login);
							
						}
						
					}
				}
				
				userFacade.alter(usuarioSelecionado);			
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"),""));
				
				listaUsers = new ArrayList<>();
				usuarioSelecionado = new TbSysUser();
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('editUser').hide();");
				
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("editUser");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}catch(Exception e){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"),""));
				
				listaUsers = new ArrayList<>();
				usuarioSelecionado = new TbSysUser();
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('editUser').hide();");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
	}
	
	
	public void deleteUser(){
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
			try{
				
				userFacade.delete(usuarioSelecionado);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("delete_success"),""));
				
				listaUsers = new ArrayList<>();
				usuarioSelecionado = new TbSysUser();
				
			}catch(ConstraintViolationException cve){
				cve.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"This User this being used in the system.",""));
				
				listaUsers = new ArrayList<>();
				usuarioSelecionado = new TbSysUser();
				
			}catch (DatabaseException de) {
				de.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
						bundle.getString("delete_error"),""));
				
				listaUsers = new ArrayList<>();
				usuarioSelecionado = new TbSysUser();
			}
		
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
	}
	
	
	
	public void saveWork(){
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
		
			if(novoGrupo.getsName() == null || novoGrupo.getsName().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_name")),""));
				
				listaWorkGroup = new ArrayList<>();
				
				return;
				
			}
			
			if(novoGrupo.getsComments() == null || novoGrupo.getsComments().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_comment")),""));
				
				listaWorkGroup = new ArrayList<>();
				
				return;
				
			}
			
			try{
				
				TbSysWorkGroup work = groupFacadeImpl.findOrderByNameAndOrganization(novoGrupo.getsName(), organizationSelecionado.getId());
				
				if(work != null){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
							"There is already a registered workGroup with this name.",""));
					
					listaWorkGroup = new ArrayList<>();
					
					return;
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				
				novoGrupo.setTbSysOrganization(organizationSelecionado);
				
				groupFacadeImpl.save(novoGrupo);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"),""));
				
				listaWorkGroup = new ArrayList<>();
				novoGrupo = new TbSysWorkGroup();
				listaUsers = new ArrayList<>();
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('addWork').hide();");
				
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("newWork");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}catch(Exception e){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"),""));
				
				listaWorkGroup = new ArrayList<>();
				novoGrupo = new TbSysWorkGroup();
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('editUser').hide();");
				
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("newWork");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
		
	}
	
	
	public void updateWork(){
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
		
			if(grupoSelecionado.getsName() == null || grupoSelecionado.getsName().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_name")),""));
				
				listaWorkGroup = new ArrayList<>();
				
				return;
				
			}
			
			if(grupoSelecionado.getsComments() == null || grupoSelecionado.getsComments().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_comment")),""));
				
				listaWorkGroup = new ArrayList<>();
				
				return;
				
			}
			
			try{
				
				TbSysWorkGroup work = groupFacadeImpl.findOrderByNameAndOrganization(novoGrupo.getsName(), organizationSelecionado.getId());
				
				if(work != null && work.getId() != grupoSelecionado.getId()){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
							"There is already a registered workGroup with this name.",""));
					
					listaWorkGroup = new ArrayList<>();
					
					return;
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				
				groupFacadeImpl.update(grupoSelecionado);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"),""));
				
				listaWorkGroup = new ArrayList<>();
				//grupoSelecionado = new TbSysWorkGroup();
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('editWork').hide();");
				
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("editWork");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}catch(Exception e){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"),""));
				
				listaWorkGroup = new ArrayList<>();
				grupoSelecionado = new TbSysWorkGroup();
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('editWork').hide();");
				
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("editWork");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
	}
	
	
	public void deleteWork(){
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
		
			List<TbSysWorkGroupUser> listGroupUser = groupUserFacadeImpl.findByGroup(grupoSelecionado.getId());
			
			if(listGroupUser != null && !listGroupUser.isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"There are users connected to this group. impossible to delete.",""));
				
				return;
				
			}else{
				
				try{
					
					groupFacadeImpl.delete(grupoSelecionado);
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"),""));
					
					listaWorkGroup = new ArrayList<>();
					grupoSelecionado = new TbSysWorkGroup();
					
				}catch(Exception e){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"),""));
					
					listaWorkGroup = new ArrayList<>();
					grupoSelecionado = new TbSysWorkGroup();
					
				}
				
			}
		
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
	}
	
	
	
	public void addUsersToGroup(){
		
		try{
			
			groupUserFacadeImpl.deleteAllUsersInGroup(grupoSelecionado.getId());
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"),""));
			
			listaWorkGroup = new ArrayList<>();
			grupoSelecionado = new TbSysWorkGroup();
			
			return;
		}
		
		
		for(TbSysUser u : listaUsersAddGrupo){
			
			if(u.isCkecked()){
				
				TbSysWorkGroupUser workGroupUser = new TbSysWorkGroupUser();
				
				workGroupUser.setTbSysUser(u);
				workGroupUser.setTbSysWorkGroup(grupoSelecionado);
				
				try{
					
					groupUserFacadeImpl.save(workGroupUser);
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"),""));
					
					listaWorkGroup = new ArrayList<>();
										
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					return;
				}				
			}	
			
		}
		
		FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
				bundle.getString("save_success"),""));
		
		listaWorkGroup = new ArrayList<>();
		listaUsersGrupoSelecionado = new ArrayList<>();
		listaUsersAddGrupo = new ArrayList<>();
		
		loadUsers(String.valueOf(grupoSelecionado.getId()));
		
		preparaListaAddGrupo();
		
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("userToGroup");
		
//		//Atualizo o Form da tela
//		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("workGroups");
		
		//Atualizo o Form da tela
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
		
	}
	
	
	public void loadUsers(String id){
		
		listaUsersGrupoSelecionado = new ArrayList<>();
		
		grupoSelecionado = groupFacadeImpl.find(Long.valueOf(id));
		
		if(!id.equals("0")){
			listaUsersGrupoSelecionado = userFacade.findByGrupoAndOrganization(organizationSelecionado.getId(), Long.valueOf(id));
			
			for(TbSysUser u : listaUsersGrupoSelecionado){
				u.setfManager(managerGroup(Long.valueOf(id), u.getId()));
			}
			
		}else{
			listaUsersGrupoSelecionado = userFacade.findByOrganization(organizationSelecionado.getId());
		}
		
		preparaListaAddGrupo();
		
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("userToGroup");
	}
	
	public boolean managerGroup(Long idGroup, Long idUser){
		
		TbSysWorkGroupUser user = groupUserFacadeImpl.findByUserAndGroup(idUser, idGroup);
		
		if(user != null && user.isfManager()){
			return true;
		}else{
			return false;
		}
		
	}
	
	public void changeUserManager(){
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
			TbSysWorkGroupUser user = groupUserFacadeImpl.findByUserAndGroup(usuarioGrupoSelecionado.getId(), grupoSelecionado.getId());
			
			if(user.isfManager()){
							
				user.setfManager(false);
				
			}else{
				user.setfManager(true);
			}
			
			try{
				
				groupUserFacadeImpl.update(user);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"),""));
				
				listaWorkGroup = new ArrayList<>();
				listaUsersGrupoSelecionado = new ArrayList<>();
				listaUsersAddGrupo = new ArrayList<>();
				usuarioGrupoSelecionado = new TbSysUser();
				
				loadUsers(String.valueOf(grupoSelecionado.getId()));
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("workGroups");
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_error"),""));
				
				listaWorkGroup = new ArrayList<>();
				listaUsersGrupoSelecionado = new ArrayList<>();
				listaUsersAddGrupo = new ArrayList<>();
				grupoSelecionado = new TbSysWorkGroup();
				usuarioGrupoSelecionado = new TbSysUser();
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			}
		
		}else if(disabled){
				
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
			
	}
	
	
	public void addUsersToProfile(){
		try{
			
			profileUserFacadeImpl.deleteAllUsersInProfile(profileSelecionado.getId());
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"),""));
			
			listaAccessProfile = new ArrayList<>();
			profileSelecionado = new TbSysAccessProfile();
			
			return;
		}
		
		for(TbSysUser u : listaUsersAddProfile){
			
			if(u.isCkecked()){
				
				TbSysAccessProfileUser accessProfileUser = new TbSysAccessProfileUser();
				
				accessProfileUser.setTbSysUser(u);
				accessProfileUser.setTbSysAccessProfile(profileSelecionado);
				
				try{
					
					profileUserFacadeImpl.save(accessProfileUser);
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"),""));
					
					listaAccessProfile = new ArrayList<>();
										
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					return;
				}				
			}				
		}
		
		FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
				bundle.getString("save_success"),""));
		
		listaAccessProfile = new ArrayList<>();
		listaUsersProfileSelecionado = new ArrayList<>();
		listaUsersAddProfile = new ArrayList<>();
		listaUsers = new ArrayList<>();
		
		loadUsersProfile(String.valueOf(profileSelecionado.getId()));
		
		preparaListaAddProfile();
		
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("userToProfile");
		
//		//Atualizo o Form da tela
//		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("workGroups");
		
		//Atualizo o Form da tela
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
	}
	
	public void loadUsersProfile(String id){
		
		profileSelecionado = profileFacadeImpl.find(Long.valueOf(id));
		
		listaUsersProfileSelecionado = userFacade.findByProfile(profileSelecionado.getId());
		
		preparaListaAddProfile();
		
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("userToProfile");
	}
	
	
	/**
	 * Getters and Setters
	 */

	public List<TbSysWorkGroup> getListaWorkGroup() {
		
		if(listaWorkGroup == null || listaWorkGroup.isEmpty()){
			preparaListaWorkGroup();
		}
		
		return listaWorkGroup;
	}



	public void setListaWorkGroup(List<TbSysWorkGroup> listaWorkGroup) {
		this.listaWorkGroup = listaWorkGroup;
	}



	public List<TbSysUser> getListaUsers() {
		
		if(listaUsers == null || listaUsers.isEmpty()){
			preparaListaSysUser();
		}
		
		return listaUsers;
	}



	public void setListaUsers(List<TbSysUser> listaUsers) {
		this.listaUsers = listaUsers;
	}



	public TbSysWorkGroup getGrupoSelecionado() {
		return grupoSelecionado;
	}


	public void setGrupoSelecionado(TbSysWorkGroup grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public TbSysWorkGroup getNovoGrupo() {
		return novoGrupo;
	}


	public void setNovoGrupo(TbSysWorkGroup novoGrupo) {
		this.novoGrupo = novoGrupo;
	}


	public TbSysUser getNovoUsuario() {
		return novoUsuario;
	}


	public void setNovoUsuario(TbSysUser novoUsuario) {
		this.novoUsuario = novoUsuario;
	}


	public TbSysUser getUsuarioSelecionado() {
		return usuarioSelecionado;
	}


	public void setUsuarioSelecionado(TbSysUser usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}


	public TbSysOrganization getOrganizationSelecionado() {
		return organizationSelecionado;
	}



	public void setOrganizationSelecionado(TbSysOrganization organizationSelecionado) {
		this.organizationSelecionado = organizationSelecionado;
	}



	public List<TbSysAccessProfile> getListaAccessProfile() {
		
		preparaListaProfile();
		
		return listaAccessProfile;
	}



	public void setListaAccessProfile(List<TbSysAccessProfile> listaAccessProfile) {
		this.listaAccessProfile = listaAccessProfile;
	}



	public TbSysAccessProfile getProfileSelecionado() {
		return profileSelecionado;
	}



	public void setProfileSelecionado(TbSysAccessProfile profileSelecionado) {
		this.profileSelecionado = profileSelecionado;
	}


	public int getActiveIndex() {
		return activeIndex;
	}


	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}


	public List<TbAdmCategory> getListaCategory() {
		
		if(listaCategory.size() == 0){
			preparaListaCategory();
		}
		
		return listaCategory;
	}


	public void setListaCategory(List<TbAdmCategory> listaCategory) {
		this.listaCategory = listaCategory;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getCountWork() {
		return countWork;
	}


	public void setCountWork(int countWork) {
		this.countWork = countWork;
	}


	public int getCountProfile() {
		return countProfile;
	}


	public void setCountProfile(int countProfile) {
		this.countProfile = countProfile;
	}


	public List<TbSysUser> getListaUsersGrupoSelecionado() {
		return listaUsersGrupoSelecionado;
	}


	public void setListaUsersGrupoSelecionado(
			List<TbSysUser> listaUsersGrupoSelecionado) {
		this.listaUsersGrupoSelecionado = listaUsersGrupoSelecionado;
	}


	public List<TbSysUser> getListaUsersAddGrupo() {
		
		preparaListaAddGrupo();
					
		return listaUsersAddGrupo;
	}


	public void setListaUsersAddGrupo(List<TbSysUser> listaUsersAddGrupo) {
		this.listaUsersAddGrupo = listaUsersAddGrupo;
	}


	public TbSysUser getUsuarioGrupoSelecionado() {
		return usuarioGrupoSelecionado;
	}


	public void setUsuarioGrupoSelecionado(TbSysUser usuarioGrupoSelecionado) {
		this.usuarioGrupoSelecionado = usuarioGrupoSelecionado;
	}


	public List<TbSysUser> getListaUsersProfileSelecionado() {
		return listaUsersProfileSelecionado;
	}


	public void setListaUsersProfileSelecionado(
			List<TbSysUser> listaUsersProfileSelecionado) {
		this.listaUsersProfileSelecionado = listaUsersProfileSelecionado;
	}


	public List<TbSysUser> getListaUsersAddProfile() {
		return listaUsersAddProfile;
	}


	public void setListaUsersAddProfile(List<TbSysUser> listaUsersAddProfile) {
		this.listaUsersAddProfile = listaUsersAddProfile;
	}


	public String getRePassword() {
		return rePassword;
	}


	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}


	public List<SelectItem> getListaOrganizationRole() {
		
		if(listaOrganizationRole == null || listaOrganizationRole.size() == 0){
			prepararListaRole();
		}
		
		return listaOrganizationRole;
	}


	public void setListaOrganizationRole(List<SelectItem> listaOrganizationRole) {
		this.listaOrganizationRole = listaOrganizationRole;
	}


	public String getRoleSelecionado() {
		
		if(organizationSelecionado.getTbSysOrganizationRole() != null){
			roleSelecionado = String.valueOf(organizationSelecionado.getTbSysOrganizationRole().getId());
		}else{
			roleSelecionado = "-1";
		}
		
		return roleSelecionado;
	}


	public void setRoleSelecionado(String roleSelecionado) {
		this.roleSelecionado = roleSelecionado;
	}


	public int getPosUsuarioGrupoSelecionado() {
		return posUsuarioGrupoSelecionado;
	}


	public void setPosUsuarioGrupoSelecionado(int posUsuarioGrupoSelecionado) {
		this.posUsuarioGrupoSelecionado = posUsuarioGrupoSelecionado;
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
