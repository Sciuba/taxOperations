package br.com.saboia.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TBADMMATERIAL_IMPORT_TEMP database table.
 * 
 */
@Entity
@Table(name="TBADMMATERIAL_IMPORT_TEMP")
@NamedQuery(name="TbAdmMaterialImportTemp.findAll", query="SELECT t FROM TbAdmMaterialImportTemp t")
public class TbAdmMaterialImportTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMMATERIAL_IMPORT_TEMP_ID_GENERATOR", sequenceName="SEQ_MATERIAL_IMPORT_TEMP", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMMATERIAL_IMPORT_TEMP_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.DATE)
	private Date dtItemCreationDate;

	@Temporal(TemporalType.DATE)
	private Date dtLastUpdate;

	private String FiscalClassificationCode;

	private Integer iFederalTributarySituation;

	private Integer iStateTributarySituation;

	private String ItemOrigin;

	private Double mCost;

	private Double mListPrice;

	private Double mRepFloorPrice;

	private Double mStandardCost;

	private String sCostumerOrderFlag;

	private String sDescription;

	private String sInternalModel;

	private String sInternalOrderFlag;

	private String sItemFiscalType;

	private String sItemStatus;

	private String sItemType;

	private String sLifeCyclePhase;

	private String sLineBusiness;

	private String sManufacturerModel;

	private String sPrimaryUnitMeasure;

	private String sTransactionConditionClass;
	
	private Integer fOrigemDaMercadoria; 
	
	public TbAdmMaterialImportTemp() {
	}

	
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getFiscalClassificationCode() {
		return FiscalClassificationCode;
	}

	public void setFiscalClassificationCode(String fiscalClassificationCode) {
		FiscalClassificationCode = fiscalClassificationCode;
	}

	public Integer getiFederalTributarySituation() {
		return iFederalTributarySituation;
	}

	public void setiFederalTributarySituation(Integer iFederalTributarySituation) {
		this.iFederalTributarySituation = iFederalTributarySituation;
	}

	public Integer getiStateTributarySituation() {
		return iStateTributarySituation;
	}

	public void setiStateTributarySituation(Integer iStateTributarySituation) {
		this.iStateTributarySituation = iStateTributarySituation;
	}

	public String getItemOrigin() {
		return ItemOrigin;
	}

	public void setItemOrigin(String itemOrigin) {
		ItemOrigin = itemOrigin;
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

	public String getsCostumerOrderFlag() {
		return sCostumerOrderFlag;
	}

	public void setsCostumerOrderFlag(String sCostumerOrderFlag) {
		this.sCostumerOrderFlag = sCostumerOrderFlag;
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

	public String getsInternalOrderFlag() {
		return sInternalOrderFlag;
	}

	public void setsInternalOrderFlag(String sInternalOrderFlag) {
		this.sInternalOrderFlag = sInternalOrderFlag;
	}

	public String getsItemFiscalType() {
		return sItemFiscalType;
	}

	public void setsItemFiscalType(String sItemFiscalType) {
		this.sItemFiscalType = sItemFiscalType;
	}

	public String getsItemStatus() {
		return sItemStatus;
	}

	public void setsItemStatus(String sItemStatus) {
		this.sItemStatus = sItemStatus;
	}

	public String getsItemType() {
		return sItemType;
	}

	public void setsItemType(String sItemType) {
		this.sItemType = sItemType;
	}

	public String getsLifeCyclePhase() {
		return sLifeCyclePhase;
	}

	public void setsLifeCyclePhase(String sLifeCyclePhase) {
		this.sLifeCyclePhase = sLifeCyclePhase;
	}

	public String getsLineBusiness() {
		return sLineBusiness;
	}

	public void setsLineBusiness(String sLineBusiness) {
		this.sLineBusiness = sLineBusiness;
	}

	public String getsManufacturerModel() {
		return sManufacturerModel;
	}

	public void setsManufacturerModel(String sManufacturerModel) {
		this.sManufacturerModel = sManufacturerModel;
	}

	public String getsPrimaryUnitMeasure() {
		return sPrimaryUnitMeasure;
	}

	public void setsPrimaryUnitMeasure(String sPrimaryUnitMeasure) {
		this.sPrimaryUnitMeasure = sPrimaryUnitMeasure;
	}

	public String getsTransactionConditionClass() {
		return sTransactionConditionClass;
	}

	public void setsTransactionConditionClass(String sTransactionConditionClass) {
		this.sTransactionConditionClass = sTransactionConditionClass;
	}



	public Integer getfOrigemDaMercadoria() {
		return fOrigemDaMercadoria;
	}



	public void setfOrigemDaMercadoria(Integer fOrigemDaMercadoria) {
		this.fOrigemDaMercadoria = fOrigemDaMercadoria;
	}

	

}