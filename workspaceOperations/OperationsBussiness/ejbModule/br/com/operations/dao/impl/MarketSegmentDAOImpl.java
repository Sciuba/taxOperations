package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.entity.TbAdmMarketSegment;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class MarketSegmentDAOImpl extends GenericDAO<TbAdmMarketSegment> {

	public MarketSegmentDAOImpl() {
		super(TbAdmMarketSegment.class);
	}
	
	public List<TbAdmMarketSegment> findAll(){
		return super.findAll();
	}
	
	public void save(TbAdmMarketSegment marketSegment){
		super.save(marketSegment);
	}

	public void alter(TbAdmMarketSegment marketSegment){
		super.update(marketSegment);
	}
	
	public void delete(TbAdmMarketSegment marketSegment){
		Object object = marketSegment.getId();
		super.delete(object, TbAdmMarketSegment.class);
	}
	
	public List<TbAdmMarketSegment> simpleQuery(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
}
