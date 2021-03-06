package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmCategory;

@Local
public interface AdmCategoryDAO {
	
	List<TbAdmCategory> findAll();
	
	void save(TbAdmCategory category);
	
	void alter(TbAdmCategory category);
	
	void delete(TbAdmCategory category);
	
	TbAdmCategory find(Long id);
	

}
