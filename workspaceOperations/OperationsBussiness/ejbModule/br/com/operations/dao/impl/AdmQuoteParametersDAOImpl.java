package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmQuoteParametersDAO;
import br.com.operations.entity.TbAdmQuoteParameters;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmQuoteParametersDAOImpl extends GenericDAO<TbAdmQuoteParameters> implements
		AdmQuoteParametersDAO {

	public AdmQuoteParametersDAOImpl() {
		super(TbAdmQuoteParameters.class);		
	}

	@Override
	public List<TbAdmQuoteParameters> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmQuoteParameters quoteParameters) {
		super.save(quoteParameters);
	}

	@Override
	public void alter(TbAdmQuoteParameters quoteParameters) {
		super.update(quoteParameters);
	}

	@Override
	public void delete(TbAdmQuoteParameters quoteParameters) {
		Object object = quoteParameters.getId();
		super.delete(object, TbAdmQuoteParameters.class);
	}

	@Override
	public TbAdmQuoteParameters find(Long id) {
		return super.find(id);
	}

	@Override
	public TbAdmQuoteParameters saveReturn(TbAdmQuoteParameters quoteParameters) {
		return super.saveReturn(quoteParameters);
	}

}
