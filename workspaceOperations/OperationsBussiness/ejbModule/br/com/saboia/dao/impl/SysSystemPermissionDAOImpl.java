package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.entity.TbSysSystemPermission;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class SysSystemPermissionDAOImpl extends GenericDAO<TbSysSystemPermission>{

	public SysSystemPermissionDAOImpl() {
		super(TbSysSystemPermission.class);
	}
	
	public List<TbSysSystemPermission> findAll(){
		return super.findAll();
	}
	
	public List<TbSysSystemPermission> findAllOrder(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public void save(TbSysSystemPermission tbSysSystemPermission){
		super.save(tbSysSystemPermission);
	}
	
	public TbSysSystemPermission update(TbSysSystemPermission tbSysSystemPermission){
		return super.update(tbSysSystemPermission);
	}
	
	public void delete(TbSysSystemPermission tbSysSystemPermission){
		Object object = tbSysSystemPermission.getId();
		super.delete(object, TbSysSystemPermission.class);
	}
	
	public List<TbSysSystemPermission> listSimpleQuery(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public List<TbSysSystemPermission> listSimpleNativeEntityQuery(String sqlQuery){
		return super.selectListSimpleNativeQueryString(sqlQuery);
	}
	
	
	public List<Object[]> listSimpleNativeQuery(String sqlQuery){
		return super.selectListSimpleNativeQueryStringObject(sqlQuery);
	}
	
	public TbSysSystemPermission simpleQuery(String sqlQuery){
		return super.selectQueryString(sqlQuery);
	}
}
