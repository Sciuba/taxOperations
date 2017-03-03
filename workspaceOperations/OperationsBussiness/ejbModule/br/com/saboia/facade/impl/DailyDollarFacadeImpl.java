package br.com.saboia.facade.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmDailyDollarDAO;
import br.com.saboia.entity.TbAdmDailyDollar;
import br.com.saboia.facade.DailyDollarFacade;

@Stateless
public class DailyDollarFacadeImpl implements DailyDollarFacade {

	@EJB
	private AdmDailyDollarDAO dailyDollarDAO;
	
	@Override
	public List<TbAdmDailyDollar> findAll() {
		return dailyDollarDAO.findAll();
	}

	@Override
	public void save(TbAdmDailyDollar dailyDollar) {
		dailyDollarDAO.save(dailyDollar);
	}

	@Override
	public void alter(TbAdmDailyDollar dailyDollar) {
		dailyDollarDAO.alter(dailyDollar);
	}

	@Override
	public void delete(TbAdmDailyDollar dailyDollar) {
		dailyDollarDAO.delete(dailyDollar);
	}

	@Override
	public TbAdmDailyDollar find(Long id) {
		return dailyDollarDAO.find(id);
	}

	@Override
	public TbAdmDailyDollar saveReturn(TbAdmDailyDollar dailyDollar) {
		return dailyDollarDAO.saveReturn(dailyDollar);
	}

	@Override
	public List<TbAdmDailyDollar> listDatesNotRate(Date dataIni) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		GregorianCalendar gc = new GregorianCalendar();
		
		gc.setTime(dataIni);
		
		gc.set(Calendar.HOUR, gc.getActualMinimum(Calendar.HOUR));  
		gc.set(Calendar.HOUR_OF_DAY, gc.getActualMinimum(Calendar.HOUR_OF_DAY));  
		gc.set(Calendar.MINUTE, gc.getActualMinimum(Calendar.MINUTE));  
		gc.set(Calendar.SECOND, gc.getActualMinimum(Calendar.SECOND));
		gc.set(Calendar.MILLISECOND, gc.getActualMinimum(Calendar.MILLISECOND));
		
		Date dataFim = null;
		
		int day  = gc.get(Calendar.DAY_OF_MONTH);
		
		if((day - 5) <= 0){
			int mes = gc.get(Calendar.MONTH);
			int year = gc.get(Calendar.YEAR);
			
			if(mes > 0){
				mes -= 1;
			}else{
				mes = 12;
				gc.set(Calendar.YEAR, (year - 1));
			}
			
			gc.set(Calendar.MONTH, mes);
			gc.set(Calendar.DAY_OF_MONTH, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
			dataFim = gc.getTime();		
			
		}else{
			gc.set(Calendar.DAY_OF_MONTH, (day - 5));
			dataFim = gc.getTime();			
		}
		
		String sqlQuery = "select daily from TbAdmDailyDollar daily where daily.dtDollar between ?1 and ?2";
		
		return dailyDollarDAO.listDatesNotRate(sqlQuery, dataFim, dataIni);
	}
	

	@Override
	public boolean findDollarDate(Date date) {
		
		GregorianCalendar gc = new GregorianCalendar();
		
		gc.setTime(date);
		
		gc.set(Calendar.HOUR, gc.getActualMinimum(Calendar.HOUR));  
		gc.set(Calendar.HOUR_OF_DAY, gc.getActualMinimum(Calendar.HOUR_OF_DAY));  
		gc.set(Calendar.MINUTE, gc.getActualMinimum(Calendar.MINUTE));  
		gc.set(Calendar.SECOND, gc.getActualMinimum(Calendar.SECOND));
		gc.set(Calendar.MILLISECOND, gc.getActualMinimum(Calendar.MILLISECOND));
		
		
		String sqlQuery = "select daily from TbAdmDailyDollar daily where daily.dtDollar = :data";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("data", gc.getTime());
		
		TbAdmDailyDollar daily = dailyDollarDAO.findDollarDate(sqlQuery, parameters);
		
		if(daily != null){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public List<TbAdmDailyDollar> findOrderBy() {
		return dailyDollarDAO.findOrderBy();
	}

	@Override
	public TbAdmDailyDollar simpleQuery() {
		
		String sqlQuery = "select dollar from TbAdmDailyDollar dollar order by dollar.id desc";
		
		return dailyDollarDAO.simpleQueryFirstResult(sqlQuery);
	}

	@Override
	public TbAdmDailyDollar simpleQueryActualDollarRate() {
		
		String sqlQuery = "select dollar from TbAdmDailyDollar dollar where  dollar.id = (select MAX(d.id) from TbAdmDailyDollar d)";
		
		return dailyDollarDAO.simpleQuery(sqlQuery);
	}

}
