package br.com.operations.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBSYSHELP database table.
 * 
 */
@Entity
@NamedQuery(name="TbSysHelp.findAll", query="SELECT t FROM TbSysHelp t")
public class TbSysHelp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSHELP_ID_GENERATOR", sequenceName="SEQ_TBSYSHELP", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSHELP_ID_GENERATOR")
	private long id;

	private String scontents;

	private String sdescription;

	private String shelptopic;

	private String skeywords;

	public TbSysHelp() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getScontents() {
		return this.scontents;
	}

	public void setScontents(String scontents) {
		this.scontents = scontents;
	}

	public String getSdescription() {
		return this.sdescription;
	}

	public void setSdescription(String sdescription) {
		this.sdescription = sdescription;
	}

	public String getShelptopic() {
		return this.shelptopic;
	}

	public void setShelptopic(String shelptopic) {
		this.shelptopic = shelptopic;
	}

	public String getSkeywords() {
		return this.skeywords;
	}

	public void setSkeywords(String skeywords) {
		this.skeywords = skeywords;
	}

}