package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmOriginXTbAdmDestinaProtSt;

@Local
public interface AdmOriginXAdmDestinationProtStDAO {
	
	List<TbAdmOriginXTbAdmDestinaProtSt> findAll();
	
	void save(TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt);
	
	void alter(TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt);
	
	void delete(TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt);
	
	TbAdmOriginXTbAdmDestinaProtSt find(Long id);
	
	TbAdmOriginXTbAdmDestinaProtSt saveReturn(TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt);

}
