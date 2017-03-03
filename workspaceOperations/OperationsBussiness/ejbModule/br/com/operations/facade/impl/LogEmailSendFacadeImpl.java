package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.impl.LogEmailSendDAOImpl;
import br.com.operations.entity.TbSysLogEmailSend;

@Stateless
public class LogEmailSendFacadeImpl {

	@EJB
	private LogEmailSendDAOImpl emailSendDAOImpl;
	
	public void save(TbSysLogEmailSend tbSysLogEmailSend){
		emailSendDAOImpl.save(tbSysLogEmailSend);
	}
	
	public TbSysLogEmailSend saveReturn(TbSysLogEmailSend emailSend){
		return emailSendDAOImpl.saveReturn(emailSend);
	}
	
	public TbSysLogEmailSend find(Long id){
		return emailSendDAOImpl.find(id);
	}
	
	public TbSysLogEmailSend update(TbSysLogEmailSend emailSend){
		return emailSendDAOImpl.update(emailSend);
	}
	
	public void delete(TbSysLogEmailSend emailSend){
		emailSendDAOImpl.delete(emailSend);
	}
	
	public List<TbSysLogEmailSend> simpleList(){
		
		String sqlQuery = "";
		
		return emailSendDAOImpl.simpleListQuery(sqlQuery);
	}
	
	public TbSysLogEmailSend simpleQuery(){
		
		String sqlQuery = "";
		
		return emailSendDAOImpl.simpleQuery(sqlQuery);
	}
	
}
