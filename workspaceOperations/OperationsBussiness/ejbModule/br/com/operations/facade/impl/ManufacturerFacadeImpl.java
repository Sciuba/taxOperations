package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.AdmManufactureDAO;
import br.com.operations.entity.TbAdmMaterialManufacturer;
import br.com.operations.facade.ManufacturerFacade;

@Stateless
public class ManufacturerFacadeImpl implements ManufacturerFacade {

	@EJB
	private AdmManufactureDAO manufactureDAO;	
	
		
	/**
	 * Métodos
	 */
	
	@Override
	public List<TbAdmMaterialManufacturer> findAll() {
		return manufactureDAO.findAll();
	}

	@Override
	public void save(TbAdmMaterialManufacturer admManufactureSeleconado) {
		
		manufactureDAO.save(admManufactureSeleconado);
		
	}

	@Override
	public void alter(TbAdmMaterialManufacturer admManufacturer) {
		manufactureDAO.alter(admManufacturer);

	}

	@Override
	public void delete(TbAdmMaterialManufacturer admManufactureSeleconado) {
		manufactureDAO.delete(admManufactureSeleconado);
	}

	@Override
	public TbAdmMaterialManufacturer find(Long id) {
		return manufactureDAO.find(id);
	}

}
