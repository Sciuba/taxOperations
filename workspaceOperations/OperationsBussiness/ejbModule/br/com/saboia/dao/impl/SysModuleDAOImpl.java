package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.entity.TbSysModule;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class SysModuleDAOImpl extends GenericDAO<TbSysModule> {

	public SysModuleDAOImpl() {
		super(TbSysModule.class);
	}
	
	public TbSysModule finOneResult(Long id){
		return super.find(id);
	}
	
	public List<TbSysModule> findAll(){
		return super.findAll();
	}
	
	public List<TbSysModule> findOrder(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public void save(TbSysModule tbSysModule){
		super.save(tbSysModule);
	}
	
	public TbSysModule update(TbSysModule tbSysModule){
		return super.update(tbSysModule);
	}
	
	public void delete(TbSysModule tbSysModule){
		Object object = tbSysModule.getId();
		super.delete(object, TbSysModule.class);
	}
	
	public List<TbSysModule> listSimpleQuery(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
	public TbSysModule simpleQuery(String sqlQuery){
		return super.selectQueryString(sqlQuery);
	}
	
}
