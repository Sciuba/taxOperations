package br.com.saboia.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbSysUser;

@Local
public interface SysUserDAO {
	
	public TbSysUser login(String login, String password);
	
	public List<TbSysUser> findAll();
	
	public void save(TbSysUser user);
	
	public TbSysUser saveReturn(TbSysUser user);
	
	public TbSysUser alter(TbSysUser user);
	
	public void delete(TbSysUser user);
	
	public TbSysUser find(Long id);
	
	public List<TbSysUser> listSimpleQuery(String sqlQuery);
	
	public TbSysUser simpleQuery(String sqlQuery);

}
