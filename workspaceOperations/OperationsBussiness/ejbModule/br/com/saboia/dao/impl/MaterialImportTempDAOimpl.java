package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.dao.MaterialImportTempDAO;
import br.com.saboia.entity.TbAdmMaterialImportTemp;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class MaterialImportTempDAOimpl extends
		GenericDAO<TbAdmMaterialImportTemp> implements MaterialImportTempDAO {

	public MaterialImportTempDAOimpl() {
		super(TbAdmMaterialImportTemp.class);
	}

	@Override
	public List<TbAdmMaterialImportTemp> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmMaterialImportTemp importTemp) {
		super.save(importTemp);
	}

	@Override
	public void deleteAll(String Query) {
		super.deleteAll(Query);
	}

	@Override
	public TbAdmMaterialImportTemp saveReturn(TbAdmMaterialImportTemp entity) {
		return super.saveReturn(entity);
	}

	@Override
	public void alter(TbAdmMaterialImportTemp importTemp) {
		super.update(importTemp);		
	}

	
	@Override
	public void delete(TbAdmMaterialImportTemp entity) {
		Object object = entity.getId();
		super.delete(object, TbAdmMaterialImportTemp.class);
	}

	@Override
	public void deleteDuplicates(String sqlQuery) {
		super.deleteNativeQuery(sqlQuery);		
	}

	@Override
	public void validaLocalizaNcmImportado(String sqlQuery) {
		super.executeNativeQuery(sqlQuery);		
	}

	@Override
	public void validaProcessoImportMaterial(String sqlQuery) {
		super.executeNativeQuery(sqlQuery);
	}

	@Override
	public void saveNew(TbAdmMaterialImportTemp entity) {
		super.saveNew(entity);				
	}
}
