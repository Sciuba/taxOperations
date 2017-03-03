package br.com.operations.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the TBSYSLOGEMAILSEND database table.
 * 
 */
/**
 * @author Fernando
 *
 */
@Entity
@NamedQuery(name="TbSysLogEmailSend.findAll", query="SELECT t FROM TbSysLogEmailSend t")
public class TbSysLogEmailSend implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSLOGEMAILSEND_ID_GENERATOR", sequenceName="SEQ_TBSYSLOGEMAILSEND", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSLOGEMAILSEND_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtSend;

	@ManyToOne
	@JoinColumn(name="IDSYSUSERREQUESTOR")
	private TbSysUser tbSysUser;

	private String sBodyEmail;

	private String sEmailAccountCc;

	private String sEmailAccountSend;

	private String sSubject;

	public TbSysLogEmailSend() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDtSend() {
		return dtSend;
	}

	public void setDtSend(Date dtSend) {
		this.dtSend = dtSend;
	}

	public TbSysUser getTbSysUser() {
		return tbSysUser;
	}

	public void setTbSysUser(TbSysUser tbSysUser) {
		this.tbSysUser = tbSysUser;
	}

	public String getSBodyEmail() {
		return this.sBodyEmail;
	}

	public void setSBodyEmail(String sBodyEmail) {
		this.sBodyEmail = sBodyEmail;
	}

	public String getSEmailAccountCc() {
		return this.sEmailAccountCc;
	}

	public void setSEmailAccountCc(String sEmailAccountCc) {
		this.sEmailAccountCc = sEmailAccountCc;
	}

	public String getSEmailAccountSend() {
		return this.sEmailAccountSend;
	}

	public void setSEmailAccountSend(String sEmailAccountSend) {
		this.sEmailAccountSend = sEmailAccountSend;
	}

	public String getSSubject() {
		return this.sSubject;
	}

	public void setSSubject(String sSubject) {
		this.sSubject = sSubject;
	}

}