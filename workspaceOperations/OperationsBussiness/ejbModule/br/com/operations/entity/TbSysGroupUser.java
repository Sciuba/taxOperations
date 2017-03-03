package br.com.operations.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBSYSGROUPUSERS database table.
 * 
 */
@Entity
@Table(name="TBSYSGROUPUSERS")
@NamedQuery(name="TbSysGroupUser.findAll", query="SELECT t FROM TbSysGroupUser t")
public class TbSysGroupUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSGROUPUSERS_ID_GENERATOR", sequenceName="SEQ_TBSYSGROUPUSERS", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSGROUPUSERS_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to TbSysGroup
	@ManyToOne
	@JoinColumn(name="IDSYSGROUPS")
	private TbSysGroup tbsysgroup;

	//bi-directional many-to-one association to TbSysUser
	@ManyToOne
	@JoinColumn(name="IDSYSUSERS")
	private TbSysUser tbsysuser;

	public TbSysGroupUser() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TbSysGroup getTbsysgroup() {
		return this.tbsysgroup;
	}

	public void setTbsysgroup(TbSysGroup tbsysgroup) {
		this.tbsysgroup = tbsysgroup;
	}

	public TbSysUser getTbsysuser() {
		return this.tbsysuser;
	}

	public void setTbsysuser(TbSysUser tbsysuser) {
		this.tbsysuser = tbsysuser;
	}

}