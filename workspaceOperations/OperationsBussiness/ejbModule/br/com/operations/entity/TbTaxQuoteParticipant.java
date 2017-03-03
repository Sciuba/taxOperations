package br.com.operations.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the TBTAXQUOTEPARTICIPANT database table.
 * 
 */
@Entity
@NamedQuery(name="TbTaxQuoteParticipant.findAll", query="SELECT t FROM TbTaxQuoteParticipant t")
public class TbTaxQuoteParticipant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBTAXQUOTEPARTICIPANT_ID_GENERATOR", sequenceName="SEQ_TBTAXQUOTEPARTICIPANT", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBTAXQUOTEPARTICIPANT_ID_GENERATOR")
	private long id;

	@ManyToOne
	@JoinColumn(name="IDSYSUSER")
	private TbSysUser tbSysUser ;

	@ManyToOne
	@JoinColumn(name="IDTAXQUOTE")
	private TbTaxQuote tbTaxQuote;

	public TbTaxQuoteParticipant() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TbSysUser getTbSysUser() {
		return tbSysUser;
	}

	public void setTbSysUser(TbSysUser tbSysUser) {
		this.tbSysUser = tbSysUser;
	}

	public TbTaxQuote getTbTaxQuote() {
		return tbTaxQuote;
	}

	public void setTbTaxQuote(TbTaxQuote tbTaxQuote) {
		this.tbTaxQuote = tbTaxQuote;
	}

	

	

}