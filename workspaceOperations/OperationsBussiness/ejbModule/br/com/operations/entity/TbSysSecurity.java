package br.com.operations.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBSYSSECURITY database table.
 * 
 */
@Entity
@NamedQuery(name="TbSysSecurity.findAll", query="SELECT t FROM TbSysSecurity t")
public class TbSysSecurity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSSECURITY_ID_GENERATOR", sequenceName="SEQ_TBSYSSECURITY", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSSECURITY_ID_GENERATOR")
	private long id;

	private String sAdmPassword;

	public TbSysSecurity() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getsAdmPassword() {
		return sAdmPassword;
	}

	public void setsAdmPassword(String sAdmPassword) {
		this.sAdmPassword = sAdmPassword;
	}

	

}