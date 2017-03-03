package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.dao.TaxQuoteItemDAO;
import br.com.operations.entity.TbTaxQuoteItem;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class TaxQuoteItemDAOImpl extends GenericDAO<TbTaxQuoteItem> implements
		TaxQuoteItemDAO {

	public TaxQuoteItemDAOImpl() {
		super(TbTaxQuoteItem.class);
	}

	@Override
	public List<TbTaxQuoteItem> findAll() {
		return super.findAll();
	}

	@Override
	public List<TbTaxQuoteItem> findOrderBy() {
		
		String sqlQuery = "";
		
		return super.selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public void save(TbTaxQuoteItem taxQuoteItem) {
		super.save(taxQuoteItem);
	}

	@Override
	public void alter(TbTaxQuoteItem taxQuoteItem) {
		super.update(taxQuoteItem);
	}

	@Override
	public void delete(TbTaxQuoteItem taxQuoteItem) {
		Object object = taxQuoteItem.getId();
		super.delete(object, TbTaxQuoteItem.class);
	}

	@Override
	public TbTaxQuoteItem find(Long id) {
		return super.find(id);
	}

	@Override
	public TbTaxQuoteItem saveReturn(TbTaxQuoteItem taxQuoteItem) {
		return super.saveReturn(taxQuoteItem);
	}

	@Override
	public List<TbTaxQuoteItem> simpleQuery(String sqlQuery) {
		return super.selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public List<String> simpleStringQuery(String sqlQuery) {
		return super.selectListStringSimpleQueryString(sqlQuery);
	}
	
	@Override
	public void deleteAll(String sqlQuery){
		super.deleteAll(sqlQuery);
	}

}
