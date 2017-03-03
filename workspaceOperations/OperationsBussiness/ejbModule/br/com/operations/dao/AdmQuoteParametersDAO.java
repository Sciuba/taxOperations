package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmQuoteParameters;

@Local
public interface AdmQuoteParametersDAO {
	
	List<TbAdmQuoteParameters> findAll();
	
	void save(TbAdmQuoteParameters quoteParameters);
	
	void alter(TbAdmQuoteParameters quoteParameters);
	
	void delete(TbAdmQuoteParameters quoteParameters);
	
	TbAdmQuoteParameters find(Long id);

	TbAdmQuoteParameters saveReturn(TbAdmQuoteParameters quoteParameters);

}
