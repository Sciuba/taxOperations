package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.TaxQuoteDiscDAO;
import br.com.saboia.entity.TbTaxQuoteDisc;
import br.com.saboia.facade.QuoteDiscFacade;

@Stateless
public class QuoteDiscFacadeImpl implements QuoteDiscFacade {

	
	@EJB
	private TaxQuoteDiscDAO quoteDiscDAO;
	
	@Override
	public List<TbTaxQuoteDisc> findAll() {
		return quoteDiscDAO.findAll();
	}

	@Override
	public List<TbTaxQuoteDisc> findOrderBy() {
		return quoteDiscDAO.findOrderBy();
	}

	@Override
	public void save(TbTaxQuoteDisc taxQuoteDisc) {
		quoteDiscDAO.save(taxQuoteDisc);
	}

	@Override
	public void alter(TbTaxQuoteDisc taxQuoteDisc) {
		quoteDiscDAO.alter(taxQuoteDisc);
	}

	@Override
	public void delete(TbTaxQuoteDisc taxQuoteDisc) {
		quoteDiscDAO.delete(taxQuoteDisc);
	}

	@Override
	public TbTaxQuoteDisc find(Long id) {
		return quoteDiscDAO.find(id);
	}

	@Override
	public TbTaxQuoteDisc saveReturn(TbTaxQuoteDisc taxQuoteDisc) {
		return quoteDiscDAO.saveReturn(taxQuoteDisc);
	}

	@Override
	public List<TbTaxQuoteDisc> simpleQuery() {
		
		String sqlQuery = "";
		
		return quoteDiscDAO.simpleQuery(sqlQuery);
	}

}
