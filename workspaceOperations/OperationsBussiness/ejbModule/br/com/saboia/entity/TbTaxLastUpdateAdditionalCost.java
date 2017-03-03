package br.com.saboia.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TBTAXLASTUPDATEADDITIONALCOST database table.
 * 
 */
@Entity
@NamedQuery(name="TbTaxLastUpdateAdditionalCost.findAll", query="SELECT t FROM TbTaxLastUpdateAdditionalCost t")
public class TbTaxLastUpdateAdditionalCost implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBTAXLASTUPDATEADDITIONALCOST_ID_GENERATOR", sequenceName="SEQ_TBTAXLASTUPDATEADDCOST", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBTAXLASTUPDATEADDITIONALCOST_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtUpdate;

	@ManyToOne
	@JoinColumn(name="IDSYSUSER")
	private TbSysUser tbSysUser;

	@ManyToOne
	@JoinColumn(name="IDTAXQUOTE")
	private TbTaxQuote tbTaxQuote;

	private Double mValue;

	private String sCostName;

	public TbTaxLastUpdateAdditionalCost() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDtUpdate() {
		return dtUpdate;
	}

	public void setDtUpdate(Date dtUpdate) {
		this.dtUpdate = dtUpdate;
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

	public Double getmValue() {
		return mValue;
	}

	public void setmValue(Double mValue) {
		this.mValue = mValue;
	}

	public String getsCostName() {
		return sCostName;
	}

	public void setsCostName(String sCostName) {
		this.sCostName = sCostName;
	}

	

}