package br.com.saboia.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.primefaces.model.LazyDataModel;

import br.com.saboia.entity.TbAdmDiscClass;
import br.com.saboia.entity.TbAdmMaterial;
import br.com.saboia.entity.TbAdmMaterialCategory;
import br.com.saboia.entity.TbAdmMaterialCategorySupport;
import br.com.saboia.entity.TbAdmMaterialClass;
import br.com.saboia.entity.TbAdmMaterialManufacturer;
import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.DiscClassFacade;
import br.com.saboia.facade.ManufacturerFacade;
import br.com.saboia.facade.MaterialClassFacade;
import br.com.saboia.facade.MaterialFacade;
import br.com.saboia.facade.impl.AccessProfileFacadeImpl;
import br.com.saboia.facade.impl.AccessProfileUserFacadeImpl;
import br.com.saboia.facade.impl.MaterialCategoryFacadeImpl;
import br.com.saboia.facade.impl.MaterialCategorySupportFacadeImpl;
import br.com.saboia.facade.impl.SystemPermissionFacadeImpl;
import br.com.saboia.lazyDataModel.MaterialLazyDataModel;

@ManagedBean(name="materialBBean")
@ViewScoped
public class MaterialBBean {

	@EJB
	private MaterialFacade materialFacade;
	
	@EJB
	private ManufacturerFacade manufacturerFacade;
	
	@EJB
	private DiscClassFacade discClassFacade;
	
	@EJB
	private MaterialClassFacade materialClassFacade;
	
	@EJB
	private MaterialLazyDataModel lazyDataModel;
	
	@EJB
	private AccessProfileFacadeImpl profileFacadeImpl;
	
	@EJB
	private AccessProfileUserFacadeImpl profileUserFacadeImpl;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	@EJB
	private MaterialCategoryFacadeImpl categoryFacadeImpl;
	
	@EJB
	private MaterialCategorySupportFacadeImpl supportFacadeImpl;
	
	private List<TbAdmMaterial> listaMaterial;
	
	private LazyDataModel<TbAdmMaterial> lazyModel;
	
	private List<TbAdmMaterialManufacturer> listaManufacturers;
	private List<TbAdmDiscClass> listaDiscount;
	private List<TbAdmMaterialClass> listaMaterialClass;
	
	private TbAdmMaterial novoMaterial;
	
	private TbAdmMaterial materialSelecionado;
	
	private String manufacturerSelecionado;
	private String classSelecionado;
	private String discountSelecionado;
	
	private String filtroSelecionado;
	private String filtroAlias;
	private String filtroPartNumber;
	private String filtroDescription;
	private String filtroNcm;
	
	private ResourceBundle bundle;
	
	private boolean hw = true;
	
	private TbSysUser userLogged;
	
	private List<TbSysSystemPermission> permissionList;
	
	private List<TbAdmMaterialCategory> listaCategory;
	private List<TbAdmMaterialCategorySupport> listaSupport;
	
	private final String SMODULE = "material";
	
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	
	/**
	 * Metodos
	 */
	
	@PostConstruct
	public void init(){
		listaMaterial = new ArrayList<>();
		listaDiscount = new ArrayList<>();
		listaManufacturers = new ArrayList<>();
		listaMaterialClass = new ArrayList<>();
		permissionList = new ArrayList<>();
		
		listaCategory = new ArrayList<TbAdmMaterialCategory>();
		listaSupport = new ArrayList<TbAdmMaterialCategorySupport>();
		
		sKey = "";
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		checkSecurity();
		
		filtroSelecionado = "3";
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("filtro", filtroSelecionado);
		
		novoMaterial = new TbAdmMaterial();
		
		materialSelecionado = new TbAdmMaterial();
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.saboia.bundle.messages", Locale.getDefault());
		
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
		
		lazyModel = lazyDataModel;
		
//		listaMaterial = materialFacade.findAll();
	}
	
	
	public void verificarVariacao(){
		
		if(!classSelecionado.equals("-1")){	
			
			TbAdmMaterialClass materialClass = materialClassFacade.find(Long.parseLong(classSelecionado));
			
			if(materialClass.getTbAdmProductType() != null && materialClass.getTbAdmProductType().getiTaxModel() == 1){
				hw = false;
				
				materialSelecionado.setTbAdmMaterialClass(materialClass);
				
			}else{
				hw = true;
				materialSelecionado.setfVariacaoPartNumber(false);
			}
		}		
	}
	
