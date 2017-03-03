package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.entity.TbTaxQuoteLog;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class TaxQuoteLogDAOImpl extends GenericDAO<TbTaxQuoteLog> {

	public TaxQuoteLogDAOImpl() {
		super(TbTaxQuoteLog.class);
	}

	public List<TbTaxQuoteLog> findAll(){
		return super.findAll();
	}
	
	public TbTaxQuoteLog findById(long id){
		return super.find(id);
	}
	
	public void save(TbTaxQuoteLog taxQuoteLog){
		super.save(taxQuoteLog);
	}
	
	public TbTaxQuoteLog update(TbTaxQuoteLog taxQuoteLog){
		return super.update(taxQuoteLog);
	}
	
	public void delete(TbTaxQuoteLog taxQuoteLog){
		Object object = taxQuoteLog.getId();
		super.delete(object, TbTaxQuoteLog.class);
	}
	
	public List<TbTaxQuoteLog> simpleQueryList(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
}
