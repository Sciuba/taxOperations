package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmQuoteDiscountLevel;

@Local
public interface AdmQuoteDiscountLevelDAO {
	
	List<TbAdmQuoteDiscountLevel> findAll();
	
	void save(TbAdmQuoteDiscountLevel discountLevel);
	
	void alter(TbAdmQuoteDiscountLevel discountLevel);
	
	void delete(TbAdmQuoteDiscountLevel discountLevel);
	
	TbAdmQuoteDiscountLevel find(Long id);
	
	TbAdmQuoteDiscountLevel saveReturn(TbAdmQuoteDiscountLevel discountLevel);

}
