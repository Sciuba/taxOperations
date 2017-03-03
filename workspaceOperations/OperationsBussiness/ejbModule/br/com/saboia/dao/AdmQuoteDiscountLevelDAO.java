package br.com.saboia.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmQuoteDiscountLevel;

@Local
public interface AdmQuoteDiscountLevelDAO {
	
	List<TbAdmQuoteDiscountLevel> findAll();
	
	void save(TbAdmQuoteDiscountLevel discountLevel);
	
	void alter(TbAdmQuoteDiscountLevel discountLevel);
	
	void delete(TbAdmQuoteDiscountLevel discountLevel);
	
	TbAdmQuoteDiscountLevel find(Long id);
	
	TbAdmQuoteDiscountLevel saveReturn(TbAdmQuoteDiscountLevel discountLevel);

}
