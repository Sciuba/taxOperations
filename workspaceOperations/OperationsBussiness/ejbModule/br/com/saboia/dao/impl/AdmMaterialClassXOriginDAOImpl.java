package br.com.saboia.dao.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.com.saboia.dao.AdmMaterialClassXOriginDAO;
import br.com.saboia.entity.TbAdmMaterialClassXOrigin;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class AdmMaterialClassXOriginDAOImpl extends GenericDAO<TbAdmMaterialClassXOrigin> implements
		AdmMaterialClassXOriginDAO {

	public AdmMaterialClassXOriginDAOImpl() {
		super(TbAdmMaterialClassXOrigin.class);
	}

	@Override
	public List<TbAdmMaterialClassXOrigin> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmMaterialClassXOrigin classXOrigin) {
		super.save(classXOrigin);
	}

	@Override
	public void alter(TbAdmMaterialClassXOrigin classXOrigin) {
		super.update(classXOrigin);
	}

	@Override
	public void delete(TbAdmMaterialClassXOrigin classXOrigin) {
		Object object = classXOrigin.getId();
		super.delete(object, TbAdmMaterialClassXOrigin.class);
	}

	@Override
	public TbAdmMaterialClassXOrigin find(Long id) {
		return super.find(id);
	}

	@Override
	public TbAdmMaterialClassXOrigin saveReturn(
			TbAdmMaterialClassXOrigin classXOrigin) {
		return super.saveReturn(classXOrigin);
	}

	@Override
	public TbAdmMaterialClassXOrigin findQuery(String namedQuery,
			Map<String, Object> parameters) {
		return super.findOneResult(namedQuery, parameters);
	}

}
