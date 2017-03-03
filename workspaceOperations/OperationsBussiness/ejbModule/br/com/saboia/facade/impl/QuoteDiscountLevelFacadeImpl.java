package br.com.saboia.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.dao.AdmQuoteDiscountLevelDAO;
import br.com.saboia.entity.TbAdmQuoteDiscountLevel;
import br.com.saboia.facade.QuoteDiscountLevelFacade;

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
