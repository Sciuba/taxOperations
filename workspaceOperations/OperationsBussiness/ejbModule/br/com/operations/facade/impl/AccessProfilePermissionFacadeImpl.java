package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.impl.SysAccessProfilePermissionDAOImpl;
import br.com.operations.entity.TbSysAccessProfilePermission;

@Stateless
public class AccessProfilePermissionFacadeImpl {

	@EJB
	private SysAccessProfilePermissionDAOImpl profilePermissionDAOImpl;

	
	public TbSysAccessProfilePermission find(Long id){
		return profilePermissionDAOImpl.find(id);
	}
	
	public List<TbSysAccessProfilePermission> findAll(){
		return profilePermissionDAOImpl.findAll();
	}
	
	public List<TbSysAccessProfilePermission> finOrder(){
		
		String sqlQuery="";
		
		return profilePermissionDAOImpl.findOrder(sqlQuery);
	}
	
	public List<TbSysAccessProfilePermission> findByProfile(Long idProfile){
		
		String sqlQuery="select a from TbSysAccessProfilePermission a where a.tbSysAccessProfile.id ="+idProfile;
		
		return profilePermissionDAOImpl.listSimpleQuery(sqlQuery);
		
	}
	
	public void save(TbSysAccessProfilePermission tbSysAccessProfilePermission){
		profilePermissionDAOImpl.save(tbSysAccessProfilePermission);
	}
	
	public TbSysAccessProfilePermission update(TbSysAccessProfilePermission tbSysAccessProfilePermission){
		return profilePermissionDAOImpl.update(tbSysAccessProfilePermission);
	}
	
	public void delete(TbSysAccessProfilePermission tbSysAccessProfilePermission){
		profilePermissionDAOImpl.delete(tbSysAccessProfilePermission);
	}
	
	public void deleteAllByProfile(Long idProfile){
		String sqlQuery = "delete from TbSysAccessProfilePermission a where a.tbSysAccessProfile.id ="+idProfile;
		
		profilePermissionDAOImpl.deleteAll(sqlQuery);
	}
}
