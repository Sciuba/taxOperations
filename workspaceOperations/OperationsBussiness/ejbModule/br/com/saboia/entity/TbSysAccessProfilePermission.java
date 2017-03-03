package br.com.saboia.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBSYSACCESPROFILEPERMISSION database table.
 * 
 */
@Entity
@Table(name="TBSYSACCESPROFILEPERMISSION")
@NamedQuery(name="TbSysAccessProfilePermission.findAll", query="SELECT t FROM TbSysAccessProfilePermission t")
public class TbSysAccessProfilePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSACCESPROFILEPERMISSION_ID_GENERATOR", sequenceName="SEQ_SYSACCESSPROFILEPERMISSION", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSACCESPROFILEPERMISSION_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to TbSysAccessProfile
	@ManyToOne
	@JoinColumn(name="IDSYSACCESSPROFILE")
	private TbSysAccessProfile tbSysAccessProfile;

	//bi-directional many-to-one association to Tbsyssystempermission
	@ManyToOne
	@JoinColumn(name="IDSYSSYSTEMPERMISSION")
	private TbSysSystemPermission tbSysSystemPermission;

	public TbSysAccessProfilePermission() {
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

	public TbSysSystemPermission getTbSysSystemPermission() {
		return tbSysSystemPermission;
	}

	public void setTbSysSystemPermission(TbSysSystemPermission tbSysSystemPermission) {
		this.tbSysSystemPermission = tbSysSystemPermission;
	}

	

}