package br.com.operations.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the TBADMGROUPOFCONTENT database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmGroupOfContent.findAll", query="SELECT t FROM TbAdmGroupOfContent t")
public class TbAdmGroupOfContent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMGROUPOFCONTENT_ID_GENERATOR", sequenceName="SEQ_TBADMGROUPOFCONTENT", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMGROUPOFCONTENT_ID_GENERATOR")
	private long id;

	private String sDescription;

	@OneToMany(mappedBy="tbAdmGroupOfContent")
	private List<TbTaxQuote> tbTaxQuotes;
	
	public TbAdmGroupOfContent() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSDescription() {
		return this.sDescription;
	}

	public void setSDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public List<TbTaxQuote> getTbTaxQuotes() {
		return tbTaxQuotes;
	}

	public void setTbTaxQuotes(List<TbTaxQuote> tbTaxQuotes) {
		this.tbTaxQuotes = tbTaxQuotes;
	}

	
}