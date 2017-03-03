package br.com.operations.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the TBTAXQUOTE database table.
 * 
 */
@Entity
@NamedQuery(name="TbTaxQuote.findAll", query="SELECT t FROM TbTaxQuote t")
public class TbTaxQuote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBTAXQUOTE_ID_GENERATOR", sequenceName="SEQ_TBTAXQUOTE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBTAXQUOTE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.DATE)
	private Date dtClose;

	@Temporal(TemporalType.DATE)
	private Date dtCreation;

	@Temporal(TemporalType.DATE)
	private Date dtDelete;

	@Temporal(TemporalType.DATE)
	private Date dtLastAccess;

	@Temporal(TemporalType.DATE)
	private Date dtLastUpdate;

	@Temporal(TemporalType.DATE)
	private Date dtRelease;

	@Temporal(TemporalType.DATE)
	private Date dtRequestingApprovalLevel2;

	@Temporal(TemporalType.DATE)
	private Date dtRequestingApprovalLevel3;

	@Temporal(TemporalType.DATE)
	private Date dtRequestingApprovalLevel4;

	@Temporal(TemporalType.DATE)
	private Date dtUpdateStatusLevel2;

	@Temporal(TemporalType.DATE)
	private Date dtUpdateStatusLevel3;

	@Temporal(TemporalType.DATE)
	private Date dtUpdateStatusLevel4;

	private boolean fContrest;

	private boolean fDelete;

	private boolean fImportAll;

	private boolean fLeasing;

	private boolean fPartner;

	private boolean fPpbDiscountClass;

	private boolean fReadonly;

	private boolean fStatusLevel2;

	private boolean fStatusLevel3;

	private boolean fStatusLevel4;

	private boolean fSuframa;
	
	private boolean fPIS;
	
	private boolean fCOFINS;
	
	private boolean fISS;
	
	private boolean fICMS;
	
	private boolean fIPI;
	
	private boolean fSt;
	
	@ManyToOne
	@JoinColumn(name="IDADMDESTINATION")
	private TbAdmDestination tbAdmDestination;

	@ManyToOne
	@JoinColumn(name="IDADMGROUPOFCONTENT")
	private TbAdmGroupOfContent tbAdmGroupOfContent;

	@ManyToOne
	@JoinColumn(name="IDADMORIGINHW")
	private TbAdmOrigin tbAdmOriginHw;

	@ManyToOne
	@JoinColumn(name="IDADMORIGINMT")
	private TbAdmOrigin tbAdmOriginMt;

	@ManyToOne
	@JoinColumn(name="IDADMORIGINSV")
	private TbAdmOrigin tbAdmOriginSv;

	@ManyToOne
	@JoinColumn(name="IDADMORIGINSW")
	private TbAdmOrigin tbAdmOriginSw;

	@ManyToOne
	@JoinColumn(name="IDADMSALESREP")
	private TbSysUser tbSysUser;

	@ManyToOne
	@JoinColumn(name="IDSYSUSERAPPROVALLEVEL2")
	private TbSysUser tbSysUserApprovalLevel2;

	@ManyToOne
	@JoinColumn(name="IDSYSUSERAPPROVALLEVEL3")
	private TbSysUser tbSysUserApprovalLevel3;

	@ManyToOne
	@JoinColumn(name="IDSYSUSERAPPROVALLEVEL4")
	private TbSysUser tbSysUserApprovalLevel4;
	
	@ManyToOne
	@JoinColumn(name="IDSYSUSERLASTUPDATE")
	private TbSysUser tbSysUserLastUpdate;
	
	@ManyToOne
	@JoinColumn(name="IDSYSUSEROWNER")
	private TbSysUser tbSysUserOwner;
		
	@ManyToOne
	@JoinColumn(name="IDSYSUSERREQUESTAPPROVALLEVEL2")
	private TbSysUser tbSysUserResquestApprovalLevel2;
	
	@ManyToOne
	@JoinColumn(name="IDSYSUSERREQUESTAPPROVALLEVEL3")
	private TbSysUser tbSysUserResquestApprovalLevel3;

	@ManyToOne
	@JoinColumn(name="IDSYSUSERREQUESTAPPROVALLEVEL4")
	private TbSysUser tbSysUserResquestApprovalLevel4;

	private Integer iQuoteVersion;

	private Double mAdditionalCostFreight;

	private Double mAdditionalCostOtherCosts;

	private Double mAdditionalCostProjectCost;

	private Double mAdditionalCostServiceCpw;

	private Double mAdditionalCostTraining;

	private Double mDollarRate;
	
	private Double mDailyDollar;

	private Double rGrossMargin;

	private Double rGrossMarginPlus;

	private Double rMinimumMarginLevel1;

	private Double rMinimumMarginLevel2;

	private Double rMinimumMarginLevel3;

	private Double rNetMargin;

	private Double rNetMarginPlus;

	private String sCloseComment;

	private String sCommentsLevel2;

	private String sCommentsLevel3;

	private String sCommentsLevel4;

	private String sCommentsOtherCost;

	private String sContract;

	private String sCustomer;

	private String sDeleteComment;

	private String sDescription;

	private String sNumeroOportunidadeAx;

	private String sQuoteName;

	private String sQuoteNumber;

	private String sQuoteNumberManufacturer;

	private String sQuoteVersionDirect;

	private String sReleaseComment;

	private BigDecimal mTotalGross;
	
	private BigDecimal mTotalNet;
	
	//bi-directional many-to-one association to TbTaxQuoteDetail
	@OneToOne(mappedBy="tbTaxQuote")
	private TbTaxQuoteDetail tbTaxQuoteDetail;

	//bi-directional many-to-one association to TbTaxQuoteDisc
	@OneToMany(mappedBy="tbtaxquote")
	private List<TbTaxQuoteDisc> tbtaxquotediscs;

	//bi-directional many-to-one association to TbTaxQuoteItem
	@OneToMany(mappedBy="tbTaxQuote")
	private List<TbTaxQuoteItem> tbTaxQuoteItems;

	@OneToMany(mappedBy="tbTaxQuote")
	private List<TbTaxQuoteParticipant> tbTaxQuoteParticipants;
	
	@OneToMany(mappedBy="tbTaxQuote")
	private List<TbTaxQuoteLog> tbTaxQuoteLogs; 
	
	@Transient
	private String dtFormatCreationDate;
	
	@Transient
	private String styleLine;
	
	@Transient
	private String personName;
	
	public TbTaxQuote() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDtClose() {
		return dtClose;
	}

	public void setDtClose(Date dtClose) {
		this.dtClose = dtClose;
	}

	public Date getDtCreation() {
		return dtCreation;
	}

	public void setDtCreation(Date dtCreation) {
		this.dtCreation = dtCreation;
	}

	public Date getDtDelete() {
		return dtDelete;
	}

	public void setDtDelete(Date dtDelete) {
		this.dtDelete = dtDelete;
	}

	public Date getDtLastAccess() {
		return dtLastAccess;
	}

	public void setDtLastAccess(Date dtLastAccess) {
		this.dtLastAccess = dtLastAccess;
	}

	public Date getDtLastUpdate() {
		return dtLastUpdate;
	}

	public void setDtLastUpdate(Date dtLastUpdate) {
		this.dtLastUpdate = dtLastUpdate;
	}

	public Date getDtRelease() {
		return dtRelease;
	}

	public void setDtRelease(Date dtRelease) {
		this.dtRelease = dtRelease;
	}

	public Date getDtRequestingApprovalLevel2() {
		return dtRequestingApprovalLevel2;
	}

	public void setDtRequestingApprovalLevel2(Date dtRequestingApprovalLevel2) {
		this.dtRequestingApprovalLevel2 = dtRequestingApprovalLevel2;
	}

	public Date getDtRequestingApprovalLevel3() {
		return dtRequestingApprovalLevel3;
	}

	public void setDtRequestingApprovalLevel3(Date dtRequestingApprovalLevel3) {
		this.dtRequestingApprovalLevel3 = dtRequestingApprovalLevel3;
	}

	public Date getDtRequestingApprovalLevel4() {
		return dtRequestingApprovalLevel4;
	}

	public void setDtRequestingApprovalLevel4(Date dtRequestingApprovalLevel4) {
		this.dtRequestingApprovalLevel4 = dtRequestingApprovalLevel4;
	}

	public Date getDtUpdateStatusLevel2() {
		return dtUpdateStatusLevel2;
	}

	public void setDtUpdateStatusLevel2(Date dtUpdateStatusLevel2) {
		this.dtUpdateStatusLevel2 = dtUpdateStatusLevel2;
	}

	public Date getDtUpdateStatusLevel3() {
		return dtUpdateStatusLevel3;
	}

	public void setDtUpdateStatusLevel3(Date dtUpdateStatusLevel3) {
		this.dtUpdateStatusLevel3 = dtUpdateStatusLevel3;
	}

	public Date getDtUpdateStatusLevel4() {
		return dtUpdateStatusLevel4;
	}

	public void setDtUpdateStatusLevel4(Date dtUpdateStatusLevel4) {
		this.dtUpdateStatusLevel4 = dtUpdateStatusLevel4;
	}

	public boolean isfContrest() {
		return fContrest;
	}

	public void setfContrest(boolean fContrest) {
		this.fContrest = fContrest;
	}

	public boolean isfDelete() {
		return fDelete;
	}

	public void setfDelete(boolean fDelete) {
		this.fDelete = fDelete;
	}

	public boolean isfImportAll() {
		return fImportAll;
	}

	public void setfImportAll(boolean fImportAll) {
		this.fImportAll = fImportAll;
	}

	public boolean isfLeasing() {
		return fLeasing;
	}

	public void setfLeasing(boolean fLeasing) {
		this.fLeasing = fLeasing;
	}

	public boolean isfPartner() {
		return fPartner;
	}

	public void setfPartner(boolean fPartner) {
		this.fPartner = fPartner;
	}

	public boolean isfPpbDiscountClass() {
		return fPpbDiscountClass;
	}

	public void setfPpbDiscountClass(boolean fPpbDiscountClass) {
		this.fPpbDiscountClass = fPpbDiscountClass;
	}

	public boolean isfReadonly() {
		return fReadonly;
	}

	public void setfReadonly(boolean fReadonly) {
		this.fReadonly = fReadonly;
	}

	public boolean isfStatusLevel2() {
		return fStatusLevel2;
	}

	public void setfStatusLevel2(boolean fStatusLevel2) {
		this.fStatusLevel2 = fStatusLevel2;
	}

	

	public boolean isfStatusLevel3() {
		return fStatusLevel3;
	}

	public void setfStatusLevel3(boolean fStatusLevel3) {
		this.fStatusLevel3 = fStatusLevel3;
	}

	public boolean isfStatusLevel4() {
		return fStatusLevel4;
	}

	public void setfStatusLevel4(boolean fStatusLevel4) {
		this.fStatusLevel4 = fStatusLevel4;
	}

	public boolean isfSuframa() {
		return fSuframa;
	}

	public void setfSuframa(boolean fSuframa) {
		this.fSuframa = fSuframa;
	}

	public TbAdmDestination getTbAdmDestination() {
		return tbAdmDestination;
	}

	public void setTbAdmDestination(TbAdmDestination tbAdmDestination) {
		this.tbAdmDestination = tbAdmDestination;
	}

	public TbAdmGroupOfContent getTbAdmGroupOfContent() {
		return tbAdmGroupOfContent;
	}

	public void setTbAdmGroupOfContent(TbAdmGroupOfContent tbAdmGroupOfContent) {
		this.tbAdmGroupOfContent = tbAdmGroupOfContent;
	}

	public TbAdmOrigin getTbAdmOriginHw() {
		return tbAdmOriginHw;
	}

	public void setTbAdmOriginHw(TbAdmOrigin tbAdmOriginHw) {
		this.tbAdmOriginHw = tbAdmOriginHw;
	}

	public TbAdmOrigin getTbAdmOriginMt() {
		return tbAdmOriginMt;
	}

	public void setTbAdmOriginMt(TbAdmOrigin tbAdmOriginMt) {
		this.tbAdmOriginMt = tbAdmOriginMt;
	}

	public TbAdmOrigin getTbAdmOriginSv() {
		return tbAdmOriginSv;
	}

	public void setTbAdmOriginSv(TbAdmOrigin tbAdmOriginSv) {
		this.tbAdmOriginSv = tbAdmOriginSv;
	}

	public TbAdmOrigin getTbAdmOriginSw() {
		return tbAdmOriginSw;
	}

	public void setTbAdmOriginSw(TbAdmOrigin tbAdmOriginSw) {
		this.tbAdmOriginSw = tbAdmOriginSw;
	}


	public TbSysUser getTbSysUser() {
		return tbSysUser;
	}

	public void setTbSysUser(TbSysUser tbSysUser) {
		this.tbSysUser = tbSysUser;
	}

	public TbSysUser getTbSysUserApprovalLevel2() {
		return tbSysUserApprovalLevel2;
	}

	public void setTbSysUserApprovalLevel2(TbSysUser tbSysUserApprovalLevel2) {
		this.tbSysUserApprovalLevel2 = tbSysUserApprovalLevel2;
	}

	public TbSysUser getTbSysUserApprovalLevel3() {
		return tbSysUserApprovalLevel3;
	}

	public void setTbSysUserApprovalLevel3(TbSysUser tbSysUserApprovalLevel3) {
		this.tbSysUserApprovalLevel3 = tbSysUserApprovalLevel3;
	}

	public TbSysUser getTbSysUserApprovalLevel4() {
		return tbSysUserApprovalLevel4;
	}

	public void setTbSysUserApprovalLevel4(TbSysUser tbSysUserApprovalLevel4) {
		this.tbSysUserApprovalLevel4 = tbSysUserApprovalLevel4;
	}

	public TbSysUser getTbSysUserLastUpdate() {
		return tbSysUserLastUpdate;
	}

	public void setTbSysUserLastUpdate(TbSysUser tbSysUserLastUpdate) {
		this.tbSysUserLastUpdate = tbSysUserLastUpdate;
	}

	public TbSysUser getTbSysUserOwner() {
		return tbSysUserOwner;
	}

	public void setTbSysUserOwner(TbSysUser tbSysUserOwner) {
		this.tbSysUserOwner = tbSysUserOwner;
	}

	public TbSysUser getTbSysUserResquestApprovalLevel2() {
		return tbSysUserResquestApprovalLevel2;
	}

	public void setTbSysUserResquestApprovalLevel2(
			TbSysUser tbSysUserResquestApprovalLevel2) {
		this.tbSysUserResquestApprovalLevel2 = tbSysUserResquestApprovalLevel2;
	}

	public TbSysUser getTbSysUserResquestApprovalLevel3() {
		return tbSysUserResquestApprovalLevel3;
	}

	public void setTbSysUserResquestApprovalLevel3(
			TbSysUser tbSysUserResquestApprovalLevel3) {
		this.tbSysUserResquestApprovalLevel3 = tbSysUserResquestApprovalLevel3;
	}

	public TbSysUser getTbSysUserResquestApprovalLevel4() {
		return tbSysUserResquestApprovalLevel4;
	}

	public void setTbSysUserResquestApprovalLevel4(
			TbSysUser tbSysUserResquestApprovalLevel4) {
		this.tbSysUserResquestApprovalLevel4 = tbSysUserResquestApprovalLevel4;
	}

	public Integer getiQuoteVersion() {
		return iQuoteVersion;
	}

	public void setiQuoteVersion(Integer iQuoteVersion) {
		this.iQuoteVersion = iQuoteVersion;
	}

	public Double getmAdditionalCostFreight() {
		return mAdditionalCostFreight;
	}

	public void setmAdditionalCostFreight(Double mAdditionalCostFreight) {
		this.mAdditionalCostFreight = mAdditionalCostFreight;
	}

	public Double getmAdditionalCostOtherCosts() {
		return mAdditionalCostOtherCosts;
	}

	public void setmAdditionalCostOtherCosts(Double mAdditionalCostOtherCosts) {
		this.mAdditionalCostOtherCosts = mAdditionalCostOtherCosts;
	}

	public Double getmAdditionalCostProjectCost() {
		return mAdditionalCostProjectCost;
	}

	public void setmAdditionalCostProjectCost(Double mAdditionalCostProjectCost) {
		this.mAdditionalCostProjectCost = mAdditionalCostProjectCost;
	}

	public Double getmAdditionalCostServiceCpw() {
		return mAdditionalCostServiceCpw;
	}

	public void setmAdditionalCostServiceCpw(Double mAdditionalCostServiceCpw) {
		this.mAdditionalCostServiceCpw = mAdditionalCostServiceCpw;
	}

	public Double getmAdditionalCostTraining() {
		return mAdditionalCostTraining;
	}

	public void setmAdditionalCostTraining(Double mAdditionalCostTraining) {
		this.mAdditionalCostTraining = mAdditionalCostTraining;
	}

	public Double getmDollarRate() {
		return mDollarRate;
	}

	public void setmDollarRate(Double mDollarRate) {
		this.mDollarRate = mDollarRate;
	}

	public Double getrGrossMargin() {
		return rGrossMargin;
	}

	public void setrGrossMargin(Double rGrossMargin) {
		this.rGrossMargin = rGrossMargin;
	}

	public Double getrGrossMarginPlus() {
		return rGrossMarginPlus;
	}

	public void setrGrossMarginPlus(Double rGrossMarginPlus) {
		this.rGrossMarginPlus = rGrossMarginPlus;
	}

	public Double getrMinimumMarginLevel1() {
		return rMinimumMarginLevel1;
	}

	public void setrMinimumMarginLevel1(Double rMinimumMarginLevel1) {
		this.rMinimumMarginLevel1 = rMinimumMarginLevel1;
	}

	public Double getrMinimumMarginLevel2() {
		return rMinimumMarginLevel2;
	}

	public void setrMinimumMarginLevel2(Double rMinimumMarginLevel2) {
		this.rMinimumMarginLevel2 = rMinimumMarginLevel2;
	}

	public Double getrMinimumMarginLevel3() {
		return rMinimumMarginLevel3;
	}

	public void setrMinimumMarginLevel3(Double rMinimumMarginLevel3) {
		this.rMinimumMarginLevel3 = rMinimumMarginLevel3;
	}

	public Double getrNetMargin() {
		return rNetMargin;
	}

	public void setrNetMargin(Double rNetMargin) {
		this.rNetMargin = rNetMargin;
	}

	public Double getrNetMarginPlus() {
		return rNetMarginPlus;
	}

	public void setrNetMarginPlus(Double rNetMarginPlus) {
		this.rNetMarginPlus = rNetMarginPlus;
	}

	public String getsCloseComment() {
		return sCloseComment;
	}

	public void setsCloseComment(String sCloseComment) {
		this.sCloseComment = sCloseComment;
	}

	public String getsCommentsLevel2() {
		return sCommentsLevel2;
	}

	public void setsCommentsLevel2(String sCommentsLevel2) {
		this.sCommentsLevel2 = sCommentsLevel2;
	}

	public String getsCommentsLevel3() {
		return sCommentsLevel3;
	}

	public void setsCommentsLevel3(String sCommentsLevel3) {
		this.sCommentsLevel3 = sCommentsLevel3;
	}

	public String getsCommentsLevel4() {
		return sCommentsLevel4;
	}

	public void setsCommentsLevel4(String sCommentsLevel4) {
		this.sCommentsLevel4 = sCommentsLevel4;
	}

	public String getsCommentsOtherCost() {
		return sCommentsOtherCost;
	}

	public void setsCommentsOtherCost(String sCommentsOtherCost) {
		this.sCommentsOtherCost = sCommentsOtherCost;
	}

	public String getsContract() {
		return sContract;
	}

	public void setsContract(String sContract) {
		this.sContract = sContract;
	}

	public String getsCustomer() {
		return sCustomer;
	}

	public void setsCustomer(String sCustomer) {
		this.sCustomer = sCustomer;
	}

	public String getsDeleteComment() {
		return sDeleteComment;
	}

	public void setsDeleteComment(String sDeleteComment) {
		this.sDeleteComment = sDeleteComment;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public String getsNumeroOportunidadeAx() {
		return sNumeroOportunidadeAx;
	}

	public void setsNumeroOportunidadeAx(String sNumeroOportunidadeAx) {
		this.sNumeroOportunidadeAx = sNumeroOportunidadeAx;
	}

	public String getsQuoteName() {
		return sQuoteName;
	}

	public void setsQuoteName(String sQuoteName) {
		this.sQuoteName = sQuoteName;
	}

	public String getsQuoteNumber() {
		return sQuoteNumber;
	}

	public void setsQuoteNumber(String sQuoteNumber) {
		this.sQuoteNumber = sQuoteNumber;
	}

	public String getsQuoteNumberManufacturer() {
		return sQuoteNumberManufacturer;
	}

	public void setsQuoteNumberManufacturer(String sQuoteNumberManufacturer) {
		this.sQuoteNumberManufacturer = sQuoteNumberManufacturer;
	}

	public String getsQuoteVersionDirect() {
		return sQuoteVersionDirect;
	}

	public void setsQuoteVersionDirect(String sQuoteVersionDirect) {
		this.sQuoteVersionDirect = sQuoteVersionDirect;
	}

	public String getsReleaseComment() {
		return sReleaseComment;
	}

	public void setsReleaseComment(String sReleaseComment) {
		this.sReleaseComment = sReleaseComment;
	}

	public TbTaxQuoteDetail getTbTaxQuoteDetail() {
		return tbTaxQuoteDetail;
	}

	public void setTbTaxQuoteDetail(TbTaxQuoteDetail tbTaxQuoteDetail) {
		this.tbTaxQuoteDetail = tbTaxQuoteDetail;
	}

	public List<TbTaxQuoteDisc> getTbtaxquotediscs() {
		return tbtaxquotediscs;
	}

	public void setTbtaxquotediscs(List<TbTaxQuoteDisc> tbtaxquotediscs) {
		this.tbtaxquotediscs = tbtaxquotediscs;
	}

	
	public List<TbTaxQuoteItem> getTbTaxQuoteItems() {
		return tbTaxQuoteItems;
	}

	public void setTbTaxQuoteItems(List<TbTaxQuoteItem> tbTaxQuoteItems) {
		this.tbTaxQuoteItems = tbTaxQuoteItems;
	}

	public String getDtFormatCreationDate() {
		
		if(dtCreation != null){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dtFormatCreationDate = sdf.format(dtCreation);
		}
				
		
		return dtFormatCreationDate;
	}

	public void setDtFormatCreationDate(String dtFormatCreationDate) {
		this.dtFormatCreationDate = dtFormatCreationDate;
	}


	public BigDecimal getmTotalGross() {
		return mTotalGross;
	}

	public void setmTotalGross(BigDecimal mTotalGross) {
		this.mTotalGross = mTotalGross;
	}

	public BigDecimal getmTotalNet() {
		return mTotalNet;
	}

	public void setmTotalNet(BigDecimal mTotalNet) {
		this.mTotalNet = mTotalNet;
	}

	public String getStyleLine() {
		
		if(dtRelease != null && dtClose == null){
			styleLine = "color: #FFCC00;";
		}else if(dtClose != null){
			styleLine = "color: #008000;";
		}else{
			styleLine = "";
		}
		
		return styleLine;
	}

	public void setStyleLine(String styleLine) {
		this.styleLine = styleLine;
	}
	public List<TbTaxQuoteParticipant> getTbTaxQuoteParticipants() {
		return tbTaxQuoteParticipants;
	}

	public void setTbTaxQuoteParticipants(
			List<TbTaxQuoteParticipant> tbTaxQuoteParticipants) {
		this.tbTaxQuoteParticipants = tbTaxQuoteParticipants;
	}

	public String getPersonName() {
		
		if(tbSysUser != null){
			personName = tbSysUser.getsFullName();
		}
		
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public boolean isfPIS() {
		return fPIS;
	}

	public void setfPIS(boolean fPIS) {
		this.fPIS = fPIS;
	}

	public boolean isfCOFINS() {
		return fCOFINS;
	}

	public void setfCOFINS(boolean fCOFINS) {
		this.fCOFINS = fCOFINS;
	}

	public boolean isfISS() {
		return fISS;
	}

	public void setfISS(boolean fISS) {
		this.fISS = fISS;
	}

	public boolean isfICMS() {
		return fICMS;
	}

	public void setfICMS(boolean fICMS) {
		this.fICMS = fICMS;
	}

	public boolean isfIPI() {
		return fIPI;
	}

	public void setfIPI(boolean fIPI) {
		this.fIPI = fIPI;
	}

	public List<TbTaxQuoteLog> getTbTaxQuoteLogs() {
		return tbTaxQuoteLogs;
	}

	public void setTbTaxQuoteLogs(List<TbTaxQuoteLog> tbTaxQuoteLogs) {
		this.tbTaxQuoteLogs = tbTaxQuoteLogs;
	}

	public boolean isfSt() {
		return fSt;
	}

	public void setfSt(boolean fSt) {
		this.fSt = fSt;
	}

	public Double getmDailyDollar() {
		return mDailyDollar;
	}

	public void setmDailyDollar(Double mDailyDollar) {
		this.mDailyDollar = mDailyDollar;
	}

	
	
}