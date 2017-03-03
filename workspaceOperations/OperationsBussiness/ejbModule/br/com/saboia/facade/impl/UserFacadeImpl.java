package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.SysUserDAO;
import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.UserFacade;

@Stateless
public class UserFacadeImpl implements UserFacade {
	
	
	@EJB
	private SysUserDAO sysUserDAO;
	
	
	@Override
	public TbSysUser login(String login, String password) {
		return sysUserDAO.login(login, password);
	}

	@Override
	public List<TbSysUser> findAll() {
		return sysUserDAO.findAll();
	}

	@Override
	public void save(TbSysUser user) {
		sysUserDAO.save(user);
		
	}

	@Override
	public TbSysUser alter(TbSysUser user) {
		return sysUserDAO.alter(user);
	}

	@Override
	public void delete(TbSysUser user) {
		sysUserDAO.delete(user);
	}

	@Override
	public TbSysUser find(Long id) {
		return sysUserDAO.find(id);
	}

	@Override
	public List<TbSysUser> findByOrganization(Long idOrganization) {
		
		String sqlQuery = "select u from TbSysUser u where u.tbSysOrganization.id ="+idOrganization+" order by u.sFullName";
		
		return sysUserDAO.listSimpleQuery(sqlQuery);
	}

	@Override
	public TbSysUser find(String logon) {
		
		String sqlQuery = "select u from TbSysUser u where UPPER(u.sLogon) = '"+logon.toUpperCase()+"'";
		
		return sysUserDAO.simpleQuery(sqlQuery);
	}

	@Override
	public List<TbSysUser> findByGrupoAndOrganization(Long idOrganization, Long idGroup) {

		String sqlQuery = "select u from TbSysUser u "
						+ "join TbSysWorkGroupUser g "
						+ "on u.id = g.tbSysUser.id "
						+ "and g.tbSysWorkGroup.id = "+idGroup+" "
						+ "where u.tbSysOrganization.id = "+idOrganization +" order by u.sFullName";				
		
		return sysUserDAO.listSimpleQuery(sqlQuery);
	}
	
	@Override
	public List<TbSysUser> findByGrupoAndOrganizationNotManager(Long idOrganization, Long idGroup) {

		String sqlQuery = "select u from TbSysUser u "
						+ "join TbSysWorkGroupUser g "
						+ "on u.id = g.tbSysUser.id "
						+ "and g.tbSysWorkGroup.id = "+idGroup+" "
						+ "where u.tbSysOrganization.id = "+idOrganization +" and g.fManager = false order by u.sFullName";				
		
		return sysUserDAO.listSimpleQuery(sqlQuery);
	}
	
	@Override
	public List<TbSysUser> findByProfile(Long idAccesProfile) {

		String sqlQuery = "select u from TbSysUser u "
						+ "join TbSysAccessProfileUser a "
						+ "on u.id = a.tbSysUser.id "
						+ "where a.tbSysAccessProfile.id = "+idAccesProfile +" order by u.sFullName";				
		
		return sysUserDAO.listSimpleQuery(sqlQuery);
	}

	@Override
	public TbSysUser saveReturn(TbSysUser user) {
		return sysUserDAO.saveReturn(user);		
	}

	@Override
	public TbSysUser rememberPassword(String login) {
		
		String sqlQuery = "select u from TbSysUser u where UPPER(u.sLogon) ='"+login.toUpperCase()+"'";
		
		return sysUserDAO.simpleQuery(sqlQuery);
	}

	@Override
	public TbSysUser recoveryIdUserCodePassword(String sCodePasswordSent) {
		
		String sqlQuery = "select u from TbSysUser u where u.sCodePasswordSend = '"+sCodePasswordSent+"'";
		
		return sysUserDAO.simpleQuery(sqlQuery);
	}

	@Override
	public List<TbSysUser> findParticipantNotIncludeInQuote(Long id) {
		
		String sqlQuery = "select p from TbSysUser p "
				+ "join TbTaxQuote q "
				+ " on q.tbSysUser.id != p.id and q.id = "+id+" "
				+ " where p.id not in (select part.tbSysUser.id "
				+ " from TbTaxQuoteParticipant part where part.tbTaxQuote.id = "+id+") order by p.sFullName";
		
		return sysUserDAO.listSimpleQuery(sqlQuery);
	}

}
