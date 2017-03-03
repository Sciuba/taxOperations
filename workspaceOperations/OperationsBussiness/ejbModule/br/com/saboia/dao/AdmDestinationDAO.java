package br.com.saboia.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmDestination;

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
