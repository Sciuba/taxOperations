package br.com.operations.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBSYSORGANIZATION database table.
 * 
 */
@Entity
@NamedQuery(name="TbSysOrganization.findAll", query="SELECT t FROM TbSysOrganization t")
public class TbSysOrganization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSORGANIZATION_ID_GENERATOR", sequenceName="SEQ_SYSORGANIZATION", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSORGANIZATION_ID_GENERATOR")
	private long id;

	private boolean fMain;

	private String sComment;

	private String sDescription;
	
	private String sName;
	
	private String sGroupEveryOneName;
	
	//bi-directional many-to-one association to TbSysOrganizationRole
	@ManyToOne
	@JoinColumn(name="IDSYSORGANIZATIONROLE")
	private TbSysOrganizationRole tbSysOrganizationRole;

	//bi-directional many-to-one association to Tbsysuser
	@OneToMany(mappedBy="tbSysOrganization")
	private List<TbSysUser> tbSysUsers;

	//bi-directional many-to-one association to Tbsysworkgroup
	@OneToMany(mappedBy="tbSysOrganization")
	private List<TbSysWorkGroup> tbSysWorkGroups;

	@Transient
	private String main;
	
	@Transient
	private int users;
	
	@Transient
	private int usersActive;
	
	public TbSysOrganization() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isfMain() {
		return fMain;
	}

	public void setfMain(boolean fMain) {
		this.fMain = fMain;
	}

	public String getsComment() {
		return sComment;
	}

	public void setsComment(String sComment) {
		this.sComment = sComment;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public TbSysOrganizationRole getTbSysOrganizationRole() {
		return tbSysOrganizationRole;
	}

	public void setTbSysOrganizationRole(TbSysOrganizationRole tbSysOrganizationRole) {
		this.tbSysOrganizationRole = tbSysOrganizationRole;
	}

	public List<TbSysUser> getTbSysUsers() {
		return tbSysUsers;
	}

	public void setTbSysUsers(List<TbSysUser> tbSysUsers) {
		this.tbSysUsers = tbSysUsers;
	}

	public List<TbSysWorkGroup> getTbSysWorkGroups() {
		return tbSysWorkGroups;
	}

	public void setTbSysWorkGroups(List<TbSysWorkGroup> tbSysWorkGroups) {
		this.tbSysWorkGroups = tbSysWorkGroups;
	}

	public TbSysUser addTbSysUser(TbSysUser tbSysUser) {
		getTbSysUsers().add(tbSysUser);
		tbSysUser.setTbSysOrganization(this);

		return tbSysUser;
	}

	public TbSysUser removeTbSysUser(TbSysUser tbSysUser) {
		getTbSysUsers().remove(tbSysUser);
		tbSysUser.setTbSysOrganization(null);

		return tbSysUser;
	}

	public TbSysWorkGroup addTbSysWorkGroup(TbSysWorkGroup tbSysWorkGroup) {
		getTbSysWorkGroups().add(tbSysWorkGroup);
		tbSysWorkGroup.setTbSysOrganization(this);

		return tbSysWorkGroup;
	}

	public TbSysWorkGroup removeTbSysWorkGroup(TbSysWorkGroup tbSysWorkGroup) {
		getTbSysWorkGroups().remove(tbSysWorkGroup);
		tbSysWorkGroup.setTbSysOrganization(null);

		return tbSysWorkGroup;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getMain() {
		
		if(fMain){
			main = "Yes";
		}else{
			main = "No";
		}
		
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public int getUsers() {
		
		return users;
	}

	public void setUsers(int users) {
		this.users = users;
	}

	public int getUsersActive() {
		
		return usersActive;
	}

	public void setUsersActive(int usersActive) {
		this.usersActive = usersActive;
	}

	public String getsGroupEveryOneName() {
		return sGroupEveryOneName;
	}

	public void setsGroupEveryOneName(String sGroupEveryOneName) {
		this.sGroupEveryOneName = sGroupEveryOneName;
	}

	
}