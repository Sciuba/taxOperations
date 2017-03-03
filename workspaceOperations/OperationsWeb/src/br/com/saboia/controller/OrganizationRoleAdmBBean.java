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

import br.com.saboia.entity.TbSysAccessProfile;
import br.com.saboia.entity.TbSysAccessProfilePermission;
import br.com.saboia.entity.TbSysModule;
import br.com.saboia.entity.TbSysOrganizationRole;
import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.impl.AccessProfileFacadeImpl;
import br.com.saboia.facade.impl.AccessProfilePermissionFacadeImpl;
import br.com.saboia.facade.impl.ModuleFacadeImpl;
import br.com.saboia.facade.impl.OrganizationRoleFacadeImpl;
import br.com.saboia.facade.impl.SystemPermissionFacadeImpl;

/**
 * @author Fernando
 *
 */
@ManagedBean(name="roleAdmBBean")
@ViewScoped
public class OrganizationRoleAdmBBean {
	
	@EJB
	private OrganizationRoleFacadeImpl roleFacadeImpl;
	
	@EJB
	private AccessProfileFacadeImpl profileFacadeImpl;
	
	@EJB
	private AccessProfilePermissionFacadeImpl profilePermissionFacadeImpl;
	
	@EJB
	private SystemPermissionFacadeImpl permissionFacadeImpl;
	
	@EJB
	private ModuleFacadeImpl moduleFacadeImpl;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	private TbSysOrganizationRole roleSelecionado;
	
	private List<TbSysOrganizationRole> listaOrganizationRole; 
	
	private List<TbSysAccessProfile> listaProfile;
	
	private List<TbSysSystemPermission> listaPermission;
	
	private TbSysSystemPermission permissionSelecionado;
	
	private TbSysAccessProfile profileSelecionado;
	
	private TbSysAccessProfile novoProfile;
	
	private TbSysAccessProfile profileDefault;

	private List<TbSysModule> listaModule;
	
	private ResourceBundle bundle;
	
	private int count;
	
	private String roleCloneSelecionado;
	
	private String radioProfileSelecionado;
	
	private boolean radioDefaultNot;
	
	private boolean radioDefaultTrue;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
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
		
		Long idRole = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roleId");
		
		roleSelecionado = roleFacadeImpl.finOneResult(idRole);
		
		count = -1;
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		checkSecurity();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
		
		listaProfile = new ArrayList<>();
		listaModule = new ArrayList<>();
		listaPermission = new ArrayList<>();
		profileSelecionado = new TbSysAccessProfile();
		novoProfile = new TbSysAccessProfile();
		permissionSelecionado = new TbSysSystemPermission();
		
		profileDefault = profileFacadeImpl.findProfileDefault();
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
	
	
	public void preparaListProfile(){
		listaProfile = profileFacadeImpl.findByOrganizationRole(roleSelecionado.getId());
	}
	
	public void preparaListaRole(){
		listaOrganizationRole = roleFacadeImpl.findOrder();
	}
	
	public void preparaListaModule(){
		if(profileSelecionado.getId() != 0){
			listaModule = moduleFacadeImpl.findOrder();
			
			
			profileSelecionado.setTbSysAccessProfilePermissions(profilePermissionFacadeImpl.findByProfile(profileSelecionado.getId()));
			
			for(TbSysModule module: listaModule	){
				module.setTbSysSystemPermissions(new ArrayList<TbSysSystemPermission>());
				module.setTbSysSystemPermissions(permissionFacadeImpl.findByIdModule(module.getId()));
				
				for(TbSysAccessProfilePermission profilePermission : profileSelecionado.getTbSysAccessProfilePermissions()){
					for(TbSysSystemPermission permission : module.getTbSysSystemPermissions()){
						if(permission.getId() == profilePermission.getTbSysSystemPermission().getId()){
							permission.setChecked(true);
						}
					}
				}
			}
		}
		
		//Atualizo o Form da tela
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("newFormPerm");
		
		return;
		
	}
	
