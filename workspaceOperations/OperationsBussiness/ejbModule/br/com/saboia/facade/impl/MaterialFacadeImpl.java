package br.com.saboia.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmMaterialDAO;
import br.com.saboia.entity.TbAdmMaterial;
import br.com.saboia.facade.MaterialFacade;

@Stateless
public class MaterialFacadeImpl implements MaterialFacade {

	@EJB
	private AdmMaterialDAO materialDAO;
	
	@Override
	public List<TbAdmMaterial> findAll() {
		return materialDAO.findAll();
	}

	@Override
	public void save(TbAdmMaterial material) {
		materialDAO.save(material);
	}

	@Override
	public void alter(TbAdmMaterial material) {
		materialDAO.alter(material);
	}

	@Override
	public void delete(TbAdmMaterial material) {
		materialDAO.delete(material);
	}

	@Override
	public TbAdmMaterial find(Long id) {
		return materialDAO.find(id);
	}

	@Override
	public TbAdmMaterial saveReturn(TbAdmMaterial material) {
		return materialDAO.saveReturn(material);
	}

	@Override
	public TbAdmMaterial findQuery(Object... params) {
		
		String namedQuery = "select material from TbAdmMaterial material where material.sInternalModel LIKE :code";		
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("code", params[0]);
		
		return materialDAO.findQuery(namedQuery, parameters);
	}

	@Override
	public List<TbAdmMaterial> findLazyDataModel(int startingAt, int maxPerPage, String filtroSelecionado, String filtroManufacturer, String filtroPartNumber, String filtroDescription, String filtroNCM) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("select material from  TbAdmMaterial material ");
		
		if(filtroSelecionado != null && filtroSelecionado.equals("1")){
			sb.append(" where material.id in (select item.tbAdmMaterial.id from TbTaxQuoteItem item)");
		}else if(filtroSelecionado != null && filtroSelecionado.equals("2")){
			sb.append(" where material.tbAdmMaterialClass is null");
		}else if(filtroSelecionado != null && filtroSelecionado.equals("3")){
			sb.append(" where material.id in (select item.tbAdmMaterial.id from TbTaxQuoteItem item) and material.tbAdmMaterialClass is null");
		}
		
		if(filtroManufacturer != null){
			if(filtroSelecionado != null){
				sb.append(" and UPPER(material.tbAdmMaterialManufacturer.sAlias) like '%"+filtroManufacturer.toUpperCase()+"%'");
			}else{
				sb.append(" where UPPER(material.tbAdmMaterialManufacturer.sAlias) like '%"+filtroManufacturer.toUpperCase()+"%'");
			}
		}
		
		if(filtroPartNumber != null){
			if(filtroSelecionado != null || filtroManufacturer != null){
				sb.append(" and UPPER(material.sInternalModel) like '%"+filtroPartNumber.toUpperCase()+"%'");
			}else{
				sb.append(" where UPPER(material.sInternalModel) like '%"+filtroPartNumber.toUpperCase()+"%'");
			}
		}
		
		if(filtroDescription != null){
			if(filtroSelecionado != null || filtroManufacturer != null || filtroPartNumber != null){
				sb.append(" and UPPER(material.sDescription) like '%"+filtroDescription.toUpperCase()+"%'");
			}else{
				sb.append(" where UPPER(material.sDescription) like '%"+filtroDescription.toUpperCase()+"%'");
			}
		}
		
		if(filtroNCM != null){
			if(filtroSelecionado != null || filtroManufacturer != null || filtroPartNumber != null || filtroDescription != null){
				sb.append(" and UPPER(material.tbAdmMaterialClass.sNcm) like '%"+filtroNCM.toUpperCase()+"%'");
			}else{
				sb.append(" where UPPER(material.tbAdmMaterialClass.sNcm) like '%"+filtroNCM.toUpperCase()+"%'");
			}
		}
		
		sb.append(" order by material.sInternalModel");
		
		return materialDAO.findAllLazyDataModel(sb.toString(), startingAt, maxPerPage);
	}

	@Override
	public int selectTotalNumberRegistry( String filtroSelecionado, String filtroManufacturer, String filtroPartNumber, String filtroDescription, String filtroNCM ) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT COUNT(material) FROM TbAdmMaterial material");
		
		if(filtroSelecionado != null && filtroSelecionado.equals("1")){
			sb.append(" where material.id in (select item.tbAdmMaterial.id from TbTaxQuoteItem item)");
		}else if(filtroSelecionado != null && filtroSelecionado.equals("2")){
			sb.append(" where material.tbAdmMaterialClass is null");
		}else if(filtroSelecionado != null && filtroSelecionado.equals("3")){
			sb.append(" where material.id in (select item.tbAdmMaterial.id from TbTaxQuoteItem item) and material.tbAdmMaterialClass is null");
		}
		
		if(filtroManufacturer != null){
			if(filtroSelecionado != null){
				sb.append(" and UPPER(material.tbAdmMaterialManufacturer.sAlias) like '%"+filtroManufacturer.toUpperCase()+"%'");
			}else{
				sb.append(" where UPPER(material.tbAdmMaterialManufacturer.sAlias) like '%"+filtroManufacturer.toUpperCase()+"%'");
			}
		}
		
		if(filtroPartNumber != null){
			if(filtroSelecionado != null || filtroManufacturer != null){
				sb.append(" and UPPER(material.sInternalModel) like '%"+filtroPartNumber.toUpperCase()+"%'");
			}else{
				sb.append(" where UPPER(material.sInternalModel) like '%"+filtroPartNumber.toUpperCase()+"%'");
			}
		}
		
		if(filtroDescription != null){
			if(filtroSelecionado != null || filtroManufacturer != null || filtroPartNumber != null){
				sb.append(" and UPPER(material.sDescription) like '%"+filtroDescription.toUpperCase()+"%'");
			}else{
				sb.append(" where UPPER(material.sDescription) like '%"+filtroDescription.toUpperCase()+"%'");
			}
		}
		
		if(filtroNCM != null){
			if(filtroSelecionado != null || filtroManufacturer != null || filtroPartNumber != null || filtroDescription != null){
				sb.append(" and UPPER(material.tbAdmMaterialClass.sNcm) like '%"+filtroNCM.toUpperCase()+"%'");
			}else{
				sb.append(" where UPPER(material.tbAdmMaterialClass.sNcm) like '%"+filtroNCM.toUpperCase()+"%'");
			}
		}
		
		
		return materialDAO.selectTotalNumberRegistry(sb.toString());
	}

	
}
