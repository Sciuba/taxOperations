package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.impl.OriginXProductTypeDAOImpl;
import br.com.operations.entity.TbAdmOriginXProductTypeSV;

@Stateless
public class OriginXProductTypeSvFacadeImpl {

	@EJB
	private OriginXProductTypeDAOImpl typeDAOImpl;
	
	
	public TbAdmOriginXProductTypeSV find(Long id){
		return typeDAOImpl.find(id);
	}
	
	public TbAdmOriginXProductTypeSV findByIdOriginAndProductType(Long idOrigin, Long idProductType){
		
		String sqlQuery = "select x from TbAdmOriginXProductTypeSV x where x.tbAdmOrigin.id = "+idOrigin+" and x.tbAdmProductType.id = "+idProductType;
		
		return typeDAOImpl.simpleQuery(sqlQuery);
	}
	
	public void save(TbAdmOriginXProductTypeSV tbAdmOriginXProductTypeSV){
		typeDAOImpl.save(tbAdmOriginXProductTypeSV);
	}
	
	public TbAdmOriginXProductTypeSV update(TbAdmOriginXProductTypeSV tbAdmOriginXProductTypeSV){
		return typeDAOImpl.update(tbAdmOriginXProductTypeSV);
	}
	
	public void delete(TbAdmOriginXProductTypeSV tbAdmOriginXProductTypeSV){
		typeDAOImpl.delete(tbAdmOriginXProductTypeSV);
	}
	
	public List<TbAdmOriginXProductTypeSV> findByIdOrigin(Long idOrigin){
		
		String sqlQuery = "select x from TbAdmOriginXProductTypeSV x where x.tbAdmOrigin.id ="+idOrigin;
		
		return typeDAOImpl.listSimpleQuery(sqlQuery);
	}
	
	public List<TbAdmOriginXProductTypeSV> findAll(){
		return typeDAOImpl.findAll();
	}
	
	public void deleteAllByOrigin(long idOrigin){
		
		String sqlQuery = "delete from TbAdmOriginXProductTypeSV sv where sv.tbAdmOrigin.id ="+idOrigin;
		
		typeDAOImpl.deleteAll(sqlQuery);
	}
	
}
