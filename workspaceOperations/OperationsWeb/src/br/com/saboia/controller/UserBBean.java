package br.com.saboia.controller;

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

import br.com.saboia.entity.TbAdmCategory;
//import br.com.saboia.entity.TbAdmPerson;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.CategoryFacade;
import br.com.saboia.facade.UserFacade;

@ManagedBean(name="userBBean")
@ViewScoped
public class UserBBean {
	
	@EJB
	private UserFacade userFacade;
	
	@EJB
	private CategoryFacade categoryFacade;
	
//	@EJB
//	private UserProfileFacade userProfileFacade;
	
	private List<TbSysUser> listaUsuario;
	
	private TbSysUser novoUsuario;
	
	private TbSysUser usuarioSelecionado;
	
//	private List<TbAdmPerson> listaAssociates;
	
	private List<TbAdmCategory> listaCategory;
	
//	private List<TbSysUserProfile> listaUserProfile;
	
	private String profileSelecionado;
	private String associateSelecionado;
	private String novoPassword;
	
	private ResourceBundle bundle;
	
	
	/**
	 * Metodos
	 */

	@PostConstruct
	public void init(){
		listaUsuario = new ArrayList<>();
//		listaAssociates = new ArrayList<>();
//		listaUserProfile = new ArrayList<>();
		listaCategory = new ArrayList<>();
		
		novoUsuario = new TbSysUser();
		usuarioSelecionado = new TbSysUser();
		
		profileSelecionado = "";
		associateSelecionado = "";
		novoPassword = "";
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
	}
	
	
	public void preparaListaUsuario(){
		listaUsuario = userFacade.findAll();
	}
	
//	public void preparaListaAssociate(){
//		
//		List<TbAdmPerson> lista = personFacade.findAll();
//		
//		TbAdmPerson admPerson = new TbAdmPerson();
//		admPerson.setId(-1);
//		admPerson.setsName(" ");
//		
//		listaAssociates.add(admPerson);
//		
//		if(lista.size() > 0){
//			for(TbAdmPerson person : lista){
//				listaAssociates.add(person);
//			}
//		}
//		
//	}
	
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
	
	public void preparaListaProfile(){
		
//		List<TbSysUserProfile> lista = userProfileFacade.findAll();
//		
//		TbSysUserProfile profile = new TbSysUserProfile();
//		profile.setId(-1);
//		profile.setsProfileName("");
//		
//		listaUserProfile.add(profile);
//		
//		if(lista.size() > 0){
//			for(TbSysUserProfile pro : lista){
//				listaUserProfile.add(pro);
//			}
//		}
		
	}
	
	public void save(){
		
		novoUsuario.setDtRegister(new Date());
		
//		if(!profileSelecionado.equals("-1")){
//			TbSysUserProfile profile = userProfileFacade.find(Long.parseLong(profileSelecionado));
//			novoUsuario.setTbsysuserprofile(profile);
//		}else{
//			
//			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
//					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_profile")), ""));
//			
//			listaUsuario = new ArrayList<>();
//			
//			return;
//		}
		
//		if(!associateSelecionado.equals("-1")){
//			TbAdmPerson person = personFacade.find(Long.parseLong(associateSelecionado));
//			novoUsuario.setTbAdmPerson(person);
//		}else{
//			
//			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
//					MessageFormat.format(bundle.getString("field_required"), bundle.getString("header_table_associates")), ""));
//			
//			listaUsuario = new ArrayList<>();
//			
//			return;
//		}
		
		
		if(novoUsuario.getsFullName() == null || novoUsuario.getsFullName().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_full_name")), ""));
			
			listaUsuario = new ArrayList<>();
			
		}else if(novoUsuario.getsEmail() == null || novoUsuario.getsEmail().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_email")), ""));
			
			listaUsuario = new ArrayList<>();
			
		}else if(novoUsuario.getsLogon() == null || novoUsuario.getsLogon().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_logon")), ""));
			
			listaUsuario = new ArrayList<>();
			
		}else if(novoUsuario.getsPassword() == null || novoUsuario.getsPassword().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_password")), ""));
			
			listaUsuario = new ArrayList<>();
			
		}else if(novoUsuario.getsDepartment() == null || novoUsuario.getsDepartment().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_departament")), ""));
			
			listaUsuario = new ArrayList<>();
			
		}else{
			
			try{
				userFacade.save(novoUsuario);
				listaUsuario = new ArrayList<>();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				novoUsuario = new TbSysUser();
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
			}
		}	
		
	}	
	
