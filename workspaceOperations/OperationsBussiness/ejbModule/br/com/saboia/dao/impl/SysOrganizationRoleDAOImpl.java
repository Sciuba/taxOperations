package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.entity.TbSysOrganizationRole;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class SysOrganizationRoleDAOImpl extends GenericDAO<TbSysOrganizationRole> {

	public SysOrganizationRoleDAOImpl() {
		super(TbSysOrganizationRole.class);
	}
	
	public TbSysOrganizationRole finOneResult(Long id){
		return super.find(id);
	}
	
	public List<TbSysOrganizationRole> findAll(){
		return super.findAll();
	}
	
	public List<TbSysOrganizationRole> findOrder(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);		
	}
	
	public List<TbSysOrganizationRole> listSimpleQuery(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public TbSysOrganizationRole simpleQuery(String sqlQuery){
		return super.selectQueryString(sqlQuery);
	}
	
	public void save(TbSysOrganizationRole tbSysOrganizationRole){
		super.save(tbSysOrganizationRole);
	}
	
	public TbSysOrganizationRole update(TbSysOrganizationRole tbSysOrganizationRole){
		return super.update(tbSysOrganizationRole);
	}
	
	public void delete(TbSysOrganizationRole tbSysOrganizationRole){
		Object object = tbSysOrganizationRole.getId();
		super.delete(object, TbSysOrganizationRole.class);
	}

}
