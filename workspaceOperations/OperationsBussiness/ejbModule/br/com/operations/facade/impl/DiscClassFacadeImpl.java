package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.AdmDiscClassDAO;
import br.com.operations.entity.TbAdmDiscClass;
import br.com.operations.facade.DiscClassFacade;

@Stateless
public class DiscClassFacadeImpl implements DiscClassFacade {
	
	@EJB
	private AdmDiscClassDAO discClassDAO;
	
	@Override
	public List<TbAdmDiscClass> findAll() {
		return discClassDAO.findAll();
	}

	@Override
	public void save(TbAdmDiscClass discClass) {
		discClassDAO.save(discClass);
	}

	@Override
	public void alter(TbAdmDiscClass discClass) {
		discClassDAO.alter(discClass);
	}

	@Override
	public void delete(TbAdmDiscClass discClass) {
		discClassDAO.delete(discClass);
	}

	@Override
	public TbAdmDiscClass find(Long id) {
		return discClassDAO.find(id);
	}

}
