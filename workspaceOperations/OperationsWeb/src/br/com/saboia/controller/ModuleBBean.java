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

import br.com.saboia.entity.TbSysModule;
import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.facade.impl.ModuleFacadeImpl;
import br.com.saboia.facade.impl.SystemPermissionFacadeImpl;

@ManagedBean(name="moduleBBean")
@ViewScoped
public class ModuleBBean {
	
	
	@EJB
	private ModuleFacadeImpl moduleFacade;
	
	@EJB
	private SystemPermissionFacadeImpl permissionFacadeI;
	
	
	private TbSysModule novoModule;
	
	private TbSysModule moduleSelecionado;
	
	private TbSysSystemPermission novoPermission;
	
	private TbSysSystemPermission permissionSelecionado;
	
	private List<TbSysModule> listaModule;
	
	private List<TbSysSystemPermission> listaPermission;
	
	private ResourceBundle bundle;
	
	/**
	 * Metodos
	 */

	@PostConstruct
	public void init(){
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
		
		novoModule = new TbSysModule();
		moduleSelecionado = new TbSysModule();
		listaModule = new ArrayList<>();
		novoPermission = new TbSysSystemPermission();
		permissionSelecionado = new TbSysSystemPermission();
		listaPermission = new ArrayList<>();
		
	}
	
	public void preparaLista(){
		listaModule = moduleFacade.findOrder();
	}
	
	public void salvar(){
		
		if(novoModule.getsDisplayName() == null || novoModule.getsDescription().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_display_name")),""));
			
			listaModule = new ArrayList<>();
			moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			
			return;
		}
		
		if(novoModule.getsModule() == null || novoModule.getsModule().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_module")),""));
			
			listaModule = new ArrayList<>();
			moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			
			return;
			
		}
		
		try{
			
			TbSysModule module = moduleFacade.verifyExists(novoModule.getsModule());
			
			if(module != null){
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						bundle.getString("msg_warn_module"),""));
				
				listaModule = new ArrayList<>();
				moduleSelecionado = new TbSysModule();
				novoModule = new TbSysModule();
				
				return;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		try{
			
			moduleFacade.save(novoModule);
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
			
			listaModule = new ArrayList<>();
			moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
			
			listaModule = new ArrayList<>();
			moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
		}
			
	}
		
		
	public void update(){
			
		if(moduleSelecionado.getsDisplayName() == null || moduleSelecionado.getsDescription().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_display_name")),""));
			
			listaModule = new ArrayList<>();
			moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			
			return;
		}
		
		if(moduleSelecionado.getsModule() == null || moduleSelecionado.getsModule().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_module")),""));
			
			listaModule = new ArrayList<>();
			moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			
			return;
			
		}
		
		try{
			
			moduleFacade.update(moduleSelecionado);
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
			
			listaModule = new ArrayList<>();
			moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
			
			listaModule = new ArrayList<>();
			moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			
		}
	}
	
		
	public void delete(){
		
		try{
			
			if(moduleSelecionado.getTbSysSystemPermissions() != null 
					&& moduleSelecionado.getTbSysSystemPermissions().size() > 0){
				
				for(int i = 0; i < moduleSelecionado.getTbSysSystemPermissions().size(); i++){
					
					permissionFacadeI.delete(moduleSelecionado.getTbSysSystemPermissions().get(i));
					
					moduleSelecionado.getTbSysSystemPermissions().remove(i);
					
					i--;
					
				}
				
			}
			
			
			moduleFacade.delete(moduleSelecionado);
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("delete_success"), ""));
			
			listaModule = new ArrayList<>();
			moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("delete_error"), ""));
			
			listaModule = new ArrayList<>();
			moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			
		}
		
	}
	
	
	public void savePermission(){
		
		if(novoPermission.getsKey() == null || novoPermission.getsKey().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_skey")),""));
			
			//listaModule = new ArrayList<>();
			//moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
			return;
			
		}
		
		if(novoPermission.getsKeyDisplayName() == null || novoPermission.getsKeyDisplayName().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_display_name")),""));
			
			//listaModule = new ArrayList<>();
			//moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
			return;
			
		}
		
		try{
			
			TbSysSystemPermission sysSystemPermission = permissionFacadeI.findPermissionByKeyAndModule(novoPermission.getsKey(), moduleSelecionado.getId());	
			
			if(sysSystemPermission != null){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						bundle.getString("msg_warn_permission_module"), ""));
				
				novoModule = new TbSysModule();
				novoPermission = new TbSysSystemPermission();
				permissionSelecionado = new TbSysSystemPermission();
				
				return;
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
			
			novoModule = new TbSysModule();
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
			return;
		}
		
		try{
			
			if(moduleSelecionado.getTbSysSystemPermissions() == null || moduleSelecionado.getTbSysSystemPermissions().isEmpty()){
				moduleSelecionado.setTbSysSystemPermissions(new ArrayList<TbSysSystemPermission>());
			}
			
			novoPermission.setTbSysModule(moduleSelecionado);
			
			moduleSelecionado.getTbSysSystemPermissions().add(permissionFacadeI.saveReturn(novoPermission));
									
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
			
			loadPermissions(String.valueOf(moduleSelecionado.getId()));
			
			//listaModule = new ArrayList<>();
			//moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
			//Fecho o modal da tela
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('permissionDialog').hide();");
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}catch (Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
			
			
			//listaModule = new ArrayList<>();
			//moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
			//Fecho o modal da tela
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('permissionDialog').hide();");
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}
		
	}
	
	public void deletePermission(){
		
		try{
			
			permissionFacadeI.delete(permissionSelecionado);
			
			moduleSelecionado.getTbSysSystemPermissions().remove(permissionSelecionado);
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("delete_success"), ""));
			
			//listaModule = new ArrayList<>();
			//moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("delete_error"), ""));
			
			//listaModule = new ArrayList<>();
			//moduleSelecionado = new TbSysModule();
			novoModule = new TbSysModule();
			novoPermission = new TbSysSystemPermission();
			permissionSelecionado = new TbSysSystemPermission();
			
		}
		
	}
	
	public void loadPermissions(String id){
		
		listaPermission = permissionFacadeI.findByIdModule(Long.valueOf(id));
		
	}
	
		
	/**
	 * Getters and Setters
	 * @return
	 */

	public TbSysModule getNovoModule() {
		return novoModule;
	}

	public void setNovoModule(TbSysModule novoModule) {
		this.novoModule = novoModule;
	}

	public TbSysModule getModuleSelecionado() {
		return moduleSelecionado;
	}

	public void setModuleSelecionado(TbSysModule moduleSelecionado) {
		this.moduleSelecionado = moduleSelecionado;
	}

	public List<TbSysModule> getListaModule() {
		
		if(listaModule == null || listaModule.size() == 0){
			preparaLista();
		}
		
		return listaModule;
	}

	public void setListaModule(List<TbSysModule> listaModule) {
		this.listaModule = listaModule;
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

	public List<TbSysSystemPermission> getListaPermission() {
		return listaPermission;
	}

	public void setListaPermission(List<TbSysSystemPermission> listaPermission) {
		this.listaPermission = listaPermission;
	}
		
		
}
