package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmDollarRate;

@Local
public interface AdmDollarRateDAO {
	
	List<TbAdmDollarRate> findAll();
	
	List<TbAdmDollarRate> findOrderBy();
	
	void save(TbAdmDollarRate dollarRate);
	
	void alter(TbAdmDollarRate dollarRate);
	
	void delete(TbAdmDollarRate dollarRate);
	
	TbAdmDollarRate find(Long id);

	TbAdmDollarRate saveReturn(TbAdmDollarRate dollarRate);
	
	TbAdmDollarRate findDollarRateByDate(String sqlQuery);
	
	TbAdmDollarRate simpleQuery(String sqlQuery);

}
