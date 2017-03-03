package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmGroupOfContent;

@Local
public interface GroupOfContentFacade {
	
	List<TbAdmGroupOfContent> findAll();
	
	void save(TbAdmGroupOfContent content);
	
	void alter(TbAdmGroupOfContent content);
	
	void delete(TbAdmGroupOfContent content);
	
	TbAdmGroupOfContent find(Long id);

	TbAdmGroupOfContent saveReturn(TbAdmGroupOfContent content);
}