	public void resetFilter(){
		filtroSelecionado = "0";
		filtroAlias = "";
		filtroDescription = "";
		filtroPartNumber = "";
		filtroNcm = "";
		
		aplicarFiltro();
		
	}
	
	public void aplicarFiltro(){
		
		try{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("filtro");
		}catch(Exception e){
			
		}
		
		try{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("filtroAlias");
		}catch(Exception e){
			
		}
		
		try{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("filtroPartNumber");
		}catch(Exception e){
			
		}
			
		try{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("filtroDescription");
		}catch(Exception e){
			
		}
		
		try{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("filtroNCM");
		}catch(Exception e){
			
		}
		
		
		if(!filtroSelecionado.isEmpty() && !filtroSelecionado.equals("0")){
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("filtro", filtroSelecionado);
		}
		
		if(!filtroAlias.isEmpty()){
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("filtroAlias", filtroAlias);
		}
		
		if(!filtroPartNumber.isEmpty()){
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("filtroPartNumber", filtroPartNumber);
		}
		
		if(!filtroDescription.isEmpty()){
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("filtroDescription", filtroDescription);
		}
		
		if(!filtroNcm.isEmpty()){
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("filtroNCM", filtroNcm);
		}
				
	}
	
	
	public void preparaListaManufacturer(){
		listaManufacturers = manufacturerFacade.findAll();
		
		for(TbAdmMaterialManufacturer m: listaManufacturers){
			if(m.getsInternalId() != null && !m.getsInternalId().isEmpty()){
				m.setInternalIdDescription("("+m.getsInternalId()+")");
			}else{
				m.setInternalIdDescription("(Undefined ID)");
			}
			
			if(m.getTbAdmManufacturerStateOfOrigin() != null){
				m.setStateDescription("("+m.getTbAdmManufacturerStateOfOrigin().getsCode()+")");
			}else{
				m.setInternalIdDescription("(Undefined State)");
			}
		}
		
	}
	
	public void preparaListaMaterialClass(){
		listaMaterialClass = materialClassFacade.findOrder();
	}

