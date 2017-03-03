package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.impl.SysOrganizationDAOImpl;
import br.com.operations.entity.TbSysOrganization;

@Stateless
public class OrganizationFacadeImpl {
	
	@EJB
	private SysOrganizationDAOImpl organizationDAOImpl;
	
	
	public TbSysOrganization find(Long id){
		return organizationDAOImpl.find(id);
	}
	
	
	public List<TbSysOrganization> findAll(){
		return organizationDAOImpl.findAll();
	}
	
	public List<TbSysOrganization> findAllOrder(){
		
		String sqlQuery = "select organization from TbSysOrganization organization order by organization.sName";
		
		return organizationDAOImpl.findAllOrder(sqlQuery);
	}
	
	public void save(TbSysOrganization tbSysOrganization){
		organizationDAOImpl.save(tbSysOrganization);
	}
	
	public TbSysOrganization saveReturn(TbSysOrganization tbSysOrganization){
		return organizationDAOImpl.saveReturn(tbSysOrganization);
	}
	
	public TbSysOrganization update(TbSysOrganization tbSysOrganization){
		return organizationDAOImpl.update(tbSysOrganization);
	}
	
	public void delete(TbSysOrganization tbSysOrganization){
		organizationDAOImpl.delete(tbSysOrganization);
	}
	
	public TbSysOrganization simpleQuery(){
		
		String sqlQuery = "select org from TbSysOrganization org where org.fMain = true";
		
		return organizationDAOImpl.simpleQuery(sqlQuery);
	}
	
	public List<TbSysOrganization> simpleQueryList(String sqlQuery){
		return organizationDAOImpl.simpleQueryList(sqlQuery);
	}
	

}
