package br.com.saboia.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBSYSACCESSPROFILE database table.
 * 
 */
@Entity
@NamedQuery(name="TbSysAccessProfile.findAll", query="SELECT t FROM TbSysAccessProfile t")
public class TbSysAccessProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSACCESSPROFILE_ID_GENERATOR", sequenceName="SEQ_SYSACCESSPROFILE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSACCESSPROFILE_ID_GENERATOR")
	private long id;

	private String sDescription;

	private String sDisplayName;

	private boolean fDefault;
	
	//bi-directional many-to-one association to TbSysAccessProfilePermission
	@OneToMany(mappedBy="tbSysAccessProfile")
	private List<TbSysAccessProfilePermission> tbSysAccessProfilePermissions;

	//bi-directional many-to-one association to TbSysOrganizationRole
	@ManyToOne
	@JoinColumn(name="IDSYSORGANIZATIONROLE")
	private TbSysOrganizationRole tbSysOrganizationRole;

	public TbSysAccessProfile() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public String getsDisplayName() {
		return sDisplayName;
	}

	public void setsDisplayName(String sDisplayName) {
		this.sDisplayName = sDisplayName;
	}

	public List<TbSysAccessProfilePermission> getTbSysAccessProfilePermissions() {
		return tbSysAccessProfilePermissions;
	}

	public void setTbSysAccessProfilePermissions(
			List<TbSysAccessProfilePermission> tbSysAccessProfilePermissions) {
		this.tbSysAccessProfilePermissions = tbSysAccessProfilePermissions;
	}

	public TbSysOrganizationRole getTbSysOrganizationRole() {
		return tbSysOrganizationRole;
	}

	public void setTbSysOrganizationRole(TbSysOrganizationRole tbSysOrganizationRole) {
		this.tbSysOrganizationRole = tbSysOrganizationRole;
	}

	public TbSysAccessProfilePermission addTbSysAccessProfilePermission(TbSysAccessProfilePermission tbSysAccessProfilePermission) {
		getTbSysAccessProfilePermissions().add(tbSysAccessProfilePermission);
		tbSysAccessProfilePermission.setTbSysAccessProfile(this);

		return tbSysAccessProfilePermission;
	}

	public TbSysAccessProfilePermission removeTbSysAccessProfilePermission(TbSysAccessProfilePermission tbSysAccessProfilePermission) {
		getTbSysAccessProfilePermissions().remove(tbSysAccessProfilePermission);
		tbSysAccessProfilePermission.setTbSysAccessProfile(null);

		return tbSysAccessProfilePermission;
	}

	public boolean isfDefault() {
		return fDefault;
	}

	public void setfDefault(boolean fDefault) {
		this.fDefault = fDefault;
	}

	
	

}