package br.com.operations.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

import br.com.operations.entity.TbAdmDestination;
import br.com.operations.entity.TbAdmGroupOfContent;
import br.com.operations.entity.TbAdmMaterial;
import br.com.operations.entity.TbAdmQuoteParameters;
import br.com.operations.entity.TbSysSystemPermission;
import br.com.operations.entity.TbSysUser;
import br.com.operations.entity.TbTaxQuote;
import br.com.operations.entity.TbTaxQuoteDetail;
import br.com.operations.entity.TbTaxQuoteItem;
import br.com.operations.entity.TbTaxQuoteParticipant;
import br.com.operations.facade.DestinationFacade;
import br.com.operations.facade.DollarRateFacade;
import br.com.operations.facade.GroupOfContentFacade;
import br.com.operations.facade.ManufacturerFacade;
import br.com.operations.facade.MaterialFacade;
import br.com.operations.facade.QuoteDetailFacade;
import br.com.operations.facade.QuoteDiscFacade;
import br.com.operations.facade.QuoteFacade;
import br.com.operations.facade.QuoteItemFacade;
import br.com.operations.facade.QuoteParametersFacade;
import br.com.operations.facade.QuoteParticipantFacade;
import br.com.operations.facade.UserFacade;
import br.com.operations.facade.impl.SystemPermissionFacadeImpl;
import br.com.operations.lazyDataModel.QuoteLazyDataModel;
import br.com.operations.to.ImportQuoteItemTO;
import br.com.operations.to.ImportQuoteTO;
import br.com.operations.utils.CSVRead;
import br.com.operations.utils.SendEmail;
import br.com.operations.utils.WebQuoteRead;
import br.com.operations.utils.XMLQuoteRead;

@ManagedBean(name="quoteBBean")
@ViewScoped
public class QuoteBBean {
	
	@EJB
	private QuoteFacade quoteFacade;
	
	@EJB
	private QuoteItemFacade itemFacade;
	
	@EJB
	private QuoteDetailFacade detailFacade;
	
	@EJB
	private QuoteDiscFacade discFacade;
	
	@EJB
	private GroupOfContentFacade groupFacade;
	
	@EJB
	private DestinationFacade destinationFacade;
	
	@EJB
	private QuoteParticipantFacade participantFacade;
	
	@EJB
	private MaterialFacade materialFacade;
	
	@EJB
	private ManufacturerFacade manufacturerFacade;
	
	@EJB
	private QuoteParametersFacade parametersFacade;
	
	@EJB
	private QuoteLazyDataModel lazyDataModel;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	@EJB
	private UserFacade userFacade;
	
	@EJB
	private DollarRateFacade dollarRateFacade;
	
	@EJB
	private SendEmail email;
	
	private TbTaxQuote novoQuote;
	
	private TbTaxQuote quoteSelecionado;

	private TbTaxQuoteDetail novoQuoteDetail;
	
	
	private LazyDataModel<TbTaxQuote> lazyModel;
	
	private List<TbTaxQuote> listaQuotes;
	private List<TbTaxQuote> listaQuotesAno;
	private List<TbSysUser> listaPerson;
	private List<TbAdmGroupOfContent> listaGroup;
	private List<TbAdmDestination> listaDestinations;
	
	
	private boolean checkListQuotesDeleted;
	
	
	private String personSelecionadoSalesRep;
	private String personSelecionado;
	private String quoteMenuSelecionado;
	private String groupSelecionado;
	private String destinoSelecionado;
	
	private ResourceBundle bundle;
	
	private TbSysUser userLogged;
	
	private String filterStatusSelecionado;
	private Date dataIni;
	private Date dataFim;
	private boolean search;
	
	private String tab = "0";
	
	private File file;
	
	private ImportQuoteTO quoteTO;
	
	private TabView tabView;
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do m�dulo conforme o usu�rio logado
	 */
	private final String SMODULE = "quote";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	/**
	 * metodos
	 */
	
