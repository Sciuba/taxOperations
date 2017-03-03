package br.com.saboia.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBSYSWORKGROUP database table.
 * 
 */
@Entity
@NamedQuery(name="TbSysWorkGroup.findAll", query="SELECT t FROM TbSysWorkGroup t")
public class TbSysWorkGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSWORKGROUP_ID_GENERATOR", sequenceName="SEQ_TBADMMARKETSEGMENT", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSWORKGROUP_ID_GENERATOR")
	private long id;

	private String sComments;

	private String sName;
	
	private String sAcronym;
	
	//bi-directional many-to-one association to TbSysOrganization
	@ManyToOne
	@JoinColumn(name="IDASYSORGANIZATION")
	private TbSysOrganization tbSysOrganization;

	//bi-directional many-to-one association to Tbsysworkgroupuser
	@OneToMany(mappedBy="tbSysWorkGroup")
	private List<TbSysWorkGroupUser> tbSysWorkGroupUsers;
	
	public TbSysWorkGroup() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getsComments() {
		return sComments;
	}

	public void setsComments(String sComments) {
		this.sComments = sComments;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public TbSysOrganization getTbSysOrganization() {
		return tbSysOrganization;
	}

	public void setTbSysOrganization(TbSysOrganization tbSysOrganization) {
		this.tbSysOrganization = tbSysOrganization;
	}

	public List<TbSysWorkGroupUser> getTbSysWorkGroupUsers() {
		return tbSysWorkGroupUsers;
	}

	public void setTbSysWorkGroupUsers(List<TbSysWorkGroupUser> tbSysWorkGroupUsers) {
		this.tbSysWorkGroupUsers = tbSysWorkGroupUsers;
	}

	public TbSysWorkGroupUser addTbSysWorkGroupUser(TbSysWorkGroupUser tbSysWorkGroupUser) {
		getTbSysWorkGroupUsers().add(tbSysWorkGroupUser);
		tbSysWorkGroupUser.setTbSysWorkGroup(this);

		return tbSysWorkGroupUser;
	}

	public TbSysWorkGroupUser removeTbSysWorkGroupUser(TbSysWorkGroupUser tbSysWorkGroupUser) {
		getTbSysWorkGroupUsers().remove(tbSysWorkGroupUser);
		tbSysWorkGroupUser.setTbSysWorkGroup(null);

		return tbSysWorkGroupUser;
	}

	public String getsAcronym() {
		return sAcronym;
	}

	public void setsAcronym(String sAcronym) {
		this.sAcronym = sAcronym;
	}
	
	
}