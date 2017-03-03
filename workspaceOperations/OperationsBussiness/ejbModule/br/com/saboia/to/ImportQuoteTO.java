package br.com.saboia.to;

import java.util.List;

public class ImportQuoteTO {
	
	private boolean quoteInBRL;
	
	private String quoteName;
	
	private String GrossRevenueTotal;
	
	private String AdrDiscountTotal;
	
	private String totalDiscount;
	
	private String escalation;
	
	private String estimatedSalesCreditTotal;
	
	private String estimatedSalesCreditPercentTotal;
	
	private String netRevenueTotal;
	
	private String sCustomer;
	
	private String fileType;
	
	private List<ImportQuoteItemTO> quoteItens;

	
	
	public String getQuoteName() {
		return quoteName;
	}

	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}

	public String getGrossRevenueTotal() {
		return GrossRevenueTotal;
	}

	public void setGrossRevenueTotal(String grossRevenueTotal) {
		GrossRevenueTotal = grossRevenueTotal;
	}

	public String getAdrDiscountTotal() {
		return AdrDiscountTotal;
	}

	public void setAdrDiscountTotal(String adrDiscountTotal) {
		AdrDiscountTotal = adrDiscountTotal;
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

	public String getEstimatedSalesCreditTotal() {
		return estimatedSalesCreditTotal;
	}

	public void setEstimatedSalesCreditTotal(String estimatedSalesCreditTotal) {
		this.estimatedSalesCreditTotal = estimatedSalesCreditTotal;
	}

	public String getEstimatedSalesCreditPercentTotal() {
		return estimatedSalesCreditPercentTotal;
	}

	public void setEstimatedSalesCreditPercentTotal(
			String estimatedSalesCreditPercentTotal) {
		this.estimatedSalesCreditPercentTotal = estimatedSalesCreditPercentTotal;
	}

	public String getNetRevenueTotal() {
		return netRevenueTotal;
	}

	public void setNetRevenueTotal(String netRevenueTotal) {
		this.netRevenueTotal = netRevenueTotal;
	}

	public List<ImportQuoteItemTO> getQuoteItens() {
		return quoteItens;
	}

	public void setQuoteItens(List<ImportQuoteItemTO> quoteItens) {
		this.quoteItens = quoteItens;
	}

	public String getsCustomer() {
		return sCustomer;
	}

	public void setsCustomer(String sCustomer) {
		this.sCustomer = sCustomer;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public boolean isQuoteInBRL() {
		return quoteInBRL;
	}

	public void setQuoteInBRL(boolean quoteInBRL) {
		this.quoteInBRL = quoteInBRL;
	}

	
	
	
}
