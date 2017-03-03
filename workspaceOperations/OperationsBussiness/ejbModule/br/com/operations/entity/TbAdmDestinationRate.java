package br.com.operations.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TBADMDESTINATIONRATE database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmDestinationRate.findAll", query="SELECT t FROM TbAdmDestinationRate t")
public class TbAdmDestinationRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMDESTINATIONRATE_ID_GENERATOR", sequenceName="SEQ_TBADMDESTINATIONRATE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMDESTINATIONRATE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.DATE)
	private Date dtExpiration;

	private Double rIcms;

	//bi-directional many-to-one association to Tbadmdestination
	@ManyToOne
	@JoinColumn(name="IDADMDESTINATION")
	private TbAdmDestination tbAdmDestination;

	public TbAdmDestinationRate() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDtExpiration() {
		return this.dtExpiration;
	}

	public void setDtExpiration(Date dtExpiration) {
		this.dtExpiration = dtExpiration;
	}

	public Double getRIcms() {
		if(rIcms != null){
			return this.rIcms * 100;
		}else{
			return this.rIcms;
		}
	}

	public void setRIcms(Double rIcms) {
		if(rIcms != null){
			this.rIcms = (rIcms / 100);
		}else{
			this.rIcms = rIcms;
		}
	}

	public TbAdmDestination getTbAdmDestination() {
		return this.tbAdmDestination;
	}

	public void setTbAdmDestination(TbAdmDestination tbAdmDestination) {
		this.tbAdmDestination = tbAdmDestination;
	}

}