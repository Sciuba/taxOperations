package br.com.saboia.facade.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.TaxQuoteDAO;
import br.com.saboia.entity.TbTaxQuote;
import br.com.saboia.facade.QuoteFacade;

@Stateless
public class QuoteFacadeImpl implements QuoteFacade {

	
	@EJB
	private TaxQuoteDAO quoteDAO;
	
	
	@Override
	public List<TbTaxQuote> findAll() {
		return quoteDAO.findAll();
	}

	@Override
	public List<TbTaxQuote> findOrderBy() {
		return quoteDAO.findOrderBy();
	}

	@Override
	public void save(TbTaxQuote taxQuote) {
		quoteDAO.save(taxQuote);
	}

	@Override
	public void alter(TbTaxQuote taxQuote) {
		quoteDAO.alter(taxQuote);
	}

	@Override
	public void delete(TbTaxQuote taxQuote) {
		quoteDAO.delete(taxQuote);
	}

	@Override
	public TbTaxQuote find(Long id) {
		return quoteDAO.find(id);
	}

	@Override
	public TbTaxQuote saveReturn(TbTaxQuote taxQuote) {
		return quoteDAO.saveReturn(taxQuote);
	}

	@Override
	public List<TbTaxQuote> simpleQuery() {
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		
		gc.set(Calendar.HOUR, gc.getActualMinimum(Calendar.HOUR));  
		gc.set(Calendar.HOUR_OF_DAY, gc.getActualMinimum(Calendar.HOUR_OF_DAY));  
		gc.set(Calendar.MINUTE, gc.getActualMinimum(Calendar.MINUTE));  
		gc.set(Calendar.SECOND, gc.getActualMinimum(Calendar.SECOND));
		gc.set(Calendar.MILLISECOND, gc.getActualMinimum(Calendar.MILLISECOND));
		
		gc.set(Calendar.DAY_OF_MONTH, gc.getActualMinimum(Calendar.DAY_OF_MONTH));
		gc.set(Calendar.MONTH, gc.getActualMinimum(Calendar.MONTH));
		
		Date dataIni = gc.getTime();
		
		gc.set(Calendar.MONTH, gc.getActualMaximum(Calendar.MONTH));
		gc.set(Calendar.DAY_OF_MONTH, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		Date dataFim = gc.getTime();
		
		String sqlQuery = "select quote from TbTaxQuote quote where quote.dtDelete is null and quote.dtCreation between ?1 and ?2";
		
		return quoteDAO.simpleQueryDateParam(sqlQuery, dataIni, dataFim);
	}
	
	

	@Override
	public List<TbTaxQuote> queryParams(Map<String, Object> params) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("select quote from TbTaxQuote quote where ");
		
		if(params.size() == 3){
			
			if(params.get("status").equals("0")){
				
				sb.append("quote.dtCreation between :dataIni and :dataFim");
				
			}else if(params.get("status").equals("1")){
				
				sb.append("quote.dtRelease is null and quote.dtCreation between :dataIni and :dataFim");
				
			}else if(params.get("status").equals("2")){
				
				sb.append("quote.dtClose is null and quote.dtCreation between :dataIni and :dataFim");
				
			}else if(params.get("status").equals("3")){
				
				sb.append("quote.dtRelease is not null and quote.dtClose is null and quote.dtCreation between :dataIni and :dataFim");
				
			}else if(params.get("status").equals("4")){
				
				sb.append("(quote.dtRelease is not null or and quote.dtClose is not null) and quote.dtCreation between :dataIni and :dataFim");
			}
			
			else if(params.get("status").equals("5")){
				
				sb.append("quote.dtRelease is null and quote.dtClose is not null and quote.dtCreation between :dataIni and :dataFim");
			}
			
			params.remove("status");
									
		}else if(params.size() == 2){
			
			sb.append("quote.dtCreation between :dataIni and :dataFim");
			
		}else{
			
			if(params.get("status").equals("0")){
				
				sb.append("quote.dtCreation");
				
			}else if(params.get("status").equals("1")){
				
				sb.append("quote.dtRelease is null");
				
			}else if(params.get("status").equals("2")){
				
				sb.append("quote.dtClose is null");
				
			}else if(params.get("status").equals("3")){
				
				sb.append("quote.dtRelease is not null and quote.dtClose is null");
				
			}else if(params.get("status").equals("4")){
				
				sb.append("quote.dtRelease is not null or and quote.dtClose is not null");
			}
			
			else if(params.get("status").equals("5")){
				
				sb.append("quote.dtRelease is null and quote.dtClose is not null");
			}
			
			params.remove("status");
		}
				
		String sqlQuery = sb.toString();
		
		return quoteDAO.simpleQueryParam(sqlQuery, params);
	}

