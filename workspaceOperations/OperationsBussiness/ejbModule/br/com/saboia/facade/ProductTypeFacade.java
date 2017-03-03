package br.com.saboia.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmProductType;

@Local
public interface ProductTypeFacade {
	
	List<TbAdmProductType> findAll();
	
	void save(TbAdmProductType productType);
	
	void alter(TbAdmProductType productType);
	
	void delete(TbAdmProductType productType);
	
	TbAdmProductType find(Long id);

	TbAdmProductType saveReturn(TbAdmProductType productType);
	
	TbAdmProductType findQuery(Object... params);
	
	int findProductTypeByTasModel(int taxModel);
	
	public List<TbAdmProductType> findProductTypeByTaxModel(int taxModel);

	List<TbAdmProductType> findDistinctTaxModel();
}
