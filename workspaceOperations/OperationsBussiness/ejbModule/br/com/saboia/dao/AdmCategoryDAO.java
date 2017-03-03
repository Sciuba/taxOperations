package br.com.saboia.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmCategory;

@Local
public interface AdmCategoryDAO {
	
	List<TbAdmCategory> findAll();
	
	void save(TbAdmCategory category);
	
	void alter(TbAdmCategory category);
	
	void delete(TbAdmCategory category);
	
	TbAdmCategory find(Long id);
	

}
