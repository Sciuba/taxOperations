package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.AdmCategoryDAO;
import br.com.operations.entity.TbAdmCategory;
import br.com.operations.facade.CategoryFacade;

@Stateless
public class CategoryFacadeImpl implements CategoryFacade {
	
	@EJB
	private AdmCategoryDAO categoryDAO;
	
	
	@Override
	public List<TbAdmCategory> findAll() {
		return categoryDAO.findAll();
	}

	@Override
	public void save(TbAdmCategory category){
		categoryDAO.save(category);		
	}

	@Override
	public void alter(TbAdmCategory category) {
		categoryDAO.alter(category);
	}

	@Override
	public void delete(TbAdmCategory category) {
		categoryDAO.delete(category);
	}

	@Override
	public TbAdmCategory find(Long id) {
		return categoryDAO.find(id);
	}

}
