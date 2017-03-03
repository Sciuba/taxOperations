package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmClienteEspecial;

@Local
public interface AdmClienteEspecialDAO {
	
	List<TbAdmClienteEspecial> findAll();
	
	void save(TbAdmClienteEspecial client);
	
	void alter(TbAdmClienteEspecial client);
	
	void delete(TbAdmClienteEspecial client);
	
	TbAdmClienteEspecial find(Long id);

	TbAdmClienteEspecial saveReturn(TbAdmClienteEspecial client);

}
