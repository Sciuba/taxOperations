package br.com.operations.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmDollarRate;

@Local
public interface DollarRateFacade {
	
	List<TbAdmDollarRate> findAll();
	
	List<TbAdmDollarRate> findOrderBy();
	
	void save(TbAdmDollarRate dollarRate);
	
	void alter(TbAdmDollarRate dollarRate);
	
	void delete(TbAdmDollarRate dollarRate);
	
	TbAdmDollarRate find(Long id);

	TbAdmDollarRate saveReturn(TbAdmDollarRate dollarRate);
	
	TbAdmDollarRate findDollarRateByDate(Date date);
	
	TbAdmDollarRate simpleQuery();

}
