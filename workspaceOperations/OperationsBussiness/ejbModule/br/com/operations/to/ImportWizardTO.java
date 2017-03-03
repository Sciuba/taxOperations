package br.com.operations.to;

public class ImportWizardTO {
	
	private int line;
	
	private long idMaterial;
	
	private String material;
	
	private long idMaterialClass;
	
	private String ncm;

	private String group;
	
	private String qty;
	
	private Float icms;
	
	private Double costValue;
	
	private String style;

	
	
	
	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public long getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(long idMaterial) {
		this.idMaterial = idMaterial;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public long getIdMaterialClass() {
		return idMaterialClass;
	}

	public void setIdMaterialClass(long idMaterialClass) {
		this.idMaterialClass = idMaterialClass;
	}

	public String getNcm() {
		
		if(idMaterialClass == 0){
			ncm = "Not avaliable";
		}
		
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public Float getIcms() {
		return icms;
	}

	public void setIcms(Float icms) {
		this.icms = icms;
	}

	public Double getCostValue() {
		return costValue;
	}

	public void setCostValue(Double costValue) {
		this.costValue = costValue;
	}

	public String getStyle() {
		
		if(getNcm().equals("Not avaliable")){
			style = "color: red";
		}
		
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	
	
}
