package br.com.operations.dao.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmMaterialClassDAO;
import br.com.operations.entity.TbAdmMaterialClass;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmMaterialClassDAOImpl extends GenericDAO<TbAdmMaterialClass>
		implements AdmMaterialClassDAO {

	public AdmMaterialClassDAOImpl() {
		super(TbAdmMaterialClass.class);
	}

	@Override
	public List<TbAdmMaterialClass> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmMaterialClass materialClass) {
		super.save(materialClass);
	}

	@Override
	public void alter(TbAdmMaterialClass materialClass) {
		super.update(materialClass);
	}

	@Override
	public void delete(TbAdmMaterialClass materialClass) {
		Object object = materialClass.getId();
		super.delete(object, TbAdmMaterialClass.class);
	}

	@Override
	public TbAdmMaterialClass find(Long id) {
		return super.find(id);
	}

	@Override
	public TbAdmMaterialClass saveReturn(TbAdmMaterialClass materialClass) {
		return super.saveReturn(materialClass);
	}

	@Override
	public TbAdmMaterialClass findQuery(String namedQuery,
			Map<String, Object> parameters) {
		return super.findOneResult(namedQuery, parameters);
	}
	
	public List<TbAdmMaterialClass> simpleQuery(String sqlQuery){
		return super.selectListSimpleNativeQueryString(sqlQuery);
	}
	
	public List<TbAdmMaterialClass> simpleQueryQuery(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public TbAdmMaterialClass queryString(String sqlQuery) {
		return super.selectQueryString(sqlQuery);
	}
	
}
