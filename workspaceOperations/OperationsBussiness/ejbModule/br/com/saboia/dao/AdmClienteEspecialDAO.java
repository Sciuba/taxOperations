package br.com.saboia.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmClienteEspecial;

@Local
public interface AdmClienteEspecialDAO {
	
	List<TbAdmClienteEspecial> findAll();
	
	void save(TbAdmClienteEspecial client);
	
	void alter(TbAdmClienteEspecial client);
	
	void delete(TbAdmClienteEspecial client);
	
	TbAdmClienteEspecial find(Long id);

	TbAdmClienteEspecial saveReturn(TbAdmClienteEspecial client);

}
