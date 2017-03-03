package br.com.operations.entity;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQuery(name="TbAdmMaterialCategorySupport.findAll", query="SELECT t FROM TbAdmMaterialCategorySupport t")
public class TbAdmMaterialCategorySupport implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="TBADMMATERIALCATEGORYSUPPORT_ID_GENERATOR", sequenceName="SEQ_ADMMATERIALCATEGORYSUPPORT", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMMATERIALCATEGORYSUPPORT_ID_GENERATOR")
	private long id;

	private String sName;
	
	private Float rRate;
	
	private boolean fDeleted;
	
	private boolean fAvailable;
	
	@OneToOne
	@JoinColumn(name="IDADMMATERIAL")
	private TbAdmMaterial tbAdmMaterial;
	
	@OneToMany(mappedBy="tbAdmMaterialCategorySupport")
	private List<TbAdmMaterial> tbAdmMaterials;
	
	public TbAdmMaterialCategorySupport(){
		
	}
	
	public long getId() {
		return id;
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
	
	public Float getrRate() {
		
		if(rRate != null){
			return rRate * 100;
		}else{
			return rRate;
		}
	}

	public void setrRate(Float rRate) {
		
		if(rRate != null){
			this.rRate = (rRate / 100);
		}else{
			this.rRate = rRate;
		}
	}

	public boolean isfAvailable() {
		return fAvailable;
	}

	public void setfAvailable(boolean fAvailable) {
		this.fAvailable = fAvailable;
	}

	public List<TbAdmMaterial> getTbAdmMaterials() {
		return tbAdmMaterials;
	}

	public void setTbAdmMaterials(List<TbAdmMaterial> tbAdmMaterials) {
		this.tbAdmMaterials = tbAdmMaterials;
	}

	public boolean isfDeleted() {
		return fDeleted;
	}

	public void setfDeleted(boolean fDeleted) {
		this.fDeleted = fDeleted;
	}

	public TbAdmMaterial getTbAdmMaterial() {
		return tbAdmMaterial;
	}

	public void setTbAdmMaterial(TbAdmMaterial tbAdmMaterial) {
		this.tbAdmMaterial = tbAdmMaterial;
	}
	
	
	
	
}
