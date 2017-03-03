package br.com.operations.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBADMMARKETSEGMENT database table.
 * 
 */
@Entity
@NamedQuery(name="TbAdmMarketSegment.findAll", query="SELECT t FROM TbAdmMarketSegment t")
public class TbAdmMarketSegment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBADMMARKETSEGMENT_ID_GENERATOR", sequenceName="SEQ_TBADMMARKETSEGMENT", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBADMMARKETSEGMENT_ID_GENERATOR")
	private long id;

	private Boolean fCofins;

	private Boolean fIcms;

	private Boolean fIpi;

	private Boolean fIss;
	
	private Boolean fPis;

	private String sDescription;

	private String sPercentIsencao;

	@ManyToOne
	@JoinColumn(name="IDADMCONTENTGROUP")
	private TbAdmGroupOfContent tbAdmGroupOfContent;	
	
	@Transient
	private String cofins;
	
	@Transient
	private String icms;
	
	@Transient
	private String iss;
	
	@Transient
	private String ipi;
	
	@Transient
	private String pis;
	
	public TbAdmMarketSegment() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boolean getfCofins() {
		return fCofins;
	}

	public void setfCofins(Boolean fCofins) {
		this.fCofins = fCofins;
	}

	public Boolean getfIcms() {
		return fIcms;
	}

	public void setfIcms(Boolean fIcms) {
		this.fIcms = fIcms;
	}

	public Boolean getfIpi() {
		return fIpi;
	}

	public void setfIpi(Boolean fIpi) {
		this.fIpi = fIpi;
	}

	public Boolean getfIss() {
		return fIss;
	}

	public void setfIss(Boolean fIss) {
		this.fIss = fIss;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public String getsPercentIsencao() {
		return sPercentIsencao;
	}

	public void setsPercentIsencao(String sPercentIsencao) {
		this.sPercentIsencao = sPercentIsencao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCofins() {
		
		if(fCofins != null && fCofins){
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
		
		if(fIcms != null && fIcms){
			icms = "Yes";
		}else{
			icms = "No";
		}
		
		return icms;
	}

	public void setIcms(String icms) {
		this.icms = icms;
	}

	public String getIss() {
		
		if(fIss != null && fIss){
			iss = "Yes";
		}else{
			iss = "No";
		}
		
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public String getIpi() {
		
		if(fIpi != null && fIpi){
			ipi = "Yes";
		}else{
			ipi = "No";
		}
		
		return ipi;
	}

	public void setIpi(String ipi) {
		this.ipi = ipi;
	}

	public String getPis() {
		
		if(fPis != null && fIpi){
			pis = "Yes";
		}else{
			pis = "No";
		}
		
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public Boolean getfPis() {
		return fPis;
	}

	public void setfPis(Boolean fPis) {
		this.fPis = fPis;
	}

	public TbAdmGroupOfContent getTbAdmGroupOfContent() {
		return tbAdmGroupOfContent;
	}

	public void setTbAdmGroupOfContent(TbAdmGroupOfContent tbAdmGroupOfContent) {
		this.tbAdmGroupOfContent = tbAdmGroupOfContent;
	}

	

}