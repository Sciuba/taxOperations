package br.com.saboia.controller;

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

import br.com.saboia.entity.TbAdmDestination;
import br.com.saboia.entity.TbAdmOrigin;
import br.com.saboia.entity.TbAdmOriginXProductTypeSV;
import br.com.saboia.entity.TbAdmOriginXTbAdmDestinaProtSt;
import br.com.saboia.entity.TbAdmProductType;
import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.DestinationFacade;
import br.com.saboia.facade.OriginFacade;
import br.com.saboia.facade.OriginXDestinaProtocolStFacade;
import br.com.saboia.facade.ProductTypeFacade;
import br.com.saboia.facade.impl.OriginXProductTypeSvFacadeImpl;
import br.com.saboia.facade.impl.SystemPermissionFacadeImpl;
import br.com.saboia.to.OriginXProductTypeSVTO;

@ManagedBean(name="originBBean")
@ViewScoped
public class SalesOriginBBean {
	
	@EJB
	private OriginFacade originFacade;
	
	@EJB
	private DestinationFacade destinationFacade;
	
	@EJB
	private OriginXDestinaProtocolStFacade originXDestinaProtocolStFacade;
	
	@EJB
	private ProductTypeFacade productTypeFacade;
	
	@EJB
	private OriginXProductTypeSvFacadeImpl originXProductTypeSvFacadeImpl;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	private List<TbAdmOrigin> listaOrigin;
	
	private TbAdmOrigin novoOrigin;
	
	private TbAdmOrigin originSelecionado;
	
	private ResourceBundle bundle;
	
	private List<TbAdmDestination> listaProtocol;
	
	private int colspan;
	
	private TbSysUser userLogged;
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "salesOrigin";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	/**
	 * Métodos
	 */
	
