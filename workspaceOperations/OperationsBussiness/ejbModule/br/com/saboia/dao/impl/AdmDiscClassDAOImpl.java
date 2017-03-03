package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.dao.AdmDiscClassDAO;
import br.com.saboia.entity.TbAdmDiscClass;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class AdmDiscClassDAOImpl extends GenericDAO<TbAdmDiscClass> implements
		AdmDiscClassDAO {

	public AdmDiscClassDAOImpl() {
		super(TbAdmDiscClass.class);
	}

	@Override
	public List<TbAdmDiscClass> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmDiscClass discClass) {
		super.save(discClass);
	}

	@Override
	public void alter(TbAdmDiscClass discClass) {
		super.update(discClass);
	}

	@Override
	public void delete(TbAdmDiscClass discClass) {
		Object object = discClass.getId();
		super.delete(object, TbAdmDiscClass.class);
	}

	@Override
	public TbAdmDiscClass find(Long id) {
		return super.find(id);
	}

}
