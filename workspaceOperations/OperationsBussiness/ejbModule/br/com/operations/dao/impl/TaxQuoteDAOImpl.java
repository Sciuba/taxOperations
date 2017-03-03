package br.com.operations.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.com.operations.dao.TaxQuoteDAO;
import br.com.operations.entity.TbTaxQuote;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class TaxQuoteDAOImpl extends GenericDAO<TbTaxQuote> implements
		TaxQuoteDAO {

	public TaxQuoteDAOImpl() {
		super(TbTaxQuote.class);		
	}

	@Override
	public List<TbTaxQuote> findAll() {
		return super.findAll();
	}

	@Override
	public List<TbTaxQuote> findOrderBy() {
			
		String sqlQuery = "";
		
		return super.selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public void save(TbTaxQuote taxQuote) {
		super.save(taxQuote);
	}

	@Override
	public void alter(TbTaxQuote taxQuote) {
		super.update(taxQuote);
	}

	@Override
	public void delete(TbTaxQuote taxQuote) {
		Object object = taxQuote.getId();
		super.delete(object, TbTaxQuote.class);
	}

	@Override
	public TbTaxQuote find(Long id) {
		return super.find(id);
	}

	@Override
	public TbTaxQuote saveReturn(TbTaxQuote taxQuote) {
		return super.saveReturn(taxQuote);
	}

	@Override
	public List<TbTaxQuote> simpleQuery(String sqlQuery) {
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	@Override
	public List<TbTaxQuote> simpleQueryDateParam(String sqlQuery, Date... param) {
		return super.selectListQueryString(sqlQuery, param);
	}

	@Override
	public List<TbTaxQuote> simpleQueryParam(String sqlQuery,
			Map<String, Object> params) {
		return super.findListResult(sqlQuery, params);
	}

	@Override
	public List<TbTaxQuote> findAllLazyDataModel(String sqlQuery,
			int startingAt, int maxPerPage) {
		return super.selectListEntityDataTableLazy(sqlQuery, startingAt, maxPerPage);
	}
	
	@Override
	public List<TbTaxQuote> selectListEntityDataTableLazyParams(String sqlQuery,
			int startingAt, int maxPerPage, Map<String, Object> params) {
		return super.selectListEntityDataTableLazyParams(sqlQuery, startingAt, maxPerPage, params);
	}
	
	@Override
	public int selectTotalNumberRegistry(String sqlQuery, Map<String, Object> params) {
		return super.selectTotalNumberRegistryParams(sqlQuery, params);
	}
}
