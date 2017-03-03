package br.com.saboia.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TBADMDAILYDOLLAR_TEMP database table.
 * 
 */
@Entity
@Table(name="TBADMDAILYDOLLAR_TEMP")
@NamedQuery(name="TbAdmDailyDollarTemp.findAll", query="SELECT t FROM TbAdmDailyDollarTemp t")
public class TbAdmDailyDollarTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMDAILYDOLLAR_TEMP_ID_GENERATOR", sequenceName="SEQ_TBADMDAILYDOLLAR_TEMP", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMDAILYDOLLAR_TEMP_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.DATE)
	private Date dtDollar;

	private long idTemp;

	private Float rDollar;

	public TbAdmDailyDollarTemp() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDtDollar() {
		return this.dtDollar;
	}

	public void setDtDollar(Date dtDollar) {
		this.dtDollar = dtDollar;
	}

	public long getIdTemp() {
		return this.idTemp;
	}

	public void setIdTemp(long idTemp) {
		this.idTemp = idTemp;
	}

	public Float getrDollar() {
		return rDollar;
	}

	public void setrDollar(Float rDollar) {
		this.rDollar = rDollar;
	}

	

}