	public void loadPermission(Long idProfile){
		
		profileSelecionado = profileFacadeImpl.find(idProfile);
		
		listaPermission = permissionFacadeImpl.listByProfileAndRole(roleSelecionado.getId(), idProfile);
	}
	
	
	public void saveProfile(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
			if(novoProfile.getsDisplayName() == null || novoProfile.getsDescription().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")),""));
				
				listaProfile = new ArrayList<>();
				listaModule = new ArrayList<>();
				listaPermission = new ArrayList<>();
				profileSelecionado = new TbSysAccessProfile();
				novoProfile = new TbSysAccessProfile();
				permissionSelecionado = new TbSysSystemPermission();
				
				return;
				
			}
			
			if(novoProfile.getsDisplayName() == null || novoProfile.getsDisplayName().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_display_name")),""));
				
				listaProfile = new ArrayList<>();
				listaModule = new ArrayList<>();
				listaPermission = new ArrayList<>();
				profileSelecionado = new TbSysAccessProfile();
				novoProfile = new TbSysAccessProfile();
				permissionSelecionado = new TbSysSystemPermission();
				
				return;
				
			}
			
			try{
				
				TbSysAccessProfile accessProfile = profileFacadeImpl.findByOrganizationAndName(novoProfile.getsDisplayName(), roleSelecionado.getId());
				
				if(accessProfile != null){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
							bundle.getString("msg_warn_profile"),""));
					
					listaProfile = new ArrayList<>();
					listaModule = new ArrayList<>();
					listaPermission = new ArrayList<>();
					profileSelecionado = new TbSysAccessProfile();
					novoProfile = new TbSysAccessProfile();
					permissionSelecionado = new TbSysSystemPermission();
					
					return;
				}
				
			}catch (Exception e){
				
			}
			
			try{
				
				novoProfile.setTbSysOrganizationRole(roleSelecionado);
				
				profileFacadeImpl.save(novoProfile);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('profileDialog').hide();");
				
				listaProfile = new ArrayList<>();
				listaModule = new ArrayList<>();
				listaPermission = new ArrayList<>();
				profileSelecionado = new TbSysAccessProfile();
				novoProfile = new TbSysAccessProfile();
				permissionSelecionado = new TbSysSystemPermission();
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}catch (Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('profileDialog').hide();");
				
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

	public void updateProfile(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
		
			if(profileSelecionado.getsDisplayName() == null || profileSelecionado.getsDescription().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")),""));
				
				listaProfile = new ArrayList<>();
				listaModule = new ArrayList<>();
				listaPermission = new ArrayList<>();
				profileSelecionado = new TbSysAccessProfile();
				novoProfile = new TbSysAccessProfile();
				permissionSelecionado = new TbSysSystemPermission();
				
				return;
				
			}
			
			if(profileSelecionado.getsDisplayName() == null || profileSelecionado.getsDisplayName().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_display_name")),""));
				
				listaProfile = new ArrayList<>();
				listaModule = new ArrayList<>();
				listaPermission = new ArrayList<>();
				profileSelecionado = new TbSysAccessProfile();
				novoProfile = new TbSysAccessProfile();
				permissionSelecionado = new TbSysSystemPermission();
				
				return;
				
			}
			
			try{
				
				profileFacadeImpl.update(profileSelecionado);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				listaProfile = new ArrayList<>();
				listaModule = new ArrayList<>();
				listaPermission = new ArrayList<>();
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}catch (Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
			}
		
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
			
	}
	
	
	
	public void deleteProfile(){
		
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			try{
				
				profilePermissionFacadeImpl.deleteAllByProfile(profileSelecionado.getId());
				
				profileFacadeImpl.delete(profileSelecionado);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("delete_success"), ""));
				
				listaProfile = new ArrayList<>();
				listaPermission = new ArrayList<>();
				profileSelecionado = new TbSysAccessProfile();
				listaModule = new ArrayList<>();
				
				count = -1;
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}catch (Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("delete_error"), ""));
			}
		
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
			
	}
	
	
	
	public void savePermission(){
		
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			profileSelecionado.setTbSysAccessProfilePermissions(null);
			
			try{
				
				profilePermissionFacadeImpl.deleteAllByProfile(profileSelecionado.getId());
				
			}catch (Exception e){
				
			}
			
			List<TbSysSystemPermission> listaSel = new ArrayList<>();
			
			for(TbSysModule module : listaModule){
				for(TbSysSystemPermission permission : module.getTbSysSystemPermissions()){
					
					if(permission.isChecked()){
						listaSel.add(permission);
					}				
				}
			}
			
			if(listaSel.size() > 0){
				
				for(TbSysSystemPermission permission : listaSel){
					
					TbSysAccessProfilePermission accessProfilePermission = new TbSysAccessProfilePermission();
					
					accessProfilePermission.setTbSysSystemPermission(permission);
					accessProfilePermission.setTbSysAccessProfile(profileSelecionado);
					
					try{
						
						profilePermissionFacadeImpl.save(accessProfilePermission);
						
						loadPermission(profileSelecionado.getId());
						
						preparaListaModule();
						
					}catch(Exception e){
						e.printStackTrace();
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								bundle.getString("save_error"), ""));
						
						return;
					}
					
				}
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				listaProfile = new ArrayList<>();
				listaPermission = new ArrayList<>();
				
				loadPermission(profileSelecionado.getId());
				
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
	
	public void deletePermission(){
		
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			List<TbSysAccessProfilePermission> lista = profilePermissionFacadeImpl.findByProfile(profileSelecionado.getId());
			
			for(TbSysAccessProfilePermission profilePermission : lista ){
				if(profilePermission.getTbSysSystemPermission().getId() == permissionSelecionado.getId()){
					try{
						
						profilePermissionFacadeImpl.delete(profilePermission);
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
								bundle.getString("delete_success"), ""));
						
						listaProfile = new ArrayList<>();
						listaPermission = new ArrayList<>();
						
						loadPermission(profileSelecionado.getId());
						
						//Atualizo o Form da tela
						FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
						
					}catch (Exception e){
						e.printStackTrace();
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								bundle.getString("delete_error"), ""));
						
					}
				}
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
	}
	
	public void cloneProfile(){
		

		checkSecurity();
		
		if(!disabled && !disabledAll){
		
		
			if(roleCloneSelecionado == null || roleCloneSelecionado.isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_organization_role")),""));
				
				listaProfile = new ArrayList<>();
				listaModule = new ArrayList<>();
				listaPermission = new ArrayList<>();
				profileSelecionado = new TbSysAccessProfile();
				novoProfile = new TbSysAccessProfile();
				permissionSelecionado = new TbSysSystemPermission();
				
				return;
				
			}	
			
			
			if(novoProfile.getsDisplayName() == null || novoProfile.getsDescription().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")),""));
				
				listaProfile = new ArrayList<>();
				listaModule = new ArrayList<>();
				//listaPermission = new ArrayList<>();
				//profileSelecionado = new TbSysAccessProfile();
				novoProfile = new TbSysAccessProfile();
				permissionSelecionado = new TbSysSystemPermission();
				
				return;
				
			}
			
			if(novoProfile.getsDescription() == null || novoProfile.getsDescription().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")),""));
				
				listaProfile = new ArrayList<>();
				listaModule = new ArrayList<>();
				//listaPermission = new ArrayList<>();
				//profileSelecionado = new TbSysAccessProfile();
				novoProfile = new TbSysAccessProfile();
				permissionSelecionado = new TbSysSystemPermission();
				
				return;
				
			}
			
			try{
				
				TbSysAccessProfile accessProfile = profileFacadeImpl.findByOrganizationAndName(novoProfile.getsDisplayName(), Long.valueOf(roleCloneSelecionado));
				
				if(accessProfile != null){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
							bundle.getString("msg_warn_profile"),""));
					
					listaProfile = new ArrayList<>();
					listaModule = new ArrayList<>();
					//listaPermission = new ArrayList<>();
					//profileSelecionado = new TbSysAccessProfile();
					novoProfile = new TbSysAccessProfile();
					permissionSelecionado = new TbSysSystemPermission();
					
					return;
				}
				
			}catch (Exception e){
				
			}
			
			try{
				
				novoProfile.setTbSysOrganizationRole(roleFacadeImpl.finOneResult(Long.valueOf(roleCloneSelecionado)));
				
				novoProfile.setTbSysAccessProfilePermissions(new ArrayList<TbSysAccessProfilePermission>());
				
				novoProfile = profileFacadeImpl.saveReturn(novoProfile);
				
				for(TbSysSystemPermission permission : listaPermission){
					
					TbSysAccessProfilePermission profilePermission = new TbSysAccessProfilePermission();
					
					profilePermission.setTbSysAccessProfile(novoProfile);
					profilePermission.setTbSysSystemPermission(permission);
					
					try{
						
						profilePermissionFacadeImpl.save(profilePermission);
						
					}catch(Exception e){
						e.printStackTrace();
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								bundle.getString("delete_error"), ""));
						
						listaProfile = new ArrayList<>();
						listaModule = new ArrayList<>();
						//listaPermission = new ArrayList<>();
						//profileSelecionado = new TbSysAccessProfile();
						novoProfile = new TbSysAccessProfile();
						permissionSelecionado = new TbSysSystemPermission();
						
						return;
					}
					
				}
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('cloneDetail').hide();");
				
				listaProfile = new ArrayList<>();
				listaModule = new ArrayList<>();
				//listaPermission = new ArrayList<>();
				//profileSelecionado = new TbSysAccessProfile();
				novoProfile = new TbSysAccessProfile();
				permissionSelecionado = new TbSysSystemPermission();
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}catch (Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('cloneDetail').hide();");
				
				listaProfile = new ArrayList<>();
				listaModule = new ArrayList<>();
				//listaPermission = new ArrayList<>();
				//profileSelecionado = new TbSysAccessProfile();
				novoProfile = new TbSysAccessProfile();
				permissionSelecionado = new TbSysSystemPermission();
				
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
	
	public void saveDefaultProfile(){
		

		checkSecurity();
		
		if(!disabled && !disabledAll){
		
		
			if(radioDefaultTrue){
				
				try{
				
					profileFacadeImpl.noDefaultProfile();
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					return;
					
				}
				
				if(radioProfileSelecionado.equals("-1")){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
							"You must choose a profile to save as default.", ""));
					
					return;
					
				}else{
					
					profileSelecionado = profileFacadeImpl.find(Long.valueOf(radioProfileSelecionado));
					
					profileSelecionado.setfDefault(true);
					
				}
				
				try{
					
					profileFacadeImpl.update(profileSelecionado);
					
					profileSelecionado = new TbSysAccessProfile();
					
					profileDefault = profileFacadeImpl.findProfileDefault();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('profileDefault').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('profileDefault').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				}
			
			}else{
				
				try{
	
					profileFacadeImpl.noDefaultProfile();
					
					profileDefault = null;
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('profileDefault').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('profileDefault').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					return;
					
				}			
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
	}
	
	public void rdoChangeDefFalse(){
	
		radioDefaultTrue = false;
		radioDefaultNot = true;
		
		RequestContext.getCurrentInstance().execute("disableComponents('true');");
			
	}
	
	public void rdoChangeDefTrue(){
		
		radioDefaultTrue = true;
		radioDefaultNot = false;
		
		RequestContext.getCurrentInstance().execute("disableComponents('false');");
		
	}
	
	public void atualizaHidden(){
		RequestContext.getCurrentInstance().execute("atualizaComponenteDefaultProfile();");
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("hiddenDef");
	}
	
	/**
	 * Getters and Setters
	 */

	public TbSysOrganizationRole getRoleSelecionado() {
		return roleSelecionado;
	}


	public void setRoleSelecionado(TbSysOrganizationRole roleSelecionado) {
		this.roleSelecionado = roleSelecionado;
	}


	public List<TbSysAccessProfile> getListaProfile() {
		
		if(listaProfile == null || listaProfile.isEmpty()){
			preparaListProfile();
		}
		
		return listaProfile;
	}


	public void setListaProfile(List<TbSysAccessProfile> listaProfile) {
		this.listaProfile = listaProfile;
	}



	public TbSysAccessProfile getProfileSelecionado() {
		return profileSelecionado;
	}



	public void setProfileSelecionado(TbSysAccessProfile profileSelecionado) {
		this.profileSelecionado = profileSelecionado;
	}



	public TbSysAccessProfile getNovoProfile() {
		return novoProfile;
	}



	public void setNovoProfile(TbSysAccessProfile novoProfile) {
		this.novoProfile = novoProfile;
	}


	public List<TbSysModule> getListaModule() {
		
		//if((listaModule == null || listaModule.size() == 0) && profileSelecionado.getId() != 0 ){
			preparaListaModule();
		//}
		
		return listaModule;
	}


	public void setListaModule(List<TbSysModule> listaModule) {
		this.listaModule = listaModule;
	}


	public List<TbSysSystemPermission> getListaPermission() {
		return listaPermission;
	}

	

	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public void setListaPermission(List<TbSysSystemPermission> listaPermission) {
		this.listaPermission = listaPermission;
	}


	public TbSysSystemPermission getPermissionSelecionado() {
		return permissionSelecionado;
	}


	public void setPermissionSelecionado(TbSysSystemPermission permissionSelecionado) {
		this.permissionSelecionado = permissionSelecionado;
	}


	public List<TbSysOrganizationRole> getListaOrganizationRole() {
		
		preparaListaRole();
		
		return listaOrganizationRole;
	}


	public void setListaOrganizationRole(
			List<TbSysOrganizationRole> listaOrganizationRole) {
		this.listaOrganizationRole = listaOrganizationRole;
	}


	public String getRoleCloneSelecionado() {
		return roleCloneSelecionado;
	}


	public void setRoleCloneSelecionado(String roleCloneSelecionado) {
		this.roleCloneSelecionado = roleCloneSelecionado;
	}


	public String getRadioProfileSelecionado() {
		
		if(profileDefault != null){
			radioProfileSelecionado = String.valueOf(profileDefault.getId());
		}
		
		return radioProfileSelecionado;
	}


	public void setRadioProfileSelecionado(String radioProfileSelecionado) {
		this.radioProfileSelecionado = radioProfileSelecionado;
	}

	
	public boolean isRadioDefaultNot() {
		
		if(profileDefault == null){
			radioDefaultNot = true;
			radioDefaultTrue = false;
		}
		
		return radioDefaultNot;
	}


	public void setRadioDefaultNot(boolean radioDefaultNot) {
		this.radioDefaultNot = radioDefaultNot;
	}


	public boolean isRadioDefaultTrue() {
		
		if(profileDefault != null){
			radioDefaultTrue = true;
			radioDefaultNot = false;
		}
		
		return radioDefaultTrue;
	}


	public void setRadioDefaultTrue(boolean radioDefaultTrue) {
		this.radioDefaultTrue = radioDefaultTrue;
	}


	public TbSysAccessProfile getProfileDefault() {
		return profileDefault;
	}


	public void setProfileDefault(TbSysAccessProfile profileDefault) {
		this.profileDefault = profileDefault;
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
