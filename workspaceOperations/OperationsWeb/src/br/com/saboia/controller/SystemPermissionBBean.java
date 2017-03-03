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

import br.com.saboia.entity.TbSysModule;
import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.facade.impl.ModuleFacadeImpl;
import br.com.saboia.facade.impl.SystemPermissionFacadeImpl;

@ManagedBean(name = "permissionBBean")
@ViewScoped
public class SystemPermissionBBean {

	@EJB
	private SystemPermissionFacadeImpl permissionFacade;
	
	@EJB
	private ModuleFacadeImpl moduleFacade;
	
	
	private List<TbSysSystemPermission> listaPermissions;
	
	private List<TbSysModule> listaModules;
	
	private TbSysSystemPermission novoPermission;
	
	private TbSysSystemPermission permissionSelecionado;
	
	private ResourceBundle bundle;
	
	private String moduleSelecionado; 
	
	
	@PostConstruct
	public void init(){
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
		
		listaPermissions = new ArrayList<>();
		novoPermission = new TbSysSystemPermission();
		permissionSelecionado = new TbSysSystemPermission();
		
	}
	
	public void prepararLista(){
		
		listaPermissions = permissionFacade.findOrder();
		
	}
	
	public void preparalistaModule(){
		listaModules  = moduleFacade.findOrder();
	}
	
	public void save(){
		
		if(!moduleSelecionado.equals("-1")){
			novoPermission.setTbSysModule(moduleFacade.findOneResult(Long.valueOf(moduleSelecionado)));
		}
		
		if(novoPermission.getsKey() == null || novoPermission.getsKey().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_skey")),""));
			
			listaPermissions = new ArrayList<>();
			
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
			return;
			
		}
		
		if(novoPermission.getsKeyDisplayName() == null || novoPermission.getsKeyDisplayName().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_display_name")),""));
			
			listaPermissions = new ArrayList<>();
			
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
			return;
			
		}
		
		try{
			
			permissionFacade.save(novoPermission);
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));

			listaPermissions = new ArrayList<>();
			
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
		}catch (Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_error"), ""));

			listaPermissions = new ArrayList<>();
			
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
		}
		
	}
	
	public void update(){
		
		if(!moduleSelecionado.equals("-1")){
			permissionSelecionado.setTbSysModule(moduleFacade.findOneResult(Long.valueOf(moduleSelecionado)));
		}
		
		if(permissionSelecionado.getsKey() == null || permissionSelecionado.getsKey().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_skey")),""));
			
			listaPermissions = new ArrayList<>();
			
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
			return;
			
		}
		
		if(permissionSelecionado.getsKeyDisplayName() == null || permissionSelecionado.getsKeyDisplayName().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_display_name")),""));
			
			listaPermissions = new ArrayList<>();
			
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
			return;
			
		}
		
		try{
			
			permissionFacade.update(permissionSelecionado);
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));

			listaPermissions = new ArrayList<>();
			
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
		}catch (Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_error"), ""));

			listaPermissions = new ArrayList<>();
			
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
		}
		
	}
	
	public void delete(){
		
		try{
			
			permissionFacade.delete(permissionSelecionado);
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("delete_success"), ""));
			
			listaPermissions = new ArrayList<>();
			
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("delete_error"), ""));
			
			listaPermissions = new ArrayList<>();
			
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
		}
		
	}

	
	/**
	 * Getters and Setters
	 * @return
	 */
	
	public List<TbSysSystemPermission> getListaPermissions() {
		return listaPermissions;
	}

	public void setListaPermissions(List<TbSysSystemPermission> listaPermissions) {
		this.listaPermissions = listaPermissions;
	}

	public List<TbSysModule> getListaModules() {
		return listaModules;
	}

	public void setListaModules(List<TbSysModule> listaModules) {
		this.listaModules = listaModules;
	}

	public TbSysSystemPermission getNovoPermission() {
		return novoPermission;
	}

	public void setNovoPermission(TbSysSystemPermission novoPermission) {
		this.novoPermission = novoPermission;
	}

	public TbSysSystemPermission getPermissionSelecionado() {
		return permissionSelecionado;
	}

	public void setPermissionSelecionado(TbSysSystemPermission permissionSelecionado) {
		this.permissionSelecionado = permissionSelecionado;
	}

	public String getModuleSelecionado() {
		return moduleSelecionado;
	}

	public void setModuleSelecionado(String moduleSelecionado) {
		this.moduleSelecionado = moduleSelecionado;
	}
	
	
	
	
}
