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
import javax.faces.model.SelectItem;

import br.com.saboia.entity.TbAdmGroupOfContent;
import br.com.saboia.entity.TbAdmMarketSegment;
import br.com.saboia.facade.GroupOfContentFacade;
import br.com.saboia.facade.impl.MarketSegmentFacadeImpl;

@ManagedBean(name="marketBBean")
@ViewScoped
public class MarketSegmentBBean {
	
	@EJB
	private MarketSegmentFacadeImpl marketSegmentFacadeImpl;
	
	@EJB
	private GroupOfContentFacade groupOfContentFacade;
	
	private List<TbAdmMarketSegment> listaMarket;
	
	private TbAdmMarketSegment segmentSelecionado;
	
	private TbAdmMarketSegment novoSegment;
	
	private ResourceBundle bundle;
	
	private List<SelectItem> listaGroupOfContents;
	
	private String groupSelecionado;
	
	@PostConstruct
	public void init(){
		
		listaMarket = new ArrayList<TbAdmMarketSegment>();
		listaGroupOfContents = new ArrayList<>();
		segmentSelecionado = new TbAdmMarketSegment();
		novoSegment = new TbAdmMarketSegment();
				
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
	}

	
	public void preparaLista(){
		listaMarket = marketSegmentFacadeImpl.findAll();
	}
	
	
	public void save(){
		
		
		if(groupSelecionado == null || groupSelecionado.equals("-1") || groupSelecionado.isEmpty()){
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_group")), ""));
			
			listaMarket = new ArrayList<>();
			groupSelecionado = "";
			return;
		}else{
			novoSegment.setTbAdmGroupOfContent(groupOfContentFacade.find(Long.valueOf(groupSelecionado)));
		}
		
		
		if(novoSegment.getsDescription() == null || novoSegment.getsDescription().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")), ""));
			
			listaMarket = new ArrayList<>();
			groupSelecionado = "";
			return;
		}
		
		try{
			
			marketSegmentFacadeImpl.save(novoSegment);
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
			
			listaMarket = new ArrayList<>();
			novoSegment = new TbAdmMarketSegment();
			segmentSelecionado = new TbAdmMarketSegment();
			groupSelecionado = "";
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
			
			listaMarket = new ArrayList<>();
			groupSelecionado = "";
			return;
		}
	}
	
	
	public void alter(){
		
		
		if(groupSelecionado == null || groupSelecionado.equals("-1") || groupSelecionado.isEmpty()){
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_group")), ""));
			
			listaMarket = new ArrayList<>();
			groupSelecionado = "";
			return;
		}else{
			segmentSelecionado.setTbAdmGroupOfContent(groupOfContentFacade.find(Long.valueOf(groupSelecionado)));
		}
		
		
		if(segmentSelecionado.getsDescription() == null || segmentSelecionado.getsDescription().isEmpty()){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")), ""));
			
			listaMarket = new ArrayList<>();
			groupSelecionado = "";
			return;
		}
		
		try{
			
			marketSegmentFacadeImpl.alter(segmentSelecionado);
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
			
			listaMarket = new ArrayList<>();
			novoSegment = new TbAdmMarketSegment();
			segmentSelecionado = new TbAdmMarketSegment();
			groupSelecionado = "";
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
			
			listaMarket = new ArrayList<>();
			groupSelecionado = "";
			return;
		}
		
	}
	
	
	public void delete(){
		
		try{
			
			marketSegmentFacadeImpl.delete(segmentSelecionado);			
			
			listaMarket = new ArrayList<>();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("delete_success"), ""));
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("delete_error"), ""));
			
			return;
		}
		
	}
	
	public List<TbAdmMarketSegment> getListaMarket() {
		
		if(listaMarket.size() == 0){
			preparaLista();
		}
		
		return listaMarket;
	}

	public void setListaMarket(List<TbAdmMarketSegment> listaMarket) {
		this.listaMarket = listaMarket;
	}

	public TbAdmMarketSegment getSegmentSelecionado() {
		return segmentSelecionado;
	}

	public void setSegmentSelecionado(TbAdmMarketSegment segmentSelecionado) {
		this.segmentSelecionado = segmentSelecionado;
	}

	public TbAdmMarketSegment getNovoSegment() {
		return novoSegment;
	}

	public void setNovoSegment(TbAdmMarketSegment novoSegment) {
		this.novoSegment = novoSegment;
	}


	public List<SelectItem> getListaGroupOfContents() {
		
		if(listaGroupOfContents.size() == 0){
			for(TbAdmGroupOfContent group : groupOfContentFacade.findAll()){
				listaGroupOfContents.add(new SelectItem(group.getId(), group.getSDescription()));
			}
		}
		
		return listaGroupOfContents;
	}


	public void setListaGroupOfContents(
			List<SelectItem> listaGroupOfContents) {
		this.listaGroupOfContents = listaGroupOfContents;
	}


	public String getGroupSelecionado() {
		
		if(segmentSelecionado != null && segmentSelecionado.getTbAdmGroupOfContent() != null){
			groupSelecionado = String.valueOf(segmentSelecionado.getTbAdmGroupOfContent().getId());
		}
		
		return groupSelecionado;
	}


	public void setGroupSelecionado(String groupSelecionado) {
		this.groupSelecionado = groupSelecionado;
	}

	
	
}
