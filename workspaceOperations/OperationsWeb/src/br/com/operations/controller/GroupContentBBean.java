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

import br.com.operations.entity.TbAdmGroupOfContent;
import br.com.operations.facade.GroupOfContentFacade;

@ManagedBean(name="groupBBean")
@ViewScoped
public class GroupContentBBean {
	
	@EJB
	private GroupOfContentFacade groupOfContentFacade;
	
	private List<TbAdmGroupOfContent> listaGroup;
	
	private TbAdmGroupOfContent novoGrupo;
	
	private TbAdmGroupOfContent grupoSelecionado;
	
	private ResourceBundle bundle;
	

	@PostConstruct
	public void init(){
		listaGroup = new ArrayList<>();
		novoGrupo = new TbAdmGroupOfContent();
		grupoSelecionado = new TbAdmGroupOfContent();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.operations.bundle.messages", Locale.getDefault());
	}
	
	public void preparaLista(){
		listaGroup = groupOfContentFacade.findAll();		
	}
	
	
	public void save(){
		
		if(novoGrupo.getSDescription() == null || novoGrupo.getSDescription().isEmpty()){
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")), ""));
			
			listaGroup = new ArrayList<>();
			
			return;
		}
		
		try{
			
			groupOfContentFacade.save(novoGrupo);
			
			listaGroup = new ArrayList<>();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
			
			novoGrupo = new TbAdmGroupOfContent();
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
			
			listaGroup = new ArrayList<>();
			
			return;
		}
		
	}

	
	public void alter(){
		
		if(grupoSelecionado.getSDescription() == null || grupoSelecionado.getSDescription().isEmpty()){
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")), ""));
			
			listaGroup = new ArrayList<>();
			
			return;
		}
		
		try{
			
			groupOfContentFacade.alter(grupoSelecionado);
			listaGroup = new ArrayList<>();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
			
			listaGroup = new ArrayList<>();
			
			return;
		}
		
	}
	
	public void delete(){
		
		try{
			groupOfContentFacade.delete(grupoSelecionado);
			listaGroup = new ArrayList<>();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
				bundle.getString("delete_success"), ""));
		
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("delete_error"), ""));
			
			return;
		}
		
	}

	public List<TbAdmGroupOfContent> getListaGroup() {
		
		if(listaGroup.size() == 0){
			preparaLista();
		}
		
		return listaGroup;
	}

	public void setListaGroup(List<TbAdmGroupOfContent> listaGroup) {
		this.listaGroup = listaGroup;
	}

	public TbAdmGroupOfContent getNovoGrupo() {
		return novoGrupo;
	}

	public void setNovoGrupo(TbAdmGroupOfContent novoGrupo) {
		this.novoGrupo = novoGrupo;
	}

	public TbAdmGroupOfContent getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(TbAdmGroupOfContent grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}
	
	
}
