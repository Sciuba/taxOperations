package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbTaxQuoteDisc;

@Local
public interface QuoteDiscFacade {
	
	List<TbTaxQuoteDisc> findAll();
	
	List<TbTaxQuoteDisc> findOrderBy();
	
	void save(TbTaxQuoteDisc taxQuoteDisc);
	
	void alter(TbTaxQuoteDisc taxQuoteDisc);
	
	void delete(TbTaxQuoteDisc taxQuoteDisc);
	
	TbTaxQuoteDisc find(Long id);

	TbTaxQuoteDisc saveReturn(TbTaxQuoteDisc taxQuoteDisc);
	
	List<TbTaxQuoteDisc> simpleQuery();

}
