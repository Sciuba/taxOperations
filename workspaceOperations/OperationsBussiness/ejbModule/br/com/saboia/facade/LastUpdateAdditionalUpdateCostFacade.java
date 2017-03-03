package br.com.saboia.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbTaxLastUpdateAdditionalCost;

@Local
public interface LastUpdateAdditionalUpdateCostFacade {

	List<TbTaxLastUpdateAdditionalCost> findAll();
	
	List<TbTaxLastUpdateAdditionalCost> findOrderBy();
	
	void save(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost);
	
	void alter(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost);
	
	void delete(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost);
	
	TbTaxLastUpdateAdditionalCost find(Long id);

	TbTaxLastUpdateAdditionalCost saveReturn(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost);
	
	List<TbTaxLastUpdateAdditionalCost> simpleQuery(String sqlQuery);
	
}
