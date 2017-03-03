package br.com.operations.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.com.operations.entity.TbAdmDestination;
import br.com.operations.entity.TbAdmMaterialClass;
import br.com.operations.entity.TbAdmMaterialClassXDestination;
import br.com.operations.entity.TbAdmMaterialClassXOrigin;
import br.com.operations.entity.TbAdmProductType;
import br.com.operations.entity.TbSysSystemPermission;
import br.com.operations.entity.TbSysUser;
import br.com.operations.facade.DestinationFacade;
import br.com.operations.facade.MaterialClassFacade;
import br.com.operations.facade.MaterialClassXDestinationFacade;
import br.com.operations.facade.MaterialClassXOriginFacade;
import br.com.operations.facade.OriginFacade;
import br.com.operations.facade.ProductTypeFacade;
import br.com.operations.facade.impl.SystemPermissionFacadeImpl;

@ManagedBean(name="classBBean")
@ViewScoped
public class MaterialClassBBean {

	@EJB
	private MaterialClassFacade classFacade;
	
	@EJB
	private MaterialClassXDestinationFacade classXDestinationFacade;
	
	@EJB
	private  MaterialClassXOriginFacade classXOriginFacade;
	
	@EJB
	private OriginFacade originFacade;
	
	@EJB
	private DestinationFacade destinationFacade;
	
	@EJB
	private ProductTypeFacade productTypeFacade;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
//	private List<TbAdmOrigin> listaOrigin;
	
	private List<TbAdmDestination> listaDestination;
	
	private List<TbAdmMaterialClass> listaMaterialClass;
	
//	private List<TbAdmMaterialClassXOrigin> listaMaterialClassOrigin;
	
	private List<TbAdmMaterialClassXDestination> listaMaterialClassDestination;
	
	private List<TbAdmProductType> listaProduct;
	
	
	private TbAdmMaterialClass novoMaterialClass;
	
	private TbAdmMaterialClass materialClassSelecionado;
	
	private String productTypeSelecionado;
	
	private ResourceBundle bundle;

	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "materialClass";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	private TbSysUser userLogged;
	
	/**
	 * metodos
	 */
	
	@PostConstruct
	public void init(){
		listaMaterialClass = new ArrayList<>();
		listaMaterialClassDestination = new ArrayList<>();
//		listaMaterialClassOrigin = new ArrayList<>();
//		listaOrigin = new ArrayList<>();
		listaProduct = new ArrayList<>();
		listaDestination = new ArrayList<>();
		
		materialClassSelecionado = new TbAdmMaterialClass();
		novoMaterialClass = new TbAdmMaterialClass();
		
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		checkSecurity();
		
		preparaListas();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.operations.bundle.messages", Locale.getDefault());
	}
	
	
	public void checkSecurity(){
		
		sKey = "";
		disabled = false;
		disabledAll = false;
		
		permissionList = systemPermissionFacadeImpl.findPermissionByUserIdAndModule(userLogged.getId(), SMODULE);
		
		if(permissionList != null && !permissionList.isEmpty()){
			for(TbSysSystemPermission permission : permissionList){
				if(sKey.isEmpty() || !sKey.equals("WRITE")){
					sKey = permission.getsKey();
				}			
			}
		}
		
		if(sKey.equals("READ")){
			disabled = true;
		}else if(sKey.isEmpty()){
			disabledAll = true;
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not allowed to access this module. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}
		
	}
	
	
	public void preparaListaMaterialClass(){
		listaMaterialClass = classFacade.findAll();
	}
	
	
	public void preparaListas(){
//		listaOrigin = originFacade.findAll();
		listaDestination = destinationFacade.findListDestinationWithProtocol();
		listaProduct = productTypeFacade.findAll();
	}
	
	
//	public void preparaListaClassOrigin(){
//		
//		listaMaterialClassOrigin = new ArrayList<>();
//		
//		if(materialClassSelecionado.getId() == 0){
//			for(TbAdmOrigin ori: listaOrigin){
//				TbAdmMaterialClassXOrigin classXOrigin = new TbAdmMaterialClassXOrigin();
//				
//				classXOrigin.setTbAdmOrigin(ori);
//				
//				listaMaterialClassOrigin.add(classXOrigin);
//			}
//		}else if(materialClassSelecionado.getAdmMaterialClassXOrigins() == null || materialClassSelecionado.getAdmMaterialClassXOrigins().size() == 0){
//			
//			for(TbAdmOrigin ori: listaOrigin){
//				TbAdmMaterialClassXOrigin classXOrigin = new TbAdmMaterialClassXOrigin();
//				
//				classXOrigin.setTbAdmOrigin(ori);
//				
//				listaMaterialClassOrigin.add(classXOrigin);
//			}
//			
//		}else{
//			
//			boolean contain = false;
//			
//			for(TbAdmOrigin ori: listaOrigin){
//				for(TbAdmMaterialClassXOrigin x : materialClassSelecionado.getAdmMaterialClassXOrigins()){
//					if(ori.getId() == x.getTbAdmOrigin().getId()){
//						contain = true;
//						
//						listaMaterialClassOrigin.add(x);
//						
//						break;
//					}else{
//						contain = false;
//					}
//				}
//				
//				if(!contain){
//					
//					TbAdmMaterialClassXOrigin classXOrigin = new TbAdmMaterialClassXOrigin();
//					
//					classXOrigin.setTbAdmOrigin(ori);
//					
//					listaMaterialClassOrigin.add(classXOrigin);
//				}
//				
//			}
//			
//		}
//		
//	}
	
