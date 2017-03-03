package br.com.saboia.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the TBADMMATERIAL database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmMaterial.findAll", query="SELECT t FROM TbAdmMaterial t")
public class TbAdmMaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMMATERIAL_ID_GENERATOR", sequenceName="SEQ_TBADMMATERIAL", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMMATERIAL_ID_GENERATOR")
	private long id;

	private boolean fAvailable;

	private boolean fExigeSerialNumberParaSW;

	private boolean fKeepRepFloor;

	private boolean fVariacaoPartNumber;
	
	private Integer fOrigemDaMercadoria;

	@ManyToOne
	@JoinColumn(name="IDADMDISCCLASS")
	private TbAdmDiscClass tbAdmDiscClass;

	@ManyToOne
	@JoinColumn(name="IDADMMATERIALMANUFACTURER")
	private TbAdmMaterialManufacturer tbAdmMaterialManufacturer;
	
	@ManyToOne
	@JoinColumn(name="IDSYSUSER")
	private TbSysUser tbSysUser;

	private Double mCost;

	private Double mListPrice;

	private Double mRepFloorPrice;

	private Double mStandardCost;

	private String sDescription;

	private String sInternalModel;

	private String sManufacturerModel;
	
	@Column(name="SPRIMARYUNITMEASURE")
	private String sPrimaryUnitMeasure;
	
	@Column(name="SLIFECYCLEPHASE")
	private String sLifeCyclePhase;
	
	@Column(name="SITEMTYPE")
	private String sItemType;
	
	@Column(name="SITEMSTATUS")
	private String sItemStatus;
	
	@Column(name="SCOSTUMERORDERFLAG")
	private String sCostumerOrderFlag;
	
	@Column(name="SINTERNALORDERFLAG")
	private String sInternalOrderFlag;
	
	@Column(name="STRANSACTIONCONDITIONCLASS")
	private String sTransactionConditionClass;
	
	@Column(name="SITEMFISCALTYPE")
	private String sItemFiscalType;
	
	@Column(name="IFEDERALTRIBUTARYSITUATION")
	private Integer iFederalTributarysituation;
	
	@Column(name="ISTATETRIBUTARYSITUATION")
	private Integer iStateTributarySituation;
	
	@Column(name="DTITEMCREATIONDATE")
	@Temporal(TemporalType.DATE)
	private Date dtItemCreationDate;
	
	@Column(name="DTLASTUPDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtLastUpdate;
	
	@Column(name="SLINEBUSINESS")
	private String sLineBusiness;
	
	@Column(name="FTAXSUPPORTACTIVE")
	private boolean fTaxSupportActive;
	
	@Column(name="RTAXSUPPORT")
	private Float rTaxSupport;
	
	//bi-directional many-to-one association to TbAdmMaterialClass
	@ManyToOne
	@JoinColumn(name="IDADMMATERIALCLASS")
	private TbAdmMaterialClass tbAdmMaterialClass;

	@ManyToOne
	@JoinColumn(name="IDADMMATERIALCATEGORY")
	private TbAdmMaterialCategory tbAdmMaterialCategory;
	
	@ManyToOne
	@JoinColumn(name="IDADMMATERIALCATEGORYSUPPORT")
	private TbAdmMaterialCategorySupport tbAdmMaterialCategorySupport;
	
	@OneToMany(mappedBy="tbAdmMaterial")
	private List<TbTaxQuoteItem> tbTaxQuoteItems;
	
	@OneToOne(mappedBy="tbAdmMaterial")
	private TbAdmMaterialCategorySupport tbAdmMaterialCategorySup; 
	
	@Transient
	private boolean fVariacaoPartNumberDisabled;
	
	
	@Transient
	private String style;
	
	@Transient
	private boolean disabled;
	
	@Transient
	private String category;
	
	@Transient
	private String support;
	
	public TbAdmMaterial() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isfAvailable() {
		return fAvailable;
	}

	public void setfAvailable(boolean fAvailable) {
		this.fAvailable = fAvailable;
	}

	public boolean isfExigeSerialNumberParaSW() {
		return fExigeSerialNumberParaSW;
	}

	public void setfExigeSerialNumberParaSW(boolean fExigeSerialNumberParaSW) {
		this.fExigeSerialNumberParaSW = fExigeSerialNumberParaSW;
	}

	public boolean isfKeepRepFloor() {
		return fKeepRepFloor;
	}

	public void setfKeepRepFloor(boolean fKeepRepFloor) {
		this.fKeepRepFloor = fKeepRepFloor;
	}

	public Integer getfOrigemDaMercadoria() {
		return fOrigemDaMercadoria;
	}

	public void setfOrigemDaMercadoria(Integer fOrigemDaMercadoria) {
		this.fOrigemDaMercadoria = fOrigemDaMercadoria;
	}

	public TbAdmDiscClass getTbAdmDiscClass() {
		return tbAdmDiscClass;
	}

	public void setTbAdmDiscClass(TbAdmDiscClass tbAdmDiscClass) {
		this.tbAdmDiscClass = tbAdmDiscClass;
	}

	public TbAdmMaterialManufacturer getTbAdmMaterialManufacturer() {
		return tbAdmMaterialManufacturer;
	}

	public void setTbAdmMaterialManufacturer(
			TbAdmMaterialManufacturer tbAdmMaterialManufacturer) {
		this.tbAdmMaterialManufacturer = tbAdmMaterialManufacturer;
	}

	public Double getmCost() {
		return mCost;
	}

	public void setmCost(Double mCost) {
		this.mCost = mCost;
	}

	public Double getmListPrice() {
		return mListPrice;
	}

	public void setmListPrice(Double mListPrice) {
		this.mListPrice = mListPrice;
	}

	public Double getmRepFloorPrice() {
		return mRepFloorPrice;
	}

	public void setmRepFloorPrice(Double mRepFloorPrice) {
		this.mRepFloorPrice = mRepFloorPrice;
	}

	public Double getmStandardCost() {
		return mStandardCost;
	}

	public void setmStandardCost(Double mStandardCost) {
		this.mStandardCost = mStandardCost;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public String getsInternalModel() {
		return sInternalModel;
	}

	public void setsInternalModel(String sInternalModel) {
		this.sInternalModel = sInternalModel;
	}

	public String getsManufacturerModel() {
		return sManufacturerModel;
	}

	public void setsManufacturerModel(String sManufacturerModel) {
		this.sManufacturerModel = sManufacturerModel;
	}

	public TbAdmMaterialClass getTbAdmMaterialClass() {
		return tbAdmMaterialClass;
	}

	public void setTbAdmMaterialClass(TbAdmMaterialClass tbAdmMaterialClass) {
		this.tbAdmMaterialClass = tbAdmMaterialClass;
	}

	public String getsPrimaryUnitMeasure() {
		return sPrimaryUnitMeasure;
	}

	public void setsPrimaryUnitMeasure(String sPrimaryUnitMeasure) {
		this.sPrimaryUnitMeasure = sPrimaryUnitMeasure;
	}

	public String getsLifeCyclePhase() {
		return sLifeCyclePhase;
	}

	public void setsLifeCyclePhase(String sLifeCyclePhase) {
		this.sLifeCyclePhase = sLifeCyclePhase;
	}

	public String getsItemType() {
		return sItemType;
	}

	public void setsItemType(String sItemType) {
		this.sItemType = sItemType;
	}

	public String getsItemStatus() {
		return sItemStatus;
	}

	public void setsItemStatus(String sItemStatus) {
		this.sItemStatus = sItemStatus;
	}

	public String getsCostumerOrderFlag() {
		return sCostumerOrderFlag;
	}

	public void setsCostumerOrderFlag(String sCostumerOrderFlag) {
		this.sCostumerOrderFlag = sCostumerOrderFlag;
	}

	public String getsTransactionConditionClass() {
		return sTransactionConditionClass;
	}

	public void setsTransactionConditionClass(String sTransactionConditionClass) {
		this.sTransactionConditionClass = sTransactionConditionClass;
	}

	public String getsItemFiscalType() {
		return sItemFiscalType;
	}

	public void setsItemFiscalType(String sItemFiscalType) {
		this.sItemFiscalType = sItemFiscalType;
	}

	public Integer getiFederalTributarysituation() {
		return iFederalTributarysituation;
	}

	public void setiFederalTributarysituation(Integer iFederalTributarysituation) {
		this.iFederalTributarysituation = iFederalTributarysituation;
	}

	public Integer getiStateTributarySituation() {
		return iStateTributarySituation;
	}

	public void setiStateTributarySituation(Integer iStateTributarySituation) {
		this.iStateTributarySituation = iStateTributarySituation;
	}

	public Date getDtItemCreationDate() {
		return dtItemCreationDate;
	}

	public void setDtItemCreationDate(Date dtItemCreationDate) {
		this.dtItemCreationDate = dtItemCreationDate;
	}

	public Date getDtLastUpdate() {
		return dtLastUpdate;
	}

	public void setDtLastUpdate(Date dtLastUpdate) {
		this.dtLastUpdate = dtLastUpdate;
	}

	public String getsLineBusiness() {
		return sLineBusiness;
	}

	public void setsLineBusiness(String sLineBusiness) {
		this.sLineBusiness = sLineBusiness;
	}

	public String getsInternalOrderFlag() {
		return sInternalOrderFlag;
	}

	public void setsInternalOrderFlag(String sInternalOrderFlag) {
		this.sInternalOrderFlag = sInternalOrderFlag;
	}

	public List<TbTaxQuoteItem> getTbTaxQuoteItems() {
		return tbTaxQuoteItems;
	}

	public void setTbTaxQuoteItems(List<TbTaxQuoteItem> tbTaxQuoteItems) {
		this.tbTaxQuoteItems = tbTaxQuoteItems;
	}

	public boolean isfVariacaoPartNumber() {
		return fVariacaoPartNumber;
	}

	public void setfVariacaoPartNumber(boolean fVariacaoPartNumber) {
		this.fVariacaoPartNumber = fVariacaoPartNumber;
	}

	public boolean isfVariacaoPartNumberDisabled() {
		
		if(tbAdmMaterialClass != null && tbAdmMaterialClass.getTbAdmProductType() != null && tbAdmMaterialClass.getTbAdmProductType().getiTaxModel() == 1){
			fVariacaoPartNumberDisabled = true;
		}else{
			fVariacaoPartNumberDisabled = false;
		}
		
		return fVariacaoPartNumberDisabled;
	}

	public void setfVariacaoPartNumberDisabled(boolean fVariacaoPartNumberDisabled) {
		this.fVariacaoPartNumberDisabled = fVariacaoPartNumberDisabled;
	}

	public String getStyle() {
		
		style = "";
		
		if(tbAdmMaterialClass == null){
			style = "color: red;";
			
			if(tbTaxQuoteItems != null && tbTaxQuoteItems.size() > 0){
				style += "font-weight: bold;";
			}
			
		}
						
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public TbSysUser getTbSysUser() {
		return tbSysUser;
	}

	public void setTbSysUser(TbSysUser tbSysUser) {
		this.tbSysUser = tbSysUser;
	}

	public boolean isDisabled() {
		
		if(getTbTaxQuoteItems() != null && getTbTaxQuoteItems().size() > 0){
			
			disabled = true;
			
		}else{
			
			disabled = false;
			
		}
		
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isfTaxSupportActive() {
		return fTaxSupportActive;
	}

	public void setfTaxSupportActive(boolean fTaxSupportActive) {
		this.fTaxSupportActive = fTaxSupportActive;
	}

	public Float getrTaxSupport() {
		
		if(rTaxSupport != null){
			return rTaxSupport * 100;
		}else{
			return rTaxSupport;
		}
	}

	public void setrTaxSupport(Float rTaxSupport) {
		
		if(rTaxSupport != null){
			this.rTaxSupport = (rTaxSupport / 100);
		}else{
			this.rTaxSupport = rTaxSupport;
		}
	}

	public TbAdmMaterialCategory getTbAdmMaterialCategory() {
		return tbAdmMaterialCategory;
	}

	public void setTbAdmMaterialCategory(TbAdmMaterialCategory tbAdmMaterialCategory) {
		this.tbAdmMaterialCategory = tbAdmMaterialCategory;
	}

	public String getCategory() {
		
		if(tbAdmMaterialCategory != null){
			category = String.valueOf(tbAdmMaterialCategory.getId());
		}
		
		return category;
	}

	public void setCategory(String category) {
		
		if(tbAdmMaterialCategory != null){
			if(!category.equals(String.valueOf(tbAdmMaterialCategory.getId()))){
				tbAdmMaterialCategory = null;
			}
		}
		
		this.category = category;
	}

	public TbAdmMaterialCategorySupport getTbAdmMaterialCategorySupport() {
		return tbAdmMaterialCategorySupport;
	}

	public void setTbAdmMaterialCategorySupport(
			TbAdmMaterialCategorySupport tbAdmMaterialCategorySupport) {
		this.tbAdmMaterialCategorySupport = tbAdmMaterialCategorySupport;
	}

	public String getSupport() {
		
		if(tbAdmMaterialCategorySupport != null){
			support = String.valueOf(tbAdmMaterialCategorySupport.getId());
		}
		
		return support;
	}

	public void setSupport(String support) {
		
		if(tbAdmMaterialCategorySupport != null){
			if(!support.equals(String.valueOf(tbAdmMaterialCategorySupport.getId()))){
				tbAdmMaterialCategorySupport = null;
			}
		}
		
		this.support = support;
	}

	public TbAdmMaterialCategorySupport getTbAdmMaterialCategorySup() {
		return tbAdmMaterialCategorySup;
	}

	public void setTbAdmMaterialCategorySup(
			TbAdmMaterialCategorySupport tbAdmMaterialCategorySup) {
		this.tbAdmMaterialCategorySup = tbAdmMaterialCategorySup;
	}
	
	
	
}