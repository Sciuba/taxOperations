package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.dao.AdmClienteEspecialDAO;
import br.com.saboia.entity.TbAdmClienteEspecial;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class AdmClienteEspecialDAOImpl extends GenericDAO<TbAdmClienteEspecial> implements
		AdmClienteEspecialDAO {

	public AdmClienteEspecialDAOImpl() {
		super(TbAdmClienteEspecial.class);		
	}

	@Override
	public List<TbAdmClienteEspecial> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmClienteEspecial client) {
		super.save(client);
	}

	@Override
	public void alter(TbAdmClienteEspecial client) {
		super.update(client);
	}

	@Override
	public void delete(TbAdmClienteEspecial client) {
		Object object = client.getId();
		super.delete(object, TbAdmClienteEspecial.class);
	}

	@Override
	public TbAdmClienteEspecial find(Long id) {
		return super.find(id);
	}

	@Override
	public TbAdmClienteEspecial saveReturn(TbAdmClienteEspecial client) {
		return super.saveReturn(client);
	}

}
