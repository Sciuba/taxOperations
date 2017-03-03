package br.com.saboia.app.quote.dao;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

import br.com.saboia.app.quote.model.QuoteModel;
import br.com.saboia.app.quote.to.QuoteItemTO;

@Local
public interface AppQuoteDAO {
	
	public List<QuoteItemTO> executeStoredProcedure(int idTaxQuote);
		
	public void salvarAtributos(QuoteModel quoteModel);
	
	public Object simpleQuery(String sqlQuery) throws SQLException;
	
	public Float selectMaxIvaByNCM(String ncm);
	
	public void updateQuery(String sqlQuery) throws SQLException;
	
	public QuoteModel selectRate(QuoteModel quoteModel);
	
	public QuoteModel selectProductType(QuoteModel quoteModel);
	
	public QuoteModel selectMaterialClass(QuoteModel quoteModel);
	
	public QuoteModel selectISS(QuoteModel quoteModel);
	
	public QuoteModel selectDefaultTaxes(QuoteModel quoteModel);
	
	public QuoteModel selectICMSIPI(QuoteModel quoteModel);
	
}