	public void preparaListaClassDestination(){
		
		listaMaterialClassDestination = new ArrayList<>();
		
		if(materialClassSelecionado.getId() == 0){
			for(TbAdmDestination dest: listaDestination){
				TbAdmMaterialClassXDestination classXDestinantion = new TbAdmMaterialClassXDestination();				
				
				classXDestinantion.setTbAdmDestination(dest);
				
				listaMaterialClassDestination.add(classXDestinantion);
			}
		}else if(materialClassSelecionado.getAdmMaterialClassXDestinations() == null || materialClassSelecionado.getAdmMaterialClassXDestinations().size() == 0){
			
			for(TbAdmDestination dest: listaDestination){
				TbAdmMaterialClassXDestination classXDestinantion = new TbAdmMaterialClassXDestination();
				
				classXDestinantion.setTbAdmDestination(dest);
				
				listaMaterialClassDestination.add(classXDestinantion);
			}
			
		}else{
			
			boolean contain = false;
			
			for(TbAdmDestination dest: listaDestination){
				for(TbAdmMaterialClassXDestination x : materialClassSelecionado.getAdmMaterialClassXDestinations()){
					if(dest.getId() == x.getTbAdmDestination().getId()){
						contain = true;
						
						listaMaterialClassDestination.add(x);
						
						break;
					}else{
						contain = false;
					}
				}
				
				if(!contain){
					
					TbAdmMaterialClassXDestination classXDestination = new TbAdmMaterialClassXDestination();
					
					classXDestination.setTbAdmDestination(dest);
					
					listaMaterialClassDestination.add(classXDestination);
				}
				
			}
			
		}
		
	}
	
