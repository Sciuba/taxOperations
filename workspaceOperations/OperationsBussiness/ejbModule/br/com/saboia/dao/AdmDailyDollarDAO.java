package br.com.saboia.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmDailyDollar;

@Local
public interface AdmDailyDollarDAO {
	
	List<TbAdmDailyDollar> findAll();
	
	List<TbAdmDailyDollar> findOrderBy();
	
	void save(TbAdmDailyDollar dailyDollar);
	
	void alter(TbAdmDailyDollar dailyDollar);
	
	void delete(TbAdmDailyDollar dailyDollar);
	
	TbAdmDailyDollar find(Long id);

	TbAdmDailyDollar saveReturn(TbAdmDailyDollar dailyDollar);
	
	List<TbAdmDailyDollar> listDatesNotRate(String sqlQuery, Date... param);
	
	TbAdmDailyDollar findDollarDate(String namedQuery, Map<String, Object> parameters);
	
	TbAdmDailyDollar simpleQuery(String sqlQuery);
	
	TbAdmDailyDollar simpleQueryFirstResult(String sqlQuery);
}
