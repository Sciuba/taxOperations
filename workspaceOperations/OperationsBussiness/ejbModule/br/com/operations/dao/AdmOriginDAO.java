package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmOrigin;

@Local
public interface AdmOriginDAO {
	
	List<TbAdmOrigin> findAll();
	
	List<TbAdmOrigin> findOrderBy();
	
	void save(TbAdmOrigin origin);
	
	void alter(TbAdmOrigin origin);
	
	void delete(TbAdmOrigin origin);
	
	TbAdmOrigin find(Long id);

	TbAdmOrigin saveReturn(TbAdmOrigin origin);
	
	TbAdmOrigin simpleQuery(String sqlQuery);
	
	List<TbAdmOrigin> simpleQueryList(String sqlQuery);
}
