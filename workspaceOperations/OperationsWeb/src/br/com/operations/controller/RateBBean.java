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

import br.com.operations.entity.TbAdmDestination;
import br.com.operations.entity.TbAdmRate;
import br.com.operations.facade.DestinationFacade;
import br.com.operations.facade.RateFacade;

@ManagedBean(name="rateBBean")
@ViewScoped
public class RateBBean {
	
	@EJB
	private RateFacade rateFacade;
	
	@EJB
	private DestinationFacade destinationFacade;
	
	private List<TbAdmDestination> listaDestination;
	
	private TbAdmRate rate;
	
	private ResourceBundle bundle;
	
	private boolean enable;
	
	private String destinationSelecionado;
	
	
	/**
	 * metodos
	 */
	
	@PostConstruct
	private void init(){
		listaDestination = new ArrayList<>();
		
		rate = new TbAdmRate();
		
		try{
			rate = rateFacade.findAll().get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.operations.bundle.messages", Locale.getDefault());
		
	}
	
	public void preparaListaDestination(){
		listaDestination = destinationFacade.findOrderByCode();
	}
	
	
	public void save(){
		
		if(!destinationSelecionado.equals("-1")){
			TbAdmDestination destination = destinationFacade.find(Long.parseLong(destinationSelecionado));
			rate.setTbAdmDestination(destination);
		}
		
		if((rate.getrCofins() < 0) || (rate.getrPis() < 0)){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Aliquots can not have negative values!", ""));
			
			return;
			
		}
		
		if((rate.getrCofins() > 100) || (rate.getrPis() > 100)){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Aliquots can not be greater than 100!", ""));
			
			return;
		}
		
		try{
			rateFacade.save(rate);
			enable = false;
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
		}
		
	}
		
	
	public void alter(){
		
		if(!destinationSelecionado.equals("-1")){
			TbAdmDestination destination = destinationFacade.find(Long.parseLong(destinationSelecionado));
			rate.setTbAdmDestination(destination);
		}else{
			rate.setTbAdmDestination(null);
		}
		
		if((rate.getrCofins() < 0) || (rate.getrPis() < 0)){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Aliquots can not have negative values!", ""));
			
			return;
			
		}
		
		if((rate.getrCofins() > 100) || (rate.getrPis() > 100)){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Aliquots can not be greater than 100!", ""));
			
			return;
		}
		
		
		try{
			rateFacade.alter(rate);
			enable = false;
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
		}
	}

	
	public void update(){
		
		if(rate.getTbAdmDestination() != null){
			destinationSelecionado = String.valueOf(rate.getTbAdmDestination().getId());
		}else{
			destinationSelecionado = "-1";
		}
		
		enable = true;
	}
	
	public void cancel(){
		enable = false;
	}
	
	/**
	 * getters and setters
	 * @return
	 */
	
	public List<TbAdmDestination> getListaDestination() {
		
		if(listaDestination.size() == 0){
			preparaListaDestination();
		}
		
		return listaDestination;
	}

	public void setListaDestination(List<TbAdmDestination> listaDestination) {
		this.listaDestination = listaDestination;
	}

	public TbAdmRate getRate() {
		return rate;
	}

	public void setRate(TbAdmRate rate) {
		this.rate = rate;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getDestinationSelecionado() {
		return destinationSelecionado;
	}

	public void setDestinationSelecionado(String destinationSelecionado) {
		this.destinationSelecionado = destinationSelecionado;
	}
	
	

}
