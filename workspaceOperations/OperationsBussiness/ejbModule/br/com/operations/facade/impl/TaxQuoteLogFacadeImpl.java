package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.impl.TaxQuoteLogDAOImpl;
import br.com.operations.entity.TbTaxQuoteLog;

@Stateless
public class TaxQuoteLogFacadeImpl {

	@EJB
	private TaxQuoteLogDAOImpl logDAOImpl;
	
	public List<TbTaxQuoteLog> findAll(){
		return logDAOImpl.findAll();
	}
	
	public TbTaxQuoteLog find(Long id){
		return logDAOImpl.find(id);
	}
	
	public void save(TbTaxQuoteLog tbTaxQuoteLog){
		logDAOImpl.save(tbTaxQuoteLog);
	}
	
	public TbTaxQuoteLog update(TbTaxQuoteLog taxQuoteLog){
		return logDAOImpl.update(taxQuoteLog);
	}
	
	public void delete(TbTaxQuoteLog tbTaxQuoteLog){
		logDAOImpl.delete(tbTaxQuoteLog);
	}
	
	public List<TbTaxQuoteLog> findOrderBy(Long id){
		
		String sqlQuery = "select log from TbTaxQuoteLog log where log.tbTaxQuote.id = "+id+" order by log.dtCreated asc";
		
		return logDAOImpl.simpleQueryList(sqlQuery);
	}
	
}
