package br.com.operations.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the TBTAXQUOTELOG database table.
 * 
 */
@Entity
@NamedQuery(name="TbTaxQuoteLog.findAll", query="SELECT t FROM TbTaxQuoteLog t")
public class TbTaxQuoteLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBTAXQUOTELOG_ID_GENERATOR", sequenceName="SEQ_TBTAXQUOTELOG", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBTAXQUOTELOG_ID_GENERATOR")
	private long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtCreated;

	private String sDescription;

	private String sLoginUser;
	
	private String sComments;
	
	@ManyToOne
	@JoinColumn(name="IDTAXQUOTE")
	private TbTaxQuote tbTaxQuote;
	
	public TbTaxQuoteLog() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDtCreated() {
		return dtCreated;
	}

	public void setDtCreated(Date dtCreated) {
		this.dtCreated = dtCreated;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public String getsLoginUser() {
		return sLoginUser;
	}

	public void setsLoginUser(String sLoginUser) {
		this.sLoginUser = sLoginUser;
	}

	public TbTaxQuote getTbTaxQuote() {
		return tbTaxQuote;
	}

	public void setTbTaxQuote(TbTaxQuote tbTaxQuote) {
		this.tbTaxQuote = tbTaxQuote;
	}

	public String getsComments() {
		return sComments;
	}

	public void setsComments(String sComments) {
		this.sComments = sComments;
	}

}