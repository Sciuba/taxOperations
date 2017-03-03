package br.com.operations.app.quote.model;

import java.util.Date;
import java.util.List;

import br.com.operations.app.quote.to.QuoteItemTO;

public class QuoteModel {
	
	
	/**
	 * Objeto representante dos Itens da Quote
	 */
	private List<QuoteItemTO> listaQuoteItemTO;
	
	
	/**
	 * Parâmetros
	 */
	private long idTaxQuote;
	
	private boolean fPartner;
	
	private boolean blnfContrEst;
	
	private boolean blnfSuframa;
	
	private boolean blnfLeasing;
	
	private boolean fImportAll;
	
	private boolean fPIS;
	
	private boolean fICMS;
	
	private boolean fISS;
	
	private boolean fCOFINS;
	
	private boolean fIPI;
	
	private boolean fSt;
	
	private Date closedQuote;
	 
	private Date releaseQuote;
	
	private long idTaxQuoteCorte;
	
	private boolean fReadOnly;
	
	private boolean fPPBDiscountClass;
	
	private boolean fDollarRate;
	
	private float multDollarRate;
	
	private boolean quoteChannel;
	
	private float rISSStandard;
	
	private float rISSStandardMTESp;
	
	private boolean fAbsoluteZeroModel;
	
	private float rICMSEntradaPadrao;
	
	private float rICMSSaidaPadrao;
	
	private float rFatorRelevanciaMaxima;
	
	private double mTotalAdditionalCost;
	
	private float rSuggestedMarginDefault;
	
	private float dblrMaiorPIS;
	
	private float dblrMaiorCofins;
	
	private float blnFixedTaxRate;
	
	private float dblrMaiorIR;
	
	private float dblrMaiorCSSL;
	
	private float dblrMaiorIPI;
	
	private float dblrMaiorISS;
	
	private float dblrMaiorICMS;
	
	
	/**
	 * Categoria de repasse do desconto do benefício do PPB
	 */
	private String PPB_Repasse_Nenhum;
	
	private String PPB_Repasse_Minimo;
	
	private String PPB_Repasse_Medio;
	
	private String PPB_Repasse_Maximo;
	
	
	/**
	 * Linhas da matriz de Impostos
	 */
	private float rICMSInterEstadual;
	
	private float rICMSInterEstadualMaterialImportado;
	
	private float rICMSST;
	
	private float rICMSMaterialClass;
	
	private float rIPI;
	
	private float rISS;
	
	private float rPis;
	
	private float rCOFINS;
	
	private float rIR;
	
	private float rCSSL;
	
	private float rTaxes;
	
	private float rSuggestedNetPrice;
	
	private float rNetPrice;
	
	private float rGrossPrice;
	
	private float rNetPriceBaseDeCalculo;
	
	private double mFatorEntrada;
	
	private float rCostCalculated;
	
	private float rCost;
	
	private float rNetMargin;
	
	private float rNetMarginPlus;
	
	private float rGrossMargin;
	
	private float rGrossMarginPlus;
	
	private double mICMSARecuperar;
	
	private double mICMSARecuperarST;

	
	/**
	 * Totais por tipo de Item
	 * @return
	 */
	private double netPriceHW;
	private double grossPriceHW;
	private double gsNetPriceHW;
	private double gsGrossPriceHW;
	private double taxesHW;
	private float netMarginHW;
	private double netPriceLessCostHW;
	private double mICMSMaterialHW;
	private double mICMSInterEstadualHW;
	private double mICMSinterestadualMatImportadoHW;
	private double mICMSSTHW;
	private double mIPIHW;
	private double mISSHW;
	private double mPISHW;
	private double mCOFINSHW;
	private double mIRHW;
	private double mCSSLHW;
	private double mICMSRecoverableHW;
	private double mTotalTaxesHW;
	private double mICMSEstadoOriginHW;
	private double mICMSEstadoDestinoHW;
	
	private double netPriceCS;
	private double grossPriceCS;
	private double gsNetPriceCS;
	private double gsGrossPriceCS;
	private double taxesCS;
	private float netMarginCS;
	private double netPriceLessCostCS;
	private double mICMSMaterialCS;
	private double mICMSInterEstadualCS;
	private double mICMSinterestadualMatImportadoCS;
	private double mICMSSTCS;
	private double mIPICS;
	private double mISSCS;
	private double mPISCS;
	private double mCOFINSCS;
	private double mIRCS;
	private double mCSSLCS;
	private double mICMSRecoverableCS;
	private double mTotalTaxesCS;
	private double mICMSEstadoOriginCS;
	private double mICMSEstadoDestinoCS;
	
	private double netPricePS;
	private double grossPricePS;
	private double gsNetPricePS;
	private double gsGrossPricePS;
	private double taxesPS;
	private float netMarginPS;
	private double netPriceLessCostPS;
	private double mICMSMaterialPS;
	private double mICMSInterEstadualPS;
	private double mICMSinterestadualMatImportadoPS;
	private double mICMSSTPS;
	private double mIPIPS;
	private double mISSPS;
	private double mPISPS;
	private double mCOFINSPS;
	private double mIRPS;
	private double mCSSLPS;
	private double mICMSRecoverablePS;
	private double mTotalTaxesPS;
	private double mICMSEstadoOriginPS;
	private double mICMSEstadoDestinoPS;
	
	private double netPriceSW;
	private double grossPriceSW;
	private double gsNetPriceSW;
	private double gsGrossPriceSW;
	private double taxesSW;
	private float netMarginSW;
	private double netPriceLessCostSW;
	private double mICMSMaterialSW;
	private double mICMSInterEstadualSW;
	private double mICMSinterestadualMatImportadoSW;
	private double mICMSSTSW;
	private double mIPISW;
	private double mISSSW;
	private double mPISSW;
	private double mCOFINSSW;
	private double mIRSW;
	private double mCSSLSW;
	private double mICMSRecoverableSW;
	private double mTotalTaxesSW;
	private double mICMSEstadoOriginSW;
	private double mICMSEstadoDestinoSW;
	
	private double netPriceMD;
	private double grossPriceMD;
	private double gsNetPriceMD;
	private double gsGrossPriceMD;
	private double taxesMD;
	private float netMarginMD;
	private double netPriceLessCostMD;
	private double mICMSMaterialMD;
	private double mICMSInterEstadualMD;
	private double mICMSinterestadualMatImportadoMD;
	private double mICMSSTMD;
	private double mIPIMD;
	private double mISSMD;
	private double mPISMD;
	private double mCOFINSMD;
	private double mIRMD;
	private double mCSSLMD;
	private double mICMSRecoverableMD;
	private double mTotalTaxesMD;
	private double mICMSEstadoOriginMD;
	private double mICMSEstadoDestinoMD;
	
