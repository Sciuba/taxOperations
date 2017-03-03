package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmDollarRateDAO;
import br.com.operations.entity.TbAdmDollarRate;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmDollarRateDAOImpl extends GenericDAO<TbAdmDollarRate> implements
		AdmDollarRateDAO {

	public AdmDollarRateDAOImpl() {
		super(TbAdmDollarRate.class);
	}

	@Override
	public List<TbAdmDollarRate> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmDollarRate dollarRate) {
		super.save(dollarRate);
	}

	@Override
	public void alter(TbAdmDollarRate dollarRate) {
		super.update(dollarRate);
	}

	@Override
	public void delete(TbAdmDollarRate dollarRate) {
		Object object = dollarRate.getId();
		super.delete(object, TbAdmDollarRate.class);
	}

	@Override
	public TbAdmDollarRate find(Long id) {
		return super.find(id);
	}

	@Override
	public TbAdmDollarRate saveReturn(TbAdmDollarRate dollarRate) {
		return super.saveReturn(dollarRate);
	}

	@Override
	public TbAdmDollarRate findDollarRateByDate(String sqlQuery) {
		return super.selectQueryString(sqlQuery);
	}

	@Override
	public List<TbAdmDollarRate> findOrderBy() {
		
		String sqlQuery = "select dollar from TbAdmDollarRate dollar order by dollar.iMonth, dollar.iYear desc";
		
		return super.selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public TbAdmDollarRate simpleQuery(String sqlQuery) {
		return super.selectFirstQueryStringDesc(sqlQuery);
	}

}
