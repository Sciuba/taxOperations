package br.com.operations.controller;

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

import br.com.operations.entity.TbAdmProductType;
import br.com.operations.entity.TbSysSystemPermission;
import br.com.operations.entity.TbSysUser;
import br.com.operations.facade.ProductTypeFacade;
import br.com.operations.facade.impl.SystemPermissionFacadeImpl;

@ManagedBean(name="productBBean")
@ViewScoped
public class ProductTypeBBean {

	@EJB
	private ProductTypeFacade typeFacade;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	private List<TbAdmProductType> listaProdutos;
	
	private TbAdmProductType produtoSelecionado;
		
	private ResourceBundle bundle;
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "productType";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	private TbSysUser userLogged;
	
	/**
	 * Metodos
	 */
	
	@PostConstruct
	public void init(){
		
		listaProdutos = new ArrayList<>();
		
		produtoSelecionado = new TbAdmProductType();
		
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
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
			
		}
		
	}
	
	
	public void preparaLista(){
		listaProdutos = typeFacade.findAll();
	}
	
	
	public void alter(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			try{
				
				typeFacade.alter(produtoSelecionado);
				listaProdutos = new ArrayList<>();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
			}catch(Exception e){
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
	
	
	/**
	 * Getters and Setters
	 */

	public List<TbAdmProductType> getListaProdutos() {
		
		if(listaProdutos.size() == 0){
			preparaLista();
		}
		
		return listaProdutos;
	}



	public void setListaProdutos(List<TbAdmProductType> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}


	public TbAdmProductType getProdutoSelecionado() {
		return produtoSelecionado;
	}



	public void setProdutoSelecionado(TbAdmProductType produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
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