	@Override
	public List<TbTaxQuote> findAllLazyDataModel(int startingAt, int maxPerPage, Map<String, Object> params, Long id, boolean checkDeleted) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("select quote from TbTaxQuote quote where ");
		
		if(params != null && !params.isEmpty()){
			if(params.size() == 3){
				
				if(params.get("status").equals("0")){
					
					sb.append("quote.dtCreation between :dataIni and :dataFim");
					
				}else if(params.get("status").equals("1")){
					
					sb.append("quote.dtRelease is null and quote.dtCreation between :dataIni and :dataFim");
					
				}else if(params.get("status").equals("2")){
					
					sb.append("quote.dtClose is null and quote.dtCreation between :dataIni and :dataFim");
					
				}else if(params.get("status").equals("3")){
					
					sb.append("quote.dtRelease is not null and quote.dtClose is null and quote.dtCreation between :dataIni and :dataFim");
					
				}else if(params.get("status").equals("4")){
					
					sb.append("(quote.dtRelease is not null or and quote.dtClose is not null) and quote.dtCreation between :dataIni and :dataFim");
				}
				
				else if(params.get("status").equals("5")){
					
					sb.append("quote.dtRelease is null and quote.dtClose is not null and quote.dtCreation between :dataIni and :dataFim");
				}
				
			}else if(params.size() == 2){
				
				sb.append("quote.dtCreation between :dataIni and :dataFim");
				
			}else{
				
				if(params.get("status").equals("0")){
					
					if(checkDeleted){
						
						sb = new StringBuilder();
						sb.append("select quote from TbTaxQuote quote order by quote.dtCreation desc");
					
					}else{
						sb.append(" quote.dtDelete is null order by quote.dtCreation desc");
					}
					
				}else if(params.get("status").equals("1")){
					
					sb.append("quote.dtRelease is null");
					
				}else if(params.get("status").equals("2")){
					
					sb.append("quote.dtClose is null");
					
				}else if(params.get("status").equals("3")){
					
					sb.append("quote.dtRelease is not null and quote.dtClose is null");
					
				}else if(params.get("status").equals("4")){
					
					sb.append("quote.dtRelease is not null or and quote.dtClose is not null");
				}
				
				else if(params.get("status").equals("5")){
					
					sb.append("quote.dtRelease is null and quote.dtClose is not null");
				}
			}
			
			if(params.size() == 1 && !params.get("status").equals("0") && params.size() == 1){
				if(checkDeleted){
					sb = new StringBuilder();
					sb.append("select quote from TbTaxQuote quote order by quote.dtCreation desc");
				}else{
					sb.append(" and quote.dtDelete is null order by quote.dtCreation desc");
				}
			}
			
			if(params.size() == 2 || params.size() == 3){
				if(checkDeleted){
					sb = new StringBuilder();
					sb.append("select quote from TbTaxQuote quote order by quote.dtCreation desc");
				}else{
					sb.append(" and quote.dtDelete is null order by quote.dtCreation desc");
				}
			}
			
			params.remove("status");
			
		}else{
			if(checkDeleted){
				sb = new StringBuilder();
				sb.append("select quote from TbTaxQuote quote order by quote.dtCreation desc");
			}else{
				sb.append(" quote.dtDelete is null order by quote.dtCreation desc");
			}
		}
				
