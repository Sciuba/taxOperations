package br.com.saboia.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;

import br.com.saboia.app.quote.facade.AppQuote;
import br.com.saboia.app.quote.model.QuoteModel;
import br.com.saboia.app.quote.to.QuoteItemTO;
import br.com.saboia.app.quote.to.SomaTotaisTO;
import br.com.saboia.entity.TbAdmDestination;
import br.com.saboia.entity.TbAdmGroupOfContent;
import br.com.saboia.entity.TbAdmMaterial;
import br.com.saboia.entity.TbAdmMaterialClass;
import br.com.saboia.entity.TbAdmOrigin;
import br.com.saboia.entity.TbAdmProductType;
import br.com.saboia.entity.TbAdmQuoteParameters;
import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.entity.TbSysWorkGroup;
import br.com.saboia.entity.TbTaxLastUpdateAdditionalCost;
import br.com.saboia.entity.TbTaxQuote;
import br.com.saboia.entity.TbTaxQuoteDetail;
import br.com.saboia.entity.TbTaxQuoteItem;
import br.com.saboia.entity.TbTaxQuoteLog;
import br.com.saboia.entity.TbTaxQuoteParticipant;
import br.com.saboia.facade.DailyDollarFacade;
import br.com.saboia.facade.DestinationFacade;
import br.com.saboia.facade.DollarRateFacade;
import br.com.saboia.facade.GroupOfContentFacade;
import br.com.saboia.facade.LastUpdateAdditionalUpdateCostFacade;
import br.com.saboia.facade.MaterialClassFacade;
import br.com.saboia.facade.MaterialFacade;
import br.com.saboia.facade.OriginFacade;
import br.com.saboia.facade.ProductTypeFacade;
import br.com.saboia.facade.QuoteDetailFacade;
import br.com.saboia.facade.QuoteDiscFacade;
import br.com.saboia.facade.QuoteFacade;
import br.com.saboia.facade.QuoteItemFacade;
import br.com.saboia.facade.QuoteParametersFacade;
import br.com.saboia.facade.QuoteParticipantFacade;
import br.com.saboia.facade.UserFacade;
import br.com.saboia.facade.impl.SystemPermissionFacadeImpl;
import br.com.saboia.facade.impl.TaxQuoteLogFacadeImpl;
import br.com.saboia.facade.impl.WorkGroupFacadeImpl;
import br.com.saboia.to.ImportWizardTO;
import br.com.saboia.utils.SendEmail;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;

@ManagedBean(name="detailBBean")
@ViewScoped
public class QuoteDetailBBean {

	
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
	private OriginFacade originFacade;
	
	@EJB
	private QuoteParticipantFacade participantFacade;
	
	@EJB
	private DailyDollarFacade dollarFacade;
	
	@EJB
	private DollarRateFacade dollarRateFacade;
	
	@EJB
	private LastUpdateAdditionalUpdateCostFacade updateCostFacade;
	
	@EJB
	private MaterialFacade materialFacade;
	
	@EJB
	private QuoteParametersFacade parametersFacade;
	
	@EJB
	private AppQuote appQuote;
	
	@EJB
	private MaterialClassFacade materialClassFacade;
	
	@EJB
	private TaxQuoteLogFacadeImpl quoteLogFacadeImpl;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	@EJB
	private UserFacade userFacade;
	
	@EJB
	private WorkGroupFacadeImpl groupFacadeImpl;
	
	@EJB
	private ProductTypeFacade productTypeFacade;
	
	@EJB
	private DailyDollarFacade dailyDollarFacade;
	
	private TbTaxQuote quoteSelecionado;
	private TbTaxQuote quoteDefault;
	private TbTaxQuote quoteVersion;
	
	private String taxModelSelecionado;
	
//	private TbTaxQuoteItem quoteItemSelecionado;
	
	private QuoteModel quoteModel;
	
	private QuoteModel quoteModelVersion;
	
	private int idQuoteItemDeleted;
	
	private TbSysUser userLogged;
	
	private String sComments;
	
	private List<TbTaxQuoteItem> listaQuoteItem;
	private List<TbAdmDestination> listaDestination;
	private List<TbAdmOrigin> listaOrigin;
	private List<TbAdmGroupOfContent> listaGrupo;
	private List<TbSysUser> listaPerson;
	private List<TbTaxLastUpdateAdditionalCost> lisAdditionalCosts;
	private List<ImportWizardTO> listaWizard;
	private List<TbAdmMaterialClass> listaMaterialClass;
	private List<TbTaxQuoteLog> listaLogQuote;
	private List<TbAdmDestination> listaDestinations;
	private List<TbTaxQuote> listaQuoteVersions;
	
	private List<TbAdmProductType> listaProductTypes;
	
	private List<TbTaxQuoteItem> listaQuoteItemPais;
	
	private List<TbSysWorkGroup> listaWorkGroup; 
	
	private ResourceBundle bundle;
	
	private boolean currencyChange;
	private String currency;
	
	private String destination;
	
	private String originHW;
	private String originSW;
	private String originSV;
	private String originMT;
	
	private String grupo;
	
	private String taxSelecionado;
	
	private String classSelecionado;
	
	private String paiSelecionado;
	
	private String ncmSelecionado;
	
	private boolean flagQuoteItemOriginal;
	
	private boolean flagQuoteNcmOriginal;
	
	private Float dollarRate;
	
	private Double discountTaxModel;
	
	private DualListModel<TbSysUser> participants;
	
	private String participantesSelecionados;
	
	private String materialCode;
	private String groupColumn;
	private String quantityColumn;
	private String totalCostvalue;
	
	private String materialPartNumber;
	
	private TbAdmMaterial materialSelecionado;
	private TbTaxQuoteItem itemSelecionado;
	
	private boolean oldVersion;
	
	private boolean flagMaterialCode;
	private boolean flagGroupColumn;
	private boolean flagQuantityColumn;
	private boolean flagTotalCostvalue;
	
	private boolean disabled;
	
	private boolean goalSeek;
	private boolean gsHW;
	private boolean gsSW;
	private boolean gsSV;
	private boolean gsMD;
	private boolean gsTotal;
	
	private boolean gsRadioNet;
	private boolean gsRadioGross;
	
	private String htmlToExcel;
	private String fileName;
	
	private String personSelecionadoSalesRep;
	private String sCustomerClone;
	private String sQuoteNumberClone;
	private String sQuoteNameClone;
	
	private String destinoSelecionado;
	private String sIeBillClone;
	private boolean fLeasingClone;
	private boolean fSuframaClone;
	private boolean fContrestClone;
	private boolean fPartnerClone;
	
	private String tab = "0";
	
	private TabView tabView;
	
	private boolean reports;
	
	NumberFormat df = NumberFormat.getCurrencyInstance();
	
	private boolean read;
	private boolean write;
	private boolean delete;
	private boolean addItem;
	private boolean goalSeekPerm;
	private boolean restoreValue;
	private boolean taxFree;
	private boolean discount;
	private boolean taxReportFull;
	private boolean taxAnalysisCalc;
	
	private boolean checkedDiscountPT;
	
	
	private List<TbSysSystemPermission> permissionList;
	
	private HashMap<String, String> mapPermission;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "quoteDetail";
		
	private String sKey;
	
	private boolean disabledAll;
	
	/**
	 * metodos
	 */
	
	@PostConstruct
	public void init(){
		
		df.setMinimumFractionDigits(2);
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		permissionList = new ArrayList<>();
		mapPermission = new HashMap<>();
		
		listaProductTypes = new ArrayList<>();
		
		listaWorkGroup = new ArrayList<>();
		
		discountTaxModel = 0D;
		
		sKey = "";
		
		checkSecurity();
		
		Long iQuote =  (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("quote");
		
		quoteSelecionado = quoteFacade.find(iQuote);
		quoteDefault = quoteFacade.find(iQuote);			
		
		
		listaQuoteVersions = new ArrayList<>();
		listaQuoteVersions = quoteFacade.verifyQuoteSameName(quoteSelecionado.getsQuoteNumber(), quoteSelecionado.getiQuoteVersion());
		
		if(listaQuoteVersions != null && !listaQuoteVersions.isEmpty()){
			oldVersion = true;
		}else{
			oldVersion = false;
		}		
		
		if(quoteSelecionado.getTbTaxQuoteDetail() == null){
			quoteSelecionado.setTbTaxQuoteDetail(new TbTaxQuoteDetail());
		}
		
		currencyChange = true;
		currency = "BRL";
		
		//TODO: Rever método
		verificaTaxDollar();
		 
		
		quoteModel = new QuoteModel();
		quoteModel.setIdTaxQuote(quoteSelecionado.getId());
		quoteModel.setListaQuoteItemTO(new ArrayList<QuoteItemTO>());
		
		
		/**
		 * Populando parametros no Objeto Model
		 * e Efetua o Carregamento dos atributos, Cálculo da Quote e efetua o save após as operações
		 */
		
		Load();
				
				
//		listaQuoteItem = itemFacade.simpleQuery(iQuote);
//		
				
		listaDestination = new ArrayList<>();
		listaOrigin = new ArrayList<>();
		listaGrupo = new ArrayList<>();
		listaPerson = new ArrayList<>();
//		lisAdditionalCosts = new ArrayList<>();
		listaWizard = new ArrayList<>();
		listaMaterialClass = new ArrayList<>();
		listaLogQuote = new ArrayList<>();
		listaDestinations = new ArrayList<TbAdmDestination>();
		
		materialCode = "";
		groupColumn = "";
		quantityColumn = "";
		totalCostvalue = "";
		
		montaPickListParticipants();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
		
		alterCurrency();
		
	}

	
	public void checkSecurity(){
		
		sKey = "";
		read = false;
		write = false;
		delete = true;
		addItem = false;
		goalSeekPerm = false;
		restoreValue = false;
		taxFree = false;
		discount = false;
		taxReportFull = false;
		taxAnalysisCalc = false;
		disabledAll = false;
		
		permissionList = systemPermissionFacadeImpl.findPermissionByUserIdAndModule(userLogged.getId(), SMODULE);
		
		if(permissionList != null && !permissionList.isEmpty()){
			for(TbSysSystemPermission permission : permissionList){
				mapPermission.put(permission.getsKey(), permission.getsKey());		
			}
		}
		
		if(mapPermission.containsKey("READ") && !mapPermission.containsKey("WRITE")){
			read = true;
		}
		
		if(mapPermission.containsKey("WRITE")){
			read = false;
			write = true;
		}
		
		if(mapPermission.containsKey("ADDITEM") && mapPermission.containsKey("WRITE")){
			addItem = true;
		}
		
		if(mapPermission.containsKey("GOALSEEK") && mapPermission.containsKey("WRITE")){
			goalSeekPerm = true;
		}
		
		if(mapPermission.containsKey("RESTOREVALUE") && mapPermission.containsKey("WRITE")){
			restoreValue = true;
		}
		
		if(mapPermission.containsKey("TAXFREE") && mapPermission.containsKey("WRITE")){
			taxFree = true;
		}
		
		if(mapPermission.containsKey("DISCOUNT") && mapPermission.containsKey("WRITE")){
			discount = true;
		}
		
		if(mapPermission.containsKey("TAXREPORTFULL")){
			taxReportFull = true;
		}
		
		if(mapPermission.containsKey("TAXANALYSIS")){
			taxAnalysisCalc = true;
		}
		
		if(mapPermission.containsKey("DELETEITEM")){
			delete = false;
		}
		
		if(!mapPermission.containsKey("READ") && !mapPermission.containsKey("WRITE")){
			
			disabledAll = true;
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not allowed to access this module. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");		
			
		}
		
	}
	
	
	public void Load(){
		
		checkSecurity();
		
		if(!read && !disabledAll){
		
			/**
			 * Parâmetros
			 */
			quoteModel.setfPartner(quoteSelecionado.isfPartner());
			quoteModel.setBlnfContrEst(quoteSelecionado.isfContrest());
			quoteModel.setBlnfSuframa(quoteSelecionado.isfSuframa());
			quoteModel.setBlnfLeasing(quoteSelecionado.isfLeasing());
			quoteModel.setClosedQuote(quoteSelecionado.getDtClose());
			quoteModel.setReleaseQuote(quoteSelecionado.getDtRelease());
			quoteModel.setfImportAll(quoteSelecionado.isfImportAll());
			quoteModel.setfPPBDiscountClass(quoteSelecionado.isfPpbDiscountClass());
			quoteModel.setmTotalAdditionalCost((quoteSelecionado.getmAdditionalCostFreight() != null ? quoteSelecionado.getmAdditionalCostFreight() : 0) + 
					(quoteSelecionado.getmAdditionalCostProjectCost() != null ? quoteSelecionado.getmAdditionalCostProjectCost() : 0) + (quoteSelecionado.getmAdditionalCostTraining() != null ? quoteSelecionado.getmAdditionalCostTraining() : 0) +
					(quoteSelecionado.getmAdditionalCostOtherCosts() != null ? quoteSelecionado.getmAdditionalCostOtherCosts() : 0));
			
			quoteModel.setfPIS(quoteSelecionado.isfPIS());
			quoteModel.setfCOFINS(quoteSelecionado.isfCOFINS());
			quoteModel.setfICMS(quoteSelecionado.isfICMS());
			quoteModel.setfIPI(quoteSelecionado.isfIPI());
			quoteModel.setfISS(quoteSelecionado.isfISS());
			quoteModel.setfSt(quoteSelecionado.isfSt());
						
			try{
				appQuote.biggestRateRecover(quoteModel);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			quoteModel.setListaQuoteItemTO(null);
			
			quoteModel.setListaQuoteItemTO(appQuote.executeStoredProcedure(Integer.parseInt(String.valueOf(quoteModel.getIdTaxQuote()))));
			
			if(quoteModel.getReleaseQuote() == null){
				
				quoteSelecionado.setmDailyDollar(dailyDollarFacade.simpleQueryActualDollarRate().getrDollar().doubleValue());				
				
				appQuote.CalcularQuote(quoteModel);
				
				appQuote.salvarAtributos(quoteModel);
				
				if(listaQuoteItem == null || listaQuoteItem.size() == 0){
					listaQuoteItem = new ArrayList<>();
					double somaGross = 0;
					double somaNet = 0;
					for(QuoteItemTO to : quoteModel.getListaQuoteItemTO()){
						
						somaNet+= to.getmNetPrice();
						somaGross+= to.getmGrossPrice();
						
					}
					
					quoteSelecionado.setmTotalNet(new BigDecimal(String.valueOf(somaNet)));
					quoteSelecionado.setmTotalGross(new BigDecimal(String.valueOf(somaGross)));
					
					try{
						
						quoteFacade.alter(quoteSelecionado);
						
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}else{
					
					double somaGross = 0;
					double somaNet = 0;
					
					for(QuoteItemTO to : quoteModel.getListaQuoteItemTO()){
						for(TbTaxQuoteItem item : listaQuoteItem){
							if(to.getId() == item.getId()){
								
								somaNet+= to.getmNetPrice();
								somaGross+= to.getmGrossPrice();
								
							}
						}
					}
					
					quoteSelecionado.setmTotalNet(new BigDecimal(String.valueOf(somaNet)));
					quoteSelecionado.setmTotalGross(new BigDecimal(String.valueOf(somaGross)));
					
					try{
						
						quoteFacade.alter(quoteSelecionado);
						
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}else{
				
				if(currencyChange){
					alterCurrency();
				}
				
				appQuote.somaCalculoQuoteRelease(quoteModel);
			}
			
		}
	}
	

	public void tabIndex(){  
        tabView.setActiveIndex(Integer.parseInt(tab)); 
    }  
	
	
	public void preparaListaDestination(){
		listaDestinations = destinationFacade.findOrderBy();
	}
	
	
	public void leasingClone(){
		if(isfLeasingClone()){
			setfContrestClone(true);
			setfPartnerClone(true);
		}else{
			setfContrestClone(false);
		}
	}
	
	public void discountProductType(){
		
		checkSecurity();
		
		if(write){
			
			if((discountTaxModel > 0 && discountTaxModel <= 100) && !taxModelSelecionado.equals("-1")){
				
				checkedDiscountPT = true;
				
				for(QuoteItemTO to : quoteModel.getListaQuoteItemTO()){
					
					if((to.getsLabelProductType() != null) && to.getiTaxModel() == Float.valueOf(taxModelSelecionado)){
						to.setChecked(true);						
					}
					
				}
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('disocuntProductType').hide();");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
								
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"All the Fields is required.", ""));
				
				return;
				
			}
			
			
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");				
			
		}
		
	}
	
	public void saveDiscountProductType(){
		
		for(QuoteItemTO to : quoteModel.getListaQuoteItemTO()){
			
			if(to.isChecked()){
				to.setrDiscount(discountTaxModel.floatValue());				
			}
			
		}
				
		salvarQuote();
		
		discountTaxModel = 0D;
		taxModelSelecionado = "-1";
		
	}
	
	
	public void goalSeek(){
		
		checkSecurity();
		
		if(write && goalSeekPerm){
			goalSeek = true;
			
			for(QuoteItemTO itemTO : quoteModel.getListaQuoteItemTO()){
				if(gsTotal){
					if(itemTO.getmImportedCost() != 0){
						itemTO.setChecked(true);
					}
				}else{
					if(gsSW && itemTO.getmImportedCost() != 0 && itemTO.getiTaxModel() == 2){
						itemTO.setChecked(true);
					}else if(gsSV && itemTO.getmImportedCost() != 0 && itemTO.getiTaxModel() == 3){
						itemTO.setChecked(true);
					}else if(gsMD && itemTO.getmImportedCost() != 0 && itemTO.getiTaxModel() == 5){
						itemTO.setChecked(true);
					}else if(gsHW && itemTO.getmImportedCost() != 0 && itemTO.getiTaxModel() == 1){
						itemTO.setChecked(true);					
					}
				}
			}
		
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
			
		}
		
	}

	public void changeToNet(){
		
		gsRadioNet = true;
		
		gsRadioGross = false;
		
	}
	
	public void changeToGross(){
		
		gsRadioGross = true;
		
		gsRadioNet = false;
		
	}
	
	public void somenteTotalGS(){
		
		if(gsTotal){
			gsSV = false;
			gsHW = false;
			gsMD = false;
			gsSW = false;
		}
		
	}
	
	public void controlarCheckBoxGS(){
		
		if(gsSV){
			gsTotal = false;
		}else if(gsHW){
			gsTotal = false;
		}else if(gsMD){
			gsTotal = false;
		}else if(gsSW){
			gsTotal = false;
		}
		
	}
	
	
	public void executeGoalSeek(){
		
		checkSecurity();
		
		if(write && goalSeekPerm){
		
			quoteModel.setGsHW(gsHW);
			quoteModel.setGsSW(gsSW);
			quoteModel.setGsCS(gsSV);
			quoteModel.setGsMD(gsMD);
			quoteModel.setGsTotal(gsTotal);
			
			String typeValue = gsRadioNet ? "netPrice" : "grossPrice";
			
			appQuote.goalSeek(quoteModel, typeValue);
		
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
		}	
			
				
	}
	
	
	public void cancelGoalSeek(){
		goalSeek = false;
	}
	
	public void saveQuoteName(){
		
		checkSecurity();
		
		if(write){
		
			quoteFacade.alter(quoteSelecionado);
			
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
		}
	}
	
	
	public void salvarQuote(){
		
		checkSecurity();
		
		if(write){
			
			/*if(currencyChange){
				currencyChange = false;
				
			}*/
			
			
			appQuote.salvarAtributos(quoteModel);
			
			try{
				appQuote.biggestRateRecover(quoteModel);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			quoteModel.setListaQuoteItemTO(appQuote.executeStoredProcedure(Integer.parseInt(String.valueOf(quoteModel.getIdTaxQuote()))));
						
			appQuote.CalcularQuote(quoteModel);
			
			appQuote.salvarAtributos(quoteModel);
			
			quoteModel.setGsGrossPriceCS(0D);
			quoteModel.setGsGrossPriceHW(0D);
			quoteModel.setGsGrossPriceSW(0D);
			quoteModel.setGsGrossPricePS(0D);
			quoteModel.setGsGrossPriceMD(0D);
			quoteModel.setGsTotalGross(0D);
			
			quoteModel.setGsNetPriceCS(0D);
			quoteModel.setGsNetPriceHW(0D);
			quoteModel.setGsNetPriceSW(0D);
			quoteModel.setGsNetPricePS(0D);
			quoteModel.setGsNetPriceMD(0D);
			quoteModel.setGsTotalNet(0D);
			
			goalSeek = false;
		
			alterCurrency();
			
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
		}
	}
	
	
	public void restoreValues(){
		
		checkSecurity();
		
		if(write && restoreValue){
			
			for(QuoteItemTO itemTO: quoteModel.getListaQuoteItemTO()){
				itemTO.setrDiscount(0);
			}
			
			salvarQuote();
		
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
			
		}
	}
	
	
	public void releaseQuote(){
		
		checkSecurity();
		
		if(write){
		
			GregorianCalendar gc1 = new GregorianCalendar();
			
			gc1.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
			
			quoteSelecionado.setDtRelease(gc1.getTime());
			
			try{
				quoteFacade.alter(quoteSelecionado);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"This quote is Released.", ""));
				
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
			}
			
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
		}
	}
	
	public void cloneQuote(){
		
		checkSecurity();
		
		if(write){
			
			
			if((personSelecionadoSalesRep == null || personSelecionadoSalesRep.isEmpty()) && userLogged.isfManagerGroupSales()){
				
				FacesContext.getCurrentInstance().addMessage("cloneMsg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required") ,"Sales Operation"), ""));
				
				return;
				
			}else if(sCustomerClone == null || sCustomerClone.isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("cloneMsg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required") , bundle.getString("label_customer_name")), ""));
								
				return;
			
			}else if(sQuoteNumberClone == null || sQuoteNumberClone.isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("cloneMsg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required") , bundle.getString("label_quote")), ""));
								
				return;
				
			}else if(sQuoteNameClone == null || sQuoteNameClone.isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("cloneMsg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required") , bundle.getString("label_quote_name")), ""));
								
				return;
				
			}else if(destinoSelecionado == null || destinoSelecionado.isEmpty() || destinoSelecionado.equals("-1")){
								
				FacesContext.getCurrentInstance().addMessage("cloneMsg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required") , bundle.getString("label_destination")), ""));
								
				return;								
				
			}
			
			TbTaxQuote cloneQuote = (TbTaxQuote) cloneSerializable(quoteSelecionado);
