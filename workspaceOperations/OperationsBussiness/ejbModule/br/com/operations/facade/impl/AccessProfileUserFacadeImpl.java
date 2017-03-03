package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.impl.AccessProfileUserDAOImpl;
import br.com.operations.entity.TbSysAccessProfileUser;

@Stateless
public class AccessProfileUserFacadeImpl {

	@EJB
	private AccessProfileUserDAOImpl profileUserDAOImpl;
	
	
	public List<TbSysAccessProfileUser> findAll(){
		return profileUserDAOImpl.findAll();
	}
	
	public TbSysAccessProfileUser find(Long id){
		return profileUserDAOImpl.find(id);
	}
	
	public List<TbSysAccessProfileUser> findByProfile(Long idProfile){
		String sqlQuery = "select a from TbSysAccessProfileUser a where a.tbSysAccessProfile.id ="+idProfile;
		return profileUserDAOImpl.listSimpleQuery(sqlQuery);
	}
	
	public void deleteAllUsersInProfile(Long id){
		String sqlQuery = "delete from TbSysAccessProfileUser u where u.tbSysAccessProfile.id = "+id;						   
		profileUserDAOImpl.deleteAll(sqlQuery);
	}
	
	public void save(TbSysAccessProfileUser tbSysAccessProfileUser){
		profileUserDAOImpl.save(tbSysAccessProfileUser);
	}
	
	public TbSysAccessProfileUser saveReturn(TbSysAccessProfileUser tbSysAccessProfileUser){
		return profileUserDAOImpl.saveReturn(tbSysAccessProfileUser);
	}
	
	public TbSysAccessProfileUser update(TbSysAccessProfileUser tbSysAccessProfileUser){
		return profileUserDAOImpl.update(tbSysAccessProfileUser);
	}
	
	public void delete(TbSysAccessProfileUser tbSysAccessProfileUser){
		profileUserDAOImpl.delete(tbSysAccessProfileUser);
	}
	
}
