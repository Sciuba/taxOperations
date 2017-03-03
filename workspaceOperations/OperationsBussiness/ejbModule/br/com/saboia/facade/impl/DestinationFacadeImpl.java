package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmDestinationDAO;
import br.com.saboia.entity.TbAdmDestination;
import br.com.saboia.facade.DestinationFacade;

@Stateless
public class DestinationFacadeImpl implements DestinationFacade {
	
	@EJB
	private AdmDestinationDAO destinationDAO;
	
	@Override
	public List<TbAdmDestination> findAll() { 
		return destinationDAO.findAll();
	}

	@Override
	public void save(TbAdmDestination destination) {
		destinationDAO.save(destination);
	}

	@Override
	public void alter(TbAdmDestination destination) {
		destinationDAO.alter(destination);
	}

	@Override
	public void delete(TbAdmDestination destination) {
		destinationDAO.delete(destination);
	}

	@Override
	public TbAdmDestination find(Long id) {
		return destinationDAO.find(id);
	}

	@Override
	public List<TbAdmDestination> findOrderBy() {
		return destinationDAO.findOrderBy();
	}
	
	@Override
	public List<TbAdmDestination> findOrderByCode() {
		
		String sqlQuery = "select dest from TbAdmDestination dest order by dest.sCode asc";
		
		return destinationDAO.findSimpleQuery(sqlQuery);
	}
	
	@Override
	public List<TbAdmDestination> findListDestinationWithProtocol() {
		
		String sqlQuery = "select dest from TbAdmDestination dest "
				+ "join TbAdmOriginXTbAdmDestinaProtSt st "
				+ "on st.tbAdmDestination.id = dest.id "
				+ "where st.tbAdmOrigin.id = 5 order by dest.sCode asc";
		
		return destinationDAO.findSimpleQuery(sqlQuery);
	}

	@Override
	public TbAdmDestination findByCode(String code) {
		
		String sqlQuery = "select dest from TbAdmDestination dest where dest.sCode = '"+code+"'";
		
		return destinationDAO.simpleQuery(sqlQuery);
	}

}
