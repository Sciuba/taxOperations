package br.com.saboia.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBADMMATERIALCLASSXDESTINATION database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmMaterialClassXDestination.findAll", query="SELECT t FROM TbAdmMaterialClassXDestination t")
public class TbAdmMaterialClassXDestination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMMATERIALCLASSXDESTINATION_ID_GENERATOR", sequenceName="SEQ_TBADMMATERIALCLASSXDEST", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMMATERIALCLASSXDESTINATION_ID_GENERATOR")
	private long id;

	private boolean fSubstituicaoTributaria;

	@ManyToOne
	@JoinColumn(name="IDADMDESTINATION")
	private TbAdmDestination tbAdmDestination;

	@ManyToOne
	@JoinColumn(name="IDADMMATERIALCLASS")
	private TbAdmMaterialClass tbAdmMaterialClass;

	private Float rIcmsInterEstadual;

	private Float rIcmsInterno;

	private Float rIva;

	private Float rIvaMaterialImportado;

	public TbAdmMaterialClassXDestination() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isfSubstituicaoTributaria() {
		return fSubstituicaoTributaria;
	}

	public void setfSubstituicaoTributaria(boolean fSubstituicaoTributaria) {
		this.fSubstituicaoTributaria = fSubstituicaoTributaria;
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

	public TbAdmDestination getTbAdmDestination() {
		return tbAdmDestination;
	}

	public void setTbAdmDestination(TbAdmDestination tbAdmDestination) {
		this.tbAdmDestination = tbAdmDestination;
	}

	public TbAdmMaterialClass getTbAdmMaterialClass() {
		return tbAdmMaterialClass;
	}

	public void setTbAdmMaterialClass(TbAdmMaterialClass tbAdmMaterialClass) {
		this.tbAdmMaterialClass = tbAdmMaterialClass;
	}

	
}