	public void save(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(novoMaterialClass.getsNcm() == null || novoMaterialClass.getsNcm().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_ncm")), ""));
				
				listaMaterialClass = new ArrayList<>();
				
			}else if(novoMaterialClass.getsDescription() == null || novoMaterialClass.getsDescription().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")), ""));
				
				listaMaterialClass = new ArrayList<>();
				
			}else if(novoMaterialClass.getrIcms() == null || novoMaterialClass.getrIpi() == null){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Aliquots should be filled!", ""));
				
				listaMaterialClass = new ArrayList<>();
				
			}else if(novoMaterialClass.getrIcms() < 0 || novoMaterialClass.getrIpi() < 0){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not have negative values!", ""));
				
				listaMaterialClass = new ArrayList<>();
				
			}else if(novoMaterialClass.getrIcms() > 100 || novoMaterialClass.getrIpi() > 100 ){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not be greater than 100!", ""));
				
				listaMaterialClass = new ArrayList<>();
				
			}else{
				
				if(!productTypeSelecionado.equals("-1")){
					novoMaterialClass.setTbAdmProductType(productTypeFacade.find(Long.parseLong(productTypeSelecionado)));
				}
				
				try{
					
					TbAdmMaterialClass matClass = classFacade.findByNcm(novoMaterialClass.getsNcm());
					
					if(matClass != null){
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
								"Already exists with this record registered NCM.", ""));
						
						listaMaterialClass = new ArrayList<>();
						
						return;
					}
					
					novoMaterialClass = classFacade.saveReturn(novoMaterialClass);
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('classDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					return;
				}
			
			
	//			for(TbAdmMaterialClassXOrigin classXOrigin : listaMaterialClassOrigin ){
	//				
	//				not = false;
	//				
	//				if(classXOrigin.getrIcmsInterEstadual() != null){
	//					not = true;
	//				}else if(classXOrigin.getrIcmsInterno() != null){
	//					not = true;
	//				}else if(classXOrigin.getrIva() != null){
	//					not = true;
	//				}else if(classXOrigin.getrIvaMaterialImportado() != null){
	//					not = true;
	//				}
	//					
	//				if(not){
	//						
	//					try{
	//												
	//						classXOrigin.setTbAdmMaterialClass(novoMaterialClass);
	//						
	//						TbAdmMaterialClassXOrigin materialClassXOrigin = classXOriginFacade.saveReturn(classXOrigin);
	//						
	//						novoMaterialClass.getAdmMaterialClassXOrigins().add(materialClassXOrigin);
	//						
	//					}catch(Exception e){
	//						e.printStackTrace();
	//						
	//						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	//								bundle.getString("save_error"), ""));
	//					}
	//				}
	//				
	//			}
				
				for(TbAdmMaterialClassXDestination classXDestination : listaMaterialClassDestination ){
					
					int count = 0;
	
					if(classXDestination.getrIvaMaterialImportado() != null){
						count++;
						
						if(classXDestination.getrIvaMaterialImportado() < 0 || classXDestination.getrIvaMaterialImportado() > 100){
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
									"Values of aliquot 'RIVA Material Importado' must be 0 or greater", ""));
							
							listaMaterialClass = new ArrayList<>();
							
							return;
						}
						
					}
					
					if(classXDestination.getrIcmsInterEstadual() != null){
						count++;
						
						if(classXDestination.getrIcmsInterEstadual() < 0 || classXDestination.getrIcmsInterEstadual() > 100){
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
									"Values of aliquots must be between 0 - 100.", ""));
							
							listaMaterialClass = new ArrayList<>();
							
