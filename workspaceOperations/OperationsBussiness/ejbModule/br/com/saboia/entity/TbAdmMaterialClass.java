package br.com.saboia.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBADMMATERIALCLASS database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmMaterialClass.findAll", query="SELECT t FROM TbAdmMaterialClass t")
public class TbAdmMaterialClass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMMATERIALCLASS_ID_GENERATOR", sequenceName="SEQ_TBADMMATERIALCLASS", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMMATERIALCLASS_ID_GENERATOR")
	private long id;

	private boolean fAvailable;

	private boolean fIssEspecial;

	private boolean fSubstituicaoTributaria;

	private Float rIcms;

	private Float rIpi;

	private String sDescription;

	private String sNcm;

	//bi-directional many-to-one association to TbAdmMaterial
	@OneToMany(mappedBy="tbAdmMaterialClass")
	private List<TbAdmMaterial> tbAdmMaterials;

	@OneToMany(mappedBy="tbAdmMaterialClass")
	private List<TbAdmMaterialClassXDestination> admMaterialClassXDestinations;
	
	@OneToMany(mappedBy="tbAdmMaterialClass")
	private List<TbAdmMaterialClassXOrigin> admMaterialClassXOrigins;
	
	@OneToMany(mappedBy="tbAdmMaterialClass")
	private List<TbTaxQuoteItem> tbTaxQuoteItems;
	
	//bi-directional many-to-one association to TbAdmProductType
	@ManyToOne
	@JoinColumn(name="IDADMPRODUCTTYPE")
	private TbAdmProductType tbAdmProductType;

	
	@Transient
	private String issEspecial;
	
	@Transient
	private String subsTributaria;
	
	@Transient
	private String siglaSt;
	
	public TbAdmMaterialClass() {
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

	public boolean isfIssEspecial() {
		return fIssEspecial;
	}

	public void setfIssEspecial(boolean fIssEspecial) {
		this.fIssEspecial = fIssEspecial;
	}

	public boolean isfSubstituicaoTributaria() {
		return fSubstituicaoTributaria;
	}

	public void setfSubstituicaoTributaria(boolean fSubstituicaoTributaria) {
		this.fSubstituicaoTributaria = fSubstituicaoTributaria;
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

	public Float getrIpi() {
		if(rIpi != null){
			return rIpi * 100;
		}else{
			return rIpi;
		}
	}

	public void setrIpi(Float rIpi) {
		if(rIpi != null){
			this.rIpi = (rIpi / 100);
		}else{
			this.rIpi = rIpi;
		}
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public String getsNcm() {
		return sNcm;
	}

	public void setsNcm(String sNcm) {
		this.sNcm = sNcm;
	}

	public List<TbAdmMaterial> getTbAdmMaterials() {
		return tbAdmMaterials;
	}

	public void setTbAdmMaterials(List<TbAdmMaterial> tbAdmMaterials) {
		this.tbAdmMaterials = tbAdmMaterials;
	}

	public TbAdmProductType getTbAdmProductType() {
		return tbAdmProductType;
	}

	public void setTbAdmProductType(TbAdmProductType tbAdmProductType) {
		this.tbAdmProductType = tbAdmProductType;
	}

	public TbAdmMaterial addTbadmmaterial(TbAdmMaterial tbadmmaterial) {
		getTbAdmMaterials().add(tbadmmaterial);
		tbadmmaterial.setTbAdmMaterialClass(this);

		return tbadmmaterial;
	}

	public TbAdmMaterial removeTbadmmaterial(TbAdmMaterial tbadmmaterial) {
		getTbAdmMaterials().remove(tbadmmaterial);
		tbadmmaterial.setTbAdmMaterialClass(null);

		return tbadmmaterial;
	}

	public List<TbAdmMaterialClassXDestination> getAdmMaterialClassXDestinations() {
		return admMaterialClassXDestinations;
	}

	public void setAdmMaterialClassXDestinations(
			List<TbAdmMaterialClassXDestination> admMaterialClassXDestinations) {
		this.admMaterialClassXDestinations = admMaterialClassXDestinations;
	}

	public List<TbAdmMaterialClassXOrigin> getAdmMaterialClassXOrigins() {
		return admMaterialClassXOrigins;
	}

	public void setAdmMaterialClassXOrigins(
			List<TbAdmMaterialClassXOrigin> admMaterialClassXOrigins) {
		this.admMaterialClassXOrigins = admMaterialClassXOrigins;
	}

	public String getIssEspecial() {
		
		if(isfIssEspecial()){
			issEspecial = "Sim";
		}else{
			issEspecial = "Não";
		}
		
		return issEspecial;
	}

	public void setIssEspecial(String issEspecial) {
		this.issEspecial = issEspecial;
	}

	public String getSubsTributaria() {
		
		if(isfSubstituicaoTributaria()){
			subsTributaria = "Sim";
		}else{
			subsTributaria = "Não";
		}
		
		return subsTributaria;
	}
	
	public void setSubsTributaria(String subsTributaria) {
		this.subsTributaria = subsTributaria;
	}

	public String getSubsTributariaST() {
		
		if(isfSubstituicaoTributaria()){
			return "(ST)";
		}else{
			return "";
		}
	}
	
	public String getSiglaSt() {
		
		if(isfSubstituicaoTributaria()){
			siglaSt = "(ST)";
		}else{
			siglaSt = "";
		}
		
		return siglaSt;
	}

	public void setSiglaSt(String siglaSt) {
		this.siglaSt = siglaSt;
	}

	public List<TbTaxQuoteItem> getTbTaxQuoteItems() {
		return tbTaxQuoteItems;
	}

	public void setTbTaxQuoteItems(List<TbTaxQuoteItem> tbTaxQuoteItems) {
		this.tbTaxQuoteItems = tbTaxQuoteItems;
	}

	
	
}