package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmCategoryDAO;
import br.com.operations.entity.TbAdmCategory;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmCategoryDAOImpl extends GenericDAO<TbAdmCategory> implements AdmCategoryDAO {

	public AdmCategoryDAOImpl() {
		super(TbAdmCategory.class);
	}

	@Override
	public List<TbAdmCategory> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmCategory category) {
		super.save(category);
	}

	@Override
	public void alter(TbAdmCategory category) {
		super.update(category);
	}

	@Override
	public void delete(TbAdmCategory category) {
		Object object = category.getId();
		super.delete(object, TbAdmCategory.class);		
	}

	@Override
	public TbAdmCategory find(Long id) {
		return super.find(id);		
	}
	
	
	
}
