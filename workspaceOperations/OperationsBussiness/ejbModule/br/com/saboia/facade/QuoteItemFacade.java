package br.com.saboia.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbTaxQuoteItem;

@Local
public interface QuoteItemFacade {
	
	List<TbTaxQuoteItem> findAll();
	
	List<TbTaxQuoteItem> findOrderBy();
	
	void save(TbTaxQuoteItem taxQuoteItem);
	
	void alter(TbTaxQuoteItem taxQuoteItem);
	
	void delete(TbTaxQuoteItem taxQuoteItem);
	
	TbTaxQuoteItem find(Long id);

	TbTaxQuoteItem saveReturn(TbTaxQuoteItem taxQuoteItem);
	
	List<TbTaxQuoteItem> simpleQuery(Long idQuote);
	
	public List<TbTaxQuoteItem> findItensByTaxQuote(Long idQuote);
	
	void deleteAll(Long idTaxQuote);

}
