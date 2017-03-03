package br.com.saboia.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;


/**
 * The persistent class for the TBTAXQUOTEDETAIL database table.
 * 
 */
@Entity
@NamedQuery(name="TbTaxQuoteDetail.findAll", query="SELECT t FROM TbTaxQuoteDetail t")
public class TbTaxQuoteDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBTAXQUOTEDETAIL_ID_GENERATOR", sequenceName="SEQ_TBTAXQUOTEDETAIL", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBTAXQUOTEDETAIL_ID_GENERATOR")
	private long id;

	private Double rDollarRate;

	private String sAddress;

	private String sAddress2;

	private String sAddress2Bill;

	private String sAddress2Ship;

	private String sAddressBill;
	
	private String sAddressShip;

	private String sBillTo;

	private String sCity;

	private String sCityBill;

	private String sCityShip;

	private String sCnpj;

	private String sCnpjBill;

	private String sCnpjShip;

	private String sContactBill;

	private String sContactPerson;

	private String sContactShip;

	private String sEmail;

	private String sEmailBill;

	private String sEmailShip;

	private String sIe;

	private String sIeBill;

	private String sIeShip;

	private String sInvoicing;

	private String sPayment;

	private String sPhone;

	private String sPhoneBill;

	private String sPhoneShip;

	private String sPoNumber;

	private String sShipTo;

	private String sSoNumber;

	private String sState;

	private String sStateBill;

	private String sStateShip;

	private String sZipCode;

	private String sZipCodeBill;

	private String sZipCodeShip;

	//bi-directional many-to-one association to TbTaxQuote
	@OneToOne
	@JoinColumn(name="IDTAXQUOTE")
	private TbTaxQuote tbTaxQuote;

	public TbTaxQuoteDetail() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getrDollarRate() {
		return rDollarRate;
	}

	public void setrDollarRate(Double rDollarRate) {
		this.rDollarRate = rDollarRate;
	}

	public String getsAddress() {
		return sAddress;
	}

	public void setsAddress(String sAddress) {
		this.sAddress = sAddress;
	}

	public String getsAddress2() {
		return sAddress2;
	}

	public void setsAddress2(String sAddress2) {
		this.sAddress2 = sAddress2;
	}

	public String getsAddress2Bill() {
		return sAddress2Bill;
	}

	public void setsAddress2Bill(String sAddress2Bill) {
		this.sAddress2Bill = sAddress2Bill;
	}

	public String getsAddress2Ship() {
		return sAddress2Ship;
	}

	public void setsAddress2Ship(String sAddress2Ship) {
		this.sAddress2Ship = sAddress2Ship;
	}

	public String getsAddressBill() {
		return sAddressBill;
	}

	public void setsAddressBill(String sAddressBill) {
		this.sAddressBill = sAddressBill;
	}

	public String getsAddressShip() {
		return sAddressShip;
	}

	public void setsAddressShip(String sAddressShip) {
		this.sAddressShip = sAddressShip;
	}

	public String getsBillTo() {
		return sBillTo;
	}

	public void setsBillTo(String sBillTo) {
		this.sBillTo = sBillTo;
	}

	public String getsCity() {
		return sCity;
	}

	public void setsCity(String sCity) {
		this.sCity = sCity;
	}

	public String getsCityBill() {
		return sCityBill;
	}

	public void setsCityBill(String sCityBill) {
		this.sCityBill = sCityBill;
	}

	public String getsCityShip() {
		return sCityShip;
	}

	public void setsCityShip(String sCityShip) {
		this.sCityShip = sCityShip;
	}

	public String getsCnpj() {
		return sCnpj;
	}

	public void setsCnpj(String sCnpj) {
		this.sCnpj = sCnpj;
	}

	public String getsCnpjBill() {
		return sCnpjBill;
	}

	public void setsCnpjBill(String sCnpjBill) {
		this.sCnpjBill = sCnpjBill;
	}

	public String getsCnpjShip() {
		return sCnpjShip;
	}

	public void setsCnpjShip(String sCnpjShip) {
		this.sCnpjShip = sCnpjShip;
	}

	public String getsContactBill() {
		return sContactBill;
	}

	public void setsContactBill(String sContactBill) {
		this.sContactBill = sContactBill;
	}

	public String getsContactPerson() {
		return sContactPerson;
	}

	public void setsContactPerson(String sContactPerson) {
		this.sContactPerson = sContactPerson;
	}

	public String getsContactShip() {
		return sContactShip;
	}

	public void setsContactShip(String sContactShip) {
		this.sContactShip = sContactShip;
	}

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public String getsEmailBill() {
		return sEmailBill;
	}

	public void setsEmailBill(String sEmailBill) {
		this.sEmailBill = sEmailBill;
	}

	public String getsEmailShip() {
		return sEmailShip;
	}

	public void setsEmailShip(String sEmailShip) {
		this.sEmailShip = sEmailShip;
	}

	public String getsIe() {
		return sIe;
	}

	public void setsIe(String sIe) {
		this.sIe = sIe;
	}

	public String getsIeBill() {
		return sIeBill;
	}

	public void setsIeBill(String sIeBill) {
		this.sIeBill = sIeBill;
	}

	public String getsIeShip() {
		return sIeShip;
	}

	public void setsIeShip(String sIeShip) {
		this.sIeShip = sIeShip;
	}

	public String getsInvoicing() {
		return sInvoicing;
	}

	public void setsInvoicing(String sInvoicing) {
		this.sInvoicing = sInvoicing;
	}

	public String getsPayment() {
		return sPayment;
	}

	public void setsPayment(String sPayment) {
		this.sPayment = sPayment;
	}

	public String getsPhone() {
		return sPhone;
	}

	public void setsPhone(String sPhone) {
		this.sPhone = sPhone;
	}

	public String getsPhoneBill() {
		return sPhoneBill;
	}

	public void setsPhoneBill(String sPhoneBill) {
		this.sPhoneBill = sPhoneBill;
	}

	public String getsPhoneShip() {
		return sPhoneShip;
	}

	public void setsPhoneShip(String sPhoneShip) {
		this.sPhoneShip = sPhoneShip;
	}

	public String getsPoNumber() {
		return sPoNumber;
	}

	public void setsPoNumber(String sPoNumber) {
		this.sPoNumber = sPoNumber;
	}

	public String getsShipTo() {
		return sShipTo;
	}

	public void setsShipTo(String sShipTo) {
		this.sShipTo = sShipTo;
	}

	public String getsSoNumber() {
		return sSoNumber;
	}

	public void setsSoNumber(String sSoNumber) {
		this.sSoNumber = sSoNumber;
	}

	public String getsState() {
		return sState;
	}

	public void setsState(String sState) {
		this.sState = sState;
	}

	public String getsStateBill() {
		return sStateBill;
	}

	public void setsStateBill(String sStateBill) {
		this.sStateBill = sStateBill;
	}

	public String getsStateShip() {
		return sStateShip;
	}

	public void setsStateShip(String sStateShip) {
		this.sStateShip = sStateShip;
	}

	public String getsZipCode() {
		return sZipCode;
	}

	public void setsZipCode(String sZipCode) {
		this.sZipCode = sZipCode;
	}

	public String getsZipCodeBill() {
		return sZipCodeBill;
	}

	public void setsZipCodeBill(String sZipCodeBill) {
		this.sZipCodeBill = sZipCodeBill;
	}

	public String getsZipCodeShip() {
		return sZipCodeShip;
	}

	public void setsZipCodeShip(String sZipCodeShip) {
		this.sZipCodeShip = sZipCodeShip;
	}

	public TbTaxQuote getTbTaxQuote() {
		return tbTaxQuote;
	}

	public void setTbTaxQuote(TbTaxQuote tbTaxQuote) {
		this.tbTaxQuote = tbTaxQuote;
	}

	

	

	

}