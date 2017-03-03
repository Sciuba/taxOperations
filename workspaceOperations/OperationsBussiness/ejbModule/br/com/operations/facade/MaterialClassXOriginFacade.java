package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmMaterialClassXOrigin;

@Local
public interface MaterialClassXOriginFacade {

	List<TbAdmMaterialClassXOrigin> findAll();
	
	void save(TbAdmMaterialClassXOrigin classXOrigin);
	
	void alter(TbAdmMaterialClassXOrigin classXOrigin);
	
	void delete(TbAdmMaterialClassXOrigin classXOrigin);
	
	TbAdmMaterialClassXOrigin find(Long id);

	TbAdmMaterialClassXOrigin saveReturn(TbAdmMaterialClassXOrigin classXOrigin);
	
	TbAdmMaterialClassXOrigin findQuery(Object... params);
}
