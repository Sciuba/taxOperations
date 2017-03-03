package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmGroupOfContent;

@Local
public interface AdmGroupOfContentDAO {
	
	List<TbAdmGroupOfContent> findAll();
	
	void save(TbAdmGroupOfContent content);
	
	void alter(TbAdmGroupOfContent content);
	
	void delete(TbAdmGroupOfContent content);
	
	TbAdmGroupOfContent find(Long id);

	TbAdmGroupOfContent saveReturn(TbAdmGroupOfContent content);

}
