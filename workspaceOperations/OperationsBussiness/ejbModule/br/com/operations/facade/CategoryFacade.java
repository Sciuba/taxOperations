package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmCategory;

@Local
public interface CategoryFacade {
	
	List<TbAdmCategory> findAll();
	
	void save(TbAdmCategory category);
	
	void alter(TbAdmCategory category);
	
	void delete(TbAdmCategory category);
	
	TbAdmCategory find(Long id);

}
