package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbSysUser;

@Local
public interface UserFacade {
	
	public TbSysUser login(String login, String password);
	
	public TbSysUser rememberPassword(String login);
	
	public TbSysUser recoveryIdUserCodePassword(String sCodePasswordSent);
	
	public List<TbSysUser> findAll();
	
	public List<TbSysUser> findByOrganization(Long idOrganization);
	
	public List<TbSysUser> findByGrupoAndOrganization(Long idOrganization, Long idGroup);
	
	public void save(TbSysUser user);
	
	public TbSysUser saveReturn(TbSysUser user);
	
	public TbSysUser alter(TbSysUser user);
	
	public void delete(TbSysUser user);
	
	public TbSysUser find(Long id);
	
	public TbSysUser find(String logon);

	List<TbSysUser> findByProfile(Long idAccesProfile);
	
	public List<TbSysUser> findParticipantNotIncludeInQuote(Long id);

	List<TbSysUser> findByGrupoAndOrganizationNotManager(Long idOrganization,
			Long idGroup);
}
