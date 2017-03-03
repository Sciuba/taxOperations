package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmDestinationRateDAO;
import br.com.operations.entity.TbAdmDestinationRate;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class AdmDestinationRateDAOImpl extends GenericDAO<TbAdmDestinationRate> implements
		AdmDestinationRateDAO {

	public AdmDestinationRateDAOImpl() {
		super(TbAdmDestinationRate.class);
	}

	@Override
	public List<TbAdmDestinationRate> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmDestinationRate destinationRate) {
		super.save(destinationRate);
	}

	@Override
	public void alter(TbAdmDestinationRate destinationRate) {
		super.update(destinationRate);
	}

	@Override
	public void delete(TbAdmDestinationRate destinationRate) {
		Object object = destinationRate.getId();
		super.delete(object, TbAdmDestinationRate.class);
	}

	@Override
	public TbAdmDestinationRate find(Long id) {
		return super.find(id);
	}

}
