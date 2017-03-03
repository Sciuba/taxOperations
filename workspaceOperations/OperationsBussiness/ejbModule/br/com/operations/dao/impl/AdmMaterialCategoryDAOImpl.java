package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.entity.TbAdmMaterialCategory;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmMaterialCategoryDAOImpl extends GenericDAO<TbAdmMaterialCategory> {

	public AdmMaterialCategoryDAOImpl() {
		super(TbAdmMaterialCategory.class);
	}
	
	public TbAdmMaterialCategory findByPk(Long id){
		return super.find(id);
	}
	
	public List<TbAdmMaterialCategory> findAll(){
		return super.findAll();
	}
	
	public void save(TbAdmMaterialCategory category) {
		super.save(category);
	}

	public TbAdmMaterialCategory alter(TbAdmMaterialCategory category) {
		return super.update(category);
	}

	public void delete(TbAdmMaterialCategory category) {
		Object object = category.getId();
		super.delete(object, TbAdmMaterialCategory.class);
	}

	public TbAdmMaterialCategory find(Long id) {
		return super.find(id);
	}
	
	public TbAdmMaterialCategory saveReturn(TbAdmMaterialCategory category) {
		return super.saveReturn(category);
	}
	
	public List<TbAdmMaterialCategory> simpleQuery(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
		
}
