package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.entity.TbSysAccessProfile;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class SysAccessProfileDAOImpl extends GenericDAO<TbSysAccessProfile> {

	public SysAccessProfileDAOImpl() {
		super(TbSysAccessProfile.class);
	}
	
	public TbSysAccessProfile find(Long id){
		return super.find(id);
	}
	
	public List<TbSysAccessProfile> findAll(){
		return super.findAll();
	}
	
	public List<TbSysAccessProfile> findOrder(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}

	public TbSysAccessProfile simpleQuery(String sqlQuery){
		return super.selectQueryString(sqlQuery);
	}
	
	public List<TbSysAccessProfile> listSimpleQuery(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public void save(TbSysAccessProfile tbSysAccessProfile){
		super.save(tbSysAccessProfile);
	}
	
	public TbSysAccessProfile update(TbSysAccessProfile tbSysAccessProfile){
		return super.update(tbSysAccessProfile);
	}
	
	public void delete(TbSysAccessProfile tbSysAccessProfile){
		Object object = tbSysAccessProfile.getId();
		super.delete(object, TbSysAccessProfile.class);
	}
	
	public void updateAll(String sqlQuery){
		super.deleteAll(sqlQuery);
	}
	
}
