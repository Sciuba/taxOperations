package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmProductTypeDAO;
import br.com.saboia.entity.TbAdmProductType;
import br.com.saboia.facade.ProductTypeFacade;

@Stateless
public class ProductTypeFacadeImpl implements ProductTypeFacade {
	
	@EJB
	private AdmProductTypeDAO productTypeDAO;
	
	@Override
	public List<TbAdmProductType> findAll() {
		return productTypeDAO.findAll();
	}

	@Override
	public void save(TbAdmProductType productType) {
		productTypeDAO.save(productType);
	}

	@Override
	public void alter(TbAdmProductType productType) {
		productTypeDAO.alter(productType);
	}

	@Override
	public void delete(TbAdmProductType productType) {
		productTypeDAO.delete(productType);
	}

	@Override
	public TbAdmProductType find(Long id) {
		return productTypeDAO.find(id);
	}

	@Override
	public TbAdmProductType saveReturn(TbAdmProductType productType) {
		return productTypeDAO.saveReturn(productType);
	}

	@Override
	public TbAdmProductType findQuery(Object... params) {
		return null;
	}

	@Override
	public int findProductTypeByTasModel(int taxModel) {
		
		String sqlQuery = "select distinct count(p.id) from TbAdmProductType p where p.iTaxModel ="+taxModel;
		
		return productTypeDAO.findProductTypeByTasModel(sqlQuery);
	}
	
	public List<TbAdmProductType> findProductTypeByTaxModel(int taxModel) {
		
		String sqlQuery = "select p from TbAdmProductType p where p.iTaxModel ="+taxModel;
		
		return productTypeDAO.findByProductType(sqlQuery);
	}
	
	@Override
	public List<TbAdmProductType> findDistinctTaxModel(){
		String sqlQuery = "select distinct t from TbAdmProductType t order by t.iTaxModel";
		
		return productTypeDAO.findByProductType(sqlQuery);
	}
	
}
