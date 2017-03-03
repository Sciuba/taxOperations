package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmOriginRate;

@Local
public interface AdmOriginRateDAO {
	
	List<TbAdmOriginRate> findAll();
	
	void save(TbAdmOriginRate originRate);
	
	void alter(TbAdmOriginRate originRate);
	
	void delete(TbAdmOriginRate originRate);
	
	TbAdmOriginRate find(Long id);

}
