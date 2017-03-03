package br.com.saboia.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmDestinationRate;

@Local
public interface DestinationRateFacade {

	List<TbAdmDestinationRate> findAll();
	
	void save(TbAdmDestinationRate destinationRate);
	
	void alter(TbAdmDestinationRate destinationRate);
	
	void delete(TbAdmDestinationRate destinationRate);
	
	TbAdmDestinationRate find(Long id);
	
}
