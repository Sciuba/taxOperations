package br.com.saboia.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmClienteEspecial;

@Local
public interface ClienteEspecialFacade {
	
	List<TbAdmClienteEspecial> findAll();
	
	void save(TbAdmClienteEspecial client);
	
	void alter(TbAdmClienteEspecial client);
	
	void delete(TbAdmClienteEspecial client);
	
	TbAdmClienteEspecial find(Long id);

	TbAdmClienteEspecial saveReturn(TbAdmClienteEspecial client);

}
