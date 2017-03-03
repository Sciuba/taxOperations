package br.com.saboia.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbTaxQuoteDisc;

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
