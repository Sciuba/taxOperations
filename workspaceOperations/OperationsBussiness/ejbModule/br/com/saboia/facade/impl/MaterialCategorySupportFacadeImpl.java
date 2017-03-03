package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.impl.AdmMaterialCategorySupportDAOImpl;
import br.com.saboia.entity.TbAdmMaterialCategorySupport;

@Stateless
public class MaterialCategorySupportFacadeImpl {
	
	@EJB
	private AdmMaterialCategorySupportDAOImpl supportDao;
	
	public TbAdmMaterialCategorySupport find(Long id){
		return supportDao.find(id);
	}
	
	public List<TbAdmMaterialCategorySupport> findAll(){
		return supportDao.findAll();
	}
	
	public List<TbAdmMaterialCategorySupport> findNotDeleted(){

		String sqlQuery = "select sup from TbAdmMaterialCategorySupport sup where sup.fDeleted = 0";
		
		return supportDao.findNotDeleted(sqlQuery);
	}
	
	public void save(TbAdmMaterialCategorySupport category){
		supportDao.save(category);
	}
	
	public TbAdmMaterialCategorySupport alter(TbAdmMaterialCategorySupport category){
		return supportDao.alter(category);
	}
	
	public TbAdmMaterialCategorySupport saveReturn(TbAdmMaterialCategorySupport category){
		return supportDao.saveReturn(category);
	}
	
	public void delete(TbAdmMaterialCategorySupport category){
		supportDao.delete(category);
	}
	
}
