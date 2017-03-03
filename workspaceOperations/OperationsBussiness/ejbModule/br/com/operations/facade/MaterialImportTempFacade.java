package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmMaterialImportTemp;

@Local
public interface MaterialImportTempFacade {
	
	List<TbAdmMaterialImportTemp> findAll();
	
	void deleteDuplicates();
	
	void validaLocalizaNcmImportado();
	
	void validaLocalizaMaterialImportado();
	
	void save(TbAdmMaterialImportTemp importTemp);
	
	void alter(TbAdmMaterialImportTemp importTemp);
	
	TbAdmMaterialImportTemp saveReturn(TbAdmMaterialImportTemp importTemp);
	
	void deleteAll();
	
	void delete(TbAdmMaterialImportTemp importTemp);
	
	void insertQuery(List<TbAdmMaterialImportTemp> listaInsert);

}
