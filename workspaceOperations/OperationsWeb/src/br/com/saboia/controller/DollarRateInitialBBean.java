package br.com.saboia.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import br.com.saboia.entity.TbAdmDailyDollar;
import br.com.saboia.entity.TbAdmDailyDollarTemp;
import br.com.saboia.entity.TbAdmDollarRate;
import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.DailyDollarFacade;
import br.com.saboia.facade.DailyDollarTempFacade;
import br.com.saboia.facade.DollarRateFacade;
import br.com.saboia.facade.impl.SystemPermissionFacadeImpl;
import br.com.saboia.utils.TxtRead;

@ManagedBean(name="initialBBean")
@ViewScoped
public class DollarRateInitialBBean {
	
	
	@EJB
	private DollarRateFacade dollarRateFacade;
	
	@EJB
	private DailyDollarFacade dailyDollarFacade;
	
	@EJB
	private DailyDollarTempFacade tempFacade;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	private List<TbAdmDailyDollar> listaDailyDollar; 
	private List<TbAdmDailyDollarTemp> listaDailyDollarTemp; 

	private TbAdmDollarRate dollarMonthInitial;
	
	private SimpleDateFormat sdf;
	
	private ResourceBundle bundle;	
	
	private boolean exibeLista;	
	
	private TbSysUser userLogged;
	
	private boolean disabled;
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "dollarRateInitial";
		
	private String sKey;
	
	private boolean disabledSecutity;
	
	private boolean disabledAll;
	
	/**
	 * metodos
	 */
	
