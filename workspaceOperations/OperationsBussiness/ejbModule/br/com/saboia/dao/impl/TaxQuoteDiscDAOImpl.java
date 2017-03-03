package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.dao.TaxQuoteDiscDAO;
import br.com.saboia.entity.TbTaxQuoteDisc;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class TaxQuoteDiscDAOImpl extends GenericDAO<TbTaxQuoteDisc> implements
		TaxQuoteDiscDAO {

	public TaxQuoteDiscDAOImpl() {
		super(TbTaxQuoteDisc.class);		
	}

	@Override
	public List<TbTaxQuoteDisc> findAll() {
		return super.findAll();
	}

	@Override
	public List<TbTaxQuoteDisc> findOrderBy() {
		
		String sqlQuery = "";
		
		return super.selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public void save(TbTaxQuoteDisc taxQuoteDisc) {
		super.save(taxQuoteDisc);
	}

	@Override
	public void alter(TbTaxQuoteDisc taxQuoteDisc) {
		super.update(taxQuoteDisc);
	}

	@Override
	public void delete(TbTaxQuoteDisc taxQuoteDisc) {
		Object object = taxQuoteDisc.getId();
		super.delete(object, TbTaxQuoteDisc.class);
	}

	@Override
	public TbTaxQuoteDisc find(Long id) {
		return super.find(id);
	}

	@Override
	public TbTaxQuoteDisc saveReturn(TbTaxQuoteDisc taxQuoteDisc) {
		return super.saveReturn(taxQuoteDisc);
	}

	@Override
	public List<TbTaxQuoteDisc> simpleQuery(String sqlQuery) {
		return super.selectListSimpleQueryString(sqlQuery);
	}

}
