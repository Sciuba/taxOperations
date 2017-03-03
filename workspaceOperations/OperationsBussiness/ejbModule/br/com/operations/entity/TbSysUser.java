package br.com.operations.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the TBSYSUSERS database table.
 * 
 */
@Entity
@Table(name="TBSYSUSERS")
@NamedQuery(name="TbSysUser.findAll", query="SELECT t FROM TbSysUser t")
public class TbSysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBSYSUSERS_ID_GENERATOR", sequenceName="SEQ_TBSYSUSERS", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBSYSUSERS_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.DATE)
	private Date dtExpiration;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtLastAccess;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtPasswordUpdate;

	@Temporal(TemporalType.DATE)
	private Date dtRegister;

	private boolean fActive;

	private boolean fBlocked;

	private boolean fNeverExpires;

	private boolean fSystemAdmin;

	private boolean fSystemDeveloper;

	private boolean iAttempts;

	private boolean iConections;

	private boolean idAdmGroupDefault;

	private boolean iLoginAttempts;

	private int referredUniqueUserId;

	private String sAltPhoneNumber;

	private String sAvatarPath;

	private String sDepartment;

	private String sEmail;

	private String sFullName;

	private String sLanguageCode;

	private String sLogon;

	private String sPassword;

	private String sPhoneNumber;
	
	private float fOrgManager;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtForgotPassword;
	
	private String sCodePasswordSend;
	
	@OneToMany(mappedBy="tbSysUserApprovalLevel2")
	private List<TbTaxQuote> tbTaxQuotesApprovalLevel2;	
	
	@OneToMany(mappedBy="tbSysUserApprovalLevel3")
	private List<TbTaxQuote> tbTaxQuotesApprovalLevel3;
	
	@OneToMany(mappedBy="tbSysUserApprovalLevel4")
	private List<TbTaxQuote> tbTaxQuotesApprovalLevel4;
	
	@OneToMany(mappedBy="tbSysUserLastUpdate")
	private List<TbTaxQuote> tbTaxQuotesLastUpdate;
	
	@OneToMany(mappedBy="tbSysUserOwner")
	private List<TbTaxQuote> tbTaxQuotesUserOwner;
	
	@OneToMany(mappedBy="tbSysUserResquestApprovalLevel2")
	private List<TbTaxQuote> tbTaxQuotesResquestApprovalLevel2;
	
	@OneToMany(mappedBy="tbSysUserResquestApprovalLevel3")
	private List<TbTaxQuote> tbTaxQuotesResquestApprovalLevel3;
	
	@OneToMany(mappedBy="tbSysUserResquestApprovalLevel4")
	private List<TbTaxQuote> tbTaxQuotesResquestApprovalLevel4;
	
	@OneToMany(mappedBy="tbSysUser")
	private List<TbTaxQuote> tbTaxQuotes;
	
	@OneToMany(mappedBy="tbSysUser")
	private List<TbTaxQuoteParticipant> tbTaxQuoteParticipants;
	
	@OneToMany(mappedBy="tbSysUser")
	private List<TbSysLogEmailSend> tbSysLogEmailSends;
	
	//bi-directional many-to-one association to TbSysOrganization
	@ManyToOne
	@JoinColumn(name="IDSYSORGANIZATION")
	private TbSysOrganization tbSysOrganization;
	
