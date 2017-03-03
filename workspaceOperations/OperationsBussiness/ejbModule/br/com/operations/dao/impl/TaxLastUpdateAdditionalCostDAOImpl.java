package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.dao.TaxLastUpdateAdditionalCostDAO;
import br.com.operations.entity.TbTaxLastUpdateAdditionalCost;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class TaxLastUpdateAdditionalCostDAOImpl extends
		GenericDAO<TbTaxLastUpdateAdditionalCost> implements
		TaxLastUpdateAdditionalCostDAO {

	public TaxLastUpdateAdditionalCostDAOImpl() {
		super(TbTaxLastUpdateAdditionalCost.class);		
	}

	@Override
	public List<TbTaxLastUpdateAdditionalCost> findAll() {
		return super.findAll();
	}

	@Override
	public List<TbTaxLastUpdateAdditionalCost> findOrderBy() {
		
		String sqlQuery = "select cost from TbTaxLastUpdateAdditionalCost order by cost.id desc";
		
		return super.selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public void save(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost) {
		super.save(lastUpdateAdditionalCost);
	}

	@Override
	public void alter(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost) {
		super.update(lastUpdateAdditionalCost);
	}

	@Override
	public void delete(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost) {
		Object object = lastUpdateAdditionalCost.getId();
		super.delete(object, TbTaxLastUpdateAdditionalCost.class);
	}

	@Override
	public TbTaxLastUpdateAdditionalCost find(Long id) {
		return super.find(id);
	}

	@Override
	public TbTaxLastUpdateAdditionalCost saveReturn(
			TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost) {
		return super.saveReturn(lastUpdateAdditionalCost);
	}

	@Override
	public List<TbTaxLastUpdateAdditionalCost> simpleQuery(String sqlQuery) {
		return super.selectListSimpleQueryString(sqlQuery);
	}

}
