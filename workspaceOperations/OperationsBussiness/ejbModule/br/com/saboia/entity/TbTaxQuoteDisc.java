package br.com.saboia.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBTAXQUOTEDISC database table.
 * 
 */
@Entity
@NamedQuery(name="TbTaxQuoteDisc.findAll", query="SELECT t FROM TbTaxQuoteDisc t")
public class TbTaxQuoteDisc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBTAXQUOTEDISC_ID_GENERATOR", sequenceName="SEQ_TBTAXQUOTEDISC", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBTAXQUOTEDISC_ID_GENERATOR")
	private long id;

	@ManyToOne
	@JoinColumn(name="IDADMDISCCLASS")
	private TbAdmDiscClass tbAdmDiscClass;

	private Double rdiscount;

	//bi-directional many-to-one association to TbTaxQuote
	@ManyToOne
	@JoinColumn(name="IDTAXQUOTE")
	private TbTaxQuote tbtaxquote;

	public TbTaxQuoteDisc() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public TbAdmDiscClass getTbAdmDiscClass() {
		return tbAdmDiscClass;
	}

	public void setTbAdmDiscClass(TbAdmDiscClass tbAdmDiscClass) {
		this.tbAdmDiscClass = tbAdmDiscClass;
	}

	public Double getRdiscount() {
		return rdiscount;
	}

	public void setRdiscount(Double rdiscount) {
		this.rdiscount = rdiscount;
	}

	public TbTaxQuote getTbtaxquote() {
		return this.tbtaxquote;
	}

	public void setTbtaxquote(TbTaxQuote tbtaxquote) {
		this.tbtaxquote = tbtaxquote;
	}

}