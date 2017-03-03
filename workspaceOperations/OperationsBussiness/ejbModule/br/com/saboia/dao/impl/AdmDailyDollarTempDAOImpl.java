package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.dao.AdmDailyDollarTempDAO;
import br.com.saboia.entity.TbAdmDailyDollarTemp;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class AdmDailyDollarTempDAOImpl extends GenericDAO<TbAdmDailyDollarTemp> implements AdmDailyDollarTempDAO{

	public AdmDailyDollarTempDAOImpl() {
		super(TbAdmDailyDollarTemp.class);
	}

	@Override
	public List<TbAdmDailyDollarTemp> findAll() {
		return super.findAll();
	}
	
	@Override
	public void save(TbAdmDailyDollarTemp temp) {
		super.save(temp);
	}
	
	@Override
	public void alter(TbAdmDailyDollarTemp temp) {
		super.update(temp);	
	}

	
	@Override
	public void delete(TbAdmDailyDollarTemp temp) {
		Object object = temp.getId();
		super.delete(object, TbAdmDailyDollarTemp.class);
	}
	
	@Override
	public TbAdmDailyDollarTemp find(Long id) {
		return super.find(id);
	}
	
	@Override
	public TbAdmDailyDollarTemp saveReturn(TbAdmDailyDollarTemp temp) {
		return super.saveReturn(temp);
	}

	@Override
	public void deleteAll(String sqlQuery) {
		super.deleteAll(sqlQuery);		
	}
}
