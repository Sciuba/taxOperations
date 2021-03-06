package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmMaterialClassXDestination;

@Local
public interface MaterialClassXDestinationFacade {
	
	List<TbAdmMaterialClassXDestination> findAll();
	
	void save(TbAdmMaterialClassXDestination classXDestination);
	
	void alter(TbAdmMaterialClassXDestination classXDestination);
	
	void delete(TbAdmMaterialClassXDestination classXDestination);
	
	TbAdmMaterialClassXDestination find(Long id);

	TbAdmMaterialClassXDestination saveReturn(TbAdmMaterialClassXDestination classXDestination);
	
	TbAdmMaterialClassXDestination findQuery(Object... params);

}
