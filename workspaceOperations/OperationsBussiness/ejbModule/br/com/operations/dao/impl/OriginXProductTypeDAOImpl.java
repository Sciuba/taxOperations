package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.entity.TbAdmOriginXProductTypeSV;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class OriginXProductTypeDAOImpl extends GenericDAO<TbAdmOriginXProductTypeSV> {

	public OriginXProductTypeDAOImpl() {
		super(TbAdmOriginXProductTypeSV.class);
	}

	public TbAdmOriginXProductTypeSV find(Long id){
		return super.find(id);
	}
	
	public TbAdmOriginXProductTypeSV simpleQuery(String sqlQuery){
		return super.selectQueryString(sqlQuery);
	}
	
	public void save(TbAdmOriginXProductTypeSV tbAdmOriginXProductTypeSV){
		super.save(tbAdmOriginXProductTypeSV);
	}
	
	public TbAdmOriginXProductTypeSV update(TbAdmOriginXProductTypeSV tbAdmOriginXProductTypeSV){
		return super.update(tbAdmOriginXProductTypeSV);
	}
	
	public void delete(TbAdmOriginXProductTypeSV tbAdmOriginXProductTypeSV){
		Object object = tbAdmOriginXProductTypeSV.getId();
		super.delete(object, TbAdmOriginXProductTypeSV.class);
	}
	
	public List<TbAdmOriginXProductTypeSV> findAll(){
		return super.findAll();
	}
	
	public List<TbAdmOriginXProductTypeSV> listSimpleQuery(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public void deleteAll(String sqlQuery){
		super.deleteAll(sqlQuery);
	}
	
}