//	@ManyToOne
//	@JoinColumn(name="IDADMPERSON")
//	private TbAdmPerson tbAdmPerson;

	//bi-directional many-to-one association to Tbsysworkgroupuser
	@OneToMany(mappedBy="tbSysUser")
	private List<TbSysWorkGroupUser> tbSysWorkGroupUsers;
	
	@OneToMany(mappedBy="tbSysUser")
	private List<TbSysAccessProfileUser> tbSysAccessProfileUsers;
	
	@Transient
	private String listaGrupo;
	
	@Transient
	private String listaGrupoSigla;
	
	@Transient
	private String listaPerfil;
	
	@Transient
	private boolean ckecked;
	
	@Transient
	private boolean fManager;
	
	@Transient
	private boolean fManagerGroupSales;
	
	public TbSysUser() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDtExpiration() {
		return dtExpiration;
	}

	public void setDtExpiration(Date dtExpiration) {
		this.dtExpiration = dtExpiration;
	}

	public Date getDtLastAccess() {
		return dtLastAccess;
	}

	public void setDtLastAccess(Date dtLastAccess) {
		this.dtLastAccess = dtLastAccess;
	}

	public Date getDtPasswordUpdate() {
		return dtPasswordUpdate;
	}

	public void setDtPasswordUpdate(Date dtPasswordUpdate) {
		this.dtPasswordUpdate = dtPasswordUpdate;
	}

	public Date getDtRegister() {
		return dtRegister;
	}

	public void setDtRegister(Date dtRegister) {
		this.dtRegister = dtRegister;
	}

	public boolean isfActive() {
		return fActive;
	}

	public void setfActive(boolean fActive) {
		this.fActive = fActive;
	}

	public boolean isfBlocked() {
		return fBlocked;
	}

	public void setfBlocked(boolean fBlocked) {
		this.fBlocked = fBlocked;
	}

	public boolean isfNeverExpires() {
		return fNeverExpires;
	}

	public void setfNeverExpires(boolean fNeverExpires) {
		this.fNeverExpires = fNeverExpires;
	}

	public boolean isfSystemAdmin() {
		return fSystemAdmin;
	}

	public void setfSystemAdmin(boolean fSystemAdmin) {
		this.fSystemAdmin = fSystemAdmin;
	}

	public boolean isfSystemDeveloper() {
		return fSystemDeveloper;
	}

	public void setfSystemDeveloper(boolean fSystemDeveloper) {
		this.fSystemDeveloper = fSystemDeveloper;
	}

	public boolean isiAttempts() {
		return iAttempts;
	}

	public void setiAttempts(boolean iAttempts) {
		this.iAttempts = iAttempts;
	}

	public boolean isiConections() {
		return iConections;
	}

	public void setiConections(boolean iConections) {
		this.iConections = iConections;
	}

	public boolean isIdAdmGroupDefault() {
		return idAdmGroupDefault;
	}

	public void setIdAdmGroupDefault(boolean idAdmGroupDefault) {
		this.idAdmGroupDefault = idAdmGroupDefault;
	}

	public boolean isiLoginAttempts() {
		return iLoginAttempts;
	}

	public void setiLoginAttempts(boolean iLoginAttempts) {
		this.iLoginAttempts = iLoginAttempts;
	}

	public long getReferredUniqueUserId() {
		return referredUniqueUserId;
	}

	public void setReferredUniqueUserId(int referredUniqueUserId) {
		this.referredUniqueUserId = referredUniqueUserId;
	}

	public String getsAltPhoneNumber() {
		return sAltPhoneNumber;
	}

	public void setsAltPhoneNumber(String sAltPhoneNumber) {
		this.sAltPhoneNumber = sAltPhoneNumber;
	}

	public String getsAvatarPath() {
		return sAvatarPath;
	}

	public void setsAvatarPath(String sAvatarPath) {
		this.sAvatarPath = sAvatarPath;
	}

	public String getsDepartment() {
		return sDepartment;
	}

	public void setsDepartment(String sDepartment) {
		this.sDepartment = sDepartment;
	}

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public String getsFullName() {
		return sFullName;
	}

	public void setsFullName(String sFullName) {
		this.sFullName = sFullName;
	}

	public String getsLanguageCode() {
		return sLanguageCode;
	}

	public void setsLanguageCode(String sLanguageCode) {
		this.sLanguageCode = sLanguageCode;
	}

	public String getsLogon() {
		return sLogon;
	}

	public void setsLogon(String sLogon) {
		this.sLogon = sLogon;
	}

	public String getsPassword() {
		return sPassword;
	}

	public void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}

	public String getsPhoneNumber() {
		return sPhoneNumber;
	}

	public void setsPhoneNumber(String sPhoneNumber) {
		this.sPhoneNumber = sPhoneNumber;
	}
