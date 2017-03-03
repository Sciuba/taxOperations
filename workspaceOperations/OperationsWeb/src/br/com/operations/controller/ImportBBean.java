package br.com.operations.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.operations.entity.TbSysSystemPermission;
import br.com.operations.entity.TbSysUser;
import br.com.operations.facade.MaterialFacade;
import br.com.operations.facade.MaterialImportTempFacade;
import br.com.operations.facade.impl.SystemPermissionFacadeImpl;
import br.com.operations.utils.ImportMaterial;

@ManagedBean(name="importBBean")
@ViewScoped
public class ImportBBean {
	
	@EJB
	private MaterialFacade materialFacade;
	
	@EJB
	private MaterialImportTempFacade importTempFacade;
	
	@EJB
	private ImportMaterial importMaterial;
	
	@EJB
	private SystemPermissionFacadeImpl  systemPermissionFacadeImpl;
	
	private StreamedContent fileDownload;
	
	private File file;
	
	private File fileImport;
	
	private boolean enable;
	
	private ResourceBundle bundle;
	
	private List<TbSysSystemPermission> permissionList;
	
	/**
	 * Token para carregar as permissoes do módulo conforme o usuário logado
	 */
	private final String SMODULE = "import";
		
	private String sKey;
	
	private boolean disabled;
	
	private boolean disabledAll;
	
	private TbSysUser userLogged;
	
	
	@PostConstruct
	public void init(){
		
		Locale.setDefault(new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.operations.bundle.messages", Locale.getDefault());
		
		permissionList = new ArrayList<>();
		
		sKey = "";
		
		LoginBBean login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged = login.getUser();
		
		checkSecurity();
		
		try{
			
			importTempFacade.deleteAll();
			
			String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("Active/ImportFiles/importMaterial");
			
			fileImport = new File(caminho + File.separator + "master.txt");
			file = new File(caminho + File.separator + "master.xls");
			
			if(file.exists()){
				if(file.length() > 100000000){
					file.renameTo(fileImport);
				}
				enable = false;
				InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/Active/ImportFiles/importMaterial/master.xls");
				fileDownload = new DefaultStreamedContent(stream, "application/xls", "master.xls");
			}else{
				
				enable = true;
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, bundle.getString("msg_import_material"), ""));				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
			enable = true;
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, bundle.getString("msg_import_material"), ""));
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

	public String importXLS() throws IOException{
		
		checkSecurity();
		
		if(!disabled && !disabledAll){
			boolean ok = false;
			
			try{
				
				if(fileImport.length() > 100000000){
					ok = importMaterial.importMaterial(fileImport);
					
				}else{
					
					ok = true;
					
					importMaterial.importXLS(file);					
				}
				
				
				
			}catch(Exception e){
				
				e.printStackTrace();
				
			}
			
			if(ok){
				
				GregorianCalendar gc = new GregorianCalendar();
				int dia = gc.get(Calendar.DAY_OF_MONTH);
				int mes = gc.get(Calendar.MONTH) + 1;
				int year = gc.get(Calendar.YEAR);
				
				String data = year+"_"+mes+"_"+dia;
				
				String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("Active/ImportFiles/importMaterial");
				
				File fileImported = new File(caminho + File.separator + "master_"+data+".xls");
				
				fileImport.renameTo(fileImported);
				
				verificaCamposRepetidosTabelaImport();
				
			}else{
				
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						bundle.getString("msg_error_import"), ""));
				
				return "ERRO";
				
			}
	        
			return "success";
			
		}else if(disabled){
			
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, 
				"You are not permission for this task. Please contacts your System Administrator.", ""));
			
			//Atualizo o Form da tela
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
			
			return "ERRO";
		}else{
			return "ERRO";
		}
	}
	
	
	public void verificaCamposRepetidosTabelaImport(){
		
		try{
			
			importTempFacade.deleteDuplicates();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public StreamedContent getFileDownload() {
		return fileDownload;
	}


	public void setFileDownload(StreamedContent fileDownload) {
		this.fileDownload = fileDownload;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public boolean isEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
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
