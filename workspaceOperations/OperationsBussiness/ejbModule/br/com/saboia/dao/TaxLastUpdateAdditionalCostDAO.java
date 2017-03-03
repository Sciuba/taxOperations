package br.com.saboia.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbTaxLastUpdateAdditionalCost;

@Local
public interface TaxLastUpdateAdditionalCostDAO {
	
	List<TbTaxLastUpdateAdditionalCost> findAll();
	
	List<TbTaxLastUpdateAdditionalCost> findOrderBy();
	
	void save(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost);
	
	void alter(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost);
	
	void delete(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost);
	
	TbTaxLastUpdateAdditionalCost find(Long id);

	TbTaxLastUpdateAdditionalCost saveReturn(TbTaxLastUpdateAdditionalCost lastUpdateAdditionalCost);
	
	List<TbTaxLastUpdateAdditionalCost> simpleQuery(String sqlQuery);
	
}
