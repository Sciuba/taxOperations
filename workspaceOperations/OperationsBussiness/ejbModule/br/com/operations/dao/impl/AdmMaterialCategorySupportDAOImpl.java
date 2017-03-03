package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.entity.TbAdmMaterialCategorySupport;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmMaterialCategorySupportDAOImpl extends GenericDAO<TbAdmMaterialCategorySupport>{

	
	public TbAdmMaterialCategorySupport find(Long id){
		return super.find(id);
	}
	
	public AdmMaterialCategorySupportDAOImpl() {
		super(TbAdmMaterialCategorySupport.class);
	}

	public List<TbAdmMaterialCategorySupport> findAll(){
		return super.findAll();
	}

	public List<TbAdmMaterialCategorySupport> findNotDeleted(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public void save(TbAdmMaterialCategorySupport category){
		super.save(category);
	}
	
	public TbAdmMaterialCategorySupport alter(TbAdmMaterialCategorySupport category){
		return super.update(category);
	}
	
	public TbAdmMaterialCategorySupport saveReturn(TbAdmMaterialCategorySupport category){
		return super.saveReturn(category);
	}
	
	public void delete(TbAdmMaterialCategorySupport category){
		super.delete(category);
	}
	
}
