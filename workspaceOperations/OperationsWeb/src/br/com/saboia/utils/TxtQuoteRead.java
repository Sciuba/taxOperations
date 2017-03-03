package br.com.saboia.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import br.com.saboia.to.ImportQuoteItemTO;
import br.com.saboia.to.ImportQuoteTO;

public class TxtQuoteRead {
	
	private final static String FILEPATH = "Active/ImportFiles/importQuote";

	public static void run() {

		File directory = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(FILEPATH));

		System.out.println(directory.getAbsolutePath());
		
		if (directory.isDirectory()) {
			File[] listFiles = directory.listFiles();

			if (listFiles.length > 0) {

				for (int i = 0; i < listFiles.length; i++) {

					String arquivoTxt = listFiles[i].getName();
					
					if(arquivoTxt.contains("txt")){
						
						File file = new File(directory.getPath()+File.separator+arquivoTxt);
						
						System.out.println(file.getAbsolutePath());
						
						BufferedReader br = null;
						String linha = "";
						String quoteItemGrupo = "";
						boolean data = false;
						
						try {
							
							br = new BufferedReader(new FileReader(file));
							
							ImportQuoteTO quoteTO = new ImportQuoteTO();
							
							quoteTO.setFileType("txt");
							
							quoteTO.setQuoteItens(new ArrayList<ImportQuoteItemTO>());
							
							ImportQuoteItemTO quoteItemTO = null;
							
							int j = 0;

							quoteTO.setGrossRevenueTotal("0.00");
							quoteTO.setsCustomer("");
							quoteTO.setQuoteName("TXTQuote ID #"+arquivoTxt);
							
							while ((linha = br.readLine()) != null) {
							 	
								// Identifica quando o WebQuote esta na moeda BRL
								if(linha.contains("Real, BRL")){
									quoteTO.setQuoteInBRL(true);
								}
								
								if(linha.contains("Line #")){
									data = true;
								}else if(data){
									
									String[] s = linha.split("   ");
									
									for(int k = 0; k < s.length; k++){
										if(!s[k].isEmpty()){
											System.out.println(s[k]);
										}
									}	
									
								}else{
									continue;
								}
								
							}
							
						}catch (FileNotFoundException e) {
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
