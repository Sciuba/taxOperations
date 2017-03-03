package br.com.saboia.to;

public class OriginXProductTypeSVTO {
	
	private long id;
	
	private long idOrigin;
	
	private long idProductType;
	
	private float rIss;
	
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdOrigin() {
		return idOrigin;
	}

	public void setIdOrigin(long idOrigin) {
		this.idOrigin = idOrigin;
	}

	public long getIdProductType() {
		return idProductType;
	}

	public void setIdProductType(long idProductType) {
		this.idProductType = idProductType;
	}

	public float getrIss() {
		return rIss;
	}

	public void setrIss(float rIss) {
		this.rIss = rIss;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
