package br.com.operations.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBSYSGROUPS database table.
 * 
 */
@Entity
@Table(name="TBSYSGROUPS")
@NamedQuery(name="TbSysGroup.findAll", query="SELECT t FROM TbSysGroup t")
public class TbSysGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSGROUPS_ID_GENERATOR", sequenceName="SEQ_TBSYSGROUPS", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSGROUPS_ID_GENERATOR")
	private long id;

	private String sdescription;

	private String sname;

	//bi-directional many-to-one association to TbSysGroupUser
	@OneToMany(mappedBy="tbsysgroup")
	private List<TbSysGroupUser> tbsysgroupusers;

	//bi-directional many-to-one association to TbSysPermission
//	@OneToMany(mappedBy="tbsysgroup")
//	private List<TbSysPermission> tbsyspermissions;

	public TbSysGroup() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSdescription() {
		return this.sdescription;
	}

	public void setSdescription(String sdescription) {
		this.sdescription = sdescription;
	}

	public String getSname() {
		return this.sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public List<TbSysGroupUser> getTbsysgroupusers() {
		return this.tbsysgroupusers;
	}

	public void setTbsysgroupusers(List<TbSysGroupUser> tbsysgroupusers) {
		this.tbsysgroupusers = tbsysgroupusers;
	}

	public TbSysGroupUser addTbsysgroupuser(TbSysGroupUser tbsysgroupuser) {
		getTbsysgroupusers().add(tbsysgroupuser);
		tbsysgroupuser.setTbsysgroup(this);

		return tbsysgroupuser;
	}

	public TbSysGroupUser removeTbsysgroupuser(TbSysGroupUser tbsysgroupuser) {
		getTbsysgroupusers().remove(tbsysgroupuser);
		tbsysgroupuser.setTbsysgroup(null);

		return tbsysgroupuser;
	}

//	public List<TbSysPermission> getTbsyspermissions() {
//		return this.tbsyspermissions;
//	}
//
//	public void setTbsyspermissions(List<TbSysPermission> tbsyspermissions) {
//		this.tbsyspermissions = tbsyspermissions;
//	}
//
//	public TbSysPermission addTbsyspermission(TbSysPermission tbsyspermission) {
//		getTbsyspermissions().add(tbsyspermission);
//		tbsyspermission.setTbsysgroup(this);
//
//		return tbsyspermission;
//	}
//
//	public TbSysPermission removeTbsyspermission(TbSysPermission tbsyspermission) {
//		getTbsyspermissions().remove(tbsyspermission);
//		tbsyspermission.setTbsysgroup(null);
//
//		return tbsyspermission;
//	}

}