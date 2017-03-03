package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmOriginDAO;
import br.com.operations.entity.TbAdmOrigin;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmOriginDAOImpl extends GenericDAO<TbAdmOrigin> implements AdmOriginDAO {

	public AdmOriginDAOImpl() {
		super(TbAdmOrigin.class);		
	}

	@Override
	public List<TbAdmOrigin> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmOrigin origin) {
		super.save(origin);
	}

	@Override
	public void alter(TbAdmOrigin origin) {
		super.update(origin);
	}

	@Override
	public void delete(TbAdmOrigin origin) {
		Object object = origin.getId();
		super.delete(object, TbAdmOrigin.class);
	}

	@Override
	public TbAdmOrigin find(Long id) {
		return super.find(id);
	}
	
	@Override
	public TbAdmOrigin saveReturn(TbAdmOrigin entity) {
		return super.saveReturn(entity);
	}

	@Override
	public List<TbAdmOrigin> findOrderBy() {
		
		String sqlQuery = "select origin from TbAdmOrigin origin ORDER BY origin.sLocale ASC";
		
		return super.selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public TbAdmOrigin simpleQuery(String sqlQuery) {
		return super.selectQueryString(sqlQuery);
	}

	@Override
	public List<TbAdmOrigin> simpleQueryList(String sqlQuery) {
		return super.selectListSimpleQueryString(sqlQuery);
	}

}
