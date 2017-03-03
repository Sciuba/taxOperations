package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.AdmRateDAO;
import br.com.operations.entity.TbAdmRate;
import br.com.operations.facade.RateFacade;

@Stateless
public class RateFacadeImpl implements RateFacade {

	
	@EJB
	private AdmRateDAO rateDAO;
	
	@Override
	public List<TbAdmRate> findAll() {
		return rateDAO.findAll();
	}

	@Override
	public void save(TbAdmRate rate) {
		rateDAO.save(rate);
	}

	@Override
	public void alter(TbAdmRate rate) {
		rateDAO.alter(rate);
	}

	@Override
	public void delete(TbAdmRate rate) {
		rateDAO.delete(rate);
	}

	@Override
	public TbAdmRate find(Long id) {
		return rateDAO.find(id);
	}

	@Override
	public TbAdmRate saveReturn(TbAdmRate rate) {
		return rateDAO.saveReturn(rate);
	}

}
