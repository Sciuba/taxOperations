package br.com.operations.facade.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

import br.com.operations.dao.impl.SysSystemPermissionDAOImpl;
import br.com.operations.entity.TbSysModule;
import br.com.operations.entity.TbSysSystemPermission;

@Stateless
public class SystemPermissionFacadeImpl {
	
	@EJB
	private SysSystemPermissionDAOImpl permissionDAO;
	
	
	public List<TbSysSystemPermission> findAll(){
		return permissionDAO.findAll();
	}
	
	public List<TbSysSystemPermission> findOrder(){
		
		String sqlQuery = "select p from TbSysSystemPermission p group by p.tbSysModule.id order by p.sDisplayName";
		
		return permissionDAO.findAllOrder(sqlQuery);				
	}
	
	public List<TbSysSystemPermission> listSimpleQuery(){
		
		String sqlQuery = "";
		
		return permissionDAO.listSimpleQuery(sqlQuery);
	}
	
	public List<TbSysSystemPermission> listByProfileAndRole(Long idRole, Long idProfile){
		
		String sqlQuery = "select a from TbSysSystemPermission a "
						+ "join TbSysAccessProfilePermission p "
						+ "on a.id = p.tbSysSystemPermission.id "
						+ "and p.tbSysAccessProfile.id ="+idProfile+" "
						+ "order by a.tbSysModule.sModule";
		
		return permissionDAO.listSimpleQuery(sqlQuery);
	}
	
	public List<TbSysSystemPermission> findPermissionByUserIdAndModule(Long idUser, String sModule){
		
		List<TbSysSystemPermission> lista = new ArrayList<>();
		
//		select pe.* from TBSYSACCESSPROFILE a 
//		  join TBSYSACCESSPROFILEUSER pu on
//		  a.id = pu.IDSYSACCESSPROFILE
//		  JOIN TBSYSACCESPROFILEPERMISSION pp on
//		  pp.IDSYSACCESSPROFILE = a.id
//		  join TBSYSSYSTEMPERMISSION pe on
//		  pe.id = pp.IDSYSSYSTEMPERMISSION
//		  join TBSYSMODULE mo on
//		  mo.id = pe.IDSYSMODULE
//		  where pu.IDSYSUSER = 41 and mo.SMODULE = 'Material';
		
		String sqlQuery = "select pe.* from TBSYSACCESSPROFILE a "
						+ "join TBSYSACCESSPROFILEUSER pu on "
						+ "a.id = pu.IDSYSACCESSPROFILE "
						+ "JOIN TBSYSACCESPROFILEPERMISSION pp on "
						+ "pp.IDSYSACCESSPROFILE = a.id	"
						+ "join TBSYSSYSTEMPERMISSION pe on "
						+ "pe.id = pp.IDSYSSYSTEMPERMISSION "
						+ "join TBSYSMODULE mo on "
						+ "mo.id = pe.IDSYSMODULE "
						+ "where pu.IDSYSUSER = "+idUser+" and mo.SMODULE = '"+sModule+"'";
		
		List<Object[]> objects = permissionDAO.listSimpleNativeQuery(sqlQuery);
		
		for(int i = 0; i < objects.size(); i++){
			TbSysSystemPermission permission = new TbSysSystemPermission();
			BigDecimal id = new BigDecimal(0);
			id = (BigDecimal) objects.get(i)[0];
			permission.setId(id.longValue());
			permission.setsKey((String) objects.get(i)[1]);
			permission.setsKeyDisplayName((String) objects.get(i)[2]);
			permission.setsDescription((String) objects.get(i)[4]);
			
			lista.add(permission);
		}
		
		return lista;
		
	}
	
	
	public void save(TbSysSystemPermission tbSysSystemPermission){
		permissionDAO.save(tbSysSystemPermission);
	}
	
	public TbSysSystemPermission update(TbSysSystemPermission tbSysSystemPermission){
		return permissionDAO.update(tbSysSystemPermission);
	}
	
	public void delete(TbSysSystemPermission tbSysSystemPermission){
		permissionDAO.delete(tbSysSystemPermission); 
	}
	
	public List<TbSysSystemPermission> findByIdModule(Long idModule){
		
		String sqlQuery = "select permission from TbSysSystemPermission permission "
				+ "where permission.tbSysModule.id = "+idModule+" order by permission.sKey";
		
		return permissionDAO.listSimpleQuery(sqlQuery);
	}
	
	public List<TbSysSystemPermission> findByIdModuleAndProfile(TbSysModule module, Long idProfile){
		
		String sqlQuery = "select permission.* from TBSYSSYSTEMPERMISSION permission "
				+ "left outer join TbSysAccesProfilePermission app "
				+ "on app.IDSYSSYSTEMPERMISSION = permission.id "
				+ "and app.idSysAccessProfile = "+idProfile+" "
				+ "where permission.IDSYSMODULE = "+module.getId()+" and app.id is null order by permission.sKey";
		
		List<TbSysSystemPermission> lista = new ArrayList<>();
		
		List<Object[]> retorno = permissionDAO.listSimpleNativeQuery(sqlQuery);
		
		for(int i = 0; i < retorno.size(); i++){
			
			TbSysSystemPermission permission = new TbSysSystemPermission();
			permission.setId(Long.valueOf(String.valueOf(retorno.get(i)[0])));
			permission.setsKey(String.valueOf(retorno.get(i)[1]));
			permission.setsKeyDisplayName(String.valueOf(retorno.get(i)[2]));
			permission.setsDescription(String.valueOf(retorno.get(i)[4]));
			permission.setTbSysModule(module);
			
			lista.add(permission);
		}
		
		return lista;
	}
	
	public TbSysSystemPermission saveReturn(TbSysSystemPermission permission){
		return permissionDAO.saveReturn(permission);
	}
	
	public TbSysSystemPermission findPermissionByKeyAndModule(String key, Long idModule){
		
		String sqlQuery = "select p from TbSysSystemPermission p where UPPER(p.sKey) = '"+ key.toUpperCase().trim() +"' and p.tbSysModule.id = "+idModule+" order by p.sKey";
		
		return permissionDAO.simpleQuery(sqlQuery);
	}
	
}