		String sqlQuery = sb.toString();
		
		return quoteDAO.selectListEntityDataTableLazyParams(sqlQuery, startingAt, maxPerPage, params);
	}

	@Override
	public int selectTotalNumberRegistry(Map<String, Object> params, Long id, boolean checkDeleted) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("select count(quote) from TbTaxQuote quote ");
		
		if(params != null && !params.isEmpty()){
			if(params.size() == 3){
				
				if(params.get("status").equals("0")){
					
					sb.append("where quote.dtCreation between :dataIni and :dataFim");
					
				}else if(params.get("status").equals("1")){
					
					sb.append("where quote.dtRelease is null and quote.dtCreation between :dataIni and :dataFim");
					
				}else if(params.get("status").equals("2")){
					
					sb.append("where quote.dtClose is null and quote.dtCreation between :dataIni and :dataFim");
					
				}else if(params.get("status").equals("3")){
					
					sb.append("where quote.dtRelease is not null and quote.dtClose is null and quote.dtCreation between :dataIni and :dataFim");
					
				}else if(params.get("status").equals("4")){
					
					sb.append("where (quote.dtRelease is not null or and quote.dtClose is not null) and quote.dtCreation between :dataIni and :dataFim");
				}
				
				else if(params.get("status").equals("5")){
					
					sb.append("where quote.dtRelease is null and quote.dtClose is not null and quote.dtCreation between :dataIni and :dataFim");
				}
				
			}else if(params.size() == 2){
				
				sb.append("where quote.dtCreation between :dataIni and :dataFim");
				
			}else{
				
				if(params.get("status").equals("0")){
					
					if(!checkDeleted){
						sb.append(" where quote.dtDelete is null ");
					}
					
				}else if(params.get("status").equals("1")){
					
					sb.append(" where quote.dtRelease is null");
					
				}else if(params.get("status").equals("2")){
					
					sb.append(" where quote.dtClose is null");
					
				}else if(params.get("status").equals("3")){
					
					sb.append(" where quote.dtRelease is not null and quote.dtClose is null");
					
				}else if(params.get("status").equals("4")){
					
					sb.append(" where quote.dtRelease is not null or and quote.dtClose is not null");
				}
				
				else if(params.get("status").equals("5")){
					
					sb.append(" where quote.dtRelease is null and quote.dtClose is not null");
				}
			}
			
			if(params.size() == 1 && !params.get("status").equals("0") && params.size() == 1){
				if(checkDeleted){
					sb.append(" and quote.idAdmPerson order by quote.dtCreation desc");
				}else{
					sb.append(" and quote.idAdmPerson and quote.dtDelete is null order by quote.dtCreation desc");					
				}
			}
			
			if(params.size() == 2 || params.size() == 3){
				if(checkDeleted){
					sb.append(" order by quote.dtCreation desc");
				}else{
					sb.append(" and quote.dtDelete is null order by quote.dtCreation desc");
				}
			}
		
		}
		
		return quoteDAO.selectTotalNumberRegistry(sb.toString(), params);
	}

	@Override
	public List<TbTaxQuote> verifyQuoteSameName(String name) {
		
		String sqlQuery = "select quote from TbTaxQuote quote where quote.sQuoteNumber = '"+name+"' and quote.fDelete = false";
		
		return quoteDAO.simpleQuery(sqlQuery);		
	}
	
	@Override
	public List<TbTaxQuote> verifyQuoteSameName(String name, long version) {
		
		String sqlQuery = "select quote from TbTaxQuote quote where quote.sQuoteNumber = '"+name+"' and quote.iQuoteVersion != "+version;
		
		return quoteDAO.simpleQuery(sqlQuery);		
	}
	
}
