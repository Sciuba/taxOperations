package br.com.operations.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import br.com.operations.to.OriginXProductTypeSVTO;


/**
 * The persistent class for the TBADMORIGIN database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmOrigin.findAll", query="SELECT t FROM TbAdmOrigin t")
public class TbAdmOrigin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMORIGIN_ID_GENERATOR", sequenceName="SEQ_TBADMORIGIN", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMORIGIN_ID_GENERATOR")
	private long id;

	private boolean fStandardIssRule;

	private Float rIcmsInterEstadual;
	
	private Float rIcmsInterno;

	private Float rIss;

	private Float rIssEspecial;

	private Float rIssSoft;
	
	private String sCode;

	private String sLocale;
	
	@ManyToOne
	@JoinColumn(name="IDADMPRODUCTTYPEHW")
	private TbAdmProductType tbAdmProductTypeHw;
	
	@ManyToOne
	@JoinColumn(name="IDADMPRODUCTTYPESW")
	private TbAdmProductType tbAdmProductTypeSw;
	
	@ManyToOne
	@JoinColumn(name="IDADMPRODUCTTYPECS")
	private TbAdmProductType tbAdmProductTypeCs;
	
	@ManyToOne
	@JoinColumn(name="IDADMPRODUCTTYPEPS")
	private TbAdmProductType tbAdmProductTypePs;
	
	@ManyToOne
	@JoinColumn(name="IDADMPRODUCTTYPEMD")
	private TbAdmProductType tbAdmProductTypeMd;
	
	@OneToMany(mappedBy="tbAdmOrigin")
	private List<TbAdmOriginXTbAdmDestinaProtSt> tbAdmOriginXTbAdmDestinaProSt;
	
	@OneToMany(mappedBy="tbAdmOrigin")
	private List<TbAdmManufacturerStateXTbAdmOrigin> admManufacturerStateXTbAdmOrigins;
	
	@OneToMany(mappedBy="tbAdmOrigin")
	private List<TbAdmOriginRate> tbAdmOriginRates;
	
	@OneToMany(mappedBy="tbAdmOriginHw")
	private List<TbAdmDestination> tbAdmDestinationsHw;
	
	@OneToMany(mappedBy="tbAdmOriginMt")
	private List<TbAdmDestination> tbAdmDestinationsMt;
	
	@OneToMany(mappedBy="tbAdmOriginSv")
	private List<TbAdmDestination> tbAdmDestinationsSv;
	
	@OneToMany(mappedBy="tbAdmOriginSw")
	private List<TbAdmDestination> tbAdmDestinationsSw;
	
	@OneToMany(mappedBy = "tbAdmOrigin")
	private List<TbTaxQuoteItem> tbTaxQuoteItems;
	
	@OneToMany(mappedBy = "tbAdmOriginHw")
	private List<TbTaxQuote> tbTaxQuotesHw;
	
	@OneToMany(mappedBy = "tbAdmOriginMt")
	private List<TbTaxQuote> tbTaxQuotesMt;
	
	@OneToMany(mappedBy = "tbAdmOriginSv")
	private List<TbTaxQuote> tbTaxQuotesSv;
	
	@OneToMany(mappedBy = "tbAdmOriginSw")
	private List<TbTaxQuote> tbTaxQuotesSw;
	
	@OneToMany(mappedBy="tbAdmOrigin")
	private List<TbAdmOriginXProductTypeSV> tbAdmOriginXProductTypeSVs;
	
	@Transient
	private String standarIssRule;

	@Transient
	private String stateProtocol;
	
	@Transient
	private boolean selecionado;
	
	@Transient
	private boolean productTypeHW;
	
	@Transient
	private boolean productTypeSW;
	
	@Transient
	private boolean productTypeCS;
	
	@Transient
	private boolean productTypePS;
	
	@Transient
	private boolean productTypeMD;
	
	@Transient
	private List<OriginXProductTypeSVTO> listaOriginXProductType;
		
	public TbAdmOrigin() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean getfStandardIssRule() {
		return this.fStandardIssRule;
	}

	public void setfStandardIssRule(boolean fStandardIssRule) {
		this.fStandardIssRule = fStandardIssRule;
	}

	public Float getrIcmsInterno() {
		if(rIcmsInterno != null){
			return rIcmsInterno * 100;
		}else{
			return rIcmsInterno;
		}
	}

	public void setrIcmsInterno(Float rIcmsInterno) {
		if(rIcmsInterno != null){
			this.rIcmsInterno = (rIcmsInterno / 100 );
		}else{
			this.rIcmsInterno = rIcmsInterno;
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

	public Float getrIss() {
		if(rIss != null){
			return rIss * 100;
		}else{
			return rIss;
		}
	}

	public void setrIss(Float rIss) {
		if(rIss != null){
			this.rIss = (rIss / 100);
		}else{
			this.rIss = rIss;
		}
	}

	public Float getrIssEspecial() {
		if(rIssEspecial != null){
			return rIssEspecial * 100;
		}else{
			return rIssEspecial;
		}
	}

	public void setrIssEspecial(Float rIssEspecial) {
		if(rIssEspecial != null){
			this.rIssEspecial = (rIssEspecial / 100);
		}else{
			this.rIssEspecial = rIssEspecial;
		}
	}

	public Float getrIssSoft() {
		if(rIssSoft != null){
			return rIssSoft * 100;
		}else{
			return rIssSoft;
		}
	}

	public void setrIssSoft(Float rIssSoft) {
		if(rIssSoft != null){
			this.rIssSoft = (rIssSoft / 100);
		}else{
			this.rIssSoft = rIssSoft;
		}
	}

	public String getsCode() {
		return this.sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getsLocale() {
		return this.sLocale;
	}

	public void setsLocale(String sLocale) {
		this.sLocale = sLocale;
	}

	public String getStandarIssRule() {
		
		if(fStandardIssRule){
			standarIssRule = "Yes";
		}else{
			standarIssRule = "No";
		}
		
		return standarIssRule;
	}

	public void setStandarIssRule(String standarIssRule) {
		this.standarIssRule = standarIssRule;
	}

	public List<TbAdmOriginXTbAdmDestinaProtSt> getTbAdmOriginXTbAdmDestinaProSt() {
		return tbAdmOriginXTbAdmDestinaProSt;
	}

	public void setTbAdmOriginXTbAdmDestinaProSt(
			List<TbAdmOriginXTbAdmDestinaProtSt> tbAdmOriginXTbAdmDestinaProSt) {
		this.tbAdmOriginXTbAdmDestinaProSt = tbAdmOriginXTbAdmDestinaProSt;
	}

	public List<TbAdmOriginRate> getTbAdmOriginRates() {
		return tbAdmOriginRates;
	}

	public void setTbAdmOriginRates(List<TbAdmOriginRate> tbAdmOriginRates) {
		this.tbAdmOriginRates = tbAdmOriginRates;
	}

	public String getStateProtocol() {
		
		if(getTbAdmOriginXTbAdmDestinaProSt().size() > 0){
			stateProtocol = "";
			for(TbAdmOriginXTbAdmDestinaProtSt st : getTbAdmOriginXTbAdmDestinaProSt()){
				if(stateProtocol == null || stateProtocol.isEmpty()){
					stateProtocol = "";
					stateProtocol += st.getTbAdmDestination().getsCode();
				}else{
					stateProtocol += ", "+st.getTbAdmDestination().getsCode();
				}
			}
		}
		
		return stateProtocol;
	}

	public void setStateProtocol(String stateProtocol) {
		this.stateProtocol = stateProtocol;
	}

	public List<TbAdmManufacturerStateXTbAdmOrigin> getAdmManufacturerStateXTbAdmOrigins() {
		return admManufacturerStateXTbAdmOrigins;
	}

	public void setAdmManufacturerStateXTbAdmOrigins(
			List<TbAdmManufacturerStateXTbAdmOrigin> admManufacturerStateXTbAdmOrigins) {
		this.admManufacturerStateXTbAdmOrigins = admManufacturerStateXTbAdmOrigins;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public List<TbAdmDestination> getTbAdmDestinationsHw() {
		return tbAdmDestinationsHw;
	}

	public void setTbAdmDestinationsHw(List<TbAdmDestination> tbAdmDestinationsHw) {
		this.tbAdmDestinationsHw = tbAdmDestinationsHw;
	}

	public List<TbAdmDestination> getTbAdmDestinationsMt() {
		return tbAdmDestinationsMt;
	}

	public void setTbAdmDestinationsMt(List<TbAdmDestination> tbAdmDestinationsMt) {
		this.tbAdmDestinationsMt = tbAdmDestinationsMt;
	}

	public List<TbAdmDestination> getTbAdmDestinationsSv() {
		return tbAdmDestinationsSv;
	}

	public void setTbAdmDestinationsSv(List<TbAdmDestination> tbAdmDestinationsSv) {
		this.tbAdmDestinationsSv = tbAdmDestinationsSv;
	}

	public List<TbAdmDestination> getTbAdmDestinationsSw() {
		return tbAdmDestinationsSw;
	}

	public void setTbAdmDestinationsSw(List<TbAdmDestination> tbAdmDestinationsSw) {
		this.tbAdmDestinationsSw = tbAdmDestinationsSw;
	}

	public List<TbTaxQuoteItem> getTbTaxQuoteItems() {
		return tbTaxQuoteItems;
	}

	public void setTbTaxQuoteItems(List<TbTaxQuoteItem> tbTaxQuoteItems) {
		this.tbTaxQuoteItems = tbTaxQuoteItems;
	}

	public List<TbTaxQuote> getTbTaxQuotesHw() {
		return tbTaxQuotesHw;
	}

	public void setTbTaxQuotesHw(List<TbTaxQuote> tbTaxQuotesHw) {
		this.tbTaxQuotesHw = tbTaxQuotesHw;
	}

	public List<TbTaxQuote> getTbTaxQuotesMt() {
		return tbTaxQuotesMt;
	}

	public void setTbTaxQuotesMt(List<TbTaxQuote> tbTaxQuotesMt) {
		this.tbTaxQuotesMt = tbTaxQuotesMt;
	}

	public List<TbTaxQuote> getTbTaxQuotesSv() {
		return tbTaxQuotesSv;
	}

	public void setTbTaxQuotesSv(List<TbTaxQuote> tbTaxQuotesSv) {
		this.tbTaxQuotesSv = tbTaxQuotesSv;
	}

	public List<TbTaxQuote> getTbTaxQuotesSw() {
		return tbTaxQuotesSw;
	}

	public void setTbTaxQuotesSw(List<TbTaxQuote> tbTaxQuotesSw) {
		this.tbTaxQuotesSw = tbTaxQuotesSw;
	}

	public TbAdmProductType getTbAdmProductTypeHw() {
		return tbAdmProductTypeHw;
	}

	public void setTbAdmProductTypeHw(TbAdmProductType tbAdmProductTypeHw) {
		this.tbAdmProductTypeHw = tbAdmProductTypeHw;
	}

	public TbAdmProductType getTbAdmProductTypeSw() {
		return tbAdmProductTypeSw;
	}

	public void setTbAdmProductTypeSw(TbAdmProductType tbAdmProductTypeSw) {
		this.tbAdmProductTypeSw = tbAdmProductTypeSw;
	}

	public TbAdmProductType getTbAdmProductTypeCs() {
		return tbAdmProductTypeCs;
	}

	public void setTbAdmProductTypeCs(TbAdmProductType tbAdmProductTypeCs) {
		this.tbAdmProductTypeCs = tbAdmProductTypeCs;
	}

	public TbAdmProductType getTbAdmProductTypePs() {
		return tbAdmProductTypePs;
	}

	public void setTbAdmProductTypePs(TbAdmProductType tbAdmProductTypePs) {
		this.tbAdmProductTypePs = tbAdmProductTypePs;
	}

	public TbAdmProductType getTbAdmProductTypeMd() {
		return tbAdmProductTypeMd;
	}

	public void setTbAdmProductTypeMd(TbAdmProductType tbAdmProductTypeMd) {
		this.tbAdmProductTypeMd = tbAdmProductTypeMd;
	}

	public boolean isProductTypeHW() {
		
		return productTypeHW;
	}

	public void setProductTypeHW(boolean productTypeHW) {
		this.productTypeHW = productTypeHW;
	}

	public boolean isProductTypeSW() {
		
		return productTypeSW;
	}

	public void setProductTypeSW(boolean productTypeSW) {
		this.productTypeSW = productTypeSW;
	}

	public boolean isProductTypeCS() {
		
		return productTypeCS;
	}

	public void setProductTypeCS(boolean productTypeCS) {
		this.productTypeCS = productTypeCS;
	}

	
	public boolean isProductTypePS() {
		
		return productTypePS;
	}

	public void setProductTypePS(boolean productTypePS) {
		this.productTypePS = productTypePS;
	}

	public boolean isProductTypeMD() {
		
		return productTypeMD;
	}

	public void setProductTypeMD(boolean productTypeMD) {
		this.productTypeMD = productTypeMD;
	}

	public List<TbAdmOriginXProductTypeSV> getTbAdmOriginXProductTypeSVs() {
		return tbAdmOriginXProductTypeSVs;
	}

	public void setTbAdmOriginXProductTypeSVs(
			List<TbAdmOriginXProductTypeSV> tbAdmOriginXProductTypeSVs) {
		this.tbAdmOriginXProductTypeSVs = tbAdmOriginXProductTypeSVs;
	}

	public List<OriginXProductTypeSVTO> getListaOriginXProductType() {
		return listaOriginXProductType;
	}

	public void setListaOriginXProductType(
			List<OriginXProductTypeSVTO> listaOriginXProductType) {
		this.listaOriginXProductType = listaOriginXProductType;
	}

	
}