							return;
						}
						
						if(classXDestination.getrIvaMaterialImportado() != null && classXDestination.getrIvaMaterialImportado() > 0){
							if(classXDestination.getrIcmsInterEstadual() <= 0){
								
								FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
										"When the value of 'RIVA Material Importado' is greater that will be the 'InterState ICMS' can not be less than or equal to 0.", ""));
								
								listaMaterialClass = new ArrayList<>();
								
								return;
							}
						}
						
					}
					
					if(classXDestination.getrIcmsInterno() != null){
						count++;
						
						if(classXDestination.getrIcmsInterno() < 0 || classXDestination.getrIcmsInterno() > 100){
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
									"Values of aliquots must be between 0 - 100.", ""));
							
							listaMaterialClass = new ArrayList<>();
							
							return;
						}
						
						if(classXDestination.getrIvaMaterialImportado() != null && classXDestination.getrIvaMaterialImportado() > 0){
							if(classXDestination.getrIcmsInterno() <= 0){
								
								FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
										"When the value of 'RIVA Material Importado' is greater that will be the 'Internal ICMS' can not be less than or equal to 0.", ""));
								
								listaMaterialClass = new ArrayList<>();
								
								return;
							}
						}
						
					}
					
					if(count == 3 || count == 0){
						
						try{
							
							if(count != 0 && count == 3){
								classXDestination.setTbAdmMaterialClass(novoMaterialClass);
								
								TbAdmMaterialClassXDestination materialClassXDestination = classXDestinationFacade.saveReturn(classXDestination);
								
								novoMaterialClass.getAdmMaterialClassXDestinations().add(materialClassXDestination);
							}
							
						}catch(Exception e){
							e.printStackTrace();
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
									bundle.getString("save_error"), ""));
							
							//Fecho o modal da tela
							RequestContext context = RequestContext.getCurrentInstance();
							context.execute("PF('classDialog').hide();");
							
							//Atualizo o Form da tela
							FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
							
							return;
						}
					}else{
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
								"All aliquots must be fulfilled if any agreement with any state below.", ""));
						
						return;
					}	
					
				}
			
				try{
					
					classFacade.alter(novoMaterialClass);
					listaMaterialClass = new ArrayList<>();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					novoMaterialClass = new TbAdmMaterialClass();
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('classDialog').hide();");
					
					RequestContext.getCurrentInstance().execute("zerarFiltrosTable();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("classDetail");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");				
					
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('classDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					//RequestContext.getCurrentInstance().execute("zerarFiltrosTable();");
				}
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('classDialog').hide();");
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}	
		
	}
	
	
	public void alter(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(materialClassSelecionado.getsNcm() == null || materialClassSelecionado.getsNcm().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_ncm")), ""));
				
				listaMaterialClass = new ArrayList<>();
				
			}else if(materialClassSelecionado.getsDescription() == null || materialClassSelecionado.getsDescription().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")), ""));
				
				listaMaterialClass = new ArrayList<>();
				
			}else if(materialClassSelecionado.getrIcms() == null || materialClassSelecionado.getrIpi() == null){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"Aliquots should be filled!", ""));
				
				listaMaterialClass = new ArrayList<>();
				
			}else if(materialClassSelecionado.getrIcms() < 0 || materialClassSelecionado.getrIpi() < 0){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not have negative values!", ""));
				
				listaMaterialClass = new ArrayList<>();
				
			}else if(materialClassSelecionado.getrIcms() > 100 || materialClassSelecionado.getrIpi() > 100 ){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not be greater than 100!", ""));
				
				listaMaterialClass = new ArrayList<>();
				
			}else{
				
				
					TbAdmMaterialClass matClass = classFacade.findByNcm(materialClassSelecionado.getsNcm());
					
					if(matClass != null){
						if(materialClassSelecionado.getId() != matClass.getId()){
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
									"Already exists with this record registered NCM.", ""));
							
							listaMaterialClass = new ArrayList<>();
							
							return;
						}
					}
				
				
				
				if(!productTypeSelecionado.equals("-1")){
					materialClassSelecionado.setTbAdmProductType(productTypeFacade.find(Long.parseLong(productTypeSelecionado)));
				}else{
					materialClassSelecionado.setTbAdmProductType(null);
				}
				
				/*for(int i = 0; i < materialClassSelecionado.getAdmMaterialClassXOrigins().size(); i++){
					try{
						
						classXOriginFacade.delete(materialClassSelecionado.getAdmMaterialClassXOrigins().get(i));
						
						materialClassSelecionado.getAdmMaterialClassXOrigins().remove(i);
						
						i--;
						
					}catch(Exception e){
						e.printStackTrace();
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								bundle.getString("save_error"), ""));
						
						//Fecho o modal da tela
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("PF('classEditDialog').hide();");
						
						//Atualizo o Form da tela
						FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
						
						return;
					}
				}*/
				
				for(TbAdmMaterialClassXDestination classXDestination : listaMaterialClassDestination ){
					
					if(classXDestination.getrIvaMaterialImportado() != null){
						
						if(classXDestination.getrIvaMaterialImportado() < 0){
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
									"Values of aliquot 'RIVA Material Importado' must be 0 or greater.", ""));
							
							listaMaterialClass = new ArrayList<>();
							
							return;
						}
						
					}
					
					if(classXDestination.getrIcmsInterEstadual() != null){
						
						if(classXDestination.getrIcmsInterEstadual() < 0 || classXDestination.getrIcmsInterEstadual() > 100){
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
									"Values of aliquots must be between 0 - 100.", ""));
							
							listaMaterialClass = new ArrayList<>();
							
							return;
						}
						
						if(classXDestination.getrIvaMaterialImportado() != null && classXDestination.getrIvaMaterialImportado() > 0){
							if(classXDestination.getrIcmsInterEstadual() <= 0){
								
								FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
										"When the value of 'RIVA Material Importado' is greater that will be the 'InterState ICMS' can not be less than or equal to 0.", ""));
								
								listaMaterialClass = new ArrayList<>();
								
								return;
							}
						}
						
					}
					
					if(classXDestination.getrIcmsInterno() != null){
						
						if(classXDestination.getrIcmsInterno() < 0 || classXDestination.getrIcmsInterno() > 100){
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
									"Values of aliquots must be between 0 - 100.", ""));
							
							listaMaterialClass = new ArrayList<>();
							
							return;
						}
						
						if(classXDestination.getrIvaMaterialImportado() != null && classXDestination.getrIvaMaterialImportado() > 0){
							if(classXDestination.getrIcmsInterno() <= 0){
								
								FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
										"When the value of 'RIVA Material Importado' is greater that will be the 'Internal ICMS' can not be less than or equal to 0.", ""));
								
								listaMaterialClass = new ArrayList<>();
								
								return;
							}
						}
					}
				}
				
				for(int i = 0; i < materialClassSelecionado.getAdmMaterialClassXDestinations().size(); i++){
					try{
						
						classXDestinationFacade.delete(materialClassSelecionado.getAdmMaterialClassXDestinations().get(i));
						
						materialClassSelecionado.getAdmMaterialClassXDestinations().remove(i);
						
						i--;
						
					}catch(Exception e){
						e.printStackTrace();
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								bundle.getString("save_error"), ""));
						
						//Fecho o modal da tela
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("PF('classEditDialog').hide();");
						
						//Atualizo o Form da tela
						FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
						
						return;
					}
				}
				
	//			for(TbAdmMaterialClassXOrigin classXOrigin : listaMaterialClassOrigin ){
	//				
	//				not = false;
	//				
	//				if(classXOrigin.getrIcmsInterEstadual() != null){
	//					not = true;
	//				}else if(classXOrigin.getrIcmsInterno() != null){
	//					not = true;
	//				}else if(classXOrigin.getrIva() != null){
	//					not = true;
	//				}else if(classXOrigin.getrIvaMaterialImportado() != null){
	//					not = true;
	//				}
	//					
	//				if(not){
	//						
	//					try{
	//												
	//						classXOrigin.setTbAdmMaterialClass(materialClassSelecionado);
	//						
	//						if(classXOrigin.getrIcmsInterno() != null){
	//							TbAdmMaterialClassXOrigin materialClassXOrigin = classXOriginFacade.saveReturn(classXOrigin);
	//							
	//							materialClassSelecionado.getAdmMaterialClassXOrigins().add(materialClassXOrigin);
	//						}else{
	//							
	//							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
	//									MessageFormat.format(bundle.getString("protocol_required"), bundle.getString("label_icms_internal")), ""));
	//							
	//							return;
	//						}
	//						
	//					}catch(Exception e){
	//						e.printStackTrace();
	//						
	//						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	//								bundle.getString("save_error"), ""));
	//					}
	//				}
	//				
	//			}
				
				for(TbAdmMaterialClassXDestination classXDestination : listaMaterialClassDestination ){
					
					int count = 0;
					
					if(classXDestination.getrIvaMaterialImportado() != null){
						count++;
					}
					
					if(classXDestination.getrIcmsInterEstadual() != null){
						count++;
					}
					
					if(classXDestination.getrIcmsInterno() != null){
						count++;
					}
					
					if(count == 3 || count == 0){
						
						try{
													
							classXDestination.setTbAdmMaterialClass(materialClassSelecionado);
							
							if(count != 0 && count == 3){
								
								TbAdmMaterialClassXDestination materialClassXDestination = classXDestinationFacade.saveReturn(classXDestination);
								materialClassSelecionado.getAdmMaterialClassXDestinations().add(materialClassXDestination);
								
							}
							
						}catch(Exception e){
							e.printStackTrace();
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
									bundle.getString("save_error"), ""));
							
							//Fecho o modal da tela
							RequestContext context = RequestContext.getCurrentInstance();
							context.execute("PF('classEditDialog').hide();");
							
							//Atualizo o Form da tela
							FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
							
							return;						
						}
					}else{
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
								"All aliquots must be fulfilled if any agreement with any state below.", ""));
						
						return;
					}				
				}
			
				try{
					
					classFacade.alter(materialClassSelecionado);
					listaMaterialClass = new ArrayList<>();
					listaMaterialClassDestination = new ArrayList<>();
	//				listaMaterialClassOrigin = new ArrayList<>();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					materialClassSelecionado = new TbAdmMaterialClass();
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('classEditDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					RequestContext.getCurrentInstance().execute("zerarFiltrosTable();");
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('classEditDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					RequestContext.getCurrentInstance().execute("zerarFiltrosTable();");
				}
			}
		
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('classEditDialog').hide();");
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}
			
	}
	
	public void delete(){
		
		for(TbAdmMaterialClassXOrigin classXOrigin : materialClassSelecionado.getAdmMaterialClassXOrigins()){
			try{
				
				classXOriginFacade.delete(classXOrigin);
				
				materialClassSelecionado.getAdmMaterialClassXOrigins().remove(classXOrigin);
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				return;
			}
		}
		
		for(TbAdmMaterialClassXDestination classXDestination : materialClassSelecionado.getAdmMaterialClassXDestinations()){
			try{
				
				classXDestinationFacade.delete(classXDestination);
				
				materialClassSelecionado.getAdmMaterialClassXDestinations().remove(classXDestination);
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				return;
			}
		}
		
		try{
			classFacade.delete(materialClassSelecionado);
			listaMaterialClass = new ArrayList<>();
			listaMaterialClassDestination = new ArrayList<>();
//			listaMaterialClassOrigin = new ArrayList<>();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
					bundle.getString("delete_success"), ""));
			
			RequestContext.getCurrentInstance().execute("zerarFiltrosTable();");
			
		}catch(Exception e){
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					bundle.getString("delete_error"), ""));
		}
		
	}
	
	/**
	 * Getters and Setters
	 * @return
	 */
	
