package br.com.operations.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;


/**
 * The persistent class for the TBSYSACCESSPROFILEUSER database table.
 * 
 */
@Entity
@NamedQuery(name="TbSysAccessProfileUser.findAll", query="SELECT t FROM TbSysAccessProfileUser t")
public class TbSysAccessProfileUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSACCESSPROFILEUSER_ID_GENERATOR", sequenceName="SEQ_SYSACCESSPROFILEUSER", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSACCESSPROFILEUSER_ID_GENERATOR")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="IDSYSACCESSPROFILE")
	private TbSysAccessProfile tbSysAccessProfile;

	@ManyToOne
	@JoinColumn(name="IDSYSUSER")
	private TbSysUser tbSysUser;

	public TbSysAccessProfileUser() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TbSysAccessProfile getTbSysAccessProfile() {
		return tbSysAccessProfile;
	}

	public void setTbSysAccessProfile(TbSysAccessProfile tbSysAccessProfile) {
		this.tbSysAccessProfile = tbSysAccessProfile;
	}

	public TbSysUser getTbSysUser() {
		return tbSysUser;
	}

	public void setTbSysUser(TbSysUser tbSysUser) {
		this.tbSysUser = tbSysUser;
	}

	
}