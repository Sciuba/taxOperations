package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.dao.AdmQuoteParametersDAO;
import br.com.saboia.entity.TbAdmQuoteParameters;
import br.com.saboia.jpa.GenericDAO;

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
