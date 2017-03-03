package br.com.saboia.dao.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.com.saboia.dao.AdmProductTypeDAO;
import br.com.saboia.entity.TbAdmProductType;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class AdmProductTypeDAOImpl extends GenericDAO<TbAdmProductType>
		implements AdmProductTypeDAO {

	public AdmProductTypeDAOImpl() {
		super(TbAdmProductType.class);
	}

	@Override
	public List<TbAdmProductType> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmProductType productType) {
		super.save(productType);
	}

	@Override
	public void alter(TbAdmProductType productType) {
		super.update(productType);
	}

	@Override
	public void delete(TbAdmProductType productType) {
		Object object = productType.getId();
		super.delete(object, TbAdmProductType.class);
	}

	@Override
	public TbAdmProductType find(Long id) {
		return super.find(id);
	}

	@Override
	public TbAdmProductType saveReturn(TbAdmProductType productType) {
		return super.saveReturn(productType);
	}

	@Override
	public TbAdmProductType findQuery(String namedQuery,
			Map<String, Object> parameters) {
		return super.findOneResult(namedQuery, parameters);
	}

	@Override
	public int findProductTypeByTasModel(String sqlQuery) {
		return super.selectTotalNumberRegistry(sqlQuery);
	}

	@Override
	public List<TbAdmProductType> findByProductType(String sqlQuery) {
		return super.selectListSimpleQueryString(sqlQuery);
	}

}
