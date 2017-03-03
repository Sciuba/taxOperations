package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.TaxLastUpdateAdditionalCostDAO;
import br.com.operations.entity.TbTaxLastUpdateAdditionalCost;
import br.com.operations.facade.LastUpdateAdditionalUpdateCostFacade;

@Stateless
public class LastUpdateAdditionalUpdateFacadeImpl implements
		LastUpdateAdditionalUpdateCostFacade {

	
	@EJB
	private TaxLastUpdateAdditionalCostDAO costDAO;
	
	@Override
	public List<TbTaxLastUpdateAdditionalCost> findAll() {
		return costDAO.findAll();
	}

	@Override
	public List<TbTaxLastUpdateAdditionalCost> findOrderBy() {
		return costDAO.findOrderBy();
	}

	@Override
	public void save(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost) {
		costDAO.save(lastUpdateAdditionalCost);
	}

	@Override
	public void alter(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost) {
		costDAO.alter(lastUpdateAdditionalCost);
	}

	@Override
	public void delete(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost) {
		costDAO.delete(lastUpdateAdditionalCost);
	}

	@Override
	public TbTaxLastUpdateAdditionalCost find(Long id) {
		return costDAO.find(id);
	}

	@Override
	public TbTaxLastUpdateAdditionalCost saveReturn(
			TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost) {
		return costDAO.saveReturn(lastUpdateAdditionalCost);
	}

	@Override
	public List<TbTaxLastUpdateAdditionalCost> simpleQuery(String sqlQuery) {
		return costDAO.simpleQuery(sqlQuery);
	}

}
