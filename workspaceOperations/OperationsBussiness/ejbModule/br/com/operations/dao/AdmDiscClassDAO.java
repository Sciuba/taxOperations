package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmDiscClass;

@Local
public interface AdmDiscClassDAO {
	
	List<TbAdmDiscClass> findAll();
	
	void save(TbAdmDiscClass discClass);
	
	void alter(TbAdmDiscClass discClass);
	
	
	void delete(TbAdmDiscClass discClass);
	
	TbAdmDiscClass find(Long id);

}
