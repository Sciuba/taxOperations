package br.com.operations.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.TaxQuoteItemDAO;
import br.com.operations.entity.TbTaxQuoteItem;
import br.com.operations.facade.QuoteItemFacade;

@Stateless
public class QuoteItemFacadeImpl implements QuoteItemFacade {

	@EJB
	private TaxQuoteItemDAO quoteItemDAO;
	
	@Override
	public List<TbTaxQuoteItem> findAll() {
		return quoteItemDAO.findAll();
	}

	@Override
	public List<TbTaxQuoteItem> findOrderBy() {
		return quoteItemDAO.findOrderBy();
	}

	@Override
	public void save(TbTaxQuoteItem taxQuoteItem) {
		quoteItemDAO.save(taxQuoteItem);
	}

	@Override
	public void alter(TbTaxQuoteItem taxQuoteItem) {
		quoteItemDAO.alter(taxQuoteItem);
	}

	@Override
	public void delete(TbTaxQuoteItem taxQuoteItem) {
		quoteItemDAO.delete(taxQuoteItem);
	}

	@Override
	public TbTaxQuoteItem find(Long id) {
		return quoteItemDAO.find(id);
	}

	@Override
	public TbTaxQuoteItem saveReturn(TbTaxQuoteItem taxQuoteItem) {
		return quoteItemDAO.saveReturn(taxQuoteItem);
	}

	@Override
	public List<TbTaxQuoteItem> simpleQuery(Long idQuote) {
		
 		String query = "select distinct item.sGroupNumber from TbTaxQuoteItem item where item.tbTaxQuote.id = "+idQuote;
		
		List<String> listaGroup = quoteItemDAO.simpleStringQuery(query);
		
		List<TbTaxQuoteItem> lista = new ArrayList<>();
		
		for(String group: listaGroup){
			
			TbTaxQuoteItem taxQuoteItem = new TbTaxQuoteItem();
			
			taxQuoteItem.setListaQuotesForGroup(new ArrayList<TbTaxQuoteItem>());
			
			String sqlQuery = "select item from TbTaxQuoteItem item where item.tbTaxQuote.id = "+idQuote+" and item.sGroupNumber = "+group+" order by item.iLineNumber";
			taxQuoteItem.setListaQuotesForGroup(quoteItemDAO.simpleQuery(sqlQuery));
			
			lista.add(taxQuoteItem);			
		}
		
		return lista;
	}
	
	
	public List<TbTaxQuoteItem> findItensByTaxQuote(Long idQuote){
		
		String query = "select item from TbTaxQuoteItem item where item.tbTaxQuote.id = "+idQuote;
		
		return quoteItemDAO.simpleQuery(query);
	}

	@Override
	public void deleteAll(Long idTaxQuote) {
		
		String sqlQuery = "delete item from TbTaxQuoteItem item where item.tbTaxQuote.id = "+idTaxQuote;
		
		quoteItemDAO.deleteAll(sqlQuery);
	}
}