//	
//	public TbAdmPerson getTbAdmPerson() {
//		return tbAdmPerson;
//	}
//
//	public void setTbAdmPerson(TbAdmPerson tbAdmPerson) {
//		this.tbAdmPerson = tbAdmPerson;
//	}

	public TbSysOrganization getTbSysOrganization() {
		return tbSysOrganization;
	}

	public void setTbSysOrganization(TbSysOrganization tbSysOrganization) {
		this.tbSysOrganization = tbSysOrganization;
	}

	public List<TbSysWorkGroupUser> getTbSysWorkGroupUsers() {
		return tbSysWorkGroupUsers;
	}

	public void setTbSysWorkGroupUsers(List<TbSysWorkGroupUser> tbSysWorkGroupUsers) {
		this.tbSysWorkGroupUsers = tbSysWorkGroupUsers;
	}

	public TbSysWorkGroupUser addTbSysWorkGroupUser(TbSysWorkGroupUser tbSysWorkGroupUser) {
		getTbSysWorkGroupUsers().add(tbSysWorkGroupUser);
		tbSysWorkGroupUser.setTbSysUser(this);

		return tbSysWorkGroupUser;
	}

	public TbSysWorkGroupUser removeTbSysWorkGroupUser(TbSysWorkGroupUser tbSysWorkGroupUser) {
		getTbSysWorkGroupUsers().remove(tbSysWorkGroupUser);
		tbSysWorkGroupUser.setTbSysUser(null);

		return tbSysWorkGroupUser;
	}

	public List<TbTaxQuote> getTbTaxQuotesApprovalLevel2() {
		return tbTaxQuotesApprovalLevel2;
	}

	public void setTbTaxQuotesApprovalLevel2(
			List<TbTaxQuote> tbTaxQuotesApprovalLevel2) {
		this.tbTaxQuotesApprovalLevel2 = tbTaxQuotesApprovalLevel2;
	}

	public List<TbTaxQuote> getTbTaxQuotesApprovalLevel3() {
		return tbTaxQuotesApprovalLevel3;
	}

	public void setTbTaxQuotesApprovalLevel3(
			List<TbTaxQuote> tbTaxQuotesApprovalLevel3) {
		this.tbTaxQuotesApprovalLevel3 = tbTaxQuotesApprovalLevel3;
	}

	public List<TbTaxQuote> getTbTaxQuotesApprovalLevel4() {
		return tbTaxQuotesApprovalLevel4;
	}

	public void setTbTaxQuotesApprovalLevel4(
			List<TbTaxQuote> tbTaxQuotesApprovalLevel4) {
		this.tbTaxQuotesApprovalLevel4 = tbTaxQuotesApprovalLevel4;
	}

	public List<TbTaxQuote> getTbTaxQuotesLastUpdate() {
		return tbTaxQuotesLastUpdate;
	}

	public void setTbTaxQuotesLastUpdate(List<TbTaxQuote> tbTaxQuotesLastUpdate) {
		this.tbTaxQuotesLastUpdate = tbTaxQuotesLastUpdate;
	}

	public List<TbTaxQuote> getTbTaxQuotesUserOwner() {
		return tbTaxQuotesUserOwner;
	}

	public void setTbTaxQuotesUserOwner(List<TbTaxQuote> tbTaxQuotesUserOwner) {
		this.tbTaxQuotesUserOwner = tbTaxQuotesUserOwner;
	}

	public List<TbTaxQuote> getTbTaxQuotesResquestApprovalLevel2() {
		return tbTaxQuotesResquestApprovalLevel2;
	}

	public void setTbTaxQuotesResquestApprovalLevel2(
			List<TbTaxQuote> tbTaxQuotesResquestApprovalLevel2) {
		this.tbTaxQuotesResquestApprovalLevel2 = tbTaxQuotesResquestApprovalLevel2;
	}

	public List<TbTaxQuote> getTbTaxQuotesResquestApprovalLevel3() {
		return tbTaxQuotesResquestApprovalLevel3;
	}

	public void setTbTaxQuotesResquestApprovalLevel3(
			List<TbTaxQuote> tbTaxQuotesResquestApprovalLevel3) {
		this.tbTaxQuotesResquestApprovalLevel3 = tbTaxQuotesResquestApprovalLevel3;
	}

	public List<TbTaxQuote> getTbTaxQuotesResquestApprovalLevel4() {
		return tbTaxQuotesResquestApprovalLevel4;
	}

	public void setTbTaxQuotesResquestApprovalLevel4(
			List<TbTaxQuote> tbTaxQuotesResquestApprovalLevel4) {
		this.tbTaxQuotesResquestApprovalLevel4 = tbTaxQuotesResquestApprovalLevel4;
	}

	public String getListaGrupo() {
		
		listaGrupo = "";
		
		if(tbSysWorkGroupUsers != null && tbSysWorkGroupUsers.size() > 0){
			for(TbSysWorkGroupUser u : tbSysWorkGroupUsers){
				if(listaGrupo.isEmpty()){
					listaGrupo = u.getTbSysWorkGroup().getsName();
				}else{
					listaGrupo += ", "+u.getTbSysWorkGroup().getsName();
				}
			}
		}
		
		return listaGrupo;
	}

	public void setListaGrupo(String listaGrupo) {
		this.listaGrupo = listaGrupo;
	}
	
	public String getListaGrupoSigla() {
		
		listaGrupoSigla = "";
		
		if(tbSysWorkGroupUsers != null && tbSysWorkGroupUsers.size() > 0){
			for(TbSysWorkGroupUser u : tbSysWorkGroupUsers){
				if(listaGrupoSigla.isEmpty()){
					listaGrupoSigla = u.getTbSysWorkGroup().getsAcronym();
				}else{
					listaGrupoSigla += ", "+u.getTbSysWorkGroup().getsAcronym();
				}
			}
		}
		
		return listaGrupoSigla;
	}

	public void setListaGrupoSigla(String listaGrupoSigla) {
		this.listaGrupoSigla = listaGrupoSigla;
	}

	public String getListaPerfil() {
		
		listaPerfil = "";
		
		if(tbSysAccessProfileUsers != null && tbSysAccessProfileUsers.size() > 0){
			for(TbSysAccessProfileUser u : tbSysAccessProfileUsers){
				if(listaPerfil.isEmpty()){
					listaPerfil = u.getTbSysAccessProfile().getsDisplayName();
				}else{
					listaPerfil += ", "+u.getTbSysAccessProfile().getsDisplayName();
				}
			}
		}
		
		return listaPerfil;
	}

	public void setListaPerfil(String listaPerfil) {
		this.listaPerfil = listaPerfil;
	}

	public List<TbSysAccessProfileUser> getTbSysAccessProfileUsers() {
		return tbSysAccessProfileUsers;
	}

	public void setTbSysAccessProfileUsers(
			List<TbSysAccessProfileUser> tbSysAccessProfileUsers) {
		this.tbSysAccessProfileUsers = tbSysAccessProfileUsers;
	}

	public float getfOrgManager() {
		return fOrgManager;
	}

	public void setfOrgManager(float fOrgManager) {
		this.fOrgManager = fOrgManager;
	}

	public boolean isCkecked() {
		return ckecked;
	}

	public void setCkecked(boolean ckecked) {
		this.ckecked = ckecked;
	}

	public boolean isfManager() {
		return fManager;
	}

	public void setfManager(boolean fManager) {
		this.fManager = fManager;
	}

	public List<TbTaxQuote> getTbTaxQuotes() {
		return tbTaxQuotes;
	}

	public void setTbTaxQuotes(List<TbTaxQuote> tbTaxQuotes) {
		this.tbTaxQuotes = tbTaxQuotes;
	}

	public List<TbSysLogEmailSend> getTbSysLogEmailSends() {
		return tbSysLogEmailSends;
	}

	public void setTbSysLogEmailSends(List<TbSysLogEmailSend> tbSysLogEmailSends) {
		this.tbSysLogEmailSends = tbSysLogEmailSends;
	}

	public String getsCodePasswordSend() {
		return sCodePasswordSend;
	}

	public void setsCodePasswordSend(String sCodePasswordSend) {
		this.sCodePasswordSend = sCodePasswordSend;
	}

	public Date getDtForgotPassword() {
		return dtForgotPassword;
	}

	public void setDtForgotPassword(Date dtForgotPassword) {
		this.dtForgotPassword = dtForgotPassword;
	}

	public List<TbTaxQuoteParticipant> getTbTaxQuoteParticipants() {
		return tbTaxQuoteParticipants;
	}

	public void setTbTaxQuoteParticipants(
			List<TbTaxQuoteParticipant> tbTaxQuoteParticipants) {
		this.tbTaxQuoteParticipants = tbTaxQuoteParticipants;
	}

	public boolean isfManagerGroupSales() {
		
		fManagerGroupSales = false;
		
		for(TbSysWorkGroupUser groupUser : getTbSysWorkGroupUsers()){
			if(groupUser.getTbSysWorkGroup().getId() == 61L && groupUser.isfManager()){
				fManagerGroupSales = true;
			}
		}
		
		return fManagerGroupSales;
	}

	public void setfManagerGroupSales(boolean fManagerGroupSales) {
		this.fManagerGroupSales = fManagerGroupSales;
	}
	
	
}