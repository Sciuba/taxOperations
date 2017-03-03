package br.com.operations.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;


/**
 * The persistent class for the TBTAXQUOTEITEM database table.
 * 
 */
@Entity
@NamedQuery(name="TbTaxQuoteItem.findAll", query="SELECT t FROM TbTaxQuoteItem t")
public class TbTaxQuoteItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBTAXQUOTEITEM_ID_GENERATOR", sequenceName="SEQ_TBTAXQUOTEITEM", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBTAXQUOTEITEM_ID_GENERATOR")
	private long id;

	private boolean fAvaliable;

	private boolean fIssEspecial;
	
	private boolean fItemPaiManual;
	
	private boolean fItemNcmManual;	
	
	private boolean fItemIndependent;
	
	private boolean fLmType;

	private boolean fPpb;

	private boolean fQuote;

	private boolean fReady;

	private boolean fSubstituicaoTributariaEntrada;

	private boolean fSubstituicaoTributariaSaida;

	private Integer iCalculationModel;

	@ManyToOne
	@JoinColumn(name="IDADMDESTINATION")
	private TbAdmDestination tbAdmDestination;

	@ManyToOne
	@JoinColumn(name="IDADMDISCCLASS")
	private TbAdmDiscClass tbAdmDiscClass;

	@ManyToOne
	@JoinColumn(name="IDADMMATERIAL")
	private TbAdmMaterial tbAdmMaterial;

	@ManyToOne
	@JoinColumn(name="IDADMMATERIALCLASS")
	private TbAdmMaterialClass tbAdmMaterialClass;

	@ManyToOne
	@JoinColumn(name="IDADMMATERIALCLASSORIGINAL")
	private TbAdmMaterialClass tbAdmMaterialClassOriginal;
	
	@ManyToOne
	@JoinColumn(name="IDADMMATERIALCLASSLOCAL")
	private TbAdmMaterialClass tbAdmMaterialClassLocal;
	
	@ManyToOne
	@JoinColumn(name="IDADMMATERIALLOCALMANUF")
	private TbAdmMaterialManufacturer tbAdmMaterialManufacturer;

	@ManyToOne
	@JoinColumn(name="IDADMORIGIN")
	private TbAdmOrigin tbAdmOrigin;

	@ManyToOne
	@JoinColumn(name="IDADMPRODUCTTYPE")
	private TbAdmProductType tbAdmProductType;

	@ManyToOne
	@JoinColumn(name="IDTAXQUOTEITEMPAI")
	private TbTaxQuoteItem tbTaxQuoteItem;
	
	@ManyToOne
	@JoinColumn(name="IDTAXQUOTEITEMPAIORIGINAL")
	private TbTaxQuoteItem tbTaxQuoteItemOriginal;

	@OneToMany(mappedBy="tbTaxQuoteItem")
	private List<TbTaxQuoteItem> tbTaxQuoteItems;
	
	private Integer iLineNumber;

	private Integer iMonthDurationSupport;
	
	private String sOrdem;

	private String sGroupNumber;

	private Integer iRowCount;

	private Integer iTaxModel;

	private Double mSupportNetPrice;
	
	private Double mAdditionalCostValueDollar;

	private Double mBaseCalculoSt;

	private Double mCofins;

	private Double mCostCalculated;

	private Double mCostMaterial;

	private Double mCssl;

	private Double mDealPrice;

	private Double mDollarRateAdditionalCost;

	private Double mFatorCustoSt;

	private Double mFatorEntrada;

	private Double mGrossPrice;

	private Double mIcmsARecuperar;

	private Double mIcmsEstadoDestino;

	private Double mIcmsEstadoOrigem;

	private Double mIcmsEstadoOrigemInterno;

	private Double mIcmsInterEstadual;

	private Double mIcmsInterEstadualMatImport;

	private Double mIcmsMaterialClass;

	private Double mIcmsProprioARecuperar;

	private Double mIcmsSt;

	private Double mImportedCost;

	private Double mImportedDealPrice;

	private Double mImportedListPrice;

	private Double mIpi;

	private Double mIpiReceber;

	private Double mIr;

	private Double mIss;

	private Double mNetPrice;

	private Double mNetPriceBaseDeCalculo;

	private Double mPis;

	private Double mSuggestedMargin;

	private Double mSuggestedNetPrice;

	private Double mTotalTaxes;

	private Double mUnitCofins;

	private Double mUnitCssl;

	private Double mUnitGrossPrice;

	private Double mUnitIcmsARecuperar;

	private Double mUnitIcmsEstadoDestino;

	private Double mUnitIcmsEstadoOrigem;

	private Double mUnitIcmsEstadoOrigemInterno;

	private Double mUnitIcmsInterestadual;

	private Double mUnitIcmsInterestMatImport;

	private Double mUnitIcmsMaterialClass;

	private Double mUnitIcmsProprioARecuperar;

	private Double mUnitIcmsSt;

	private Double mUnitIpi;

	private Double mUnitIpiRecoverable;

	private Double mUnitIr;

	private Double mUnitIss;

	private Double mUnitNetPrice;

	private Double mUnitNetPriceBaseDeCalculo;

	private Double mUnitPis;

	private Double mUnitSuggestedNetPrice;

	private Float rCofins;

	private Float rCssl;

	private Float rDiscount;

	private Float rFatorCusto;

	private Float rFatorCustost;

	private Float rFatorDisconto;

	private Float rFatorSaida;

	private Float rIcmsEstadoDestino;

	private Float rIcmsEstadoOrigem;

	private Float rIcmsEstadoOrigemInterno;

	private Float rIcmsInterestadual;

	private Float rIcmsIntereStlMatImport;

	private Float rIcmsIntereStMatClassOrigin;

	private Float rIcmsIntereStMatlClassDest;

	private Float rIcmsInterno;

	private Float rIcmsMaterialClass;

	private Float rIcmsProprioARecuperar;

	private Float rIcmsRecuperar;

	private Float rImpDiscount;

	private Float rIpi;

	private Float rIpiRecuperar;

	private Float rIr;

	private Float rIss;

	private Float rIva;

	private Float rIvaMaterialImportado;

	private Float rMarginItem;

	private Float rPis;

	private Float rQty;

	private Float rSuggestedMargin;

	private Float rTaxesRecoverableMargin;

	private String sCalculationMemoryMaterial;

	private String sCodeDiscClass;

	private String sDescription;

	private String sImpDescription;

	private String sLabelProductType;

	private String sModel;

	private String sNcm;
	
	private String sNcmOriginal;
	
	@Column(name="SIMPORTMODEL")
	private String sImportModel;
	
	@Column(name="SIMPORTNAMEDPRODUCTCAT")
	private String sImportNamedProductCategory;
	
	@Column(name="SIMPORTDISCCAT")
	private String sImportDiscCat;
	
	@Column(name="SIMPORTLINEBUSINESS")
	private String sImportLineBusiness;
	
	@Column(name="SIMPORTUNITPRICE")
	private String sImportUnitPrice;
	
	@Column(name="SIMPORTGROSSREVENUE")
	private String sImportGrossRevenue;
	
	@Column(name="SIMPORTCONTRACTUALDISC")
	private String sImportContractualDisc;
	
	@Column(name="SIMPORTADRDISCOUNT")
	private String sImportAdrDiscount;
	
	@Column(name="SIMPORTTOTALDISC")
	private String sImportTotalDisc;
	
	@Column(name="SIMPORTESCALATION")
	private String sImportEscalation;
	
	@Column(name="SIMPESTIMATEDSALESCRED")
	private String sImportEstimatedSalesCredit;
	
	@Column(name="SIMPESTIMATEDSALESCREDITPERC")
	private String sImportEstimatedSalesCreditPercent;
	
	
	//bi-directional many-to-one association to TbTaxQuote
	@ManyToOne
	@JoinColumn(name="IDTAXQUOTE")
	private TbTaxQuote tbTaxQuote;

	
	@Transient
	private List<TbTaxQuoteItem> listaQuotesForGroup;
	
	@Transient
	private String style;
		
	public TbTaxQuoteItem() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isfAvaliable() {
		return fAvaliable;
	}

	public void setfAvaliable(boolean fAvaliable) {
		this.fAvaliable = fAvaliable;
	}

	public boolean isfIssEspecial() {
		return fIssEspecial;
	}

	public void setfIssEspecial(boolean fIssEspecial) {
		this.fIssEspecial = fIssEspecial;
	}

	public boolean isfLmType() {
		return fLmType;
	}

	public void setfLmType(boolean fLmType) {
		this.fLmType = fLmType;
	}

	public boolean isfPpb() {
		return fPpb;
	}

	public void setfPpb(boolean fPpb) {
		this.fPpb = fPpb;
	}

	public boolean isfQuote() {
		return fQuote;
	}

	public void setfQuote(boolean fQuote) {
		this.fQuote = fQuote;
	}

	public boolean isfReady() {
		return fReady;
	}

	public void setfReady(boolean fReady) {
		this.fReady = fReady;
	}

	public boolean isfSubstituicaoTributariaEntrada() {
		return fSubstituicaoTributariaEntrada;
	}

	public void setfSubstituicaoTributariaEntrada(
			boolean fSubstituicaoTributariaEntrada) {
		this.fSubstituicaoTributariaEntrada = fSubstituicaoTributariaEntrada;
	}

	public boolean isfSubstituicaoTributariaSaida() {
		return fSubstituicaoTributariaSaida;
	}

	public void setfSubstituicaoTributariaSaida(boolean fSubstituicaoTributariaSaida) {
		this.fSubstituicaoTributariaSaida = fSubstituicaoTributariaSaida;
	}

	public Integer getiCalculationModel() {
		return iCalculationModel;
	}

	public void setiCalculationModel(Integer iCalculationModel) {
		this.iCalculationModel = iCalculationModel;
	}

	public TbAdmDestination getTbAdmDestination() {
		return tbAdmDestination;
	}

	public void setTbAdmDestination(TbAdmDestination tbAdmDestination) {
		this.tbAdmDestination = tbAdmDestination;
	}

	public TbAdmDiscClass getTbAdmDiscClass() {
		return tbAdmDiscClass;
	}

	public void setTbAdmDiscClass(TbAdmDiscClass tbAdmDiscClass) {
		this.tbAdmDiscClass = tbAdmDiscClass;
	}

	public TbAdmMaterial getTbAdmMaterial() {
		return tbAdmMaterial;
	}

	public void setTbAdmMaterial(TbAdmMaterial tbAdmMaterial) {
		this.tbAdmMaterial = tbAdmMaterial;
	}

	public TbAdmMaterialClass getTbAdmMaterialClass() {
		return tbAdmMaterialClass;
	}

	public void setTbAdmMaterialClass(TbAdmMaterialClass tbAdmMaterialClass) {
		this.tbAdmMaterialClass = tbAdmMaterialClass;
	}

	public TbAdmMaterialManufacturer getTbAdmMaterialManufacturer() {
		return tbAdmMaterialManufacturer;
	}

	public void setTbAdmMaterialManufacturer(
			TbAdmMaterialManufacturer tbAdmMaterialManufacturer) {
		this.tbAdmMaterialManufacturer = tbAdmMaterialManufacturer;
	}

	public TbAdmOrigin getTbAdmOrigin() {
		return tbAdmOrigin;
	}

	public void setTbAdmOrigin(TbAdmOrigin tbAdmOrigin) {
		this.tbAdmOrigin = tbAdmOrigin;
	}

	public TbAdmProductType getTbAdmProductType() {
		return tbAdmProductType;
	}

	public void setTbAdmProductType(TbAdmProductType tbAdmProductType) {
		this.tbAdmProductType = tbAdmProductType;
	}

	public TbTaxQuoteItem getTbTaxQuoteItem() {
		return tbTaxQuoteItem;
	}

	public void setTbTaxQuoteItem(TbTaxQuoteItem tbTaxQuoteItem) {
		this.tbTaxQuoteItem = tbTaxQuoteItem;
	}

	public List<TbTaxQuoteItem> getTbTaxQuoteItems() {
		return tbTaxQuoteItems;
	}

	public void setTbTaxQuoteItems(List<TbTaxQuoteItem> tbTaxQuoteItems) {
		this.tbTaxQuoteItems = tbTaxQuoteItems;
	}

	public Integer getiLineNumber() {
		return iLineNumber;
	}

	public void setiLineNumber(Integer iLineNumber) {
		this.iLineNumber = iLineNumber;
	}

	public String getsGroupNumber() {
		return sGroupNumber;
	}

	public void setsGroupNumber(String sGroupNumber) {
		this.sGroupNumber = sGroupNumber;
	}

	public Integer getiRowCount() {
		return iRowCount;
	}

	public void setiRowCount(Integer iRowCount) {
		this.iRowCount = iRowCount;
	}

	public Integer getiTaxModel() {
		return iTaxModel;
	}

	public void setiTaxModel(Integer iTaxModel) {
		this.iTaxModel = iTaxModel;
	}

	public Double getmAdditionalCostValueDollar() {
		return mAdditionalCostValueDollar;
	}

	public void setmAdditionalCostValueDollar(Double mAdditionalCostValueDollar) {
		this.mAdditionalCostValueDollar = mAdditionalCostValueDollar;
	}

	public Double getmBaseCalculoSt() {
		return mBaseCalculoSt;
	}

	public void setmBaseCalculoSt(Double mBaseCalculoSt) {
		this.mBaseCalculoSt = mBaseCalculoSt;
	}

	public Double getmCofins() {
		return mCofins;
	}

	public void setmCofins(Double mCofins) {
		this.mCofins = mCofins;
	}

	public Double getmCostCalculated() {
		return mCostCalculated;
	}

	public void setmCostCalculated(Double mCostCalculated) {
		this.mCostCalculated = mCostCalculated;
	}

	public Double getmCostMaterial() {
		return mCostMaterial;
	}

	public void setmCostMaterial(Double mCostMaterial) {
		this.mCostMaterial = mCostMaterial;
	}

	public Double getmCssl() {
		return mCssl;
	}

	public void setmCssl(Double mCssl) {
		this.mCssl = mCssl;
	}

	public Double getmDealPrice() {
		return mDealPrice;
	}

	public void setmDealPrice(Double mDealPrice) {
		this.mDealPrice = mDealPrice;
	}

	public Double getmDollarRateAdditionalCost() {
		return mDollarRateAdditionalCost;
	}

	public void setmDollarRateAdditionalCost(Double mDollarRateAdditionalCost) {
		this.mDollarRateAdditionalCost = mDollarRateAdditionalCost;
	}

	public Double getmFatorCustoSt() {
		return mFatorCustoSt;
	}

	public void setmFatorCustoSt(Double mFatorCustoSt) {
		this.mFatorCustoSt = mFatorCustoSt;
	}

	public Double getmFatorEntrada() {
		return mFatorEntrada;
	}

	public void setmFatorEntrada(Double mFatorEntrada) {
		this.mFatorEntrada = mFatorEntrada;
	}

	public Double getmGrossPrice() {
		return mGrossPrice;
	}

	public void setmGrossPrice(Double mGrossPrice) {
		this.mGrossPrice = mGrossPrice;
	}

	public Double getmIcmsARecuperar() {
		return mIcmsARecuperar;
	}

	public void setmIcmsARecuperar(Double mIcmsARecuperar) {
		this.mIcmsARecuperar = mIcmsARecuperar;
	}

	public Double getmIcmsEstadoDestino() {
		return mIcmsEstadoDestino;
	}

	public void setmIcmsEstadoDestino(Double mIcmsEstadoDestino) {
		this.mIcmsEstadoDestino = mIcmsEstadoDestino;
	}

	public Double getmIcmsEstadoOrigem() {
		return mIcmsEstadoOrigem;
	}

	public void setmIcmsEstadoOrigem(Double mIcmsEstadoOrigem) {
		this.mIcmsEstadoOrigem = mIcmsEstadoOrigem;
	}

	public Double getmIcmsEstadoOrigemInterno() {
		return mIcmsEstadoOrigemInterno;
	}

	public void setmIcmsEstadoOrigemInterno(Double mIcmsEstadoOrigemInterno) {
		this.mIcmsEstadoOrigemInterno = mIcmsEstadoOrigemInterno;
	}

	public Double getmIcmsInterEstadual() {
		return mIcmsInterEstadual;
	}

	public void setmIcmsInterEstadual(Double mIcmsInterEstadual) {
		this.mIcmsInterEstadual = mIcmsInterEstadual;
	}

	public Double getmIcmsInterEstadualMatImport() {
		return mIcmsInterEstadualMatImport;
	}

	public void setmIcmsInterEstadualMatImport(Double mIcmsInterEstadualMatImport) {
		this.mIcmsInterEstadualMatImport = mIcmsInterEstadualMatImport;
	}

	public Double getmIcmsMaterialClass() {
		return mIcmsMaterialClass;
	}

	public void setmIcmsMaterialClass(Double mIcmsMaterialClass) {
		this.mIcmsMaterialClass = mIcmsMaterialClass;
	}

	public Double getmIcmsProprioARecuperar() {
		return mIcmsProprioARecuperar;
	}

	public void setmIcmsProprioARecuperar(Double mIcmsProprioARecuperar) {
		this.mIcmsProprioARecuperar = mIcmsProprioARecuperar;
	}

	public Double getmIcmsSt() {
		return mIcmsSt;
	}

	public void setmIcmsSt(Double mIcmsSt) {
		this.mIcmsSt = mIcmsSt;
	}

	public Double getmImportedCost() {
		return mImportedCost;
	}

	public void setmImportedCost(Double mImportedCost) {
		this.mImportedCost = mImportedCost;
	}

	public Double getmImportedDealPrice() {
		return mImportedDealPrice;
	}

	public void setmImportedDealPrice(Double mImportedDealPrice) {
		this.mImportedDealPrice = mImportedDealPrice;
	}

	public Double getmImportedListPrice() {
		return mImportedListPrice;
	}

	public void setmImportedListPrice(Double mImportedListPrice) {
		this.mImportedListPrice = mImportedListPrice;
	}

	public Double getmIpi() {
		return mIpi;
	}

	public void setmIpi(Double mIpi) {
		this.mIpi = mIpi;
	}

	public Double getmIpiReceber() {
		return mIpiReceber;
	}

	public void setmIpiReceber(Double mIpiReceber) {
		this.mIpiReceber = mIpiReceber;
	}

	public Double getmIr() {
		return mIr;
	}

	public void setmIr(Double mIr) {
		this.mIr = mIr;
	}

	public Double getmIss() {
		return mIss;
	}

	public void setmIss(Double mIss) {
		this.mIss = mIss;
	}

	public Double getmNetPrice() {
		return mNetPrice;
	}

	public void setmNetPrice(Double mNetPrice) {
		this.mNetPrice = mNetPrice;
	}

	public Double getmNetPriceBaseDeCalculo() {
		return mNetPriceBaseDeCalculo;
	}

	public void setmNetPriceBaseDeCalculo(Double mNetPriceBaseDeCalculo) {
		this.mNetPriceBaseDeCalculo = mNetPriceBaseDeCalculo;
	}

	public Double getmPis() {
		return mPis;
	}

	public void setmPis(Double mPis) {
		this.mPis = mPis;
	}

	public Double getmSuggestedMargin() {
		return mSuggestedMargin;
	}

	public void setmSuggestedMargin(Double mSuggestedMargin) {
		this.mSuggestedMargin = mSuggestedMargin;
	}

	public Double getmSuggestedNetPrice() {
		return mSuggestedNetPrice;
	}

	public void setmSuggestedNetPrice(Double mSuggestedNetPrice) {
		this.mSuggestedNetPrice = mSuggestedNetPrice;
	}

	public Double getmTotalTaxes() {
		return mTotalTaxes;
	}

	public void setmTotalTaxes(Double mTotalTaxes) {
		this.mTotalTaxes = mTotalTaxes;
	}

	public Double getmUnitCofins() {
		return mUnitCofins;
	}

	public void setmUnitCofins(Double mUnitCofins) {
		this.mUnitCofins = mUnitCofins;
	}

	public Double getmUnitCssl() {
		return mUnitCssl;
	}

	public void setmUnitCssl(Double mUnitCssl) {
		this.mUnitCssl = mUnitCssl;
	}

	public Double getmUnitGrossPrice() {
		return mUnitGrossPrice;
	}

	public void setmUnitGrossPrice(Double mUnitGrossPrice) {
		this.mUnitGrossPrice = mUnitGrossPrice;
	}

	public Double getmUnitIcmsARecuperar() {
		return mUnitIcmsARecuperar;
	}

	public void setmUnitIcmsARecuperar(Double mUnitIcmsARecuperar) {
		this.mUnitIcmsARecuperar = mUnitIcmsARecuperar;
	}

	public Double getmUnitIcmsEstadoDestino() {
		return mUnitIcmsEstadoDestino;
	}

	public void setmUnitIcmsEstadoDestino(Double mUnitIcmsEstadoDestino) {
		this.mUnitIcmsEstadoDestino = mUnitIcmsEstadoDestino;
	}

	public Double getmUnitIcmsEstadoOrigem() {
		return mUnitIcmsEstadoOrigem;
	}

	public void setmUnitIcmsEstadoOrigem(Double mUnitIcmsEstadoOrigem) {
		this.mUnitIcmsEstadoOrigem = mUnitIcmsEstadoOrigem;
	}

	public Double getmUnitIcmsEstadoOrigemInterno() {
		return mUnitIcmsEstadoOrigemInterno;
	}

	public void setmUnitIcmsEstadoOrigemInterno(Double mUnitIcmsEstadoOrigemInterno) {
		this.mUnitIcmsEstadoOrigemInterno = mUnitIcmsEstadoOrigemInterno;
	}

	public Double getmUnitIcmsInterestadual() {
		return mUnitIcmsInterestadual;
	}

	public void setmUnitIcmsInterestadual(Double mUnitIcmsInterestadual) {
		this.mUnitIcmsInterestadual = mUnitIcmsInterestadual;
	}

	public Double getmUnitIcmsInterestMatImport() {
		return mUnitIcmsInterestMatImport;
	}

	public void setmUnitIcmsInterestMatImport(Double mUnitIcmsInterestMatImport) {
		this.mUnitIcmsInterestMatImport = mUnitIcmsInterestMatImport;
	}

	public Double getmUnitIcmsMaterialClass() {
		return mUnitIcmsMaterialClass;
	}

	public void setmUnitIcmsMaterialClass(Double mUnitIcmsMaterialClass) {
		this.mUnitIcmsMaterialClass = mUnitIcmsMaterialClass;
	}

	public Double getmUnitIcmsProprioARecuperar() {
		return mUnitIcmsProprioARecuperar;
	}

	public void setmUnitIcmsProprioARecuperar(Double mUnitIcmsProprioARecuperar) {
		this.mUnitIcmsProprioARecuperar = mUnitIcmsProprioARecuperar;
	}

	public Double getmUnitIcmsSt() {
		return mUnitIcmsSt;
	}

	public void setmUnitIcmsSt(Double mUnitIcmsSt) {
		this.mUnitIcmsSt = mUnitIcmsSt;
	}

	public Double getmUnitIpi() {
		return mUnitIpi;
	}

	public void setmUnitIpi(Double mUnitIpi) {
		this.mUnitIpi = mUnitIpi;
	}

	public Double getmUnitIpiRecoverable() {
		return mUnitIpiRecoverable;
	}

	public void setmUnitIpiRecoverable(Double mUnitIpiRecoverable) {
		this.mUnitIpiRecoverable = mUnitIpiRecoverable;
	}

	public Double getmUnitIr() {
		return mUnitIr;
	}

	public void setmUnitIr(Double mUnitIr) {
		this.mUnitIr = mUnitIr;
	}

	public Double getmUnitIss() {
		return mUnitIss;
	}

	public void setmUnitIss(Double mUnitIss) {
		this.mUnitIss = mUnitIss;
	}

	public Double getmUnitNetPrice() {
		return mUnitNetPrice;
	}

	public void setmUnitNetPrice(Double mUnitNetPrice) {
		this.mUnitNetPrice = mUnitNetPrice;
	}

	public Double getmUnitNetPriceBaseDeCalculo() {
		return mUnitNetPriceBaseDeCalculo;
	}

	public void setmUnitNetPriceBaseDeCalculo(Double mUnitNetPriceBaseDeCalculo) {
		this.mUnitNetPriceBaseDeCalculo = mUnitNetPriceBaseDeCalculo;
	}

	public Double getmUnitPis() {
		return mUnitPis;
	}

	public void setmUnitPis(Double mUnitPis) {
		this.mUnitPis = mUnitPis;
	}

	public Double getmUnitSuggestedNetPrice() {
		return mUnitSuggestedNetPrice;
	}

	public void setmUnitSuggestedNetPrice(Double mUnitSuggestedNetPrice) {
		this.mUnitSuggestedNetPrice = mUnitSuggestedNetPrice;
	}

	public Float getrCofins() {
		return rCofins;
	}

	public void setrCofins(Float rCofins) {
		this.rCofins = rCofins;
	}

	public Float getrCssl() {
		return rCssl;
	}

	public void setrCssl(Float rCssl) {
		this.rCssl = rCssl;
	}

	public Float getrDiscount() {
		return rDiscount;
	}

	public void setrDiscount(Float rDiscount) {
		this.rDiscount = rDiscount;
	}

	public Float getrFatorCusto() {
		return rFatorCusto;
	}

	public void setrFatorCusto(Float rFatorCusto) {
		this.rFatorCusto = rFatorCusto;
	}

	public Float getrFatorCustost() {
		return rFatorCustost;
	}

	public void setrFatorCustost(Float rFatorCustost) {
		this.rFatorCustost = rFatorCustost;
	}

	public Float getrFatorDisconto() {
		return rFatorDisconto;
	}

	public void setrFatorDisconto(Float rFatorDisconto) {
		this.rFatorDisconto = rFatorDisconto;
	}

	public Float getrFatorSaida() {
		return rFatorSaida;
	}

	public void setrFatorSaida(Float rFatorSaida) {
		this.rFatorSaida = rFatorSaida;
	}

	public Float getrIcmsEstadoDestino() {
		return rIcmsEstadoDestino;
	}

	public void setrIcmsEstadoDestino(Float rIcmsEstadoDestino) {
		this.rIcmsEstadoDestino = rIcmsEstadoDestino;
	}

	public Float getrIcmsEstadoOrigem() {
		return rIcmsEstadoOrigem;
	}

	public void setrIcmsEstadoOrigem(Float rIcmsEstadoOrigem) {
		this.rIcmsEstadoOrigem = rIcmsEstadoOrigem;
	}

	public Float getrIcmsEstadoOrigemInterno() {
		return rIcmsEstadoOrigemInterno;
	}

	public void setrIcmsEstadoOrigemInterno(Float rIcmsEstadoOrigemInterno) {
		this.rIcmsEstadoOrigemInterno = rIcmsEstadoOrigemInterno;
	}

	public Float getrIcmsInterestadual() {
		return rIcmsInterestadual;
	}

	public void setrIcmsInterestadual(Float rIcmsInterestadual) {
		this.rIcmsInterestadual = rIcmsInterestadual;
	}

	public Float getrIcmsIntereStlMatImport() {
		return rIcmsIntereStlMatImport;
	}

	public void setrIcmsIntereStlMatImport(Float rIcmsIntereStlMatImport) {
		this.rIcmsIntereStlMatImport = rIcmsIntereStlMatImport;
	}

	public Float getrIcmsIntereStMatClassOrigin() {
		return rIcmsIntereStMatClassOrigin;
	}

	public void setrIcmsIntereStMatClassOrigin(Float rIcmsIntereStMatClassOrigin) {
		this.rIcmsIntereStMatClassOrigin = rIcmsIntereStMatClassOrigin;
	}

	public Float getrIcmsIntereStMatlClassDest() {
		return rIcmsIntereStMatlClassDest;
	}

	public void setrIcmsIntereStMatlClassDest(Float rIcmsIntereStMatlClassDest) {
		this.rIcmsIntereStMatlClassDest = rIcmsIntereStMatlClassDest;
	}

	public Float getrIcmsInterno() {
		return rIcmsInterno;
	}

	public void setrIcmsInterno(Float rIcmsInterno) {
		this.rIcmsInterno = rIcmsInterno;
	}

	public Float getrIcmsMaterialClass() {
		return rIcmsMaterialClass;
	}

	public void setrIcmsMaterialClass(Float rIcmsMaterialClass) {
		this.rIcmsMaterialClass = rIcmsMaterialClass;
	}

	public Float getrIcmsProprioARecuperar() {
		return rIcmsProprioARecuperar;
	}

	public void setrIcmsProprioARecuperar(Float rIcmsProprioARecuperar) {
		this.rIcmsProprioARecuperar = rIcmsProprioARecuperar;
	}

	public Float getrIcmsRecuperar() {
		return rIcmsRecuperar;
	}

	public void setrIcmsRecuperar(Float rIcmsRecuperar) {
		this.rIcmsRecuperar = rIcmsRecuperar;
	}

	public Float getrImpDiscount() {
		return rImpDiscount;
	}

	public void setrImpDiscount(Float rImpDiscount) {
		this.rImpDiscount = rImpDiscount;
	}

	public Float getrIpi() {
		return rIpi;
	}

	public void setrIpi(Float rIpi) {
		this.rIpi = rIpi;
	}

	public Float getrIpiRecuperar() {
		return rIpiRecuperar;
	}

	public void setrIpiRecuperar(Float rIpiRecuperar) {
		this.rIpiRecuperar = rIpiRecuperar;
	}

	public Float getrIr() {
		return rIr;
	}

	public void setrIr(Float rIr) {
		this.rIr = rIr;
	}

	public Float getrIss() {
		return rIss;
	}

	public void setrIss(Float rIss) {
		this.rIss = rIss;
	}

	public Float getrIva() {
		return rIva;
	}

	public void setrIva(Float rIva) {
		this.rIva = rIva;
	}

	public Float getrIvaMaterialImportado() {
		return rIvaMaterialImportado;
	}

	public void setrIvaMaterialImportado(Float rIvaMaterialImportado) {
		this.rIvaMaterialImportado = rIvaMaterialImportado;
	}

	public Float getrMarginItem() {
		return rMarginItem;
	}

	public void setrMarginItem(Float rMarginItem) {
		this.rMarginItem = rMarginItem;
	}

	public Float getrPis() {
		return rPis;
	}

	public void setrPis(Float rPis) {
		this.rPis = rPis;
	}

	public Float getrQty() {
		return rQty;
	}

	public void setrQty(Float rQty) {
		this.rQty = rQty;
	}

	public Float getrSuggestedMargin() {
		return rSuggestedMargin;
	}

	public void setrSuggestedMargin(Float rSuggestedMargin) {
		this.rSuggestedMargin = rSuggestedMargin;
	}

	public Float getrTaxesRecoverableMargin() {
		return rTaxesRecoverableMargin;
	}

	public void setrTaxesRecoverableMargin(Float rTaxesRecoverableMargin) {
		this.rTaxesRecoverableMargin = rTaxesRecoverableMargin;
	}

	public String getsCalculationMemoryMaterial() {
		return sCalculationMemoryMaterial;
	}

	public void setsCalculationMemoryMaterial(String sCalculationMemoryMaterial) {
		this.sCalculationMemoryMaterial = sCalculationMemoryMaterial;
	}

	public String getsCodeDiscClass() {
		return sCodeDiscClass;
	}

	public void setsCodeDiscClass(String sCodeDiscClass) {
		this.sCodeDiscClass = sCodeDiscClass;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public String getsImpDescription() {
		return sImpDescription;
	}

	public void setsImpDescription(String sImpDescription) {
		this.sImpDescription = sImpDescription;
	}

	public String getsLabelProductType() {
		return sLabelProductType;
	}

	public void setsLabelProductType(String sLabelProductType) {
		this.sLabelProductType = sLabelProductType;
	}

	public String getsModel() {
		return sModel;
	}

	public void setsModel(String sModel) {
		this.sModel = sModel;
	}

	public String getsNcm() {
		return sNcm;
	}

	public void setsNcm(String sNcm) {
		this.sNcm = sNcm;
	}

	public TbTaxQuote getTbTaxQuote() {
		return tbTaxQuote;
	}

	public void setTbTaxQuote(TbTaxQuote tbTaxQuote) {
		this.tbTaxQuote = tbTaxQuote;
	}

	public String getsOrdem() {
		return sOrdem;
	}

	public void setsOrdem(String sOrdem) {
		this.sOrdem = sOrdem;
	}

	public String getsImportModel() {
		return sImportModel;
	}

	public void setsImportModel(String sImportModel) {
		this.sImportModel = sImportModel;
	}

	public String getsImportNamedProductCategory() {
		return sImportNamedProductCategory;
	}

	public void setsImportNamedProductCategory(String sImportNamedProductCategory) {
		this.sImportNamedProductCategory = sImportNamedProductCategory;
	}

	public String getsImportDiscCat() {
		return sImportDiscCat;
	}

	public void setsImportDiscCat(String sImportDiscCat) {
		this.sImportDiscCat = sImportDiscCat;
	}

	public String getsImportLineBusiness() {
		return sImportLineBusiness;
	}

	public void setsImportLineBusiness(String sImportLineBusiness) {
		this.sImportLineBusiness = sImportLineBusiness;
	}

	public String getsImportUnitPrice() {
		return sImportUnitPrice;
	}

	public void setsImportUnitPrice(String sImportUnitPrice) {
		this.sImportUnitPrice = sImportUnitPrice;
	}

	public String getsImportGrossRevenue() {
		return sImportGrossRevenue;
	}

	public void setsImportGrossRevenue(String sImportGrossRevenue) {
		this.sImportGrossRevenue = sImportGrossRevenue;
	}

	public String getsImportContractualDisc() {
		return sImportContractualDisc;
	}

	public void setsImportContractualDisc(String sImportContractualDisc) {
		this.sImportContractualDisc = sImportContractualDisc;
	}

	public String getsImportAdrDiscount() {
		return sImportAdrDiscount;
	}

	public void setsImportAdrDiscount(String sImportAdrDiscount) {
		this.sImportAdrDiscount = sImportAdrDiscount;
	}

	public String getsImportTotalDisc() {
		return sImportTotalDisc;
	}

	public void setsImportTotalDisc(String sImportTotalDisc) {
		this.sImportTotalDisc = sImportTotalDisc;
	}

	public String getsImportEscalation() {
		return sImportEscalation;
	}

	public void setsImportEscalation(String sImportEscalation) {
		this.sImportEscalation = sImportEscalation;
	}

	public String getsImportEstimatedSalesCredit() {
		return sImportEstimatedSalesCredit;
	}

	public void setsImportEstimatedSalesCredit(String sImportEstimatedSalesCredit) {
		this.sImportEstimatedSalesCredit = sImportEstimatedSalesCredit;
	}

	public String getsImportEstimatedSalesCreditPercent() {
		return sImportEstimatedSalesCreditPercent;
	}

	public void setsImportEstimatedSalesCreditPercent(
			String sImportEstimatedSalesCreditPercent) {
		this.sImportEstimatedSalesCreditPercent = sImportEstimatedSalesCreditPercent;
	}

	public List<TbTaxQuoteItem> getListaQuotesForGroup() {
		return listaQuotesForGroup;
	}

	public void setListaQuotesForGroup(List<TbTaxQuoteItem> listaQuotesForGroup) {
		this.listaQuotesForGroup = listaQuotesForGroup;
	}

	public String getStyle() {
		
		if(tbAdmMaterialClass == null && tbAdmMaterial.getTbAdmMaterialClass() == null ){
			style = "color: #FF0000;";
		}else{
			style = "";
		}		
		
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Integer getiMonthDurationSupport() {
		return iMonthDurationSupport;
	}

	public void setiMonthDurationSupport(Integer iMonthDurationSupport) {
		this.iMonthDurationSupport = iMonthDurationSupport;
	}

	public Double getmSupportNetPrice() {
		return mSupportNetPrice;
	}

	public void setmSupportNetPrice(Double mSupportNetPrice) {
		this.mSupportNetPrice = mSupportNetPrice;
	}

	public TbAdmMaterialClass getTbAdmMaterialClassLocal() {
		return tbAdmMaterialClassLocal;
	}

	public void setTbAdmMaterialClassLocal(
			TbAdmMaterialClass tbAdmMaterialClassLocal) {
		this.tbAdmMaterialClassLocal = tbAdmMaterialClassLocal;
	}

	public TbTaxQuoteItem getTbTaxQuoteItemOriginal() {
		return tbTaxQuoteItemOriginal;
	}

	public void setTbTaxQuoteItemOriginal(TbTaxQuoteItem tbTaxQuoteItemOriginal) {
		this.tbTaxQuoteItemOriginal = tbTaxQuoteItemOriginal;
	}

	public boolean isfItemPaiManual() {
		return fItemPaiManual;
	}

	public void setfItemPaiManual(boolean fItemPaiManual) {
		this.fItemPaiManual = fItemPaiManual;
	}

	public boolean isfItemIndependent() {
		return fItemIndependent;
	}

	public void setfItemIndependent(boolean fItemIndependent) {
		this.fItemIndependent = fItemIndependent;
	}

	public String getsNcmOriginal() {
		return sNcmOriginal;
	}

	public void setsNcmOriginal(String sNcmOriginal) {
		this.sNcmOriginal = sNcmOriginal;
	}

	public boolean isfItemNcmManual() {
		return fItemNcmManual;
	}

	public void setfItemNcmManual(boolean fItemNcmManual) {
		this.fItemNcmManual = fItemNcmManual;
	}

	public TbAdmMaterialClass getTbAdmMaterialClassOriginal() {
		return tbAdmMaterialClassOriginal;
	}

	public void setTbAdmMaterialClassOriginal(
			TbAdmMaterialClass tbAdmMaterialClassOriginal) {
		this.tbAdmMaterialClassOriginal = tbAdmMaterialClassOriginal;
	}
	
	
	
}