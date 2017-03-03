package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbTaxQuoteDetail;

@Local
public interface QuoteDetailFacade {
	
	List<TbTaxQuoteDetail> findAll();
	
	List<TbTaxQuoteDetail> findOrderBy();
	
	void save(TbTaxQuoteDetail taxQuoteDetail);
	
	void alter(TbTaxQuoteDetail taxQuoteDetail);
	
	void delete(TbTaxQuoteDetail taxQuoteDetail);
	
	TbTaxQuoteDetail find(Long id);

	TbTaxQuoteDetail saveReturn(TbTaxQuoteDetail taxQuoteDetail);
	
	List<TbTaxQuoteDetail> simpleQuery();
	
	void deleteAll(Long idTaxQuote);
	
}