	private double gsTotalNet;
	private double gsTotalGross;
	private double gsTaxesTotal;
	private float netMarginTotal;
	private double netPriceLessCostTotal;
	private double mICMSMaterialTotal;
	private double mICMSInterEstadualTotal;
	private double mICMSinterestadualMatImportadoTotal;
	private double mICMSSTTotal;
	private double mIPITotal;
	private double mISSTotal;
	private double mPISTotal;
	private double mCOFINSTotal;
	private double mIRTotal;
	private double mCSSLTotal;
	private double mICMSRecoverableTotal;
	private double mTotalTaxesTotal;
	private double mICMSEstadoOriginTotal;
	private double mICMSEstadoDestinoTotal;
	
	
	private boolean gsHW;
	private boolean gsSW;
	private boolean gsPS;
	private boolean gsCS;
	private boolean gsMD;
	private boolean gsTotal;
	
	public List<QuoteItemTO> getListaQuoteItemTO() {
		return listaQuoteItemTO;
	}

	public void setListaQuoteItemTO(List<QuoteItemTO> listaQuoteItemTO) {
		this.listaQuoteItemTO = listaQuoteItemTO;
	}

	public boolean isfPartner() {
		return fPartner;
	}

	public void setfPartner(boolean fPartner) {
		this.fPartner = fPartner;
	}

	public Date getClosedQuote() {
		return closedQuote;
	}

	public void setClosedQuote(Date date) {
		this.closedQuote = date;
	}

	public Date getReleaseQuote() {
		return releaseQuote;
	}

	public void setReleaseQuote(Date releaseQuote) {
		this.releaseQuote = releaseQuote;
	}

	public long getIdTaxQuoteCorte() {
		return idTaxQuoteCorte;
	}

	public void setIdTaxQuoteCorte(long idTaxQuoteCorte) {
		this.idTaxQuoteCorte = idTaxQuoteCorte;
	}

	public boolean isfReadOnly() {
		return fReadOnly;
	}

	public void setfReadOnly(boolean fReadOnly) {
		this.fReadOnly = fReadOnly;
	}

	public boolean getfPPBDiscountClass() {
		return fPPBDiscountClass;
	}

	public void setfPPBDiscountClass(boolean fPPBDiscountClass) {
		this.fPPBDiscountClass = fPPBDiscountClass;
	}

	public boolean isfDollarRate() {
		return fDollarRate;
	}

	public void setfDollarRate(boolean fDollarRate) {
		this.fDollarRate = fDollarRate;
	}

	public float getMultDollarRate() {
		return multDollarRate;
	}

	public void setMultDollarRate(float multDollarRate) {
		this.multDollarRate = multDollarRate;
	}

	public boolean isQuoteChannel() {
		return quoteChannel;
	}

	public void setQuoteChannel(boolean quoteChannel) {
		this.quoteChannel = quoteChannel;
	}

	public float getrISSStandard() {
		return rISSStandard;
	}

	public void setrISSStandard(float rISSStandard) {
		this.rISSStandard = rISSStandard;
	}

	public float getrISSStandardMTESp() {
		return rISSStandardMTESp;
	}

	public void setrISSStandardMTESp(float rISSStandardMTESp) {
		this.rISSStandardMTESp = rISSStandardMTESp;
	}

	public boolean isfAbsoluteZeroModel() {
		return fAbsoluteZeroModel;
	}

	public void setfAbsoluteZeroModel(boolean fAbsoluteZeroModel) {
		this.fAbsoluteZeroModel = fAbsoluteZeroModel;
	}

	public float getrICMSEntradaPadrao() {
		return rICMSEntradaPadrao;
	}

	public void setrICMSEntradaPadrao(float rICMSEntradaPadrao) {
		this.rICMSEntradaPadrao = rICMSEntradaPadrao;
	}

	public float getrICMSSaidaPadrao() {
		return rICMSSaidaPadrao;
	}

	public void setrICMSSaidaPadrao(float rICMSSaidaPadrao) {
		this.rICMSSaidaPadrao = rICMSSaidaPadrao;
	}

	public float getrFatorRelevanciaMaxima() {
		return rFatorRelevanciaMaxima;
	}

	public void setrFatorRelevanciaMaxima(float rFatorRelevanciaMaxima) {
		this.rFatorRelevanciaMaxima = rFatorRelevanciaMaxima;
	}

	public double getmTotalAdditionalCost() {
		return mTotalAdditionalCost;
	}

	public void setmTotalAdditionalCost(double mTotalAdditionalCost) {
		this.mTotalAdditionalCost = mTotalAdditionalCost;
	}

	public float getrSuggestedMarginDefault() {
		return rSuggestedMarginDefault;
	}

	public void setrSuggestedMarginDefault(float rSuggestedMarginDefault) {
		this.rSuggestedMarginDefault = rSuggestedMarginDefault;
	}

	public String getPPB_Repasse_Nenhum() {
		return PPB_Repasse_Nenhum;
	}

	public void setPPB_Repasse_Nenhum(String pPB_Repasse_Nenhum) {
		PPB_Repasse_Nenhum = pPB_Repasse_Nenhum;
	}

	public String getPPB_Repasse_Minimo() {
		return PPB_Repasse_Minimo;
	}

	public void setPPB_Repasse_Minimo(String pPB_Repasse_Minimo) {
		PPB_Repasse_Minimo = pPB_Repasse_Minimo;
	}

	public String getPPB_Repasse_Medio() {
		return PPB_Repasse_Medio;
	}

	public void setPPB_Repasse_Medio(String pPB_Repasse_Medio) {
		PPB_Repasse_Medio = pPB_Repasse_Medio;
	}

	public String getPPB_Repasse_Maximo() {
		return PPB_Repasse_Maximo;
	}

	public void setPPB_Repasse_Maximo(String pPB_Repasse_Maximo) {
		PPB_Repasse_Maximo = pPB_Repasse_Maximo;
	}

	public float getrICMSInterEstadual() {
		return rICMSInterEstadual;
	}

	public void setrICMSInterEstadual(float rICMSInterEstadual) {
		this.rICMSInterEstadual = rICMSInterEstadual;
	}

	public float getrICMSInterEstadualMaterialImportado() {
		return rICMSInterEstadualMaterialImportado;
	}

