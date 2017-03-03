package br.com.operations.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmMaterialClass;

@Local
public interface AdmMaterialClassDAO {

	List<TbAdmMaterialClass> findAll();
	
	void save(TbAdmMaterialClass materialClass);
	
	void alter(TbAdmMaterialClass materialClass);
	
	void delete(TbAdmMaterialClass materialClass);
	
	TbAdmMaterialClass find(Long id);
	
	TbAdmMaterialClass queryString(String sqlQuery);

	TbAdmMaterialClass saveReturn(TbAdmMaterialClass materialClass);
	
	TbAdmMaterialClass findQuery(String namedQuery, Map<String, Object> parameters);
	
	List<TbAdmMaterialClass> simpleQuery(String sqlQuery);
	
	List<TbAdmMaterialClass> simpleQueryQuery(String sqlQuery);
}
