package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.entity.TbSysWorkGroupUser;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class WorkGroupUserDAOImpl extends GenericDAO<TbSysWorkGroupUser> {

	public WorkGroupUserDAOImpl() {
		super(TbSysWorkGroupUser.class);
	}
	
	public List<TbSysWorkGroupUser> findAll(){
		return super.findAll();
	}
	
	public List<TbSysWorkGroupUser> findOrder(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public TbSysWorkGroupUser find(Long id){
		return super.find(id);
	}
	
	public int findCount(String sqlQuery){
		return super.selectTotalNumberRegistry(sqlQuery);
	}
	
	public List<TbSysWorkGroupUser> listSimpleQuery(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public TbSysWorkGroupUser simpleQuery(String sqlQuery){
		return super.selectQueryString(sqlQuery);
	}
	
	public void save(TbSysWorkGroupUser tbSysWorkGroupUser){
		super.save(tbSysWorkGroupUser);
	}
	
	public TbSysWorkGroupUser update(TbSysWorkGroupUser tbSysWorkGroupUser){
		return super.update(tbSysWorkGroupUser);
	}
	
	public void delete(TbSysWorkGroupUser tbSysWorkGroupUser){
		Object object = tbSysWorkGroupUser.getId();
		super.delete(object, TbSysWorkGroupUser.class);
	}
	
	public void deleteAll(String sqlQuery){
		super.deleteAll(sqlQuery);
	}
	
}