	public void setrICMSInterEstadualMaterialImportado(
			float rICMSInterEstadualMaterialImportado) {
		this.rICMSInterEstadualMaterialImportado = rICMSInterEstadualMaterialImportado;
	}

	public float getrICMSST() {
		return rICMSST;
	}

	public void setrICMSST(float rICMSST) {
		this.rICMSST = rICMSST;
	}

	public float getrICMSMaterialClass() {
		return rICMSMaterialClass;
	}

	public void setrICMSMaterialClass(float rICMSMaterialClass) {
		this.rICMSMaterialClass = rICMSMaterialClass;
	}

	public float getrIPI() {
		return rIPI;
	}

	public void setrIPI(float rIPI) {
		this.rIPI = rIPI;
	}

	public float getrISS() {
		return rISS;
	}

	public void setrISS(float rISS) {
		this.rISS = rISS;
	}

	public float getrPis() {
		return rPis;
	}

	public void setrPis(float rPis) {
		this.rPis = rPis;
	}

	public float getrCOFINS() {
		return rCOFINS;
	}

	public void setrCOFINS(float rCOFINS) {
		this.rCOFINS = rCOFINS;
	}

	public float getrIR() {
		return rIR;
	}

	public void setrIR(float rIR) {
		this.rIR = rIR;
	}

	public float getrCSSL() {
		return rCSSL;
	}

	public void setrCSSL(float rCSSL) {
		this.rCSSL = rCSSL;
	}

	public float getrTaxes() {
		return rTaxes;
	}

	public void setrTaxes(float rTaxes) {
		this.rTaxes = rTaxes;
	}

	public float getrSuggestedNetPrice() {
		return rSuggestedNetPrice;
	}

	public void setrSuggestedNetPrice(float rSuggestedNetPrice) {
		this.rSuggestedNetPrice = rSuggestedNetPrice;
	}

	public float getrNetPrice() {
		
		rNetPrice = (float) (netPriceCS + netPriceHW + netPriceMD + netPricePS + netPriceSW);
		
		return rNetPrice;
	}

	public void setrNetPrice(float rNetPrice) {
		this.rNetPrice = rNetPrice;
	}

	public float getrGrossPrice() {
		
		rGrossPrice = (float) (grossPriceCS + grossPriceHW + grossPriceMD + grossPricePS + grossPriceSW);
		
		return rGrossPrice;
	}

	public void setrGrossPrice(float rGrossPrice) {
		this.rGrossPrice = rGrossPrice;
	}

	public float getrNetPriceBaseDeCalculo() {
		return rNetPriceBaseDeCalculo;
	}

	public void setrNetPriceBaseDeCalculo(float rNetPriceBaseDeCalculo) {
		this.rNetPriceBaseDeCalculo = rNetPriceBaseDeCalculo;
	}

	public double getmFatorEntrada() {
		return mFatorEntrada;
	}

	public void setmFatorEntrada(double mFatorEntrada) {
		this.mFatorEntrada = mFatorEntrada;
	}

	public float getrCostCalculated() {
		return rCostCalculated;
	}

	public void setrCostCalculated(float rCostCalculated) {
		this.rCostCalculated = rCostCalculated;
	}

	public float getrCost() {
		return rCost;
	}

	public void setrCost(float rCost) {
		this.rCost = rCost;
	}

	public float getrNetMargin() {
		return rNetMargin;
	}

	public void setrNetMargin(float rNetMargin) {
		this.rNetMargin = rNetMargin;
	}

	public float getrNetMarginPlus() {
		return rNetMarginPlus;
	}

	public void setrNetMarginPlus(float rNetMarginPlus) {
		this.rNetMarginPlus = rNetMarginPlus;
	}

	public float getrGrossMargin() {
		return rGrossMargin;
	}

	public void setrGrossMargin(float rGrossMargin) {
		this.rGrossMargin = rGrossMargin;
	}

	public float getrGrossMarginPlus() {
		return rGrossMarginPlus;
	}

	public void setrGrossMarginPlus(float rGrossMarginPlus) {
		this.rGrossMarginPlus = rGrossMarginPlus;
	}

	public double getmICMSARecuperar() {
		return mICMSARecuperar;
	}

	public void setmICMSARecuperar(double mICMSARecuperar) {
		this.mICMSARecuperar = mICMSARecuperar;
	}

	public double getmICMSARecuperarST() {
		return mICMSARecuperarST;
	}

	public void setmICMSARecuperarST(double mICMSARecuperarST) {
		this.mICMSARecuperarST = mICMSARecuperarST;
	}

	public boolean isBlnfContrEst() {
		return blnfContrEst;
	}

	public void setBlnfContrEst(boolean blnfContrEst) {
		this.blnfContrEst = blnfContrEst;
	}

	public boolean isBlnfSuframa() {
		return blnfSuframa;
	}

	public void setBlnfSuframa(boolean blnfSuframa) {
		this.blnfSuframa = blnfSuframa;
	}

	public boolean isBlnfLeasing() {
		return blnfLeasing;
	}

	public void setBlnfLeasing(boolean blnfLeasing) {
		this.blnfLeasing = blnfLeasing;
	}

	public boolean isfImportAll() {
		return fImportAll;
	}

	public void setfImportAll(boolean fImportAll) {
		this.fImportAll = fImportAll;
	}

	public float getDblrMaiorPIS() {
		return dblrMaiorPIS;
	}

	public void setDblrMaiorPIS(float dblrMaiorPIS) {
		this.dblrMaiorPIS = dblrMaiorPIS;
	}

	public float getDblrMaiorCofins() {
		return dblrMaiorCofins;
	}

	public void setDblrMaiorCofins(float dblrMaiorCofins) {
		this.dblrMaiorCofins = dblrMaiorCofins;
	}

	public float getBlnFixedTaxRate() {
		return blnFixedTaxRate;
	}

	public void setBlnFixedTaxRate(float blnFixedTaxRate) {
		this.blnFixedTaxRate = blnFixedTaxRate;
	}

	public float getDblrMaiorIR() {
		return dblrMaiorIR;
	}

	public void setDblrMaiorIR(float dblrMaiorIR) {
		this.dblrMaiorIR = dblrMaiorIR;
	}

	public float getDblrMaiorCSSL() {
		return dblrMaiorCSSL;
	}

	public void setDblrMaiorCSSL(float dblrMaiorCSSL) {
		this.dblrMaiorCSSL = dblrMaiorCSSL;
	}

	public float getDblrMaiorIPI() {
		return dblrMaiorIPI;
	}

