package br.com.saboia.utils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import br.com.saboia.app.importMaterial.ImportMaterialJDBC;
import br.com.saboia.entity.TbAdmMaterialImportTemp;
import br.com.saboia.enums.MaterialEnum;
import br.com.saboia.facade.MaterialFacade;
import br.com.saboia.facade.MaterialImportTempFacade;

@Stateful
public class ImportMaterial {
	
	@EJB
	private MaterialFacade materialFacade;
	
	
	@EJB
	private MaterialImportTempFacade importTempFacade;
	
	@EJB
	private ImportMaterialJDBC importMaterialJDBC;
	
	
	public synchronized boolean importMaterial(File file) throws IOException{
		
		boolean ok = false;
		
		TbAdmMaterialImportTemp material = new TbAdmMaterialImportTemp();
		
		LineIterator it = FileUtils.lineIterator(file);
		
		try {
			
			int count = 1;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			
			List<TbAdmMaterialImportTemp> listaInsert = new ArrayList<>();
			
			while (it.hasNext()) {
		        String line = it.nextLine();
		        if(line.contains("<td valign=")){
		        	
		        	String txt = "";
		        	
		        	try{
						txt = line.split(">")[3].split("</")[0];
		        	}catch(Exception e){
		        		txt = "";
		        	}
		        	
		        	if(txt.isEmpty() || !MaterialEnum.isColumnCorrect(txt.trim())){
		        		
		        		ok = true;
		        		
		        		try{
			        		
			        		if(count > 16){
			        			material = new TbAdmMaterialImportTemp();
			        			count = 1;
			        		}
			        		
			        		if(count == 1){
			        			try{
			        				material.setsInternalModel(line.split(">")[5].split("</")[0]);
			        			}catch(Exception e){
			        				try{
			        					material.setsInternalModel(line.split(">")[3].split("</")[0]);
			        				}catch(Exception e1){
			        					material.setsInternalModel(null);
			        				}
			        			}
			        		}else if(count == 2){
			        			try{
			        				material.setsDescription(line.split(">")[3].split("</")[0]);
			        			}catch(Exception e){
		        					material.setsDescription(null);
		        				}
			        		}else if(count == 3){
			        			try{
			        				material.setsPrimaryUnitMeasure(line.split(">")[3].split("</")[0]);
			        			}catch(Exception e){
		        					material.setsPrimaryUnitMeasure(null);
		        				}
			        		}else if(count == 4){
			        			try{
			        				material.setsLifeCyclePhase(line.split(">")[3].split("</")[0]);
			        			}catch(Exception e){
		        					material.setsLifeCyclePhase(null);
		        				}
			        		}else if(count == 5){
			        			try{
			        				material.setsItemType(line.split(">")[3].split("</")[0]);
			        			}catch(Exception e){
		        					material.setsItemType(null);
		        				}
			        		}else if(count == 6){
			        			try{
			        				material.setsItemStatus(line.split(">")[3].split("</")[0]);
			        			}catch(Exception e){
		        					material.setsItemStatus(null);
		        				}
			        		}else if(count == 7){
			        			try{
			        				material.setsCostumerOrderFlag(line.split(">")[3].split("</")[0]);
			        			}catch(Exception e){
		        					material.setsCostumerOrderFlag(null);
		        				}
			        		}else if(count == 8){
			        			try{
			        				material.setsInternalOrderFlag(line.split(">")[3].split("</")[0]);
			        			}catch(Exception e){
			        				material.setsInternalOrderFlag(null);
			        			}
			        		}else if(count == 9){
			        			try{
			        				if(line.split(">")[3].split("</")[0].trim().equals("847150XX")){
			        					material.setFiscalClassificationCode("84715010");
			        				}else{
			        					material.setFiscalClassificationCode(line.split(">")[3].split("</")[0]);
			        				}
			        			}catch(Exception e){
			        				material.setFiscalClassificationCode(null);
			        			}
			        		}else if(count == 10){
			        			try{
			        				material.setsTransactionConditionClass(line.split(">")[3].split("</")[0]);
			        			}catch(Exception e){
			        				material.setsTransactionConditionClass(null);
			        			}
			        		}else if(count == 11){
			        			try{
			        				material.setItemOrigin(line.split(">")[3].split("</")[0]);
			        			}catch(Exception e){
			        				material.setItemOrigin(null);
			        			}
			        		}else if(count == 12){
			        			try{
			        				material.setsItemFiscalType(line.split(">")[3].split("</")[0]);
			        			}catch(Exception e){
			        				material.setsItemFiscalType(null);
			        			}
			        		}else if(count == 13){
			        			try{
									material.setiFederalTributarySituation(Integer.parseInt(line.split(">")[3].split("</")[0]) == 0 ? null : Integer.parseInt(line.split(">")[3].split("</")[0]));
								}catch(Exception nfe){
									material.setiFederalTributarySituation(null);
								}
			        		}else if(count == 14){
			        			try{
									material.setiStateTributarySituation(Integer.parseInt(line.split(">")[3].split("</")[0]) == 0 ? null : Integer.parseInt(line.split(">")[3].split("</")[0]));
								}catch(Exception nfe){
									material.setiStateTributarySituation(null);
								}
			        		}else if(count == 15){
			        			try{
			        				material.setDtItemCreationDate(convertStringToDate(line.split(">")[3].split("</")[0]));
			        			}catch(Exception nfe){
									material.setDtItemCreationDate(null);
								}
			        		}else if(count == 16){
			        			try{
			        				material.setDtLastUpdate(convertStringToDate(line.split(">")[3].split("</")[0]));
			        			}catch(Exception nfe){
									material.setDtLastUpdate(null);
								}
			        			
			        			try{
									
			        							        				
			        				material.setfOrigemDaMercadoria(2); //Todo material é de origem importada.
			        					
		        					if(listaInsert.size() < 50){
			        				
			        					listaInsert.add(material);
			        					
			        				}else{
			        					
			        					listaInsert.add(material);
			        					
			        					importMaterialJDBC.insertListMaterials(listaInsert);
			        					
			        					listaInsert = new ArrayList<>();
			        					
			        				}
			        				
								}catch(Exception e){
									e.printStackTrace();
									
									FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
											"Error writing the data.", ""));
									
									return false;
								}	
			        			
			        		}
			        		
			        	}catch(Exception e){
			        		e.printStackTrace();
			        		
			        		return false;
			        	}
		        		
		        		 count++;
		        	}
		        }
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally {
		    LineIterator.closeQuietly(it);
		}
		
		return ok;
		
	}
	
	
	public Date convertStringToDate(String date){
		
		SimpleDateFormat sdfUnderscore = new SimpleDateFormat("dd-MMM-yyyy", new Locale("pt", "BR"));
		SimpleDateFormat sdfUnderscoreTwo = new SimpleDateFormat("dd/MMM/yyyy", new Locale("pt", "BR"));
		SimpleDateFormat sdfBar = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
		SimpleDateFormat sdfPerson = new SimpleDateFormat("MMM D, yyyy", new Locale("pt", "BR"));
		
		Date data = null;
		
		try{
			data = sdfUnderscore.parse(date);
		}catch(Exception e){
			
			try{
				data = sdfUnderscoreTwo.parse(date.replace("-", "/"));
				
			}catch(ParseException e3){
				try {
					data = sdfBar.parse(date);
					
				} catch (ParseException e1) {
					
					try{
						data = sdfPerson.parse(date);
						
					}catch (ParseException e2) {
						e2.printStackTrace();
					}
				}
			}
		}
		
		return data;
		
	}
	
	
	public void importXLS(File file) {
		
		TbAdmMaterialImportTemp material = new TbAdmMaterialImportTemp();
		
		/* pega o arkivo do Excel */  
		try{
			Workbook workbook = Workbook.getWorkbook(file);    
		
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			
			List<TbAdmMaterialImportTemp> listaInsert = new ArrayList<>();
			
			
			/* pega a primeira planilha dentro do arquivo XLS */  
			Sheet sheet = workbook.getSheet(0);   
			
			
			for(int i = 1; i <= sheet.getRows(); i++){
				for(int j = 0 ; j < sheet.getColumns(); j++){
					
					if(j == 0){
						material = new TbAdmMaterialImportTemp();
					}
        								
					/* pega os valores das célular como se numa matriz */  
					try{
						Cell a1 = sheet.getCell(j,i);  
					
						
						if(j == 0){
							material.setsInternalModel(a1.getContents());
							continue;
						}else if(j == 1){
							material.setsDescription(a1.getContents());
							continue;
						}else if(j == 2){
							material.setsPrimaryUnitMeasure(a1.getContents());
							continue;
						}else if(j == 3){
							material.setsLifeCyclePhase(a1.getContents());
							continue;
						}else if(j == 4){
							material.setsItemType(a1.getContents());
							continue;
						}else if(j == 5){
							material.setsItemStatus(a1.getContents());		
							continue;
						}else if(j == 6){
							material.setsCostumerOrderFlag(a1.getContents());
							continue;
						}else if(j == 7){
							material.setsInternalOrderFlag(a1.getContents());
							continue;
						}else if(j == 8){
							if(a1.getContents().trim().equals("847150XX")){
								material.setFiscalClassificationCode("84715010");							
							}else{
								material.setFiscalClassificationCode(a1.getContents());
							}
							continue;
						}else if(j == 9){
							material.setsTransactionConditionClass(a1.getContents());
							continue;
						}else if(j == 10){
							material.setItemOrigin(a1.getContents());
							continue;
						}else if(j == 11){
							material.setsItemFiscalType(a1.getContents());
							continue;
						}else if(j == 12){
							try{
								material.setiFederalTributarySituation(Integer.valueOf(a1.getContents()));
								continue;
							}catch(Exception e){
								material.setiFederalTributarySituation(0);
								continue;
							}
						}else if(j == 13){
							try{
								material.setiStateTributarySituation(Integer.valueOf(a1.getContents()));
								continue;
							}catch(Exception e){
								material.setiStateTributarySituation(0);
								continue;
							}
						}else if(j == 14){
							try{
								material.setDtItemCreationDate(convertStringToDate(a1.getContents()));
								continue;
							}catch(Exception e){
								material.setDtItemCreationDate(null);
								continue;
							}
						}else if(j == 15){
							try{
								material.setDtLastUpdate(convertStringToDate(a1.getContents()));
							}catch(Exception e){
								material.setDtLastUpdate(null);
							}
							
							material.setfOrigemDaMercadoria(2); //Todo material é de origem importada.
							
							listaInsert.add(material);
							
							material = new TbAdmMaterialImportTemp();
							
							if(listaInsert.size() > 50){
								
								importMaterialJDBC.insertListMaterials(listaInsert);
								
								listaInsert = new ArrayList<>();
								
							}
						}
						
					}catch(Exception e){
						
						if(listaInsert.size() != 0){
							
							importMaterialJDBC.insertListMaterials(listaInsert);
							
							listaInsert = new ArrayList<>();
							
							material = new TbAdmMaterialImportTemp();
						}
						
						break;
					}
					
				}
				
			}		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		
		ImportMaterial imp = new ImportMaterial();
		
		//imp.importXLS();
		
	}
	
	
}
