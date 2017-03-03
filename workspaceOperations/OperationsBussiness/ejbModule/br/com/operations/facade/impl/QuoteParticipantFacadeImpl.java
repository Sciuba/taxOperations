package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.TaxQuoteParticipantDAO;
import br.com.operations.entity.TbTaxQuoteParticipant;
import br.com.operations.facade.QuoteParticipantFacade;

@Stateless
public class QuoteParticipantFacadeImpl implements QuoteParticipantFacade {

	@EJB
	private TaxQuoteParticipantDAO participantDAO;
	
	@Override
	public List<TbTaxQuoteParticipant> findAll() {
		return participantDAO.findAll();
	}

	@Override
	public List<TbTaxQuoteParticipant> findOrderBy() {
		return participantDAO.findOrderBy();
	}

	@Override
	public void save(TbTaxQuoteParticipant quoteParticipant) {
		participantDAO.save(quoteParticipant);
	}

	@Override
	public void alter(TbTaxQuoteParticipant quoteParticipant) {
		participantDAO.alter(quoteParticipant);
	}

	@Override
	public void delete(TbTaxQuoteParticipant quoteParticipant) {
		participantDAO.delete(quoteParticipant);
	}

	@Override
	public TbTaxQuoteParticipant find(Long id) {
		return participantDAO.find(id);
	}

	@Override
	public TbTaxQuoteParticipant saveReturn(
			TbTaxQuoteParticipant quoteParticipant) {
		return participantDAO.saveReturn(quoteParticipant);
	}

	@Override
	public List<TbTaxQuoteParticipant> simpleQuery() {
		return participantDAO.simpleQuery(null);
	}

}
