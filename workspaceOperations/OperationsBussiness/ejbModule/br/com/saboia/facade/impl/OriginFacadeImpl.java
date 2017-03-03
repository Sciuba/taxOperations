package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmOriginDAO;
import br.com.saboia.entity.TbAdmOrigin;
import br.com.saboia.facade.OriginFacade;

@Stateless
public class OriginFacadeImpl implements OriginFacade {
	
	@EJB
	private AdmOriginDAO originDAO;
	
	@Override
	public List<TbAdmOrigin> findAll() {
		return originDAO.findAll();
	}

	@Override
	public void save(TbAdmOrigin origin) {
		originDAO.save(origin);
	}

	@Override
	public void alter(TbAdmOrigin origin) {
		originDAO.alter(origin);
	}

	@Override
	public void delete(TbAdmOrigin origin) {
		originDAO.delete(origin);
	}

	@Override
	public TbAdmOrigin find(Long id) {
		return originDAO.find(id);
	}

	@Override
	public TbAdmOrigin saveReturn(TbAdmOrigin origin) {
		return originDAO.saveReturn(origin);
	}

	@Override
	public List<TbAdmOrigin> findOrderBy() {
		return originDAO.findOrderBy();
	}

	@Override
	public TbAdmOrigin findByCode(String code) {
		
		String sqlQuery = "select ori from TbAdmOrigin ori where ori.sCode = '"+code+"'";
		
		return originDAO.simpleQuery(sqlQuery);
	}
	
	
}
