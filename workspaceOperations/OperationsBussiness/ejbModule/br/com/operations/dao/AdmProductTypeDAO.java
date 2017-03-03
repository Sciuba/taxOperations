package br.com.operations.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmProductType;

@Local
public interface AdmProductTypeDAO {
	
	List<TbAdmProductType> findAll();
	
	void save(TbAdmProductType productType);
	
	void alter(TbAdmProductType productType);
	
	void delete(TbAdmProductType productType);
	
	TbAdmProductType find(Long id);

	TbAdmProductType saveReturn(TbAdmProductType productType);
	
	TbAdmProductType findQuery(String namedQuery, Map<String, Object> parameters);
	
	int findProductTypeByTasModel(String sqlQuery);
	
	List<TbAdmProductType> findByProductType(String sqlQuery);
}
