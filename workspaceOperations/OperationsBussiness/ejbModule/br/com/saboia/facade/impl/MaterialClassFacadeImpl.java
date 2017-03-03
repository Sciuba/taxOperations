package br.com.saboia.facade.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmMaterialClassDAO;
import br.com.saboia.entity.TbAdmMaterialClass;
import br.com.saboia.facade.MaterialClassFacade;

@Stateless
public class MaterialClassFacadeImpl implements MaterialClassFacade {

	@EJB
	private AdmMaterialClassDAO materialClassDAO;
	
	@Override
	public List<TbAdmMaterialClass> findAll() {
		return materialClassDAO.findAll();
	}

	@Override
	public void save(TbAdmMaterialClass materialClass) {
		materialClassDAO.save(materialClass);
	}

	@Override
	public void alter(TbAdmMaterialClass materialClass) {
		materialClassDAO.alter(materialClass);
	}

	@Override
	public void delete(TbAdmMaterialClass materialClass) {
		materialClassDAO.delete(materialClass);
	}

	@Override
	public TbAdmMaterialClass find(Long id) {
		return materialClassDAO.find(id);
	}

	@Override
	public TbAdmMaterialClass saveReturn(TbAdmMaterialClass materialClass) {
		return materialClassDAO.saveReturn(materialClass);
	}

	@Override
	public TbAdmMaterialClass findQuery(String namedQuery,
			Map<String, Object> parameters) {
		return null;
	}
	
	public List<TbAdmMaterialClass> queryList(){
		
		String sqlQuery = "select * from tbAdmMaterialClass";
		
		List<TbAdmMaterialClass> lista = materialClassDAO.simpleQuery(sqlQuery); 
		
		return lista;
	}

	@Override
	public List<TbAdmMaterialClass> findOrder() {
		
		String sqlQuery = "select material from TbAdmMaterialClass material order by material.tbAdmProductType.sLabel, material.sNcm";
		
		return materialClassDAO.simpleQueryQuery(sqlQuery);
	}

	@Override
	public TbAdmMaterialClass findByNcm(String ncm) {
		
		String sqlQuery = "select matClass from TbAdmMaterialClass matClass where matClass.sNcm = '"+ncm+"'";
		
		return materialClassDAO.queryString(sqlQuery);
	}
	
}