	public void preparaListaDiscountClass(){
		listaDiscount = discClassFacade.findAll();
	}
	
	
	public void save(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			boolean msgAvaliable = false;
			
			novoMaterial.setfAvailable(true);
			
			if(!manufacturerSelecionado.equals("-1")){
				novoMaterial.setTbAdmMaterialManufacturer(manufacturerFacade.find(Long.parseLong(manufacturerSelecionado)));
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_manufacturer")),""));
				
				return;
			}
			
			if(!classSelecionado.equals("-1")){
				novoMaterial.setTbAdmMaterialClass(materialClassFacade.find(Long.parseLong(classSelecionado)));
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_ncm")),""));
				
				return;
			}
			
			if(novoMaterial.getsDescription() == null || novoMaterial.getsDescription().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")),""));
				
				return;
			}
			
			if(novoMaterial.getsInternalModel() == null || novoMaterial.getsInternalModel().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_part_number")),""));
				
				return;
			}
			
			if(novoMaterial.isfTaxSupportActive() && (novoMaterial.getrTaxSupport() == null || novoMaterial.getrTaxSupport() < 0F)){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), "Support Percentage"),""));
				
				return;
				
			}
			
			
			try{
				
				if(!novoMaterial.getCategory().equals("-1")){ 
					novoMaterial.setTbAdmMaterialCategory(categoryFacadeImpl.find(Long.parseLong(novoMaterial.getCategory())));
				}else{
					novoMaterial.setTbAdmMaterialCategory(null);
				}
				
				if(!novoMaterial.getSupport().equals("-1")){
					novoMaterial.setTbAdmMaterialCategorySupport(supportFacadeImpl.find(Long.parseLong(novoMaterial.getSupport())));
				}else{
					novoMaterial.setTbAdmMaterialCategorySupport(null);
				}
				
				TbAdmMaterial material = materialFacade.findQuery(novoMaterial.getsInternalModel());
				
				if(material != null){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
							"There is already stuff with this Part Number",""));
					
					return;
					
				}
				
			}catch(Exception e){
				
			}
			
			
			if(novoMaterial.isfAvailable()){
				
				if(novoMaterial.getTbAdmMaterialManufacturer() == null && novoMaterial.getTbAdmMaterialManufacturer().getId() == 0){
					msgAvaliable = true;
				}else if(novoMaterial.getTbAdmMaterialClass() == null && novoMaterial.getTbAdmMaterialClass().getId() == 0){
					msgAvaliable = true;
				}else if(novoMaterial.getfOrigemDaMercadoria() == null){
					msgAvaliable = true;
				}else if(novoMaterial.getsInternalModel() == null){
					msgAvaliable = true;
				}
				
			}
			
			try{
				
				if(!novoMaterial.isfTaxSupportActive()){
					materialSelecionado.setrTaxSupport(null);
				}
				
				novoMaterial.setDtItemCreationDate(new Date());
				
				materialFacade.save(novoMaterial);
				listaMaterial = new ArrayList<>();
				novoMaterial = new TbAdmMaterial();
				
				if(!msgAvaliable){
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
				}else{
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							"Successfully saved data, but the material was not active. Manufacturer, Class and Origin of the Material must be filled", ""));
					
				}
				
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('materialDialog').hide();");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('materialDialog').hide();");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('materialDialog').hide();");
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}
	}
	
	public void alter(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
		
			boolean msgAvaliable = false;
			
			if(!manufacturerSelecionado.equals("-1")){
				materialSelecionado.setTbAdmMaterialManufacturer(manufacturerFacade.find(Long.parseLong(manufacturerSelecionado)));
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_manufacturer")),""));
				
				return;
			}
			
			if(!classSelecionado.equals("-1")){
				materialSelecionado.setTbAdmMaterialClass(materialClassFacade.find(Long.parseLong(classSelecionado)));
				
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_ncm")),""));
				
				return;
			}
			
			if(materialSelecionado.getsDescription() == null || materialSelecionado.getsDescription().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_description")),""));
				
				return;
			}
			
			if(materialSelecionado.getsInternalModel() == null || materialSelecionado.getsInternalModel().isEmpty()){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), bundle.getString("label_part_number")),""));
				
				return;
			}
			
			
			if(materialSelecionado.isfTaxSupportActive() && (materialSelecionado.getrTaxSupport() == null || materialSelecionado.getrTaxSupport() < 0F)){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						MessageFormat.format(bundle.getString("field_required"), "Support Percentage"),""));
				
				return;
				
			}
			
			
			TbAdmMaterial material = materialFacade.findQuery(novoMaterial.getsInternalModel());
			
			if(material != null && (material.getId() != materialSelecionado.getId())){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						"There is already stuff with this Part Number",""));
				
				return;
				
			}
			
			
			if(materialSelecionado.isfAvailable()){
				
				if(materialSelecionado.getTbAdmMaterialManufacturer() == null || materialSelecionado.getTbAdmMaterialManufacturer().getId() == 0){
					msgAvaliable = true;
				}else if(materialSelecionado.getTbAdmMaterialClass() == null || materialSelecionado.getTbAdmMaterialClass().getId() == 0){
					msgAvaliable = true;
				}else if(materialSelecionado.getfOrigemDaMercadoria() == null){
					msgAvaliable = true;
				}else if(materialSelecionado.getsInternalModel() == null){
					msgAvaliable = true;
				}
			}
			
	//		if(!discountSelecionado.equals("-1")){
	//			materialSelecionado.setTbAdmDiscClass(discClassFacade.find(Long.parseLong(discountSelecionado)));
	//		}else{
	//			materialSelecionado.setTbAdmDiscClass(null);
	//		}
			
			try{
				
				if(!materialSelecionado.isfTaxSupportActive()){
					materialSelecionado.setrTaxSupport(null);
				}
				
				if(!materialSelecionado.getCategory().equals("-1")){
					materialSelecionado.setTbAdmMaterialCategory(categoryFacadeImpl.find(Long.parseLong(materialSelecionado.getCategory())));
				}else{
					materialSelecionado.setTbAdmMaterialCategory(null);
				}
				
				if(!materialSelecionado.getSupport().equals("-1")){
					materialSelecionado.setTbAdmMaterialCategorySupport(supportFacadeImpl.find(Long.parseLong(materialSelecionado.getSupport())));
				}else{
					materialSelecionado.setTbAdmMaterialCategorySupport(null);
				}
				
				materialSelecionado.setDtLastUpdate(new Date());
				
				materialSelecionado.setTbSysUser(userLogged);
				
				materialFacade.alter(materialSelecionado);
				listaMaterial = new ArrayList<>();
				materialSelecionado = new TbAdmMaterial();
				
				if(!msgAvaliable){
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							bundle.getString("save_success"), ""));
					
				}else{
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
							"Successfully saved data, but the material was not active. Manufacturer, Class and Origin of the Material must be filled", ""));
				}
				
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('materialEditDialog').hide();");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("save_error"), ""));
				
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('materialEditDialog').hide();");
				
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			}
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('materialEditDialog').hide();");
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
		}
	}
	
	public void delete(){
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
			
			try{
				
				materialFacade.delete(materialSelecionado);
				listaMaterial = new ArrayList<>();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, 
						bundle.getString("delete_success"), ""));
				
			}catch(Exception e){
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("delete_error"), ""));
			}
			
		}else if(disabled){
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
					"You are not permission for this task. Please contacts your System Administrator.", ""));
			
				//Atualizo o Form da tela
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
				
		}
	}
	
	
		

	/**
	 * getters and Setters
	 */
	

	public List<TbAdmMaterial> getListaMaterial() {
		
		if(listaMaterial.size() == 0){
			preparaLista();
		}
		
		return listaMaterial;
	}


	public void setListaMaterial(List<TbAdmMaterial> listaMaterial) {
		this.listaMaterial = listaMaterial;
	}

	public List<TbAdmMaterialManufacturer> getListaManufacturers() {
		
		if(listaManufacturers.size() == 0){
			preparaListaManufacturer();
		}
		
		return listaManufacturers;
	}

	public void setListaManufacturers(
			List<TbAdmMaterialManufacturer> listaManufacturers) {
		this.listaManufacturers = listaManufacturers;
	}

	public List<TbAdmDiscClass> getListaDiscount() {
		
		if(listaDiscount.size() == 0){
			preparaListaDiscountClass();
		}
		
		return listaDiscount;
	}

	public void setListaDiscount(List<TbAdmDiscClass> listaDiscount) {
		this.listaDiscount = listaDiscount;
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

	public TbAdmMaterial getNovoMaterial() {
		return novoMaterial;
	}


	public void setNovoMaterial(TbAdmMaterial novoMaterial) {
		this.novoMaterial = novoMaterial;
	}


	public TbAdmMaterial getMaterialSelecionado() {
		return materialSelecionado;
	}


	public void setMaterialSelecionado(TbAdmMaterial materialSelecionado) {
		this.materialSelecionado = materialSelecionado;
	}

	public String getManufacturerSelecionado() {
		
		if(materialSelecionado.getTbAdmMaterialManufacturer() == null){
			manufacturerSelecionado = "-1";
		}else{
			manufacturerSelecionado = String.valueOf(materialSelecionado.getTbAdmMaterialManufacturer().getId());
		}
		
		return manufacturerSelecionado;
	}

	public void setManufacturerSelecionado(String manufacturerSelecionado) {
		this.manufacturerSelecionado = manufacturerSelecionado;
	}

	public String getClassSelecionado() {
		
		if(materialSelecionado.getTbAdmMaterialClass() == null){
			classSelecionado = "-1";
		}else{
			classSelecionado = String.valueOf(materialSelecionado.getTbAdmMaterialClass().getId());
			if(materialSelecionado.getTbAdmMaterialClass().getTbAdmProductType() != null && materialSelecionado.getTbAdmMaterialClass().getTbAdmProductType().getiTaxModel() == 1){
				hw = false;
			}else{
				hw = true;
			}
		}
		
		return classSelecionado;
	}

	public void setClassSelecionado(String classSelecionado) {
		this.classSelecionado = classSelecionado;
	}

	public String getDiscountSelecionado() {
		
		if(materialSelecionado.getTbAdmDiscClass() == null){
			discountSelecionado = "-1";
		}else{
			discountSelecionado = String.valueOf(materialSelecionado.getTbAdmDiscClass().getId());
		}
		
		return discountSelecionado;
	}

	public void setDiscountSelecionado(String discountSelecionado) {
		this.discountSelecionado = discountSelecionado;
	}

	public LazyDataModel<TbAdmMaterial> getLazyModel() {
		
		if(lazyModel == null){
			preparaLista();
		}
		
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<TbAdmMaterial> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public String getFiltroSelecionado() {
		return filtroSelecionado;
	}

	public void setFiltroSelecionado(String filtroSelecionado) {
		this.filtroSelecionado = filtroSelecionado;
	}

	public boolean isHw() {
		return hw;
	}

	public void setHw(boolean hw) {
		this.hw = hw;
	}

	public String getFiltroAlias() {
		return filtroAlias;
	}

	public void setFiltroAlias(String filtroAlias) {
		this.filtroAlias = filtroAlias;
	}

	public String getFiltroPartNumber() {
		return filtroPartNumber;
	}

	public void setFiltroPartNumber(String filtroPartNumber) {
		this.filtroPartNumber = filtroPartNumber;
	}

	public String getFiltroDescription() {
		return filtroDescription;
	}

	public void setFiltroDescription(String filtroDescription) {
		this.filtroDescription = filtroDescription;
	}

	public String getFiltroNcm() {
		return filtroNcm;
	}

	public void setFiltroNcm(String filtroNcm) {
		this.filtroNcm = filtroNcm;
	}

	

	public List<TbSysSystemPermission> getPermissionList() {
		return permissionList;
	}


	public void setPermissionList(List<TbSysSystemPermission> permissionList) {
		this.permissionList = permissionList;
	}


	public String getsKey() {
		return sKey;
	}


	public void setsKey(String sKey) {
		this.sKey = sKey;
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


	public List<TbAdmMaterialCategory> getListaCategory() {
		
		if(listaCategory == null || listaCategory.isEmpty()){
			listaCategory = categoryFacadeImpl.findListNotDeleted();
		}
		
		return listaCategory;
	}


	public void setListaCategory(List<TbAdmMaterialCategory> listaCategory) {
		this.listaCategory = listaCategory;
	}


	public List<TbAdmMaterialCategorySupport> getListaSupport() {
		
		if(listaSupport == null || listaSupport.isEmpty()){
			listaSupport = supportFacadeImpl.findAll();
		}
		
		return listaSupport;
	}


	public void setListaSupport(List<TbAdmMaterialCategorySupport> listaSupport) {
		this.listaSupport = listaSupport;
	}
	
	
	
}
