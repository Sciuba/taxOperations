package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmMaterialClassXOriginDAO;
import br.com.saboia.entity.TbAdmMaterialClassXOrigin;
import br.com.saboia.facade.MaterialClassXOriginFacade;

@Stateless
public class MaterialClassXOriginFacadeImpl implements
		MaterialClassXOriginFacade {

	@EJB 
	private AdmMaterialClassXOriginDAO classXOriginDAO;
	
	@Override
	public List<TbAdmMaterialClassXOrigin> findAll() {
		return classXOriginDAO.findAll();
	}

	@Override
	public void save(TbAdmMaterialClassXOrigin classXOrigin) {
		classXOriginDAO.save(classXOrigin);
	}

	@Override
	public void alter(TbAdmMaterialClassXOrigin classXOrigin) {
		classXOriginDAO.alter(classXOrigin);
	}

	@Override
	public void delete(TbAdmMaterialClassXOrigin classXOrigin) {
		classXOriginDAO.delete(classXOrigin);
	}

	@Override
	public TbAdmMaterialClassXOrigin find(Long id) {
		return classXOriginDAO.find(id);
	}

	@Override
	public TbAdmMaterialClassXOrigin saveReturn(
			TbAdmMaterialClassXOrigin classXOrigin) {
		return classXOriginDAO.saveReturn(classXOrigin);
	}

	@Override
	public TbAdmMaterialClassXOrigin findQuery(Object... params) {
		return null;
	}

}