	@PostConstruct
	public void init(){
		
		listaOrigin = new ArrayList<>();
		listaProtocol = new ArrayList<>();
		novoOrigin = new TbAdmOrigin();
		originSelecionado = new TbAdmOrigin();
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		checkSecurity();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
		
		colspan = productTypeFacade.findProductTypeByTasModel(3); // 3 é a identificação de todos os product Type referente à serviço
		
		novoOrigin.setListaOriginXProductType(new ArrayList<OriginXProductTypeSVTO>());
		
		List<TbAdmProductType> list = productTypeFacade.findProductTypeByTaxModel(3);
		
		for(TbAdmProductType sv : list){
			
			OriginXProductTypeSVTO svto = new OriginXProductTypeSVTO();
			svto.setId(0);
			svto.setIdOrigin(0);
			svto.setIdProductType(sv.getId());
			svto.setrIss(0);
			svto.setDescription(sv.getsDescription());
			
			novoOrigin.getListaOriginXProductType().add(svto);
		}
		
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
	
	
	public void preparaLista(){
		listaOrigin = originFacade.findOrderBy();
		
		for(TbAdmOrigin origin : listaOrigin){
			
//			if(origin.getTbAdmProductTypeHw() != null && origin.getTbAdmProductTypeHw().getId() != 0){
//				origin.setProductTypeHW(true);
//			}else{
//				origin.setProductTypeHW(false);
//			}
//			
//			if(origin.getTbAdmProductTypeSw() != null && origin.getTbAdmProductTypeSw().getId() != 0){
//				origin.setProductTypeSW(true);
//			}else{
//				origin.setProductTypeSW(false);
//			}
//			
//			if(origin.getTbAdmProductTypeCs() != null && origin.getTbAdmProductTypeCs().getId() != 0){
//				origin.setProductTypeCS(true);
//			}else{
//				origin.setProductTypeCS(false);
//			}
//			
//			if(origin.getTbAdmProductTypePs() != null && origin.getTbAdmProductTypePs().getId() != 0){
//				origin.setProductTypePS(true);
//			}else{
//				origin.setProductTypePS(false);
//			}
//			
//			if(origin.getTbAdmProductTypeMd() != null && origin.getTbAdmProductTypeMd().getId() != 0){
//				origin.setProductTypeMD(true);
//			}else{
//				origin.setProductTypeMD(false);
//			}
			
			origin.setListaOriginXProductType(new ArrayList<OriginXProductTypeSVTO>());
			
			List<TbAdmOriginXProductTypeSV> list = originXProductTypeSvFacadeImpl.findByIdOrigin(origin.getId());
			
			for(TbAdmOriginXProductTypeSV sv : list){
				
				OriginXProductTypeSVTO svto = new OriginXProductTypeSVTO();
				svto.setId(sv.getId());
				svto.setIdOrigin(sv.getTbAdmOrigin().getId());
				svto.setIdProductType(sv.getTbAdmProductType().getId());
				svto.setrIss(sv.getrIss());
				svto.setDescription(sv.getTbAdmProductType().getsDescription());
				
				origin.getListaOriginXProductType().add(svto);
			}
			
		}
		
	}
	
	public void preparaListaDestination(){
		listaProtocol = destinationFacade.findOrderBy();
		
		if(originSelecionado.getTbAdmOriginXTbAdmDestinaProSt() != null && originSelecionado.getTbAdmOriginXTbAdmDestinaProSt().size() > 0){
			for(TbAdmDestination destination: listaProtocol){
				for(TbAdmOriginXTbAdmDestinaProtSt destinaProtSt: originSelecionado.getTbAdmOriginXTbAdmDestinaProSt()){
					if(destination.getId() == destinaProtSt.getTbAdmDestination().getId()){
						destination.setSelecionado(true);						
					}
				}
			}
		}
		
	}
	
	
	
	public void save(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(novoOrigin.getsCode() == null || novoOrigin.getsCode().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_code")), ""));
				
				listaOrigin = new ArrayList<>();
				
				return;
				
			}else if(novoOrigin.getsLocale() == null || novoOrigin.getsLocale().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_location")), ""));
				
				listaOrigin = new ArrayList<>();
				
				return;
				
			}else if((novoOrigin.getrIcmsInterno() < 0) || (novoOrigin.getrIssSoft() < 0) || (novoOrigin.getrIcmsInterEstadual() < 0)){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not have negative values!", ""));
				
				listaOrigin = new ArrayList<>();
				
				return;
				
			}else if((novoOrigin.getrIcmsInterno() > 100) || (novoOrigin.getrIssSoft() > 100) || (novoOrigin.getrIcmsInterEstadual() > 100)){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not be greater than 100!", ""));
				
				listaOrigin = new ArrayList<>();
				
				return;
				
			}else{
				
				try{
					
					TbAdmOrigin admOrigin = originFacade.findByCode(novoOrigin.getsCode());
					
					if(admOrigin != null){
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"There is already registered with this source code.", ""));
						
						listaOrigin = new ArrayList<>();
						
						return;
						
					}
					
				}catch(Exception e){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"There is already registered with this source code!", ""));
					
					listaOrigin = new ArrayList<>();
					
