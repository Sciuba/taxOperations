package br.com.operations.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmDailyDollarDAO;
import br.com.operations.entity.TbAdmDailyDollar;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmDailyDollarDAOImpl extends GenericDAO<TbAdmDailyDollar> implements AdmDailyDollarDAO {

	public AdmDailyDollarDAOImpl() {
		super(TbAdmDailyDollar.class);
	}

	@Override
	public List<TbAdmDailyDollar> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmDailyDollar dailyDollar) {
		super.save(dailyDollar);
	}

	@Override
	public void alter(TbAdmDailyDollar dailyDollar) {
		super.update(dailyDollar);
	}

	@Override
	public void delete(TbAdmDailyDollar dailyDollar) {
		Object object = dailyDollar.getId();
		super.delete(object, TbAdmDailyDollar.class);
	}

	@Override
	public TbAdmDailyDollar find(Long id) {
		return super.find(id);
	}

	@Override
	public TbAdmDailyDollar saveReturn(TbAdmDailyDollar dailyDollar) {
		return super.saveReturn(dailyDollar);
	}

	@Override
	public List<TbAdmDailyDollar> listDatesNotRate(String sqlQuery, Date... param) {
		return super.selectListQueryString(sqlQuery, param);
	}

	@Override
	public TbAdmDailyDollar findDollarDate(String namedQuery, Map<String, Object> parameters) {
		return super.findOneResult(namedQuery, parameters);
	}

	@Override
	public List<TbAdmDailyDollar> findOrderBy() {
		
		String sqlQuery = "select daily from TbAdmDailyDollar daily order by daily.dtDollar desc";
		
		return selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public TbAdmDailyDollar simpleQuery(String sqlQuery) {
		return super.selectQueryString(sqlQuery);
	}

	@Override
	public TbAdmDailyDollar simpleQueryFirstResult(String sqlQuery) {
		return selectFirstQueryStringDesc(sqlQuery);
	}

}
