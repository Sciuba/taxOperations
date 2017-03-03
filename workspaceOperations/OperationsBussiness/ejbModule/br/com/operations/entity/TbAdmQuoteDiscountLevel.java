package br.com.operations.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBADMQUOTEDISCOUNTLEVEL database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmQuoteDiscountLevel.findAll", query="SELECT t FROM TbAdmQuoteDiscountLevel t")
public class TbAdmQuoteDiscountLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMQUOTEDISCOUNTLEVEL_ID_GENERATOR", sequenceName="SEQ_TBADMQUOTEDISCOUNTLEVEL", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMQUOTEDISCOUNTLEVEL_ID_GENERATOR")
	private long id;

	private boolean fDiscountModulePerLevel;

	private Float rMinimumMarginLevel1;

	private Float rMinimumMarginLevel2;

	private Float rMinimumMarginLevel3;
	
	@Transient
	private String discountModulePerLevel;

	public TbAdmQuoteDiscountLevel() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isfDiscountModulePerLevel() {
		return fDiscountModulePerLevel;
	}

	public void setfDiscountModulePerLevel(boolean fDiscountModulePerLevel) {
		this.fDiscountModulePerLevel = fDiscountModulePerLevel;
	}

	public Float getrMinimumMarginLevel1() {
		if(rMinimumMarginLevel1 != null){
			return rMinimumMarginLevel1 * 100;
		}else{
			return rMinimumMarginLevel1;
		}
	}

	public void setrMinimumMarginLevel1(Float rMinimumMarginLevel1) {
		if(rMinimumMarginLevel1 != null){
			this.rMinimumMarginLevel1 = (rMinimumMarginLevel1 / 100);
		}else{
			this.rMinimumMarginLevel1 = rMinimumMarginLevel1;
		}
	}

	public Float getrMinimumMarginLevel2() {
		if(rMinimumMarginLevel2 != null){
			return rMinimumMarginLevel2 * 100;
		}else{
			return rMinimumMarginLevel2;
		}
	}

	public void setrMinimumMarginLevel2(Float rMinimumMarginLevel2) {
		if(rMinimumMarginLevel2 != null){
			this.rMinimumMarginLevel2 = (rMinimumMarginLevel2 / 100);
		}else{
			this.rMinimumMarginLevel2 = rMinimumMarginLevel2;
		}
	}

	public Float getrMinimumMarginLevel3() {
		if(rMinimumMarginLevel3 != null){
			return rMinimumMarginLevel3 * 100;
		}else{
			return rMinimumMarginLevel3;
		}
	}

	public void setrMinimumMarginLevel3(Float rMinimumMarginLevel3) {
		if(rMinimumMarginLevel3 != null){
			this.rMinimumMarginLevel3 = (rMinimumMarginLevel3 / 100);
		}else{
			this.rMinimumMarginLevel3 = rMinimumMarginLevel3; 
		}
	}

	public String getDiscountModulePerLevel() {
		
		if(isfDiscountModulePerLevel()){
			discountModulePerLevel = "Yes";
		}else{
			discountModulePerLevel = "No";
		}
		
		return discountModulePerLevel;
	}

	public void setDiscountModulePerLevel(String discountModulePerLevel) {
		this.discountModulePerLevel = discountModulePerLevel;
	}

	
}