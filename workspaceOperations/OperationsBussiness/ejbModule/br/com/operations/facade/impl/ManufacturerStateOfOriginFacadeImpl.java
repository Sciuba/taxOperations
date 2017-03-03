package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.AdmManufactureStateOfOriginDAO;
import br.com.operations.entity.TbAdmManufacturerStateOfOrigin;
import br.com.operations.facade.ManufacturerStateOfOriginFacade;


@Stateless
public class ManufacturerStateOfOriginFacadeImpl implements
		ManufacturerStateOfOriginFacade {
	
	@EJB
	private AdmManufactureStateOfOriginDAO stateOfOriginDAO;
	
	@Override
	public List<TbAdmManufacturerStateOfOrigin> findAll() {
		return stateOfOriginDAO.findAll();
	}

	@Override
	public void save(TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin) {
		
		stateOfOriginDAO.save(admManufacturerStateOfOrigin);
		
	}

	@Override
	public void alter(
			TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin) {
		
		stateOfOriginDAO.alter(admManufacturerStateOfOrigin);

	}

	@Override
	public void delete(
			TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin) {
		stateOfOriginDAO.delete(admManufacturerStateOfOrigin);
	}

	@Override
	public TbAdmManufacturerStateOfOrigin find(long id) {
		return stateOfOriginDAO.find(id);
	}

	@Override
	public TbAdmManufacturerStateOfOrigin saveReturn(
			TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin) {
		return stateOfOriginDAO.saveReturn(admManufacturerStateOfOrigin);
	}

}
