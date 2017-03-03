package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmOriginXTbAdmDestinaProtSt;

@Local
public interface OriginXDestinaProtocolStFacade {
	
	List<TbAdmOriginXTbAdmDestinaProtSt> findAll();
	
	void save(TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt);
	
	void alter(TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt);
	
	void delete(TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt);
	
	TbAdmOriginXTbAdmDestinaProtSt find(Long id);
	
	TbAdmOriginXTbAdmDestinaProtSt saveReturn(TbAdmOriginXTbAdmDestinaProtSt admOriginXTbAdmDestinaProtSt);

}
