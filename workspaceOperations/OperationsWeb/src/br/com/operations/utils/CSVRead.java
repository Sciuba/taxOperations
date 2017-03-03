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

public class CSVRead {

	private final static String FILEPATH = "Active/ImportFiles/importQuote";

	public static void run() {

		File directory = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(FILEPATH));

		System.out.println(directory.getAbsolutePath());

		if (directory.isDirectory()) {
			File[] listFiles = directory.listFiles();

			if (listFiles.length > 0) {

				for (int i = 0; i < listFiles.length; i++) {

					String arquivoCSV = listFiles[i].getName();
					
					if(arquivoCSV.contains("csv")){
						
						File file = new File(directory.getPath()+File.separator+arquivoCSV);
						
						System.out.println(file.getAbsolutePath());
						
						BufferedReader br = null;
						String linha = "";
						String csvDivisor = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
						String quoteItemGrupo = "";
						
						try {
	
							br = new BufferedReader(new FileReader(file));
							
							int line = 0;
							
							ImportQuoteTO quoteTO = null;
							ImportQuoteItemTO quoteItemTO = null;
							
							while ((linha = br.readLine()) != null) {
								
								if(line == 2){
									quoteTO = new ImportQuoteTO();
									String name[] = file.getName().split("_");
									try{
										quoteTO.setQuoteName(name[2].split("\\.")[0]);
									}catch(Exception e){
										
									}
									
									quoteTO.setQuoteItens(new ArrayList<ImportQuoteItemTO>());
								}
								
								String[] pais = linha.split(csvDivisor);
								
								if(pais.length == 1){
									if(!pais[0].isEmpty()){
										String linhaAlterada = linha.substring(0, linha.length() - 1).replace("\"\"", "\"");
										pais = linhaAlterada.split(csvDivisor);
									}
								}
								
								if(pais != null && pais.length != 0){
									for(int j = 0; j < pais.length; j++){
										
										if(line >= 2){
											if(line == 2){
												
												quoteTO.setGrossRevenueTotal(pais[9].replaceAll("[\"\']", ""));
												quoteTO.setAdrDiscountTotal(pais[19].replaceAll("[\"\']", ""));
												quoteTO.setTotalDiscount(pais[20].replaceAll("[\"\']", ""));
												quoteTO.setEscalation(pais[21].replaceAll("[\"\']", ""));
												quoteTO.setEstimatedSalesCreditTotal(pais[24].replaceAll("[\"\']", ""));
												quoteTO.setEstimatedSalesCreditPercentTotal(pais[25].replaceAll("[\"\']", ""));
												quoteTO.setNetRevenueTotal(pais[26].replaceAll("[\"\']", ""));
												
												break;
		
											}else if(line == 3){
												break;
											}else{
												
												quoteItemTO = new ImportQuoteItemTO();
												
												if(pais[j].contains("SUBTOTAL")){
													quoteItemGrupo = pais[j].split("-")[0].replace("\"", "");
													break;
												}
												
												if(!quoteItemGrupo.isEmpty()){
													quoteItemTO.setsGroupNumber(quoteItemGrupo);
												}
												
												if(pais.length > 1){
													quoteItemTO.setLine(pais[1].replaceAll("[\"\']", ""));
													quoteItemTO.setPartLineType(pais[2]);
													quoteItemTO.setPartDescription(pais[3]);
													quoteItemTO.setNamedProductCategory(pais[4]);
													quoteItemTO.setDiscCat(pais[5].replaceAll("[\"\']", ""));
													quoteItemTO.setLineOfBusiness(pais[6]);
													quoteItemTO.setQty(pais[7].replaceAll("[\"\']", ""));
													quoteItemTO.setUnitPrice(pais[8].replaceAll("[\"\']", ""));
													quoteItemTO.setGrossRevenue(pais[9].replaceAll("[\"\']", ""));
													quoteItemTO.setContractualDiscount(pais[11].replaceAll("[\"\']", ""));
													quoteItemTO.setAdrDiscount(pais[19].replaceAll("[\"\']", ""));
													quoteItemTO.setTotalDiscount(pais[20].replaceAll("[\"\']", ""));
													quoteItemTO.setEscalation(pais[21]);
													quoteItemTO.setEstimatedSalesCredit(pais[24].replaceAll("[\"\']", ""));
													quoteItemTO.setEstimatedSalesCreditPercent(pais[25].replaceAll("[\"\']", ""));
													quoteItemTO.setNetRevenue(pais[26].replaceAll("[\"\']", ""));
													
													quoteTO.getQuoteItens().add(quoteItemTO);
													
													break;
												}else{
													break;
												}
											}
											
										}else{
											break;
										}
									}
								}
								
								line++;
								
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
	
	/*public static void main(String[] args) {
		run();
	}*/
}
