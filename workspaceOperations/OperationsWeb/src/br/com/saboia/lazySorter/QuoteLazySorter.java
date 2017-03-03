package br.com.saboia.lazySorter;

import java.lang.reflect.Field;
import java.util.Comparator;

import org.primefaces.model.SortOrder;

import br.com.saboia.entity.TbTaxQuote;

public class QuoteLazySorter implements Comparator<TbTaxQuote>{

	 private String sortField;
     
	    private SortOrder sortOrder;
	     
	    public QuoteLazySorter(String sortField, SortOrder sortOrder) {
	        this.sortField = sortField;
	        this.sortOrder = sortOrder;
	    }
	 
	    @SuppressWarnings("rawtypes")
		public int compare(TbTaxQuote quote1, TbTaxQuote quote2) {
	        try {
	            Field f1 = TbTaxQuote.class.getDeclaredField(this.sortField);
	        	
	            f1.setAccessible(true);
	        	
	        	Object value1 = f1.get(quote1);
	            
	        	Field f2 = TbTaxQuote.class.getDeclaredField(this.sortField);
	        	
	        	f2.setAccessible(true);
	        	
	            Object value2 = f2.get(quote2);
	 
	            @SuppressWarnings("unchecked")
				int value = ((Comparable)value1).compareTo(value2);
	             
	            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
	        }
	        catch(Exception e) {
	            throw new RuntimeException();
	        }
	    }
	
}
