package br.com.saboia.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.context.FacesContext;

import br.com.saboia.entity.TbAdmDailyDollarTemp;

public class TxtRead {
	
	private final static String FILEPATH = "txt";
	
	
	public static List<TbAdmDailyDollarTemp> txtDataRead(){
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.HOUR, gc.getActualMinimum(Calendar.HOUR));  
		gc.set(Calendar.HOUR_OF_DAY, gc.getActualMinimum(Calendar.HOUR_OF_DAY));  
		gc.set(Calendar.MINUTE, gc.getActualMinimum(Calendar.MINUTE));  
		gc.set(Calendar.SECOND, gc.getActualMinimum(Calendar.SECOND));
		
		List<TbAdmDailyDollarTemp> lista = new ArrayList<>();
		
		String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath(FILEPATH);
		
		File file = new File(caminho +File.separator+"dailyDollarRate.txt");
		
		if(file.exists()){
			
			try{
				
				FileReader fileReader = new FileReader(file);
				BufferedReader reader = new BufferedReader(fileReader);
				String data = null;
				while((data = reader.readLine()) != null){
				    data = data.replaceFirst(",", "/");
				    
				    String dados[] = data.split("/");
				    
				    Float dollarRate = Float.parseFloat(dados[1].substring(3, 9).replace(",",".")); 
				    
				    gc.set(Calendar.YEAR, Integer.parseInt(dados[0].substring(1, 5)));
				    gc.set(Calendar.MONTH, (Integer.parseInt(dados[0].substring(5, 7)) - 1));
				    gc.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dados[0].substring(7, 9)));
				   
				    TbAdmDailyDollarTemp temp = new TbAdmDailyDollarTemp();
				    
				    temp.setDtDollar(gc.getTime());
				    temp.setrDollar(dollarRate);
				    
				    lista.add(temp);
				    
				}
				
				fileReader.close();
				reader.close();
				
			}catch(Exception e){		
				e.printStackTrace();
			}
		}
		
		return lista;		
	}
	
	public static void main(String[] args) {
		txtDataRead();
	}

}
