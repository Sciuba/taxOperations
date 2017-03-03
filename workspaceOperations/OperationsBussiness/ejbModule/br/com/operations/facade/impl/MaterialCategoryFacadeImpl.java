package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.impl.AdmMaterialCategoryDAOImpl;
import br.com.operations.entity.TbAdmMaterialCategory;

@Stateless
public class MaterialCategoryFacadeImpl {
	
	@EJB
	private AdmMaterialCategoryDAOImpl categoryDao;
	
	public List<TbAdmMaterialCategory> findAll(){
		return categoryDao.findAll();
	}
	
	public void save(TbAdmMaterialCategory category){
		categoryDao.save(category);
	}
	
	public TbAdmMaterialCategory find(Long id){
		return categoryDao.find(id);
	}
	
	public TbAdmMaterialCategory alter(TbAdmMaterialCategory category){
		return categoryDao.alter(category);
	}
	
	public TbAdmMaterialCategory saveReturn(TbAdmMaterialCategory category){
		return categoryDao.saveReturn(category);
	}
	
	public void delete(TbAdmMaterialCategory category){
		categoryDao.delete(category);
	}
	
	public List<TbAdmMaterialCategory> findListNotDeleted(){
		
		String sqlQuery = "select category from TbAdmMaterialCategory category where category.fDeleted is null";
		
		return categoryDao.simpleQuery(sqlQuery);
		
	}
	
}
