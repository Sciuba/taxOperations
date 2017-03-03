package br.com.saboia.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmMaterial;

@Local
public interface AdmMaterialDAO {
	
	List<TbAdmMaterial> findAll();
	
	public List<TbAdmMaterial> findAllLazyDataModel(String sqlQuery, int startingAt, int maxPerPage);
	
	int selectTotalNumberRegistry(String sqlQuery);
	
	void save(TbAdmMaterial material);
	
	void alter(TbAdmMaterial material);
	
	void delete(TbAdmMaterial material);
	
	TbAdmMaterial find(Long id);

	TbAdmMaterial saveReturn(TbAdmMaterial material);
	
	TbAdmMaterial findQuery(String namedQuery, Map<String, Object> parameters);

}
