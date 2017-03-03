package br.com.saboia.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmMaterialClass;

@Local
public interface MaterialClassFacade {
	
	List<TbAdmMaterialClass> findAll();
	
	List<TbAdmMaterialClass> findOrder();
	
	void save(TbAdmMaterialClass materialClass);
	
	void alter(TbAdmMaterialClass materialClass);
	
	void delete(TbAdmMaterialClass materialClass);
	
	TbAdmMaterialClass find(Long id);
	
	TbAdmMaterialClass findByNcm(String ncm);

	TbAdmMaterialClass saveReturn(TbAdmMaterialClass materialClass);
	
	TbAdmMaterialClass findQuery(String namedQuery, Map<String, Object> parameters);
	
	public List<TbAdmMaterialClass> queryList();

}
