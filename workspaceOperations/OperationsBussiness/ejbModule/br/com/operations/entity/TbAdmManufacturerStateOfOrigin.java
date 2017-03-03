package br.com.operations.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the TBADMMANUFACTURESTATE database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmManufacturerStateOfOrigin.findAll", query="SELECT t FROM TbAdmManufacturerStateOfOrigin t")
@Table(name = "TBADMMANUFACTURERSTATEOFORIGIN")
public class TbAdmManufacturerStateOfOrigin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMMANUFACTURESTATE_ID_GENERATOR", sequenceName="SEQ_TBADMMANUFACTURESTATE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMMANUFACTURESTATE_ID_GENERATOR")
	private long id;

	private Float ricms;
	
	@Column(name="RICMSMATERIALIMPORTADO")
	private Float ricmsMaterialImportado;
	
	private Float ripi;
	
	@Column(name="SCODE")
	private String sCode;
	
	@Column(name="SDESCRIPTION")
	private String sDescription;

	//bi-directional many-to-one association to TbAdmManufacture
	@OneToMany(mappedBy="tbAdmManufacturerStateOfOrigin")
	private List<TbAdmMaterialManufacturer> tbAdmMaterialManufacture;
	
	@OneToMany(mappedBy="tbAdmManufacturerStateOfOrigin")
	private List<TbAdmManufacturerStateXTbAdmOrigin> admManufacturerStateXTbAdmOrigins;

	public TbAdmManufacturerStateOfOrigin() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Float getRicms() {
		if(ricms  != null){
			return ricms * 100;
		}else{
			return ricms;
		}
	}

	public void setRicms(Float ricms) {
		if(ricms != null){
			this.ricms = (ricms / 100);
		}else{
			this.ricms = ricms;
		}
	}

	public Float getRicmsMaterialImportado() {
		if(ricmsMaterialImportado != null){
			return ricmsMaterialImportado * 100;
		}else{
			return ricmsMaterialImportado;
		}
	}

	public void setRicmsMaterialImportado(Float ricmsMaterialImportado) {
		if(ricmsMaterialImportado != null){
			this.ricmsMaterialImportado = (ricmsMaterialImportado / 100);
		}else{
			this.ricmsMaterialImportado = ricmsMaterialImportado; 
		}
	}

	public Float getRipi() {
		if(ripi != null){
			return ripi * 100;
		}else{
			return ripi;
		}
	}

	public void setRipi(Float ripi) {
		if(ripi != null){
			this.ripi = (ripi / 100);
		}else{
			this.ripi = ripi;
		}
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public List<TbAdmMaterialManufacturer> getTbAdmManuFactures() {
		return this.tbAdmMaterialManufacture;
	}

	public void setTbadmmanufactures(List<TbAdmMaterialManufacturer> tbAdmMaterialManufacture) {
		this.tbAdmMaterialManufacture = tbAdmMaterialManufacture;
	}

	public TbAdmMaterialManufacturer addTbAdmManufacture(TbAdmMaterialManufacturer tbAdmManufacture) {
		getTbAdmManuFactures().add(tbAdmManufacture);
		tbAdmManufacture.setTbAdmManufacturerStateOfOrigin(this);

		return tbAdmManufacture;
	}

	public TbAdmMaterialManufacturer removeTbAdmManuFacture(TbAdmMaterialManufacturer tbAdmManufacture) {
		getTbAdmManuFactures().remove(tbAdmManufacture);
		tbAdmManufacture.setTbAdmManufacturerStateOfOrigin(null);

		return tbAdmManufacture;
	}

	public List<TbAdmManufacturerStateXTbAdmOrigin> getAdmManufacturerStateXTbAdmOrigins() {
		return admManufacturerStateXTbAdmOrigins;
	}

	public void setAdmManufacturerStateXTbAdmOrigins(
			List<TbAdmManufacturerStateXTbAdmOrigin> admManufacturerStateXTbAdmOrigins) {
		this.admManufacturerStateXTbAdmOrigins = admManufacturerStateXTbAdmOrigins;
	}

	
	
}