package br.com.operations.dao.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmMaterialClassXDestinationDAO;
import br.com.operations.entity.TbAdmMaterialClassXDestination;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmMaterialClassXDestinationDAOImpl extends GenericDAO<TbAdmMaterialClassXDestination>
		implements AdmMaterialClassXDestinationDAO {

	public AdmMaterialClassXDestinationDAOImpl() {
		super(TbAdmMaterialClassXDestination.class);
	}

	@Override
	public List<TbAdmMaterialClassXDestination> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmMaterialClassXDestination classXDestination) {
		super.save(classXDestination);
	}

	@Override
	public void alter(TbAdmMaterialClassXDestination classXDestination) {
		super.update(classXDestination);
	}

	@Override
	public void delete(TbAdmMaterialClassXDestination classXDestination) {
		Object object = classXDestination.getId();
		super.delete(object, TbAdmMaterialClassXDestination.class);
	}

	@Override
	public TbAdmMaterialClassXDestination find(Long id) {
		return super.find(id);
	}

	@Override
	public TbAdmMaterialClassXDestination saveReturn(
			TbAdmMaterialClassXDestination classXDestination) {
		return super.saveReturn(classXDestination);
	}

	@Override
	public TbAdmMaterialClassXDestination findQuery(String namedQuery,
			Map<String, Object> parameters) {
		return super.findOneResult(namedQuery, parameters);
	}

}