	public void setDblrMaiorIPI(float dblrMaiorIPI) {
		this.dblrMaiorIPI = dblrMaiorIPI;
	}

	public float getDblrMaiorISS() {
		return dblrMaiorISS;
	}

	public void setDblrMaiorISS(float dblrMaiorISS) {
		this.dblrMaiorISS = dblrMaiorISS;
	}

	public float getDblrMaiorICMS() {
		return dblrMaiorICMS;
	}

	public void setDblrMaiorICMS(float dblrMaiorICMS) {
		this.dblrMaiorICMS = dblrMaiorICMS;
	}

	public long getIdTaxQuote() {
		return idTaxQuote;
	}

	public void setIdTaxQuote(long idTaxQuote) {
		this.idTaxQuote = idTaxQuote;
	}

	public double getNetPriceHW() {
		return netPriceHW;
	}

	public void setNetPriceHW(double netPriceHW) {
		this.netPriceHW = netPriceHW;
	}

	public double getGrossPriceHW() {
		return grossPriceHW;
	}

	public void setGrossPriceHW(double grossPriceHW) {
		this.grossPriceHW = grossPriceHW;
	}

	public double getNetPriceCS() {
		return netPriceCS;
	}

	public void setNetPriceCS(double netPriceCS) {
		this.netPriceCS = netPriceCS;
	}

	public double getGrossPriceCS() {
		return grossPriceCS;
	}

	public void setGrossPriceCS(double grossPriceCS) {
		this.grossPriceCS = grossPriceCS;
	}

	public double getTaxesCS() {
		return taxesCS;
	}

	public void setTaxesCS(double taxesCS) {
		this.taxesCS = taxesCS;
	}

	public double getNetPricePS() {
		return netPricePS;
	}

	public void setNetPricePS(double netPricePS) {
		this.netPricePS = netPricePS;
	}

	public double getGrossPricePS() {
		return grossPricePS;
	}

	public void setGrossPricePS(double grossPricePS) {
		this.grossPricePS = grossPricePS;
	}

	public double getNetPriceSW() {
		return netPriceSW;
	}

	public void setNetPriceSW(double netPriceSW) {
		this.netPriceSW = netPriceSW;
	}

	public double getGrossPriceSW() {
		return grossPriceSW;
	}

	public void setGrossPriceSW(double grossPriceSW) {
		this.grossPriceSW = grossPriceSW;
	}

	public double getNetPriceMD() {
		return netPriceMD;
	}

	public void setNetPriceMD(double netPriceMD) {
		this.netPriceMD = netPriceMD;
	}

	public double getGrossPriceMD() {
		return grossPriceMD;
	}

	public void setGrossPriceMD(double grossPriceMD) {
		this.grossPriceMD = grossPriceMD;
	}

	public double getTaxesHW() {
		return taxesHW;
	}

	public void setTaxesHW(double taxesHW) {
		this.taxesHW = taxesHW;
	}

	public double getTaxesPS() {
		return taxesPS;
	}

	public void setTaxesPS(double taxesPS) {
		this.taxesPS = taxesPS;
	}

	public double getTaxesSW() {
		return taxesSW;
	}

	public void setTaxesSW(double taxesSW) {
		this.taxesSW = taxesSW;
	}

	public double getTaxesMD() {
		return taxesMD;
	}

	public void setTaxesMD(double taxesMD) {
		this.taxesMD = taxesMD;
	}

	public double getGsNetPriceHW() {
		return gsNetPriceHW;
	}

	public void setGsNetPriceHW(double gsNetPriceHW) {
		this.gsNetPriceHW = gsNetPriceHW;
	}

	public double getGsGrossPriceHW() {
		return gsGrossPriceHW;
	}

	public void setGsGrossPriceHW(double gsGrossPriceHW) {
		this.gsGrossPriceHW = gsGrossPriceHW;
	}

	public double getGsNetPriceCS() {
		return gsNetPriceCS;
	}

	public void setGsNetPriceCS(double gsNetPriceCS) {
		this.gsNetPriceCS = gsNetPriceCS;
	}

	public double getGsGrossPriceCS() {
		return gsGrossPriceCS;
	}

	public void setGsGrossPriceCS(double gsGrossPriceCS) {
		this.gsGrossPriceCS = gsGrossPriceCS;
	}

	public double getGsNetPricePS() {
		return gsNetPricePS;
	}

	public void setGsNetPricePS(double gsNetPricePS) {
		this.gsNetPricePS = gsNetPricePS;
	}

	public double getGsGrossPricePS() {
		return gsGrossPricePS;
	}

	public void setGsGrossPricePS(double gsGrossPricePS) {
		this.gsGrossPricePS = gsGrossPricePS;
	}

	public double getGsNetPriceSW() {
		return gsNetPriceSW;
	}

	public void setGsNetPriceSW(double gsNetPriceSW) {
		this.gsNetPriceSW = gsNetPriceSW;
	}

	public double getGsGrossPriceSW() {
		return gsGrossPriceSW;
	}

	public void setGsGrossPriceSW(double gsGrossPriceSW) {
		this.gsGrossPriceSW = gsGrossPriceSW;
	}

	public double getGsNetPriceMD() {
		return gsNetPriceMD;
	}

	public void setGsNetPriceMD(double gsNetPriceMD) {
		this.gsNetPriceMD = gsNetPriceMD;
	}

	public double getGsGrossPriceMD() {
		return gsGrossPriceMD;
	}

	public void setGsGrossPriceMD(double gsGrossPriceMD) {
		this.gsGrossPriceMD = gsGrossPriceMD;
	}

	public double getGsTotalNet() {
		
		gsTotalNet = (gsNetPriceCS + gsNetPriceHW + gsNetPriceMD + gsNetPricePS + gsNetPriceSW);
		
		return gsTotalNet;
	}

	public void setGsTotalNet(double gsTotalNet) {
		this.gsTotalNet = gsTotalNet;
	}

	public double getGsTotalGross() {
		
		gsTotalGross = (gsGrossPriceCS + gsGrossPriceHW + gsGrossPriceMD + gsGrossPricePS + gsGrossPriceSW);
		
		return gsTotalGross;
	}

	public void setGsTotalGross(double gsTotalGross) {
		this.gsTotalGross = gsTotalGross;
	}

	public boolean isGsHW() {
		return gsHW;
	}

	public void setGsHW(boolean gsHW) {
		this.gsHW = gsHW;
	}

	public boolean isGsSW() {
		return gsSW;
	}

