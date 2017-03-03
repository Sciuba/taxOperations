package br.com.saboia.app.quote.facade;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

import br.com.saboia.app.quote.model.QuoteModel;
import br.com.saboia.app.quote.to.QuoteItemTO;

@Local
public interface AppQuote {

	public List<QuoteItemTO> executeStoredProcedure(int idTaxQuote);
	
	public QuoteModel CalcularQuote(QuoteModel quoteModel);
	
	public Float selectMaxIvaByNCM(String ncm);
	
	public QuoteModel somaCalculoQuoteRelease(QuoteModel quoteModel);
	
	public void salvarAtributos(QuoteModel quoteModel);
	
	public QuoteModel goalSeek(QuoteModel quoteModel, String typeValue);
	
	public QuoteItemTO calculationScenarioSW(QuoteItemTO quoteItemTO);
	
	public QuoteItemTO calculationScenarioSV(QuoteItemTO quoteItemTO);
	
	public QuoteItemTO calculationScenario1(QuoteItemTO quoteItemTO);
	
	public QuoteItemTO calculationScenario4(QuoteItemTO quoteItemTO);
	
	public QuoteItemTO calculationScenario6(QuoteItemTO quoteItemTO);
	
	public QuoteItemTO calculationScenario7(QuoteItemTO quoteItemTO);
	
	public QuoteItemTO calculationScenario9(QuoteItemTO quoteItemTO);
	
	public QuoteItemTO calculationScenario11(QuoteItemTO quoteItemTO);
	
	public QuoteItemTO calculationScenario99(QuoteItemTO quoteItemTO);
	
	public QuoteModel biggestRateRecover(QuoteModel quoteModel) throws NumberFormatException, SQLException;
	
}
