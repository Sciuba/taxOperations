package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.AdmQuoteParametersDAO;
import br.com.operations.entity.TbAdmQuoteParameters;
import br.com.operations.facade.QuoteParametersFacade;

@Stateless
public class QuoteParametersFacadeImpl implements QuoteParametersFacade {
	
	@EJB
	private AdmQuoteParametersDAO parametersDAO;
	
	@Override
	public List<TbAdmQuoteParameters> findAll() {
		return parametersDAO.findAll();
	}

	@Override
	public void save(TbAdmQuoteParameters quoteParameters) {
		parametersDAO.save(quoteParameters);
		
	}

	@Override
	public void alter(TbAdmQuoteParameters quoteParameters) {
		parametersDAO.alter(quoteParameters);
	}

	@Override
	public void delete(TbAdmQuoteParameters quoteParameters) {
		parametersDAO.delete(quoteParameters);
	}

	@Override
	public TbAdmQuoteParameters find(Long id) {
		return parametersDAO.find(id);
	}

	@Override
	public TbAdmQuoteParameters saveReturn(TbAdmQuoteParameters quoteParameters) {
		return parametersDAO.saveReturn(quoteParameters);
	}

}
