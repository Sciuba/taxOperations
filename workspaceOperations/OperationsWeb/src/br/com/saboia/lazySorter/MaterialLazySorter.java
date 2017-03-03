package br.com.saboia.lazySorter;

import java.lang.reflect.Field;
import java.util.Comparator;

import org.primefaces.model.SortOrder;

import br.com.saboia.entity.TbAdmMaterial;

public class MaterialLazySorter implements Comparator<TbAdmMaterial> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public MaterialLazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    @SuppressWarnings("rawtypes")
	public int compare(TbAdmMaterial mat1, TbAdmMaterial mat2) {
        try {
            Field f1 = TbAdmMaterial.class.getDeclaredField(this.sortField);
        	
            f1.setAccessible(true);
        	
        	Object value1 = f1.get(mat1);
            
        	Field f2 = TbAdmMaterial.class.getDeclaredField(this.sortField);
        	
        	f2.setAccessible(true);
        	
            Object value2 = f2.get(mat2);
 
            @SuppressWarnings("unchecked")
			int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
