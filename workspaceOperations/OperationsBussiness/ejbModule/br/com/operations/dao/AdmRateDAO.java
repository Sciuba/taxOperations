package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmRate;

@Local
public interface AdmRateDAO {
	
	List<TbAdmRate> findAll();
	
	void save(TbAdmRate rate);
	
	void alter(TbAdmRate rate);
	
	void delete(TbAdmRate rate);
	
	TbAdmRate find(Long id);

	TbAdmRate saveReturn(TbAdmRate rate);

}
