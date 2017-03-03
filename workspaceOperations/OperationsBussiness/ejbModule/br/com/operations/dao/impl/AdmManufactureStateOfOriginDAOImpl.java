package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmManufactureStateOfOriginDAO;
import br.com.operations.entity.TbAdmManufacturerStateOfOrigin;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmManufactureStateOfOriginDAOImpl extends GenericDAO<TbAdmManufacturerStateOfOrigin> implements
		AdmManufactureStateOfOriginDAO {

	public AdmManufactureStateOfOriginDAOImpl() {
		super(TbAdmManufacturerStateOfOrigin.class);
		
	}

	@Override
	public List<TbAdmManufacturerStateOfOrigin> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin) {
		super.save(admManufacturerStateOfOrigin);
	}

	@Override
	public void delete(TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin) {
		Object object = admManufacturerStateOfOrigin.getId();
		super.delete(object, TbAdmManufacturerStateOfOrigin.class);
	}

	@Override
	public void alter(
			TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin) {		
		super.update(admManufacturerStateOfOrigin);
	}
	
	@Override
	public TbAdmManufacturerStateOfOrigin find(long id){
		return super.find(id);		
	}

	@Override
	public TbAdmManufacturerStateOfOrigin saveReturn(
			TbAdmManufacturerStateOfOrigin entity) {
		return super.saveReturn(entity);
	}
}
