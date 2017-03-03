package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmQuoteDiscountLevel;

@Local
public interface QuoteDiscountLevelFacade {
	
	List<TbAdmQuoteDiscountLevel> findAll();
	
	void save(TbAdmQuoteDiscountLevel discountLevel);
	
	void alter(TbAdmQuoteDiscountLevel discountLevel);
	
	void delete(TbAdmQuoteDiscountLevel discountLevel);
	
	TbAdmQuoteDiscountLevel find(Long id);
	
	TbAdmQuoteDiscountLevel saveReturn(TbAdmQuoteDiscountLevel discountLevel);

}
