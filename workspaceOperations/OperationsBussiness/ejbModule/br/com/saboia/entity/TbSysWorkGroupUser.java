package br.com.saboia.entity;

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
 * The persistent class for the TBSYSWORKGROUPUSER database table.
 * 
 */
@Entity
@NamedQuery(name="TbSysWorkGroupUser.findAll", query="SELECT t FROM TbSysWorkGroupUser t")
public class TbSysWorkGroupUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSWORKGROUPUSER_ID_GENERATOR", sequenceName="SEQ_TBADMMARKETSEGMENT", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSWORKGROUPUSER_ID_GENERATOR")
	private long id;

	private boolean fManager;

	//bi-directional many-to-one association to Tbsysuser
	@ManyToOne
	@JoinColumn(name="IDSYSUSER") 
	private TbSysUser tbSysUser;

	//bi-directional many-to-one association to Tbsysworkgroup
	@ManyToOne
	@JoinColumn(name="IDSYSWORKGROUP")
	private TbSysWorkGroup tbSysWorkGroup;

	public TbSysWorkGroupUser() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isfManager() {
		return fManager;
	}

	public void setfManager(boolean fManager) {
		this.fManager = fManager;
	}

	public TbSysUser getTbSysUser() {
		return tbSysUser;
	}

	public void setTbSysUser(TbSysUser tbSysUser) {
		this.tbSysUser = tbSysUser;
	}

	public TbSysWorkGroup getTbSysWorkGroup() {
		return tbSysWorkGroup;
	}

	public void setTbSysWorkGroup(TbSysWorkGroup tbSysWorkGroup) {
		this.tbSysWorkGroup = tbSysWorkGroup;
	}

	

}