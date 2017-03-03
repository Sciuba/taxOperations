package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmMaterialImportTemp;

@Local
public interface MaterialImportTempDAO {
	
	List<TbAdmMaterialImportTemp> findAll();
	
	void deleteDuplicates(String sqlQuery);
	
	void validaLocalizaNcmImportado(String sqlQuery);
	
	void validaProcessoImportMaterial(String sqlQuery);
	
	void save(TbAdmMaterialImportTemp importTemp);
	
	void alter(TbAdmMaterialImportTemp importTemp);
	
	TbAdmMaterialImportTemp saveReturn(TbAdmMaterialImportTemp importTemp);
	
	void delete(TbAdmMaterialImportTemp importTemp);
	
	void deleteAll(String Query);
	
	void saveNew(TbAdmMaterialImportTemp lista);
}
