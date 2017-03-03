package br.com.operations.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;


/**
 * The persistent class for the TBADMQUOTEPARAMETERS database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmQuoteParameters.findAll", query="SELECT t FROM TbAdmQuoteParameters t")
public class TbAdmQuoteParameters implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMQUOTEPARAMETERS_ID_GENERATOR", sequenceName="SEQ_TBADMQUOTEPARAMETERS", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMQUOTEPARAMETERS_ID_GENERATOR")
	private long id;

	private boolean fAbsoluteZeroModel;

	@ManyToOne
	@JoinColumn(name="IDSALESDESTINATION")
	private TbAdmDestination tbAdmDestination;

	private Float rAdditionalPercentRateDollar;

	private Float rDiscount;

	private Float rFatorRelevanciaMaxima;

	private Float rIcmsEntradaPadrao;

	private Float rIcmsSaidaPadrao;

	private Float rIssStandard;

	private Float rIssStandardMtesp;

	private Float rSuggestedMargin;

	private String sEmail;

	private String sEmailAlertMatNoClassified;

	private String sFilialCep;

	private String sFilialCidade;

	private String sFilialCnpj;

	private String sFilialEndereco;

	private String sFilialEstado;

	private String sFilialInscEst;

	private String sFilialInscMun;

	private String sFilialNome;

	private String sFilinalInnscMun;

	private String sImportOrderEmail;

	private String sMatrizCep;

	private String sMatrizCidade;

	private String sMatrizCnpj;

	private String sMatrizEndereco;

	private String sMatrizEstado;

	private String sMatrizInscest;

	private String sMatrizInscMun;

	private String sMatrizlNome;

	private String sMsgRate;

	private boolean fSendEmailSalesRep;
	
	@Transient
	private String zeroAbsoluto;
	
	@Transient
	private String sendEmailSalesRep;
	
	public TbAdmQuoteParameters() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isfAbsoluteZeroModel() {
		return fAbsoluteZeroModel;
	}

	public void setfAbsoluteZeroModel(boolean fAbsoluteZeroModel) {
		this.fAbsoluteZeroModel = fAbsoluteZeroModel;
	}

	public TbAdmDestination getTbAdmDestination() {
		return tbAdmDestination;
	}

	public void setTbAdmDestination(TbAdmDestination tbAdmDestination) {
		this.tbAdmDestination = tbAdmDestination;
	}

	public Float getrAdditionalPercentRateDollar() {
		if(rAdditionalPercentRateDollar != null){
			return rAdditionalPercentRateDollar  * 100;
		}else{
			return rAdditionalPercentRateDollar;
		}
	}

	public void setrAdditionalPercentRateDollar(Float rAdditionalPercentRateDollar) {
		if(rAdditionalPercentRateDollar != null){
			this.rAdditionalPercentRateDollar = (rAdditionalPercentRateDollar / 100);
		}else{
			this.rAdditionalPercentRateDollar = rAdditionalPercentRateDollar;
		}
	}

	public Float getrDiscount() {
		if(rDiscount != null){
			return rDiscount * 100;
		}else{
			return rDiscount;
		}
	}

	public void setrDiscount(Float rDiscount) {
		if(rDiscount != null){
			this.rDiscount = (rDiscount / 100);
		}else{
			this.rDiscount = rDiscount;
		}
	}

	public Float getrFatorRelevanciaMaxima() {
		if(rFatorRelevanciaMaxima != null){
			return rFatorRelevanciaMaxima * 100;
		}else{
			return rFatorRelevanciaMaxima;
		}
	}

	public void setrFatorRelevanciaMaxima(Float rFatorRelevanciaMaxima) {
		if(rFatorRelevanciaMaxima != null){
			this.rFatorRelevanciaMaxima = (rFatorRelevanciaMaxima / 100);
		}else{
			this.rFatorRelevanciaMaxima = rFatorRelevanciaMaxima; 
		}
	}

	public Float getrIcmsEntradaPadrao() {
		if(rIcmsEntradaPadrao != null){
			return rIcmsEntradaPadrao * 100;
		}else{
			return rIcmsEntradaPadrao;
		}
	}

	public void setrIcmsEntradaPadrao(Float rIcmsEntradaPadrao) {
		if(rIcmsEntradaPadrao != null){
			this.rIcmsEntradaPadrao = (rIcmsEntradaPadrao / 100);
		}else{
			this.rIcmsEntradaPadrao = rIcmsEntradaPadrao;
		}
	}

	public Float getrIcmsSaidaPadrao() {
		if(rIcmsSaidaPadrao != null){
			return rIcmsSaidaPadrao * 100;
		}else{
			return rIcmsSaidaPadrao;
		}
	}

	public void setrIcmsSaidaPadrao(Float rIcmsSaidaPadrao) {
		if(rIcmsSaidaPadrao != null){
			this.rIcmsSaidaPadrao = (rIcmsSaidaPadrao / 100);
		}else{
			this.rIcmsSaidaPadrao = rIcmsSaidaPadrao;
		}
	}

	public Float getrIssStandard() {
		if(rIssStandard != null){
			return rIssStandard * 100;
		}else{
			return rIssStandard;
		}
	}

	public void setrIssStandard(Float rIssStandard) {
		if(rIssStandard != null){
			this.rIssStandard = (rIssStandard / 100);
		}else{
			this.rIssStandard = rIssStandard;
		}
	}

	public Float getrIssStandardMtesp() {
		if(rIssStandardMtesp != null){
			return rIssStandardMtesp * 100;
		}else{
			return rIssStandardMtesp;
		}
	}

	public void setrIssStandardMtesp(Float rIssStandardMtesp) {
		if(rIssStandardMtesp != null){
			this.rIssStandardMtesp = (rIssStandardMtesp / 100);
		}else{
			this.rIssStandardMtesp = rIssStandardMtesp;
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

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public String getsEmailAlertMatNoClassified() {
		return sEmailAlertMatNoClassified;
	}

	public void setsEmailAlertMatNoClassified(String sEmailAlertMatNoClassified) {
		this.sEmailAlertMatNoClassified = sEmailAlertMatNoClassified;
	}

	public String getsFilialCep() {
		return sFilialCep;
	}

	public void setsFilialCep(String sFilialCep) {
		this.sFilialCep = sFilialCep;
	}

	public String getsFilialCidade() {
		return sFilialCidade;
	}

	public void setsFilialCidade(String sFilialCidade) {
		this.sFilialCidade = sFilialCidade;
	}

	public String getsFilialCnpj() {
		return sFilialCnpj;
	}

	public void setsFilialCnpj(String sFilialCnpj) {
		this.sFilialCnpj = sFilialCnpj;
	}

	public String getsFilialEndereco() {
		return sFilialEndereco;
	}

	public void setsFilialEndereco(String sFilialEndereco) {
		this.sFilialEndereco = sFilialEndereco;
	}

	public String getsFilialEstado() {
		return sFilialEstado;
	}

	public void setsFilialEstado(String sFilialEstado) {
		this.sFilialEstado = sFilialEstado;
	}

	public String getsFilialInscEst() {
		return sFilialInscEst;
	}

	public void setsFilialInscEst(String sFilialInscEst) {
		this.sFilialInscEst = sFilialInscEst;
	}

	public String getsFilialInscMun() {
		return sFilialInscMun;
	}

	public void setsFilialInscMun(String sFilialInscMun) {
		this.sFilialInscMun = sFilialInscMun;
	}

	public String getsFilialNome() {
		return sFilialNome;
	}

	public void setsFilialNome(String sFilialNome) {
		this.sFilialNome = sFilialNome;
	}

	public String getsFilinalInnscMun() {
		return sFilinalInnscMun;
	}

	public void setsFilinalInnscMun(String sFilinalInnscMun) {
		this.sFilinalInnscMun = sFilinalInnscMun;
	}

	public String getsImportOrderEmail() {
		return sImportOrderEmail;
	}

	public void setsImportOrderEmail(String sImportOrderEmail) {
		this.sImportOrderEmail = sImportOrderEmail;
	}

	public String getsMatrizCep() {
		return sMatrizCep;
	}

	public void setsMatrizCep(String sMatrizCep) {
		this.sMatrizCep = sMatrizCep;
	}

	public String getsMatrizCidade() {
		return sMatrizCidade;
	}

	public void setsMatrizCidade(String sMatrizCidade) {
		this.sMatrizCidade = sMatrizCidade;
	}

	public String getsMatrizCnpj() {
		return sMatrizCnpj;
	}

	public void setsMatrizCnpj(String sMatrizCnpj) {
		this.sMatrizCnpj = sMatrizCnpj;
	}

	public String getsMatrizEndereco() {
		return sMatrizEndereco;
	}

	public void setsMatrizEndereco(String sMatrizEndereco) {
		this.sMatrizEndereco = sMatrizEndereco;
	}

	public String getsMatrizEstado() {
		return sMatrizEstado;
	}

	public void setsMatrizEstado(String sMatrizEstado) {
		this.sMatrizEstado = sMatrizEstado;
	}

	public String getsMatrizInscest() {
		return sMatrizInscest;
	}

	public void setsMatrizInscest(String sMatrizInscest) {
		this.sMatrizInscest = sMatrizInscest;
	}

	public String getsMatrizInscMun() {
		return sMatrizInscMun;
	}

	public void setsMatrizInscMun(String sMatrizInscMun) {
		this.sMatrizInscMun = sMatrizInscMun;
	}

	public String getsMatrizlNome() {
		return sMatrizlNome;
	}

	public void setsMatrizlNome(String sMatrizlNome) {
		this.sMatrizlNome = sMatrizlNome;
	}

	public String getsMsgRate() {
		return sMsgRate;
	}

	public void setsMsgRate(String sMsgRate) {
		this.sMsgRate = sMsgRate;
	}

	public String getZeroAbsoluto() {
		
		if(isfAbsoluteZeroModel()){
			zeroAbsoluto = "Sim";
		}else{
			zeroAbsoluto = "Não";
		}		
		
		return zeroAbsoluto;
	}

	public void setZeroAbsoluto(String zeroAbsoluto) {
		this.zeroAbsoluto = zeroAbsoluto;
	}

	public boolean isfSendEmailSalesRep() {
		return fSendEmailSalesRep;
	}

	public void setfSendEmailSalesRep(boolean fSendEmailSalesRep) {
		this.fSendEmailSalesRep = fSendEmailSalesRep;
	}

	public String getSendEmailSalesRep() {
		
		if(fSendEmailSalesRep){
			
			sendEmailSalesRep = "Yes";
			
		}else{
			
			sendEmailSalesRep = "No";
			
		}
		
		return sendEmailSalesRep;
	}

	public void setSendEmailSalesRep(String sendEmailSalesRep) {
		this.sendEmailSalesRep = sendEmailSalesRep;
	}

	
	

}