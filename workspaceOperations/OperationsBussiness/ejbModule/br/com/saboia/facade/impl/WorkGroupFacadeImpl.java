package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.impl.WorkGroupDAOImpl;
import br.com.saboia.entity.TbSysWorkGroup;


@Stateless
public class WorkGroupFacadeImpl {
	
	@EJB
	private WorkGroupDAOImpl groupDAOImpl;
	
	
	public List<TbSysWorkGroup> findAll(){
		return groupDAOImpl.findAll();
	}
	
	public TbSysWorkGroup find(Long id){
		return groupDAOImpl.find(id);
	}

	public List<TbSysWorkGroup> findOrder(){
		
		String sqlQuery = "select w from TbSysWorkGroup w order by w.sName";
		
		return groupDAOImpl.findOrder(sqlQuery);		
	}
	
	public List<TbSysWorkGroup> findOrderByOrganization(Long idOrg){
		
		String sqlQuery = "select w from TbSysWorkGroup w where w.tbSysOrganization.id = "+ idOrg + " order by w.sName";
		
		return groupDAOImpl.listSimpleQuery(sqlQuery);
	}
	
	public TbSysWorkGroup findOrderByNameAndOrganization(String name, Long idOrg){
		
		String sqlQuery = "select w from TbSysWorkGroup w where UPPER(w.sName) = '"+name.toUpperCase()+"' and w.tbSysOrganization.id = "+ idOrg;
		
		return groupDAOImpl.simpleQuery(sqlQuery);
	}
	
	public int findQtyUser(Long idUser, Long idOrg){
		
		String sqlQuery = "select count(u) from TbSysWorkGroup w "
						+ "join TbSysWorkGroupUser u on "
						+ "w.id = u.tbSysWorkGroup.id "
						+ "where w.tbSysOrganization.id = "+idOrg;
		
		return groupDAOImpl.findCount(sqlQuery); 			
	}
	
	public TbSysWorkGroup saveReturn(TbSysWorkGroup tbSysWorkGroup){
		return groupDAOImpl.saveReturn(tbSysWorkGroup);
	}
	
	public void save(TbSysWorkGroup tbSysWorkGroup){
		groupDAOImpl.save(tbSysWorkGroup);
	}
	
	public TbSysWorkGroup update(TbSysWorkGroup tbSysWorkGroup){
		return groupDAOImpl.update(tbSysWorkGroup);
	}
	
	public void delete(TbSysWorkGroup tbSysWorkGroup){
		groupDAOImpl.delete(tbSysWorkGroup);
	}
	
	
}
