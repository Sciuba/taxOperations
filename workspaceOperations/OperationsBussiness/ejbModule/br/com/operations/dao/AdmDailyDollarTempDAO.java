package br.com.operations.dao;

import java.util.List;

import br.com.operations.entity.TbAdmDailyDollarTemp;

public interface AdmDailyDollarTempDAO {
	
	List<TbAdmDailyDollarTemp> findAll();
	
	void save(TbAdmDailyDollarTemp temp);
	
	void alter(TbAdmDailyDollarTemp temp);
	
	void delete(TbAdmDailyDollarTemp temp);
	
	TbAdmDailyDollarTemp find(Long id);

	TbAdmDailyDollarTemp saveReturn(TbAdmDailyDollarTemp temp);
	
	void deleteAll(String sqlQuery);

}
