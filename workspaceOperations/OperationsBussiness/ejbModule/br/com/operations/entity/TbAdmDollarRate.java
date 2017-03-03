package br.com.operations.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBADMDOLLARRATE database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmDollarRate.findAll", query="SELECT t FROM TbAdmDollarRate t")
public class TbAdmDollarRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMDOLLARRATE_ID_GENERATOR", sequenceName="SEQ_TBADMDOLLARRATE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMDOLLARRATE_ID_GENERATOR")
	private long id;

	private Integer iMonth;

	private Integer iYear;

	private Float rDollar;
	
	@Transient
	private String mes;

	public TbAdmDollarRate() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getiMonth() {
		return iMonth;
	}

	public void setiMonth(Integer iMonth) {
		this.iMonth = iMonth;
	}

	public Integer getiYear() {
		return iYear;
	}

	public void setiYear(Integer iYear) {
		this.iYear = iYear;
	}

	public Float getrDollar() {
		return rDollar;
	}

	public void setrDollar(Float rDollar) {
		this.rDollar = rDollar;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}
	
	
	
}