	public void setGsSW(boolean gsSW) {
		this.gsSW = gsSW;
	}

	public boolean isGsPS() {
		return gsPS;
	}

	public void setGsPS(boolean gsPS) {
		this.gsPS = gsPS;
	}

	public boolean isGsCS() {
		return gsCS;
	}

	public void setGsCS(boolean gsCS) {
		this.gsCS = gsCS;
	}

	public boolean isGsMD() {
		return gsMD;
	}

	public void setGsMD(boolean gsMD) {
		this.gsMD = gsMD;
	}

	public boolean isGsTotal() {
		return gsTotal;
	}

	public void setGsTotal(boolean gsTotal) {
		this.gsTotal = gsTotal;
	}

	public double getGsTaxesTotal() {
		
		gsTaxesTotal = (taxesCS + taxesHW + taxesMD + taxesPS + taxesSW);
		
		return gsTaxesTotal;
	}

	public void setGsTaxesTotal(double gsTaxesTotal) {
		this.gsTaxesTotal = gsTaxesTotal;
	}

	public float getNetMarginHW() {
		return netMarginHW;
	}

	public void setNetMarginHW(float netMarginHW) {
		this.netMarginHW = netMarginHW;
	}

	public double getNetPriceLessCostHW() {
		return netPriceLessCostHW;
	}

	public void setNetPriceLessCostHW(double netPriceLessCostHW) {
		this.netPriceLessCostHW = netPriceLessCostHW;
	}

	public float getNetMarginCS() {
		return netMarginCS;
	}

	public void setNetMarginCS(float netMarginCS) {
		this.netMarginCS = netMarginCS;
	}

	public double getNetPriceLessCostCS() {
		return netPriceLessCostCS;
	}

	public void setNetPriceLessCostCS(double netPriceLessCostCS) {
		this.netPriceLessCostCS = netPriceLessCostCS;
	}

	public float getNetMarginPS() {
		return netMarginPS;
	}

	public void setNetMarginPS(float netMarginPS) {
		this.netMarginPS = netMarginPS;
	}

	public double getNetPriceLessCostPS() {
		return netPriceLessCostPS;
	}

	public void setNetPriceLessCostPS(double netPriceLessCostPS) {
		this.netPriceLessCostPS = netPriceLessCostPS;
	}

	public float getNetMarginSW() {
		return netMarginSW;
	}

	public void setNetMarginSW(float netMarginSW) {
		this.netMarginSW = netMarginSW;
	}

	public double getNetPriceLessCostSW() {
		return netPriceLessCostSW;
	}

	public void setNetPriceLessCostSW(double netPriceLessCostSW) {
		this.netPriceLessCostSW = netPriceLessCostSW;
	}

	public float getNetMarginMD() {
		return netMarginMD;
	}

	public void setNetMarginMD(float netMarginMD) {
		this.netMarginMD = netMarginMD;
	}

	public double getNetPriceLessCostMD() {
		return netPriceLessCostMD;
	}

	public void setNetPriceLessCostMD(double netPriceLessCostMD) {
		this.netPriceLessCostMD = netPriceLessCostMD;
	}

	public float getNetMarginTotal() {
		
		netMarginTotal = netMarginCS + netMarginHW + netMarginMD + netMarginPS + netMarginSW;
		
		return netMarginTotal;
	}

	public void setNetMarginTotal(float netMarginTotal) {
		this.netMarginTotal = netMarginTotal;
	}

	public double getNetPriceLessCostTotal() {
		
		netPriceLessCostTotal = netPriceLessCostCS + netPriceLessCostHW + netPriceLessCostMD + netPriceLessCostPS + netPriceLessCostSW;
		
		return netPriceLessCostTotal;
	}

	public void setNetPriceLessCostTotal(double netPriceLessCostTotal) {
		this.netPriceLessCostTotal = netPriceLessCostTotal;
	}
	
	public float getHwPlusSWMargin(){
		return netMarginHW + netMarginSW;
	}
	
	public double getHWPlusSWCost(){
		return netPriceLessCostHW + netPriceLessCostSW;
	}

	public double getmICMSMaterialHW() {
		return mICMSMaterialHW;
	}

	public void setmICMSMaterialHW(double mICMSMaterialHW) {
		this.mICMSMaterialHW = mICMSMaterialHW;
	}

	public double getmICMSInterEstadualHW() {
		return mICMSInterEstadualHW;
	}

	public void setmICMSInterEstadualHW(double mICMSInterEstadualHW) {
		this.mICMSInterEstadualHW = mICMSInterEstadualHW;
	}

	public double getmICMSinterestadualMatImportadoHW() {
		return mICMSinterestadualMatImportadoHW;
	}

	public void setmICMSinterestadualMatImportadoHW(
			double mICMSinterestadualMatImportadoHW) {
		this.mICMSinterestadualMatImportadoHW = mICMSinterestadualMatImportadoHW;
	}

	public double getmICMSSTHW() {
		return mICMSSTHW;
	}

	public void setmICMSSTHW(double mICMSSTHW) {
		this.mICMSSTHW = mICMSSTHW;
	}

	public double getmIPIHW() {
		return mIPIHW;
	}

	public void setmIPIHW(double mIPIHW) {
		this.mIPIHW = mIPIHW;
	}

	public double getmISSHW() {
		return mISSHW;
	}

	public void setmISSHW(double mISSHW) {
		this.mISSHW = mISSHW;
	}

	public double getmPISHW() {
		return mPISHW;
	}

	public void setmPISHW(double mPISHW) {
		this.mPISHW = mPISHW;
	}

	public double getmCOFINSHW() {
		return mCOFINSHW;
	}

	public void setmCOFINSHW(double mCOFINSHW) {
		this.mCOFINSHW = mCOFINSHW;
	}

	public double getmIRHW() {
		return mIRHW;
	}

	public void setmIRHW(double mIRHW) {
		this.mIRHW = mIRHW;
	}

	public double getmCSSLHW() {
		return mCSSLHW;
	}

	public void setmCSSLHW(double mCSSLHW) {
		this.mCSSLHW = mCSSLHW;
	}

	public double getmICMSRecoverableHW() {
		return mICMSRecoverableHW;
	}

	public void setmICMSRecoverableHW(double mICMSRecoverableHW) {
		this.mICMSRecoverableHW = mICMSRecoverableHW;
	}

	public double getmICMSMaterialCS() {
		return mICMSMaterialCS;
	}

	public void setmICMSMaterialCS(double mICMSMaterialCS) {
		this.mICMSMaterialCS = mICMSMaterialCS;
	}

