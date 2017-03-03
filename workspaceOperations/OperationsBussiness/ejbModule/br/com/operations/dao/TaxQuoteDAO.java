package br.com.operations.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.operations.entity.TbTaxQuote;

@Local
public interface TaxQuoteDAO {
	
	List<TbTaxQuote> findAll();
	
	List<TbTaxQuote> findOrderBy();
	
	void save(TbTaxQuote taxQuote);
	
	void alter(TbTaxQuote taxQuote);
	
	void delete(TbTaxQuote taxQuote);
	
	TbTaxQuote find(Long id);

	TbTaxQuote saveReturn(TbTaxQuote taxQuote);
	
	List<TbTaxQuote> simpleQuery(String sqlQuery);

	List<TbTaxQuote> simpleQueryDateParam(String sqlQuery, Date... param);
	
	List<TbTaxQuote> simpleQueryParam(String sqlQuery, Map<String, Object> params);
	
	public List<TbTaxQuote> findAllLazyDataModel(String sqlQuery, int startingAt, int maxPerPage);
	
	public List<TbTaxQuote> selectListEntityDataTableLazyParams(String sqlQuery, int startingAt, int maxPerPage, Map<String, Object> filters);
	
	int selectTotalNumberRegistry(String sqlQuery, Map<String, Object> params);
	
}
