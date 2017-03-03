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

import br.com.operations.entity.TbAdmCategory;
import br.com.operations.facade.CategoryFacade;

@ManagedBean(name="categoryBBean")
@ViewScoped
public class CategoryBBean {
	
	@EJB
	private CategoryFacade categoryFacade;
	
	private TbAdmCategory categorySelecionado;
	
	private TbAdmCategory novoCategory;
	
	private List<TbAdmCategory> listaCategory;
	
	private ResourceBundle bundle;
	
	
	/*
	 *Métodos 
	 */
	
	@PostConstruct
	public void init(){
		categorySelecionado = new TbAdmCategory();
		novoCategory = new TbAdmCategory();
		listaCategory = new ArrayList<>();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.operations.bundle.messages", Locale.getDefault());
		
	}

	
	/**
	 * prepara a lista para exibir na tela
	 */
	public void preparaLista(){
		listaCategory = categoryFacade.findAll();
	}
	
	
	public void save(){
		
		if(novoCategory.getsAcronym() == null || novoCategory.getsAcronym().isEmpty() ){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_acronym")), ""));
			
		}else if(novoCategory.getsCategory() == null || novoCategory.getsCategory().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_category")), ""));
			
		}else if(novoCategory.getImodule() == null || novoCategory.getImodule() > 2){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_module")), ""));
			
		}else{
		
			try{
				categoryFacade.save(novoCategory);
				this.listaCategory = new ArrayList<>();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				novoCategory = new TbAdmCategory();
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
			}
			
		}
	}
	
	
	
	public void alter(){		

		if(categorySelecionado.getsAcronym() == null || categorySelecionado.getsAcronym().isEmpty() ){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_acronym")), ""));
			
		}else if(categorySelecionado.getsCategory() == null || categorySelecionado.getsCategory().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_category")), ""));
			
		}else if(categorySelecionado.getImodule() == null || categorySelecionado.getImodule() > 2){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_module")), ""));
			
		}else{
		
			try{
				categoryFacade.alter(categorySelecionado);
				this.listaCategory = new ArrayList<>();
				
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
			
			categoryFacade.delete(categorySelecionado);
			this.listaCategory = new ArrayList<>();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("delete_success"), ""));
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("delete_error"), ""));
		}
	}
	
	
	
	
	/**
	 * Getters and Setters
	 */
	
	public TbAdmCategory getCategorySelecionado() {
		return categorySelecionado;
	}


	public void setCategorySelecionado(TbAdmCategory categorySelecionado) {
		this.categorySelecionado = categorySelecionado;
	}


	public TbAdmCategory getNovoCategory() {
		return novoCategory;
	}


	public void setNovoCategory(TbAdmCategory novoCategory) {
		this.novoCategory = novoCategory;
	}


	public List<TbAdmCategory> getListaCategory() {
		
		if(this.listaCategory.size() == 0){
			preparaLista();
		}
		
		return listaCategory;
	}


	public void setListaCategory(List<TbAdmCategory> listaCategory) {
		this.listaCategory = listaCategory;
	}
	
	
	
	

}
