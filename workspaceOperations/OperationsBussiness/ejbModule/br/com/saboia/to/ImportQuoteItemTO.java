package br.com.saboia.to;

public class ImportQuoteItemTO {
	
	private boolean isSupportLine;
	
	private Float rTax;
	
	private String quoteItemName;
	
	private String line;
	
	private String partLineType;
	
	private String partDescription;
	
	private String namedProductCategory;
	
	private String discCat;
	
	private String lineOfBusiness;
	
	private String qty;
	
	private String unitPrice;
	
	private String listFee;
	
	private String GrossRevenue;
	
	private String ContractualDiscount;
	
	private String AdrDiscount;
	
	private String totalDiscount;
	
	private String escalation;
	
	private String estimatedSalesCredit;
	
	private String estimatedSalesCreditPercent;
	
	private String netRevenue;

	private String sGroupNumber;
	
	private String durationSupport;
	
	private String supportNetPrice;
	
	public String getQuoteItemName() {
		return quoteItemName;
	}

	public void setQuoteItemName(String quoteItemName) {
		this.quoteItemName = quoteItemName;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getPartLineType() {
		return partLineType;
	}

	public void setPartLineType(String partLineType) {
		this.partLineType = partLineType;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public String getNamedProductCategory() {
		return namedProductCategory;
	}

	public void setNamedProductCategory(String namedProductCategory) {
		this.namedProductCategory = namedProductCategory;
	}

	public String getDiscCat() {
		return discCat;
	}

	public void setDiscCat(String discCat) {
		this.discCat = discCat;
	}

	public String getLineOfBusiness() {
		return lineOfBusiness;
	}

	public void setLineOfBusiness(String lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getGrossRevenue() {
		return GrossRevenue;
	}

	public void setGrossRevenue(String grossRevenue) {
		GrossRevenue = grossRevenue;
	}

	public String getContractualDiscount() {
		return ContractualDiscount;
	}

	public void setContractualDiscount(String contractualDiscount) {
		ContractualDiscount = contractualDiscount;
	}

	public String getAdrDiscount() {
		return AdrDiscount;
	}

	public void setAdrDiscount(String adrDiscount) {
		AdrDiscount = adrDiscount;
	}

	public String getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(String totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public String getEscalation() {
		return escalation;
	}

	public void setEscalation(String escalation) {
		this.escalation = escalation;
	}

	public String getEstimatedSalesCredit() {
		return estimatedSalesCredit;
	}

	public void setEstimatedSalesCredit(String estimatedSalesCredit) {
		this.estimatedSalesCredit = estimatedSalesCredit;
	}

	public String getEstimatedSalesCreditPercent() {
		return estimatedSalesCreditPercent;
	}

	public void setEstimatedSalesCreditPercent(String estimatedSalesCreditPercent) {
		this.estimatedSalesCreditPercent = estimatedSalesCreditPercent;
	}

	public String getNetRevenue() {
		return netRevenue;
	}

	public void setNetRevenue(String netRevenue) {
		this.netRevenue = netRevenue;
	}

	public String getsGroupNumber() {
		return sGroupNumber;
	}

	public void setsGroupNumber(String sGroupNumber) {
		this.sGroupNumber = sGroupNumber;
	}

	public String getDurationSupport() {
		
		if(durationSupport != null && !durationSupport.isEmpty()){
			String month = durationSupport.trim().substring(0, 1);
			
			durationSupport = String.valueOf(Integer.valueOf(month) * 12);
		}
		
		return durationSupport;
	}

	public void setDurationSupport(String durationSupport) {
		this.durationSupport = durationSupport;
	}

	public String getSupportNetPrice() {
		return supportNetPrice;
	}

	public void setSupportNetPrice(String supportNetPrice) {
		this.supportNetPrice = supportNetPrice;
	}

	public String getListFee() {
		return listFee;
	}

	public void setListFee(String listFee) {
		this.listFee = listFee;
	}

	public boolean isSupportLine() {
		return isSupportLine;
	}

	public void setSupportLine(boolean isSupportLine) {
		this.isSupportLine = isSupportLine;
	}

	public Float getrTax() {
		return rTax;
	}

	public void setrTax(Float rTax) {
		this.rTax = rTax;
	}

	
	
}
