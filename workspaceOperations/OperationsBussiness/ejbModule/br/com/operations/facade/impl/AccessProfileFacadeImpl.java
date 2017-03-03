package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.impl.SysAccessProfileDAOImpl;
import br.com.operations.entity.TbSysAccessProfile;
import br.com.operations.entity.TbSysAccessProfileUser;

@Stateless
public class AccessProfileFacadeImpl {

	@EJB
	private SysAccessProfileDAOImpl profileDAOImpl;
	
	public TbSysAccessProfile find(Long id){
		return profileDAOImpl.find(id);
	}
	
	public List<TbSysAccessProfile> findAll(){
		return profileDAOImpl.findAll();
	}
	
	public List<TbSysAccessProfile> finOrder(){
		
		String sqlQuery = "select a from TbSysAccessProfile a order by a.sDisplayName";
		
		return profileDAOImpl.findOrder(sqlQuery);
	}
	
	public List<TbSysAccessProfile> findByOrganizationRole(Long idRole){
		
		String sqlQuery = "select a from TbSysAccessProfile a where a.tbSysOrganizationRole.id = "+idRole+" order by a.sDisplayName";
		
		return profileDAOImpl.listSimpleQuery(sqlQuery);
	}
	
	public TbSysAccessProfile findByOrganizationAndName(String name, Long idOrganization){
		
		String sqlQuery = "select a from TbSysAccessProfile a where a.tbSysOrganizationRole.id = "+idOrganization+" and UPPER(a.sDisplayName) = '"+name+"'";
		
		return profileDAOImpl.simpleQuery(sqlQuery);
	}
	
	public TbSysAccessProfile findProfileDefault(){
		
		String sqlQuery = "select a from TbSysAccessProfile a where a.fDefault = true";
		
		return profileDAOImpl.simpleQuery(sqlQuery);
	}
	
	public void noDefaultProfile(){
		String sqlQuery = "update TbSysAccessProfile a set a.fDefault = false";
		
		profileDAOImpl.updateAll(sqlQuery);
	}
	
	public List<TbSysAccessProfile> findProfileByUserIdAndOrganization(Long idUser){
		String sqlQuery = "select a from TbSysAccessProfile a "
						+ "join TbSysAccessProfileUser pu on "
						+ "pu.tbSysAccessProfile.id = a.id "
						+ "where pu.tbSysUser.id = "+idUser;
		
		return profileDAOImpl.listSimpleQuery(sqlQuery);
	}
	
	public void save(TbSysAccessProfile tbSysAccessProfile){
		profileDAOImpl.save(tbSysAccessProfile);
	}
	
	public TbSysAccessProfile saveReturn(TbSysAccessProfile tbSysAccessProfile){
		return profileDAOImpl.saveReturn(tbSysAccessProfile);
	}
	
	public TbSysAccessProfile update(TbSysAccessProfile tbSysAccessProfile){
		return profileDAOImpl.update(tbSysAccessProfile);
	}
	
	public void delete(TbSysAccessProfile tbSysAccessProfile){
		profileDAOImpl.delete(tbSysAccessProfile);
	}
}
