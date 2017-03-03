package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.dao.AdmRateDAO;
import br.com.saboia.entity.TbAdmRate;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class AdmRateDAOImpl extends GenericDAO<TbAdmRate> implements AdmRateDAO {

	public AdmRateDAOImpl() {
		super(TbAdmRate.class);
	}

	@Override
	public List<TbAdmRate> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmRate rate) {
		super.save(rate);
	}

	@Override
	public void alter(TbAdmRate rate) {
		super.update(rate);
	}

	@Override
	public void delete(TbAdmRate rate) {
		Object object = rate.getId();
		super.delete(object, TbAdmRate.class);
	}

	@Override
	public TbAdmRate find(Long id) {
		return super.find(id);
	}

	@Override
	public TbAdmRate saveReturn(TbAdmRate rate) {
		return super.saveReturn(rate);
	}

}
