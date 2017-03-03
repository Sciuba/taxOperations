package br.com.operations.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.operations.entity.TbTaxQuote;

@Local
public interface QuoteFacade {
	
	List<TbTaxQuote> findAll();
	
	List<TbTaxQuote> findOrderBy();
	
	void save(TbTaxQuote taxQuote);
	
	void alter(TbTaxQuote taxQuote);
	
	void delete(TbTaxQuote taxQuote);
	
	TbTaxQuote find(Long id);

	TbTaxQuote saveReturn(TbTaxQuote taxQuote);
	
	List<TbTaxQuote> simpleQuery();
	
	List<TbTaxQuote> queryParams(Map<String, Object> params);
	
	public List<TbTaxQuote> findAllLazyDataModel(int startingAt, int maxPerPage, Map<String, Object> params, Long id, boolean checkDeleted);
	
	int selectTotalNumberRegistry(Map<String, Object> params, Long id, boolean checkDeleted);
	
	List<TbTaxQuote> verifyQuoteSameName(String name);

	List<TbTaxQuote> verifyQuoteSameName(String name, long version);

}
