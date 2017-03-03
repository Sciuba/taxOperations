package br.com.saboia.entity;

import java.io.Serializable;
import java.util.List;

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
 * The persistent class for the TBADMDESTINATION database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmDestination.findAll", query="SELECT t FROM TbAdmDestination t")
public class TbAdmDestination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMDESTINATION_ID_GENERATOR", sequenceName="SEQ_TBADMDESTINATION", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMDESTINATION_ID_GENERATOR")
	private long id;

	private boolean fAvaliable;

	private int fTipocalculo;
	
	@ManyToOne
	@JoinColumn(name="IDADMORIGINHW")
	private TbAdmOrigin tbAdmOriginHw;
	
	@ManyToOne
	@JoinColumn(name="IDADMORIGINMT")
	private TbAdmOrigin tbAdmOriginMt;

	@ManyToOne
	@JoinColumn(name="IDADMORIGINSV")
	private TbAdmOrigin tbAdmOriginSv;

	@ManyToOne
	@JoinColumn(name="IDADMORIGINSW")
	private TbAdmOrigin tbAdmOriginSw;

	private Float rIcms;

	private Float rIcmsInterEstadual;

	private Float rIcmsInterEstadualMatImport;

	private Float rIcmSPPB;

	private String sCode;

	private String sLocale;

	@Transient
	private boolean selecionado;
	
	@Transient
	private String originHW;
	
	@Transient
	private String originSW;
	
	@Transient
	private String originMT;
	
	@Transient
	private String originSV;
	
	@Transient
	private String completeOriginHW;
	
	@Transient
	private String completeOriginSW;
	
	@Transient
	private String completeOriginMT;
	
	@Transient
	private String completeOriginSV;
	
	@Transient
	private String avaliable;
	
	@Transient
	private String protocol = "No";
	
	//bi-directional many-to-one association to Tbadmdestinationrate
	@OneToMany(mappedBy="tbAdmDestination")
	private List<TbAdmDestinationRate> tbAdmDestinationRates;

	//bi-directional many-to-one association to TbAdmOriginXTbAdmDestinaProtSt
	@OneToMany(mappedBy="tbAdmDestination")
	private List<TbAdmOriginXTbAdmDestinaProtSt> tbAdmOriginXTbAdmDestinaProSt;
	
	@OneToMany(mappedBy="tbAdmDestination")
	private List<TbAdmMaterialClassXDestination> tbAdmMaterialClassXDestinations;
	
	@OneToMany(mappedBy="tbAdmDestination")
	private List<TbTaxQuoteItem> tbTaxQuoteItems;
	
	@OneToMany(mappedBy="tbAdmDestination")
	private List<TbTaxQuote> tbTaxQuotes;
	
	public TbAdmDestination() {
	}

	public long getId() {
		return id;
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

	public int getfTipocalculo() {
		return fTipocalculo;
	}

	public void setfTipocalculo(int fTipocalculo) {
		this.fTipocalculo = fTipocalculo;
	}

	public TbAdmOrigin getTbAdmOriginHw() {
		return tbAdmOriginHw;
	}

	public void setTbAdmOriginHw(TbAdmOrigin tbAdmOriginHw) {
		this.tbAdmOriginHw = tbAdmOriginHw;
	}

	public TbAdmOrigin getTbAdmOriginMt() {
		return tbAdmOriginMt;
	}

	public void setTbAdmOriginMt(TbAdmOrigin tbAdmOriginMt) {
		this.tbAdmOriginMt = tbAdmOriginMt;
	}

	public TbAdmOrigin getTbAdmOriginSv() {
		return tbAdmOriginSv;
	}

	public void setTbAdmOriginSv(TbAdmOrigin tbAdmOriginSv) {
		this.tbAdmOriginSv = tbAdmOriginSv;
	}

	public TbAdmOrigin getTbAdmOriginSw() {
		return tbAdmOriginSw;
	}

	public void setTbAdmOriginSw(TbAdmOrigin tbAdmOriginSw) {
		this.tbAdmOriginSw = tbAdmOriginSw;
	}

	public Float getrIcms() {
		if(rIcms != null){
			return rIcms * 100;
		}else{
			return rIcms;
		}
	}

	public void setrIcms(Float rIcms) {
		if(rIcms != null){
			this.rIcms = (rIcms / 100);
		}else{
			this.rIcms = rIcms;
		}
	}

	public Float getrIcmsInterEstadual() {
		if(rIcmsInterEstadual != null){
			return rIcmsInterEstadual * 100;
		}else{
			return rIcmsInterEstadual;
		}
	}

	public void setrIcmsInterEstadual(Float rIcmsInterEstadual) {
		if(rIcmsInterEstadual != null){
			this.rIcmsInterEstadual = (rIcmsInterEstadual / 100);
		}else{
			this.rIcmsInterEstadual = rIcmsInterEstadual;
		}
	}

	public Float getrIcmsInterEstadualMatImport() {
		if(rIcmsInterEstadualMatImport != null){
			return rIcmsInterEstadualMatImport * 100;
		}else{
			return rIcmsInterEstadualMatImport;
		}
	}

	public void setrIcmsInterEstadualMatImport(Float rIcmsInterEstadualMatImport) {
		if(rIcmsInterEstadualMatImport != null){
			this.rIcmsInterEstadualMatImport = (rIcmsInterEstadualMatImport / 100);
		}else{
			this.rIcmsInterEstadualMatImport = rIcmsInterEstadualMatImport;
		}
	}

	public Float getrIcmSPPB() {
		if(rIcmSPPB != null){
			return rIcmSPPB * 100;
		}else{
			return rIcmSPPB;
		}
	}

	public void setrIcmSPPB(Float rIcmSPPB) {
		if(rIcmSPPB != null){
			this.rIcmSPPB = (rIcmSPPB / 100);
		}else{
			this.rIcmSPPB = rIcmSPPB;
		}
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getsLocale() {
		return sLocale;
	}

	public void setsLocale(String sLocale) {
		this.sLocale = sLocale;
	}

	public List<TbAdmDestinationRate> getTbAdmDestinationRates() {
		return tbAdmDestinationRates;
	}

	public void setTbAdmDestinationRates(
			List<TbAdmDestinationRate> tbAdmDestinationRates) {
		this.tbAdmDestinationRates = tbAdmDestinationRates;
	}

	public List<TbAdmOriginXTbAdmDestinaProtSt> getTbAdmOriginXTbAdmDestinaProSt() {
		return tbAdmOriginXTbAdmDestinaProSt;
	}

	public void setTbAdmOriginXTbAdmDestinaProSt(
			List<TbAdmOriginXTbAdmDestinaProtSt> tbAdmOriginXTbAdmDestinaProSt) {
		this.tbAdmOriginXTbAdmDestinaProSt = tbAdmOriginXTbAdmDestinaProSt;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public String getOriginHW() {
		
		if(getTbAdmOriginHw() != null){
			originHW = String.valueOf(getTbAdmOriginHw().getId());
		}
		
		return originHW;
	}

	public void setOriginHW(String originHW) {
		this.originHW = originHW;
	}

	public String getOriginSW() {
		
		if(getTbAdmOriginSw() != null){
			originSW = String.valueOf(getTbAdmOriginSw().getId());
		}
		
		return originSW;
	}

	public void setOriginSW(String originSW) {
		this.originSW = originSW;
	}

	public String getOriginMT() {
		
		if(getTbAdmOriginMt() != null){
			originMT = String.valueOf(getTbAdmOriginMt().getId());
		}
		
		return originMT;
	}

	public void setOriginMT(String originMT) {
		this.originMT = originMT;
	}

	public String getOriginSV() {
		
		if(getTbAdmOriginSv() != null){
			originSV = String.valueOf(getTbAdmOriginSv().getId());
		}
		
		return originSV;
	}

	public void setOriginSV(String originSV) {
		this.originSV = originSV;
	}

	public String getCompleteOriginHW() {
		
		if(getTbAdmOriginHw() != null){
			completeOriginHW = getTbAdmOriginHw().getsCode()+" - "+ getTbAdmOriginHw().getsLocale();
		}
		
		return completeOriginHW;
	}

	public void setCompleteOriginHW(String completeOriginHW) {
		this.completeOriginHW = completeOriginHW;
	}

	public String getCompleteOriginSW() {
		
		if(getTbAdmOriginSw() != null){
			completeOriginSW = getTbAdmOriginSw().getsCode()+" - "+ getTbAdmOriginSw().getsLocale();
		}
		
		return completeOriginSW;
	}

	public void setCompleteOriginSW(String completeOriginSW) {
		this.completeOriginSW = completeOriginSW;
	}

	public String getCompleteOriginMT() {
		
		if(getTbAdmOriginMt() != null){
			completeOriginMT = getTbAdmOriginMt().getsCode()+" - "+ getTbAdmOriginMt().getsLocale();
		}
		
		return completeOriginMT;
	}

	public void setCompleteOriginMT(String completeOriginMT) {
		this.completeOriginMT = completeOriginMT;
	}

	public String getCompleteOriginSV() {
		
		if(getTbAdmOriginSv() != null){
			completeOriginSV = getTbAdmOriginSv().getsCode()+" - "+ getTbAdmOriginSv().getsLocale();
		}
		
		return completeOriginSV;
	}

	public void setCompleteOriginSV(String completeOriginSV) {
		this.completeOriginSV = completeOriginSV;
	}

	public String getAvaliable() {
		
		if(isfAvaliable()){
			avaliable = "Yes";
		}else{
			avaliable = "No";
		}
		
		return avaliable;
	}

	public void setAvaliable(String avaliable) {
		this.avaliable = avaliable;
	}

	public String getProtocol() {
		
		for(TbAdmOriginXTbAdmDestinaProtSt st : getTbAdmOriginXTbAdmDestinaProSt()){
			if(getTbAdmOriginHw() != null){
				if(st.getTbAdmOrigin().getId() == getTbAdmOriginHw().getId()){
					protocol = "Yes";
				}
			}
		}
		
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public List<TbAdmMaterialClassXDestination> getTbAdmMaterialClassXDestinations() {
		return tbAdmMaterialClassXDestinations;
	}

	public void setTbAdmMaterialClassXDestinations(
			List<TbAdmMaterialClassXDestination> tbAdmMaterialClassXDestinations) {
		this.tbAdmMaterialClassXDestinations = tbAdmMaterialClassXDestinations;
	}

	public List<TbTaxQuoteItem> getTbTaxQuoteItems() {
		return tbTaxQuoteItems;
	}

	public void setTbTaxQuoteItems(List<TbTaxQuoteItem> tbTaxQuoteItems) {
		this.tbTaxQuoteItems = tbTaxQuoteItems;
	}

	public List<TbTaxQuote> getTbTaxQuotes() {
		return tbTaxQuotes;
	}

	public void setTbTaxQuotes(List<TbTaxQuote> tbTaxQuotes) {
		this.tbTaxQuotes = tbTaxQuotes;
	}
	
		
}