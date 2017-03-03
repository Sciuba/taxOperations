package br.com.saboia.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbTaxQuoteItem;

@Local
public interface TaxQuoteItemDAO {
	
	List<TbTaxQuoteItem> findAll();
	
	List<TbTaxQuoteItem> findOrderBy();
	
	void save(TbTaxQuoteItem taxQuoteItem);
	
	void alter(TbTaxQuoteItem taxQuoteItem);
	
	void delete(TbTaxQuoteItem taxQuoteItem);
	
	TbTaxQuoteItem find(Long id);

	TbTaxQuoteItem saveReturn(TbTaxQuoteItem taxQuoteItem);
	
	List<TbTaxQuoteItem> simpleQuery(String sqlQuery);
	
	List<String> simpleStringQuery(String sqlQuery);
	
	void deleteAll(String sqlQuery);
}
