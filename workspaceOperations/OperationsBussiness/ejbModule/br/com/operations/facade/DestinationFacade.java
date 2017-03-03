package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmDestination;

@Local
public interface DestinationFacade {
	
	List<TbAdmDestination> findAll();
	
	List<TbAdmDestination> findOrderBy();
	
	void save(TbAdmDestination destination);
	
	void alter(TbAdmDestination destination);
	
	void delete(TbAdmDestination destination);
	
	TbAdmDestination find(Long id);
	
	List<TbAdmDestination> findListDestinationWithProtocol();
	
	TbAdmDestination findByCode(String code);
	
	List<TbAdmDestination> findOrderByCode();
}
