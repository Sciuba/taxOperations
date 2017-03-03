package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.impl.SysOrganizationRoleDAOImpl;
import br.com.saboia.entity.TbSysOrganizationRole;

@Stateless
public class OrganizationRoleFacadeImpl {

	@EJB
	private SysOrganizationRoleDAOImpl roleDAO;
	
	public TbSysOrganizationRole finOneResult(Long id){
		return roleDAO.finOneResult(id);
	}
	
	public List<TbSysOrganizationRole> findAll(){
		return roleDAO.findAll();
	}
	
	public List<TbSysOrganizationRole> findOrder(){
		
		String sqlQuery = "select role from TbSysOrganizationRole role order by role.sDisplayName";
		
		return roleDAO.listSimpleQuery(sqlQuery);		
	}
	
	public TbSysOrganizationRole findKeyExists(String key){
		
		String sqlQuery = "select role from TbSysOrganizationRole role where UPPER(role.sKey) = '"+ key.toUpperCase().trim() +"'";
		
		return roleDAO.simpleQuery(sqlQuery);
	}
	
	public void save(TbSysOrganizationRole tbSysOrganizationRole){
		roleDAO.save(tbSysOrganizationRole);
	}
	
	public TbSysOrganizationRole update(TbSysOrganizationRole tbSysOrganizationRole){
		return roleDAO.update(tbSysOrganizationRole);
	}
	
	public void delete(TbSysOrganizationRole tbSysOrganizationRole){
		roleDAO.delete(tbSysOrganizationRole);
	}
	
}
