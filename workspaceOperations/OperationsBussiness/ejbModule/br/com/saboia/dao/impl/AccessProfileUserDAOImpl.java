package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.entity.TbSysAccessProfileUser;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class AccessProfileUserDAOImpl extends GenericDAO<TbSysAccessProfileUser> {

	public AccessProfileUserDAOImpl() {
		super(TbSysAccessProfileUser.class);
	}

	
	public TbSysAccessProfileUser find(Long id){
		return super.find(id);
	}
	
	public TbSysAccessProfileUser simpleQuery(String sqlQuery){
		return super.selectQueryString(sqlQuery);
	}
	
	public List<TbSysAccessProfileUser> findAll(){
		return super.findAll();
	}
	
	public List<TbSysAccessProfileUser> listSimpleQuery(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public TbSysAccessProfileUser saveReturn(TbSysAccessProfileUser tbSysAccessProfileUser){
		return super.saveReturn(tbSysAccessProfileUser);
	}
	
	public void save(TbSysAccessProfileUser tbSysAccessProfileUser){
		super.save(tbSysAccessProfileUser);
	}
	
	public TbSysAccessProfileUser update(TbSysAccessProfileUser tbSysAccessProfileUser){
		return super.update(tbSysAccessProfileUser);
	}
	
	public void delete(TbSysAccessProfileUser tbSysAccessProfileUser){
		Object object = tbSysAccessProfileUser.getId();
		super.delete(object, TbSysAccessProfileUser.class);
	}
	
	public void deleteAll(String sqlQuery){
		super.deleteAll(sqlQuery);
	}
}
