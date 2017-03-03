package br.com.operations.app.quote.to;

public class SomaTotaisTO {
	
	private int position;
	
	private double revenue;
	
	private double netTotal;
	
	private double grossTotal;
	
	private double totalTaxes;
	
	private String texto;
	
	private String style;
	
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public double getNetTotal() {
		return netTotal;
	}

	public void setNetTotal(double netTotal) {
		this.netTotal = netTotal;
	}

	public double getGrossTotal() {
		return grossTotal;
	}

	public void setGrossTotal(double grossTotal) {
		this.grossTotal = grossTotal;
	}

	public double getTotalTaxes() {
		return totalTaxes;
	}

	public void setTotalTaxes(double totalTaxes) {
		this.totalTaxes = totalTaxes;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public String getStyle() {
		
		style = "background: #D3E7FF;";
		
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	

}
