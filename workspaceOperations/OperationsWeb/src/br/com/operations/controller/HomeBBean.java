package br.com.operations.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.operations.entity.TbAdmDailyDollar;
import br.com.operations.entity.TbAdmDollarRate;
import br.com.operations.facade.DailyDollarFacade;
import br.com.operations.facade.DollarRateFacade;

@ManagedBean(name = "homeBBean")
@ViewScoped
public class HomeBBean {
	
	@EJB
	private DollarRateFacade dollarRateFacade;
	
	@EJB
	private DailyDollarFacade dailyDollarFacade;
	
	private TbAdmDollarRate admDollarRate;
	
	private TbAdmDailyDollar admDailyDollar;

	private Date dataTaxaDollar;
	
	private Date dataDailyDollar;
	
	@PostConstruct
	public void init(){
		
		
		admDollarRate = dollarRateFacade.simpleQuery();
		
		admDailyDollar = dailyDollarFacade.simpleQueryActualDollarRate();
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.MONTH, admDollarRate.getiMonth()-1);
		gc.set(Calendar.YEAR, admDollarRate.getiYear());
		
		dataTaxaDollar = gc.getTime();
		
		dataDailyDollar = admDailyDollar.getdtDollar();
	}	
		
	public TbAdmDollarRate getAdmDollarRate() {
		return admDollarRate;
	}

	public void setAdmDollarRate(TbAdmDollarRate admDollarRate) {
		this.admDollarRate = admDollarRate;
	}


	public Date getDataTaxaDollar() {
		return dataTaxaDollar;
	}


	public void setDataTaxaDollar(Date dataTaxaDollar) {
		this.dataTaxaDollar = dataTaxaDollar;
	}

	public TbAdmDailyDollar getAdmDailyDollar() {
		return admDailyDollar;
	}

	public void setAdmDailyDollar(TbAdmDailyDollar admDailyDollar) {
		this.admDailyDollar = admDailyDollar;
	}

	public Date getDataDailyDollar() {
		return dataDailyDollar;
	}

	public void setDataDailyDollar(Date dataDailyDollar) {
		this.dataDailyDollar = dataDailyDollar;
	}
	
	
}
