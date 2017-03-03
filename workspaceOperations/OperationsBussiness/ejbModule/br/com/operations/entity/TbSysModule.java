package br.com.operations.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


/**
 * The persistent class for the TBSYSMODULE database table.
 * 
 */
@Entity
@NamedQuery(name="TbSysModule.findAll", query="SELECT t FROM TbSysModule t")
public class TbSysModule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSMODULE_ID_GENERATOR", sequenceName="SEQ_SYSMODULE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSMODULE_ID_GENERATOR")
	private long id;

	private String sDescription;

	private String sDisplayName;

	private String sModule;

	@OneToMany(mappedBy="tbSysModule")
	private List<TbSysSystemPermission> tbSysSystemPermissions;
	
	public TbSysModule() {
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

	public String getsModule() {
		return sModule;
	}

	public void setsModule(String sModule) {
		this.sModule = sModule;
	}

	public List<TbSysSystemPermission> getTbSysSystemPermissions() {
		return tbSysSystemPermissions;
	}

	public void setTbSysSystemPermissions(
			List<TbSysSystemPermission> tbSysSystemPermissions) {
		this.tbSysSystemPermissions = tbSysSystemPermissions;
	}

	

}