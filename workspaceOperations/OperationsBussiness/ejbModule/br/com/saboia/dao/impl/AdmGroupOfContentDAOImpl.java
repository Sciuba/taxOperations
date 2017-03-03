package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.dao.AdmGroupOfContentDAO;
import br.com.saboia.entity.TbAdmGroupOfContent;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class AdmGroupOfContentDAOImpl extends GenericDAO<TbAdmGroupOfContent> implements
		AdmGroupOfContentDAO {

	public AdmGroupOfContentDAOImpl() {
		super(TbAdmGroupOfContent.class);
		
	}

	@Override
	public List<TbAdmGroupOfContent> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmGroupOfContent content) {
		super.save(content);
	}

	@Override
	public void alter(TbAdmGroupOfContent content) {
		super.update(content);
	}

	@Override
	public void delete(TbAdmGroupOfContent content) {
		Object object = content.getId();
		super.delete(object, TbAdmGroupOfContent.class);
	}

	@Override
	public TbAdmGroupOfContent find(Long id) {
		return super.find(id);
	}

	@Override
	public TbAdmGroupOfContent saveReturn(TbAdmGroupOfContent content) {
		return super.saveReturn(content);
	}

}