	public void alter(){
		
//		if(!profileSelecionado.equals("-1")){
//			TbSysUserProfile profile = userProfileFacade.find(Long.parseLong(profileSelecionado));
//			usuarioSelecionado.setTbsysuserprofile(profile);
//		}else{
//			
//			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
//					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_profile")), ""));
//			
//			listaUsuario = new ArrayList<>();
//			
//			return;
//		}
		
		
//		if(!associateSelecionado.equals("-1")){
//			TbAdmPerson person = personFacade.find(Long.parseLong(associateSelecionado));
//			usuarioSelecionado.setTbAdmPerson(person);
//		}else{
//			
//			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
//					MessageFormat.format(bundle.getString("field_required"), bundle.getString("header_table_associates")), ""));
//			
//			listaUsuario = new ArrayList<>();
//			
//			return;
//		}
		
		
		if(usuarioSelecionado.getsFullName() == null || usuarioSelecionado.getsFullName().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_full_name")), ""));
			
			listaUsuario = new ArrayList<>();
			
		}else if(usuarioSelecionado.getsEmail() == null || usuarioSelecionado.getsEmail().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_email")), ""));
			
			listaUsuario = new ArrayList<>();
			
		}else if(usuarioSelecionado.getsLogon() == null || usuarioSelecionado.getsLogon().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_logon")), ""));
			
			listaUsuario = new ArrayList<>();
			
		}else if(usuarioSelecionado.getsDepartment() == null || usuarioSelecionado.getsDepartment().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_departament")), ""));
			
			listaUsuario = new ArrayList<>();
			
		}else{
		
			try{
				
				if(!novoPassword.isEmpty()){
					usuarioSelecionado.setsPassword(novoPassword);
				}
				
				userFacade.alter(usuarioSelecionado);
				listaUsuario = new ArrayList<>();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
			}
		}
	}
	
	public void delete(){
		
		try{
			userFacade.save(usuarioSelecionado);
			listaUsuario = new ArrayList<>();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("delete_success"), ""));
			
		}catch(Exception e){
			
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("delete_error"), ""));			
		}
	}
	
//	public void preparaAtributos(){
//		
//		if(usuarioSelecionado.getTbAdmPerson() != null){
//			associateSelecionado = String.valueOf(usuarioSelecionado.getTbAdmPerson().getId());
//		}
//		
//		if(usuarioSelecionado.getTbsysuserprofile() != null){
//			profileSelecionado = String.valueOf(usuarioSelecionado.getTbsysuserprofile().getId());
//		}
					 
//	}
	
	/**
	 * Getters and Setters
	 */

	public List<TbSysUser> getListaUsuario() {
		
		if(listaUsuario.size() == 0){
			preparaListaUsuario();
		}
		
		return listaUsuario;
	}

	public void setListaUsuario(List<TbSysUser> listaUsuario) {
		this.listaUsuario = listaUsuario;
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

//	public List<TbAdmPerson> getListaAssociates() {
//		
//		if(listaAssociates.size() == 0){
//			preparaListaAssociate();
//		}
//		
//		return listaAssociates;
//	}
//
//	public void setListaAssociates(List<TbAdmPerson> listaAssociates) {
//		this.listaAssociates = listaAssociates;
//	}

	public List<TbAdmCategory> getListaCategory() {
		
		if(listaCategory.size() == 0){
			preparaListaCategory();
		}
		
		return listaCategory;
	}

	public void setListaCategory(List<TbAdmCategory> listaCategory) {
		this.listaCategory = listaCategory;
	}

//	public List<TbSysUserProfile> getListaUserProfile() {
//		
//		if(listaUserProfile.size() == 0){
//			preparaListaProfile();
//		}
//		
//		return listaUserProfile;
//	}
//
//	public void setListaUserProfile(List<TbSysUserProfile> listaUserProfile) {
//		this.listaUserProfile = listaUserProfile;
//	}


	public String getProfileSelecionado() {
		
		if(usuarioSelecionado.getId() > 0){
//			preparaAtributos();
		}
		
		return profileSelecionado;
	}


	public void setProfileSelecionado(String profileSelecionado) {
		this.profileSelecionado = profileSelecionado;
	}


	public String getAssociateSelecionado() {
		
		if(usuarioSelecionado.getId() > 0){
//			preparaAtributos();
		}
		
		return associateSelecionado;
	}


	public void setAssociateSelecionado(String associateSelecionado) {
		this.associateSelecionado = associateSelecionado;
	}


	public String getNovoPassword() {
		return novoPassword;
	}


	public void setNovoPassword(String novoPassword) {
		this.novoPassword = novoPassword;
	}
	
	
	
}