	public double getmICMSInterEstadualCS() {
		return mICMSInterEstadualCS;
	}

	public void setmICMSInterEstadualCS(double mICMSInterEstadualCS) {
		this.mICMSInterEstadualCS = mICMSInterEstadualCS;
	}

	public double getmICMSinterestadualMatImportadoCS() {
		return mICMSinterestadualMatImportadoCS;
	}

	public void setmICMSinterestadualMatImportadoCS(
			double mICMSinterestadualMatImportadoCS) {
		this.mICMSinterestadualMatImportadoCS = mICMSinterestadualMatImportadoCS;
	}

	public double getmICMSSTCS() {
		return mICMSSTCS;
	}

	public void setmICMSSTCS(double mICMSSTCS) {
		this.mICMSSTCS = mICMSSTCS;
	}

	public double getmIPICS() {
		return mIPICS;
	}

	public void setmIPICS(double mIPICS) {
		this.mIPICS = mIPICS;
	}

	public double getmISSCS() {
		return mISSCS;
	}

	public void setmISSCS(double mISSCS) {
		this.mISSCS = mISSCS;
	}

	public double getmPISCS() {
		return mPISCS;
	}

	public void setmPISCS(double mPISCS) {
		this.mPISCS = mPISCS;
	}

	public double getmCOFINSCS() {
		return mCOFINSCS;
	}

	public void setmCOFINSCS(double mCOFINSCS) {
		this.mCOFINSCS = mCOFINSCS;
	}

	public double getmIRCS() {
		return mIRCS;
	}

	public void setmIRCS(double mIRCS) {
		this.mIRCS = mIRCS;
	}

	public double getmCSSLCS() {
		return mCSSLCS;
	}

	public void setmCSSLCS(double mCSSLCS) {
		this.mCSSLCS = mCSSLCS;
	}

	public double getmICMSRecoverableCS() {
		return mICMSRecoverableCS;
	}

	public void setmICMSRecoverableCS(double mICMSRecoverableCS) {
		this.mICMSRecoverableCS = mICMSRecoverableCS;
	}

	public double getmICMSMaterialPS() {
		return mICMSMaterialPS;
	}

	public void setmICMSMaterialPS(double mICMSMaterialPS) {
		this.mICMSMaterialPS = mICMSMaterialPS;
	}

	public double getmICMSInterEstadualPS() {
		return mICMSInterEstadualPS;
	}

	public void setmICMSInterEstadualPS(double mICMSInterEstadualPS) {
		this.mICMSInterEstadualPS = mICMSInterEstadualPS;
	}

	public double getmICMSinterestadualMatImportadoPS() {
		return mICMSinterestadualMatImportadoPS;
	}

	public void setmICMSinterestadualMatImportadoPS(
			double mICMSinterestadualMatImportadoPS) {
		this.mICMSinterestadualMatImportadoPS = mICMSinterestadualMatImportadoPS;
	}

	public double getmICMSSTPS() {
		return mICMSSTPS;
	}

	public void setmICMSSTPS(double mICMSSTPS) {
		this.mICMSSTPS = mICMSSTPS;
	}

	public double getmIPIPS() {
		return mIPIPS;
	}

	public void setmIPIPS(double mIPIPS) {
		this.mIPIPS = mIPIPS;
	}

	public double getmISSPS() {
		return mISSPS;
	}

	public void setmISSPS(double mISSPS) {
		this.mISSPS = mISSPS;
	}

	public double getmPISPS() {
		return mPISPS;
	}

	public void setmPISPS(double mPISPS) {
		this.mPISPS = mPISPS;
	}

	public double getmCOFINSPS() {
		return mCOFINSPS;
	}

	public void setmCOFINSPS(double mCOFINSPS) {
		this.mCOFINSPS = mCOFINSPS;
	}

	public double getmIRPS() {
		return mIRPS;
	}

	public void setmIRPS(double mIRPS) {
		this.mIRPS = mIRPS;
	}

	public double getmCSSLPS() {
		return mCSSLPS;
	}

	public void setmCSSLPS(double mCSSLPS) {
		this.mCSSLPS = mCSSLPS;
	}

	public double getmICMSRecoverablePS() {
		return mICMSRecoverablePS;
	}

	public void setmICMSRecoverablePS(double mICMSRecoverablePS) {
		this.mICMSRecoverablePS = mICMSRecoverablePS;
	}

	public double getmICMSMaterialSW() {
		return mICMSMaterialSW;
	}

	public void setmICMSMaterialSW(double mICMSMaterialSW) {
		this.mICMSMaterialSW = mICMSMaterialSW;
	}

	public double getmICMSInterEstadualSW() {
		return mICMSInterEstadualSW;
	}

	public void setmICMSInterEstadualSW(double mICMSInterEstadualSW) {
		this.mICMSInterEstadualSW = mICMSInterEstadualSW;
	}

	public double getmICMSinterestadualMatImportadoSW() {
		return mICMSinterestadualMatImportadoSW;
	}

	public void setmICMSinterestadualMatImportadoSW(
			double mICMSinterestadualMatImportadoSW) {
		this.mICMSinterestadualMatImportadoSW = mICMSinterestadualMatImportadoSW;
	}

	public double getmICMSSTSW() {
		return mICMSSTSW;
	}

	public void setmICMSSTSW(double mICMSSTSW) {
		this.mICMSSTSW = mICMSSTSW;
	}

	public double getmIPISW() {
		return mIPISW;
	}

	public void setmIPISW(double mIPISW) {
		this.mIPISW = mIPISW;
	}

	public double getmISSSW() {
		return mISSSW;
	}

	public void setmISSSW(double mISSSW) {
		this.mISSSW = mISSSW;
	}

	public double getmPISSW() {
		return mPISSW;
	}

	public void setmPISSW(double mPISSW) {
		this.mPISSW = mPISSW;
	}

	public double getmCOFINSSW() {
		return mCOFINSSW;
	}

	public void setmCOFINSSW(double mCOFINSSW) {
		this.mCOFINSSW = mCOFINSSW;
	}

	public double getmIRSW() {
		return mIRSW;
	}

	public void setmIRSW(double mIRSW) {
		this.mIRSW = mIRSW;
	}

	public double getmCSSLSW() {
		return mCSSLSW;
	}

	public void setmCSSLSW(double mCSSLSW) {
		this.mCSSLSW = mCSSLSW;
	}

	public double getmICMSRecoverableSW() {
		return mICMSRecoverableSW;
	}

