package br.com.operations.dao.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmManufacturerStateXTbAdmOriginDAO;
import br.com.operations.entity.TbAdmManufacturerStateXTbAdmOrigin;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmManufacturerStateXTbAdmOriginDAOImpl extends GenericDAO<TbAdmManufacturerStateXTbAdmOrigin>
		implements AdmManufacturerStateXTbAdmOriginDAO {

	public AdmManufacturerStateXTbAdmOriginDAOImpl() {
		super(TbAdmManufacturerStateXTbAdmOrigin.class);		
	}

	@Override
	public List<TbAdmManufacturerStateXTbAdmOrigin> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmManufacturerStateXTbAdmOrigin stateXTbAdmOrigin) {
		super.save(stateXTbAdmOrigin);
	}

	@Override
	public void alter(TbAdmManufacturerStateXTbAdmOrigin stateXTbAdmOrigin) {
		super.update(stateXTbAdmOrigin);
	}

	@Override
	public void delete(TbAdmManufacturerStateXTbAdmOrigin stateXTbAdmOrigin) {
		Object object = stateXTbAdmOrigin.getId();
		super.delete(object, TbAdmManufacturerStateXTbAdmOrigin.class);

	}

	@Override
	public TbAdmManufacturerStateXTbAdmOrigin find(Long id) {
		return super.find(id);
	}

	@Override
	public TbAdmManufacturerStateXTbAdmOrigin saveReturn(
			TbAdmManufacturerStateXTbAdmOrigin stateXTbAdmOrigin) {
		return super.saveReturn(stateXTbAdmOrigin);
	}

	@Override
	public TbAdmManufacturerStateXTbAdmOrigin findQuery(String namedQuery,
			Map<String, Object> parameters) {
		return super.findOneResult(namedQuery, parameters);
	}

}