//	public List<TbAdmOrigin> getListaOrigin() {
//		return listaOrigin;
//	}
//
//	public void setListaOrigin(List<TbAdmOrigin> listaOrigin) {
//		this.listaOrigin = listaOrigin;
//	}

	public List<TbAdmDestination> getListaDestination() {
		return listaDestination;
	}

	public void setListaDestination(List<TbAdmDestination> listaDestination) {
		this.listaDestination = listaDestination;
	}

	public List<TbAdmMaterialClass> getListaMaterialClass() {
		
		if(listaMaterialClass.size() == 0){
			preparaListaMaterialClass();
		}
		
		return listaMaterialClass;
	}

	public void setListaMaterialClass(List<TbAdmMaterialClass> listaMaterialClass) {
		this.listaMaterialClass = listaMaterialClass;
	}

	public TbAdmMaterialClass getNovoMaterialClass() {
		return novoMaterialClass;
	}

	public void setNovoMaterialClass(TbAdmMaterialClass novoMaterialClass) {
		this.novoMaterialClass = novoMaterialClass;
	}

	public TbAdmMaterialClass getMaterialClassSelecionado() {
		
//		preparaListaClassOrigin();
		preparaListaClassDestination();
		
		return materialClassSelecionado;
	}

	public void setMaterialClassSelecionado(
			TbAdmMaterialClass materialClassSelecionado) {
		this.materialClassSelecionado = materialClassSelecionado;
	}

