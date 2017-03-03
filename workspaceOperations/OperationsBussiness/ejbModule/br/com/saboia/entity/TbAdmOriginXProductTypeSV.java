package br.com.saboia.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the TBADMORIGINXPRODUCTTYPE database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmOriginXProductType.findAll", query="SELECT t FROM TbAdmOriginXProductTypeSV t")
public class TbAdmOriginXProductTypeSV implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMORIGINXPRODUCTTYPE_ID_GENERATOR", sequenceName="SEQ_TBADMORIGINXPRODUCTTYPE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMORIGINXPRODUCTTYPE_ID_GENERATOR")
	private long id;

	@ManyToOne
	@JoinColumn(name="IDADMORIGIN")
	private TbAdmOrigin tbAdmOrigin;

	@ManyToOne
	@JoinColumn(name="IDADMPRODUCTTYPE")
	private TbAdmProductType tbAdmProductType;

	private Float rIss;

	public TbAdmOriginXProductTypeSV() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TbAdmOrigin getTbAdmOrigin() {
		return tbAdmOrigin;
	}

	public void setTbAdmOrigin(TbAdmOrigin tbAdmOrigin) {
		this.tbAdmOrigin = tbAdmOrigin;
	}

	public TbAdmProductType getTbAdmProductType() {
		return tbAdmProductType;
	}

	public void setTbAdmProductType(TbAdmProductType tbAdmProductType) {
		this.tbAdmProductType = tbAdmProductType;
	}

	public Float getrIss() {
		
		if(rIss != null){
			return rIss * 100;
		}else{
			return rIss;			
		}

	}

	public void setrIss(Float rIss) {
		
		if(rIss != null){
			this.rIss = ( rIss / 100 );
		}else{
			this.rIss = rIss;			
		}

	}


}