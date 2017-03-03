package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.AdmQuoteDiscountLevelDAO;
import br.com.operations.entity.TbAdmQuoteDiscountLevel;
import br.com.operations.facade.QuoteDiscountLevelFacade;

@Stateless
public class QuoteDiscountLevelFacadeImpl implements QuoteDiscountLevelFacade {

	@EJB
	private AdmQuoteDiscountLevelDAO discountLevelDAO;
	
	@Override
	public List<TbAdmQuoteDiscountLevel> findAll() {
		return discountLevelDAO.findAll();
	}

	@Override
	public void save(TbAdmQuoteDiscountLevel discountLevel) {
		discountLevelDAO.save(discountLevel);
	}

	@Override
	public void alter(TbAdmQuoteDiscountLevel discountLevel) {
		discountLevelDAO.alter(discountLevel);
	}

	@Override
	public void delete(TbAdmQuoteDiscountLevel discountLevel) {
		discountLevelDAO.delete(discountLevel);
	}

	@Override
	public TbAdmQuoteDiscountLevel find(Long id) {
		return discountLevelDAO.find(id);
	}

	@Override
	public TbAdmQuoteDiscountLevel saveReturn(TbAdmQuoteDiscountLevel discountLevel) {
		return discountLevelDAO.saveReturn(discountLevel);
	}

}
