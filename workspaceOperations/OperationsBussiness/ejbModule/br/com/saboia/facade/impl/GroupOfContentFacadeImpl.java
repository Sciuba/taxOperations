package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmGroupOfContentDAO;
import br.com.saboia.entity.TbAdmGroupOfContent;
import br.com.saboia.facade.GroupOfContentFacade;

@Stateless
public class GroupOfContentFacadeImpl implements GroupOfContentFacade {

	@EJB
	private AdmGroupOfContentDAO groupOfContentDAO;
	
	@Override
	public List<TbAdmGroupOfContent> findAll() {
		return groupOfContentDAO.findAll();
	}

	@Override
	public void save(TbAdmGroupOfContent content) {
		groupOfContentDAO.save(content);
	}

	@Override
	public void alter(TbAdmGroupOfContent content) {
		groupOfContentDAO.alter(content);
	}

	@Override
	public void delete(TbAdmGroupOfContent content) {
		groupOfContentDAO.delete(content);

	}

	@Override
	public TbAdmGroupOfContent find(Long id) {
		return groupOfContentDAO.find(id);
	}

	@Override
	public TbAdmGroupOfContent saveReturn(TbAdmGroupOfContent content) {
		return groupOfContentDAO.saveReturn(content);
	}

}
