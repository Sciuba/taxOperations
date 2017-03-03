package br.com.saboia.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the TBAMDORIGINRATE database table.
 * 
 */
@Entity
@Table(name="TBADMORIGINRATE")
@NamedQuery(name="TbAdmOriginRate.findAll", query="SELECT t FROM TbAdmOriginRate t")
public class TbAdmOriginRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBAMDORIGINRATE_ID_GENERATOR", sequenceName="SEQ_TBADMDESTINATION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBAMDORIGINRATE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.DATE)
	private Date dtExpiration;
	
	@ManyToOne
	@JoinColumn(name="IDADMORIGIN")
	private TbAdmOrigin tbAdmOrigin;

	private Float rIss;

	private Float rIssSoft;

	public TbAdmOriginRate() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDtExpiration() {
		return dtExpiration;
	}

	public void setDtExpiration(Date dtExpiration) {
		this.dtExpiration = dtExpiration;
	}

	public TbAdmOrigin getTbAdmOrigin() {
		return tbAdmOrigin;
	}

	public void setTbAdmOrigin(TbAdmOrigin tbAdmOrigin) {
		this.tbAdmOrigin = tbAdmOrigin;
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
			this.rIss = (rIss / 100);
		}else{
			this.rIss = rIss;
		}
	}

	public Float getrIssSoft() {
		if(rIssSoft != null){
			return rIssSoft * 100;
		}else{
			return rIssSoft;
		}
	}

	public void setrIssSoft(Float rIssSoft) {
		if(rIssSoft != null){
			this.rIssSoft = (rIssSoft / 100);
		}else{
			this.rIssSoft = rIssSoft;
		}
	}

	

}