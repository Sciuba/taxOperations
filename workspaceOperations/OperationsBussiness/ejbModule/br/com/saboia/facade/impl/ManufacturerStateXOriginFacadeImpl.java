package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmManufacturerStateXTbAdmOriginDAO;
import br.com.saboia.entity.TbAdmManufacturerStateXTbAdmOrigin;
import br.com.saboia.facade.ManufacturerStateXOriginFacade;

@Stateless
public class ManufacturerStateXOriginFacadeImpl implements
		ManufacturerStateXOriginFacade {

	
	@EJB
	private AdmManufacturerStateXTbAdmOriginDAO dao;
	
	
	@Override
	public List<TbAdmManufacturerStateXTbAdmOrigin> findAll() {
		return dao.findAll();
	}

	@Override
	public void save(TbAdmManufacturerStateXTbAdmOrigin stateXTbAdmOrigin) {
		dao.save(stateXTbAdmOrigin);
	}

	@Override
	public void alter(TbAdmManufacturerStateXTbAdmOrigin stateXTbAdmOrigin) {
		dao.alter(stateXTbAdmOrigin);
	}

	@Override
	public void delete(TbAdmManufacturerStateXTbAdmOrigin stateXTbAdmOrigin) {
		dao.delete(stateXTbAdmOrigin);
	}

	@Override
	public TbAdmManufacturerStateXTbAdmOrigin find(Long id) {
		return dao.find(id);
	}

	@Override
	public TbAdmManufacturerStateXTbAdmOrigin saveReturn(
			TbAdmManufacturerStateXTbAdmOrigin stateXTbAdmOrigin) {
		return dao.saveReturn(stateXTbAdmOrigin);
	}

	@Override
	public TbAdmManufacturerStateXTbAdmOrigin findQuery(Object... params) {
		
		return null;
	}

}
