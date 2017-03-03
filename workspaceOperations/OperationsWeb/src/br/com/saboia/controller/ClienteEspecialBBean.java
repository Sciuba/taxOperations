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

import br.com.saboia.entity.TbAdmClienteEspecial;
import br.com.saboia.facade.ClienteEspecialFacade;

@ManagedBean(name="clienteBBean")
@ViewScoped
public class ClienteEspecialBBean {
	
	@EJB
	private ClienteEspecialFacade clienteEspecialFacade;
	
	private List<TbAdmClienteEspecial> listaClient;
	
	private TbAdmClienteEspecial novoClient;
	
	private TbAdmClienteEspecial clienteSelecionado;
	
	private ResourceBundle bundle;
	

	@PostConstruct
	public void init(){
		listaClient = new ArrayList<>();
		novoClient = new TbAdmClienteEspecial();
		clienteSelecionado = new TbAdmClienteEspecial();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
	}
	
	public void preparaLista(){
		listaClient = clienteEspecialFacade.findAll();		
	}
	
	
	public void save(){
		
		if(novoClient.getSRazaoSocial() == null || novoClient.getSRazaoSocial().isEmpty()){
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")), ""));
			
			listaClient = new ArrayList<>();
			
			return;
		}
		
		if(novoClient.getsCNPJ() == null || novoClient.getsCNPJ().isEmpty()){
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_cnpj")), ""));
			
			listaClient = new ArrayList<>();
			
			return;
		}
		
		try{
			
			clienteEspecialFacade.save(novoClient);
			
			listaClient = new ArrayList<>();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
			
			novoClient = new TbAdmClienteEspecial();
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
			
			listaClient = new ArrayList<>();
			
			return;
		}
		
	}

	
	public void alter(){
		
		if(clienteSelecionado.getSRazaoSocial() == null || clienteSelecionado.getSRazaoSocial().isEmpty()){
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")), ""));
			
			listaClient = new ArrayList<>();
			
			return;
		}
		
		if(clienteSelecionado.getsCNPJ() == null || clienteSelecionado.getsCNPJ().isEmpty()){
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_cnpj")), ""));
			
			listaClient = new ArrayList<>();
			
			return;
		}
		
		try{
			
			clienteEspecialFacade.alter(clienteSelecionado);
			listaClient = new ArrayList<>();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("save_error"), ""));
			
			listaClient = new ArrayList<>();
			
			return;
		}
		
	}
	
	public void delete(){
		
		try{
			clienteEspecialFacade.delete(clienteSelecionado);
			listaClient = new ArrayList<>();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
				bundle.getString("delete_success"), ""));
		
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("delete_error"), ""));
			
			return;
		}
		
	}

	public List<TbAdmClienteEspecial> getListaClient() {
		
		if(listaClient.size() == 0){
			preparaLista();
		}
		
		return listaClient;
	}

	public void setListaClient(List<TbAdmClienteEspecial> listaClient) {
		this.listaClient = listaClient;
	}

	public TbAdmClienteEspecial getNovoClient() {
		return novoClient;
	}

	public void setNovoClient(TbAdmClienteEspecial novoClient) {
		this.novoClient = novoClient;
	}

	public TbAdmClienteEspecial getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(TbAdmClienteEspecial clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	
	
	
}
