package br.com.operations.lazyDataModel;

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

import br.com.operations.entity.TbAdmMaterial;
import br.com.operations.facade.MaterialFacade;
import br.com.operations.lazySorter.MaterialLazySorter;

@Stateless
public class MaterialLazyDataModel extends LazyDataModel<TbAdmMaterial>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7448929379001494214L;

	@EJB
	private MaterialFacade materialFacade;
	
	private List<TbAdmMaterial> listView;
	
	private String filtroSelecionado;
	private String filtroAlias;
	private String filtroPartNumber;
	private String filtroDescription;
	private String filtroNcm;
	
	
	@Override
	public List<TbAdmMaterial> load(int startingAt, int maxPerPage,	String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		
		 List<TbAdmMaterial> listDAO = new ArrayList<>();
		 
		 listView = new ArrayList<>();
		 
		 filtroSelecionado = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("filtro");
		 filtroAlias = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("filtroAlias");
		 filtroPartNumber = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("filtroPartNumber");
		 filtroDescription = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("filtroDescription");
		 filtroNcm = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("filtroNCM");
		 
		try{
			
			listDAO = materialFacade.findLazyDataModel(startingAt, maxPerPage, filtroSelecionado, filtroAlias, filtroPartNumber, filtroDescription, filtroNcm);
			
			boolean match = true;
			
			for(TbAdmMaterial material: listDAO){
			
				if (filters != null) {
	                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
	                    try {
	                        String filterProperty = it.next();
	                        Object filterValue = filters.get(filterProperty);
	                        
	                        Field f = material.getClass().getDeclaredField(filterProperty); // pegar nome da propriedade da classe
	                        
	                        f.setAccessible(true); // como o atributo é do tipo privado antes de pegar o valor dele é preciso deixa-lo acessível
	                        
	                        String fieldValue = String.valueOf(f.get(material)).toUpperCase();
	 
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
					listView.add(material);
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		//sort
        if(sortField != null) {
            Collections.sort(listView, new MaterialLazySorter(sortField, sortOrder));
        }		
		 
        // set the total of players
       // if(getRowCount() <= 0){
            setRowCount(materialFacade.selectTotalNumberRegistry(filtroSelecionado, filtroAlias, filtroPartNumber, filtroDescription, filtroNcm));
        //}
 
        // set the page dize
        setPageSize(maxPerPage);
    	
        
        return listView;
		
	} 
  
	
    @Override
    public Object getRowKey(TbAdmMaterial material) {
        return material.getId();
    }
 
    @Override
    public TbAdmMaterial getRowData(String materialId) {
        Integer id = Integer.valueOf(materialId);
 
        for (TbAdmMaterial material : listView) {
            if(id.equals(material.getId())){
                return material;
            }
        }
 
        return null;
    }


	public String getFiltroSelecionado() {
		return filtroSelecionado;
	}


	public void setFiltroSelecionado(String filtroSelecionado) {
		this.filtroSelecionado = filtroSelecionado;
	}


	public String getFiltroAlias() {
		return filtroAlias;
	}


	public void setFiltroAlias(String filtroAlias) {
		this.filtroAlias = filtroAlias;
	}


	public String getFiltroPartNumber() {
		return filtroPartNumber;
	}


	public void setFiltroPartNumber(String filtroPartNumber) {
		this.filtroPartNumber = filtroPartNumber;
	}


	public String getFiltroDescription() {
		return filtroDescription;
	}


	public void setFiltroDescription(String filtroDescription) {
		this.filtroDescription = filtroDescription;
	}


	public String getFiltroNcm() {
		return filtroNcm;
	}


	public void setFiltroNcm(String filtroNcm) {
		this.filtroNcm = filtroNcm;
	}
    
    
    
}
