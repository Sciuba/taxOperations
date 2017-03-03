package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmDestinationRate;

@Local
public interface AdmDestinationRateDAO {
	
	List<TbAdmDestinationRate> findAll();
	
	void save(TbAdmDestinationRate destinationRate);
	
	void alter(TbAdmDestinationRate destinationRate);
	
	void delete(TbAdmDestinationRate destinationRate);
	
	TbAdmDestinationRate find(Long id);

}
