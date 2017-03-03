package br.com.saboia.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.com.saboia.dao.SysUserDAO;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class SysUserDAOImpl extends GenericDAO<TbSysUser> implements SysUserDAO{

	public SysUserDAOImpl() {
		super(TbSysUser.class);		
	}

	@Override
	public TbSysUser login(String login, String password) {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("1", login);
		parameters.put("2", password);
		
		TbSysUser user = super.findOneResult("select user from TbSysUser user where user.sLogon = ?1 and user.sPassword = ?2", parameters);
		
		if(user == null){
			user = new TbSysUser();
		}
				
		return user;
	}
	
	
	@Override
	public void save(TbSysUser user) {
		super.save(user);		
	}
	
	@Override
	public TbSysUser alter(TbSysUser user) {
		return super.update(user);		
	}

	@Override
	public TbSysUser find(Long id) {
		return super.find(id);
	}
	
	@Override
	public void delete(TbSysUser user){
		Object object = user.getId();
		super.delete(object, TbSysUser.class);
	}
	
	@Override
	public List<TbSysUser> findAll(){
		return super.findAll();
	}

	@Override
	public List<TbSysUser> listSimpleQuery(String sqlQuery) {
		return super.selectListSimpleQueryString(sqlQuery);
	}

	@Override
	public TbSysUser simpleQuery(String sqlQuery) {
		return super.selectQueryString(sqlQuery);
	}
	
	

}
