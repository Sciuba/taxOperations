package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.impl.SysModuleDAOImpl;
import br.com.saboia.entity.TbSysModule;

@Stateless
public class ModuleFacadeImpl {
	
	@EJB
	private SysModuleDAOImpl moduleDAOImpl;
	
	
	public TbSysModule findOneResult(Long id){
		return moduleDAOImpl.find(id);
	}
	
	public List<TbSysModule> findAll(){
		return moduleDAOImpl.findAll();
	}
	
	public List<TbSysModule> findOrder(){
		
		String sqlQuery = "select module from TbSysModule module order by module.sDisplayName";
		
		return moduleDAOImpl.findOrder(sqlQuery);
	}
	
	public void save(TbSysModule tbSysModule){
		moduleDAOImpl.save(tbSysModule);
	}
	
	public TbSysModule update(TbSysModule tbSysModule){
		return moduleDAOImpl.update(tbSysModule);
	}
	
	public void delete(TbSysModule tbSysModule){
		moduleDAOImpl.delete(tbSysModule);
	}
	
	public TbSysModule verifyExists(String nome){
		
		String sqlQuery = "select m from TbSysModule m where UPPER(m.sModule) = '"+nome+"'";
		
		return moduleDAOImpl.simpleQuery(sqlQuery);
	}
	
	public List<TbSysModule> findByIdPermission(Long idPermission){
		
		String sqlQuery = "select module from TbSysModule module "
				+ "join TbSysSystemPermission permission "
				+ "on permission.tbSysModule.id = module.id"
				+ " where permission.id = "+idPermission;
		
		return moduleDAOImpl.listSimpleQuery(sqlQuery);
		
	}
}