					return;
					
				}
				
				
				try{
					
	//				if(novoOrigin.isProductTypeHW()){
	//					novoOrigin.setTbAdmProductTypeHw(productTypeFacade.find(1L));
	//				}else{
	//					novoOrigin.setTbAdmProductTypeHw(null);
	//				}
	//				
	//				if(novoOrigin.isProductTypeSW()){
	//					novoOrigin.setTbAdmProductTypeSw(productTypeFacade.find(2L));
	//				}else{
	//					novoOrigin.setTbAdmProductTypeSw(null);
	//				}
	//				
	//				if(novoOrigin.isProductTypeCS()){
	//					novoOrigin.setTbAdmProductTypeCs(productTypeFacade.find(3L));
	//				}else{
	//					novoOrigin.setTbAdmProductTypeCs(null);
	//				}
	//				
	//				if(novoOrigin.isProductTypePS()){
	//					novoOrigin.setTbAdmProductTypePs(productTypeFacade.find(5L));
	//				}else{
	//					novoOrigin.setTbAdmProductTypePs(null);
	//				}
	//				
	//				if(novoOrigin.isProductTypeMD()){
	//					novoOrigin.setTbAdmProductTypeMd(productTypeFacade.find(7L));
	//				}else{
	//					novoOrigin.setTbAdmProductTypeMd(null);
	//				}
					
					for(OriginXProductTypeSVTO svto: novoOrigin.getListaOriginXProductType()){
						
						if(svto.getrIss() > 100){
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
									"Aliquots can not be greater than 100!", ""));
							
							listaOrigin = new ArrayList<>();
							
							return;
							
						}else if(svto.getrIss() < 0){
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
									"Aliquots can not have negative values!", ""));
							
							listaOrigin = new ArrayList<>();
							
							return;
							
						}
						
					}
					
					novoOrigin = originFacade.saveReturn(novoOrigin);
					
					for(OriginXProductTypeSVTO svto: novoOrigin.getListaOriginXProductType()){
						
						TbAdmOriginXProductTypeSV sv = new TbAdmOriginXProductTypeSV();
						
						sv.setrIss(svto.getrIss());
						sv.setTbAdmOrigin(novoOrigin);
						sv.setTbAdmProductType(productTypeFacade.find(svto.getIdProductType()));
						
						originXProductTypeSvFacadeImpl.save(sv);
						
					}
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
	
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('originDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					listaOrigin = new ArrayList<>();
					
					return;
					
				}
				
				List<TbAdmOriginXTbAdmDestinaProtSt> lista = new ArrayList<>();
				
				for(TbAdmDestination destination :listaProtocol){
					
					TbAdmOriginXTbAdmDestinaProtSt admDestinaProtSt = new TbAdmOriginXTbAdmDestinaProtSt();
					
					if(destination.isSelecionado()){				
						
						admDestinaProtSt.setTbAdmOrigin(novoOrigin);
						admDestinaProtSt.setTbAdmDestination(destination);
						
						try{
							
							admDestinaProtSt = originXDestinaProtocolStFacade.saveReturn(admDestinaProtSt);
							
						}catch(Exception e){
							e.printStackTrace();
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
									bundle.getString("save_error"), ""));
							
							listaOrigin = new ArrayList<>();
							
	
							//Fecho o modal da tela
							RequestContext context = RequestContext.getCurrentInstance();
							context.execute("PF('originDialog').hide();");
							
							//Atualizo o Form da tela
							FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
							
							return;
						}
						
						lista.add(admDestinaProtSt);
					}
				}
				
				
				novoOrigin.setTbAdmOriginXTbAdmDestinaProSt(lista);	
				
				try{
					originFacade.alter(novoOrigin);
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					listaOrigin = new ArrayList<>();
					listaProtocol = new ArrayList<>();
					novoOrigin = new TbAdmOrigin();
					
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('originDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					listaOrigin = new ArrayList<>();
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('originDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					return;
				}			
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('originDialog').hide();");
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}
	}
	
	
	
	public void alter(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if(originSelecionado.getsCode() == null || originSelecionado.getsCode().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_code")), ""));
				
				listaOrigin = new ArrayList<>();
				
				return;
				
			}else if(originSelecionado.getsLocale() == null || originSelecionado.getsLocale().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_location")), ""));
				
				listaOrigin = new ArrayList<>();
				
				return;
				
			}else if( (originSelecionado.getrIcmsInterno() < 0) || (originSelecionado.getrIssSoft() < 0) || (originSelecionado.getrIcmsInterEstadual() < 0)){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not have negative values!", ""));
				
				listaOrigin = new ArrayList<>();
				
				return;
				
			}else if((originSelecionado.getrIcmsInterno() > 100) || (originSelecionado.getrIssSoft() > 100) || (originSelecionado.getrIcmsInterEstadual() > 100)){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Aliquots can not be greater than 100!", ""));
				
				listaOrigin = new ArrayList<>();
				
				return;
				
			}else{
				
				
				try{
					
					TbAdmOrigin admOrigin = originFacade.findByCode(originSelecionado.getsCode());
					
					if(admOrigin.getId() != originSelecionado.getId()){
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"There is already registered with this source code.", ""));
						
						listaOrigin = new ArrayList<>();
						
						return;
						
					}
					
				}catch(Exception e){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"There is already registered with this source code!", ""));
					
					listaOrigin = new ArrayList<>();
					
					return;
					
				}
				
				
				for(OriginXProductTypeSVTO svto: originSelecionado.getListaOriginXProductType()){
					
					if(svto.getrIss() > 100){
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"Aliquots can not be greater than 100!", ""));
						
						listaOrigin = new ArrayList<>();
						
						return;
						
					}else if(svto.getrIss() < 0){
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"Aliquots can not have negative values!", ""));
						
						listaOrigin = new ArrayList<>();
						
						return;
						
					}else{
						
						TbAdmOriginXProductTypeSV sv = originXProductTypeSvFacadeImpl.findByIdOriginAndProductType(svto.getIdOrigin(), svto.getIdProductType());
						
						sv.setrIss(svto.getrIss());
						
						originXProductTypeSvFacadeImpl.update(sv);
						
					}
					
				}
				
				
				if(originSelecionado.isProductTypeHW()){
					originSelecionado.setTbAdmProductTypeHw(productTypeFacade.find(1L));
				}else{
					originSelecionado.setTbAdmProductTypeHw(null);
				}
				
				if(originSelecionado.isProductTypeSW()){
					originSelecionado.setTbAdmProductTypeSw(productTypeFacade.find(2L));
				}else{
					originSelecionado.setTbAdmProductTypeSw(null);
				}
				
				if(originSelecionado.isProductTypeCS()){
					originSelecionado.setTbAdmProductTypeCs(productTypeFacade.find(3L));
				}else{
					originSelecionado.setTbAdmProductTypeCs(null);
				}
				
				if(originSelecionado.isProductTypePS()){
					originSelecionado.setTbAdmProductTypePs(productTypeFacade.find(5L));
				}else{
					originSelecionado.setTbAdmProductTypePs(null);
				}
				
				if(originSelecionado.isProductTypeMD()){
					originSelecionado.setTbAdmProductTypeMd(productTypeFacade.find(7L));
				}else{
					originSelecionado.setTbAdmProductTypeMd(null);
				}
				
				List<TbAdmOriginXTbAdmDestinaProtSt> listaAdd = new ArrayList<>();
				List<TbAdmOriginXTbAdmDestinaProtSt> listaRemove = new ArrayList<>();
				
				boolean contain;
				
				for(TbAdmDestination destination :listaProtocol){
					
					TbAdmOriginXTbAdmDestinaProtSt admDestinaProtSt = new TbAdmOriginXTbAdmDestinaProtSt();
					
					if(destination.isSelecionado()){
						
						contain = false;
						
						for(TbAdmOriginXTbAdmDestinaProtSt protSt : originSelecionado.getTbAdmOriginXTbAdmDestinaProSt()){					
							if(destination.getId() != protSt.getTbAdmDestination().getId()){						
								contain = false;					
							}else{
								contain = true;
								break;
							}					
						}
						
						if(!contain){
							admDestinaProtSt.setTbAdmOrigin(originSelecionado);
							admDestinaProtSt.setTbAdmDestination(destination);
							
							try{
								
								admDestinaProtSt = originXDestinaProtocolStFacade.saveReturn(admDestinaProtSt);
							
							}catch(Exception e){
								e.printStackTrace();
								
								FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
										bundle.getString("save_error"), ""));
								
								listaOrigin = new ArrayList<>();
								
								//Fecho o modal da tela
								RequestContext context = RequestContext.getCurrentInstance();
								context.execute("PF('originEditDialog').hide();");
								
								//Atualizo o Form da tela
								FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
								
								return;
								
							}
		
							listaAdd.add(admDestinaProtSt);
						}
						
					}else{
						
						contain = false;
						
						for(TbAdmOriginXTbAdmDestinaProtSt protSt : originSelecionado.getTbAdmOriginXTbAdmDestinaProSt()){					
							if(destination.getId() != protSt.getTbAdmDestination().getId()){						
								contain = false;					
							}else{
								contain = true;
								listaRemove.add(protSt);
								break;
							}					
						}
									
					}
					
				}
				
				for(TbAdmOriginXTbAdmDestinaProtSt l : listaRemove){
					originSelecionado.getTbAdmOriginXTbAdmDestinaProSt().remove(l);
					
					try{
						
						originXDestinaProtocolStFacade.delete(l);
						
					}catch(Exception e){
						e.printStackTrace();
						
						FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								bundle.getString("save_error"), ""));
						
						listaOrigin = new ArrayList<>();
						
						//Fecho o modal da tela
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("PF('originEditDialog').hide();");
						
						//Atualizo o Form da tela
						FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
						
						return;					
					}
				}
				
				for(TbAdmOriginXTbAdmDestinaProtSt l : listaAdd){
					originSelecionado.getTbAdmOriginXTbAdmDestinaProSt().add(l);
				}
				
				try{
					
					originFacade.alter(originSelecionado);
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
					
					listaOrigin = new ArrayList<>();
					listaProtocol = new ArrayList<>();
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('originEditDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("save_error"), ""));
					
					listaOrigin = new ArrayList<>();
					
					//Fecho o modal da tela
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('originEditDialog').hide();");
					
					//Atualizo o Form da tela
					FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
					
					return;	
				}			
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('originEditDialog').hide();");
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");			
		}
	}
	
	
	public void delete(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			if((originSelecionado.getTbAdmDestinationsHw() == null || originSelecionado.getTbAdmDestinationsHw().isEmpty()) && 
					(originSelecionado.getTbAdmDestinationsMt() == null || originSelecionado.getTbAdmDestinationsMt().isEmpty()) && 
						(originSelecionado.getTbAdmDestinationsSv() == null || originSelecionado.getTbAdmDestinationsSv().isEmpty()) && 
							(originSelecionado.getTbAdmDestinationsSw() == null || originSelecionado.getTbAdmDestinationsSw().isEmpty())){
				
				if(originSelecionado.getTbAdmOriginXTbAdmDestinaProSt().size() > 0){
					List<TbAdmOriginXTbAdmDestinaProtSt> lista = new ArrayList<>();
					
					for(TbAdmOriginXTbAdmDestinaProtSt l : originSelecionado.getTbAdmOriginXTbAdmDestinaProSt()){
						lista.add(l);
					}
					
					for(int i = 0 ; i < lista.size() ; i++){
						originSelecionado.getTbAdmOriginXTbAdmDestinaProSt().remove(lista.get(i));
						
						try{
							originXDestinaProtocolStFacade.delete(lista.get(i));
						}catch(Exception e){
							e.printStackTrace();
							
							FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
									bundle.getString("delete_error"), ""));
							
							return;
						}
						
						lista.remove(i);
						
						i--;
					}
					
				}		
				
				try{
					
					originXProductTypeSvFacadeImpl.deleteAllByOrigin(originSelecionado.getId());
					
					originFacade.delete(originSelecionado);
					listaOrigin = new ArrayList<>();
					
					listaProtocol = new ArrayList<>();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("delete_success"), ""));
					
				}catch(Exception e){
					e.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							bundle.getString("delete_error"), ""));
					
					return;
				}
				
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("delete_error_fk"), ""));
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");			
		}
		
	}

	public void zeraLista(){
		listaProtocol = new ArrayList<>();
		originSelecionado = new TbAdmOrigin();
	}
	
	/**
	 * Getters and Setters
	 */	
	
	
	public List<TbAdmOrigin> getListaOrigin() {
		
		if(listaOrigin.size() == 0){
			preparaLista();
		}
		
		return listaOrigin;
	}

	public void setListaOrigin(List<TbAdmOrigin> listaOrigin) {
		this.listaOrigin = listaOrigin;
	}

	public TbAdmOrigin getNovoOrigin() {
		return novoOrigin;
	}

	public void setNovoOrigin(TbAdmOrigin novoOrigin) {
		this.novoOrigin = novoOrigin;
	}

	public TbAdmOrigin getOriginSelecionado() {
		return originSelecionado;
	}

	public void setOriginSelecionado(TbAdmOrigin originSelecionado) {
		this.originSelecionado = originSelecionado;
	}

	public List<TbAdmDestination> getListaProtocol() {
		
		preparaListaDestination();
			
		return listaProtocol;
	}

	public void setListaProtocol(List<TbAdmDestination> listaProtocol) {
		this.listaProtocol = listaProtocol;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
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
