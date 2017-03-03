package br.com.operations.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the TBADMRATE database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmRate.findAll", query="SELECT t FROM TbAdmRate t")
public class TbAdmRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMRATE_ID_GENERATOR", sequenceName="SEQ_TBADMRATE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMRATE_ID_GENERATOR")
	private long id;

	@ManyToOne
	@JoinColumn(name="IDADMDESTINATION")
	private TbAdmDestination tbAdmDestination;

	private Double mMandatoryRackMinValue;
	
	@Column(name="FFIXEDTAXRATE")
	private boolean fFixedTaxRate;
	
	private Float rCofins;

	private Float rDollarQuote;

	private Float rIpiPPB;

	private Float rPis;

	@Transient
	private String fixedTaxRate;
	
	public TbAdmRate() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TbAdmDestination getTbAdmDestination() {
		return tbAdmDestination;
	}

	public void setTbAdmDestination(TbAdmDestination tbAdmDestination) {
		this.tbAdmDestination = tbAdmDestination;
	}

	public Double getmMandatoryRackMinValue() {
		return mMandatoryRackMinValue;
	}

	public void setmMandatoryRackMinValue(Double mMandatoryRackMinValue) {
		this.mMandatoryRackMinValue = mMandatoryRackMinValue;
	}

	public Float getrCofins() {
		if(rCofins != null){
			return rCofins * 100;
		}else{
			return rCofins;
		}
	}

	public void setrCofins(Float rCofins) {
		if(rCofins != null){
			this.rCofins = (rCofins / 100);
		}else{
			this.rCofins = rCofins;
		}
	}

	public Float getrDollarQuote() {
		if(rDollarQuote != null){
			return rDollarQuote * 100;
		}else{
			return rDollarQuote;
		}
	}

	public void setrDollarQuote(Float rDollarQuote) {
		if(rDollarQuote != null){
			this.rDollarQuote = (rDollarQuote / 100);
		}else{
			this.rDollarQuote = rDollarQuote;
		}
	}

	public Float getrIpiPPB() {
		if(rIpiPPB != null){
			return rIpiPPB * 100;
		}else{
			return rIpiPPB;
		}
	}

	public void setrIpiPPB(Float rIpiPPB) {
		if(rIpiPPB != null){
			this.rIpiPPB = (rIpiPPB / 100);
		}else{
			this.rIpiPPB = rIpiPPB;
		}
	}

	public Float getrPis() {
		if(rPis != null){
			return rPis * 100;
		}else{
			return rPis;
		}
	}

	public void setrPis(Float rPis) {
		if(rPis != null){
			this.rPis = (rPis / 100);
		}else{
			this.rPis = rPis;
		}
	}

	public boolean isfFixedTaxRate() {
		return fFixedTaxRate;
	}

	public void setfFixedTaxRate(boolean fFixedTaxRate) {
		this.fFixedTaxRate = fFixedTaxRate;
	}

	public String getFixedTaxRate() {
		
		if(isfFixedTaxRate()){
			fixedTaxRate = "Yes";
		}else{
			fixedTaxRate = "No";
		}
		
		return fixedTaxRate;
	}

	public void setFixedTaxRate(String fixedTaxRate) {
		this.fixedTaxRate = fixedTaxRate;
	}
	
	
}