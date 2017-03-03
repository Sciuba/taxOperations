package br.com.operations.dao.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmMaterialDAO;
import br.com.operations.entity.TbAdmMaterial;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmMaterialDAOImpl extends GenericDAO<TbAdmMaterial> implements
		AdmMaterialDAO {

	public AdmMaterialDAOImpl() {
		super(TbAdmMaterial.class);
	}

	@Override
	public List<TbAdmMaterial> findAll() {
		return super.findAll();
	}

	@Override
	public List<TbAdmMaterial> findAllLazyDataModel(String sqlQuery, int startingAt, int maxPerPage){
		return super.selectListEntityDataTableLazy(sqlQuery, startingAt, maxPerPage);
	}
	
	@Override
	public int selectTotalNumberRegistry(String sqlQuery) {
		return super.selectTotalNumberRegistry(sqlQuery);
	}
	
	@Override
	public void save(TbAdmMaterial material) {
		super.save(material);
	}

	@Override
	public void alter(TbAdmMaterial material) {
		super.update(material);
	}

	@Override
	public void delete(TbAdmMaterial material) {
		Object object = material.getId();
		super.delete(object, TbAdmMaterial.class);
	}

	@Override
	public TbAdmMaterial find(Long id) {
		return super.find(id);
	}

	@Override
	public TbAdmMaterial saveReturn(TbAdmMaterial material) {
		return super.saveReturn(material);
	}

	@Override
	public TbAdmMaterial findQuery(String namedQuery,
			Map<String, Object> parameters) {
		return super.findOneResult(namedQuery, parameters);
	}

}
