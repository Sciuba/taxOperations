package br.com.saboia.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBADMPRODUCTTYPE database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmProductType.findAll", query="SELECT t FROM TbAdmProductType t")
public class TbAdmProductType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMPRODUCTTYPE_ID_GENERATOR", sequenceName="SEQ_TBADMPRODUCTTYPE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMPRODUCTTYPE_ID_GENERATOR")
	private long id;

	private boolean fCalculationMethod;

	private boolean fRevRecog;

	private Float iTaxModel;

	private Float rCofins;

	private Float rCssl;

	private Float rIR;

	private Float rPIS;

	private Float rSuggestedMargin;

	private Double rTpDealPrice;

	private Double rTransferPriceRate;

	private String sDescription;

	private String sLabel;
	
	@Transient
	private String calculationMethod;
	
	@Transient
	private String taxModel;
	
	@Transient
	private String revRevog;
	
	@Transient
	private String sLabelAcronym;
	
	//bi-directional many-to-one association to TbAdmMaterialClass
	@OneToMany(mappedBy="tbAdmProductType")
	private List<TbAdmMaterialClass> tbAdmMaterialClasses;
	
	@OneToMany(mappedBy="tbAdmProductType")
	private List<TbTaxQuoteItem> taxQuoteItems;
	
	@OneToMany(mappedBy="tbAdmProductType")
	private List<TbAdmOriginXProductTypeSV> tbAdmOriginXProductTypeSVs;
	
	public TbAdmProductType() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isfRevRecog() {
		return fRevRecog;
	}

	public void setfRevRecog(boolean fRevRecog) {
		this.fRevRecog = fRevRecog;
	}

	public Float getiTaxModel() {
		return iTaxModel;
	}

	public void setiTaxModel(Float iTaxModel) {
		this.iTaxModel = iTaxModel;
	}

	public Float getrCofins() {
		if(rCofins != null){
			return rCofins * 100;
		}else{
			return rCofins;
		}
	}

	public void setrCofins(Float rCofins) {
		if(rCofins != null){
			this.rCofins = (rCofins / 100);
		}else{
			this.rCofins = rCofins;
		}
	}

	public Float getrCssl() {
		if(rCssl != null){
			return rCssl * 100;
		}else{
			return rCssl;
		}
	}

	public void setrCssl(Float rCssl) {
		if(rCssl != null){
			this.rCssl = (rCssl / 100);
		}else{
			this.rCssl = rCssl;
		}
	}

	public Float getrIR() {
		if(rIR != null){
			return rIR * 100;
		}else{
			return rIR;
		}
	}

	public void setrIR(Float rIR) {
		if(rIR != null){
			this.rIR = (rIR / 100);
		}else{
			this.rIR = rIR;
		}
	}

	public Float getrPIS() {
		if(rPIS != null){
			return rPIS * 100;
		}else{
			return rPIS;
		}
	}

	public void setrPIS(Float rPIS) {
		if(rPIS != null){
			this.rPIS = (rPIS / 100);
		}else{
			this.rPIS = rPIS; 
		}
	}

	public Float getrSuggestedMargin() {
		if(rSuggestedMargin != null){
			return rSuggestedMargin * 100;
		}else{
			return rSuggestedMargin;
		}
	}

	public void setrSuggestedMargin(Float rSuggestedMargin) {
		if(rSuggestedMargin != null){
			this.rSuggestedMargin = (rSuggestedMargin / 100);
		}else{
			this.rSuggestedMargin = rSuggestedMargin;
		}
	}

	public Double getrTpDealPrice() {
		if(rTpDealPrice != null){
			return rTpDealPrice * 100;
		}else{
			return rTpDealPrice;
		}
	}

	public void setrTpDealPrice(Double rTpDealPrice) {
		if(rTpDealPrice != null){
			this.rTpDealPrice = (rTpDealPrice / 100);
		}else{
			this.rTpDealPrice = rTpDealPrice;
		}
	}

	public Double getrTransferPriceRate() {
		if(rTransferPriceRate != null){
			return rTransferPriceRate * 100;
		}else{
			return rTransferPriceRate;
		}
	}

	public void setrTransferPriceRate(Double rTransferPriceRate) {
		if(rTransferPriceRate != null){
		    this.rTransferPriceRate = (rTransferPriceRate / 100);
		}else{
			this.rTransferPriceRate = rTransferPriceRate;
		}
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public String getsLabel() {
		return sLabel;
	}

	public void setsLabel(String sLabel) {
		this.sLabel = sLabel;
	}

	public List<TbAdmMaterialClass> getTbAdmMaterialClasses() {
		return tbAdmMaterialClasses;
	}

	public void setTbAdmMaterialClasses(
			List<TbAdmMaterialClass> tbAdmMaterialClasses) {
		this.tbAdmMaterialClasses = tbAdmMaterialClasses;
	}

	public TbAdmMaterialClass addTbadmmaterialclass(TbAdmMaterialClass tbadmmaterialclass) {
		getTbAdmMaterialClasses().add(tbadmmaterialclass);
		tbadmmaterialclass.setTbAdmProductType(this);

		return tbadmmaterialclass;
	}

	public TbAdmMaterialClass removeTbadmmaterialclass(TbAdmMaterialClass tbadmmaterialclass) {
		getTbAdmMaterialClasses().remove(tbadmmaterialclass);
		tbadmmaterialclass.setTbAdmProductType(null);

		return tbadmmaterialclass;
	}

	public boolean isfCalculationMethod() {
		return fCalculationMethod;
	}

	public void setfCalculationMethod(boolean fCalculationMethod) {
		this.fCalculationMethod = fCalculationMethod;
	}

	public String getCalculationMethod() {
		
		if(isfCalculationMethod()){
			calculationMethod = "BLP / Rep Floor Price";
		}else{
			calculationMethod = "Deal Price";
		}
		
		return calculationMethod;
	}

	public void setCalculationMethod(String calculationMethod) {
		this.calculationMethod = calculationMethod;
	}

	public String getTaxModel() {
		
		if(iTaxModel == null){
			taxModel = "";
		}else if(iTaxModel == 1){
			taxModel = "HW";
		}else if(iTaxModel == 2){
			taxModel = "SW";
		}else if(iTaxModel == 3){
			taxModel = "SV";
		}else if(iTaxModel == 4){
			taxModel = "SV";
		}else{
			taxModel = "MD";
		}
		
		return taxModel;
	}

	public void setTaxModel(String taxModel) {
		this.taxModel = taxModel;
	}

	public String getRevRevog() {
		
		if(isfRevRecog()){
			revRevog = "Yes";
		}else{
			revRevog = "No";
		}
		
		return revRevog;
	}

	public void setRevRevog(String revRevog) {
		this.revRevog = revRevog;
	}

	public List<TbTaxQuoteItem> getTaxQuoteItems() {
		return taxQuoteItems;
	}

	public void setTaxQuoteItems(List<TbTaxQuoteItem> taxQuoteItems) {
		this.taxQuoteItems = taxQuoteItems;
	}

	public List<TbAdmOriginXProductTypeSV> getTbAdmOriginXProductTypeSVs() {
		return tbAdmOriginXProductTypeSVs;
	}

	public void setTbAdmOriginXProductTypeSVs(
			List<TbAdmOriginXProductTypeSV> tbAdmOriginXProductTypeSVs) {
		this.tbAdmOriginXProductTypeSVs = tbAdmOriginXProductTypeSVs;
	}

	public String getsLabelAcronym() {
		
		if(iTaxModel == 1){
			sLabelAcronym = "HW";
		}else if(iTaxModel == 2){
			sLabelAcronym = "SW";
		}else if(iTaxModel == 3){
			sLabelAcronym = "SV";
		}else if(iTaxModel == 5){
			sLabelAcronym = "MD";
		}
		
		return sLabelAcronym;
	}

	public void setsLabelAcronym(String sLabelAcronym) {
		this.sLabelAcronym = sLabelAcronym;
	}

	
	
	
}