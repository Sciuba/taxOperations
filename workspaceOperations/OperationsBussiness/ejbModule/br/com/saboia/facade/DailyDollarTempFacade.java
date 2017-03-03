package br.com.saboia.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmDailyDollarTemp;

@Local
public interface DailyDollarTempFacade {
	
	List<TbAdmDailyDollarTemp> findAll();
	
	void save(TbAdmDailyDollarTemp temp);
	
	void alter(TbAdmDailyDollarTemp temp);
	
	void delete(TbAdmDailyDollarTemp temp);
	
	TbAdmDailyDollarTemp find(Long id);

	TbAdmDailyDollarTemp saveReturn(TbAdmDailyDollarTemp temp);
	
	void deleteAll();

}
