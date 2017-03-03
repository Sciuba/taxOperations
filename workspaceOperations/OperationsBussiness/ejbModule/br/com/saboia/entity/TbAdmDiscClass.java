package br.com.saboia.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the TBADMDISCCLASS database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmDiscClass.findAll", query="SELECT t FROM TbAdmDiscClass t")
public class TbAdmDiscClass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMDISCCLASS_ID_GENERATOR", sequenceName="SEQ_TBADMPERSON")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMDISCCLASS_ID_GENERATOR")
	private long id;

	private String sCode;

	private String sDecription;

	@OneToMany(mappedBy="tbAdmDiscClass")
	private List<TbAdmMaterial> tbAdmMaterials;
	
	@OneToMany(mappedBy="tbAdmDiscClass")
	private List<TbTaxQuoteItem> tbTaxQuoteItems;
	
	@OneToMany(mappedBy="tbAdmDiscClass")
	private List<TbTaxQuoteDisc> tbTaxQuoteDiscs;
	
	public TbAdmDiscClass() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSCode() {
		return this.sCode;
	}

	public void setSCode(String sCode) {
		this.sCode = sCode;
	}

	public String getSDecription() {
		return this.sDecription;
	}

	public void setSDecription(String sDecription) {
		this.sDecription = sDecription;
	}

	public List<TbAdmMaterial> getTbAdmMaterials() {
		return tbAdmMaterials;
	}

	public void setTbAdmMaterials(List<TbAdmMaterial> tbAdmMaterials) {
		this.tbAdmMaterials = tbAdmMaterials;
	}

	public List<TbTaxQuoteItem> getTbTaxQuoteItems() {
		return tbTaxQuoteItems;
	}

	public void setTbTaxQuoteItems(List<TbTaxQuoteItem> tbTaxQuoteItems) {
		this.tbTaxQuoteItems = tbTaxQuoteItems;
	}

	public List<TbTaxQuoteDisc> getTbTaxQuoteDiscs() {
		return tbTaxQuoteDiscs;
	}

	public void setTbTaxQuoteDiscs(List<TbTaxQuoteDisc> tbTaxQuoteDiscs) {
		this.tbTaxQuoteDiscs = tbTaxQuoteDiscs;
	}
	
	
}