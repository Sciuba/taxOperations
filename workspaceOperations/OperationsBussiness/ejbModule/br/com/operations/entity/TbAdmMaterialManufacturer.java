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
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the TBADMMANUFACTURE database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmMaterialManufacture.findAll", query="SELECT t FROM TbAdmMaterialManufacturer t")
@Table( name = "TBADMMATERIALMANUFACTURER")
public class TbAdmMaterialManufacturer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMMATERIALMANUFACTURE_ID_GENERATOR", sequenceName="SEQ_TBADMMATERIALMANUFACTURE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMMATERIALMANUFACTURE_ID_GENERATOR")
	private long id;
	
	@Column(name="SNAME")
	private String sName;
	
	@Column(name="SALIAS")
	private String sAlias;
	
	@Column(name="SDESCRIPTION")
	private String sDescription;
	
	@Column(name="SINTERNALID")
	private String sInternalId;

	@Transient
	private String internalIdDescription;
	
	@Transient
	private String stateDescription;
	
	//bi-directional many-to-one association to TbAdmManufactureState
	@ManyToOne
	@JoinColumn(name="IDADMMANUFACTURERSTATEOFORIGIN")
	private TbAdmManufacturerStateOfOrigin tbAdmManufacturerStateOfOrigin;

	@OneToMany(mappedBy="tbAdmMaterialManufacturer")
	private List<TbAdmMaterial> tbAdmMaterials;
	
	@OneToMany(mappedBy="tbAdmMaterialManufacturer")
	private List<TbTaxQuoteItem> tbTaxQuoteItems;
	
	public TbAdmMaterialManufacturer() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}
	
	public String getsAlias() {
		return sAlias;
	}

	public void setsAlias(String sAlias) {
		this.sAlias = sAlias;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public String getsInternalId() {
		return sInternalId;
	}

	public void setsInternalId(String sInternalId) {
		this.sInternalId = sInternalId;
	}

	public TbAdmManufacturerStateOfOrigin getTbAdmManufacturerStateOfOrigin() {
		return this.tbAdmManufacturerStateOfOrigin;
	}

	public void setTbAdmManufacturerStateOfOrigin(TbAdmManufacturerStateOfOrigin tbAdmManufacturerStateOfOrigin) {
		this.tbAdmManufacturerStateOfOrigin = tbAdmManufacturerStateOfOrigin;
	}

	public String getInternalIdDescription() {
		return internalIdDescription;
	}

	public void setInternalIdDescription(String internalIdDescription) {
		this.internalIdDescription = internalIdDescription;
	}

	public List<TbAdmMaterial> getTbAdmMaterials() {
		return tbAdmMaterials;
	}

	public void setTbAdmMaterials(List<TbAdmMaterial> tbAdmMaterials) {
		this.tbAdmMaterials = tbAdmMaterials;
	}

	public String getStateDescription() {
		return stateDescription;
	}

	public void setStateDescription(String stateDescription) {
		this.stateDescription = stateDescription;
	}

	public List<TbTaxQuoteItem> getTbTaxQuoteItems() {
		return tbTaxQuoteItems;
	}

	public void setTbTaxQuoteItems(List<TbTaxQuoteItem> tbTaxQuoteItems) {
		this.tbTaxQuoteItems = tbTaxQuoteItems;
	}
	
	
	
}