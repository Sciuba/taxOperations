package br.com.operations.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the TBADMMATERIALCLASSXORIGIN database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmMaterialClassXOrigin.findAll", query="SELECT t FROM TbAdmMaterialClassXOrigin t")
public class TbAdmMaterialClassXOrigin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMMATERIALCLASSXORIGIN_ID_GENERATOR", sequenceName="SEQ_TBADMMATERIALCLASSXORIGIN", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMMATERIALCLASSXORIGIN_ID_GENERATOR")
	private long id;

	@ManyToOne
	@JoinColumn(name="IDADMMATERIALCLASS")
	private TbAdmMaterialClass tbAdmMaterialClass;
	
	@ManyToOne
	@JoinColumn(name="IDADMORIGIN")
	private TbAdmOrigin tbAdmOrigin;

	private Float rIcmsInterEstadual;

	private Float rIcmsInterno;

	private Float rIva;

	private Float rIvaMaterialImportado;

	public TbAdmMaterialClassXOrigin() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Float getrIcmsInterEstadual() {
		if(rIcmsInterEstadual != null){
			return rIcmsInterEstadual * 100;
		}else{
			return rIcmsInterEstadual;
		}
	}

	public void setrIcmsInterEstadual(Float rIcmsInterEstadual) {
		if(rIcmsInterEstadual != null){
			this.rIcmsInterEstadual = (rIcmsInterEstadual / 100);
		}else{
			this.rIcmsInterEstadual = rIcmsInterEstadual;
		}
	}

	public Float getrIcmsInterno() {
		if(rIcmsInterno != null){
			return rIcmsInterno * 100;
		}else{
			return rIcmsInterno;
		}
	}

	public void setrIcmsInterno(Float rIcmsInterno) {
		if(rIcmsInterno != null){
			this.rIcmsInterno = (rIcmsInterno / 100);
		}else{
			this.rIcmsInterno = rIcmsInterno;
		}
	}

	public Float getrIva() {
		if(rIva != null){
			return rIva * 100;
		}else{
			return rIva;
		}
	}

	public void setrIva(Float rIva) {
		if(rIva != null){
			this.rIva = (rIva / 100);
		}else{
			this.rIva = rIva;
		}
	}

	public Float getrIvaMaterialImportado() {
		if(rIvaMaterialImportado != null){
			return rIvaMaterialImportado * 100;
		}else{
			return rIvaMaterialImportado;
		}
	}

	public void setrIvaMaterialImportado(Float rIvaMaterialImportado) {
		if(rIvaMaterialImportado != null){
			this.rIvaMaterialImportado = (rIvaMaterialImportado / 100);
		}else{
			this.rIvaMaterialImportado = rIvaMaterialImportado;
		}
	}

	public TbAdmMaterialClass getTbAdmMaterialClass() {
		return tbAdmMaterialClass;
	}

	public void setTbAdmMaterialClass(TbAdmMaterialClass tbAdmMaterialClass) {
		this.tbAdmMaterialClass = tbAdmMaterialClass;
	}

	public TbAdmOrigin getTbAdmOrigin() {
		return tbAdmOrigin;
	}

	public void setTbAdmOrigin(TbAdmOrigin tbAdmOrigin) {
		this.tbAdmOrigin = tbAdmOrigin;
	}

	

}