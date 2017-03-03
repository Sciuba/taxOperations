package br.com.operations.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBSYSORGANIZATIONROLE database table.
 * 
 */
@Entity
@NamedQuery(name="TbSysOrganizationRole.findAll", query="SELECT t FROM TbSysOrganizationRole t")
public class TbSysOrganizationRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSORGANIZATIONROLE_ID_GENERATOR", sequenceName="SEQ_SYSORGANIZATIONROLE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSORGANIZATIONROLE_ID_GENERATOR")
	private long id;

	private String sDisplayName;

	private String sKey;

	private String sDescription;
	
	//bi-directional many-to-one association to TbSysAccessProfile
	@OneToMany(mappedBy="tbSysOrganizationRole")
	private List<TbSysAccessProfile> tbSysAccessProfiles;

	//bi-directional many-to-one association to TbSysOrganization
	@OneToMany(mappedBy="tbSysOrganizationRole")
	private List<TbSysOrganization> tbSysOrganizations;

	public TbSysOrganizationRole() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getsDisplayName() {
		return sDisplayName;
	}

	public void setsDisplayName(String sDisplayName) {
		this.sDisplayName = sDisplayName;
	}

	public String getsKey() {
		return sKey;
	}

	public void setsKey(String sKey) {
		this.sKey = sKey;
	}

	public List<TbSysAccessProfile> getTbSysAccessProfiles() {
		return tbSysAccessProfiles;
	}

	public void setTbSysAccessProfiles(List<TbSysAccessProfile> tbSysAccessProfiles) {
		this.tbSysAccessProfiles = tbSysAccessProfiles;
	}

	public List<TbSysOrganization> getTbSysOrganizations() {
		return tbSysOrganizations;
	}

	public void setTbSysOrganizations(List<TbSysOrganization> tbSysOrganizations) {
		this.tbSysOrganizations = tbSysOrganizations;
	}

	public TbSysAccessProfile addTbSysAccessProfile(TbSysAccessProfile tbSysAccessProfile) {
		getTbSysAccessProfiles().add(tbSysAccessProfile);
		tbSysAccessProfile.setTbSysOrganizationRole(this);

		return tbSysAccessProfile;
	}

	public TbSysAccessProfile removeTbSysAccessProfile(TbSysAccessProfile tbSysAccessProfile) {
		getTbSysAccessProfiles().remove(tbSysAccessProfile);
		tbSysAccessProfile.setTbSysOrganizationRole(null);

		return tbSysAccessProfile;
	}
	
	public TbSysOrganization addTbtbSysOrganization(TbSysOrganization tbSysOrganization) {
		getTbSysOrganizations().add(tbSysOrganization);
		tbSysOrganization.setTbSysOrganizationRole(this);  

		return tbSysOrganization;
	}

	public TbSysOrganization removeTbSysOrganization(TbSysOrganization tbSysOrganization) {
		getTbSysOrganizations().remove(tbSysOrganization);
		tbSysOrganization.setTbSysOrganizationRole(null);

		return tbSysOrganization;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	
}