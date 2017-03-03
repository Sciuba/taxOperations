package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmOrigin;

@Local
public interface OriginFacade {
	
	List<TbAdmOrigin> findAll();
	
	List<TbAdmOrigin> findOrderBy();
	
	void save(TbAdmOrigin origin);
	
	void alter(TbAdmOrigin origin);
	
	void delete(TbAdmOrigin origin);
	
	TbAdmOrigin find(Long id);
	
	TbAdmOrigin saveReturn(TbAdmOrigin origin);
	
	TbAdmOrigin findByCode(String code);
}
