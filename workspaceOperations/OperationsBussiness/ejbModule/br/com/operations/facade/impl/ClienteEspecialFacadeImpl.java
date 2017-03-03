package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.AdmClienteEspecialDAO;
import br.com.operations.entity.TbAdmClienteEspecial;
import br.com.operations.facade.ClienteEspecialFacade;

@Stateless
public class ClienteEspecialFacadeImpl implements ClienteEspecialFacade {

	@EJB
	private AdmClienteEspecialDAO clienteEspecialDAO;
	
	@Override
	public List<TbAdmClienteEspecial> findAll() {
		return clienteEspecialDAO.findAll();
	}

	@Override
	public void save(TbAdmClienteEspecial client) {
		clienteEspecialDAO.save(client);
	}

	@Override
	public void alter(TbAdmClienteEspecial client) {
		clienteEspecialDAO.alter(client);
	}

	@Override
	public void delete(TbAdmClienteEspecial client) {
		clienteEspecialDAO.delete(client);
	}

	@Override
	public TbAdmClienteEspecial find(Long id) {
		return clienteEspecialDAO.find(id);
	}

	@Override
	public TbAdmClienteEspecial saveReturn(TbAdmClienteEspecial client) {
		return clienteEspecialDAO.saveReturn(client);
	}

}
