package br.com.saboia.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbTaxQuoteDetail;

@Local
public interface TaxQuoteDetailDAO {
	
	List<TbTaxQuoteDetail> findAll();
	
	List<TbTaxQuoteDetail> findOrderBy();
	
	void save(TbTaxQuoteDetail taxQuoteDetail);
	
	void alter(TbTaxQuoteDetail taxQuoteDetail);
	
	void delete(TbTaxQuoteDetail taxQuoteDetail);
	
	TbTaxQuoteDetail find(Long id);

	TbTaxQuoteDetail saveReturn(TbTaxQuoteDetail taxQuoteDetail);
	
	List<TbTaxQuoteDetail> simpleQuery(String sqlQuery);

	void deleteAll(String sqlQuery);
}
