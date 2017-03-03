package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.impl.WorkGroupUserDAOImpl;
import br.com.saboia.entity.TbSysWorkGroupUser;

@Stateless
public class WorkGroupUserFacadeImpl {

	@EJB
	private WorkGroupUserDAOImpl workGroupUserDAOImpl;
	
	
	public TbSysWorkGroupUser find(Long id){
		return workGroupUserDAOImpl.find(id);
	}
	
	public List<TbSysWorkGroupUser> findAll(){
		return workGroupUserDAOImpl.findAll();
	}
	
	public TbSysWorkGroupUser findByUserAndGroup(Long idUser, Long idGroup){
		
		String sqlQuery = "select w from TbSysWorkGroupUser w where w.tbSysUser.id ="+idUser+" and w.tbSysWorkGroup.id ="+idGroup;
		
		return workGroupUserDAOImpl.simpleQuery(sqlQuery); 			
	}
	
	public List<TbSysWorkGroupUser> findByUser(Long idUser){
		
		String sqlQuery = "select w from TbSysWorkGroupUser w where w.tbSysUser.id ="+idUser;
		
		return workGroupUserDAOImpl.listSimpleQuery(sqlQuery); 			
	}
	
	public List<TbSysWorkGroupUser> findByGroup(Long idGroup){
		
		String sqlQuery = "select w from TbSysWorkGroupUser w where w.tbSysWorkGroup.id ="+idGroup;
		
		return workGroupUserDAOImpl.listSimpleQuery(sqlQuery); 			
	}
	
	public void deleteAllUsersInGroup(Long idWorkGroup){
		String sqlQuery = "delete from TbSysWorkGroupUser w where w.tbSysWorkGroup.id ="+idWorkGroup;
		
		workGroupUserDAOImpl.deleteAll(sqlQuery);
	}
	
	public void save(TbSysWorkGroupUser tbSysWorkGroupUser){
		workGroupUserDAOImpl.save(tbSysWorkGroupUser);
	}
	
	public TbSysWorkGroupUser update(TbSysWorkGroupUser tbSysWorkGroupUser){
		return workGroupUserDAOImpl.update(tbSysWorkGroupUser);
	}
	
	public void delete(TbSysWorkGroupUser tbSysWorkGroupUser){
		workGroupUserDAOImpl.delete(tbSysWorkGroupUser);
	}
	
	public TbSysWorkGroupUser saveReturn(TbSysWorkGroupUser tbSysWorkGroupUser){
		return workGroupUserDAOImpl.saveReturn(tbSysWorkGroupUser);
	}
	
	
	
}