	@PostConstruct
	public void init(){
		
		novoQuote = new TbTaxQuote();
		novoQuoteDetail = new TbTaxQuoteDetail();
		quoteSelecionado = new TbTaxQuote();
		listaQuotes = new ArrayList<>();
		listaQuotesAno = new ArrayList<>();
		listaPerson = new ArrayList<>();
		listaGroup = new ArrayList<>();
		listaDestinations = new ArrayList<>();
		personSelecionado = "";
		personSelecionadoSalesRep = "";
		quoteMenuSelecionado = "";
		groupSelecionado = "";
		destinoSelecionado = "";
		tabView  = new TabView();
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();

		permissionList = new ArrayList<>();
		
		sKey = "";
		
		checkSecurity();
		
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.operations.bundle.messages", Locale.getDefault());
		
		reset();
		
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

		lazyModel = lazyDataModel;
		
		//		listaQuotes = quoteFacade.findAll();
	}
	
	
	public void checkListaQuoteDelete(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
			try{
			
				if(checkListQuotesDeleted){
					
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deleted", true);
					
				}else{
					
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("deleted");
					
				}
				
			}catch(Exception e){
				e.printStackTrace();							
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
		
		
	}
	
	
	public void preparaListPerson(){
		
		try{
			listaPerson = userFacade.findByGrupoAndOrganizationNotManager(userLogged.getTbSysOrganization().getId(), 61L); //61 id grupo sales;
		}catch(Exception e){
			listaPerson = new ArrayList<>();
		}
		
		
		if(listaPerson == null){
			listaPerson = new ArrayList<>();
		}
		
	}
	
	public void preparaListaGroup(){
		listaGroup = groupFacade.findAll();
	}
	
	public void preparaListaDestination(){
		listaDestinations = destinationFacade.findOrderBy();
	}
	
	public void preparaListaQuoteAno(){
		listaQuotesAno = quoteFacade.simpleQuery();
		
		if(listaQuotesAno == null){
			listaQuotesAno = new ArrayList<>();
		}
		
	}
	
	
	public String redirectQuoteDetail(){
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("quote", quoteSelecionado.getId());
		
		return "details";
		
	}
	
	
	public String save(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			//TODO: Veirifciar no novo esquema de seguran�a
			//if(userLogged.getTbAdmPerson().getTbAdmCategory().getsCategory().equals("Sales Rep")){
				novoQuote.setTbSysUser(userLogged);
			//}else{
				
				try{
					novoQuote = quoteFacade.saveReturn(novoQuote);
					
					novoQuoteDetail.setTbTaxQuote(novoQuote);
					
					novoQuoteDetail = detailFacade.saveReturn(novoQuoteDetail);
					
					novoQuote.setTbTaxQuoteDetail(novoQuoteDetail);
					
					novoQuote.setmDollarRate(dollarRateFacade.simpleQuery().getrDollar().doubleValue());
					
					quoteFacade.alter(novoQuote);
					
					TbTaxQuoteParticipant participant = new TbTaxQuoteParticipant();
					participant.setTbSysUser(userLogged);
					participant.setTbTaxQuote(novoQuote);
					
					participantFacade.save(participant);
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					try{
						
						detailFacade.delete(novoQuoteDetail);
						quoteFacade.delete(novoQuote);
						
					}catch(Exception e1){
						e1.printStackTrace();
					}
					
					return "";
				}
			//}
			
			
			if(!destinoSelecionado.equals("-1")){
				
				TbAdmDestination destino = destinationFacade.find(Long.parseLong(destinoSelecionado));
				novoQuote.setTbAdmDestination(destino);
				novoQuote.setTbAdmOriginHw(destino.getTbAdmOriginHw());
				novoQuote.setTbAdmOriginSw(destino.getTbAdmOriginSw());
				novoQuote.setTbAdmOriginSv(destino.getTbAdmOriginSv());
				novoQuote.setTbAdmOriginMt(destino.getTbAdmOriginMt());
			}
			/*
			if(!groupSelecionado.equals("-1")){
				novoQuote.setTbAdmGroupOfContent(groupFacade.find(Long.parseLong(groupSelecionado)));
			}*/
			
			//TODO: idem ao de cima
			/*if(!personSelecionadoSalesRep.equals("-1") && !personSelecionadoSalesRep.isEmpty()){
				novoQuote.setTbAdmPerson(personFacade.find(Long.parseLong(personSelecionadoSalesRep)));
			}
			
			if(novoQuote.getTbAdmPerson() == null){
					
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_participant")), ""));
				
				novoQuote = new TbTaxQuote();
				
				return "";
				
			}*/
		
			
			if(quoteMenuSelecionado.equals("-1") || quoteMenuSelecionado.isEmpty()){
				novoQuote.setiQuoteVersion(1);
			}
			
			novoQuote.setDtCreation(new Date());
			
			try{
				
				if(novoQuote.getId() == 0){
					novoQuote = quoteFacade.saveReturn(novoQuote);
					
					novoQuoteDetail.setTbTaxQuote(novoQuote);
					
					try{
						detailFacade.save(novoQuoteDetail);
					}catch(Exception e){
						detailFacade.alter(novoQuoteDetail);
					}
					
				}else{
					
					novoQuoteDetail.setTbTaxQuote(novoQuote);
					
					try{
						detailFacade.save(novoQuoteDetail);
					}catch(Exception e){
						detailFacade.alter(novoQuoteDetail);
					}
					
					quoteFacade.alter(novoQuote);
					
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("quote", novoQuote.getId());
					
				}
				
				listaQuotesAno = new ArrayList<>();
				listaQuotes = new ArrayList<>();			
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				novoQuote = new TbTaxQuote();
				novoQuoteDetail = new TbTaxQuoteDetail();
				
				return "details";
				
			}catch(Exception e){
				
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				return "";
				
			}
		
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
			
			return "";
		}else{
			return "";
		}
		
	}
	
