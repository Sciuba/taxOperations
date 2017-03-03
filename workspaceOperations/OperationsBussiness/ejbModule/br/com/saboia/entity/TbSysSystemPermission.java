package br.com.saboia.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBSYSSYSTEMPERMISSION database table.
 * 
 */
@Entity
@NamedQuery(name="TbSysSystemPermission.findAll", query="SELECT t FROM TbSysSystemPermission t")
public class TbSysSystemPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSSYSTEMPERMISSION_ID_GENERATOR", sequenceName="SEQ_TBADMMARKETSEGMENT", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSSYSTEMPERMISSION_ID_GENERATOR")
	private long id;

	private String sKey;

	private String sKeyDisplayName;

	private String sDescription;
	
	//bi-directional many-to-one association to TbSysAccessProfilePermission
	@OneToMany(mappedBy="tbSysSystemPermission")
	private List<TbSysAccessProfilePermission> tbSysAccessProfilePermissions;
	
	@ManyToOne
	@JoinColumn(name="IDSYSMODULE")
	private TbSysModule tbSysModule;
	
	@Transient
	private boolean checked;
	
	public TbSysSystemPermission() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getsKey() {
		return sKey;
	}

	public void setsKey(String sKey) {
		this.sKey = sKey;
	}

	public String getsKeyDisplayName() {
		return sKeyDisplayName;
	}

	public void setsKeyDisplayName(String sKeyDisplayName) {
		this.sKeyDisplayName = sKeyDisplayName;
	}

	public List<TbSysAccessProfilePermission> getTbSysAccessProfilePermissions() {
		return tbSysAccessProfilePermissions;
	}

	public void setTbSysAccessProfilePermissions(
			List<TbSysAccessProfilePermission> tbSysAccessProfilePermissions) {
		this.tbSysAccessProfilePermissions = tbSysAccessProfilePermissions;
	}

	public TbSysAccessProfilePermission addTbSysAccessProfilePermission(TbSysAccessProfilePermission tbSysAccessProfilePermission) {
		getTbSysAccessProfilePermissions().add(tbSysAccessProfilePermission);
		tbSysAccessProfilePermission.setTbSysSystemPermission(this);

		return tbSysAccessProfilePermission;
	}

	public TbSysAccessProfilePermission removeTbSysAccessProfilePermission(TbSysAccessProfilePermission tbSysAccessProfilePermission) {
		getTbSysAccessProfilePermissions().remove(tbSysAccessProfilePermission);
		tbSysAccessProfilePermission.setTbSysSystemPermission(null);

		return tbSysAccessProfilePermission;
	}

	public TbSysModule getTbSysModule() {
		return tbSysModule;
	}

	public void setTbSysModule(TbSysModule tbSysModule) {
		this.tbSysModule = tbSysModule;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}