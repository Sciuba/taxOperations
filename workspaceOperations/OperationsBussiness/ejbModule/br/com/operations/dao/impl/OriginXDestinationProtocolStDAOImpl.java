package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmOriginXAdmDestinationProtStDAO;
import br.com.operations.entity.TbAdmOriginXTbAdmDestinaProtSt;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class OriginXDestinationProtocolStDAOImpl extends GenericDAO<TbAdmOriginXTbAdmDestinaProtSt>
		implements AdmOriginXAdmDestinationProtStDAO {

	public OriginXDestinationProtocolStDAOImpl() {
		super(TbAdmOriginXTbAdmDestinaProtSt.class);
	}

	@Override
	public List<TbAdmOriginXTbAdmDestinaProtSt> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt) {
		super.save(admOriginXTbAdmDestinaProtSt);
	}

	@Override
	public void alter(
			TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt) {
		super.update(admOriginXTbAdmDestinaProtSt);
	}

	@Override
	public void delete(
			TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt) {
		Object object = admOriginXTbAdmDestinaProtSt.getId();
		super.delete(object, TbAdmOriginXTbAdmDestinaProtSt.class);
	}

	@Override
	public TbAdmOriginXTbAdmDestinaProtSt find(Long id) {
		return super.find(id);
	}

	
	@Override
	public TbAdmOriginXTbAdmDestinaProtSt saveReturn(
			TbAdmOriginXTbAdmDestinaProtSt entity) {
		return super.saveReturn(entity);
	}
}
