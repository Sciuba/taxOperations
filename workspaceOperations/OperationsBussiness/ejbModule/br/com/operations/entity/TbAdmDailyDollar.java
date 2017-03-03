package br.com.operations.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TBADMDAILYDOLLAR database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmDailyDollar.findAll", query="SELECT t FROM TbAdmDailyDollar t")
public class TbAdmDailyDollar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMDAILYDOLLAR_ID_GENERATOR", sequenceName="SEQ_TBADMDAILYDOLLAR", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMDAILYDOLLAR_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.DATE)
	private Date dtDollar;
	
	private Float rDollar;

	@Transient
	private String dataFormat;
	
	public TbAdmDailyDollar() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getdtDollar() {
		return dtDollar;
	}

	public void setdtDollar(Date dtDollar) {
		this.dtDollar = dtDollar;
	}

	public Float getrDollar() {
		return rDollar;
	}

	public void setrDollar(Float rDollar) {
		this.rDollar = rDollar;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	

}