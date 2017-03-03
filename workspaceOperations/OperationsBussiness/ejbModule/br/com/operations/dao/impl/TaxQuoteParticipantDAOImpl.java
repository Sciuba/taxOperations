package br.com.operations.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.operations.dao.TaxQuoteParticipantDAO;
import br.com.operations.entity.TbTaxQuoteParticipant;
import br.com.operations.jpa.GenericDAO;

@Stateless
public class TaxQuoteParticipantDAOImpl extends
		GenericDAO<TbTaxQuoteParticipant> implements TaxQuoteParticipantDAO {

	public TaxQuoteParticipantDAOImpl() {
		super(TbTaxQuoteParticipant.class);		
	}

	@Override
	public List<TbTaxQuoteParticipant> findAll() {
		return super.findAll();
	}

	@Override
	public List<TbTaxQuoteParticipant> findOrderBy() {
		return null;
	}

	@Override
	public void save(TbTaxQuoteParticipant quoteParticipant) {
		super.save(quoteParticipant);
	}

	@Override
	public void alter(TbTaxQuoteParticipant quoteParticipant) {
		super.update(quoteParticipant);
	}

	@Override
	public void delete(TbTaxQuoteParticipant quoteParticipant) {
		Object object = quoteParticipant.getId();
		super.delete(object, TbTaxQuoteParticipant.class);
	}

	@Override
	public TbTaxQuoteParticipant find(Long id) {
		return super.find(id);
	}

	@Override
	public TbTaxQuoteParticipant saveReturn(
			TbTaxQuoteParticipant quoteParticipant) {
		return super.saveReturn(quoteParticipant);
	}

	@Override
	public List<TbTaxQuoteParticipant> simpleQuery(String sqlQuery) {
		return super.selectListSimpleQueryString(sqlQuery);
	}

}
