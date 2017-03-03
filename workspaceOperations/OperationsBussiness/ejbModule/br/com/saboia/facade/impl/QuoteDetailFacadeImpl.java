package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.TaxQuoteDetailDAO;
import br.com.saboia.entity.TbTaxQuoteDetail;
import br.com.saboia.facade.QuoteDetailFacade;

@Stateless
public class QuoteDetailFacadeImpl implements QuoteDetailFacade {

	@EJB
	private TaxQuoteDetailDAO quoteDetailDAO;
	
	@Override
	public List<TbTaxQuoteDetail> findAll() {
		return quoteDetailDAO.findAll();
	}

	@Override
	public List<TbTaxQuoteDetail> findOrderBy() {
		return quoteDetailDAO.findOrderBy();
	}

	@Override
	public void save(TbTaxQuoteDetail taxQuoteDetail) {
		quoteDetailDAO.save(taxQuoteDetail);
	}

	@Override
	public void alter(TbTaxQuoteDetail taxQuoteDetail) {
		quoteDetailDAO.alter(taxQuoteDetail);
	}

	@Override
	public void delete(TbTaxQuoteDetail taxQuoteDetail) {
		quoteDetailDAO.delete(taxQuoteDetail);		
	}

	@Override
	public TbTaxQuoteDetail find(Long id) {
		return quoteDetailDAO.find(id);
	}

	@Override
	public TbTaxQuoteDetail saveReturn(TbTaxQuoteDetail taxQuoteDetail) {
		return quoteDetailDAO.saveReturn(taxQuoteDetail);
	}

	@Override
	public List<TbTaxQuoteDetail> simpleQuery() {
		
		String sqlQuery = "";
		
		return quoteDetailDAO.simpleQuery(sqlQuery);
	}

	@Override
	public void deleteAll(Long idTaxQuote) {
		
		String sqlQuery="delete detail from TbTaxQuoteDetail detail where detail.tbTaxQuote.id = "+idTaxQuote;
						  
		quoteDetailDAO.deleteAll(sqlQuery);
	}

}
