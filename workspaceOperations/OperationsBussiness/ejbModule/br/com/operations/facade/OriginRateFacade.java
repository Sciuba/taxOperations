package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmOriginRate;

@Local
public interface OriginRateFacade {

List<TbAdmOriginRate> findAll();
	
	void save(TbAdmOriginRate originRate);
	
	void alter(TbAdmOriginRate originRate);
	
	void delete(TbAdmOriginRate originRate);
	
	TbAdmOriginRate find(Long id);
	
}
