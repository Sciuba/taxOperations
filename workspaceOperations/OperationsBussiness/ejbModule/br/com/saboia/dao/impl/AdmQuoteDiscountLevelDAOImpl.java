package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.dao.AdmQuoteDiscountLevelDAO;
import br.com.saboia.entity.TbAdmQuoteDiscountLevel;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class AdmQuoteDiscountLevelDAOImpl extends GenericDAO<TbAdmQuoteDiscountLevel> implements
		AdmQuoteDiscountLevelDAO {

	public AdmQuoteDiscountLevelDAOImpl() {
		super(TbAdmQuoteDiscountLevel.class);		
	}

	@Override
	public List<TbAdmQuoteDiscountLevel> findAll() {
		return super.findAll();
	}

	@Override
	public void save(TbAdmQuoteDiscountLevel discountLevel) {
		super.save(discountLevel);
	}

	@Override
	public void alter(TbAdmQuoteDiscountLevel discountLevel) {
		super.update(discountLevel);
	}

	@Override
	public void delete(TbAdmQuoteDiscountLevel discountLevel) {
		Object object = discountLevel.getId();
		super.delete(object, TbAdmQuoteDiscountLevel.class);
	}

	@Override
	public TbAdmQuoteDiscountLevel find(Long id) {
		return super.find(id);
	}

	@Override
	public TbAdmQuoteDiscountLevel saveReturn(TbAdmQuoteDiscountLevel discountLevel) {
		return super.saveReturn(discountLevel);
	}
	
}
