package br.com.operations.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the TBADMMANUFACTURESTXTBADMORIGIN database table.
 * 
 */
@Entity
@Table(name="TBADMMANUFACTURESTXTBADMORIGIN")
@NamedQuery(name="TbAdmManufacturerStateXTbAdmOrigin.findAll", query="SELECT t FROM TbAdmManufacturerStateXTbAdmOrigin t")
public class TbAdmManufacturerStateXTbAdmOrigin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMMANUFACTURESTXTBADMORIGIN_ID_GENERATOR", sequenceName="SEQ_TBADMMANUFACSTXORIGIN", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMMANUFACTURESTXTBADMORIGIN_ID_GENERATOR")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="IDADMMANUFACTURERSTATE")
	private TbAdmManufacturerStateOfOrigin tbAdmManufacturerStateOfOrigin;
	
	@ManyToOne
	@JoinColumn(name="IDADMORIGIN")
	private TbAdmOrigin tbAdmOrigin;
	

	public TbAdmManufacturerStateXTbAdmOrigin() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TbAdmManufacturerStateOfOrigin getTbAdmManufacturerStateOfOrigin() {
		return tbAdmManufacturerStateOfOrigin;
	}

	public void setTbAdmManufacturerStateOfOrigin(
			TbAdmManufacturerStateOfOrigin tbAdmManufacturerStateOfOrigin) {
		this.tbAdmManufacturerStateOfOrigin = tbAdmManufacturerStateOfOrigin;
	}

	public TbAdmOrigin getTbAdmOrigin() {
		return tbAdmOrigin;
	}

	public void setTbAdmOrigin(TbAdmOrigin tbAdmOrigin) {
		this.tbAdmOrigin = tbAdmOrigin;
	}

	

}