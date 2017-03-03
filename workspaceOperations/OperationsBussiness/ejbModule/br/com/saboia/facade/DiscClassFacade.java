package br.com.saboia.facade;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.ejb.Local;

import com.sun.xml.ws.rx.rm.runtime.sequence.persistent.PersistenceException;

import br.com.saboia.entity.TbAdmDiscClass;

@Local
public interface DiscClassFacade {


	List<TbAdmDiscClass> findAll();
	
	void save(TbAdmDiscClass discClass);
	
	void alter(TbAdmDiscClass discClass);
	
	void delete(TbAdmDiscClass discClass);
	
	TbAdmDiscClass find(Long id);
	
}