	public void setmICMSRecoverableSW(double mICMSRecoverableSW) {
		this.mICMSRecoverableSW = mICMSRecoverableSW;
	}

	public double getmICMSMaterialMD() {
		return mICMSMaterialMD;
	}

	public void setmICMSMaterialMD(double mICMSMaterialMD) {
		this.mICMSMaterialMD = mICMSMaterialMD;
	}

	public double getmICMSInterEstadualMD() {
		return mICMSInterEstadualMD;
	}

	public void setmICMSInterEstadualMD(double mICMSInterEstadualMD) {
		this.mICMSInterEstadualMD = mICMSInterEstadualMD;
	}

	public double getmICMSinterestadualMatImportadoMD() {
		return mICMSinterestadualMatImportadoMD;
	}

	public void setmICMSinterestadualMatImportadoMD(
			double mICMSinterestadualMatImportadoMD) {
		this.mICMSinterestadualMatImportadoMD = mICMSinterestadualMatImportadoMD;
	}

	public double getmICMSSTMD() {
		return mICMSSTMD;
	}

	public void setmICMSSTMD(double mICMSSTMD) {
		this.mICMSSTMD = mICMSSTMD;
	}

	public double getmIPIMD() {
		return mIPIMD;
	}

	public void setmIPIMD(double mIPIMD) {
		this.mIPIMD = mIPIMD;
	}

	public double getmISSMD() {
		return mISSMD;
	}

	public void setmISSMD(double mISSMD) {
		this.mISSMD = mISSMD;
	}

	public double getmPISMD() {
		return mPISMD;
	}

	public void setmPISMD(double mPISMD) {
		this.mPISMD = mPISMD;
	}

	public double getmCOFINSMD() {
		return mCOFINSMD;
	}

	public void setmCOFINSMD(double mCOFINSMD) {
		this.mCOFINSMD = mCOFINSMD;
	}

	public double getmIRMD() {
		return mIRMD;
	}

	public void setmIRMD(double mIRMD) {
		this.mIRMD = mIRMD;
	}

	public double getmCSSLMD() {
		return mCSSLMD;
	}

	public void setmCSSLMD(double mCSSLMD) {
		this.mCSSLMD = mCSSLMD;
	}

	public double getmICMSRecoverableMD() {
		return mICMSRecoverableMD;
	}

	public void setmICMSRecoverableMD(double mICMSRecoverableMD) {
		this.mICMSRecoverableMD = mICMSRecoverableMD;
	}

	public double getmICMSMaterialTotal() {
		
		mICMSMaterialTotal = (mICMSMaterialHW + mICMSMaterialCS + mICMSMaterialMD + mICMSMaterialSW + mICMSMaterialPS);
		
		return mICMSMaterialTotal;
	}

	public void setmICMSMaterialTotal(double mICMSMaterialTotal) {
		this.mICMSMaterialTotal = mICMSMaterialTotal;
	}

	public double getmICMSInterEstadualTotal() {
		
		mICMSInterEstadualTotal = (mICMSInterEstadualCS + mICMSInterEstadualHW + mICMSInterEstadualMD + mICMSInterEstadualPS + mICMSInterEstadualSW);
		
		return mICMSInterEstadualTotal;
	}

	public void setmICMSInterEstadualTotal(double mICMSInterEstadualTotal) {
		this.mICMSInterEstadualTotal = mICMSInterEstadualTotal;
	}

	public double getmICMSinterestadualMatImportadoTotal() {
		
		mICMSinterestadualMatImportadoTotal = (mICMSinterestadualMatImportadoCS + mICMSinterestadualMatImportadoHW + mICMSinterestadualMatImportadoMD + mICMSinterestadualMatImportadoPS + mICMSinterestadualMatImportadoSW);
		
		return mICMSinterestadualMatImportadoTotal;
	}

	public void setmICMSinterestadualMatImportadoTotal(
			double mICMSinterestadualMatImportadoTotal) {
		this.mICMSinterestadualMatImportadoTotal = mICMSinterestadualMatImportadoTotal;
	}

	public double getmICMSSTTotal() {
		
		mICMSSTTotal = (mICMSSTCS + mICMSSTHW + mICMSSTMD + mICMSSTPS + mICMSSTSW);
		
		return mICMSSTTotal;
	}

	public void setmICMSSTTotal(double mICMSSTTotal) {
		this.mICMSSTTotal = mICMSSTTotal;
	}

	public double getmIPITotal() {
		
		mIPITotal = (mIPICS + mIPIHW + mIPIMD + mIPIPS + mIPISW);
		
		return mIPITotal;
	}

	public void setmIPITotal(double mIPITotal) {
		this.mIPITotal = mIPITotal;
	}

	public double getmISSTotal() {
		
		mISSTotal = (mISSCS + mISSHW + mISSMD + mISSPS + mISSSW);
		
		return mISSTotal;
	}

	public void setmISSTotal(double mISSTotal) {
		this.mISSTotal = mISSTotal;
	}

	public double getmPISTotal() {
		
		mPISTotal = (mPISCS + mPISHW + mPISMD + mPISPS + mPISSW);
		
		return mPISTotal;
	}

	public void setmPISTotal(double mPISTotal) {
		this.mPISTotal = mPISTotal;
	}

	public double getmCOFINSTotal() {
		
		mCOFINSTotal = (mCOFINSCS + mCOFINSHW + mCOFINSMD + mCOFINSPS + mCOFINSSW);
		
		return mCOFINSTotal;
	}

	public void setmCOFINSTotal(double mCOFINSTotal) {
		this.mCOFINSTotal = mCOFINSTotal;
	}

	public double getmIRTotal() {
		
		mIRTotal = (mIRCS + mIRHW + mIRMD + mIRPS + mIRSW);
		
		return mIRTotal;
	}

	public void setmIRTotal(double mIRTotal) {
		this.mIRTotal = mIRTotal;
	}

	public double getmCSSLTotal() {
		
		mCSSLTotal = (mCSSLCS + mCSSLHW + mCSSLMD + mCSSLPS + mCSSLSW);

		return mCSSLTotal;
	}

	public void setmCSSLTotal(double mCSSLTotal) {
		this.mCSSLTotal = mCSSLTotal;
	}

	public double getmICMSRecoverableTotal() {
		
		mICMSRecoverableTotal = (mICMSRecoverableCS + mICMSRecoverableHW + mICMSRecoverableMD + mICMSRecoverablePS + mICMSRecoverableSW);
		
		return mICMSRecoverableTotal;
	}

