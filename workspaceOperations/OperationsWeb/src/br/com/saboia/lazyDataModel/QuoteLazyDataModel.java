package br.com.saboia.lazyDataModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.saboia.controller.LoginBBean;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.entity.TbTaxQuote;
import br.com.saboia.facade.QuoteFacade;
import br.com.saboia.facade.QuoteItemFacade;
import br.com.saboia.lazySorter.QuoteLazySorter;

@Stateless
public class QuoteLazyDataModel extends LazyDataModel<TbTaxQuote>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7508656104679077006L;

	@EJB
	private QuoteFacade quoteFacade;
	
	@EJB
	private QuoteItemFacade itemFacade;
	
	private List<TbTaxQuote> listView;
	
	private Map<String, Object> params;
	
	private boolean limpaFilters;
	
	private boolean checkDeleted;
	
	private TbSysUser userLogged;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbTaxQuote> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		
		 List<TbTaxQuote> listDAO = new ArrayList<>();
		 
		 listView = new ArrayList<>();
		 checkDeleted = false;
		 
		try{
			
			LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
			
			userLogged = login.getUser();
			
			try{
				checkDeleted = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("deleted");
			}catch(Exception e){
				//e.printStackTrace();
			}
			
			params = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("filtersQuote");
			
			try{
				limpaFilters = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cleanFilter");
			
			
				if(limpaFilters){
					filters = null;
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cleanFilter");
					limpaFilters = false;
				}
			}catch(Exception e){
				
			}
			
			listDAO = quoteFacade.findAllLazyDataModel(startingAt, maxPerPage, params, userLogged.getId(), checkDeleted);
			
			boolean match = true;
			
			for(TbTaxQuote quote: listDAO){
				
				try{
					quote.setPersonName(quote.getTbSysUser().getsFullName());
				}catch(Exception e){
					
				}
				
				if (filters != null) {
	                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
	                    try {
	                        String filterProperty = it.next();
	                        Object filterValue = filters.get(filterProperty);
	                        
	                        Field f = quote.getClass().getDeclaredField(filterProperty); // pegar nome da propriedade da classe
	                        
	                        f.setAccessible(true); // como o atributo � do tipo privado antes de pegar o valor dele � preciso deixa-lo acess�vel
	                        
	                        String fieldValue = String.valueOf(f.get(quote)).toUpperCase();
	 
	                        if(filterValue == null || fieldValue.contains(filterValue.toString().toUpperCase())) {
	                            match = true;
	                            
		                    }else {
	                            match = false;
	                            break;
		                    }
	                        
	                    } catch(Exception e) {
	                        match = false;
	                    }
	                }
	            }
				
				if(match){
					listView.add(quote);
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		//sort
        if(sortField != null) {
            Collections.sort(listView, new QuoteLazySorter(sortField, sortOrder));
        }		
		 
        // set the total of players
       // if(getRowCount() <= 0){
            setRowCount(quoteFacade.selectTotalNumberRegistry(params, userLogged.getId(), checkDeleted));
        //}
 
        // set the page dize
        setPageSize(maxPerPage);
    	
        
        return listView;
		
	} 
  
	
    @Override
    public Object getRowKey(TbTaxQuote quote) {
        return quote.getId();
    }
 
    @Override
    public TbTaxQuote getRowData(String quoteId) {
        Integer id = Integer.valueOf(quoteId);
 
        for (TbTaxQuote quote : listView) {
            if(id.equals(quote.getId())){
                return quote;
            }
        }
 
        return null;
    }
	
}
