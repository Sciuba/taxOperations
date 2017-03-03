package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmDestinationRateDAO;
import br.com.saboia.entity.TbAdmDestinationRate;
import br.com.saboia.facade.DestinationRateFacade;

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