	public void setmICMSRecoverableTotal(double mICMSRecoverableTotal) {
		this.mICMSRecoverableTotal = mICMSRecoverableTotal;
	}

	public double getmTotalTaxesHW() {
		return mTotalTaxesHW;
	}

	public void setmTotalTaxesHW(double mTotalTaxesHW) {
		this.mTotalTaxesHW = mTotalTaxesHW;
	}

	public double getmTotalTaxesCS() {
		return mTotalTaxesCS;
	}

	public void setmTotalTaxesCS(double mTotalTaxesCS) {
		this.mTotalTaxesCS = mTotalTaxesCS;
	}

	public double getmTotalTaxesPS() {
		return mTotalTaxesPS;
	}

	public void setmTotalTaxesPS(double mTotalTaxesPS) {
		this.mTotalTaxesPS = mTotalTaxesPS;
	}

	public double getmTotalTaxesSW() {
		return mTotalTaxesSW;
	}

	public void setmTotalTaxesSW(double mTotalTaxesSW) {
		this.mTotalTaxesSW = mTotalTaxesSW;
	}

	public double getmTotalTaxesMD() {
		return mTotalTaxesMD;
	}

	public void setmTotalTaxesMD(double mTotalTaxesMD) {
		this.mTotalTaxesMD = mTotalTaxesMD;
	}

	public double getmTotalTaxesTotal() {
		
		mTotalTaxesTotal = (mTotalTaxesCS + mTotalTaxesHW + mTotalTaxesMD + mTotalTaxesPS + mTotalTaxesSW);
		
		return mTotalTaxesTotal;
	}

	public void setmTotalTaxesTotal(double mTotalTaxesTotal) {
		this.mTotalTaxesTotal = mTotalTaxesTotal;
	}

	public double getmICMSEstadoOriginHW() {
		return mICMSEstadoOriginHW;
	}

	public void setmICMSEstadoOriginHW(double mICMSEstadoOriginHW) {
		this.mICMSEstadoOriginHW = mICMSEstadoOriginHW;
	}

	public double getmICMSEstadoDestinoHW() {
		return mICMSEstadoDestinoHW;
	}

	public void setmICMSEstadoDestinoHW(double mICMSEstadoDestinoHW) {
		this.mICMSEstadoDestinoHW = mICMSEstadoDestinoHW;
	}

	public double getmICMSEstadoOriginCS() {
		return mICMSEstadoOriginCS;
	}

	public void setmICMSEstadoOriginCS(double mICMSEstadoOriginCS) {
		this.mICMSEstadoOriginCS = mICMSEstadoOriginCS;
	}

	public double getmICMSEstadoDestinoCS() {
		return mICMSEstadoDestinoCS;
	}

	public void setmICMSEstadoDestinoCS(double mICMSEstadoDestinoCS) {
		this.mICMSEstadoDestinoCS = mICMSEstadoDestinoCS;
	}

	public double getmICMSEstadoOriginPS() {
		return mICMSEstadoOriginPS;
	}

	public void setmICMSEstadoOriginPS(double mICMSEstadoOriginPS) {
		this.mICMSEstadoOriginPS = mICMSEstadoOriginPS;
	}

	public double getmICMSEstadoDestinoPS() {
		return mICMSEstadoDestinoPS;
	}

	public void setmICMSEstadoDestinoPS(double mICMSEstadoDestinoPS) {
		this.mICMSEstadoDestinoPS = mICMSEstadoDestinoPS;
	}

	public double getmICMSEstadoOriginSW() {
		return mICMSEstadoOriginSW;
	}

	public void setmICMSEstadoOriginSW(double mICMSEstadoOriginSW) {
		this.mICMSEstadoOriginSW = mICMSEstadoOriginSW;
	}

	public double getmICMSEstadoDestinoSW() {
		return mICMSEstadoDestinoSW;
	}

	public void setmICMSEstadoDestinoSW(double mICMSEstadoDestinoSW) {
		this.mICMSEstadoDestinoSW = mICMSEstadoDestinoSW;
	}

	public double getmICMSEstadoOriginMD() {
		return mICMSEstadoOriginMD;
	}

	public void setmICMSEstadoOriginMD(double mICMSEstadoOriginMD) {
		this.mICMSEstadoOriginMD = mICMSEstadoOriginMD;
	}

	public double getmICMSEstadoDestinoMD() {
		return mICMSEstadoDestinoMD;
	}

	public void setmICMSEstadoDestinoMD(double mICMSEstadoDestinoMD) {
		this.mICMSEstadoDestinoMD = mICMSEstadoDestinoMD;
	}

	public double getmICMSEstadoOriginTotal() {
		
		mICMSEstadoOriginTotal = mICMSEstadoOriginHW + mICMSEstadoOriginSW + mICMSEstadoOriginPS + mICMSEstadoOriginCS + mICMSEstadoOriginMD;
		
		return mICMSEstadoOriginTotal;
	}

	public void setmICMSEstadoOriginTotal(double mICMSEstadoOriginTotal) {
		this.mICMSEstadoOriginTotal = mICMSEstadoOriginTotal;
	}

	public double getmICMSEstadoDestinoTotal() {
		
		mICMSEstadoDestinoTotal = mICMSEstadoDestinoHW + mICMSEstadoDestinoSW + mICMSEstadoDestinoPS + mICMSEstadoDestinoCS + mICMSEstadoDestinoMD;
						
		return mICMSEstadoDestinoTotal;
	}

	public void setmICMSEstadoDestinoTotal(double mICMSEstadoDestinoTotal) {
		this.mICMSEstadoDestinoTotal = mICMSEstadoDestinoTotal;
	}

	public boolean isfPIS() {
		return fPIS;
	}

	public void setfPIS(boolean fPIS) {
		this.fPIS = fPIS;
	}

	public boolean isfICMS() {
		return fICMS;
	}

	public void setfICMS(boolean fICMS) {
		this.fICMS = fICMS;
	}

	public boolean isfISS() {
		return fISS;
	}

	public void setfISS(boolean fISS) {
		this.fISS = fISS;
	}

	public boolean isfCOFINS() {
		return fCOFINS;
	}

	public void setfCOFINS(boolean fCOFINS) {
		this.fCOFINS = fCOFINS;
	}

	public boolean isfIPI() {
		return fIPI;
	}

	public void setfIPI(boolean fIPI) {
		this.fIPI = fIPI;
	}

	public boolean isfSt() {
		return fSt;
	}

	public void setfSt(boolean fSt) {
		this.fSt = fSt;
	}
	
	
	
}
