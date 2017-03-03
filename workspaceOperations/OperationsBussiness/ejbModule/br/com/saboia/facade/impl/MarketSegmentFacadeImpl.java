package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.impl.MarketSegmentDAOImpl;
import br.com.saboia.entity.TbAdmMarketSegment;

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
