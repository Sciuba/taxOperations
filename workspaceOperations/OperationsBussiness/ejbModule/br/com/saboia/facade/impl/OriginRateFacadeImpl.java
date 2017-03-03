package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmOriginRateDAO;
import br.com.saboia.entity.TbAdmOriginRate;
import br.com.saboia.facade.OriginRateFacade;

@Stateless
public class OriginRateFacadeImpl implements OriginRateFacade {
	
	@EJB
	private AdmOriginRateDAO rateDAO;
	
	
	@Override
	public List<TbAdmOriginRate> findAll() {
		return rateDAO.findAll();
	}

	@Override
	public void save(TbAdmOriginRate originRate) {
		rateDAO.save(originRate);
		
	}

	@Override
	public void alter(TbAdmOriginRate originRate) {
		rateDAO.alter(originRate);
		
	}

	@Override
	public void delete(TbAdmOriginRate originRate) {
		rateDAO.delete(originRate);
		
	}

	@Override
	public TbAdmOriginRate find(Long id) {
		return rateDAO.find(id);
	}

}
