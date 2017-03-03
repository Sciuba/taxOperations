package br.com.operations.controller;

import java.sql.SQLIntegrityConstraintViolationException;
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
import javax.persistence.PersistenceException;

import br.com.operations.entity.TbAdmDiscClass;
import br.com.operations.facade.DiscClassFacade;


@ManagedBean(name="discClassBBean")
@ViewScoped
public class DiscClassBBean {

	@EJB
	private DiscClassFacade discClassFacade;
	
	private TbAdmDiscClass novoDiscClass;
	
	private TbAdmDiscClass discClassSelecionado;
	
	private List<TbAdmDiscClass> listaDiscClass;
	
	private ResourceBundle bundle;
	
	
	/**
	 * Metodos
	 */
	
	@PostConstruct
	public void init(){
		novoDiscClass = new TbAdmDiscClass();
		discClassSelecionado = new TbAdmDiscClass();
		listaDiscClass = new ArrayList<>();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.operations.bundle.messages", Locale.getDefault());
	}


	public void preparaLista(){
		listaDiscClass = discClassFacade.findAll();
	}
	
	
	public void save(){
			try{
				
				discClassFacade.save(novoDiscClass);
				listaDiscClass = new ArrayList<>();
				
				novoDiscClass = new TbAdmDiscClass();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
			}
	}
	
	public void alter(){
		try{
			
			discClassFacade.alter(discClassSelecionado);
			listaDiscClass = new ArrayList<>();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
		}
	}
	
	public void delete() {
		
		if(discClassSelecionado.getTbAdmMaterials() == null || discClassSelecionado.getTbAdmMaterials().size() == 0){
			
			try{
				
				discClassFacade.delete(discClassSelecionado);
				listaDiscClass = new ArrayList<>();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("delete_success"), ""));
				
			}catch(Exception e){
				
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("delete_error"), ""));
			}
			
		}else{
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("delete_error_fk"), ""));
		}
	}
	
	
	
	
	/**
	 * Getters and Setters
	 */
	
	
	public TbAdmDiscClass getNovoDiscClass() {
		return novoDiscClass;
	}


	public void setNovoDiscClass(TbAdmDiscClass novoDiscClass) {
		this.novoDiscClass = novoDiscClass;
	}


	public TbAdmDiscClass getDiscClassSelecionado() {
		return discClassSelecionado;
	}


	public void setDiscClassSelecionado(TbAdmDiscClass discClassSelecionado) {
		this.discClassSelecionado = discClassSelecionado;
	}


	public List<TbAdmDiscClass> getListaDiscClass() {
		
		if(listaDiscClass.size() == 0){
			preparaLista();
		}
		
		return listaDiscClass;
	}


	public void setListaDiscClass(List<TbAdmDiscClass> listaDiscClass) {
		this.listaDiscClass = listaDiscClass;
	}
	
	
	
	
	
}
