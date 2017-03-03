package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.dao.AdmOriginRateDAO;
import br.com.saboia.entity.TbAdmOriginRate;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class AdmOriginRateDAOImpl extends GenericDAO<TbAdmOriginRate> implements
		AdmOriginRateDAO {

	public AdmOriginRateDAOImpl() {
		super(TbAdmOriginRate.class);		
	}

	@Override
	public List<TbAdmOriginRate> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmOriginRate originRate) {
		super.save(originRate);
	}

	@Override
	public void alter(TbAdmOriginRate originRate) {
		super.update(originRate);
	}

	@Override
	public void delete(TbAdmOriginRate originRate) {
		Object object = originRate.getId();
		super.delete(object, TbAdmOriginRate.class);
	}

	@Override
	public TbAdmOriginRate find(Long id) {
		return super.find(id);
	}

}
