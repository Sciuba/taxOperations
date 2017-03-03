package br.com.saboia.entity;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


/**
 * The persistent class for the TBADMMATERIALCATEGORY database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmMaterialCategory.findAll", query="SELECT t FROM TbAdmMaterialCategory t")
public class TbAdmMaterialCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMMATERIALCATEGORY_ID_GENERATOR", sequenceName="SEQ_MATERIALCATEGORY", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMMATERIALCATEGORY_ID_GENERATOR")
	private long id;

	private String sHashCode;

	private String sDescription;

	private String iHashLevel;
	
	private Boolean fGroup;
	
	private Boolean fAvailable;
	
	private Boolean fDeleted;
	
	private Blob bHashData;

	@OneToMany(mappedBy="tbAdmMaterialCategory")
	private List<TbAdmMaterial> tbAdmMaterials;

	public TbAdmMaterialCategory() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getsHashCode() {
		return sHashCode;
	}

	public void setsHashCode(String sHashCode) {
		this.sHashCode = sHashCode;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public String getiHashLevel() {
		return iHashLevel;
	}

	public void setiHashLevel(String iHashLevel) {
		this.iHashLevel = iHashLevel;
	}

	public Boolean getfGroup() {
		return fGroup;
	}

	public void setfGroup(Boolean fGroup) {
		this.fGroup = fGroup;
	}

	public Boolean getfAvailable() {
		return fAvailable;
	}

	public void setfAvailable(Boolean fAvailable) {
		this.fAvailable = fAvailable;
	}

	public Boolean getfDeleted() {
		return fDeleted;
	}

	public void setfDeleted(Boolean fDeleted) {
		this.fDeleted = fDeleted;
	}

	public Blob getbHashData() {
		return bHashData;
	}

	public void setbHashData(Blob bHashData) {
		this.bHashData = bHashData;
	}

	public List<TbAdmMaterial> getTbAdmMaterials() {
		return tbAdmMaterials;
	}
	
	public void setTbAdmMaterials(List<TbAdmMaterial> tbAdmMaterials) {
		this.tbAdmMaterials = tbAdmMaterials;
	}
		

}