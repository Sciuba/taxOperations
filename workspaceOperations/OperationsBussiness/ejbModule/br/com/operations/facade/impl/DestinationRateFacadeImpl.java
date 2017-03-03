package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.AdmDestinationRateDAO;
import br.com.operations.entity.TbAdmDestinationRate;
import br.com.operations.facade.DestinationRateFacade;

@Stateless
public class DestinationRateFacadeImpl implements DestinationRateFacade {

	@EJB
	private AdmDestinationRateDAO rateDAO;
	
	@Override
	public List<TbAdmDestinationRate> findAll() {
		return rateDAO.findAll();
	}

	@Override
	public void save(TbAdmDestinationRate destinationRate) {
		rateDAO.save(destinationRate);
	}

	@Override
	public void alter(TbAdmDestinationRate destinationRate) {
		rateDAO.alter(destinationRate);
	}

	@Override
	public void delete(TbAdmDestinationRate destinationRate) {
		rateDAO.delete(destinationRate);
	}

	@Override
	public TbAdmDestinationRate find(Long id) {
		return rateDAO.find(id);
	}

}