//	public List<TbAdmMaterialClassXOrigin> getListaMaterialClassOrigin() {
//		
//		if(listaMaterialClassOrigin.size() == 0){
//			preparaListaClassOrigin();
//		}
//		
//		return listaMaterialClassOrigin;
//	}
//
//	public void setListaMaterialClassOrigin(
//			List<TbAdmMaterialClassXOrigin> listaMaterialClassOrigin) {
//		this.listaMaterialClassOrigin = listaMaterialClassOrigin;
//	}

	public List<TbAdmMaterialClassXDestination> getListaMaterialClassDestination() {
		
		if(listaMaterialClassDestination.size() == 0){
			preparaListaClassDestination();
		}
		
		return listaMaterialClassDestination;
	}

	public void setListaMaterialClassDestination(
			List<TbAdmMaterialClassXDestination> listaMaterialClassDestination) {
		this.listaMaterialClassDestination = listaMaterialClassDestination;
	}

	public List<TbAdmProductType> getListaProduct() {
		return listaProduct;
	}

	public void setListaProduct(List<TbAdmProductType> listaProduct) {
		this.listaProduct = listaProduct;
	}


	public String getProductTypeSelecionado() {
		
		if(materialClassSelecionado.getTbAdmProductType() != null && materialClassSelecionado.getTbAdmProductType().getId() > 0){
			productTypeSelecionado = String.valueOf(materialClassSelecionado.getTbAdmProductType().getId());
		}else{
			productTypeSelecionado = "-1";
		}
		
		return productTypeSelecionado;
	}


	public void setProductTypeSelecionado(String productTypeSelecionado) {
		this.productTypeSelecionado = productTypeSelecionado;
	}


	public boolean isDisabled() {
		return disabled;
	}


	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}


	public boolean isDisabledAll() {
		return disabledAll;
	}


	public void setDisabledAll(boolean disabledAll) {
		this.disabledAll = disabledAll;
	}
			
	
}
