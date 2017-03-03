package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmDestination;

@Local
public interface AdmDestinationDAO {
	
	List<TbAdmDestination> findAll();
	
	List<TbAdmDestination> findOrderBy();
	
	void save(TbAdmDestination destination);
	
	void alter(TbAdmDestination destination);
	
	void delete(TbAdmDestination destination);
	
	TbAdmDestination find(Long id);
	
	List<TbAdmDestination> findSimpleQuery(String sqlQuery);
	
	TbAdmDestination simpleQuery(String sqlQuery);
	
}
