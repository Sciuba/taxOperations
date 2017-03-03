package br.com.operations.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;


/**
 * The persistent class for the TBADMCATEGORY database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmCategory.findAll", query="SELECT t FROM TbAdmCategory t")
public class TbAdmCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMCATEGORY_ID_GENERATOR", sequenceName="SEQ_TBADMCATEGORY", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMCATEGORY_ID_GENERATOR")
	private long id;

	private Integer imodule;

	private String sAcronym;

	private String sCategory;
	
//	@OneToMany(mappedBy="tbAdmCategory")
//	private List<TbAdmPerson> tbAdmPersons;
	
	@Transient
	private String module;

	public TbAdmCategory() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getImodule() {
		return imodule;
	}

	public void setImodule(Integer imodule) {
		this.imodule = imodule;
	}

	public String getsAcronym() {
		return sAcronym;
	}

	public void setsAcronym(String sAcronym) {
		this.sAcronym = sAcronym;
	}

	public String getsCategory() {
		return sCategory;
	}

	public void setsCategory(String sCategory) {
		this.sCategory = sCategory;
	}

	public String getModule() {
		
		if(this.imodule == 0){
			module = "All";
		}else if(this.imodule == 1){
			module = "Quote";
		}else{
			module = "Order";
		}
		
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

//	public List<TbAdmPerson> getTbAdmPersons() {
//		return tbAdmPersons;
//	}
//
//	public void setTbAdmPersons(List<TbAdmPerson> tbAdmPersons) {
//		this.tbAdmPersons = tbAdmPersons;
//	}
	

}