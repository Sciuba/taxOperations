package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.entity.TbSysOrganization;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class SysOrganizationDAOImpl extends GenericDAO<TbSysOrganization>{

	public SysOrganizationDAOImpl() {
		super(TbSysOrganization.class);
	}
	
	public List<TbSysOrganization> findAll(){
		return super.findAll();
	}
	
	public List<TbSysOrganization> findAllOrder(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public void save(TbSysOrganization tbSysOrganization){
		super.save(tbSysOrganization);
	}
	
	public TbSysOrganization saveReturn(TbSysOrganization tbSysOrganization){
		return super.saveReturn(tbSysOrganization);
	}
	
	public TbSysOrganization update(TbSysOrganization tbSysOrganization){
		return super.update(tbSysOrganization);
	}
	
	public void delete(TbSysOrganization tbSysOrganization){
		Object object = tbSysOrganization.getId();
		super.delete(object, TbSysOrganization.class);
	}
	
	public TbSysOrganization simpleQuery(String sqlQuery){
		return super.selectQueryString(sqlQuery);
	}
	
	public List<TbSysOrganization> simpleQueryList(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
}
