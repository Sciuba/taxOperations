package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.impl.MarketSegmentDAOImpl;
import br.com.operations.entity.TbAdmMarketSegment;

@Stateless
public class MarketSegmentFacadeImpl {

	@EJB
	private MarketSegmentDAOImpl marketSegmentDAOImpl;
	
	public List<TbAdmMarketSegment> findAll(){
		return marketSegmentDAOImpl.findAll();
	}
	
	public void save(TbAdmMarketSegment marketSegment){
		marketSegmentDAOImpl.save(marketSegment);
	}

	public void alter(TbAdmMarketSegment marketSegment){
		marketSegmentDAOImpl.alter(marketSegment);
	}
	
	public void delete(TbAdmMarketSegment marketSegment){
		marketSegmentDAOImpl.delete(marketSegment);
	}
	
	public List<TbAdmMarketSegment> simpleQuery(String sqlQuery){
		
		
		
		return marketSegmentDAOImpl.simpleQuery(sqlQuery);
	}
	
}