//			
			cloneQuote.setId(0);
			
			cloneQuote.setsQuoteName(sQuoteNameClone);
			cloneQuote.setsQuoteNumber(sQuoteNumberClone);
			cloneQuote.setsCustomer(sCustomerClone);
			
			
			try{
				
				cloneQuote.setTbAdmDestination(destinationFacade.find(Long.valueOf(destinoSelecionado)));
				
				if(cloneQuote.getsQuoteNumber().equals(quoteSelecionado.getsQuoteNumber())){
					cloneQuote.setiQuoteVersion(quoteSelecionado.getiQuoteVersion() + 1);
					
					quoteSelecionado.setDtDelete(new Date());
					quoteSelecionado.setDtRelease(new Date());
					quoteSelecionado.setfDelete(true);
					quoteSelecionado.setsDescription("Quote deleted because there is a new version of the same created in the system");
				
					quoteFacade.alter(quoteSelecionado);
				
				}else{
					cloneQuote.setiQuoteVersion(1);
				}
								
				cloneQuote = quoteFacade.saveReturn(cloneQuote);
				
			}catch(Exception e){
				
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				return;				
				
			}
			
			//cloneQuote.setTbTaxQuoteItems(new ArrayList<TbTaxQuoteItem>());
			
			List<TbTaxQuoteItem> listaItens = itemFacade.findItensByTaxQuote(quoteSelecionado.getId());
			
			if(listaItens != null && listaItens.size() > 0){
				
				for(TbTaxQuoteItem i : listaItens){
					
					TbTaxQuoteItem item = (TbTaxQuoteItem) cloneSerializable(i);
					
					item.setId(0);
					
					item.setTbTaxQuote(cloneQuote);
										
					try{
						
						itemFacade.save(item);
						
					}catch(Exception e){
						
						e.printStackTrace();
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								bundle.getString("save_error"), ""));
						
						return;
						
					}
					
				}
				
				try{
					
					quoteFacade.alter(cloneQuote);
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							"Clone Success", ""));
					
					RequestContext context = RequestContext.getCurrentInstance();
						context.execute("PF('cloneForm').hide();");					
					
				}catch(Exception e){
					//Erro ao atualizar os itens da Quote;
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					return;
					
				}
				
			}
		
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
		}
	}
	
	/**
	 * Método para clonar objetos mesmo sendo complexos
	 * @param obj
	 * @return
	 */
	
	Object cloneSerializable(Serializable obj) {  
	  ObjectOutputStream out = null;  
	  ObjectInputStream in = null;  
	  
	  try {  
	    ByteArrayOutputStream bout = new ByteArrayOutputStream();  
	    out = new ObjectOutputStream(bout);  
	              
	    out.writeObject(obj);  
	    out.close();  
	              
	    ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());  
	    in = new ObjectInputStream(bin);              
	    Object copy = in.readObject();  
	  
	    in.close();  
	              
	    return copy;  
	  } catch (Exception ex) {  
	    ex.printStackTrace();  
	  } finally {  
	    try {  
	      if(out != null) {  
	        out.close();  
	      }  
	                  
	      if(in != null) {  
	        in.close();  
	      }  
	    } catch (IOException ignore) {}  
	  }  
	          
	  return null;  
	}  
	
	
	public void closeQuote(){
		
		checkSecurity();
		
		if(write){
		
			GregorianCalendar gc1 = new GregorianCalendar();
			
			gc1.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
			
			quoteSelecionado.setDtClose(gc1.getTime());
			
			try{
				quoteFacade.alter(quoteSelecionado);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"This quote is Closed.", ""));
				
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
			}
		
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
		}
	}
	
	public void unreleaseQuote(){
		
		checkSecurity();
		
		if(write){
			
			quoteSelecionado.setDtRelease(null);
			
			try{
				quoteFacade.alter(quoteSelecionado);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"This quote is ReOpen.", ""));
				
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
			}
			
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
		}
	}	
	
	public void alterCurrency(){
		if(currencyChange){
			currency = "BRL";
			
			quoteModel.setrNetPrice((float) (quoteModel.getrNetPrice() * quoteSelecionado.getmDollarRate()));
			quoteModel.setNetPriceHW(quoteModel.getNetPriceHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setNetPriceCS(quoteModel.getNetPriceCS() * quoteSelecionado.getmDollarRate());
			quoteModel.setNetPriceSW(quoteModel.getNetPriceSW() * quoteSelecionado.getmDollarRate());
			quoteModel.setNetPriceMD(quoteModel.getNetPriceMD() * quoteSelecionado.getmDollarRate());
			
			quoteModel.setrGrossPrice((float) (quoteModel.getrGrossPrice() * quoteSelecionado.getmDollarRate()));
			quoteModel.setGrossPriceHW(quoteModel.getGrossPriceHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setGrossPriceSW(quoteModel.getGrossPriceSW() * quoteSelecionado.getmDollarRate());
			quoteModel.setGrossPriceCS(quoteModel.getGrossPriceCS() * quoteSelecionado.getmDollarRate());
			quoteModel.setGrossPriceMD(quoteModel.getGrossPriceMD() * quoteSelecionado.getmDollarRate());
			
			quoteModel.setTaxesHW(quoteModel.getTaxesHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setTaxesSW(quoteModel.getTaxesSW() * quoteSelecionado.getmDollarRate());
			quoteModel.setTaxesCS(quoteModel.getTaxesCS() * quoteSelecionado.getmDollarRate());
			quoteModel.setTaxesMD(quoteModel.getTaxesMD() * quoteSelecionado.getmDollarRate());
			
			quoteModel.setmICMSMaterialHW(quoteModel.getmICMSMaterialHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSMaterialSW(quoteModel.getmICMSMaterialSW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSMaterialCS(quoteModel.getmICMSMaterialCS() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSMaterialMD(quoteModel.getmICMSMaterialMD() * quoteSelecionado.getmDollarRate());
			
			quoteModel.setmICMSEstadoOriginHW(quoteModel.getmICMSEstadoOriginHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSEstadoOriginSW(quoteModel.getmICMSEstadoOriginSW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSEstadoOriginCS(quoteModel.getmICMSEstadoOriginCS() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSEstadoOriginMD(quoteModel.getmICMSEstadoOriginMD() * quoteSelecionado.getmDollarRate());
			
			quoteModel.setmICMSEstadoDestinoHW(quoteModel.getmICMSEstadoDestinoHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSEstadoDestinoSW(quoteModel.getmICMSEstadoDestinoSW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSEstadoDestinoCS(quoteModel.getmICMSEstadoDestinoCS() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSEstadoDestinoMD(quoteModel.getmICMSEstadoDestinoMD() * quoteSelecionado.getmDollarRate());
			
			quoteModel.setmICMSInterEstadualHW(quoteModel.getmICMSInterEstadualHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSInterEstadualSW(quoteModel.getmICMSInterEstadualSW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSInterEstadualCS(quoteModel.getmICMSInterEstadualCS() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSInterEstadualMD(quoteModel.getmICMSInterEstadualMD() * quoteSelecionado.getmDollarRate());
			
			quoteModel.setmICMSinterestadualMatImportadoHW(quoteModel.getmICMSinterestadualMatImportadoHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSinterestadualMatImportadoSW(quoteModel.getmICMSinterestadualMatImportadoSW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSinterestadualMatImportadoCS(quoteModel.getmICMSinterestadualMatImportadoCS() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSinterestadualMatImportadoMD(quoteModel.getmICMSinterestadualMatImportadoMD() * quoteSelecionado.getmDollarRate());
			
			quoteModel.setmIPIHW(quoteModel.getmIPIHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmIPISW(quoteModel.getmIPISW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmIPICS(quoteModel.getmIPICS() * quoteSelecionado.getmDollarRate());
			quoteModel.setmIPIMD(quoteModel.getmIPIMD() * quoteSelecionado.getmDollarRate());
			
			quoteModel.setmISSHW(quoteModel.getmISSHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmISSSW(quoteModel.getmISSSW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmISSCS(quoteModel.getmISSCS() * quoteSelecionado.getmDollarRate());
			quoteModel.setmISSMD(quoteModel.getmISSMD() * quoteSelecionado.getmDollarRate());
			
			quoteModel.setmPISHW(quoteModel.getmPISHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmPISSW(quoteModel.getmPISSW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmPISCS(quoteModel.getmPISCS() * quoteSelecionado.getmDollarRate());
			quoteModel.setmPISMD(quoteModel.getmPISMD() * quoteSelecionado.getmDollarRate());
			
			quoteModel.setmCOFINSHW(quoteModel.getmCOFINSHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmCOFINSSW(quoteModel.getmCOFINSSW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmCOFINSCS(quoteModel.getmCOFINSCS() * quoteSelecionado.getmDollarRate());
			quoteModel.setmCOFINSMD(quoteModel.getmCOFINSMD() * quoteSelecionado.getmDollarRate());
			
			quoteModel.setmTotalTaxesHW(quoteModel.getmTotalTaxesHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmTotalTaxesSW(quoteModel.getmTotalTaxesSW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmTotalTaxesCS(quoteModel.getmTotalTaxesCS() * quoteSelecionado.getmDollarRate());
			quoteModel.setmTotalTaxesMD(quoteModel.getmTotalTaxesMD() * quoteSelecionado.getmDollarRate());
			
			quoteModel.setmICMSSTHW(quoteModel.getmICMSSTHW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSSTSW(quoteModel.getmICMSSTSW() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSSTCS(quoteModel.getmICMSSTCS() * quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSSTMD(quoteModel.getmICMSSTMD() * quoteSelecionado.getmDollarRate());
			
			for(QuoteItemTO item: quoteModel.getListaQuoteItemTO()){
				item.setmNetPrice(item.getmNetPrice() * quoteSelecionado.getmDollarRate());
				item.setmGrossPrice(item.getmGrossPrice() * quoteSelecionado.getmDollarRate());
				item.setmImportedCost(item.getmImportedCost() * quoteSelecionado.getmDollarRate());
				item.setmTotalTaxes(item.getmTotalTaxes() * quoteSelecionado.getmDollarRate());
				
				try{
					for(SomaTotaisTO to : item.getListaTotais()){
					
						to.setGrossTotal(to.getGrossTotal() * quoteSelecionado.getmDollarRate());
						to.setNetTotal(to.getNetTotal() * quoteSelecionado.getmDollarRate());
						to.setTotalTaxes(to.getTotalTaxes() * quoteSelecionado.getmDollarRate());					
						
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}else{
			currency = "US";
			
			
			quoteModel.setNetPriceHW(quoteModel.getNetPriceHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setNetPriceCS(quoteModel.getNetPriceCS() / quoteSelecionado.getmDollarRate());
			quoteModel.setNetPriceSW(quoteModel.getNetPriceSW() / quoteSelecionado.getmDollarRate());
			quoteModel.setNetPriceMD(quoteModel.getNetPriceMD() / quoteSelecionado.getmDollarRate());
			
			quoteModel.setGrossPriceHW(quoteModel.getGrossPriceHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setGrossPriceSW(quoteModel.getGrossPriceSW() / quoteSelecionado.getmDollarRate());
			quoteModel.setGrossPriceCS(quoteModel.getGrossPriceCS() / quoteSelecionado.getmDollarRate());
			quoteModel.setGrossPriceMD(quoteModel.getGrossPriceMD() / quoteSelecionado.getmDollarRate());
			
			
			quoteModel.setTaxesHW(quoteModel.getTaxesHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setTaxesSW(quoteModel.getTaxesSW() / quoteSelecionado.getmDollarRate());
			quoteModel.setTaxesCS(quoteModel.getTaxesCS() / quoteSelecionado.getmDollarRate());
			quoteModel.setTaxesMD(quoteModel.getTaxesMD() / quoteSelecionado.getmDollarRate());
			
			
			quoteModel.setmICMSMaterialHW(quoteModel.getmICMSMaterialHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSMaterialSW(quoteModel.getmICMSMaterialSW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSMaterialCS(quoteModel.getmICMSMaterialCS() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSMaterialMD(quoteModel.getmICMSMaterialMD() / quoteSelecionado.getmDollarRate());
			
			quoteModel.setmICMSEstadoOriginHW(quoteModel.getmICMSEstadoOriginHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSEstadoOriginSW(quoteModel.getmICMSEstadoOriginSW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSEstadoOriginCS(quoteModel.getmICMSEstadoOriginCS() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSEstadoOriginMD(quoteModel.getmICMSEstadoOriginMD() / quoteSelecionado.getmDollarRate());
			
			quoteModel.setmICMSEstadoDestinoHW(quoteModel.getmICMSEstadoDestinoHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSEstadoDestinoSW(quoteModel.getmICMSEstadoDestinoSW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSEstadoDestinoCS(quoteModel.getmICMSEstadoDestinoCS() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSEstadoDestinoMD(quoteModel.getmICMSEstadoDestinoMD() / quoteSelecionado.getmDollarRate());
			
			quoteModel.setmICMSInterEstadualHW(quoteModel.getmICMSInterEstadualHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSInterEstadualSW(quoteModel.getmICMSInterEstadualSW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSInterEstadualCS(quoteModel.getmICMSInterEstadualCS() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSInterEstadualMD(quoteModel.getmICMSInterEstadualMD() / quoteSelecionado.getmDollarRate());
			
			quoteModel.setmICMSinterestadualMatImportadoHW(quoteModel.getmICMSinterestadualMatImportadoHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSinterestadualMatImportadoSW(quoteModel.getmICMSinterestadualMatImportadoSW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSinterestadualMatImportadoCS(quoteModel.getmICMSinterestadualMatImportadoCS() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSinterestadualMatImportadoMD(quoteModel.getmICMSinterestadualMatImportadoMD() / quoteSelecionado.getmDollarRate());
			
			quoteModel.setmIPIHW(quoteModel.getmIPIHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmIPISW(quoteModel.getmIPISW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmIPICS(quoteModel.getmIPICS() / quoteSelecionado.getmDollarRate());
			quoteModel.setmIPIMD(quoteModel.getmIPIMD() / quoteSelecionado.getmDollarRate());
			
			quoteModel.setmISSHW(quoteModel.getmISSHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmISSSW(quoteModel.getmISSSW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmISSCS(quoteModel.getmISSCS() / quoteSelecionado.getmDollarRate());
			quoteModel.setmISSMD(quoteModel.getmISSMD() / quoteSelecionado.getmDollarRate());
			
			quoteModel.setmPISHW(quoteModel.getmPISHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmPISSW(quoteModel.getmPISSW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmPISCS(quoteModel.getmPISCS() / quoteSelecionado.getmDollarRate());
			quoteModel.setmPISMD(quoteModel.getmPISMD() / quoteSelecionado.getmDollarRate());
			
			quoteModel.setmCOFINSHW(quoteModel.getmCOFINSHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmCOFINSSW(quoteModel.getmCOFINSSW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmCOFINSCS(quoteModel.getmCOFINSCS() / quoteSelecionado.getmDollarRate());
			quoteModel.setmCOFINSMD(quoteModel.getmCOFINSMD() / quoteSelecionado.getmDollarRate());
			
			quoteModel.setmTotalTaxesHW(quoteModel.getmTotalTaxesHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmTotalTaxesSW(quoteModel.getmTotalTaxesSW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmTotalTaxesCS(quoteModel.getmTotalTaxesCS() / quoteSelecionado.getmDollarRate());
			quoteModel.setmTotalTaxesMD(quoteModel.getmTotalTaxesMD() / quoteSelecionado.getmDollarRate());
			
			quoteModel.setmICMSSTHW(quoteModel.getmICMSSTHW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSSTSW(quoteModel.getmICMSSTSW() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSSTCS(quoteModel.getmICMSSTCS() / quoteSelecionado.getmDollarRate());
			quoteModel.setmICMSSTMD(quoteModel.getmICMSSTMD() / quoteSelecionado.getmDollarRate());
			
			
			for(QuoteItemTO item: quoteModel.getListaQuoteItemTO()){
				item.setmNetPrice(item.getmNetPrice() / quoteSelecionado.getmDollarRate());
				item.setmGrossPrice(item.getmGrossPrice() / quoteSelecionado.getmDollarRate());
				item.setmImportedCost(item.getmImportedCost() / quoteSelecionado.getmDollarRate());
				item.setmTotalTaxes(item.getmTotalTaxes() / quoteSelecionado.getmDollarRate());
				
				try{
					for(SomaTotaisTO to : item.getListaTotais()){
					
						to.setGrossTotal(to.getGrossTotal() / quoteSelecionado.getmDollarRate());
						to.setNetTotal(to.getNetTotal() / quoteSelecionado.getmDollarRate());
						to.setTotalTaxes(to.getTotalTaxes() / quoteSelecionado.getmDollarRate());					
						
					}	
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}			
		}
	}
	
	public void verificaTaxDollar(){
		
		if(quoteSelecionado.getmDollarRate() != null){
			taxSelecionado = "1";
		}else{
			taxSelecionado = "";
		}
		
	}
	
	
	public void ajustaOrigemQuote(){
		
		if(!destination.equals("-1")){
			
			TbAdmDestination dest = destinationFacade.find(Long.valueOf(destination));
			
			originHW = String.valueOf(dest.getTbAdmOriginHw().getId());
			quoteSelecionado.setTbAdmOriginHw(dest.getTbAdmOriginHw());
			
			originSV = String.valueOf(dest.getTbAdmOriginSv().getId());
			quoteSelecionado.setTbAdmOriginSv(dest.getTbAdmOriginSv());
			
			originSW = String.valueOf(dest.getTbAdmOriginSw().getId());
			quoteSelecionado.setTbAdmOriginSw(dest.getTbAdmOriginSw());
			
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("tabDestination");
			
		}
		
	}
	
	public void exportToXLSOldVersion(){
		
		
		try{
			
			quoteModelVersion = new QuoteModel();
			
			quoteModelVersion.setfPartner(quoteVersion.isfPartner());
			quoteModelVersion.setBlnfContrEst(quoteVersion.isfContrest());
			quoteModelVersion.setBlnfSuframa(quoteVersion.isfSuframa());
			quoteModelVersion.setBlnfLeasing(quoteVersion.isfLeasing());
			quoteModelVersion.setClosedQuote(quoteVersion.getDtClose());
			quoteModelVersion.setReleaseQuote(quoteVersion.getDtRelease());
			quoteModelVersion.setfImportAll(quoteVersion.isfImportAll());
			quoteModelVersion.setfPPBDiscountClass(quoteVersion.isfPpbDiscountClass());
			quoteModelVersion.setmTotalAdditionalCost((quoteVersion.getmAdditionalCostFreight() != null ? quoteVersion.getmAdditionalCostFreight() : 0) + 
					(quoteVersion.getmAdditionalCostProjectCost() != null ? quoteVersion.getmAdditionalCostProjectCost() : 0) + (quoteVersion.getmAdditionalCostTraining() != null ? quoteVersion.getmAdditionalCostTraining() : 0) +
					(quoteVersion.getmAdditionalCostOtherCosts() != null ? quoteVersion.getmAdditionalCostOtherCosts() : 0));
			
			quoteModelVersion.setfPIS(quoteVersion.isfPIS());
			quoteModelVersion.setfCOFINS(quoteVersion.isfCOFINS());
			quoteModelVersion.setfICMS(quoteVersion.isfICMS());
			quoteModelVersion.setfIPI(quoteVersion.isfIPI());
			quoteModelVersion.setfISS(quoteVersion.isfISS());
			quoteModelVersion.setfSt(quoteVersion.isfSt());
						
			try{
				appQuote.biggestRateRecover(quoteModelVersion);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			quoteModelVersion.setListaQuoteItemTO(null);
			
			quoteModelVersion.setListaQuoteItemTO(appQuote.executeStoredProcedure(Integer.parseInt(String.valueOf(quoteVersion.getId()))));
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
				
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH:mm");
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<tbody>");
		sb.append("<tr >");
		sb.append("<td style='font-weight: bold;'>Quote Number - Version</td>");
		sb.append("<td></td>");
		sb.append("<td colspan='3' style='font-weight: bold;'>Customer</td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Quote Name </td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr >");
		sb.append("<td >"+quoteVersion.getsQuoteNumber()+" / "+quoteVersion.getiQuoteVersion()+"</span></td>");
		sb.append("<td></td>");
		sb.append("<td colspan='3'>"+quoteVersion.getsCustomer()+"</td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteVersion.getsQuoteName()+"</td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		
		sb.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<tbody>");
		sb.append("<tr >");
		sb.append("<td style='font-weight: bold;'>Sales Rep</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin HW</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin SW</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin SV</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin MT</td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td >"+quoteVersion.getTbSysUser().getsFullName()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteVersion.getTbAdmOriginHw().getsCode()+" - "+quoteVersion.getTbAdmOriginHw().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteVersion.getTbAdmOriginSw().getsCode()+" - "+quoteVersion.getTbAdmOriginSw().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteVersion.getTbAdmOriginSv().getsCode()+" - "+quoteVersion.getTbAdmOriginSv().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteVersion.getTbAdmOriginMt().getsCode()+" - "+quoteVersion.getTbAdmOriginMt().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		
		sb.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th style='font-weight: bold;'>Sales Destination</th>");
		sb.append("<th style='font-weight: bold;'>Sales Destination With Protocol</th>");
		sb.append("<th style='font-weight: bold;'>Suframa</th>");
		sb.append("<th style='font-weight: bold;'>Leasing</th>");
		sb.append("<th style='font-weight: bold;'>Regular State Taxpayer</th>");
		sb.append("<th style='font-weight: bold;'>Sale to Reseller</th>");
		sb.append("<td></td>");
		sb.append("<th style='font-weight: bold;'>Dollar Rate</th>");
		sb.append("<td></td>");
		sb.append("<th style='font-weight: bold;'>Currency</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		sb.append("<tr>");
		sb.append("<td>"+quoteVersion.getTbAdmDestination().getsCode()+"-"+quoteVersion.getTbAdmDestination().getsLocale()+"</td>");
		sb.append("<td></td>");//TODO: pegar Valor
		sb.append("<td>"+(quoteModelVersion.isBlnfSuframa() ? "Yes" : "No")+"</td>");
		sb.append("<td>"+(quoteModelVersion.isBlnfLeasing() ? "Yes" : "No")+"</td>");
		sb.append("<td>"+(quoteModelVersion.isBlnfContrEst() ? "Yes" : "No")+"</td>");
		sb.append("<td>"+(quoteModelVersion.isfPartner() ? "Yes" : "No")+"</td>");
		sb.append("<td></td>");
		sb.append("<td>"+new BigDecimal(quoteVersion.getmDollarRate()).setScale(4, RoundingMode.HALF_EVEN)+"</td>");
		sb.append("<td></td>");
		sb.append("<td>"+currency+"</td>");
		sb.append("</tr>");
		sb.append("</table>");
		
		sb.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>#</th>");
		//sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Quote Number</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Part Number</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Description</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>NCM</th>");
		//sb.append("<th style='font-weight: bold; background: #BEBCBC;'>STS</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ProductType</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Qty</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Imported Cost</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Net Price</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Total Taxes</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Gross Price</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		
			
		for(int i = 0; i < quoteModelVersion.getListaQuoteItemTO().size(); i++){
			
			
			sb.append("<tr valign='top'>");
			sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+quoteModelVersion.getListaQuoteItemTO().get(i).getsOrdem()+"</td>");
			//sb.append("<td>"+quoteModelVersion.getListaQuoteItemTO().get(i).getsGroupNumber()+"</td>");
			sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+quoteModelVersion.getListaQuoteItemTO().get(i).getsModel()+"</td>");
			sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+((quoteModelVersion.getListaQuoteItemTO().get(i).getsImpDescription() == null || quoteModelVersion.getListaQuoteItemTO().get(i).getsImpDescription().isEmpty()) ? quoteModelVersion.getListaQuoteItemTO().get(i).getsDescription() : quoteModelVersion.getListaQuoteItemTO().get(i).getsImpDescription())+"</td>");
			sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+(quoteModelVersion.getListaQuoteItemTO().get(i).getsNCM() != null ? quoteModelVersion.getListaQuoteItemTO().get(i).getsNCM() : quoteModelVersion.getListaQuoteItemTO().get(i).getsNCMMaterialClass())+"</td>");
			//sb.append("<td>-</td>");
			sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+ (quoteModelVersion.getListaQuoteItemTO().get(i).getsLabelProductType() == null ? "" : quoteModelVersion.getListaQuoteItemTO().get(i).getsLabelProductType()) +"</td>");
			sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+quoteModelVersion.getListaQuoteItemTO().get(i).getrQty()+"</td>");
			
			if(quoteModelVersion.getListaQuoteItemTO().get(i).getId() != quoteModelVersion.getListaQuoteItemTO().get(i).getIdTaxQuoteItem()){
				sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getmImportedCost())+"</td>");
				sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getmNetPrice())+"</td>");
				sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getmTotalTaxes())+"</td>");
				sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getmGrossPrice())+"</td>");
			}else{
				sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
				sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
				sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
				sb.append("<td style='"+quoteModelVersion.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
			}
			
			sb.append("</tr>");
			
			try{
				
				if(quoteModelVersion.getListaQuoteItemTO().get(i).getId() == quoteModelVersion.getListaQuoteItemTO().get(i).getIdTaxQuoteItem()){
					continue;
				}
				
				if(quoteModelVersion.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModelVersion.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 3){
					
									
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-style: italic; text-align: right;"+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-style: italic; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");				
				
				}else if(quoteModelVersion.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 3 && quoteModelVersion.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
					
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-style: italic; text-align: right; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-style: italic; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");	
					
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-weight: bold; text-align: right; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(1).getTexto()+":</td>");
					sb.append("<td style='font-weight: bold; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(1).getRevenue())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(1).getNetTotal())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(1).getTotalTaxes())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(1).getGrossTotal())+"</td>");
					sb.append("</tr>");	
					
				}else if(quoteModelVersion.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModelVersion.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
				
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-weight: bold; text-align: right; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-weight: bold; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");	
				
				}
				
			}catch(Exception e){
				
				if(quoteModelVersion.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 1){
				
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-weight: bold; text-align: right; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-weight: bold; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModelVersion.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");	
					
				}				
			}
		}
		
		sb.append("</table>");
		
		String arquivo = sb.toString();
		
		try{
			
			fileName = quoteVersion.getsQuoteNumber()+"_TaxReport";
			
			FileOutputStream fileOut = new FileOutputStream(fileName);  
			fileOut.write(arquivo.getBytes());  
			fileOut.flush();  
			fileOut.close();
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
    	    ExternalContext externalContext = facesContext.getExternalContext();
    	    externalContext.setResponseContentType("application/vnd.ms-excel");
    	    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\""+fileName+"_"+sdf.format(new Date())+".xls\"");
			
    	    OutputStream output = externalContext.getResponseOutputStream();  
    	    	  
	        //Escrevendo 
    	    output.write(arquivo.getBytes());
	    	 
	        //Para finalizar o processo
	        output.flush();
    	    output.close();
    	   
    	    RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('oldVersions').hide();");
    	    
    	    
		}catch(Exception e){
			e.printStackTrace();
		}
	
		
	}
	
	public void exporterToExcel(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH:mm");
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<tbody>");
		sb.append("<tr >");
		sb.append("<td style='font-weight: bold;'>Quote Number - Version</td>");
		sb.append("<td></td>");
		sb.append("<td colspan='3' style='font-weight: bold;'>Customer</td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Quote Name </td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr >");
		sb.append("<td >"+quoteSelecionado.getsQuoteNumber()+" / "+quoteSelecionado.getiQuoteVersion()+"</span></td>");
		sb.append("<td></td>");
		sb.append("<td colspan='3'>"+quoteSelecionado.getsCustomer()+"</td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getsQuoteName()+"</td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		
		sb.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<tbody>");
		sb.append("<tr >");
		sb.append("<td style='font-weight: bold;'>Sales Rep</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin HW</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin SW</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin SV</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin MT</td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td >"+quoteSelecionado.getTbSysUser().getsFullName()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getTbAdmOriginHw().getsCode()+" - "+quoteSelecionado.getTbAdmOriginHw().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getTbAdmOriginSw().getsCode()+" - "+quoteSelecionado.getTbAdmOriginSw().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getTbAdmOriginSv().getsCode()+" - "+quoteSelecionado.getTbAdmOriginSv().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getTbAdmOriginMt().getsCode()+" - "+quoteSelecionado.getTbAdmOriginMt().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		
		sb.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th style='font-weight: bold;'>Sales Destination</th>");
		sb.append("<th style='font-weight: bold;'>Sales Destination With Protocol</th>");
		sb.append("<th style='font-weight: bold;'>Suframa</th>");
		sb.append("<th style='font-weight: bold;'>Leasing</th>");
		sb.append("<th style='font-weight: bold;'>Regular State Taxpayer</th>");
		sb.append("<th style='font-weight: bold;'>Sale to Reseller</th>");
		sb.append("<td></td>");
		sb.append("<th style='font-weight: bold;'>Dollar Rate</th>");
		sb.append("<td></td>");
		sb.append("<th style='font-weight: bold;'>Currency</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		sb.append("<tr>");
		sb.append("<td>"+quoteSelecionado.getTbAdmDestination().getsCode()+"-"+quoteSelecionado.getTbAdmDestination().getsLocale()+"</td>");
		sb.append("<td></td>");//TODO: pegar Valor
		sb.append("<td>"+(quoteModel.isBlnfSuframa() ? "Yes" : "No")+"</td>");
		sb.append("<td>"+(quoteModel.isBlnfLeasing() ? "Yes" : "No")+"</td>");
		sb.append("<td>"+(quoteModel.isBlnfContrEst() ? "Yes" : "No")+"</td>");
		sb.append("<td>"+(quoteModel.isfPartner() ? "Yes" : "No")+"</td>");
		sb.append("<td></td>");
		sb.append("<td>"+new BigDecimal(quoteSelecionado.getmDollarRate()).setScale(4, RoundingMode.HALF_EVEN)+"</td>");
		sb.append("<td></td>");
		sb.append("<td>"+currency+"</td>");
		sb.append("</tr>");
		sb.append("</table>");
		
		sb.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>#</th>");
		//sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Quote Number</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Part Number</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Description</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>NCM</th>");
		//sb.append("<th style='font-weight: bold; background: #BEBCBC;'>STS</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ProductType</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Qty</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Imported Cost</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Net Price</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Total Taxes</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Gross Price</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		
			
		for(int i = 0; i < quoteModel.getListaQuoteItemTO().size(); i++){
			
			
			sb.append("<tr valign='top'>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getsOrdem()+"</td>");
			//sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getsGroupNumber()+"</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getsModel()+"</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+((quoteModel.getListaQuoteItemTO().get(i).getsImpDescription() == null || quoteModel.getListaQuoteItemTO().get(i).getsImpDescription().isEmpty()) ? quoteModel.getListaQuoteItemTO().get(i).getsDescription() : quoteModel.getListaQuoteItemTO().get(i).getsImpDescription())+"</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+(quoteModel.getListaQuoteItemTO().get(i).getsNCM() != null ? quoteModel.getListaQuoteItemTO().get(i).getsNCM() : quoteModel.getListaQuoteItemTO().get(i).getsNCMMaterialClass())+"</td>");
			//sb.append("<td>-</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+ (quoteModel.getListaQuoteItemTO().get(i).getsLabelProductType() == null ? "" : quoteModel.getListaQuoteItemTO().get(i).getsLabelProductType()) +"</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getrQty()+"</td>");
			
			if(quoteModel.getListaQuoteItemTO().get(i).getId() != quoteModel.getListaQuoteItemTO().get(i).getIdTaxQuoteItem()){
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmImportedCost())+"</td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmNetPrice())+"</td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes())+"</td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice())+"</td>");
			}else{
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
			}
			
			sb.append("</tr>");
			
			try{
				
				if(quoteModel.getListaQuoteItemTO().get(i).getId() == quoteModel.getListaQuoteItemTO().get(i).getIdTaxQuoteItem()){
					continue;
				}
				
				if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 3){
					
									
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-style: italic; text-align: right;"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");				
				
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 3 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
					
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-style: italic; text-align: right; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");	
					
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-weight: bold; text-align: right; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getTexto()+":</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getRevenue())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getNetTotal())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getTotalTaxes())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getGrossTotal())+"</td>");
					sb.append("</tr>");	
					
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
				
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-weight: bold; text-align: right; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");	
				
				}
				
			}catch(Exception e){
				
				if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 1){
				
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-weight: bold; text-align: right; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");	
					
				}				
			}
		}
		
		sb.append("</table>");
		
		String arquivo = sb.toString();
		
		try{
			
			fileName = quoteSelecionado.getsQuoteNumber()+"_TaxReport";
			
			FileOutputStream fileOut = new FileOutputStream(fileName);  
			fileOut.write(arquivo.getBytes());  
			fileOut.flush();  
			fileOut.close();
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
    	    ExternalContext externalContext = facesContext.getExternalContext();
    	    externalContext.setResponseContentType("application/vnd.ms-excel");
    	    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\""+fileName+"_"+sdf.format(new Date())+".xls\"");
			
    	    OutputStream output = externalContext.getResponseOutputStream();  
    	    	  
	        //Escrevendo 
    	    output.write(arquivo.getBytes());
	    	 
	        //Para finalizar o processo
	        output.flush();
    	    output.close();
    	   
    	    
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public void exporterToExcelFull(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH:mm");
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<tbody>");
		sb.append("<tr >");
		sb.append("<td style='font-weight: bold;'>Quote Number - Version</td>");
		sb.append("<td></td>");
		sb.append("<td colspan='3' style='font-weight: bold;'>Customer</td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Quote Name </td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr >");
		sb.append("<td >"+quoteSelecionado.getsQuoteNumber()+" / "+quoteSelecionado.getiQuoteVersion()+"</span></td>");
		sb.append("<td></td>");
		sb.append("<td colspan='3'>"+quoteSelecionado.getsCustomer()+"</td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getsQuoteName()+"</td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		
		sb.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<tbody>");
		sb.append("<tr >");
		sb.append("<td style='font-weight: bold;'>Sales Rep</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin HW</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin SW</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin SV</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin MT</td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td >"+quoteSelecionado.getTbSysUser().getsFullName()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getTbAdmOriginHw().getsCode()+" - "+quoteSelecionado.getTbAdmOriginHw().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getTbAdmOriginSw().getsCode()+" - "+quoteSelecionado.getTbAdmOriginSw().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getTbAdmOriginSv().getsCode()+" - "+quoteSelecionado.getTbAdmOriginSv().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getTbAdmOriginMt().getsCode()+" - "+quoteSelecionado.getTbAdmOriginMt().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		
		sb.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th style='font-weight: bold;'>Sales Destination</th>");
		sb.append("<th style='font-weight: bold;'>Sales Destination With Protocol</th>");
		sb.append("<th style='font-weight: bold;'>Suframa</th>");
		sb.append("<th style='font-weight: bold;'>Leasing</th>");
		sb.append("<th style='font-weight: bold;'>Regular State Taxpayer</th>");
		sb.append("<th style='font-weight: bold;'>Sale to Reseller</th>");
		sb.append("<td></td>");
		sb.append("<th style='font-weight: bold;'>Dollar Rate</th>");
		sb.append("<td></td>");
		sb.append("<th style='font-weight: bold;'>Currency</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		sb.append("<tr>");
		sb.append("<td>"+quoteSelecionado.getTbAdmDestination().getsCode()+"-"+quoteSelecionado.getTbAdmDestination().getsLocale()+"</td>");
		sb.append("<td></td>");//TODO: pegar Valor
		sb.append("<td>"+(quoteModel.isBlnfSuframa() ? "Yes" : "No")+"</td>");
		sb.append("<td>"+(quoteModel.isBlnfLeasing() ? "Yes" : "No")+"</td>");
		sb.append("<td>"+(quoteModel.isBlnfContrEst() ? "Yes" : "No")+"</td>");
		sb.append("<td>"+(quoteModel.isfPartner() ? "Yes" : "No")+"</td>");
		sb.append("<td></td>");
		sb.append("<td>"+new BigDecimal(quoteSelecionado.getmDollarRate()).setScale(4, RoundingMode.HALF_EVEN)+"</td>");
		sb.append("<td></td>");
		sb.append("<td>"+currency+"</td>");
		sb.append("</tr>");
		sb.append("</table>");
		
		sb.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>#</th>");
		//sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Quote Number</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Part Number</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Description</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>NCM</th>");
		//sb.append("<th style='font-weight: bold; background: #BEBCBC;'>STS</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ProductType</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Qty</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Imported Cost</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Net Price</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Total Taxes</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Gross Price</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		
			
		for(int i = 0; i < quoteModel.getListaQuoteItemTO().size(); i++){
			
			
			sb.append("<tr valign='top'>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getsOrdem()+"</td>");
			//sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getsGroupNumber()+"</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+(quoteModel.getListaQuoteItemTO().get(i).getsModel() == null ? "" : quoteModel.getListaQuoteItemTO().get(i).getsModel()) +"</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+((quoteModel.getListaQuoteItemTO().get(i).getsImpDescription() == null || quoteModel.getListaQuoteItemTO().get(i).getsImpDescription().isEmpty()) ? quoteModel.getListaQuoteItemTO().get(i).getsDescription() : quoteModel.getListaQuoteItemTO().get(i).getsImpDescription())+"</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+(quoteModel.getListaQuoteItemTO().get(i).getsNCMMaterialClassPai() == null ? (quoteModel.getListaQuoteItemTO().get(i).getsNCM() == null ? quoteModel.getListaQuoteItemTO().get(i).getsNCMMaterialClass() : quoteModel.getListaQuoteItemTO().get(i).getsNCM()) : quoteModel.getListaQuoteItemTO().get(i).getsNCMMaterialClass())+"</td>");
			//sb.append("<td>-</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+(quoteModel.getListaQuoteItemTO().get(i).getsLabelProductType() == null ? "" : quoteModel.getListaQuoteItemTO().get(i).getsLabelProductType())+"</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getrQty()+"</td>");
			
			if(quoteModel.getListaQuoteItemTO().get(i).getId() != quoteModel.getListaQuoteItemTO().get(i).getIdTaxQuoteItem()){
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmImportedCost())+"</td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmNetPrice())+"</td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes())+"</td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice())+"</td>");
			}else{
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
			}
			
			sb.append("</tr>");
			
			try{
				
				if(quoteModel.getListaQuoteItemTO().get(i).getId() == quoteModel.getListaQuoteItemTO().get(i).getIdTaxQuoteItem()){
					continue;
				}
				
				if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 3){
					
									
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-style: italic; text-align: right; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");				
				
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 3 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
					
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-style: italic; text-align: right; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");	
					
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-weight: bold; text-align: right; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getTexto()+":</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getRevenue())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getNetTotal())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getTotalTaxes())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getGrossTotal())+"</td>");
					sb.append("</tr>");	
					
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
				
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-weight: bold; text-align: right; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");	
				
				}
				
			}catch(Exception e){
				
				if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 1){
				
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-weight: bold; text-align: right; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");	
					
				}				
			}
		}
		
		sb.append("</table>");
		
		sb.append("<br/>");
		sb.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th style='font-weight: bold;'></th>");
		sb.append("<th style='font-weight: bold;'>HW</th>");
		sb.append("<th style='font-weight: bold;'>SW</th>");
		sb.append("<th style='font-weight: bold;'>CS</th>");
		sb.append("<th style='font-weight: bold;'>MD</th>");
		sb.append("<th style='font-weight: bold;'>Total</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>ICMS (Material)</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSMaterialHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSMaterialSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSMaterialCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSMaterialMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSMaterialTotal())+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>ICMS (Estado Origem)</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSEstadoOriginHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSEstadoOriginSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSEstadoOriginCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSEstadoOriginMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSEstadoOriginTotal())+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>ICMS (Estado Destino)</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSEstadoDestinoHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSEstadoDestinoSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSEstadoDestinoCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSEstadoDestinoMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSEstadoDestinoTotal())+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>ICMS (InterEstadual)</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSInterEstadualHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSInterEstadualSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSInterEstadualCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSInterEstadualMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSInterEstadualTotal())+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>ICMS (InterEstadual - Mat. imp.)</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSinterestadualMatImportadoHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSinterestadualMatImportadoSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSinterestadualMatImportadoCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSinterestadualMatImportadoMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSinterestadualMatImportadoTotal())+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>IPI</td>");
		sb.append("<td>"+df.format(quoteModel.getmIPIHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmIPISW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmIPICS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmIPIMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmIPITotal())+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>ISS</td>");
		sb.append("<td>"+df.format(quoteModel.getmISSHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmISSSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmISSCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmISSMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmISSTotal())+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>PIS</td>");
		sb.append("<td>"+df.format(quoteModel.getmPISHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmPISSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmPISCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmPISMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmPISTotal())+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>COFINS</td>");
		sb.append("<td>"+df.format(quoteModel.getmCOFINSHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmCOFINSSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmCOFINSCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmCOFINSMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmCOFINSTotal())+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>Total Taxes</td>");
		sb.append("<td>"+df.format(quoteModel.getmTotalTaxesHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmTotalTaxesSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmTotalTaxesCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmTotalTaxesMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmTotalTaxesTotal())+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>ICMS (ST)</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSSTHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSSTSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSSTCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSSTMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSSTTotal())+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>Net Price</td>");
		sb.append("<td>"+df.format(quoteModel.getNetPriceHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getNetPriceSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getNetPriceCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getNetPriceMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getrNetPrice())+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>Net Price</td>");
		sb.append("<td>"+df.format(quoteModel.getGrossPriceHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getGrossPriceSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getGrossPriceCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getGrossPriceMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getrGrossPrice())+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("</table>");
		
		String arquivo = sb.toString();
		
		try{
			
			fileName = quoteSelecionado.getsQuoteNumber()+"_TaxReportFull";
			
			FileOutputStream fileOut = new FileOutputStream(fileName);  
			fileOut.write(arquivo.getBytes());  
			fileOut.flush();  
			fileOut.close();
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
    	    ExternalContext externalContext = facesContext.getExternalContext();
    	    externalContext.setResponseContentType("application/vnd.ms-excel");
    	    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\""+fileName+"_"+sdf.format(new Date())+".xls\"");
			
    	    OutputStream output = externalContext.getResponseOutputStream();  
    	    	  
	        //Escrevendo 
    	    output.write(arquivo.getBytes());
	    	 
	        //Para finalizar o processo
	        output.flush();
    	    output.close();
    	   
    	    
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void exportEspelhoNota(){
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH:mm");
			
			DecimalFormat formatoBR = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));
			DecimalFormat formatoUS = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols (new Locale ("en", "US")));
			
			formatoBR.setMinimumFractionDigits(2);   
			formatoBR.setParseBigDecimal (true);
			
			formatoUS.setMinimumFractionDigits(2);
			formatoUS.setParseBigDecimal (true);
			
			BigDecimal accert;
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
			sb.append("<tbody>");
			
			sb.append("<tr >");
			sb.append("<td style='font-weight: bold;'>Quote Number - Version</td>");
			sb.append("<td >"+quoteSelecionado.getsQuoteNumber()+" / "+quoteSelecionado.getiQuoteVersion()+"</span></td>");
			sb.append("<td></td>");
			sb.append("<td style='font-weight: bold;'>Customer</td>");
			sb.append("<td>"+quoteSelecionado.getsCustomer()+"</td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			
			sb.append("<tr >");
			sb.append("<td style='font-weight: bold;'>Vendas</td>");
			
			if(quoteModel.isfPartner()){
				sb.append("<td style='font-weight: bold;'>Diretas</td>");
			}else{
				sb.append("<td style='font-weight: bold;'>Indiretas</td>");
			}
			
			sb.append("<td></td>");
			sb.append("<td style='font-weight: bold;'>Quote Name </td>");
			sb.append("<td >"+quoteSelecionado.getsQuoteName()+"</td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			
			sb.append("<tr>");
			sb.append("<td style='font-weight: bold;'>Estado Destino</td>");
			sb.append("<td >"+quoteSelecionado.getTbAdmOriginHw().getsCode()+" - "+quoteSelecionado.getTbAdmOriginHw().getsLocale()+"</td>");
			sb.append("<td></td>");
			sb.append("<td style='font-weight: bold;'>Tempo de Suporte</td>");
			sb.append("<td style='font-weight: bold;'></td>");
			sb.append("<td style='font-weight: bold;'> Moeda</td>");
			sb.append("<td>"+currency+"</td>");
			sb.append("</tr>");
			
			sb.append("</tbody>");
			sb.append("</table>");
			
			sb.append("<br/>");
			
			sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
			sb.append("<thead>");
			sb.append("<tr>");
			sb.append("<th style='font-weight: bold; '>PN c ST</th>");
			sb.append("<th style='font-weight: bold; '>NCM</th>");
			sb.append("<th style='font-weight: bold; '>Part# - Descrição</th>");
			sb.append("<th style='font-weight: bold; '>Qde</th>");
			sb.append("<th style='font-weight: bold; '>Valor Unitario</th>");
			sb.append("<th style='font-weight: bold; '>Valor Total</th>");
			sb.append("<th style='font-weight: bold;'>ICMS (%)</th>");
			sb.append("<th style='font-weight: bold; '>ICMS ($)</th>");
			sb.append("<th style='font-weight: bold; '>IPI (%)</th>");
			sb.append("<th style='font-weight: bold; '>IPI ($)</th>");
			sb.append("<th style='font-weight: bold; '>PIS (%)</th>");
			sb.append("<th style='font-weight: bold; '>PIS ($)</th>");
			sb.append("<th style='font-weight: bold; '>COFINS (%)</th>");
			sb.append("<th style='font-weight: bold; '>COFINS ($)</th>");
			sb.append("<th style='font-weight: bold; '>ISS (%)</th>");
			sb.append("<th style='font-weight: bold; '>ISS ($)</th>");
			sb.append("<th style='font-weight: bold; '>ST (%)</th>");
			sb.append("<th style='font-weight: bold; '>ICMS ST ($)</th>");
			sb.append("<th style='font-weight: bold; '>Fator (%)</th>");
			sb.append("<th style='font-weight: bold; '>Descontos</th>");
			sb.append("<th style='font-weight: bold; '>Valor Liquido</th>");
			sb.append("<th style='font-weight: bold; '>Impostos</th>");
			sb.append("<th style='font-weight: bold; '>Preco Final</th>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("</thead>");
			
			double valorUnitario = 0;
			double valorTotal = 0;
			double valorLiquido = 0;
			double impostos = 0;
			double precoFinal = 0 ;
			double icms = 0;
			double ipi = 0;
			double pis = 0;
			double iss = 0;
			double cofins = 0;
			double icmsst = 0;
			
			for(int i = 0; i < quoteModel.getListaQuoteItemTO().size(); i++){
										
				if(quoteModel.getListaQuoteItemTO().get(i).getfSubstituicaoTributariaSaidaMaterialClass() == 1 && quoteModel.isBlnfContrEst() &&
						quoteModel.getListaQuoteItemTO().get(i).getfDestinationWithProtocol() == 1 
						&& ((quoteModel.getListaQuoteItemTO().get(i).getrIVAMaterialImportadoMaterialClassDestination() > 0))){
					
					sb.append("<td style='text-align:left;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>S</td>");
					
				}else{
					sb.append("<td style='text-align:left;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>N</td>");
				}
				
				sb.append("<td style='text-align:left;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>'"+(quoteModel.getListaQuoteItemTO().get(i).getsNCMMaterialClassPai() != null ? quoteModel.getListaQuoteItemTO().get(i).getsNCMMaterialClassPai() : (quoteModel.getListaQuoteItemTO().get(i).getsNCM() != null ? quoteModel.getListaQuoteItemTO().get(i).getsNCM() : quoteModel.getListaQuoteItemTO().get(i).getsNCMMaterialClass() == null ? "" : quoteModel.getListaQuoteItemTO().get(i).getsNCMMaterialClass()))+"</td>");
				sb.append("<td style='text-align:left;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+(quoteModel.getListaQuoteItemTO().get(i).getsModel() == null ? "" : quoteModel.getListaQuoteItemTO().get(i).getsModel() + (quoteModel.getListaQuoteItemTO().get(i).getsImpDescription() != null ? " - "+quoteModel.getListaQuoteItemTO().get(i).getsImpDescription() : " - "+quoteModel.getListaQuoteItemTO().get(i).getsDescription()))+"</td>");
				sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getrQty()+"</td>");
				
				try{
					if(quoteModel.getListaQuoteItemTO().get(i).getrQty() > 0){
						valorUnitario += (quoteModel.getListaQuoteItemTO().get(i).getmImportedCost() / quoteModel.getListaQuoteItemTO().get(i).getrQty());
					}else{
						valorUnitario += 0D;
					}
				}catch(Exception e){
					valorUnitario += 0D;
				}
				
				if(currencyChange){
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format((quoteModel.getListaQuoteItemTO().get(i).getrQty() > 0 ? (quoteModel.getListaQuoteItemTO().get(i).getmImportedCost() / quoteModel.getListaQuoteItemTO().get(i).getrQty()) : 0D))+"</td>");
				}else{
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format((quoteModel.getListaQuoteItemTO().get(i).getrQty() > 0 ? (quoteModel.getListaQuoteItemTO().get(i).getmImportedCost() / quoteModel.getListaQuoteItemTO().get(i).getrQty()) : 0D))+"</td>");
				}
				
				valorTotal += (quoteModel.getListaQuoteItemTO().get(i).getmImportedCost());
				if(currencyChange){
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format((quoteModel.getListaQuoteItemTO().get(i).getmImportedCost()))+"</td>");
				}else{
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format((quoteModel.getListaQuoteItemTO().get(i).getmImportedCost()))+"</td>");
				}
				
				if(quoteModel.getListaQuoteItemTO().get(i).getiCalculationModel() == 1){
					Float ICMS = (float) (quoteModel.getListaQuoteItemTO().get(i).getrICMSInterEstadualDestination() * 100 );
					accert = new BigDecimal(ICMS).setScale(2, RoundingMode.HALF_EVEN);
					ICMS = accert.floatValue();
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+ICMS+"</td>");
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiCalculationModel() == 4){
					Float ICMS = (float) (quoteModel.getListaQuoteItemTO().get(i).getrICMSInterEstadualDestination() * 100 );
					accert = new BigDecimal(ICMS).setScale(2, RoundingMode.HALF_EVEN);
					ICMS = accert.floatValue();
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+ICMS+"</td>");
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiCalculationModel() == 6){
					Float ICMS = (float) (quoteModel.getListaQuoteItemTO().get(i).getrICMSEstadoDestinoDestination() * 100 );
					accert = new BigDecimal(ICMS).setScale(2, RoundingMode.HALF_EVEN);
					ICMS = accert.floatValue();
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+ICMS+"</td>");
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiCalculationModel() == 7){
					Float ICMS = (float) (quoteModel.getListaQuoteItemTO().get(i).getrICMSMaterialClassMaterialClass() * 100 );
					accert = new BigDecimal(ICMS).setScale(2, RoundingMode.HALF_EVEN);
					ICMS = accert.floatValue();
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+ICMS+"</td>");
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiCalculationModel() == 9){
					Float ICMS = (float) (quoteModel.getListaQuoteItemTO().get(i).getrICMSInterEstadualDestination() * 100 );
					accert = new BigDecimal(ICMS).setScale(2, RoundingMode.HALF_EVEN);
					ICMS = accert.floatValue();
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+ICMS+"</td>");
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiCalculationModel() == 11){
					Float ICMS = (float) (quoteModel.getListaQuoteItemTO().get(i).getrICMSInternoOrigemOrigin() * 100 );
					accert = new BigDecimal(ICMS).setScale(2, RoundingMode.HALF_EVEN);
					ICMS = accert.floatValue();
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+ICMS+"</td>");
				}else{
					Float ICMS = (float) (quoteModel.getListaQuoteItemTO().get(i).getrICMSEstadoDestinoDestination() * 100 );
					accert = new BigDecimal(ICMS).setScale(2, RoundingMode.HALF_EVEN);
					ICMS = accert.floatValue();
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+ICMS+"</td>");
				}
				
				if(quoteModel.getListaQuoteItemTO().get(i).getiCalculationModel() == 1){
					icms += quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoDestino();
					if(currencyChange){
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoDestino())+"</td>");
					}else{
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoDestino())+"</td>");
					}
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiCalculationModel() == 4){
					icms += quoteModel.getListaQuoteItemTO().get(i).getmICMSInterEstadual();
					if(currencyChange){
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSInterEstadual())+"</td>");
					}else{
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSInterEstadual())+"</td>");
					}
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiCalculationModel() == 6){
					icms += quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoDestino();
					if(currencyChange){
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoDestino())+"</td>");
					}else{
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoDestino())+"</td>");
					}
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiCalculationModel() == 7){
					icms += quoteModel.getListaQuoteItemTO().get(i).getmICMSMaterialClass();
					if(currencyChange){
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSMaterialClass())+"</td>");
					}else{
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSMaterialClass())+"</td>");
					}
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiCalculationModel() == 9){
					icms += quoteModel.getListaQuoteItemTO().get(i).getmICMSInterEstadual();
					if(currencyChange){
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSInterEstadual())+"</td>");
					}else{
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSInterEstadual())+"</td>");
					}
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiCalculationModel() == 11){
					icms += quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoDestino();
					if(currencyChange){
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoDestino())+"</td>");
					}else{
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoDestino())+"</td>");
					}
				}else{
					icms += quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoDestino();
					if(currencyChange){
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoDestino())+"</td>");
					}else{
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoDestino())+"</td>");
					}
				}
				
				Float IPI = (float) (quoteModel.getListaQuoteItemTO().get(i).getrIPIMaterialClass() * 100 );
				accert = new BigDecimal(IPI).setScale(2, RoundingMode.HALF_EVEN);
				IPI = accert.floatValue();
				sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+IPI+"</td>");
				
				ipi += quoteModel.getListaQuoteItemTO().get(i).getmIPI();
				if(currencyChange){
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmIPI())+"</td>");
				}else{
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmIPI())+"</td>");
				}
				
				Float PIS = (float) (quoteModel.getListaQuoteItemTO().get(i).getrPISProductType() * 100 );
				accert = new BigDecimal(PIS).setScale(2, RoundingMode.HALF_EVEN);
				PIS = accert.floatValue();				
				sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+PIS+"</td>");
				
				pis +=quoteModel.getListaQuoteItemTO().get(i).getmPIS();
				if(currencyChange){
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmPIS())+"</td>");
				}else{
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmPIS())+"</td>");
				}
				
				Float COFINS = (float) (quoteModel.getListaQuoteItemTO().get(i).getrCOFINSProductType() * 100 );
				accert = new BigDecimal(COFINS).setScale(2, RoundingMode.HALF_EVEN);
				COFINS = accert.floatValue();
				sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+COFINS+"</td>");
				
				cofins += quoteModel.getListaQuoteItemTO().get(i).getmCOFINS();
				if(currencyChange){
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmCOFINS())+"</td>");
				}else{
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmCOFINS())+"</td>");
				}
				
				Float ISS = (float) (quoteModel.getListaQuoteItemTO().get(i).getrISSOrigin() * 100 );
				accert = new BigDecimal(ISS).setScale(2, RoundingMode.HALF_EVEN);
				ISS = accert.floatValue();
				sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+ISS+"</td>");
				
				iss += quoteModel.getListaQuoteItemTO().get(i).getmISS();
				if(currencyChange){
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmISS())+"</td>");
				}else{
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmISS())+"</td>");
				}
				
								
				if(quoteModel.getListaQuoteItemTO().get(i).getfSubstituicaoTributariaSaidaMaterialClass() == 1 && quoteModel.isBlnfContrEst() &&
						quoteModel.getListaQuoteItemTO().get(i).getfDestinationWithProtocol() == 1 
						&& ((quoteModel.getListaQuoteItemTO().get(i).getrIVAMaterialImportadoMaterialClassDestination() > 0))){
					
					try{
						Float IVA = (float) (quoteModel.getListaQuoteItemTO().get(i).getrIVAMaterialImportadoMaterialClassDestination() * 100 );
						accert = new BigDecimal(IVA).setScale(2, RoundingMode.HALF_EVEN);
						IVA = accert.floatValue();
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+IVA+"</td>");
					}catch(Exception e){
						sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'></td>");
					}
				}else{
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'></td>");
				}
				
				icmsst += quoteModel.getListaQuoteItemTO().get(i).getmICMSST();
				if(currencyChange){
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSST())+"</td>");
				}else{
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSST())+"</td>");
				}
				
				
				Float Fator = (float) (quoteModel.getListaQuoteItemTO().get(i).getrFatorSaida() * 100 );
				accert = new BigDecimal(Fator).setScale(2, RoundingMode.HALF_EVEN);
				Fator = accert.floatValue();
				sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+Fator+"</td>");
				
				//sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'></td>"); //TODO: William Verificar
				
				Float discount = (float) (quoteModel.getListaQuoteItemTO().get(i).getrDiscount());
				accert = new BigDecimal(discount).setScale(2, RoundingMode.HALF_EVEN);
				discount = accert.floatValue();
				sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+discount+"</td>");
				
				valorLiquido += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
				impostos += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
				precoFinal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
				if(currencyChange){
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmNetPrice())+"</td>");
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes())+"</td>");
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoBR.format(quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice())+"</td>");
				}else{
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmNetPrice())+"</td>");
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes())+"</td>");
					sb.append("<td style='text-align:right;"+quoteModel.getListaQuoteItemTO().get(i).getStyle()+"'>"+formatoUS.format(quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice())+"</td>");
				}
				
				sb.append("<td></td>");
				
				sb.append("</tr>");
			}
			
			sb.append("</table>");
			sb.append("<table>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td style='font-weight: bold; text-align:right;'>Total</td>");
			sb.append("<td></td>");
			
			if(currencyChange){
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoBR.format(valorUnitario)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoBR.format(valorTotal)+"</td>");
			}else{
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoUS.format(valorUnitario)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoUS.format(valorTotal)+"</td>");
			}
			
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			
			if(currencyChange){
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoBR.format(valorLiquido)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoBR.format(impostos)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoBR.format(precoFinal)+"</td>");
				sb.append("<td></td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoBR.format(pis)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoBR.format(cofins)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoBR.format(iss)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoBR.format(ipi)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoBR.format(icms)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoBR.format(icmsst)+"</td>");
			}else{
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoUS.format(valorLiquido)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoUS.format(impostos)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoUS.format(precoFinal)+"</td>");
				sb.append("<td></td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoUS.format(pis)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoUS.format(cofins)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoUS.format(iss)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoUS.format(ipi)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoUS.format(icms)+"</td>");
				sb.append("<td style='font-weight: bold; text-align:right;'>"+formatoUS.format(icmsst)+"</td>");
			}
			
			sb.append("</tr>");
			sb.append("</table>");
			
			sb.append("<br/>");
			sb.append("<br/>");
			
			sb.append("<table>");
			sb.append("<thead>");
			sb.append("<tr>");
			sb.append("<th style='font-weight: bold;'>Summary</th>");
			sb.append("<th style='font-weight: bold;'>Valor Liquido</th>");
			sb.append("<th style='font-weight: bold;'>Impostos</th>");
			sb.append("<th style='font-weight: bold;'>Preco Final</th>");
			sb.append("</tr>");
			sb.append("</thead>");
			sb.append("<tr>");
			sb.append("<td style='font-weight: bold;'>Hardware Fees</td>");
			
			if(currencyChange){
				sb.append("<td style='text-align: right;'>"+formatoBR.format(quoteModel.getNetPriceHW())+"</td>");
				sb.append("<td style='text-align: right;'>"+formatoBR.format(quoteModel.getTaxesHW())+"</td>");
				sb.append("<td style='text-align: right; font-weight: bold;'>"+formatoBR.format(quoteModel.getGrossPriceHW())+"</td>");
			}else{
				sb.append("<td style='text-align: right;'>"+formatoUS.format(quoteModel.getNetPriceHW())+"</td>");
				sb.append("<td style='text-align: right;'>"+formatoUS.format(quoteModel.getTaxesHW())+"</td>");
				sb.append("<td style='text-align: right; font-weight: bold;'>"+formatoUS.format(quoteModel.getGrossPriceHW())+"</td>");
			}
			
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td style='font-weight: bold;'>Software Fees</td>");
			
			if(quoteModel.getNetPriceSW() != 0D){
				if(currencyChange){
					sb.append("<td style='text-align: right;'>"+formatoBR.format(quoteModel.getNetPriceSW())+"</td>");
					sb.append("<td style='text-align: right;'>"+formatoBR.format(quoteModel.getTaxesSW())+"</td>");
					sb.append("<td style='text-align: right; font-weight: bold;'>"+formatoBR.format(quoteModel.getGrossPriceSW())+"</td>");
				}else{
					sb.append("<td style='text-align: right;'>"+formatoUS.format(quoteModel.getNetPriceSW())+"</td>");
					sb.append("<td style='text-align: right;'>"+formatoUS.format(quoteModel.getTaxesSW())+"</td>");
					sb.append("<td style=' text-align: right;font-weight: bold;'>"+formatoUS.format(quoteModel.getGrossPriceSW())+"</td>");
				}
			}else{
				sb.append("<td style='text-align: right;'>0.00</td>");
				sb.append("<td style='text-align: right;'>0.00</td>");
				sb.append("<td style='text-align: right; font-weight: bold;'>0.00</td>");
			}
			
			sb.append("</tr>");
			sb.append("<tr>");
			
			sb.append("<td style='font-weight: bold;'>Service Fees</td>");
			
			if(quoteModel.getNetPriceCS() != 0D){
				if(currencyChange){
					sb.append("<td style='text-align: right;'>"+formatoBR.format(quoteModel.getNetPriceCS())+"</td>");
					sb.append("<td style='text-align: right;'>"+formatoBR.format(quoteModel.getTaxesCS())+"</td>");
					sb.append("<td style='text-align: right; font-weight: bold;'>"+formatoBR.format(quoteModel.getGrossPriceCS())+"</td>");
				}else{
					sb.append("<td style='text-align: right;'>"+formatoUS.format(quoteModel.getNetPriceCS())+"</td>");
					sb.append("<td style='text-align: right;'>"+formatoUS.format(quoteModel.getTaxesCS())+"</td>");
					sb.append("<td style='text-align: right; font-weight: bold;'>"+formatoUS.format(quoteModel.getGrossPriceCS())+"</td>");
				}
			}else{
				sb.append("<td style='text-align: right;'>0.00</td>");
				sb.append("<td style='text-align: right;'>0.00</td>");
				sb.append("<td style='text-align: right; font-weight: bold;'>0.00</td>");
			}
			
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td style='font-weight: bold;'>Total</td>");
			
			if(currencyChange){
				sb.append("<td style='text-align: right; font-weight: bold;'>"+formatoBR.format((quoteModel.getNetPriceSW() + quoteModel.getNetPriceHW() + quoteModel.getNetPriceCS()))+"</td>");
				sb.append("<td style='text-align: right; font-weight: bold;'>"+formatoBR.format((quoteModel.getTaxesSW() + quoteModel.getTaxesHW() + quoteModel.getTaxesCS()))+"</td>");
				sb.append("<td style='text-align: right; font-weight: bold;'>"+formatoBR.format((quoteModel.getGrossPriceSW() + quoteModel.getGrossPriceHW() + quoteModel.getGrossPriceCS()))+"</td>");
			}else{
				sb.append("<td style='text-align: right; font-weight: bold;'>"+formatoUS.format((quoteModel.getNetPriceSW() + quoteModel.getNetPriceHW() + quoteModel.getNetPriceCS()))+"</td>");
				sb.append("<td style='text-align: right; font-weight: bold;'>"+formatoUS.format((quoteModel.getTaxesSW() + quoteModel.getTaxesHW() + quoteModel.getTaxesCS()))+"</td>");
				sb.append("<td style='text-align: right; font-weight: bold;'>"+formatoUS.format((quoteModel.getGrossPriceSW() + quoteModel.getGrossPriceHW() + quoteModel.getGrossPriceCS()))+"</td>");
			}
			
			sb.append("</tr>");
			sb.append("</table>");
			
			String arquivo = sb.toString();
			
			try{
				
				fileName = quoteSelecionado.getsQuoteNumber()+"_EspelhoNota";
				
				FileOutputStream fileOut = new FileOutputStream(fileName);  
				fileOut.write(arquivo.getBytes());  
				fileOut.flush();  
				fileOut.close();
				
				FacesContext facesContext = FacesContext.getCurrentInstance();
			    ExternalContext externalContext = facesContext.getExternalContext();
			    externalContext.setResponseContentType("application/vnd.ms-excel");
			    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\""+fileName+"_"+sdf.format(new Date())+".xls\"");
				
			    OutputStream output = externalContext.getResponseOutputStream();  
			    	  
		        //Escrevendo 
			    output.write(arquivo.getBytes());
		    	 
		        //Para finalizar o processo
		        output.flush();
			    output.close();
			    
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	
	public void writeExcelReport(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH:mm");
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<tbody>");
		sb.append("<tr >");
		sb.append("<td style='font-weight: bold;'>Quote Number - Version</td>");
		sb.append("<td></td>");
		sb.append("<td colspan='3' style='font-weight: bold;'>Customer</td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Quote Name </td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr >");
		sb.append("<td >"+quoteSelecionado.getsQuoteNumber()+" / "+quoteSelecionado.getiQuoteVersion()+"</span></td>");
		sb.append("<td></td>");
		sb.append("<td colspan='3'>"+quoteSelecionado.getsCustomer()+"</td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getsQuoteName()+"</td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		
		sb.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<tbody>");
		sb.append("<tr >");
		sb.append("<td style='font-weight: bold;'>Sales Rep</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin HW</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin SW</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin SV</td>");
		sb.append("<td></td>");
		sb.append("<td style='font-weight: bold;'>Sale Origin MT</td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td >"+quoteSelecionado.getTbSysUser().getsFullName()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getTbAdmOriginHw().getsCode()+" - "+quoteSelecionado.getTbAdmOriginHw().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getTbAdmOriginSw().getsCode()+" - "+quoteSelecionado.getTbAdmOriginSw().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getTbAdmOriginSv().getsCode()+" - "+quoteSelecionado.getTbAdmOriginSv().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("<td >"+quoteSelecionado.getTbAdmOriginMt().getsCode()+" - "+quoteSelecionado.getTbAdmOriginMt().getsLocale()+"</td>");
		sb.append("<td></td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		
		sb.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th style='font-weight: bold;'>Sales Destination</th>");
		sb.append("<th style='font-weight: bold;'>Sales Destination With Protocol</th>");
		sb.append("<th style='font-weight: bold;'>Suframa</th>");
		sb.append("<th style='font-weight: bold;'>Leasing</th>");
		sb.append("<th style='font-weight: bold;'>Regular State Taxpayer</th>");
		sb.append("<th style='font-weight: bold;'>Sale to Reseller</th>");
		sb.append("<td></td>");
		sb.append("<th style='font-weight: bold;'>Dollar Rate</th>");
		sb.append("<td></td>");
		sb.append("<th style='font-weight: bold;'>Currency</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		sb.append("<tr>");
		sb.append("<td>"+quoteSelecionado.getTbAdmDestination().getsCode()+"-"+quoteSelecionado.getTbAdmDestination().getsLocale()+"</td>");
		sb.append("<td></td>");//TODO: pegar Valor
		sb.append("<td>"+(quoteModel.isBlnfSuframa() ? "Yes" : "No")+"</td>");
		sb.append("<td>"+(quoteModel.isBlnfLeasing() ? "Yes" : "No")+"</td>");
		sb.append("<td>"+(quoteModel.isBlnfContrEst() ? "Yes" : "No")+"</td>");
		sb.append("<td>"+(quoteModel.isfPartner() ? "Yes" : "No")+"</td>");
		sb.append("<td></td>");
		sb.append("<td>"+new BigDecimal(quoteSelecionado.getmDollarRate()).setScale(4, RoundingMode.HALF_EVEN)+"</td>");
		sb.append("<td></td>");
		sb.append("<td>"+currency+"</td>");
		sb.append("</tr>");
		sb.append("</table>");
		
		sb.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>#</th>");
		//sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Quote Number</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Part Number</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Description</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>NCM</th>");
		//sb.append("<th style='font-weight: bold; background: #BEBCBC;'>STS</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ProductType</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Qty</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Imported Cost</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Net Price</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Total Taxes</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Gross Price</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		
			
		for(int i = 0; i < quoteModel.getListaQuoteItemTO().size(); i++){
			
			
			sb.append("<tr valign='top'>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"' >"+quoteModel.getListaQuoteItemTO().get(i).getsOrdem()+"</td>");
			//sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getsGroupNumber()+"</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+(quoteModel.getListaQuoteItemTO().get(i).getsModel() == null ? "" : quoteModel.getListaQuoteItemTO().get(i).getsModel())+"</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+((quoteModel.getListaQuoteItemTO().get(i).getsImpDescription() == null || quoteModel.getListaQuoteItemTO().get(i).getsImpDescription().isEmpty()) ? quoteModel.getListaQuoteItemTO().get(i).getsDescription() : quoteModel.getListaQuoteItemTO().get(i).getsImpDescription())+"</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+(quoteModel.getListaQuoteItemTO().get(i).getsNCM() != null ? quoteModel.getListaQuoteItemTO().get(i).getsNCM() : quoteModel.getListaQuoteItemTO().get(i).getsNCMMaterialClass())+"</td>");
			//sb.append("<td>-</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+(quoteModel.getListaQuoteItemTO().get(i).getsLabelProductType() == null ? "" : quoteModel.getListaQuoteItemTO().get(i).getsLabelProductType())+"</td>");
			sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getrQty()+"</td>");
			
			if(quoteModel.getListaQuoteItemTO().get(i).getId() != quoteModel.getListaQuoteItemTO().get(i).getIdTaxQuoteItem()){
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmImportedCost())+"</td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmNetPrice())+"</td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes())+"</td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice())+"</td>");
			}else{
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
				sb.append("<td style='"+quoteModel.getListaQuoteItemTO().get(i).getStyleTd()+"'></td>");
			}
			sb.append("</tr>");
			
			try{
				
				if(quoteModel.getListaQuoteItemTO().get(i).getId() == quoteModel.getListaQuoteItemTO().get(i).getIdTaxQuoteItem()){
					continue;
				}
				
				if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 3){
					
									
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");				
				
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 3 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
					
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-style: italic; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");	
					
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getTexto()+":</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getRevenue())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getNetTotal())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getTotalTaxes())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getGrossTotal())+"</td>");
					sb.append("</tr>");	
					
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
				
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");	
				
				}
				
			}catch(Exception e){
				
				if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 1){
				
					sb.append("<tr>");
					sb.append("<td colspan='6' style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
					sb.append("<td style='font-weight: bold; "+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getStyle()+"'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
					sb.append("</tr>");	
					
				}				
			}
		}
		
		sb.append("<tr>");
		sb.append("</tr>");
		
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold; '>Total Net:</td>");
		sb.append("<td style='font-weight: bold; '></td>");
		sb.append("<td style='font-weight: bold; '>"+df.format(quoteModel.getrNetPrice())+"</td>");
		sb.append("</tr>");	
		
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold; '>Total Taxes:</td>");
		sb.append("<td style='font-weight: bold; '></td>");
		sb.append("<td style='font-weight: bold; '>"+df.format(quoteModel.getmTotalTaxesTotal())+"</td>");
		sb.append("</tr>");	
		
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold;'></td>");
		sb.append("<td style='font-weight: bold; '>Total Gross:</td>");
		sb.append("<td style='font-weight: bold; '></td>");
		sb.append("<td style='font-weight: bold; '>"+df.format(quoteModel.getrGrossPrice())+"</td>");
		sb.append("</tr>");	
		
		sb.append("</table>");
		
		sb.append("<br/>");
		
		sb.append("<h1> Quote - Total</h1>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'></th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>HW</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>SW</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>CS</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>MD</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Total</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>Net Price:</td>");
		sb.append("<td>"+df.format(quoteModel.getNetPriceHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getNetPriceSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getNetPriceCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getNetPriceMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getrNetPrice())+"</td>");
		sb.append("</tr>");
		
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>ICMS ST:</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSSTHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSSTSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSSTCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSSTMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getmICMSSTTotal())+"</td>");
		sb.append("</tr>");
		
		sb.append("<tr>");
		sb.append("<td style='font-weight: bold;'>Gross Price:</td>");
		sb.append("<td>"+df.format(quoteModel.getGrossPriceHW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getGrossPriceSW())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getGrossPriceCS())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getGrossPriceMD())+"</td>");
		sb.append("<td>"+df.format(quoteModel.getrGrossPrice())+"</td>");
		sb.append("</tr>");
		
		sb.append("</table>");
		
		
		String arquivo = sb.toString();
		
		try{
			
			fileName = quoteSelecionado.getsQuoteNumber()+"_TaxesAnalysis";
			
			FileOutputStream fileOut = new FileOutputStream(fileName);  
			fileOut.write(arquivo.getBytes());  
			fileOut.flush();  
			fileOut.close();
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    ExternalContext externalContext = facesContext.getExternalContext();
		    externalContext.setResponseContentType("application/vnd.ms-excel");
		    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\""+fileName+"_"+sdf.format(new Date())+".xls\"");
			
		    OutputStream output = externalContext.getResponseOutputStream();  
		    	  
	        //Escrevendo 
		    output.write(arquivo.getBytes());
	    	 
	        //Para finalizar o processo
	        output.flush();
		    output.close();
		    
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void writeExcelReportCalc(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH:mm");
		
		StringBuilder cab = new StringBuilder();
		
		StringBuilder sb = new StringBuilder();
		
		cab.append("<table border='1' cellpadding='3' cellspacing='0'>");
		cab.append("<tbody>");
		cab.append("<tr >");
		cab.append("<td style='font-weight: bold;'>Quote Number - Version</td>");
		cab.append("<td></td>");
		cab.append("<td colspan='3' style='font-weight: bold;'>Customer</td>");
		cab.append("<td></td>");
		cab.append("<td></td>");
		cab.append("<td style='font-weight: bold;'>Quote Name </td>");
		cab.append("<td></td>");
		cab.append("<td></td>");
		cab.append("</tr>");
		cab.append("<tr >");
		cab.append("<td >"+quoteSelecionado.getsQuoteNumber()+" / "+quoteSelecionado.getiQuoteVersion()+"</span></td>");
		cab.append("<td></td>");
		cab.append("<td colspan='3'>"+quoteSelecionado.getsCustomer()+"</td>");
		cab.append("<td></td>");
		cab.append("<td></td>");
		cab.append("<td >"+quoteSelecionado.getsQuoteName()+"</td>");
		cab.append("<td></td>");
		cab.append("<td></td>");
		cab.append("</tr>");
		cab.append("</tbody>");
		cab.append("</table>");
		
		cab.append("<br/>");
		
		cab.append("<table border='1' cellpadding='3' cellspacing='0'>");
		cab.append("<tbody>");
		cab.append("<tr >");
		cab.append("<td style='font-weight: bold;'>Sales Rep</td>");
		cab.append("<td></td>");
		cab.append("<td style='font-weight: bold;'>Sale Origin HW</td>");
		cab.append("<td></td>");
		cab.append("<td style='font-weight: bold;'>Sale Origin SW</td>");
		cab.append("<td></td>");
		cab.append("<td style='font-weight: bold;'>Sale Origin SV</td>");
		cab.append("<td></td>");
		cab.append("<td style='font-weight: bold;'>Sale Origin MT</td>");
		cab.append("<td></td>");
		cab.append("</tr>");
		cab.append("<tr>");
		cab.append("<td >"+quoteSelecionado.getTbSysUser().getsFullName()+"</td>");
		cab.append("<td></td>");
		cab.append("<td >"+quoteSelecionado.getTbAdmOriginHw().getsCode()+" - "+quoteSelecionado.getTbAdmOriginHw().getsLocale()+"</td>");
		cab.append("<td></td>");
		cab.append("<td >"+quoteSelecionado.getTbAdmOriginSw().getsCode()+" - "+quoteSelecionado.getTbAdmOriginSw().getsLocale()+"</td>");
		cab.append("<td></td>");
		cab.append("<td >"+quoteSelecionado.getTbAdmOriginSv().getsCode()+" - "+quoteSelecionado.getTbAdmOriginSv().getsLocale()+"</td>");
		cab.append("<td></td>");
		cab.append("<td >"+quoteSelecionado.getTbAdmOriginMt().getsCode()+" - "+quoteSelecionado.getTbAdmOriginMt().getsLocale()+"</td>");
		cab.append("<td></td>");
		cab.append("</tr>");
		cab.append("</tbody>");
		cab.append("</table>");
		
		cab.append("<br/>");
		
		cab.append("<table border='1' cellpadding='3' cellspacing='0'>");
		cab.append("<thead>");
		cab.append("<tr>");
		cab.append("<th style='font-weight: bold;'>Sales Destination</th>");
		cab.append("<th style='font-weight: bold;'>Sales Destination With Protocol</th>");
		cab.append("<th style='font-weight: bold;'>Suframa</th>");
		cab.append("<th style='font-weight: bold;'>Leasing</th>");
		cab.append("<th style='font-weight: bold;'>Regular State Taxpayer</th>");
		cab.append("<th style='font-weight: bold;'>Sale to Reseller</th>");
		cab.append("<td></td>");
		cab.append("<th style='font-weight: bold;'>Dollar Rate</th>");
		cab.append("<td></td>");
		cab.append("<th style='font-weight: bold;'>Currency</th>");
		cab.append("</tr>");
		cab.append("</thead>");
		cab.append("<tr>");
		cab.append("<td>"+quoteSelecionado.getTbAdmDestination().getsCode()+"-"+quoteSelecionado.getTbAdmDestination().getsLocale()+"</td>");
		cab.append("<td></td>");//TODO: pegar Valor
		cab.append("<td>"+(quoteModel.isBlnfSuframa() ? "Yes" : "No")+"</td>");
		cab.append("<td>"+(quoteModel.isBlnfLeasing() ? "Yes" : "No")+"</td>");
		cab.append("<td>"+(quoteModel.isBlnfContrEst() ? "Yes" : "No")+"</td>");
		cab.append("<td>"+(quoteModel.isfPartner() ? "Yes" : "No")+"</td>");
		cab.append("<td></td>");
		cab.append("<td>"+new BigDecimal(quoteSelecionado.getmDollarRate()).setScale(4, RoundingMode.HALF_EVEN)+"</td>");
		cab.append("<td></td>");
		cab.append("<td>"+currency+"</td>");
		cab.append("</tr>");
		cab.append("</table>");
		
		cab.append("<br/>");
		
		sb.append("<table border='1' cellpadding='3' cellspacing='0'>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>#</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Avaliable</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ST - Saída</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ST - Entrada</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ProductType</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>NCM</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Part Number</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Description</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Qty</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Import List Price</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Imported Cost</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Suggested Margin</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Net Price</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Suggested Margin</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Discount</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>(%) ICMS Interno</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>(%) ICMS Estado Origem</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>(%) ICMS Estado Origem Interno</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>(%) ICMS Estado Destino</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>(%) ICMS Material Class</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>(%) ICMS InterEstadual</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>(%) IPI</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>(%) PIS</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>(%) COFINS</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>(%) IR</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>(%) ISS</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>(%) IVA</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>(%) IVA Material Importado</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ICMS InterEstadual Material Importado</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ICMS Estado Origem</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ICMS Estado Origem Interno</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ICMS Material Class</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ICMS InterEstadual</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ICMS ST</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>IPI</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>PIS</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>COFINS</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ISS</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>CSSL</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>ICMS Estado Origem Interno</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Total Taxes</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Gross Price</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Calculation Model</th>");
		sb.append("<th style='font-weight: bold; background: #BEBCBC;'>Calculation Memory Material</th>");		
		sb.append("</tr>");
		sb.append("</thead>");
		
		
		for(int i = 0; i < quoteModel.getListaQuoteItemTO().size(); i++){
			
			sb.append("<tr valign='top'>");
			sb.append("<td >"+quoteModel.getListaQuoteItemTO().get(i).getsOrdem()+"</td>");
			sb.append("<td>"+(quoteModel.getListaQuoteItemTO().get(i).getfAvailable() == 1 ? "Yes" : "No")+"</td>");
			sb.append("<td>"+(quoteModel.getListaQuoteItemTO().get(i).getfSubstituicaoTributariaSaida() == 1 ? "Yes" : "No")+"</td>");
			sb.append("<td>"+(quoteModel.getListaQuoteItemTO().get(i).getfSubstituicaoTributariaEntrada() == 1 ? "Yes" : "No")+"</td>");
			sb.append("<td>"+(quoteModel.getListaQuoteItemTO().get(i).getsLabelProductType() == null ? "" : quoteModel.getListaQuoteItemTO().get(i).getsLabelProductType())+"</td>");
			
			if(quoteModel.getListaQuoteItemTO().get(i).getsNCM() == null && quoteModel.getListaQuoteItemTO().get(i).getsNCMMaterialClassPai() == null){
				sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getsNCMMaterialClass()+"</td>");
			}else if(quoteModel.getListaQuoteItemTO().get(i).getsNCMMaterialClassPai() != null){
				sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getsNCMMaterialClassPai()+"</td>");
			}else{
				sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getsNCM()+"</td>");
			}
			
			sb.append("<td>"+(quoteModel.getListaQuoteItemTO().get(i).getsModel() == null ? "" : quoteModel.getListaQuoteItemTO().get(i).getsModel())+"</td>");
			sb.append("<td>"+((quoteModel.getListaQuoteItemTO().get(i).getsImpDescription() == null || quoteModel.getListaQuoteItemTO().get(i).getsImpDescription().isEmpty()) ? quoteModel.getListaQuoteItemTO().get(i).getsDescription() : quoteModel.getListaQuoteItemTO().get(i).getsImpDescription())+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrQty()+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmImportedListPrice())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmImportedCost())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmSuggestedMargin())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmNetPrice())+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrSuggestedMargin()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrDiscount()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrICMSInterno()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrICMSEstadoOrigem()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrICMSEstadoOrigemInterno()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrICMSEstadoDestino()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrICMSMaterialClass()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrICMSInterEstadual()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrIPI()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrPIS()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrCOFINS()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrIR()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrISS()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrIVA()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getrIVAMaterialImportado()+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSInterEstadualMaterialImportado())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoOrigem())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoOrigemInterno())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSMaterialClass())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSInterEstadual())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSST())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmIPI())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmPIS())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmCOFINS())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmISS())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmCSSL())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmICMSEstadoOrigemInterno())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes())+"</td>");
			sb.append("<td>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice())+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getiCalculationModel()+"</td>");
			sb.append("<td>"+quoteModel.getListaQuoteItemTO().get(i).getsCalculationMemoryMaterial()+"</td>");
			sb.append("</tr>");			
			
//			try{
//				
//				if(quoteModel.getListaQuoteItemTO().get(i).getId() == quoteModel.getListaQuoteItemTO().get(i).getIdTaxQuoteItem()){
//					continue;
//				}
//				
//				if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 3){
//					
//									
//					sb.append("<tr>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
//					for(i = 0 ; i < 30; i++){
//						if(i == 27){
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
//						}else if(i == 28){
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
//						}else{
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//						}
//					}
//					sb.append("</tr>");				
//				
//				}else if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 3 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
//					
//					sb.append("<tr>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
//					for(i = 0 ; i < 30; i++){
//						if(i == 27){
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
//						}else if(i == 28){
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
//						}else{
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//						}
//					}
//					sb.append("</tr>");	
//					
//					sb.append("<tr>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getTexto()+":</td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getRevenue())+"</td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getNetTotal())+"</td>");
//					for(i = 0 ; i < 30; i++){
//						if(i == 27){
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getTotalTaxes())+"</td>");
//						}else if(i == 28){
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(1).getGrossTotal())+"</td>");
//						}else{
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//						}
//					}
//					sb.append("</tr>");		
//					
//				}else if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
//				
//					sb.append("<tr>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
//					for(i = 0 ; i < 30; i++){
//						if(i == 27){
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
//						}else if(i == 28){
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
//						}else{
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//						}
//					}
//					sb.append("</tr>");	
//				
//				}
//				
//			}catch(Exception e){
//				
//				if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 1){
//				
//					sb.append("<tr>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTexto()+":</td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getRevenue())+"</td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//					sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getNetTotal())+"</td>");
//					for(i = 0 ; i < 30; i++){
//						if(i == 27){
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getTotalTaxes())+"</td>");
//						}else if(i == 28){
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'>"+df.format(quoteModel.getListaQuoteItemTO().get(i).getListaTotais().get(0).getGrossTotal())+"</td>");
//						}else{
//							sb.append("<td style='font-weight: bold; background: #BEBCBC;'></td>");
//						}
//					}
//					sb.append("</tr>");		
//					
//				}				
//			}
			
		}
		
		sb.append("</table>");
		
		String arquivo = cab.toString() + sb.toString();
		
		try{
			
			fileName = quoteSelecionado.getsQuoteNumber()+"_TaxesAnalysisCalc";
			
			FileOutputStream fileOut = new FileOutputStream("TaxesReportMargin");  
			fileOut.write(arquivo.getBytes());  
			fileOut.flush();  
			fileOut.close();
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    ExternalContext externalContext = facesContext.getExternalContext();
		    externalContext.setResponseContentType("application/vnd.ms-excel");
		    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\""+fileName+"_"+sdf.format(new Date())+".xls\"");
			
		    OutputStream output = externalContext.getResponseOutputStream();  
		    	  
	        //Escrevendo 
		    output.write(arquivo.getBytes());
	    	 
	        //Para finalizar o processo
	        output.flush();
		    output.close();
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
 
        for (Row row : sheet) {
            for (Cell cell : row) {
                cell.setCellValue(cell.getStringCellValue().toUpperCase());
                cell.setCellStyle(style);
            }
        }
        
        try{
        	
        	FacesContext facesContext = FacesContext.getCurrentInstance();
    	    ExternalContext externalContext = facesContext.getExternalContext();
    	    externalContext.setResponseContentType("application/vnd.ms-excel");
    	    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"quoteRel_"+quoteSelecionado.getsQuoteNumber()+".xls\"");

    	    wb.write(externalContext.getResponseOutputStream());
    	    facesContext.responseComplete();
	       
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        
    }
	
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4.rotate());
 
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        //String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" + File.separator + "images" + File.separator + "prime_logo.png";
         
        //pdf.add(Image.getInstance(logo));
              
    }
	
	public void preparaListWokGroup(){
		listaWorkGroup = groupFacadeImpl.findOrderByOrganization(userLogged.getTbSysOrganization().getId());
		
		if(listaWorkGroup == null){
			listaWorkGroup = new ArrayList<>();
		}
		
	}
	
	public void montaPickListParticipants(){
		
		List<TbSysUser> personSource = new ArrayList<TbSysUser>();
		List<TbSysUser> personTarget = new ArrayList<TbSysUser>();
		
		for(TbSysUser person : getListaPerson()){
			personSource.add(person);
		}
		
		if(quoteSelecionado.getTbTaxQuoteParticipants() != null && quoteSelecionado.getTbTaxQuoteParticipants().size() != 0){
			for(TbTaxQuoteParticipant participant : quoteSelecionado.getTbTaxQuoteParticipants()){
				personTarget.add(participant.getTbSysUser());
			}
		}
		 
		participants = new DualListModel<>(personSource, personTarget);
	}
	
	public void preparaListaProductType(){
		
		int count = 0;
		
		while(listaProductTypes.size() < 4){
			
			count++;
			
			if(count == 1){
				TbAdmProductType type = new TbAdmProductType();
				type.setiTaxModel(1F);
				type.setsLabelAcronym("HW");
				
				listaProductTypes.add(type);
				
				continue;
				
			}else if(count == 2){
				TbAdmProductType type = new TbAdmProductType();
				type.setiTaxModel(2F);
				type.setsLabelAcronym("SW");
				
				listaProductTypes.add(type);
				
				continue;
				
			}else if(count == 3){
				TbAdmProductType type = new TbAdmProductType();
				type.setiTaxModel(3F);
				type.setsLabelAcronym("SV");
				
				listaProductTypes.add(type);
				
				continue;
				
			}else if(count == 5){
				TbAdmProductType type = new TbAdmProductType();
				type.setiTaxModel(5F);
				type.setsLabelAcronym("MD");
				
				listaProductTypes.add(type);
				
				continue;
			}
		}
		
	}
	
	public void preparaListDestination(){
		listaDestination = destinationFacade.findOrderBy();
	}
	
	public void preparaListaLogQuote(){
		listaLogQuote = quoteLogFacadeImpl.findOrderBy(quoteSelecionado.getId());
	}
	
	public void preparaListOrigin(){
		listaOrigin = originFacade.findOrderBy();
	}
	
	public void preparaListGrupo(){
		listaGrupo = groupFacade.findAll();
	}
	
	public void preparaListPerson(){
		listaPerson = userFacade.findParticipantNotIncludeInQuote(quoteSelecionado.getId());
		
		if(listaPerson == null || listaPerson.isEmpty()){
			listaPerson = userFacade.findByGrupoAndOrganization(userLogged.getTbSysOrganization().getId(), 61L); //61 =  id grupo de vendas
		}
	}
	
	public void preparaListaAdditionalCost(){
		lisAdditionalCosts = updateCostFacade.findAll();
	}
	
	public void preparaListaQuoteItem(){
//		listaQuoteItem = itemFacade.simpleQuery(quoteSelecionado.getId());
	}
	
	public void preparaListaWizard(){
		
		if(!materialCode.isEmpty() && !isFlagMaterialCode()){
			String[] lista = materialCode.split("\\r\\n|\\r|\\n");
			
			for(int i = 0; i < lista.length; i++){
				
				TbAdmMaterial material = materialFacade.findQuery(lista[i].trim());
				ImportWizardTO wizardTO = new ImportWizardTO();
				
				if(material != null){
					
					wizardTO.setIdMaterial(material.getId());
					wizardTO.setMaterial(material.getsInternalModel());
					
					if(material.getTbAdmMaterialClass() != null){
						wizardTO.setIdMaterialClass(material.getTbAdmMaterialClass().getId());
						wizardTO.setNcm(material.getTbAdmMaterialClass().getsNcm() +" "+ material.getTbAdmMaterialClass().getSiglaSt()); 
					}
					
				}else{
					
					wizardTO.setMaterial(lista[i]);
										
				}
				
				wizardTO.setLine(i+1);
				
				listaWizard.add(wizardTO);
				
			}
			
			setFlagMaterialCode(true);
		}
		
		if(!groupColumn.isEmpty() && !isFlagGroupColumn()){
			String[] lista = groupColumn.split("\\r\\n|\\r|\\n");
			
			for(int i = 0; i < listaWizard.size(); i++){
				try{
					listaWizard.get(i).setGroup(lista[i].trim());
				}catch(Exception e){
					listaWizard.get(i).setGroup(null);
				}
			}
			
			setFlagGroupColumn(true);
		}
		
		if(!quantityColumn.isEmpty() && !isFlagQuantityColumn()){
			String[] lista = quantityColumn.split("\\r\\n|\\r|\\n");
			
			for(int i = 0; i < listaWizard.size(); i++){
				try{
					listaWizard.get(i).setQty(String.valueOf(Integer.valueOf(lista[i].trim())));
				}catch(Exception e){
					listaWizard.get(i).setQty("1");
				}
			}
			
			setFlagQuantityColumn(true);
		}
		
		if(!totalCostvalue.isEmpty() && !isFlagTotalCostvalue()){
			String[] lista = totalCostvalue.split("\\r\\n|\\r|\\n");
			
			for(int i = 0; i < listaWizard.size(); i++){
				try{
					listaWizard.get(i).setCostValue(Double.parseDouble(lista[i].trim()));
				}catch(Exception e){
					listaWizard.get(i).setCostValue(0D);
				}
			}
			
			setFlagTotalCostvalue(true);
			
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("impWizard:saveWizard");
		}
		
	}	
	
	
	public void buttonImportSave(){	
				
		if(disabled){			
			disabled = false;
			
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("impWizard:saveWizard");
			
		}else{			
			disabled =  isFlagGroupColumn() && isFlagMaterialCode() && isFlagQuantityColumn();
		}
				
	}
	
	
	public void saveWizard(){
		
		checkSecurity();
		
		if(write){
		
			boolean sendMail = false;
			
			for(ImportWizardTO to: listaWizard){
				
				TbTaxQuoteItem quoteItem = new TbTaxQuoteItem();
				
				quoteItem.setTbTaxQuote(quoteSelecionado);
				
				try{
					if(to.getIdMaterial() != 0){
						
						quoteItem.setTbAdmMaterial(materialFacade.find(to.getIdMaterial()));
						
						quoteItem.setsModel(quoteItem.getTbAdmMaterial().getsInternalModel());
						
						
						
						if(quoteItem.getTbAdmMaterial().getTbAdmMaterialClass() != null){
							quoteItem.setTbAdmMaterialClass(quoteItem.getTbAdmMaterial().getTbAdmMaterialClass());
							quoteItem.setsNcm(quoteItem.getTbAdmMaterialClass().getsNcm());
							quoteItem.setsLabelProductType(quoteItem.getTbAdmMaterial().getTbAdmMaterialClass().getTbAdmProductType().getsLabel());
						}
						
						
					}else{
						
						TbAdmMaterial newMaterial = new TbAdmMaterial();
						newMaterial.setsInternalModel(to.getMaterial());
						newMaterial.setmCost((to.getCostValue() / Integer.parseInt(to.getQty())));
						
						GregorianCalendar gc = new GregorianCalendar();
						
						gc.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
						
						newMaterial.setDtItemCreationDate(gc.getTime());
						
						quoteItem.setTbAdmMaterial(materialFacade.saveReturn(newMaterial));														
						
						sendMail = true;
						
					}
					
					quoteItem.setrQty(Float.parseFloat(to.getQty()));
					quoteItem.setsGroupNumber(to.getGroup());
					quoteItem.setmImportedCost((to.getCostValue()));
					
					if(quoteModel.getListaQuoteItemTO() != null
							&& quoteModel.getListaQuoteItemTO().size() > 0){
						
						int position = quoteModel.getListaQuoteItemTO().size();
						
						quoteItem.setiLineNumber((quoteModel.getListaQuoteItemTO().get(position - 1).getiLineNumber() + 1));
						
						int novaOrdem = Integer.parseInt(quoteModel.getListaQuoteItemTO().get(position - 1).getsOrdem().split("\\.")[0]);
						
						quoteItem.setsOrdem(String.valueOf((novaOrdem+1)+".0"));
						
					}else{
					
						quoteItem.setiLineNumber(to.getLine());
						quoteItem.setsOrdem(to.getLine()+".0");
					}
					
					itemFacade.save(quoteItem);
					
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					return;
				}
			}
			
			if(sendMail){
				SendEmail email = new SendEmail();
				
				
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
					email.sendHtmlEmail(" ", mailSalesRep, emailsAlertas, "Material Declassified", quoteFacade.find(quoteSelecionado.getId()), "");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			
			if(currencyChange){
				alterCurrency();
			}
			
			//Chama o método inicial para recarregar a nova lista adicionada manualmente;
			Load();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("save_success"), ""));
			
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
		}
		
	}
	
	
	
	public void onBack(){
		if(isFlagGroupColumn() && isFlagMaterialCode() && isFlagQuantityColumn() && isFlagTotalCostvalue()){
			
			for(ImportWizardTO to: listaWizard){
				to.setCostValue(null);
			}
			
			setFlagTotalCostvalue(false);
			totalCostvalue = "";
			
		}else if(isFlagGroupColumn() && isFlagMaterialCode() && isFlagQuantityColumn() && !isFlagTotalCostvalue()){
			
			for(ImportWizardTO to: listaWizard){
				to.setQty(null);
			}
			
			setFlagQuantityColumn(false);
			quantityColumn = "";
			
		}else if(isFlagGroupColumn() && isFlagMaterialCode() && !isFlagQuantityColumn() && !isFlagTotalCostvalue()){
			
			for(ImportWizardTO to: listaWizard){
				to.setGroup(null);
			}
			
			setFlagGroupColumn(false);
			groupColumn = "";
			
		}else if(!isFlagGroupColumn() && isFlagMaterialCode() && !isFlagQuantityColumn() && !isFlagTotalCostvalue()){
		
			listaWizard = new ArrayList<>();
			
			setFlagMaterialCode(false);
			materialCode = "";
			
		}
		
	}
	
	
	
	public void resetImportWizard(){
		
		listaWizard = new ArrayList<>();
		
		materialCode = "";
		groupColumn = "";
		quantityColumn = "";
		totalCostvalue = "";
		
		setFlagMaterialCode(false);
		setFlagQuantityColumn(false);
		setFlagGroupColumn(false);
		setFlagTotalCostvalue(false);
		
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("impWizard:saveWizard");
	}
	
	
	
	public void save(){
		
		checkSecurity();
		
		if(write){
			
			if(!grupo.equals("-1")){
				quoteSelecionado.setTbAdmGroupOfContent(groupFacade.find(Long.parseLong(grupo)));
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_work_group")), ""));
				
				return;
			}
			
			if(!destination.equals("-1")){
				quoteSelecionado.setTbAdmDestination(destinationFacade.find(Long.parseLong(destination)));
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_quote_destination_location")), ""));
				
				return;
				
			}
			
			if(!originHW.equals("-1")){
				quoteSelecionado.setTbAdmOriginHw(originFacade.find(Long.parseLong(originHW)));
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_quote_locaton_hw")), ""));
				
				return;
			}
			
			if(!originSW.equals("-1")){
				quoteSelecionado.setTbAdmOriginSw(originFacade.find(Long.parseLong(originSW)));
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_quote_locaton_sw")), ""));
				
				return;
			}
			
	//		if(!originMT.equals("-1")){
	//			quoteSelecionado.setTbAdmOriginMt(originFacade.find(Long.parseLong(originMT)));
	//		}else{
	//			
	//			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
	//					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_quote_locaton_mt")), ""));
	//			
	//			return;
	//		}
			
			if(!originSV.equals("-1")){
				quoteSelecionado.setTbAdmOriginSv(originFacade.find(Long.parseLong(originSV)));
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_quote_locaton_sv")), ""));
				
				return;
				
			}		
			
			if(taxSelecionado == null || taxSelecionado.isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_dollar_rate_quote")), ""));
				
				return;
				
			}else{
				
				if(taxSelecionado.equals("1")){
					if(quoteSelecionado.getmDollarRate() == null){
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
								MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_dollar_rate_quote")), ""));
						
						return;
					}
				}else{
					quoteSelecionado.setmDollarRate(Double.parseDouble(String.valueOf(dollarRate)));
				}
				
			}
			
			if(quoteSelecionado.isfContrest()){
				if(quoteSelecionado.getTbTaxQuoteDetail().getsIeBill() == null || quoteSelecionado.getTbTaxQuoteDetail().getsIeBill().isEmpty()){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
							MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_insc_est")), ""));
					
					return;
				}
			}
			
			
			
			try{
				
				if(quoteSelecionado.getTbTaxQuoteDetail() != null ){
					
					if(quoteSelecionado.getTbTaxQuoteDetail().getId() == 0){
						TbTaxQuoteDetail detail = quoteSelecionado.getTbTaxQuoteDetail();
						
						detail.setTbTaxQuote(quoteSelecionado);
						
						quoteSelecionado.setTbTaxQuoteDetail(detailFacade.saveReturn(detail));
					}else{
						detailFacade.alter(quoteSelecionado.getTbTaxQuoteDetail());
					}
				}
				
				
				if(quoteSelecionado.getmAdditionalCostFreight() != null && !quoteSelecionado.getmAdditionalCostFreight().equals(quoteDefault.getmAdditionalCostFreight())){
					TbTaxLastUpdateAdditionalCost cost = new TbTaxLastUpdateAdditionalCost();
					
					GregorianCalendar gc = new GregorianCalendar();
					
					gc.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
					
					cost.setDtUpdate(gc.getTime());
					cost.setTbSysUser(userLogged);
					cost.setTbTaxQuote(quoteSelecionado);
					cost.setmValue(quoteSelecionado.getmAdditionalCostFreight());
					cost.setsCostName(bundle.getString("label_freight"));
					
					updateCostFacade.save(cost);
				}
				
				if(quoteSelecionado.getmAdditionalCostProjectCost() != null && !quoteSelecionado.getmAdditionalCostProjectCost().equals(quoteDefault.getmAdditionalCostProjectCost())){
					TbTaxLastUpdateAdditionalCost cost = new TbTaxLastUpdateAdditionalCost();
					cost.setDtUpdate(new Date());
					cost.setTbSysUser(userLogged);
					cost.setTbTaxQuote(quoteSelecionado);
					cost.setmValue(quoteSelecionado.getmAdditionalCostProjectCost());
					cost.setsCostName(bundle.getString("label_project_cost"));
					
					updateCostFacade.save(cost);
				}
				
				if(quoteSelecionado.getmAdditionalCostOtherCosts() != null && !quoteSelecionado.getmAdditionalCostOtherCosts().equals(quoteDefault.getmAdditionalCostOtherCosts())){
					TbTaxLastUpdateAdditionalCost cost = new TbTaxLastUpdateAdditionalCost();
					cost.setDtUpdate(new Date());
					cost.setTbSysUser(userLogged);
					cost.setTbTaxQuote(quoteSelecionado);
					cost.setmValue(quoteSelecionado.getmAdditionalCostOtherCosts());
					cost.setsCostName(bundle.getString("label_other_cost"));
					
					updateCostFacade.save(cost);
				}
				
				if(quoteSelecionado.getmAdditionalCostTraining() != null && !quoteSelecionado.getmAdditionalCostTraining().equals(quoteDefault.getmAdditionalCostTraining())){
					TbTaxLastUpdateAdditionalCost cost = new TbTaxLastUpdateAdditionalCost();
					cost.setDtUpdate(new Date());
					cost.setTbSysUser(userLogged);
					cost.setTbTaxQuote(quoteSelecionado);
					cost.setmValue(quoteSelecionado.getmAdditionalCostTraining());
					cost.setsCostName(bundle.getString("label_training"));
					
					updateCostFacade.save(cost);
				}
				
				quoteFacade.alter(quoteSelecionado);
				
				lisAdditionalCosts = new ArrayList<>();
				
				if(currencyChange){
					alterCurrency();
				}
				
				Load();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
			}
			
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
		}
		
		
	}
	
	
	public void registerLogTaxFree(){
		
		checkSecurity();
		
		if(write && taxFree){
			if((quoteSelecionado.isfSt() || quoteSelecionado.isfICMS() || quoteSelecionado.isfIPI() || quoteSelecionado.isfISS() || quoteSelecionado.isfCOFINS() || quoteSelecionado.isfPIS()) 
					&& (sComments == null || sComments.isEmpty())){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"This filed comments is required.", ""));
				
				return;
			}
			
			
			try{
				if(quoteSelecionado.isfCOFINS()){
					TbTaxQuoteLog log = new TbTaxQuoteLog();
					
					log.setTbTaxQuote(quoteSelecionado); 
					log.setsLoginUser(userLogged.getsFullName());
					log.setDtCreated(new Date());
					log.setsDescription("Marcado isenção fiscal do imposto COFINS para esta quote");
					log.setsComments(sComments);
					
					quoteLogFacadeImpl.save(log);
					
				}else{
					
					TbTaxQuoteLog log = new TbTaxQuoteLog();
					
					log.setTbTaxQuote(quoteSelecionado); 
					log.setsLoginUser(userLogged.getsFullName());
					log.setDtCreated(new Date());
					log.setsDescription("Não há isenção fiscal do imposto COFINS para esta quote");
					log.setsComments(sComments);
					
					quoteLogFacadeImpl.save(log);
				}
				
				if(quoteSelecionado.isfICMS()){
					TbTaxQuoteLog log = new TbTaxQuoteLog();
					
					log.setTbTaxQuote(quoteSelecionado); 
					log.setsLoginUser(userLogged.getsFullName());
					log.setDtCreated(new Date());
					log.setsDescription("Marcado isenção fiscal do imposto ICMS para esta quote");
					log.setsComments(sComments);
					
					quoteLogFacadeImpl.save(log);
					
				}else{
					TbTaxQuoteLog log = new TbTaxQuoteLog();
					
					log.setTbTaxQuote(quoteSelecionado); 
					log.setsLoginUser(userLogged.getsFullName());
					log.setDtCreated(new Date());
					log.setsDescription("Não Há isenção fiscal do imposto ICMS para esta quote");
					log.setsComments(sComments);
					
					quoteLogFacadeImpl.save(log);
					
				}
				
				if(quoteSelecionado.isfIPI()){
					TbTaxQuoteLog log = new TbTaxQuoteLog();
					
					log.setTbTaxQuote(quoteSelecionado); 
					log.setsLoginUser(userLogged.getsFullName());
					log.setDtCreated(new Date());
					log.setsDescription("Marcado isenção fiscal do imposto IPI para esta quote");
					log.setsComments(sComments);
					
					quoteLogFacadeImpl.save(log);
					
				}else{
					TbTaxQuoteLog log = new TbTaxQuoteLog();
					
					log.setTbTaxQuote(quoteSelecionado); 
					log.setsLoginUser(userLogged.getsFullName());
					log.setDtCreated(new Date());
					log.setsDescription("Não Há isenção fiscal do imposto IPI para esta quote");
					log.setsComments(sComments);
					
					quoteLogFacadeImpl.save(log);
					
				}
				
				if(quoteSelecionado.isfISS()){
					TbTaxQuoteLog log = new TbTaxQuoteLog();
					
					log.setTbTaxQuote(quoteSelecionado); 
					log.setsLoginUser(userLogged.getsFullName());
					log.setDtCreated(new Date());
					log.setsDescription("Marcado isenção fiscal do imposto ISS para esta quote");
					log.setsComments(sComments);
					
					quoteLogFacadeImpl.save(log);			
					
				}else{
					TbTaxQuoteLog log = new TbTaxQuoteLog();
					
					log.setTbTaxQuote(quoteSelecionado); 
					log.setsLoginUser(userLogged.getsFullName());
					log.setDtCreated(new Date());
					log.setsDescription("Não há isenção fiscal do imposto ISS para esta quote");
					log.setsComments(sComments);
					
					quoteLogFacadeImpl.save(log);
					
				}
				
				if(quoteSelecionado.isfPIS()){
					TbTaxQuoteLog log = new TbTaxQuoteLog();
					
					log.setTbTaxQuote(quoteSelecionado); 
					log.setsLoginUser(userLogged.getsFullName());
					log.setDtCreated(new Date());
					log.setsDescription("Marcado isenção fiscal do imposto PIS para esta quote");
					log.setsComments(sComments);
					
					quoteLogFacadeImpl.save(log);
					
				}else{
					TbTaxQuoteLog log = new TbTaxQuoteLog();
					
					log.setTbTaxQuote(quoteSelecionado); 
					log.setsLoginUser(userLogged.getsFullName());
					log.setDtCreated(new Date());
					log.setsDescription("Não há isenção fiscal do imposto PIS para esta quote");
					log.setsComments(sComments);
					
					quoteLogFacadeImpl.save(log);
					
				}
				
				if(quoteSelecionado.isfSt()){
					TbTaxQuoteLog log = new TbTaxQuoteLog();
					
					log.setTbTaxQuote(quoteSelecionado); 
					log.setsLoginUser(userLogged.getsFullName());
					log.setDtCreated(new Date());
					log.setsDescription("Marcado isenção fiscal de Substituição Tributária para esta quote");
					log.setsComments(sComments);
					
					quoteLogFacadeImpl.save(log);
					
				}else{
					TbTaxQuoteLog log = new TbTaxQuoteLog();
					
					log.setTbTaxQuote(quoteSelecionado); 
					log.setsLoginUser(userLogged.getsFullName());
					log.setDtCreated(new Date());
					log.setsDescription("Não há isenção fiscal de Substituição Tributária para esta quote");
					log.setsComments(sComments);
					
					quoteLogFacadeImpl.save(log);
					
				}
				
				quoteFacade.alter(quoteSelecionado);
				
				sComments = "";
				
				listaLogQuote = new ArrayList<>();
				
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("logShow");
				
				//Fecho o modal da tela
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('taxfree').hide();");
				
			}catch(Exception e){
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				sComments = "";
				
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('taxfree').hide();");
			}
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
		}
	
	}
	
	
	public void leasing(){
		if(quoteSelecionado.isfLeasing()){
			quoteSelecionado.setfContrest(true);
			quoteSelecionado.setfPartner(true);
		}else{
			quoteSelecionado.setfContrest(false);
		}
	}
	
	
	public void addParticipant(){
		
		List<TbSysUser> lista = participants.getTarget();
		
		for(int i = 0; i < quoteSelecionado.getTbTaxQuoteParticipants().size(); i++){
			
			try{
				participantFacade.delete(quoteSelecionado.getTbTaxQuoteParticipants().get(i));
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
			}
			
			quoteSelecionado.getTbTaxQuoteParticipants().remove(i);
			
			i--;
		}
		
		List<TbTaxQuoteParticipant> listaPart = new ArrayList<>();
		
		for(TbSysUser person: lista){
			
			TbTaxQuoteParticipant quoteParticipant = new TbTaxQuoteParticipant();
			quoteParticipant.setTbTaxQuote(quoteSelecionado);
			quoteParticipant.setTbSysUser(person);
			
			try{
				quoteParticipant = participantFacade.saveReturn(quoteParticipant);
				
				listaPart.add(quoteParticipant);
				
			}catch(Exception e){
				e.printStackTrace();

				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
			}
			
		}
		
		quoteSelecionado.setTbTaxQuoteParticipants(listaPart);
		
	}
	
	
	public void billToShip(){
		
		quoteSelecionado.getTbTaxQuoteDetail().setsCnpjShip(quoteSelecionado.getTbTaxQuoteDetail().getsCnpjBill());
		quoteSelecionado.getTbTaxQuoteDetail().setsIeShip(quoteSelecionado.getTbTaxQuoteDetail().getsIeBill());
		quoteSelecionado.getTbTaxQuoteDetail().setsContactShip(quoteSelecionado.getTbTaxQuoteDetail().getsContactBill());
		quoteSelecionado.getTbTaxQuoteDetail().setsEmailShip(quoteSelecionado.getTbTaxQuoteDetail().getsEmailBill());
		quoteSelecionado.getTbTaxQuoteDetail().setsPhoneShip(quoteSelecionado.getTbTaxQuoteDetail().getsPhoneBill());
		
	}
	
	public void billToInvoice(){
		
		quoteSelecionado.getTbTaxQuoteDetail().setsCnpj(quoteSelecionado.getTbTaxQuoteDetail().getsCnpjBill());
		quoteSelecionado.getTbTaxQuoteDetail().setsIe(quoteSelecionado.getTbTaxQuoteDetail().getsIeBill());
		quoteSelecionado.getTbTaxQuoteDetail().setsContactPerson(quoteSelecionado.getTbTaxQuoteDetail().getsContactBill());
		quoteSelecionado.getTbTaxQuoteDetail().setsEmail(quoteSelecionado.getTbTaxQuoteDetail().getsEmailBill());
		quoteSelecionado.getTbTaxQuoteDetail().setsPhone(quoteSelecionado.getTbTaxQuoteDetail().getsPhoneBill());
		
	}
	
	public void shipToInvoice(){
		
		quoteSelecionado.getTbTaxQuoteDetail().setsCnpj(quoteSelecionado.getTbTaxQuoteDetail().getsCnpjShip());
		quoteSelecionado.getTbTaxQuoteDetail().setsIe(quoteSelecionado.getTbTaxQuoteDetail().getsIeShip());
		quoteSelecionado.getTbTaxQuoteDetail().setsContactPerson(quoteSelecionado.getTbTaxQuoteDetail().getsContactShip());
		quoteSelecionado.getTbTaxQuoteDetail().setsEmail(quoteSelecionado.getTbTaxQuoteDetail().getsEmailShip());
		quoteSelecionado.getTbTaxQuoteDetail().setsPhone(quoteSelecionado.getTbTaxQuoteDetail().getsPhoneShip());
		
	}
	
	public void saveDetails(){
		
		checkSecurity();
		
		if(write){
			
			try{
				
				if(quoteSelecionado.getTbTaxQuoteDetail().getId() == 0){
					quoteSelecionado.setTbTaxQuoteDetail(detailFacade.saveReturn(quoteSelecionado.getTbTaxQuoteDetail()));
				}else{
					detailFacade.alter(quoteSelecionado.getTbTaxQuoteDetail());
				}
				
				quoteFacade.alter(quoteSelecionado);
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				if(currencyChange){
					alterCurrency();
				}
				
				Load();
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
			}
			
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
		}
		
	}
	
	
	public void resetReview(){
		
		long id = quoteSelecionado.getTbTaxQuoteDetail().getId();
		
		if(id != 0){
			quoteSelecionado.setTbTaxQuoteDetail(null);
			
			quoteSelecionado.setTbTaxQuoteDetail(detailFacade.find(id));
		}else{
			quoteSelecionado.setTbTaxQuoteDetail(new TbTaxQuoteDetail());
		}
	}
	
	public void delete(){
		
		checkSecurity();
		
		if(write){
		
			if(quoteSelecionado.getDtClose() == null){
				TbTaxQuoteItem quoteItem = itemFacade.find(Long.parseLong(String.valueOf(idQuoteItemDeleted)));
			
				try{
					itemFacade.delete(quoteItem);
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("delete_success"), ""));
				
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("delete_error"), ""));
				}
			
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"This quote is closed", ""));
				
			}
			
			if(currencyChange){
				alterCurrency();
			}
			
			Load();
			
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
			
		}
		
	}
	
	
	public String onFlowProcess(FlowEvent event) {
       
       return event.getNewStep();       
    }
	
	
	public void preparaListaMaterialClass(){
		listaMaterialClass = materialClassFacade.findOrder();
	}
	
	
	public void popularMaterialSelecionado(){
		
		//recupero o valor que estou enviando pela página com a tag <f:param>
		materialPartNumber = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("partNumber");
				
		materialSelecionado = materialFacade.find(Long.valueOf(materialPartNumber));
		
		if(materialSelecionado != null && materialSelecionado.getTbAdmMaterialClass() != null){
			classSelecionado = String.valueOf(materialSelecionado.getTbAdmMaterialClass().getId());
		}
	}
	
	public void popularItemSelecionado(){
		
		//recupero o valor que estou enviando pela página com a tag <f:param>
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idItem");
				
		itemSelecionado = itemFacade.find(Long.valueOf(id));
		
		if(itemSelecionado != null && itemSelecionado.getTbTaxQuoteItem() != null){
			paiSelecionado = String.valueOf(itemSelecionado.getTbTaxQuoteItem().getId());
		}
		
		if(itemSelecionado != null && itemSelecionado.getTbAdmMaterialClass() != null && itemSelecionado.getTbAdmMaterialClassLocal() != null){
			ncmSelecionado = String.valueOf(itemSelecionado.getTbAdmMaterialClassLocal().getId());
		}else if(itemSelecionado != null && itemSelecionado.getTbAdmMaterialClass() != null){
			ncmSelecionado = String.valueOf(itemSelecionado.getTbAdmMaterialClass().getId());
		}
		
		if(listaQuoteItemPais == null || listaQuoteItemPais.isEmpty()){
			
			listaQuoteItemPais = new ArrayList<>();
			
			for(QuoteItemTO itemTO : quoteModel.getListaQuoteItemTO()){
				
				if(itemTO.getId() == itemTO.getIdTaxQuoteItem()){
					listaQuoteItemPais.add(itemFacade.find(itemTO.getId()));
				}
				
			}
		}
		
	}
	
	public void changeFather(){
		
		checkSecurity();
		
		if(write){
			
			if(!flagQuoteItemOriginal){
		
				if(paiSelecionado.equals("-1")){
					
					if(itemSelecionado.getTbTaxQuoteItem() != null){
						itemSelecionado.setTbTaxQuoteItemOriginal(itemFacade.find(itemSelecionado.getTbTaxQuoteItem().getId()));
					}else{
						itemSelecionado.setTbTaxQuoteItemOriginal(null);
					}
					
					itemSelecionado.setTbTaxQuoteItem(null);
					itemSelecionado.setfItemIndependent(true);
					
				}else{
					
					if(itemSelecionado.getTbTaxQuoteItem() != null){
						itemSelecionado.setTbTaxQuoteItemOriginal(itemFacade.find(itemSelecionado.getTbTaxQuoteItem().getId()));
					}else{
						itemSelecionado.setTbTaxQuoteItemOriginal(null);
					}
					
					itemSelecionado.setTbTaxQuoteItem(itemFacade.find(Long.parseLong(paiSelecionado)));
					itemSelecionado.setfItemPaiManual(true);
					itemSelecionado.setfItemIndependent(false);
					
				}
				
			}else{
				
				if(itemSelecionado.getTbTaxQuoteItemOriginal() != null){
					itemSelecionado.setTbTaxQuoteItem(itemFacade.find(itemSelecionado.getTbTaxQuoteItemOriginal().getId()));
					itemSelecionado.setfItemIndependent(false);
				}else{
					itemSelecionado.setTbTaxQuoteItem(null);
					itemSelecionado.setfItemIndependent(true);
				}
				
				itemSelecionado.setTbTaxQuoteItemOriginal(null);
				itemSelecionado.setfItemPaiManual(false);
				
				
			}
			
			
			try{
				
				itemFacade.alter(itemSelecionado);
				itemSelecionado = new TbTaxQuoteItem();
				flagQuoteItemOriginal = false;
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				Load();
				salvarQuote();
				
							
			}catch(Exception e){
				
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
			}
			
		
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
		}
	}
	
	
	
	public void changeNcm(){
		
		checkSecurity();
		
		if(write){	
			
			if(!flagQuoteNcmOriginal){
				
				if(ncmSelecionado.equals("-1")){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
							"You must choose the NCM.", ""));
					
					return;
										
				}else if(Long.parseLong(ncmSelecionado) == itemSelecionado.getTbAdmMaterialClass().getId()){
					
					return;
					
				}else{
					
					if(itemSelecionado.getTbAdmMaterialClassOriginal() == null){
						itemSelecionado.setTbAdmMaterialClassOriginal(materialClassFacade.find(itemSelecionado.getTbAdmMaterialClass().getId()));
					}
					
					itemSelecionado.setsNcmOriginal(itemSelecionado.getsNcm());
					itemSelecionado.setsNcm(materialClassFacade.find(Long.valueOf(ncmSelecionado)).getsNcm());
					itemSelecionado.setTbAdmMaterialClass(materialClassFacade.find(Long.valueOf(ncmSelecionado)));
					itemSelecionado.setfItemNcmManual(true);
					itemSelecionado.setTbAdmMaterialClassLocal(null);
					
				}
				
			}else{
				
				itemSelecionado.setTbAdmMaterialClass(materialClassFacade.find(itemSelecionado.getTbAdmMaterialClassOriginal().getId()));
				itemSelecionado.setsNcm(itemSelecionado.getsNcmOriginal());
				itemSelecionado.setfItemNcmManual(false);
				itemSelecionado.setsNcmOriginal(null);
				itemSelecionado.setTbAdmMaterialClassOriginal(null);
				
			}
			
			
			try{
				
				itemFacade.alter(itemSelecionado);
				itemSelecionado = new TbTaxQuoteItem();
				flagQuoteNcmOriginal = false;
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				Load();
				salvarQuote();
				
							
			}catch(Exception e){
				
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
			}
			
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
		}
		
	}
	
			
			
	public void alterMaterial(){
		
		checkSecurity();
		
		if(write){
		
			if(!classSelecionado.equals("-1")){
				materialSelecionado.setTbAdmMaterialClass(materialClassFacade.find(Long.parseLong(classSelecionado)));
			}else{
				materialSelecionado.setTbAdmMaterialClass(null);
			}
			
			try{
				materialFacade.alter(materialSelecionado);
				materialSelecionado = new TbAdmMaterial();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("save_success"), ""));
				
				if(currencyChange){
					alterCurrency();
				}
				
				salvarQuote();
				Load();
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
			}
			
		}else if(!disabledAll){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
				
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
				
		}
		
		
	}
	
	
	public void prepareReport(){
		reports = true;
	}
	
	public void back(){
		reports = false;
	}
	
	
	
	
	
	
	
	/**
	 * Getters and Setters
	 */
	
	public TbTaxQuote getQuoteSelecionado() {
		return quoteSelecionado;
	}

	public void setQuoteSelecionado(TbTaxQuote quoteSelecionado) {
		this.quoteSelecionado = quoteSelecionado;
	}


	public boolean isCurrencyChange() {
		return currencyChange;
	}


	public void setCurrencyChange(boolean currencyChange) {
		this.currencyChange = currencyChange;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


//	public TbTaxQuoteItem getQuoteItemSelecionado() {
//		return quoteItemSelecionado;
//	}
//
//
//	public void setQuoteItemSelecionado(TbTaxQuoteItem quoteItemSelecionado) {
//		this.quoteItemSelecionado = quoteItemSelecionado;
//	}


	public String getOriginHW() {
		
		if(quoteSelecionado.getTbAdmOriginHw() != null){
			originHW = String.valueOf(quoteSelecionado.getTbAdmOriginHw().getId());
		}
		
		return originHW;
	}


	public void setOriginHW(String originHW) {
		this.originHW = originHW;
	}


	public String getOriginSW() {
		
		if(quoteSelecionado.getTbAdmOriginSw() != null){
			originSW = String.valueOf(quoteSelecionado.getTbAdmOriginSw().getId());
		}
		
		return originSW;
	}


	public void setOriginSW(String originSW) {
		this.originSW = originSW;
	}


	public String getOriginSV() {
		
		if(quoteSelecionado.getTbAdmOriginSv() != null){
			originSV = String.valueOf(quoteSelecionado.getTbAdmOriginSv().getId());
		}
		
		return originSV;
	}


	public void setOriginSV(String originSV) {
		this.originSV = originSV;
	}


	public String getOriginMT() {
		
		if(quoteSelecionado.getTbAdmOriginMt() != null){
			originMT = String.valueOf(quoteSelecionado.getTbAdmOriginMt().getId());
		}
		
		return originMT;
	}


	public void setOriginMT(String originMT) {
		this.originMT = originMT;
	}


	public List<TbAdmDestination> getListaDestination() {
		
		if(listaDestination == null || listaDestination.size() == 0){
			preparaListDestination();
		}
		
		return listaDestination;
	}


	public void setListaDestination(List<TbAdmDestination> listaDestination) {
		this.listaDestination = listaDestination;
	}


	public List<TbAdmOrigin> getListaOrigin() {
		
		if(listaOrigin == null || listaOrigin.size() == 0){
			preparaListOrigin();
		}
		
		return listaOrigin;
	}


	public void setListaOrigin(List<TbAdmOrigin> listaOrigin) {
		this.listaOrigin = listaOrigin;
	}


	public List<TbAdmGroupOfContent> getListaGrupo() {
		
		if(listaGrupo == null || listaGrupo.size() == 0){
			preparaListGrupo();
		}
		
		return listaGrupo;
	}


	public void setListaGrupo(List<TbAdmGroupOfContent> listaGrupo) {
		this.listaGrupo = listaGrupo;
	}


	public String getDestination() {
		
		if(quoteSelecionado.getTbAdmDestination() != null){
			destination = String.valueOf(quoteSelecionado.getTbAdmDestination().getId());
		}
		
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public String getGrupo() {
		
		if(quoteSelecionado.getTbAdmGroupOfContent() != null){
			grupo = String.valueOf(quoteSelecionado.getTbAdmGroupOfContent().getId());
		}
		
		return grupo;
	}


	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}


	public String getTaxSelecionado() {
		return taxSelecionado;
	}


	public void setTaxSelecionado(String taxSelecionado) {
		this.taxSelecionado = taxSelecionado;
	}


	public Float getDollarRate(){
		
		if(dollarRate == null){
			dollarRate =  dollarRateFacade.simpleQuery().getrDollar();
			
			if(dollarRate == null){
				 dollarRate = 0.0F;
			}
		}
		
		
		return dollarRate;
	}


	public void setDollarRate(Float dollarRate) {
		this.dollarRate = dollarRate;
	}


	public List<TbSysUser> getListaPerson() {
		
		if(listaPerson == null || listaPerson.size() == 0){
			preparaListPerson();
		}
		
		return listaPerson;
	}


	public void setListaPerson(List<TbSysUser> listaPerson) {
		this.listaPerson = listaPerson;
	}


	public DualListModel<TbSysUser> getParticipants() {
		
		montaPickListParticipants();
		
		return participants;
	}


	public void setParticipants(DualListModel<TbSysUser> participants) {
		this.participants = participants;
	}


	public String getParticipantesSelecionados() {
		
		participantesSelecionados = "";
		
		if(quoteSelecionado.getTbTaxQuoteParticipants() != null){
			for(TbTaxQuoteParticipant part: quoteSelecionado.getTbTaxQuoteParticipants()){
				if(participantesSelecionados == null || participantesSelecionados.isEmpty()){
					participantesSelecionados = part.getTbSysUser().getsFullName();
				}else{
					participantesSelecionados +=" ," + part.getTbSysUser().getsFullName();
				}
			}
		}
		
		return participantesSelecionados;
	}


	public void setParticipantesSelecionados(String participantesSelecionados) {
		this.participantesSelecionados = participantesSelecionados;
	}


	public List<TbTaxLastUpdateAdditionalCost> getLisAdditionalCosts() {
		
		if(lisAdditionalCosts == null || lisAdditionalCosts.size() == 0){
			preparaListaAdditionalCost();
		}
		
		return lisAdditionalCosts;
	}


	public void setLisAdditionalCosts(
			List<TbTaxLastUpdateAdditionalCost> lisAdditionalCosts) {
		this.lisAdditionalCosts = lisAdditionalCosts;
	}


	public String getMaterialCode() {
		return materialCode;
	}


	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}


	public String getGroupColumn() {
		return groupColumn;
	}


	public void setGroupColumn(String groupColumn) {
		this.groupColumn = groupColumn;
	}


	public String getQuantityColumn() {
		return quantityColumn;
	}


	public void setQuantityColumn(String quantityColumn) {
		this.quantityColumn = quantityColumn;
	}


	public String getTotalCostvalue() {
		return totalCostvalue;
	}


	public void setTotalCostvalue(String totalCostvalue) {
		this.totalCostvalue = totalCostvalue;
	}


	public List<ImportWizardTO> getListaWizard() {
		
		preparaListaWizard();
		
				
		return listaWizard;
	}


	public void setListaWizard(List<ImportWizardTO> listaWizard) {
		this.listaWizard = listaWizard;
	}


	public boolean isFlagMaterialCode() {
		return flagMaterialCode;
	}


	public void setFlagMaterialCode(boolean flagMaterialCode) {
		this.flagMaterialCode = flagMaterialCode;
	}


	public boolean isFlagGroupColumn() {
		return flagGroupColumn;
	}


	public void setFlagGroupColumn(boolean flagGroupColumn) {
		this.flagGroupColumn = flagGroupColumn;
	}


	public boolean isFlagQuantityColumn() {
		return flagQuantityColumn;
	}


	public void setFlagQuantityColumn(boolean flagQuantityColumn) {
		this.flagQuantityColumn = flagQuantityColumn;
	}

	public boolean isFlagTotalCostvalue() {
		return flagTotalCostvalue;
	}


	public void setFlagTotalCostvalue(boolean flagTotalCostvalue) {
		this.flagTotalCostvalue = flagTotalCostvalue;
	}


	public boolean isDisabled() {
		return disabled;
	}


	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}



	public QuoteModel getQuoteModel() {
		return quoteModel;
	}



	public void setQuoteModel(QuoteModel quoteModel) {
		this.quoteModel = quoteModel;
	}



	public boolean isGoalSeek() {
		return goalSeek;
	}



	public void setGoalSeek(boolean goalSeek) {
		this.goalSeek = goalSeek;
	}



	public boolean isGsHW() {
		return gsHW;
	}



	public void setGsHW(boolean gsHW) {
		this.gsHW = gsHW;
	}



	public boolean isGsSW() {
		return gsSW;
	}



	public void setGsSW(boolean gsSW) {
		this.gsSW = gsSW;
	}


	public boolean isGsSV() {
		return gsSV;
	}


	public void setGsSV(boolean gsSV) {
		this.gsSV = gsSV;
	}


	public boolean isGsMD() {
		return gsMD;
	}



	public void setGsMD(boolean gsMD) {
		this.gsMD = gsMD;
	}



	public boolean isGsTotal() {
		return gsTotal;
	}



	public void setGsTotal(boolean gsTotal) {
		this.gsTotal = gsTotal;
	}



	public boolean isGsRadioNet() {
		return gsRadioNet;
	}



	public void setGsRadioNet(boolean gsRadioNet) {
		this.gsRadioNet = gsRadioNet;
	}



	public boolean isGsRadioGross() {
		return gsRadioGross;
	}



	public void setGsRadioGross(boolean gsRadioGross) {
		this.gsRadioGross = gsRadioGross;
	}



	public int getIdQuoteItemDeleted() {
		return idQuoteItemDeleted;
	}



	public void setIdQuoteItemDeleted(int idQuoteItemDeleted) {
		this.idQuoteItemDeleted = idQuoteItemDeleted;
	}



	public String getMaterialPartNumber() {
		return materialPartNumber;
	}



	public void setMaterialPartNumber(String materialPartNumber) {
		this.materialPartNumber = materialPartNumber;
	}



	public TbAdmMaterial getMaterialSelecionado() {
		return materialSelecionado;
	}



	public void setMaterialSelecionado(TbAdmMaterial materialSelecionado) {
		this.materialSelecionado = materialSelecionado;
	}



	public List<TbAdmMaterialClass> getListaMaterialClass() {
		
		if(listaMaterialClass == null || listaMaterialClass.size() == 0){
			preparaListaMaterialClass();
		}
		
		return listaMaterialClass;
	}



	public void setListaMaterialClass(List<TbAdmMaterialClass> listaMaterialClass) {
		this.listaMaterialClass = listaMaterialClass;
	}



	public String getClassSelecionado() {
		return classSelecionado;
	}



	public void setClassSelecionado(String classSelecionado) {
		this.classSelecionado = classSelecionado;
	}



	public boolean isReports() {
		return reports;
	}



	public void setReports(boolean reports) {
		this.reports = reports;
	}



	public String getHtmlToExcel() {
		return htmlToExcel;
	}



	public void setHtmlToExcel(String htmlToExcel) {
		this.htmlToExcel = htmlToExcel;
	}



	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public List<TbTaxQuoteLog> getListaLogQuote() {
		
		if(listaLogQuote == null || listaLogQuote.isEmpty()){
			preparaListaLogQuote();
		}
		
		return listaLogQuote;
	}



	public void setListaLogQuote(List<TbTaxQuoteLog> listaLogQuote) {
		this.listaLogQuote = listaLogQuote;
	}



	public String getsComments() {
		return sComments;
	}



	public void setsComments(String sComments) {
		this.sComments = sComments;
	}



	public boolean isRead() {
		return read;
	}



	public void setRead(boolean read) {
		this.read = read;
	}



	public boolean isWrite() {
		return write;
	}



	public void setWrite(boolean write) {
		this.write = write;
	}



	public boolean isAddItem() {
		return addItem;
	}



	public void setAddItem(boolean addItem) {
		this.addItem = addItem;
	}



	public boolean isGoalSeekPerm() {
		return goalSeekPerm;
	}



	public void setGoalSeekPerm(boolean goalSeekPerm) {
		this.goalSeekPerm = goalSeekPerm;
	}



	public boolean isRestoreValue() {
		return restoreValue;
	}



	public void setRestoreValue(boolean restoreValue) {
		this.restoreValue = restoreValue;
	}



	public boolean isTaxFree() {
		return taxFree;
	}



	public void setTaxFree(boolean taxFree) {
		this.taxFree = taxFree;
	}



	public boolean isDiscount() {
		return discount;
	}



	public void setDiscount(boolean discount) {
		this.discount = discount;
	}



	public boolean isTaxReportFull() {
		return taxReportFull;
	}



	public void setTaxReportFull(boolean taxReportFull) {
		this.taxReportFull = taxReportFull;
	}



	public boolean isTaxAnalysisCalc() {
		return taxAnalysisCalc;
	}



	public void setTaxAnalysisCalc(boolean taxAnalysisCalc) {
		this.taxAnalysisCalc = taxAnalysisCalc;
	}


	public boolean isDisabledAll() {
		return disabledAll;
	}


	public void setDisabledAll(boolean disabledAll) {
		this.disabledAll = disabledAll;
	}


	public List<TbSysWorkGroup> getListaWorkGroup() {
		
		preparaListWokGroup();
		
		return listaWorkGroup;
	}


	public void setListaWorkGroup(List<TbSysWorkGroup> listaWorkGroup) {
		this.listaWorkGroup = listaWorkGroup;
	}


	public List<TbAdmProductType> getListaProductTypes() {
		
		if(listaProductTypes == null || listaProductTypes.isEmpty()){
			preparaListaProductType();
		}
		
		return listaProductTypes;
	}


	public void setListaProductTypes(List<TbAdmProductType> listaProductTypes) {
		this.listaProductTypes = listaProductTypes;
	}


	public String getTaxModelSelecionado() {
		return taxModelSelecionado;
	}


	public void setTaxModelSelecionado(String taxModelSelecionado) {
		this.taxModelSelecionado = taxModelSelecionado;
	}


	public Double getDiscountTaxModel() {
		return discountTaxModel;
	}


	public void setDiscountTaxModel(Double discountTaxModel) {
		this.discountTaxModel = discountTaxModel;
	}


	public boolean isDelete() {
		return delete;
	}


	public void setDelete(boolean delete) {
		this.delete = delete;
	}


	public boolean isCheckedDiscountPT() {
		return checkedDiscountPT;
	}


	public void setCheckedDiscountPT(boolean checkedDiscountPT) {
		this.checkedDiscountPT = checkedDiscountPT;
	}


	public TbTaxQuoteItem getItemSelecionado() {
		return itemSelecionado;
	}


	public void setItemSelecionado(TbTaxQuoteItem itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}


	public String getPaiSelecionado() {
		return paiSelecionado;
	}


	public void setPaiSelecionado(String paiSelecionado) {
		this.paiSelecionado = paiSelecionado;
	}


	public List<TbTaxQuoteItem> getListaQuoteItemPais() {
		return listaQuoteItemPais;
	}


	public void setListaQuoteItemPais(List<TbTaxQuoteItem> listaQuoteItemPais) {
		this.listaQuoteItemPais = listaQuoteItemPais;
	}


	public boolean isFlagQuoteItemOriginal() {
		return flagQuoteItemOriginal;
	}


	public void setFlagQuoteItemOriginal(boolean flagQuoteItemOriginal) {
		this.flagQuoteItemOriginal = flagQuoteItemOriginal;
	}


	public String getNcmSelecionado() {
		return ncmSelecionado;
	}


	public void setNcmSelecionado(String ncmSelecionado) {
		this.ncmSelecionado = ncmSelecionado;
	}


	public boolean isFlagQuoteNcmOriginal() {
		return flagQuoteNcmOriginal;
	}


	public void setFlagQuoteNcmOriginal(boolean flagQuoteNcmOriginal) {
		this.flagQuoteNcmOriginal = flagQuoteNcmOriginal;
	}


	public String getPersonSelecionadoSalesRep() {
		return personSelecionadoSalesRep;
	}


	public void setPersonSelecionadoSalesRep(String personSelecionadoSalesRep) {
		this.personSelecionadoSalesRep = personSelecionadoSalesRep;
	}


	public String getsCustomerClone() {
		
		if(quoteSelecionado != null){
			
			sCustomerClone = quoteSelecionado.getsCustomer();
			
		}else{
			sCustomerClone = "";
		}
		
		return sCustomerClone;
	}


	public void setsCustomerClone(String sCustomerClone) {
		this.sCustomerClone = sCustomerClone;
	}


	public String getsQuoteNumberClone() {
		
		if(quoteSelecionado != null){
			
			sQuoteNumberClone = "C"+quoteSelecionado.getsQuoteNumber();
			
		}else{
			sQuoteNumberClone = "";
		}
		
		return sQuoteNumberClone;
	}


	public void setsQuoteNumberClone(String sQuoteNumberClone) {
		this.sQuoteNumberClone = sQuoteNumberClone;
	}


	public String getsQuoteNameClone() {
		
		if(quoteSelecionado != null){
			
			sQuoteNameClone = quoteSelecionado.getsQuoteName();
			
		}else{
			sQuoteNameClone = "";
		}
		
		return sQuoteNameClone;
	}


	public void setsQuoteNameClone(String sQuoteNameClone) {
		this.sQuoteNameClone = sQuoteNameClone;
	}


	public String getDestinoSelecionado() {
		return destinoSelecionado;
	}


	public void setDestinoSelecionado(String destinoSelecionado) {
		this.destinoSelecionado = destinoSelecionado;
	}


	public String getsIeBillClone() {
		return sIeBillClone;
	}


	public void setsIeBillClone(String sIeBillClone) {
		this.sIeBillClone = sIeBillClone;
	}


	public boolean isfLeasingClone() {
		return fLeasingClone;
	}


	public void setfLeasingClone(boolean fLeasingClone) {
		this.fLeasingClone = fLeasingClone;
	}


	public boolean isfSuframaClone() {
		return fSuframaClone;
	}


	public void setfSuframaClone(boolean fSuframaClone) {
		this.fSuframaClone = fSuframaClone;
	}


	public boolean isfContrestClone() {
		return fContrestClone;
	}


	public void setfContrestClone(boolean fContrestClone) {
		this.fContrestClone = fContrestClone;
	}


	public boolean isfPartnerClone() {
		return fPartnerClone;
	}


	public void setfPartnerClone(boolean fPartnerClone) {
		this.fPartnerClone = fPartnerClone;
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

	
	public List<TbAdmDestination> getListaDestinations() {
		
		if(listaDestinations.size() == 0){
			preparaListaDestination();
		}
		
		return listaDestinations;
	}

	public void setListaDestinations(List<TbAdmDestination> listaDestinations) {
		this.listaDestinations = listaDestinations;
	}


	public boolean isOldVersion() {
		return oldVersion;
	}


	public void setOldVersion(boolean oldVersion) {
		this.oldVersion = oldVersion;
	}


	public List<TbTaxQuote> getListaQuoteVersions() {
		return listaQuoteVersions;
	}


	public void setListaQuoteVersions(List<TbTaxQuote> listaQuoteVersions) {
		this.listaQuoteVersions = listaQuoteVersions;
	}


	public TbTaxQuote getQuoteVersion() {
		return quoteVersion;
	}


	public void setQuoteVersion(TbTaxQuote quoteVersion) {
		this.quoteVersion = quoteVersion;
	}


	public QuoteModel getQuoteModelVersion() {
		return quoteModelVersion;
	}


	public void setQuoteModelVersion(QuoteModel quoteModelVersion) {
		this.quoteModelVersion = quoteModelVersion;
	}	

	
	
//	public List<TbTaxQuoteItem> getListaQuoteItem() {
//		
//		if(listaQuoteItem.size() == 0){
//			preparaListaQuoteItem();
//		}
//		
//		return listaQuoteItem;
//	}


//	public void setListaQuoteItem(List<TbTaxQuoteItem> listaQuoteItem) {
//		this.listaQuoteItem = listaQuoteItem;
//	}	
	
	
	
}
