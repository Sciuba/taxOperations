package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.entity.TbSysWorkGroup;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class WorkGroupDAOImpl extends GenericDAO<TbSysWorkGroup> {

	public WorkGroupDAOImpl() {
		super(TbSysWorkGroup.class);
	}

	public TbSysWorkGroup find(Long id){
		return super.find(id);
	}
	
	public List<TbSysWorkGroup> findAll(){
		return super.findAll();
	}
	
	public int findCount(String sqlQuery){
		return super.selectTotalNumberRegistry(sqlQuery);
	}
	
	public List<TbSysWorkGroup> findOrder(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public List<TbSysWorkGroup> listSimpleQuery(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public TbSysWorkGroup simpleQuery(String sqlQuery){
		return super.selectQueryString(sqlQuery);
	}
	
	public void save(TbSysWorkGroup tbSysWorkGroup){
		super.save(tbSysWorkGroup);
	}
	
	public TbSysWorkGroup update(TbSysWorkGroup tbSysWorkGroup){
		return super.update(tbSysWorkGroup);
	}
	
	public void delete(TbSysWorkGroup tbSysWorkGroup){
		Object object = tbSysWorkGroup.getId();
		super.delete(object, TbSysWorkGroup.class);
	}
	
	public TbSysWorkGroup saveReturn(TbSysWorkGroup tbSysWorkGroup){
		return super.saveReturn(tbSysWorkGroup);
	}
	
}
