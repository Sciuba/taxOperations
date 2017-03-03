package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.AdmOriginXAdmDestinationProtStDAO;
import br.com.operations.entity.TbAdmOriginXTbAdmDestinaProtSt;
import br.com.operations.facade.OriginXDestinaProtocolStFacade;

@Stateless
public class OriginXDestinationProtocoloStFacadeImpl implements
		OriginXDestinaProtocolStFacade {
	
	@EJB
	private AdmOriginXAdmDestinationProtStDAO protocolStDAO;
	
	@Override
	public List<TbAdmOriginXTbAdmDestinaProtSt> findAll() {
		return protocolStDAO.findAll();
	}

	@Override
	public void save(TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt) {
		protocolStDAO.save(admOriginXTbAdmDestinaProtSt);
	}

	@Override
	public void alter(
			TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt) {
		protocolStDAO.alter(admOriginXTbAdmDestinaProtSt);
	}

	@Override
	public void delete(
			TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt) {
		protocolStDAO.delete(admOriginXTbAdmDestinaProtSt);
	}

	@Override
	public TbAdmOriginXTbAdmDestinaProtSt find(Long id) {
		return protocolStDAO.find(id);
	}

	@Override
	public TbAdmOriginXTbAdmDestinaProtSt saveReturn(
			TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt) {
		return protocolStDAO.saveReturn(admOriginXTbAdmDestinaProtSt);
	}
}
