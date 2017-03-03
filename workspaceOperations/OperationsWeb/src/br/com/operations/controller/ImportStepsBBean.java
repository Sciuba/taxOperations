package br.com.operations.controller;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.operations.facade.MaterialImportTempFacade;

@ManagedBean(name = "stepBBean")
@RequestScoped
public class ImportStepsBBean {

	@EJB
	private MaterialImportTempFacade importTempFacade;

	private boolean ok;
	
	public String validaELocalizaNcmImportados() {

		try {

			importTempFacade.validaLocalizaNcmImportado();

			return "success";

		} catch (Exception e) {
			e.printStackTrace();

			Locale.setDefault(new Locale((String) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("language"), (String) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("country")));

			ResourceBundle bundle = ResourceBundle.getBundle(
					"br.com.operations.bundle.messages", Locale.getDefault());

			FacesContext.getCurrentInstance().addMessage(
					"msg",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle
							.getString("msg_error_import"), ""));

			return "erro";

		}
	}

	public String validaELocalizaMateriaisImportados() {

		try {

			importTempFacade.validaLocalizaMaterialImportado();
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sucesso", true);
			
			return "success";
			
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sucesso", false);
			
			return "success";
			
		}

	}
	
	public void checaProcesso(){
		
		try{
			ok = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sucesso");
			
			if(ok){
				Locale.setDefault(new Locale((String) FacesContext
						.getCurrentInstance().getExternalContext().getSessionMap()
						.get("language"), (String) FacesContext
						.getCurrentInstance().getExternalContext().getSessionMap()
						.get("country")));

				ResourceBundle bundle = ResourceBundle.getBundle(
						"br.com.operations.bundle.messages", Locale.getDefault());
				
						FacesContext.getCurrentInstance().addMessage(
								"msg",
								new FacesMessage(FacesMessage.SEVERITY_INFO, bundle
										.getString("msg_succes_import"), ""));
						
			}else{
				Locale.setDefault(new Locale((String) FacesContext
						.getCurrentInstance().getExternalContext().getSessionMap()
						.get("language"), (String) FacesContext
						.getCurrentInstance().getExternalContext().getSessionMap()
						.get("country")));

				ResourceBundle bundle = ResourceBundle.getBundle(
						"br.com.operations.bundle.messages", Locale.getDefault());
				
				FacesContext.getCurrentInstance().addMessage(
						"msg",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle
								.getString("msg_error_import"), ""));

			}
			
		}catch(Exception e){
			e.printStackTrace();
			
			ok = false;
			
			Locale.setDefault(new Locale((String) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("language"), (String) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("country")));

			ResourceBundle bundle = ResourceBundle.getBundle(
					"br.com.operations.bundle.messages", Locale.getDefault());

			FacesContext.getCurrentInstance().addMessage(
					"msg",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle
							.getString("msg_error_import"), ""));
		}
		
	}

}
