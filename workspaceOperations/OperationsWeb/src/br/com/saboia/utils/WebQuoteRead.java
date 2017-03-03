package br.com.saboia.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.faces.context.FacesContext;

import br.com.saboia.to.ImportQuoteItemTO;
import br.com.saboia.to.ImportQuoteTO;

public class WebQuoteRead {
	
	private final static String FILEPATH = "Active/ImportFiles/importQuote";

	public static void run() {

		File directory = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(FILEPATH));

		System.out.println(directory.getAbsolutePath());

		if (directory.isDirectory()) {
			File[] listFiles = directory.listFiles();

			if (listFiles.length > 0) {

				for (int i = 0; i < listFiles.length; i++) {

					String arquivoHTML = listFiles[i].getName();
					
					if(arquivoHTML.toLowerCase().contains("htm") || arquivoHTML.toLowerCase().contains("html")){
						
						File file = new File(directory.getPath()+File.separator+arquivoHTML);
						
						System.out.println(file.getAbsolutePath());
						
						BufferedReader br = null;
						String linha = "";
						String quoteItemGrupo = "";
						
						try {
	
							br = new BufferedReader(new FileReader(file));
							
							ImportQuoteTO quoteTO = new ImportQuoteTO();
							
							quoteTO.setFileType("html");
							
							quoteTO.setQuoteItens(new ArrayList<ImportQuoteItemTO>());
							
							ImportQuoteItemTO quoteItemTO = null;
							
							int j = 0;

							try{
								quoteTO.setGrossRevenueTotal("0.00");
							}catch(Exception e){
								quoteTO.setGrossRevenueTotal("0.00");
							}
							
							while ((linha = br.readLine()) != null) {
							 	
								// Identifica quando o WebQuote esta na moeda BRL
								if(linha.contains("All amounts in BRL")){
									quoteTO.setQuoteInBRL(true);
								}
													
								if(linha.startsWith("<div class=\"rc-content-main\"><br><table") || linha.startsWith("<div class=\"rc-content-main\"><br/><table")){									
						        	
									if( j > 0){
										
						        		String[] result = linha.split("</");
						        		

						        		for(int k = 0 ; k < result.length; k++){
						        			if(result[k].toUpperCase().contains("P5_CUST_NAME")){
						        				try{
						        					quoteTO.setsCustomer(result[3].split(">")[3]);
						        				}catch(Exception e){
						        					quoteTO.setsCustomer("");
						        				}
						        			}
						        		}
						        		
						        	}else{
						        		
						        		String[] result = linha.split("</");
						        		
						        		for(int k = 0 ; k < result.length; k++){
						        			if(result[k].toUpperCase().contains("P5_QUOTE_ID")){
						        				quoteTO.setQuoteName("WebQuote ID #"+result[3].split(">")[3]);
						        			}
						        		}
						        		
						        		j++;
						        	}
									
								}
								
								if(linha.contains("<tr class=\"highlight-row\">")){
							        
									quoteItemTO = new ImportQuoteItemTO();
									
						        	String[] result = linha.split("</");
						        	
//						        	System.out.println("Tamanho da Linha = "+result.length);
						        	
						        	for(int k = 0 ; k < result.length; k++){
						        	
							        	try{
							        		
							        		if(result[k].toUpperCase().contains("QUOTE_LINE_NUMBER")){
							        			
							        			try{
							        				quoteItemTO.setLine(result[k].split(">")[3]);
							        			}catch(Exception e){
							        				quoteItemTO.setLine(result[k].split(">")[2]);
							        			}
							        			
							        		}
							        		
							        		if(result[k].toUpperCase().contains("INV_PART_NUMBER")){
							        			
							        			try{
							        				quoteItemTO.setPartLineType(result[k].split(">")[3]);
							        			}catch(Exception e){
							        				quoteItemTO.setPartLineType(result[k].split(">")[2]);
							        			}
							        			
							        		}
							        		
							        		if(result[k].toUpperCase().contains("DESCRIPTION")){
								        		
							        			try{
								        			quoteItemTO.setPartDescription(result[k].split(">")[3]);
								        		}catch(Exception e){
								        			quoteItemTO.setPartDescription(result[k].split(">")[2]);
								        		}
							        			
							        		}
							        		
							        		if(result[k].toUpperCase().contains("QUANTITY")){
							        			quoteItemTO.setQty(result[k].split(">")[2]);
							        		}
							        		
							        		if(result[k].toUpperCase().contains("UNIT_LIST_PRICE")){
							        			quoteItemTO.setUnitPrice(result[k].split(">")[2]);
							        		}
							        		
							        		//System.out.println("List Fees = "+result[9].split(">")[2]);
							        		if(result[k].toUpperCase().contains("LISTFEES")){
							        			quoteItemTO.setListFee(result[k].split(">")[2]);
							        		}
							        		
							        		if(result[k].toUpperCase().contains("REQUESTED_DISCOUNT_PERCENT")){
							        			quoteItemTO.setAdrDiscount(result[k].split(">")[2]);
							        		}
							        		
							        		//System.out.println("Unit Selling Price = "+result[11].split(">")[2]);
							        		
							        		if(result[k].toUpperCase().contains("EXTENDED_NET_PRICE")){
							        			quoteItemTO.setNetRevenue(result[k].split(">")[2]);
							        		}
							        		
							        		if(result[k].toUpperCase().contains("SUPPORT_DURATION_IN_YEARS")){
							        			quoteItemTO.setDurationSupport(result[k].split(">")[2]);
							        		}

							        		//System.out.println("Include Data Retention Service = "+result[14].split(">")[2]);
							        		
							        		if(result[k].toUpperCase().contains("SUPPORT_NET_PRICE")){
							        			quoteItemTO.setSupportNetPrice(result[k].split(">")[2]);
							        		}
							        		
							        		
							        	}catch(Exception e){
							        		
							        	}		        	
							        	
						        	}
						        	
						        	quoteTO.getQuoteItens().add(quoteItemTO);
								}								
							}
							
							if(quoteTO != null){
								FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("importQuote", quoteTO);
							}
	
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (br != null) {
								try {
									br.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}							     
					}
				}
			}
		}
	}	
}