	public void alter(){
		
	}
	
	public void delete(){
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
			quoteSelecionado.setfDelete(true);
			quoteSelecionado.setDtDelete(new Date());
			
			try{
				
				quoteFacade.alter(quoteSelecionado);
				
	
				listaQuotesAno = new ArrayList<>();
				listaQuotes = new ArrayList<>();			
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("delete_success"), ""));
				
			}catch(Exception e){
				
				listaQuotesAno = new ArrayList<>();
				listaQuotes = new ArrayList<>();			
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("delete_error"), ""));
				
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
			
		}			
			/*try{
				
				if(quoteSelecionado.getTbTaxQuoteItems() != null && quoteSelecionado.getTbTaxQuoteItems().size() > 0){
					itemFacade.deleteAll(quoteSelecionado.getId());
				}
				
				detailFacade.deleteAll(quoteSelecionado.getId());
							
				if(quoteSelecionado.getTbTaxQuoteParticipants() != null && quoteSelecionado.getTbTaxQuoteParticipants().size() > 0){
					for(int i = 0; i < quoteSelecionado.getTbTaxQuoteParticipants().size(); i++){
						participantFacade.delete(quoteSelecionado.getTbTaxQuoteParticipants().get(i));
						
						quoteSelecionado.getTbTaxQuoteParticipants().remove(i);
						
						i--;
					}
				}
				
				quoteFacade.delete(quoteSelecionado);
				
				listaQuotesAno = new ArrayList<>();
				listaQuotes = new ArrayList<>();			
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("delete_success"), ""));
				
			}catch(Exception e){
				
				listaQuotesAno = new ArrayList<>();
				listaQuotes = new ArrayList<>();			
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("delete_error"), ""));
				
			}
		*/	
	}	
	
	public void insereNumeroQuote(){
		
		if(!quoteMenuSelecionado.equals("-1")){
			
			TbTaxQuote quoteAno = quoteFacade.find(Long.parseLong(quoteMenuSelecionado));
			novoQuote.setiQuoteVersion(quoteAno.getiQuoteVersion() + 1);
			novoQuote.setsQuoteNumber(quoteAno.getsQuoteNumber());	
		}
		
	}	
	
	public String onFlowProcess(FlowEvent event) {

		return event.getNewStep();
    }
	
	
	public void leasing(){
		if(novoQuote.isfLeasing()){
			novoQuote.setfContrest(true);
			novoQuote.setfPartner(true);
		}else{
			novoQuote.setfContrest(false);
		}
	}
	
	
	public void filterQuote(){
		
		try{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("filtersQuote");
		}catch(Exception e){
			
		}
		
		if((dataIni == null || dataFim == null) && filterStatusSelecionado.equals("-1") && !checkListQuotesDeleted){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					bundle.getString("msg_error_quote_search_fileds"), ""));
			
			return;
			
		}else if((dataIni != null && dataFim != null) && dataIni.after(dataFim)){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					bundle.getString("msg_erro_quote_search_data"), ""));
			
			return;
			
		}else{
			
			search = true;
			
			Map<String, Object> params = new HashMap<>();
			
			if(!filterStatusSelecionado.equals("-1")){
				params.put("status", filterStatusSelecionado);
			}
			
			if(dataIni != null && dataFim != null){
				params.put("dataIni", dataIni);
				params.put("dataFim", dataFim);
			}
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("filtersQuote", params);
			
		}		
		
	}
	
	public void reset(){
		
		dataIni = null;
		dataFim = null;
		
		filterStatusSelecionado = "-1";
		
		listaQuotes = new ArrayList<>();
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cleanFilter", true);
		
		try{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("filtersQuote");
		}catch(Exception e){
			
		}
		
		search = false;
	}
	
	
	public void fileUpload(FileUploadEvent event){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			UploadedFile upFile = event.getFile();
			
			InputStream in;
			
			try {
				
				in = event.getFile().getInputstream();
			
				String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("Active/ImportFiles/importQuote");
				
				System.out.println(caminho);
				
				file = new File(caminho + File.separator + upFile.getFileName());
				
				System.out.println(caminho + File.separator + upFile.getFileName());
				
				OutputStream out = new FileOutputStream(file);
		        
		        int read = 0;
		        byte[] bytes = new byte[1024];
		      
		        while ((read = in.read(bytes)) != -1) {
		            out.write(bytes, 0, read);
		        }
		      
		        in.close();
		        out.flush();
		        out.close();
			
		        if(upFile.getFileName().toLowerCase().contains("csv")){
		        	CSVRead.run();
		        }else if(upFile.getFileName().toLowerCase().contains("html") || upFile.getFileName().toLowerCase().contains("htm")){
		        	
//		        	FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
//		    				"The WebQuote the import function is not active in the system.", ""));
//		        	
//		        	upFile = null;
//		        	
//		        	return;
		        	
		        	WebQuoteRead.run();
		        }else if(upFile.getFileName().toLowerCase().contains("xml")){
		        	XMLQuoteRead.run();
		        }else if(upFile.getFileName().toLowerCase().contains("txt")){
		        	
		        	FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
		    				"The WebQuote the import function is not active in the system.", ""));
		        	
		        	upFile = null;
		        	
		        	return;
		        	
		        }
		       
		        importProcess();
		       
		        String imported = FacesContext.getCurrentInstance().getExternalContext().getRealPath("Active/ImportFiles/importedQuote");
		        
		        File fileRenamed = new File(imported + File.separator + upFile.getFileName()+"_"+novoQuote.getId());
		        
		        file.renameTo(fileRenamed);
		        
		        file.delete();
		        
		        
			} catch (IOException e) {
				e.printStackTrace();
				
				
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
						
		}
        
	}
	
	
	public void importProcess(){
		
		boolean sendMail = false;
		
		double somaSupportNetPrice = 0D;
		String sOrdem = "";
		
		quoteTO = (ImportQuoteTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("importQuote");
		
		novoQuote = new TbTaxQuote();
		novoQuoteDetail = new TbTaxQuoteDetail();
		
		novoQuote.setsQuoteNumber(quoteTO.getQuoteName());
		novoQuote.setsQuoteName(quoteTO.getQuoteName());
		
		if(quoteTO.getsCustomer() != null){
			
			novoQuote.setsCustomer(quoteTO.getsCustomer());
			
			if(quoteTO.getsCustomer().isEmpty()){
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Customer was not found in the imported file.", ""));
			}
		}
				
		
		novoQuote.setmDollarRate(dollarRateFacade.simpleQuery().getrDollar().doubleValue());
		
		GregorianCalendar gc = new GregorianCalendar();
		
		gc.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		
		novoQuote.setDtCreation(gc.getTime());
		
		try{
			novoQuote = quoteFacade.saveReturn(novoQuote);
			
		}catch(Exception e){
			
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("msg_error_imported_quote"), ""));
			
			return;
			
		}
		
		//TODO:VErificar no novo esquema de seguran�a
		//if(userLogged.getTbAdmPerson().getTbAdmCategory().getsCategory().equals("Sales Rep")){
			novoQuote.setTbSysUser(userLogged);
			
		/*}else{
			
			TbTaxQuoteParticipant participant = new TbTaxQuoteParticipant();
			participant.setTbAdmPerson(userLogged.getTbAdmPerson());
			participant.setTbTaxQuote(novoQuote);
			
			try{
				
				participantFacade.save(participant);
			
			}catch(Exception e){
				
				try{
					quoteFacade.delete(novoQuote);
				}catch(Exception e1){					
					e1.printStackTrace();					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("msg_error_imported_quote"), ""));
					return;
				}
				
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("msg_error_imported_quote"), ""));
				
				return;
				
			}
			
		}*/
				
		
		novoQuote.setTbTaxQuoteItems(new ArrayList<TbTaxQuoteItem>());
		
		int ordem = 1;
		
		for(ImportQuoteItemTO itemTO: quoteTO.getQuoteItens()){
			
			TbTaxQuoteItem quoteItem = new TbTaxQuoteItem();
			
			quoteItem.setsModel(itemTO.getPartLineType());
			
			TbAdmMaterial material = materialFacade.findQuery(itemTO.getPartLineType().trim());
			
			if(material != null){
				
				quoteItem.setTbAdmMaterial(material);
				
				if(material.getTbAdmMaterialClass() != null){
					quoteItem.setTbAdmMaterialClass(material.getTbAdmMaterialClass());
					quoteItem.setsNcm(material.getTbAdmMaterialClass().getsNcm());
					try{
						quoteItem.setsLabelProductType(quoteItem.getTbAdmMaterial().getTbAdmMaterialClass().getTbAdmProductType().getsLabel());
						quoteItem.setiTaxModel(quoteItem.getTbAdmMaterial().getTbAdmMaterialClass().getTbAdmProductType().getiTaxModel().intValue());
					}catch(Exception e){
						
					}
				}else{
					sendMail = true;
				}
				
			}else{
				
				sendMail = true;
				
				TbAdmMaterial newMaterial = new TbAdmMaterial();
				newMaterial.setsInternalModel(itemTO.getPartLineType().trim());
				newMaterial.setsDescription(itemTO.getPartDescription().trim());
				newMaterial.setfAvailable(true);
				
				try{
					newMaterial.setmCost((Double.parseDouble(itemTO.getGrossRevenue().replace(",", "")) / Integer.parseInt(itemTO.getQty())));
				}catch(Exception e){
					newMaterial.setmCost(0D);
				}
				
				newMaterial.setTbAdmMaterialManufacturer(manufacturerFacade.find(21L)); // Todos os materiais importados s�o de fabricante oracle
				
				GregorianCalendar gc1 = new GregorianCalendar();
				
				gc1.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
				
				newMaterial.setDtItemCreationDate(gc1.getTime());
				
				try{
					quoteItem.setTbAdmMaterial(materialFacade.saveReturn(newMaterial));	
				}catch(Exception e){
					
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("msg_error_imported_quote"), ""));
					
					return;					
				}
			}
			
			/*if(itemTO.getLine().trim().endsWith(".0")){
				try{
					somaSupportNetPrice += Double.valueOf(itemTO.getSupportNetPrice().replace(",", ""));
					sOrdem = itemTO.getLine();
				}catch(Exception e){
					
				}
			}*/
			
			quoteItem.setsImportModel(itemTO.getPartLineType());
			quoteItem.setsImpDescription(itemTO.getPartDescription());
			quoteItem.setsImportNamedProductCategory(itemTO.getNamedProductCategory());
			quoteItem.setsImportGrossRevenue(itemTO.getGrossRevenue());
			quoteItem.setsImportEscalation(itemTO.getEscalation());
			quoteItem.setsImportAdrDiscount(itemTO.getAdrDiscount());
			quoteItem.setsImportContractualDisc(itemTO.getContractualDiscount());
			quoteItem.setsImportLineBusiness(itemTO.getLineOfBusiness());
			quoteItem.setsImportUnitPrice(itemTO.getUnitPrice());
			quoteItem.setsImportEstimatedSalesCredit(itemTO.getEstimatedSalesCredit());
			quoteItem.setsImportEstimatedSalesCreditPercent(itemTO.getEstimatedSalesCreditPercent());
			quoteItem.setsImportDiscCat(itemTO.getDiscCat());
			
			try{
				quoteItem.setiMonthDurationSupport(Integer.valueOf(itemTO.getDurationSupport()));
			}catch(Exception e){
				quoteItem.setiMonthDurationSupport(null);
			}
			
			try{
				if(quoteTO.isQuoteInBRL()){
					quoteItem.setmSupportNetPrice((Double.valueOf(itemTO.getSupportNetPrice()) / novoQuote.getmDollarRate()));
				}else{
					quoteItem.setmSupportNetPrice(Double.valueOf(itemTO.getSupportNetPrice()));
				}
			}catch(Exception e){
				quoteItem.setmSupportNetPrice(null);
			}
			
			try{
				if(quoteTO.isQuoteInBRL()){
					//quoteItem.setmImportedCost((Double.parseDouble(itemTO.getNetRevenue().replace(",", ""))) / novoQuote.getmDollarRate());
					quoteItem.setmImportedCost((Double.parseDouble(itemTO.getListFee().replace(",", ""))) / novoQuote.getmDollarRate());
				}else{
					//quoteItem.setmImportedCost(Double.parseDouble(itemTO.getNetRevenue().replace(",", "")));
					quoteItem.setmImportedCost(Double.parseDouble(itemTO.getListFee().replace(",", "")));
				}
			}catch(Exception e){
				quoteItem.setmImportedCost(0D);
			}
			
			try{ // =(1-(B1/A1))*100
				if(!itemTO.getAdrDiscount().isEmpty() && !itemTO.getAdrDiscount().equals("&nbsp;") && Float.parseFloat(itemTO.getUnitPrice().replace(",", "")) != 0F){
					quoteItem.setrDiscount((1-(Float.parseFloat(itemTO.getNetRevenue().replace(",", "")) / Float.parseFloat(itemTO.getUnitPrice().replace(",", ""))))/* * 100*/);
					if(quoteItem.getrDiscount() < 1){
						quoteItem.setrDiscount(Float.parseFloat("0."+itemTO.getAdrDiscount().replace(",", "")));
					}
				}else{
					quoteItem.setrDiscount(0F);
				}
			}catch(Exception e){
				quoteItem.setrDiscount(0F);
			}
			
			try{
				quoteItem.setrQty(Float.parseFloat(itemTO.getQty()));
			}catch(Exception e){
				quoteItem.setrQty(0F);
			}
			quoteItem.setsGroupNumber(itemTO.getsGroupNumber());
			quoteItem.setsOrdem(itemTO.getLine());
			
			quoteItem.setiLineNumber(ordem);
			
			quoteItem.setTbTaxQuote(novoQuote);
			
			if(!itemTO.isSupportLine()){
				novoQuote.getTbTaxQuoteItems().add(itemFacade.saveReturn(quoteItem));
			}else{
				novoQuote.getTbTaxQuoteItems().add(quoteItem);
			}
					
			
			ordem++;
		}
		
		
		
		for(int i = 0; i < novoQuote.getTbTaxQuoteItems().size(); i++){
			
			if(quoteTO.getQuoteItens().get(i).isSupportLine()){
				
				Float tax = (float) (novoQuote.getTbTaxQuoteItems().get(i).getmImportedCost() / novoQuote.getTbTaxQuoteItems().get(i-1).getmImportedCost());
				
				TbAdmMaterial material = materialFacade.findQuery(novoQuote.getTbTaxQuoteItems().get(i-1).getsModel());
				
				if(material != null){
					material.setrTaxSupport((tax * 100));
					material.setfTaxSupportActive(true);
					
					materialFacade.alter(material);
				}else{
					TbAdmMaterial newMaterial = new TbAdmMaterial();
					newMaterial.setsInternalModel(quoteTO.getQuoteItens().get(i).getPartLineType().trim());
					newMaterial.setsDescription(quoteTO.getQuoteItens().get(i).getPartDescription().trim());
					newMaterial.setfAvailable(true);
					
					try{
						newMaterial.setmCost((Double.parseDouble(quoteTO.getQuoteItens().get(i).getGrossRevenue().replace(",", "")) / Integer.parseInt(quoteTO.getQuoteItens().get(i).getQty())));
					}catch(Exception e){
						newMaterial.setmCost(0D);
					}
					
					newMaterial.setTbAdmMaterialManufacturer(manufacturerFacade.find(21L)); // Todos os materiais importados s�o de fabricante oracle
					
					GregorianCalendar gc1 = new GregorianCalendar();
					
					gc1.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
					
					newMaterial.setDtItemCreationDate(gc1.getTime());
					
					material.setrTaxSupport((tax * 100));
					material.setfTaxSupportActive(true);
					
					try{
						newMaterial = materialFacade.saveReturn(newMaterial);	
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}
			
		}
		
		
		
		
		if(sendMail){
			
			TbAdmQuoteParameters parameters = parametersFacade.findAll().get(0);
			
			String[] emailAlert = parameters.getsEmailAlertMatNoClassified().split(";");
			
			List<String> emailsAlertas = new ArrayList<>();
			
			String mailSalesRep = null;
			
			for(int i = 0; i < emailAlert.length; i ++){
				emailsAlertas.add(emailAlert[i]);
			}
			
			if(parameters.isfSendEmailSalesRep()){
				mailSalesRep = userLogged.getsEmail();
			}
			
			try{
				email.sendHtmlEmail(" ", mailSalesRep, emailsAlertas, "Material without tax classification", novoQuote, "");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("quoteImportWiz");
		
	}
	
	
	public String saveImportCsv(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(novoQuote.getsCustomer() == null || novoQuote.getsCustomer().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_customer_name")), ""));
				
				return "error";
			}
			
			if(novoQuote.getsQuoteName() == null || novoQuote.getsQuoteName().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_quote_name")), ""));
				
				return "error";
			}
			
			if(novoQuote.getsQuoteNumber() == null || novoQuote.getsQuoteNumber().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_quote_number")), ""));
				
				return "error";
			}
			
			if(!destinoSelecionado.equals("-1")){
				
				TbAdmDestination destino = destinationFacade.find(Long.parseLong(destinoSelecionado));
				novoQuote.setTbAdmDestination(destino);
				novoQuote.setTbAdmOriginHw(destino.getTbAdmOriginHw());
				novoQuote.setTbAdmOriginSw(destino.getTbAdmOriginSw());
				novoQuote.setTbAdmOriginSv(destino.getTbAdmOriginSv());
				novoQuote.setTbAdmOriginMt(destino.getTbAdmOriginMt());
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_destination")), ""));
				
				return "error";
			}
			
			
			novoQuoteDetail.setTbTaxQuote(novoQuote);
			
			novoQuoteDetail = detailFacade.saveReturn(novoQuoteDetail);
			
			novoQuote.setTbTaxQuoteDetail(novoQuoteDetail);
			
			//quoteFacade.alter(novoQuote);
			
		//	if(!personSelecionadoSalesRep.equals("-1")){
		//		novoQuote.setTbAdmPerson(personFacade.find(Long.valueOf(personSelecionadoSalesRep)));
		//	}
			
			if(!groupSelecionado.equals("-1")){
				novoQuote.setTbAdmGroupOfContent(groupFacade.find(Long.parseLong(groupSelecionado)));
			}
			
			try{
				
				List<TbTaxQuote> listTaxQuote = quoteFacade.verifyQuoteSameName(novoQuote.getsQuoteNumber());
				
				if(listTaxQuote != null && !listTaxQuote.isEmpty()){
					novoQuote.setiQuoteVersion(listTaxQuote.size() + 1);
				}else{
					novoQuote.setiQuoteVersion(1);
				}
				
				for(TbTaxQuote tbTaxQuote : listTaxQuote){
					
					if(tbTaxQuote.getId() != novoQuote.getId()){
						tbTaxQuote.setDtDelete(new Date());
						tbTaxQuote.setDtRelease(new Date());
						tbTaxQuote.setfDelete(true);
						tbTaxQuote.setsDescription("Quote deleted because there is a new version of the same created in the system");
					
						quoteFacade.alter(tbTaxQuote);
					}
					
				}
					
	
				quoteFacade.alter(novoQuote);
				
				
				listaQuotesAno = new ArrayList<>();
				listaQuotes = new ArrayList<>();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('quoteImportDialog').hide();");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
				return redirectImportCvs();
				
			}catch(Exception e){
				e.printStackTrace();
				
				listaQuotesAno = new ArrayList<>();
				listaQuotes = new ArrayList<>();
				
				cancelImportCsv();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('quoteImportDialog').hide();");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
				return "error";
				
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Fecho o modal da tela
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('quoteImportDialog').hide();");
			
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
			
			return "error";
		}else{
			return "error";
		}
	}
	
	
	public void cancelImportCsv(){
		
		novoQuote.setfDelete(true);
		novoQuote.setDtDelete(new Date());
		
		try{
			
			quoteFacade.alter(novoQuote);
			

			listaQuotesAno = new ArrayList<>();
			listaQuotes = new ArrayList<>();			
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
			
		}catch(Exception e){
			
			listaQuotesAno = new ArrayList<>();
			listaQuotes = new ArrayList<>();			
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}
	}
	
	
	public String redirectImportCvs(){
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("quote", novoQuote.getId());
		
		return "details";
		
	}
	
	
	public String onFlowProcessImport(FlowEvent event) {

		return event.getNewStep();
    }
	
	
	public void tabIndex(){  
        tabView.setActiveIndex(Integer.parseInt(tab)); 
    }  
	
	/**
	 * Getter and Setter 
	 * @return
	 */
	
	public TbTaxQuote getNovoQuote() {
		return novoQuote;
	}

	public void setNovoQuote(TbTaxQuote novoQuote) {
		this.novoQuote = novoQuote;
	}

	public TbTaxQuote getQuoteSelecionado() {
		return quoteSelecionado;
	}

	public void setQuoteSelecionado(TbTaxQuote quoteSelecionado) {
		this.quoteSelecionado = quoteSelecionado;
	}

	public List<TbTaxQuote> getListaQuotes() {
		
		if(listaQuotes.size() == 0 && !isSearch()){
			preparaLista();
		}
		
		return listaQuotes;
	}

	public void setListaQuotes(List<TbTaxQuote> listaQuotes) {
		this.listaQuotes = listaQuotes;
	}

	public List<TbSysUser> getListaPerson() {
		
		preparaListPerson();
		
		return listaPerson;
	}

	public void setListaPerson(List<TbSysUser> listaPerson) {
		this.listaPerson = listaPerson;
	}

	public String getPersonSelecionado() {		
		return personSelecionado;
	}

	public void setPersonSelecionado(String personSelecionado) {
		this.personSelecionado = personSelecionado;
	}

	public String getQuoteMenuSelecionado() {
		return quoteMenuSelecionado;
	}

	public void setQuoteMenuSelecionado(String quoteMenuSelecionado) {
		this.quoteMenuSelecionado = quoteMenuSelecionado;
	}

	public TbTaxQuoteDetail getNovoQuoteDetail() {
		return novoQuoteDetail;
	}

	public void setNovoQuoteDetail(TbTaxQuoteDetail novoQuoteDetail) {
		this.novoQuoteDetail = novoQuoteDetail;
	}

	public List<TbAdmGroupOfContent> getListaGroup() {
		
		if(listaGroup.size() == 0){
			preparaListaGroup();
		}
		
		return listaGroup;
	}

	public void setListaGroup(List<TbAdmGroupOfContent> listaGroup) {
		this.listaGroup = listaGroup;
	}

	public String getGroupSelecionado() {
		return groupSelecionado;
	}

	public void setGroupSelecionado(String groupSelecionado) {
		this.groupSelecionado = groupSelecionado;
	}

	public List<TbAdmDestination> getListaDestinations() {
		
		if(listaDestinations.size() == 0){
			preparaListaDestination();
		}
		
		return listaDestinations;
	}

	public void setListaDestinations(List<TbAdmDestination> listaDestinations) {
		this.listaDestinations = listaDestinations;
	}

	public String getDestinoSelecionado() {
		return destinoSelecionado;
	}

	public void setDestinoSelecionado(String destinoSelecionado) {
		this.destinoSelecionado = destinoSelecionado;
	}

	public List<TbTaxQuote> getListaQuotesAno() {
		
		if(listaQuotesAno.size() == 0){
			preparaListaQuoteAno();
		}
		
		return listaQuotesAno;
	}

	public void setListaQuotesAno(List<TbTaxQuote> listaQuotesAno) {
		this.listaQuotesAno = listaQuotesAno;
	}

	public String getPersonSelecionadoSalesRep() {
		return personSelecionadoSalesRep;
	}

	public void setPersonSelecionadoSalesRep(String personSelecionadoSalesRep) {
		this.personSelecionadoSalesRep = personSelecionadoSalesRep;
	}

	public String getFilterStatusSelecionado() {
		return filterStatusSelecionado;
	}

	public void setFilterStatusSelecionado(String filterStatusSelecionado) {
		this.filterStatusSelecionado = filterStatusSelecionado;
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public ImportQuoteTO getQuoteTO() {
		return quoteTO;
	}

	public void setQuoteTO(ImportQuoteTO quoteTO) {
		this.quoteTO = quoteTO;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public TabView getTabView() {
		return tabView;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
	}

	public LazyDataModel<TbTaxQuote> getLazyModel() {
		
		if(lazyModel == null){
			preparaLista();
		}
		
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<TbTaxQuote> lazyModel) {
		this.lazyModel = lazyModel;
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


	public boolean isCheckListQuotesDeleted() {
		return checkListQuotesDeleted;
	}


	public void setCheckListQuotesDeleted(boolean checkListQuotesDeleted) {
		this.checkListQuotesDeleted = checkListQuotesDeleted;
	}	
	
	
	
}
