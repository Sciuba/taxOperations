package br.com.saboia.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmMaterialClassXDestination;

@Local
public interface AdmMaterialClassXDestinationDAO {
	
	List<TbAdmMaterialClassXDestination> findAll();
	
	void save(TbAdmMaterialClassXDestination classXDestination);
	
	void alter(TbAdmMaterialClassXDestination classXDestination);
	
	void delete(TbAdmMaterialClassXDestination classXDestination);
	
	TbAdmMaterialClassXDestination find(Long id);

	TbAdmMaterialClassXDestination saveReturn(TbAdmMaterialClassXDestination classXDestination);
	
	TbAdmMaterialClassXDestination findQuery(String namedQuery, Map<String, Object> parameters);
}
