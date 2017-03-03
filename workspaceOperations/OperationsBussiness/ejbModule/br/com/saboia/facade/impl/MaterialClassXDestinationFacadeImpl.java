package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmMaterialClassXDestinationDAO;
import br.com.saboia.entity.TbAdmMaterialClassXDestination;
import br.com.saboia.facade.MaterialClassXDestinationFacade;

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
