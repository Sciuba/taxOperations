package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.AdmDailyDollarTempDAO;
import br.com.operations.entity.TbAdmDailyDollarTemp;
import br.com.operations.facade.DailyDollarTempFacade;

@Stateless
public class DalilyDollarTempFacadeImpl implements DailyDollarTempFacade {

	@EJB
	private AdmDailyDollarTempDAO dailyDollarTempDAO;
	
	@Override
	public List<TbAdmDailyDollarTemp> findAll() {
		return dailyDollarTempDAO.findAll();
	}

	@Override
	public void save(TbAdmDailyDollarTemp temp) {
		dailyDollarTempDAO.save(temp);
	}

	@Override
	public void alter(TbAdmDailyDollarTemp temp) {
		dailyDollarTempDAO.alter(temp);
	}

	@Override
	public void delete(TbAdmDailyDollarTemp temp) {
		dailyDollarTempDAO.delete(temp);
	}

	@Override
	public TbAdmDailyDollarTemp find(Long id) {
		return dailyDollarTempDAO.find(id);
	}

	@Override
	public TbAdmDailyDollarTemp saveReturn(TbAdmDailyDollarTemp temp) {
		return dailyDollarTempDAO.saveReturn(temp);
	}

	@Override
	public void deleteAll() {
		
		String sqlQuery = "delete from TbAdmDailyDollarTemp";
		
		dailyDollarTempDAO.deleteAll(sqlQuery);
		
	}

}
