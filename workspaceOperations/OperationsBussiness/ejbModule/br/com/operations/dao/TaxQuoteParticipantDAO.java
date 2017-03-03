package br.com.operations.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbTaxQuoteParticipant;

@Local
public interface TaxQuoteParticipantDAO {
	
	List<TbTaxQuoteParticipant> findAll();
	
	List<TbTaxQuoteParticipant> findOrderBy();
	
	void save(TbTaxQuoteParticipant quoteParticipant);
	
	void alter(TbTaxQuoteParticipant quoteParticipant);
	
	void delete(TbTaxQuoteParticipant quoteParticipant);
	
	TbTaxQuoteParticipant find(Long id);

	TbTaxQuoteParticipant saveReturn(TbTaxQuoteParticipant quoteParticipant);
	
	List<TbTaxQuoteParticipant> simpleQuery(String sqlQuery);

}
