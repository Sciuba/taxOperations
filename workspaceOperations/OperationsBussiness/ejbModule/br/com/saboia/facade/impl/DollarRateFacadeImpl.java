package br.com.saboia.facade.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmDollarRateDAO;
import br.com.saboia.entity.TbAdmDollarRate;
import br.com.saboia.facade.DollarRateFacade;

@Stateless
public class DollarRateFacadeImpl implements DollarRateFacade {

	@EJB
	private AdmDollarRateDAO dollarRateDAO;
	
	@Override
	public List<TbAdmDollarRate> findAll() {
		return dollarRateDAO.findAll();
	}

	@Override
	public void save(TbAdmDollarRate dollarRate) {
		dollarRateDAO.save(dollarRate);
	}

	@Override
	public void alter(TbAdmDollarRate dollarRate) {
		dollarRateDAO.alter(dollarRate);
	}

	@Override
	public void delete(TbAdmDollarRate dollarRate) {
		dollarRateDAO.delete(dollarRate);
	}

	@Override
	public TbAdmDollarRate find(Long id) {
		return dollarRateDAO.find(id);
	}

	@Override
	public TbAdmDollarRate saveReturn(TbAdmDollarRate dollarRate) {
		return dollarRateDAO.saveReturn(dollarRate);
	}

	@Override
	public TbAdmDollarRate findDollarRateByDate(Date date) {
		
		GregorianCalendar gc = new  GregorianCalendar();
		gc.setTime(date);
		
		int month = gc.get(Calendar.MONTH) + 1;
		int year = gc.get(Calendar.YEAR);
		
		String sqlQuery = "select dollarRate from TbAdmDollarRate dollarRate where dollarRate.iMonth ="+ month +" and  dollarRate.iYear = "+year;
		
		return dollarRateDAO.findDollarRateByDate(sqlQuery);
	}

	@Override
	public List<TbAdmDollarRate> findOrderBy() {
		return dollarRateDAO.findOrderBy();
	}

	@Override
	public TbAdmDollarRate simpleQuery() {
		
		String sqlQuery = "select dollarRate from TbAdmDollarRate dollarRate order by dollarRate.id desc";
		
		return dollarRateDAO.simpleQuery(sqlQuery);
	}

}
