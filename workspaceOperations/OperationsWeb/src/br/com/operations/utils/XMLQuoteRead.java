package br.com.operations.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.faces.context.FacesContext;

import br.com.operations.to.ImportQuoteItemTO;
import br.com.operations.to.ImportQuoteTO;

public class XMLQuoteRead {
	
	private final static String FILEPATH = "Active/ImportFiles/importQuote";

	public static void run() {

		File directory = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(FILEPATH));

		System.out.println(directory.getAbsolutePath());
		
		if (directory.isDirectory()) {
			File[] listFiles = directory.listFiles();

			if (listFiles.length > 0) {

				for (int i = 0; i < listFiles.length; i++) {

					String arquivoXml = listFiles[i].getName();
					
					if(arquivoXml.contains("xml")){
						
						File file = new File(directory.getPath()+File.separator+arquivoXml);
						
						System.out.println(file.getAbsolutePath());
						
						BufferedReader br = null;
						String linha = "";
						String quoteItemGrupo = "";
						boolean data = false;
						
						try {
							
							br = new BufferedReader(new FileReader(file));
							
							ImportQuoteTO quoteTO = new ImportQuoteTO();
							
							quoteTO.setFileType("xml");
							
							quoteTO.setQuoteItens(new ArrayList<ImportQuoteItemTO>());
							
							ImportQuoteItemTO quoteItemTO = null;
							
							int j = 0;

							quoteTO.setGrossRevenueTotal("0.00");
							quoteTO.setsCustomer("");
							quoteTO.setQuoteName("XMLQuote ID #"+arquivoXml);
							
							while ((linha = br.readLine()) != null) {
								
								if(linha.matches("Real")){
									quoteTO.setQuoteInBRL(true);
								}
								
								if(linha.startsWith("<lineItem")){
									j = 1;
									quoteItemTO = new ImportQuoteItemTO();
									
									if(linha.contains("isSupportLine=\"true\"")){
										quoteItemTO.setSupportLine(true);
									}
								}
								
								if(j == 1){
									
									if(linha.contains("<cartLineNumber>")){
										quoteItemTO.setLine(br.readLine());
									}
									
									if(linha.contains("<partNumber>")){
										quoteItemTO.setPartLineType(br.readLine());
									}
									
									if(linha.contains("<partDescription>")){
										quoteItemTO.setPartDescription(br.readLine());
									}
									
									if(linha.contains("<unitListPrice>")){
										quoteItemTO.setUnitPrice(br.readLine());
									}
									
									if(linha.contains("<quantity>")){
										quoteItemTO.setQty(br.readLine());
									}
									
									if(linha.contains("<extendedListPrice>")){
										//quoteItemTO.setNetRevenue(br.readLine());
										quoteItemTO.setListFee(br.readLine());
										j = 0;
										quoteTO.getQuoteItens().add(quoteItemTO);										
									}
									
								}
								
							}
							
							if(quoteTO != null){
								FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("importQuote", quoteTO);
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
