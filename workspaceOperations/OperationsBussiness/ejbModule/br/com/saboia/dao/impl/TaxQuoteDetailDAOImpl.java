package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.dao.TaxQuoteDetailDAO;
import br.com.saboia.entity.TbTaxQuoteDetail;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class TaxQuoteDetailDAOImpl extends GenericDAO<TbTaxQuoteDetail>
		implements TaxQuoteDetailDAO {

	public TaxQuoteDetailDAOImpl() {
		super(TbTaxQuoteDetail.class);		
	}

	@Override
	public List<TbTaxQuoteDetail> findAll() {
		return super.findAll();
	}

	@Override
	public List<TbTaxQuoteDetail> findOrderBy() {
		
		String sqlQuery = "";
		
		return super.selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public void save(TbTaxQuoteDetail taxQuoteDetail) {
		super.save(taxQuoteDetail);
	}

	@Override
	public void alter(TbTaxQuoteDetail taxQuoteDetail) {
		super.update(taxQuoteDetail);
	}

	@Override
	public void delete(TbTaxQuoteDetail taxQuoteDetail) {
		Object object = taxQuoteDetail.getId();
		super.delete(object, TbTaxQuoteDetail.class);
	}

	@Override
	public TbTaxQuoteDetail find(Long id) {
		return super.find(id);
	}

	@Override
	public TbTaxQuoteDetail saveReturn(TbTaxQuoteDetail taxQuoteDetail) {
		return super.saveReturn(taxQuoteDetail);
	}

	@Override
	public List<TbTaxQuoteDetail> simpleQuery(String sqlQuery) {
		return super.selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public void deleteAll(String sqlQuery){
		super.deleteAll(sqlQuery);
	}
}
