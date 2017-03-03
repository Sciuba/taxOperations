package br.com.saboia.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the TBADMCLIENTEESPECIAL database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmClienteEspecial.findAll", query="SELECT t FROM TbAdmClienteEspecial t")
public class TbAdmClienteEspecial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMCLIENTEESPECIAL_ID_GENERATOR", sequenceName="SEQ_TBADMCLIENTEESPECIAL", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMCLIENTEESPECIAL_ID_GENERATOR")
	private long id;

	private String sRazaoSocial;
	
	private String sCNPJ;
	
	private boolean fCofins;

	private boolean fIcms;

	private boolean fIpi;

	private boolean fIss;
	
	private boolean fPis;

	@Transient
	private String cofins;
	
	@Transient
	private String icms;
	
	@Transient
	private String ipi;
	
	@Transient
	private String iss;
	
	@Transient
	private String pis;
	
	public TbAdmClienteEspecial() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSRazaoSocial() {
		return this.sRazaoSocial;
	}

	public void setSRazaoSocial(String sRazaoSocial) {
		this.sRazaoSocial = sRazaoSocial;
	}

	public String getsRazaoSocial() {
		return sRazaoSocial;
	}

	public void setsRazaoSocial(String sRazaoSocial) {
		this.sRazaoSocial = sRazaoSocial;
	}

	public String getsCNPJ() {
		return sCNPJ;
	}

	public void setsCNPJ(String sCNPJ) {
		this.sCNPJ = sCNPJ;
	}

	public boolean isfCofins() {
		return fCofins;
	}

	public void setfCofins(boolean fCofins) {
		this.fCofins = fCofins;
	}

	public boolean isfIcms() {
		return fIcms;
	}

	public void setfIcms(boolean fIcms) {
		this.fIcms = fIcms;
	}

	public boolean isfIpi() {
		return fIpi;
	}

	public void setfIpi(boolean fIpi) {
		this.fIpi = fIpi;
	}

	public boolean isfIss() {
		return fIss;
	}

	public void setfIss(boolean fIss) {
		this.fIss = fIss;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCofins() {
		
		if(fCofins){
			cofins = "Yes";
		}else{
			cofins = "No";
		}
		
		return cofins;
	}

	public void setCofins(String cofins) {
		this.cofins = cofins;
	}

	public String getIcms() {
		
		if(fIcms){
			icms = "Yes";
		}else{
			icms = "No";
		}
		
		return icms;
	}

	public void setIcms(String icms) {
		this.icms = icms;
	}

	public String getIpi() {
		
		if(fIpi){
			ipi = "Yes";
		}else{
			ipi = "No";
		}
		
		return ipi;
	}

	public void setIpi(String ipi) {
		this.ipi = ipi;
	}

	public String getIss() {
		
		if(fIss){
			iss = "Yes";
		}else{
			iss = "No";
		}
		
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public boolean isfPis() {
		return fPis;
	}

	public void setfPis(boolean fPis) {
		this.fPis = fPis;
	}

	public String getPis() {
		
		if(fPis){
			pis = "Yes";
		}else{
			pis = "No";
		}
		
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}
	
	
}