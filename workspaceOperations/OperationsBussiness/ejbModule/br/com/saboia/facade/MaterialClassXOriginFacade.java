package br.com.saboia.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmMaterialClassXOrigin;

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
