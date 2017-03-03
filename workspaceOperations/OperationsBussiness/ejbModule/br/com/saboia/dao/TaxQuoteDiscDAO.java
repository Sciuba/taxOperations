package br.com.saboia.dao;

import java.util.List;

import br.com.saboia.entity.TbTaxQuoteDisc;

public interface TaxQuoteDiscDAO {
	
	List<TbTaxQuoteDisc> findAll();
	
	List<TbTaxQuoteDisc> findOrderBy();
	
	void save(TbTaxQuoteDisc taxQuoteDisc);
	
	void alter(TbTaxQuoteDisc taxQuoteDisc);
	
	void delete(TbTaxQuoteDisc taxQuoteDisc);
	
	TbTaxQuoteDisc find(Long id);

	TbTaxQuoteDisc saveReturn(TbTaxQuoteDisc taxQuoteDisc);
	
	List<TbTaxQuoteDisc> simpleQuery(String sqlQuery);

}
