
package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.entity.TbSysLogEmailSend;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class LogEmailSendDAOImpl extends GenericDAO<TbSysLogEmailSend> {

	public LogEmailSendDAOImpl() {
		super(TbSysLogEmailSend.class);
	}
	
	public TbSysLogEmailSend find(Long id){
		return super.find(id);
	}
	
	public TbSysLogEmailSend simpleQuery(String sqlQuery){
		return super.selectQueryString(sqlQuery);
	}
	
	public void save(TbSysLogEmailSend tbSysLogEmailSend){
		super.save(tbSysLogEmailSend);
	}
	
	public TbSysLogEmailSend saveReturn(TbSysLogEmailSend tbSysLogEmailSend){
		return super.saveReturn(tbSysLogEmailSend);
	}
	
	public TbSysLogEmailSend update(TbSysLogEmailSend tbSysLogEmailSend){
		return super.update(tbSysLogEmailSend);
	}
	
	public void delete(TbSysLogEmailSend tbSysLogEmailSend){
		Object object = tbSysLogEmailSend.getId();
		super.delete(object, TbSysLogEmailSend.class);
	}
	
	public List<TbSysLogEmailSend> simpleListQuery(String sqlQuery){
		return super.selectListSimpleQueryString(sqlQuery);
	}
	
}