	@PostConstruct
	public void init(){
		
		tempFacade.deleteAll();
	
		listaDailyDollarTemp = new ArrayList<>();		

		importTxt();		
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		checkSecurity();
		
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		sdf = new SimpleDateFormat("yyyy/MMMM", Locale.getDefault());
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
		
		try{
			
			dollarMonthInitial = dollarRateFacade.findDollarRateByDate(new Date());
			if(dollarMonthInitial == null && !disabledAll && !disabledSecutity){
				dollarMonthInitial = new TbAdmDollarRate();
				dollarMonthInitial.setMes(sdf.format(new Date()));
			}/*else{
				try {
					redirectPage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			
		}catch(NoResultException e){
			e.printStackTrace();
		}catch (RuntimeException e1) {
			e1.printStackTrace();
		
	
		}finally{
			if(dollarMonthInitial == null){
				dollarMonthInitial = new TbAdmDollarRate();
				dollarMonthInitial.setMes(sdf.format(new Date()));
				
			}/*else{
				
				try {
					
					FacesContext.getCurrentInstance().getExternalContext().redirect("../../pages/private/home.xhtml");
					
					return;
					
					FacesContext fc = FacesContext.getCurrentInstance();
				    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "success");
				
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}*/
		}
						
		try{		
			
			if(listaDailyDollarTemp.size() > 0){
				
				listaDailyDollar = new ArrayList<>();
				
				for(TbAdmDailyDollarTemp temp: listaDailyDollarTemp){
					
					if(!dailyDollarFacade.findDollarDate(temp.getDtDollar())){
					
						TbAdmDailyDollar dollar = new TbAdmDailyDollar();
						dollar.setrDollar(temp.getrDollar());
						dollar.setdtDollar(temp.getDtDollar());
						dailyDollarFacade.save(dollar);
					}
				}
				
				listaDailyDollar = dailyDollarFacade.listDatesNotRate(new Date());
				
			}else{
				
				listaDailyDollar = dailyDollarFacade.listDatesNotRate(new Date());
			}
		}catch(Exception e){
			
			e.printStackTrace();
			
		}finally{
			
			if(listaDailyDollar == null){
			
				listaDailyDollar = new ArrayList<>();
			}
			
			
		}
		
		if(listaDailyDollar.size() < 6){
			
			preparaLista();
			exibeLista = true;
		}else{
			exibeLista = false;
		}
		
		if(!exibeLista && dollarMonthInitial.getId() > 0){
			try {
				
				FacesContext.getCurrentInstance().getExternalContext().redirect("../../pages/private/home.xhtml");
				return;
				
				/*FacesContext fc = FacesContext.getCurrentInstance();
			    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "success");*/
				
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
		}
	}

	
	public void checkSecurity(){
		
		sKey = "";
		disabledSecutity = false;
		disabledAll = false;
		
		permissionList = systemPermissionFacadeImpl.findPermissionByUserIdAndModule(userLogged.getId(), SMODULE);
		
		if(permissionList != null && !permissionList.isEmpty()){
			for(TbSysSystemPermission permission : permissionList){
				if(sKey.isEmpty() || !sKey.equals("WRITE")){
					sKey = permission.getsKey();
				}			
			}
		}
		
		if(sKey.equals("READ")){
			disabledSecutity = true;
		}else if(sKey.isEmpty()){
			disabledAll = true;
			
//			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
//					"You are not allowed to access this module. Please contacts your System Administrator.", ""));
			
//			//Atualizo o Form da tela
//			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form");
			
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void preparaLista() {

		List<Date> listaDatas = new ArrayList<>();

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		gc.set(Calendar.HOUR, gc.getActualMinimum(Calendar.HOUR));  
		gc.set(Calendar.HOUR_OF_DAY, gc.getActualMinimum(Calendar.HOUR_OF_DAY));  
		gc.set(Calendar.MINUTE, gc.getActualMinimum(Calendar.MINUTE));  
		gc.set(Calendar.SECOND, gc.getActualMinimum(Calendar.SECOND));  
		gc.set(Calendar.MILLISECOND, gc.getActualMinimum(Calendar.MILLISECOND));

		int day = gc.get(Calendar.DAY_OF_MONTH);
		int mes = gc.get(Calendar.MONTH);
		int year = gc.get(Calendar.YEAR);

		listaDatas.add(gc.getTime());

		if ((day - 5) <= 0) {

			if (mes > 0) {
				mes -= 1;
			} else {
				mes = 12;
				year -= 1;
				gc.set(Calendar.YEAR, year);
			}

			gc.set(Calendar.MONTH, mes);
			gc.set(Calendar.DAY_OF_MONTH,
					gc.getActualMaximum(Calendar.DAY_OF_MONTH));
			int dayMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);

			for (int i = 0; i < 5; i++) {
				gc.set(Calendar.DAY_OF_MONTH, (dayMonth - (i + 1)));
				listaDatas.add(gc.getTime());
			}

		} else {

			for (int i = 0; i < 5; i++) {
				gc.set(Calendar.DAY_OF_MONTH, (day - (i + 1)));
				listaDatas.add(gc.getTime());
			}

		}

		if (listaDailyDollar.size() == 0) {
			
			for (int i = 0; i < listaDatas.size(); i++) {
				TbAdmDailyDollar daily = new TbAdmDailyDollar();
				daily.setdtDollar(listaDatas.get(i));
				listaDailyDollar.add(daily);
			}
			
		} else {

			boolean contains = false;

			for (int i = 0; i < listaDatas.size(); i++) {
				for (TbAdmDailyDollar dollar : listaDailyDollar) {
					if (dollar.getdtDollar().equals(listaDatas.get(i))) {
						contains = true;
						break;
					} else {
						contains = false;
					}
				}

				if (!contains) {
					TbAdmDailyDollar daily = new TbAdmDailyDollar();
					daily.setdtDollar(listaDatas.get(i));
					listaDailyDollar.add(daily);
				}
			}

			Collections.sort(listaDailyDollar, new Comparator() {

				@Override
				public int compare(Object o1, Object o2) {
					TbAdmDailyDollar d1 = (TbAdmDailyDollar) o1;
					TbAdmDailyDollar d2 = (TbAdmDailyDollar) o2;

					return d1.getdtDollar().before(d2.getdtDollar()) ? +1 : (d1
							.getdtDollar().after(d2.getdtDollar()) ? -1 : 0);
				}

			});
		}

	}
	
	
	public void importTxt(){
		List<TbAdmDailyDollarTemp> lista = TxtRead.txtDataRead();
		
		if(lista.size() > 0){
			for(TbAdmDailyDollarTemp temp : lista){
				listaDailyDollarTemp.add(tempFacade.saveReturn(temp));
			}
		}
	}
	
	
	
	public String save(){
		
		if(dollarMonthInitial.getrDollar() == null){
			

			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_dollar_rate")), ""));
			
			return "";
			
		}
		
		try{
			
			if(dollarMonthInitial.getId() == 0){
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(new Date());;
				
				dollarMonthInitial.setiMonth((gc.get(Calendar.MONTH))+1);
				dollarMonthInitial.setiYear(gc.get(Calendar.YEAR));
				
				dollarRateFacade.save(dollarMonthInitial);
			}
			
			
			for(TbAdmDailyDollar dailyDollar: listaDailyDollar){
				if(dailyDollar.getrDollar() != null){
					try{
						if(dailyDollar.getId() > 0){
							dailyDollarFacade.alter(dailyDollar);
						}else{
							dailyDollarFacade.save(dailyDollar);
						}
					}catch(Exception e){
						dailyDollarFacade.alter(dailyDollar);
					}
				}
			}
			
			return "success";
			
		}catch(Exception e){
			e.printStackTrace();
			
			return "";
		}	
		
		
	}
	
	public String cancel(){
		return "success";
	}
	
	
	public void redirectPage() throws IOException{
		
		if(dollarMonthInitial != null){
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../../pages/private/home.xhtml");
			return;
			
//			FacesContext fc = FacesContext.getCurrentInstance();
//		    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "success");
			
		}
		
	}
	
	
	
	/**
	 * Getters and Setters
	 * @return
	 */

	public List<TbAdmDailyDollar> getListaDailyDollar() {
		return listaDailyDollar;
	}

	public void setListaDailyDollar(List<TbAdmDailyDollar> listaDailyDollar) {
		this.listaDailyDollar = listaDailyDollar;
	}

	public TbAdmDollarRate getDollarMonthInitial() {
		return dollarMonthInitial;
	}

	public void setDollarMonthInitial(TbAdmDollarRate dollarMonthInitial) {
		this.dollarMonthInitial = dollarMonthInitial;
	}


	public boolean isExibeLista() {
		return exibeLista;
	}


	public void setExibeLista(boolean exibeLista) {
		this.exibeLista = exibeLista;
	}



	public List<TbAdmDailyDollarTemp> getListaDailyDollarTemp() {
		return listaDailyDollarTemp;
	}



	public void setListaDailyDollarTemp(
			List<TbAdmDailyDollarTemp> listaDailyDollarTemp) {
		this.listaDailyDollarTemp = listaDailyDollarTemp;
	}
			

	
}
