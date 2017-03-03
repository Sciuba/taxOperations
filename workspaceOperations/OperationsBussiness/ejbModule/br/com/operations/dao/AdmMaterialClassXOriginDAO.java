package br.com.operations.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmMaterialClassXOrigin;

@Local
public interface AdmMaterialClassXOriginDAO {
	
	List<TbAdmMaterialClassXOrigin> findAll();
	
	void save(TbAdmMaterialClassXOrigin classXOrigin);
	
	void alter(TbAdmMaterialClassXOrigin classXOrigin);
	
	void delete(TbAdmMaterialClassXOrigin classXOrigin);
	
	TbAdmMaterialClassXOrigin find(Long id);

	TbAdmMaterialClassXOrigin saveReturn(TbAdmMaterialClassXOrigin classXOrigin);
	
	TbAdmMaterialClassXOrigin findQuery(String namedQuery, Map<String, Object> parameters);
}
