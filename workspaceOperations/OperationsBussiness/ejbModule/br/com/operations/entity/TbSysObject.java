package br.com.operations.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBSYSOBJECTS database table.
 * 
 */
@Entity
@Table(name="TBSYSOBJECTS")
@NamedQuery(name="TbSysObject.findAll", query="SELECT t FROM TbSysObject t")
public class TbSysObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSOBJECTS_ID_GENERATOR", sequenceName="SEQ_TBSYSOBJECT", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSOBJECTS_ID_GENERATOR")
	private long id;

	private String scomments;

	private String sdescription;

	private String smenemonic;

//	//bi-directional many-to-one association to TbSysPermission
//	@OneToMany(mappedBy="tbsysobject")
//	private List<TbSysPermission> tbsyspermissions;

	public TbSysObject() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getScomments() {
		return this.scomments;
	}

	public void setScomments(String scomments) {
		this.scomments = scomments;
	}

	public String getSdescription() {
		return this.sdescription;
	}

	public void setSdescription(String sdescription) {
		this.sdescription = sdescription;
	}

	public String getSmenemonic() {
		return this.smenemonic;
	}

	public void setSmenemonic(String smenemonic) {
		this.smenemonic = smenemonic;
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
//		tbsyspermission.setTbsysobject(this);
//
//		return tbsyspermission;
//	}
//
//	public TbSysPermission removeTbsyspermission(TbSysPermission tbsyspermission) {
//		getTbsyspermissions().remove(tbsyspermission);
//		tbsyspermission.setTbsysobject(null);
//
//		return tbsyspermission;
//	}

}