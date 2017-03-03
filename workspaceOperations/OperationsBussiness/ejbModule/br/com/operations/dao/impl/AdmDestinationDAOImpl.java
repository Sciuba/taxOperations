package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmDestinationDAO;
import br.com.operations.entity.TbAdmDestination;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmDestinationDAOImpl extends GenericDAO<TbAdmDestination> implements
		AdmDestinationDAO {

	public AdmDestinationDAOImpl() {
		super(TbAdmDestination.class);
	}

	@Override
	public List<TbAdmDestination> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmDestination destination) {
		super.save(destination);
	}

	@Override
	public void alter(TbAdmDestination destination) {
		super.update(destination);
	}

	@Override
	public void delete(TbAdmDestination destination) {
		Object object = destination.getId();
		super.delete(object, TbAdmDestination.class);
	}

	@Override
	public TbAdmDestination find(Long id) {
		return super.find(id);
	}

	@Override
	public List<TbAdmDestination> findOrderBy() {
		
		String sqlQuery = "select dest from TbAdmDestination dest order by dest.sLocale asc";
		
		return selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public List<TbAdmDestination> findSimpleQuery(String sqlQuery) {
		return super.selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public TbAdmDestination simpleQuery(String sqlQuery) {
		return super.selectQueryString(sqlQuery);
	}

}
