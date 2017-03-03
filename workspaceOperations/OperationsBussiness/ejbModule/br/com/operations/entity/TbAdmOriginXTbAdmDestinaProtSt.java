package br.com.operations.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBADMORIGINXTBADMDESTINAPROTST database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmOriginXTbAdmDestinaProtSt.findAll", query="SELECT t FROM TbAdmOriginXTbAdmDestinaProtSt t")
public class TbAdmOriginXTbAdmDestinaProtSt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMORIGINXTBADMDESTINAPROTST_ID_GENERATOR", sequenceName="SEQ_ADMORIGINXADMDESTINAPROTST", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMORIGINXTBADMDESTINAPROTST_ID_GENERATOR")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="IDADMORIGIN")
	private TbAdmOrigin tbAdmOrigin;

	//bi-directional many-to-one association to Tbadmdestination
	@ManyToOne
	@JoinColumn(name="IDADMDESTINATION")
	private TbAdmDestination tbAdmDestination;

	public TbAdmOriginXTbAdmDestinaProtSt() {
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

	public TbAdmDestination getTbAdmDestination() {
		return this.tbAdmDestination;
	}

	public void setTbAdmDestination(TbAdmDestination tbAdmDestination) {
		this.tbAdmDestination = tbAdmDestination;
	}

}