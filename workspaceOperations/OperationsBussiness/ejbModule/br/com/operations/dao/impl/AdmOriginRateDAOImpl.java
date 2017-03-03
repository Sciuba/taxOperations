package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.dao.AdmOriginRateDAO;
import br.com.operations.entity.TbAdmOriginRate;
import br.com.operations.jpa.GenericDAO;

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
