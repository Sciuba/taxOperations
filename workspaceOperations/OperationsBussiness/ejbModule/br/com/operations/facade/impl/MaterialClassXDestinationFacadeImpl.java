package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.AdmMaterialClassXDestinationDAO;
import br.com.operations.entity.TbAdmMaterialClassXDestination;
import br.com.operations.facade.MaterialClassXDestinationFacade;

@Stateless
public class MaterialClassXDestinationFacadeImpl implements
		MaterialClassXDestinationFacade {

	@EJB
	private AdmMaterialClassXDestinationDAO classXDestinationDAO;
	
	@Override
	public List<TbAdmMaterialClassXDestination> findAll() {
		return classXDestinationDAO.findAll();
	}

	@Override
	public void save(TbAdmMaterialClassXDestination classXDestination) {
		classXDestinationDAO.save(classXDestination);
	}

	@Override
	public void alter(TbAdmMaterialClassXDestination classXDestination) {
		classXDestinationDAO.alter(classXDestination);
	}

	@Override
	public void delete(TbAdmMaterialClassXDestination classXDestination) {
		classXDestinationDAO.delete(classXDestination);
	}

	@Override
	public TbAdmMaterialClassXDestination find(Long id) {
		return classXDestinationDAO.find(id);
	}

	@Override
	public TbAdmMaterialClassXDestination saveReturn(
			TbAdmMaterialClassXDestination classXDestination) {
		return classXDestinationDAO.saveReturn(classXDestination);
	}

	@Override
	public TbAdmMaterialClassXDestination findQuery(Object... params) {
		return null;
	}

}
