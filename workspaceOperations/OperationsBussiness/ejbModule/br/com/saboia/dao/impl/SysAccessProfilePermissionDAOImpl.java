package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.entity.TbSysAccessProfilePermission;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class SysAccessProfilePermissionDAOImpl extends GenericDAO<TbSysAccessProfilePermission> {

	public SysAccessProfilePermissionDAOImpl() {
		super(TbSysAccessProfilePermission.class);
	}

	public TbSysAccessProfilePermission find(Long id){
		return super.find(id);
	}
	
	public List<TbSysAccessProfilePermission> findAll(){
		return super.findAll();
	}
	
	public List<TbSysAccessProfilePermission> findOrder(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}

	public TbSysAccessProfilePermission simpleQuery(String sqlQuery){
		return super.selectQueryString(sqlQuery);
	}
	
	public List<TbSysAccessProfilePermission> listSimpleQuery(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public void save(TbSysAccessProfilePermission tbSysAccessProfilePermission){
		super.save(tbSysAccessProfilePermission);
	}
	
	public TbSysAccessProfilePermission update(TbSysAccessProfilePermission tbSysAccessProfilePermission){
		return super.update(tbSysAccessProfilePermission);
	}
	
	public void delete(TbSysAccessProfilePermission tbSysAccessProfilePermission){
		Object object = tbSysAccessProfilePermission.getId();
		super.delete(object, TbSysAccessProfilePermission.class);
	}
	
	public void deleteAll(String sqlQuery){
		super.deleteAll(sqlQuery);
	}
}
