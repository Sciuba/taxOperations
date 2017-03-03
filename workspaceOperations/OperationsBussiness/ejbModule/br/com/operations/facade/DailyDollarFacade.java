package br.com.operations.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmDailyDollar;

@Local
public interface DailyDollarFacade {
	
	List<TbAdmDailyDollar> findAll();
	
	List<TbAdmDailyDollar> findOrderBy();
	
	void save(TbAdmDailyDollar dailyDollar);
	
	void alter(TbAdmDailyDollar dailyDollar);
	
	void delete(TbAdmDailyDollar dailyDollar);
	
	TbAdmDailyDollar find(Long id);

	TbAdmDailyDollar saveReturn(TbAdmDailyDollar dailyDollar);
	
	List<TbAdmDailyDollar> listDatesNotRate(Date data);
	
	boolean findDollarDate(Date date);
	
	TbAdmDailyDollar simpleQuery();
	
	TbAdmDailyDollar simpleQueryActualDollarRate();

}
