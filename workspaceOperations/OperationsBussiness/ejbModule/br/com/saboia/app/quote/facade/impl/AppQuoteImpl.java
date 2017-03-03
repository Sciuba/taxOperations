package br.com.saboia.app.quote.facade.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.saboia.app.quote.dao.AppQuoteDAO;
import br.com.saboia.app.quote.facade.AppQuote;
import br.com.saboia.app.quote.model.QuoteModel;
import br.com.saboia.app.quote.to.QuoteItemTO;
import br.com.saboia.app.quote.to.SomaTotaisTO;
import br.com.saboia.dao.TaxQuoteDAO;
import br.com.saboia.dao.TaxQuoteDetailDAO;

/**
 * 
 * @author Fernando
 *
 */
@Stateless
public class AppQuoteImpl implements AppQuote {

	@EJB
	private AppQuoteDAO appQuoteDAO;
	
	@EJB
	private TaxQuoteDAO quoteDAO;
	
	@EJB
	private TaxQuoteDetailDAO detailDAO;
	
	@Override
	public List<QuoteItemTO> executeStoredProcedure(int idTaxQuote) {
		
		List<QuoteItemTO> listaQuoteItemTOs = appQuoteDAO.executeStoredProcedure(idTaxQuote);
		
		return listaQuoteItemTOs;
	}
	
	@Override
	public Float selectMaxIvaByNCM(String ncm){
		
		return appQuoteDAO.selectMaxIvaByNCM(ncm);
		
	}
	
	/**
	 * Identifica o tipo de item da Quote ( Hw, SV, TS ou MD) e faz o Cálculo conforme as regras e alíquotas existentes
	 * @author Fernando
	 * @param QuoteModel
	 */
	@Override
	public QuoteModel CalcularQuote(QuoteModel quoteModel) {

		zerarTotais(quoteModel);
		
		QuoteItemTO itemPAi = null;
		
		for(QuoteItemTO itemTO : quoteModel.getListaQuoteItemTO()){
			
			float MaxIvaMAterialImportadoDestinantion = 0;
									
			//material nao classificado, significa que é HW e assume as maiores aliquotas cadastradas no sistema
			if((itemTO.getIdAdmProductType() == 0 || itemTO.getfAvailableMaterialClass() == 0)){
				
				itemTO.setrICMSMaterialClassMaterialClass(quoteModel.getDblrMaiorICMS());
				itemTO.setrICMSInterEstadualDestination(quoteModel.getDblrMaiorICMS());
				itemTO.setrICMSInterEstadualMaterialImportadoDestination(quoteModel.getDblrMaiorICMS());
				itemTO.setrICMSEstadoOrigemInternoMaterialClassOrigin(quoteModel.getDblrMaiorICMS());
				itemTO.setrICMSEstadoOrigemOrigin(quoteModel.getDblrMaiorICMS());
				itemTO.setrICMSEstadoDestinoDestination(quoteModel.getDblrMaiorICMS());
				itemTO.setrICMSEstadoOrigemInternoMaterialClassOrigin(quoteModel.getDblrMaiorICMS());
				itemTO.setrIVAOrigin(quoteModel.getDblrMaiorICMS());
				itemTO.setrIVAMaterialImportadoOrigin(quoteModel.getDblrMaiorICMS());
				itemTO.setrIRProductType(quoteModel.getDblrMaiorIR());
				itemTO.setrCSSLProductType(quoteModel.getDblrMaiorCSSL());
				itemTO.setrIPIMaterialClass(0);
				
				//maiores aliquotas para materiais não classificados
				itemTO.setrPISProductType(quoteModel.getDblrMaiorPIS());
				itemTO.setrCOFINSProductType(quoteModel.getDblrMaiorCofins());
				
				//assume que é Hardware
				itemTO.setIdAdmProductType(1);
				itemTO.setiTaxModel(1);
			}
			
			//Identificando item pai
			if(itemTO.getIdTaxQuoteItem() == itemTO.getId()){
				itemPAi = itemTO;
				continue;
			}
			
			//pega os impostos do pai e passa para o filho
			if( itemPAi != null && itemTO.getIdTaxQuoteItem()  == itemPAi.getId() && itemTO.getiFlagTypeLine() != 3){
				assumeValoresItemPai(itemPAi, itemTO);				
			}
			
			
			if(itemTO.getsNCMMaterialClassPai() != null){
				MaxIvaMAterialImportadoDestinantion = selectMaxIvaByNCM(itemTO.getsNCMMaterialClassPai());
			}else if(itemTO.getsNCM() != null){
				MaxIvaMAterialImportadoDestinantion = selectMaxIvaByNCM(itemTO.getsNCM());
			}else{
				MaxIvaMAterialImportadoDestinantion = selectMaxIvaByNCM(itemTO.getsNCMMaterialClass());
			}
			
			
			//Aplicando Isenções de impostos
			if(quoteModel.isfSt()){
				
				itemTO.setfSubstituicaoTributariaSaidaMaterialClass(0);
			}
			
			if(quoteModel.isfCOFINS()){
				
				itemTO.setrCOFINS(0);
				itemTO.setrCOFINSProductType(0);
				
			}
			
			if(quoteModel.isfPIS()){
				
				itemTO.setrPIS(0);
				itemTO.setrPISProductType(0);
			}
			
			if(quoteModel.isfICMS()){
				
				itemTO.setrICMSARecuperarST(0);
				itemTO.setrICMSEstadoDestino(0);
				itemTO.setrICMSEstadoDestinoDestination(0);
				itemTO.setrICMSEstadoOrigem(0);
				itemTO.setrICMSEstadoOrigemInterno(0);
				itemTO.setrICMSEstadoOrigemInternoMaterialClassOrigin(0);
				itemTO.setrICMSInterEstadualMaterialImportadoDestination(0);
				itemTO.setrICMSInterEstadualOrigemOrigin(0);
				itemTO.setrICMSInterno(0);
				itemTO.setrICMSInternoMaterialClassDestination(0);
				itemTO.setrICMSInternoOrigemOrigin(0);
				itemTO.setrICMSMAterialClass(0);
				itemTO.setrICMSMaterialClassMaterialClass(0);
				itemTO.setrICMSProprioARecuperar(0);
				itemTO.setrICMSRecuperar(0);
				
			}
			
			if(quoteModel.isfIPI()){
				
				itemTO.setrIPI(0);
				itemTO.setrIPIMaterialClass(0);
				itemTO.setrIPIRecuperar(0);
				
			}
			
			if(quoteModel.isfISS()){
				
				itemTO.setrISS(0);
				itemTO.setrISSOrigin(0);
				itemTO.setrISSOriginServ(0);
				itemTO.setrISSSoftOrigin(0);
				
			}
			
			
			if(itemTO.getiTaxModel() == 2){
				
				itemTO = calculationScenarioSW(itemTO);
				
				quoteModel.setGrossPriceSW(quoteModel.getGrossPriceSW() +  itemTO.getmGrossPrice());
				quoteModel.setNetPriceSW(quoteModel.getNetPriceSW() + itemTO.getmNetPrice());
				
				quoteModel.setTaxesSW(quoteModel.getTaxesSW() + itemTO.getmTotalTaxes());
				
				quoteModel.setNetMarginSW(quoteModel.getNetMarginSW() + itemTO.getNetMargin());
				quoteModel.setNetPriceLessCostSW(quoteModel.getNetPriceLessCostSW() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
				
				quoteModel.setmICMSMaterialSW(quoteModel.getmICMSMaterialSW() + itemTO.getmICMSMaterialClass());
				quoteModel.setmICMSInterEstadualSW(quoteModel.getmICMSInterEstadualSW() + itemTO.getmICMSInterEstadual());
				quoteModel.setmICMSinterestadualMatImportadoSW(quoteModel.getmICMSinterestadualMatImportadoSW() + itemTO.getmICMSInterEstadualMaterialImportado());
				quoteModel.setmICMSSTSW(quoteModel.getmICMSSTSW() + itemTO.getmICMSST());
				quoteModel.setmIPISW(quoteModel.getmIPISW() + itemTO.getmIPI());
				quoteModel.setmISSSW(quoteModel.getmISSSW() + itemTO.getmISS());
				quoteModel.setmCOFINSSW(quoteModel.getmCOFINSSW() + itemTO.getmCOFINS());
				quoteModel.setmIRSW(quoteModel.getmIRSW() + itemTO.getmIR());
				quoteModel.setmCSSLSW(quoteModel.getmCSSLSW() + itemTO.getmCSSL());
				quoteModel.setmICMSRecoverableSW(quoteModel.getmICMSRecoverableSW() + itemTO.getmICMSARecuperar());
				quoteModel.setmTotalTaxesSW(quoteModel.getmTotalTaxesSW() + itemTO.getmTotalTaxes());
				quoteModel.setmICMSEstadoDestinoSW(quoteModel.getmICMSEstadoDestinoSW() + itemTO.getmICMSEstadoDestino());
				quoteModel.setmICMSEstadoOriginSW(quoteModel.getmICMSEstadoOriginSW() + itemTO.getmICMSEstadoOrigem());
				quoteModel.setmPISSW(quoteModel.getmPISSW() + itemTO.getmPIS());
				
				
			}else if(itemTO.getiTaxModel() == 3){
								
				itemTO = calculationScenarioSV(itemTO);
				
				quoteModel.setGrossPriceCS(quoteModel.getGrossPriceCS() +  itemTO.getmGrossPrice());
				quoteModel.setNetPriceCS(quoteModel.getNetPriceCS() + itemTO.getmNetPrice());
				
				quoteModel.setTaxesCS(quoteModel.getTaxesCS() + itemTO.getmTotalTaxes());
				quoteModel.setNetMarginCS(quoteModel.getNetMarginCS() + itemTO.getNetMargin());
				quoteModel.setNetPriceLessCostCS(quoteModel.getNetPriceLessCostCS() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
				
				quoteModel.setmICMSMaterialCS(quoteModel.getmICMSMaterialCS() + itemTO.getmICMSMaterialClass());
				quoteModel.setmICMSInterEstadualCS(quoteModel.getmICMSInterEstadualCS() + itemTO.getmICMSInterEstadual());
				quoteModel.setmICMSinterestadualMatImportadoCS(quoteModel.getmICMSinterestadualMatImportadoCS() + itemTO.getmICMSInterEstadualMaterialImportado());
				quoteModel.setmICMSSTCS(quoteModel.getmICMSSTCS() + itemTO.getmICMSST());
				quoteModel.setmIPICS(quoteModel.getmIPICS() + itemTO.getmIPI());
				quoteModel.setmISSCS(quoteModel.getmISSCS() + itemTO.getmISS());
				quoteModel.setmCOFINSCS(quoteModel.getmCOFINSCS() + itemTO.getmCOFINS());
				quoteModel.setmIRCS(quoteModel.getmIRCS() + itemTO.getmIR());
				quoteModel.setmCSSLCS(quoteModel.getmCSSLCS() + itemTO.getmCSSL());
				quoteModel.setmICMSRecoverableCS(quoteModel.getmICMSRecoverableCS() + itemTO.getmICMSARecuperar());
				quoteModel.setmTotalTaxesCS(quoteModel.getmTotalTaxesCS() + itemTO.getmTotalTaxes());
				quoteModel.setmICMSEstadoDestinoCS(quoteModel.getmICMSEstadoDestinoCS() + itemTO.getmICMSEstadoDestino());
				quoteModel.setmICMSEstadoOriginCS(quoteModel.getmICMSEstadoOriginCS() + itemTO.getmICMSEstadoOrigem());
				quoteModel.setmPISCS(quoteModel.getmPISCS() + itemTO.getmPIS());

	//TODO: Não Existe mais TaxModel 4
//			}else if(itemTO.getiTaxModel() == 4){
//			
//				itemTO = calculationScenarioSV(itemTO);
//				
//				quoteModel.setGrossPricePS(quoteModel.getGrossPricePS() +  itemTO.getmGrossPrice());
//				quoteModel.setNetPricePS(quoteModel.getNetPricePS() + itemTO.getmNetPrice());
//				
//				quoteModel.setTaxesPS(quoteModel.getTaxesPS() + itemTO.getmTotalTaxes());
//				quoteModel.setNetMarginPS(quoteModel.getNetMarginPS() + itemTO.getNetMargin());
//				quoteModel.setNetPriceLessCostPS(quoteModel.getNetPriceLessCostPS() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
//				
//				quoteModel.setmICMSMaterialPS(quoteModel.getmICMSMaterialPS() + itemTO.getmICMSMaterialClass());
//				quoteModel.setmICMSInterEstadualPS(quoteModel.getmICMSInterEstadualPS() + itemTO.getmICMSInterEstadual());
//				quoteModel.setmICMSinterestadualMatImportadoPS(quoteModel.getmICMSinterestadualMatImportadoPS() + itemTO.getmICMSInterEstadualMaterialImportado());
//				quoteModel.setmICMSSTPS(quoteModel.getmICMSSTPS() + itemTO.getmICMSST());
//				quoteModel.setmIPIPS(quoteModel.getmIPIPS() + itemTO.getmIPI());
//				quoteModel.setmISSPS(quoteModel.getmISSPS() + itemTO.getmISS());
//				quoteModel.setmCOFINSPS(quoteModel.getmCOFINSPS() + itemTO.getmCOFINS());
//				quoteModel.setmIRPS(quoteModel.getmIRPS() + itemTO.getmIR());
//				quoteModel.setmCSSLPS(quoteModel.getmCSSLPS() + itemTO.getmCSSL());
//				quoteModel.setmICMSRecoverablePS(quoteModel.getmICMSRecoverablePS() + itemTO.getmICMSARecuperar());
//				quoteModel.setmTotalTaxesPS(quoteModel.getmTotalTaxesPS() + itemTO.getmTotalTaxes());
//				quoteModel.setmICMSEstadoDestinoPS(quoteModel.getmICMSEstadoDestinoPS() + itemTO.getmICMSEstadoDestino());
//				quoteModel.setmICMSEstadoOriginPS(quoteModel.getmICMSEstadoOriginPS() + itemTO.getmICMSEstadoOrigem());
//				quoteModel.setmPISPS(quoteModel.getmPISPS() + itemTO.getmPIS());
				
			}else{ //Hardware
				
				//IdAdmOrigin == 5 => São Paulo
				
				
				// Verifico se há substituição tributária
				if(itemTO.getfSubstituicaoTributariaSaidaMaterialClass() == 1 && quoteModel.isBlnfContrEst() && itemTO.getfDestinationWithProtocol() == 1 
						&& ((itemTO.getrIVAMaterialImportadoMaterialClassDestination() > 0) || MaxIvaMAterialImportadoDestinantion > 0)){
					
					if(itemTO.getrIVAMaterialImportadoMaterialClassDestination() == 0){
						itemTO.setfUseMaxIvaMaterialImportado(1);
						itemTO.setrIVAMaterialImportadoMaterialClassDestination(MaxIvaMAterialImportadoDestinantion);
					}
					
					
					//TODO: Modelo 1 e modelo 2 estão sendo unificados pois não existe diferença de calculo entre estados com protocolo.
					//if(itemTO.getIdAdmOriginHW() == 5 && itemTO.getIdAdmDestination() == 11  && !quoteModel.isfPartner() && !quoteModel.isBlnfSuframa() && !quoteModel.isBlnfLeasing()){
					
					//TODO: Caso específico ara atender são paulo origem para são paulo destino
					if(itemTO.getIdAdmOriginHW() == 5 && itemTO.getIdAdmDestination() == 4 && !quoteModel.isfPartner() && quoteModel.isBlnfContrEst()){
					
						itemTO = calculationScenario6(itemTO);										
						
						if(itemTO.getiTaxModel() == 5){
							quoteModel.setGrossPriceMD(quoteModel.getGrossPriceMD() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceMD(quoteModel.getNetPriceMD() + itemTO.getmNetPrice());
							quoteModel.setTaxesMD(quoteModel.getTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginMD(quoteModel.getNetMarginMD() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostMD(quoteModel.getNetPriceLessCostMD() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							quoteModel.setmICMSMaterialMD(quoteModel.getmICMSMaterialMD() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualMD(quoteModel.getmICMSInterEstadualMD() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoMD(quoteModel.getmICMSinterestadualMatImportadoMD() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTMD(quoteModel.getmICMSSTMD() + itemTO.getmICMSST());
							quoteModel.setmIPIMD(quoteModel.getmIPIMD() + itemTO.getmIPI());
							quoteModel.setmISSMD(quoteModel.getmISSMD() + itemTO.getmISS());
							quoteModel.setmCOFINSMD(quoteModel.getmCOFINSMD() + itemTO.getmCOFINS());
							quoteModel.setmIRMD(quoteModel.getmIRMD() + itemTO.getmIR());
							quoteModel.setmCSSLMD(quoteModel.getmCSSLMD() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableMD(quoteModel.getmICMSRecoverableMD() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesMD(quoteModel.getmTotalTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoMD(quoteModel.getmICMSEstadoDestinoMD() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginMD(quoteModel.getmICMSEstadoOriginMD() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISMD(quoteModel.getmPISMD() + itemTO.getmPIS());
							
						}else{
							quoteModel.setGrossPriceHW(quoteModel.getGrossPriceHW() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceHW(quoteModel.getNetPriceHW() + itemTO.getmNetPrice());
							quoteModel.setTaxesHW(quoteModel.getTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginHW(quoteModel.getNetMarginHW() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostHW(quoteModel.getNetPriceLessCostHW() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialHW(quoteModel.getmICMSMaterialHW() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualHW(quoteModel.getmICMSInterEstadualHW() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoHW(quoteModel.getmICMSinterestadualMatImportadoHW() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTHW(quoteModel.getmICMSSTHW() + itemTO.getmICMSST());
							quoteModel.setmIPIHW(quoteModel.getmIPIHW() + itemTO.getmIPI());
							quoteModel.setmISSHW(quoteModel.getmISSHW() + itemTO.getmISS());
							quoteModel.setmCOFINSHW(quoteModel.getmCOFINSHW() + itemTO.getmCOFINS());
							quoteModel.setmIRHW(quoteModel.getmIRHW() + itemTO.getmIR());
							quoteModel.setmCSSLHW(quoteModel.getmCSSLHW() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableHW(quoteModel.getmICMSRecoverableHW() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesHW(quoteModel.getmTotalTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoHW(quoteModel.getmICMSEstadoDestinoHW() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginHW(quoteModel.getmICMSEstadoOriginHW() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISHW(quoteModel.getmPISHW() + itemTO.getmPIS());
						}
						
						
					}else if(itemTO.getIdAdmOriginHW() == 5 && !quoteModel.isfPartner()){
					
						itemTO = calculationScenario1(itemTO);
						
						if(itemTO.getiTaxModel() == 5){
							quoteModel.setGrossPriceMD(quoteModel.getGrossPriceMD() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceMD(quoteModel.getNetPriceMD() + itemTO.getmNetPrice());
							quoteModel.setTaxesMD(quoteModel.getTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginMD(quoteModel.getNetMarginMD() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostMD(quoteModel.getNetPriceLessCostMD() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialMD(quoteModel.getmICMSMaterialMD() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualMD(quoteModel.getmICMSInterEstadualMD() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoMD(quoteModel.getmICMSinterestadualMatImportadoMD() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTMD(quoteModel.getmICMSSTMD() + itemTO.getmICMSST());
							quoteModel.setmIPIMD(quoteModel.getmIPIMD() + itemTO.getmIPI());
							quoteModel.setmISSMD(quoteModel.getmISSMD() + itemTO.getmISS());
							quoteModel.setmCOFINSMD(quoteModel.getmCOFINSMD() + itemTO.getmCOFINS());
							quoteModel.setmIRMD(quoteModel.getmIRMD() + itemTO.getmIR());
							quoteModel.setmCSSLMD(quoteModel.getmCSSLMD() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableMD(quoteModel.getmICMSRecoverableMD() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesMD(quoteModel.getmTotalTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoMD(quoteModel.getmICMSEstadoDestinoMD() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginMD(quoteModel.getmICMSEstadoOriginMD() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISMD(quoteModel.getmPISMD() + itemTO.getmPIS());
							
						}else{
							quoteModel.setGrossPriceHW(quoteModel.getGrossPriceHW() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceHW(quoteModel.getNetPriceHW() + itemTO.getmNetPrice());
							quoteModel.setTaxesHW(quoteModel.getTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginHW(quoteModel.getNetMarginHW() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostHW(quoteModel.getNetPriceLessCostHW() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialHW(quoteModel.getmICMSMaterialHW() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualHW(quoteModel.getmICMSInterEstadualHW() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoHW(quoteModel.getmICMSinterestadualMatImportadoHW() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTHW(quoteModel.getmICMSSTHW() + itemTO.getmICMSST());
							quoteModel.setmIPIHW(quoteModel.getmIPIHW() + itemTO.getmIPI());
							quoteModel.setmISSHW(quoteModel.getmISSHW() + itemTO.getmISS());
							quoteModel.setmCOFINSHW(quoteModel.getmCOFINSHW() + itemTO.getmCOFINS());
							quoteModel.setmIRHW(quoteModel.getmIRHW() + itemTO.getmIR());
							quoteModel.setmCSSLHW(quoteModel.getmCSSLHW() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableHW(quoteModel.getmICMSRecoverableHW() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesHW(quoteModel.getmTotalTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoHW(quoteModel.getmICMSEstadoDestinoHW() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginHW(quoteModel.getmICMSEstadoOriginHW() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISHW(quoteModel.getmPISHW() + itemTO.getmPIS());
							
						}
						
					}else if(itemTO.getIdAdmOriginHW() == 5 && itemTO.getIdAdmDestination() == 4 && quoteModel.isfPartner()){
						
						itemTO = calculationScenario11(itemTO);
						
						if(itemTO.getiTaxModel() == 5){
							quoteModel.setGrossPriceMD(quoteModel.getGrossPriceMD() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceMD(quoteModel.getNetPriceMD() + itemTO.getmNetPrice());
							quoteModel.setTaxesMD(quoteModel.getTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginMD(quoteModel.getNetMarginMD() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostMD(quoteModel.getNetPriceLessCostMD() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialMD(quoteModel.getmICMSMaterialMD() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualMD(quoteModel.getmICMSInterEstadualMD() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoMD(quoteModel.getmICMSinterestadualMatImportadoMD() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTMD(quoteModel.getmICMSSTMD() + itemTO.getmICMSST());
							quoteModel.setmIPIMD(quoteModel.getmIPIMD() + itemTO.getmIPI());
							quoteModel.setmISSMD(quoteModel.getmISSMD() + itemTO.getmISS());
							quoteModel.setmCOFINSMD(quoteModel.getmCOFINSMD() + itemTO.getmCOFINS());
							quoteModel.setmIRMD(quoteModel.getmIRMD() + itemTO.getmIR());
							quoteModel.setmCSSLMD(quoteModel.getmCSSLMD() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableMD(quoteModel.getmICMSRecoverableMD() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesMD(quoteModel.getmTotalTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoMD(quoteModel.getmICMSEstadoDestinoMD() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginMD(quoteModel.getmICMSEstadoOriginMD() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISMD(quoteModel.getmPISMD() + itemTO.getmPIS());
							
						}else{
							quoteModel.setGrossPriceHW(quoteModel.getGrossPriceHW() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceHW(quoteModel.getNetPriceHW() + itemTO.getmNetPrice());
							quoteModel.setTaxesHW(quoteModel.getTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginHW(quoteModel.getNetMarginHW() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostHW(quoteModel.getNetPriceLessCostHW() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialHW(quoteModel.getmICMSMaterialHW() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualHW(quoteModel.getmICMSInterEstadualHW() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoHW(quoteModel.getmICMSinterestadualMatImportadoHW() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTHW(quoteModel.getmICMSSTHW() + itemTO.getmICMSST());
							quoteModel.setmIPIHW(quoteModel.getmIPIHW() + itemTO.getmIPI());
							quoteModel.setmISSHW(quoteModel.getmISSHW() + itemTO.getmISS());
							quoteModel.setmCOFINSHW(quoteModel.getmCOFINSHW() + itemTO.getmCOFINS());
							quoteModel.setmIRHW(quoteModel.getmIRHW() + itemTO.getmIR());
							quoteModel.setmCSSLHW(quoteModel.getmCSSLHW() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableHW(quoteModel.getmICMSRecoverableHW() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesHW(quoteModel.getmTotalTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoHW(quoteModel.getmICMSEstadoDestinoHW() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginHW(quoteModel.getmICMSEstadoOriginHW() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISHW(quoteModel.getmPISHW() + itemTO.getmPIS());
						}
						
					}else if(itemTO.getIdAdmOriginHW() == 5 && quoteModel.isfPartner()){
						
						itemTO = calculationScenario9(itemTO);
					
						if(itemTO.getiTaxModel() == 5){
							quoteModel.setGrossPriceMD(quoteModel.getGrossPriceMD() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceMD(quoteModel.getNetPriceMD() + itemTO.getmNetPrice());
							quoteModel.setTaxesMD(quoteModel.getTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginMD(quoteModel.getNetMarginMD() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostMD(quoteModel.getNetPriceLessCostMD() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialMD(quoteModel.getmICMSMaterialMD() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualMD(quoteModel.getmICMSInterEstadualMD() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoMD(quoteModel.getmICMSinterestadualMatImportadoMD() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTMD(quoteModel.getmICMSSTMD() + itemTO.getmICMSST());
							quoteModel.setmIPIMD(quoteModel.getmIPIMD() + itemTO.getmIPI());
							quoteModel.setmISSMD(quoteModel.getmISSMD() + itemTO.getmISS());
							quoteModel.setmCOFINSMD(quoteModel.getmCOFINSMD() + itemTO.getmCOFINS());
							quoteModel.setmIRMD(quoteModel.getmIRMD() + itemTO.getmIR());
							quoteModel.setmCSSLMD(quoteModel.getmCSSLMD() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableMD(quoteModel.getmICMSRecoverableMD() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesMD(quoteModel.getmTotalTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoMD(quoteModel.getmICMSEstadoDestinoMD() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginMD(quoteModel.getmICMSEstadoOriginMD() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISMD(quoteModel.getmPISMD() + itemTO.getmPIS());
							
						}else{
							quoteModel.setGrossPriceHW(quoteModel.getGrossPriceHW() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceHW(quoteModel.getNetPriceHW() + itemTO.getmNetPrice());
							quoteModel.setTaxesHW(quoteModel.getTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginHW(quoteModel.getNetMarginHW() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostHW(quoteModel.getNetPriceLessCostHW() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialHW(quoteModel.getmICMSMaterialHW() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualHW(quoteModel.getmICMSInterEstadualHW() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoHW(quoteModel.getmICMSinterestadualMatImportadoHW() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTHW(quoteModel.getmICMSSTHW() + itemTO.getmICMSST());
							quoteModel.setmIPIHW(quoteModel.getmIPIHW() + itemTO.getmIPI());
							quoteModel.setmISSHW(quoteModel.getmISSHW() + itemTO.getmISS());
							quoteModel.setmCOFINSHW(quoteModel.getmCOFINSHW() + itemTO.getmCOFINS());
							quoteModel.setmIRHW(quoteModel.getmIRHW() + itemTO.getmIR());
							quoteModel.setmCSSLHW(quoteModel.getmCSSLHW() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableHW(quoteModel.getmICMSRecoverableHW() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesHW(quoteModel.getmTotalTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoHW(quoteModel.getmICMSEstadoDestinoHW() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginHW(quoteModel.getmICMSEstadoOriginHW() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISHW(quoteModel.getmPISHW() + itemTO.getmPIS());
						}
						
					}else{
						
						itemTO = calculationScenario99(itemTO);										
						
						if(itemTO.getiTaxModel() == 5){
							quoteModel.setGrossPriceMD(quoteModel.getGrossPriceMD() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceMD(quoteModel.getNetPriceMD() + itemTO.getmNetPrice());
							quoteModel.setTaxesMD(quoteModel.getTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginMD(quoteModel.getNetMarginMD() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostMD(quoteModel.getNetPriceLessCostMD() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							quoteModel.setmICMSMaterialMD(quoteModel.getmICMSMaterialMD() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualMD(quoteModel.getmICMSInterEstadualMD() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoMD(quoteModel.getmICMSinterestadualMatImportadoMD() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTMD(quoteModel.getmICMSSTMD() + itemTO.getmICMSST());
							quoteModel.setmIPIMD(quoteModel.getmIPIMD() + itemTO.getmIPI());
							quoteModel.setmISSMD(quoteModel.getmISSMD() + itemTO.getmISS());
							quoteModel.setmCOFINSMD(quoteModel.getmCOFINSMD() + itemTO.getmCOFINS());
							quoteModel.setmIRMD(quoteModel.getmIRMD() + itemTO.getmIR());
							quoteModel.setmCSSLMD(quoteModel.getmCSSLMD() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableMD(quoteModel.getmICMSRecoverableMD() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesMD(quoteModel.getmTotalTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoMD(quoteModel.getmICMSEstadoDestinoMD() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginMD(quoteModel.getmICMSEstadoOriginMD() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISMD(quoteModel.getmPISMD() + itemTO.getmPIS());
							
						}else{
							quoteModel.setGrossPriceHW(quoteModel.getGrossPriceHW() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceHW(quoteModel.getNetPriceHW() + itemTO.getmNetPrice());
							quoteModel.setTaxesHW(quoteModel.getTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginHW(quoteModel.getNetMarginHW() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostHW(quoteModel.getNetPriceLessCostHW() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialHW(quoteModel.getmICMSMaterialHW() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualHW(quoteModel.getmICMSInterEstadualHW() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoHW(quoteModel.getmICMSinterestadualMatImportadoHW() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTHW(quoteModel.getmICMSSTHW() + itemTO.getmICMSST());
							quoteModel.setmIPIHW(quoteModel.getmIPIHW() + itemTO.getmIPI());
							quoteModel.setmISSHW(quoteModel.getmISSHW() + itemTO.getmISS());
							quoteModel.setmCOFINSHW(quoteModel.getmCOFINSHW() + itemTO.getmCOFINS());
							quoteModel.setmIRHW(quoteModel.getmIRHW() + itemTO.getmIR());
							quoteModel.setmCSSLHW(quoteModel.getmCSSLHW() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableHW(quoteModel.getmICMSRecoverableHW() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesHW(quoteModel.getmTotalTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoHW(quoteModel.getmICMSEstadoDestinoHW() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginHW(quoteModel.getmICMSEstadoOriginHW() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISHW(quoteModel.getmPISHW() + itemTO.getmPIS());
						}
						
					}
					
				}else{
					
					if(itemTO.getIdAdmOriginHW() == 5 && !quoteModel.isfPartner() && !quoteModel.isBlnfContrEst()){
							
						itemTO = calculationScenario7(itemTO);
						
						if(itemTO.getiTaxModel() == 5){
							quoteModel.setGrossPriceMD(quoteModel.getGrossPriceMD() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceMD(quoteModel.getNetPriceMD() + itemTO.getmNetPrice());
							quoteModel.setTaxesMD(quoteModel.getTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginMD(quoteModel.getNetMarginMD() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostMD(quoteModel.getNetPriceLessCostMD() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialMD(quoteModel.getmICMSMaterialMD() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualMD(quoteModel.getmICMSInterEstadualMD() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoMD(quoteModel.getmICMSinterestadualMatImportadoMD() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTMD(quoteModel.getmICMSSTMD() + itemTO.getmICMSST());
							quoteModel.setmIPIMD(quoteModel.getmIPIMD() + itemTO.getmIPI());
							quoteModel.setmISSMD(quoteModel.getmISSMD() + itemTO.getmISS());
							quoteModel.setmCOFINSMD(quoteModel.getmCOFINSMD() + itemTO.getmCOFINS());
							quoteModel.setmIRMD(quoteModel.getmIRMD() + itemTO.getmIR());
							quoteModel.setmCSSLMD(quoteModel.getmCSSLMD() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableMD(quoteModel.getmICMSRecoverableMD() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesMD(quoteModel.getmTotalTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoMD(quoteModel.getmICMSEstadoDestinoMD() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginMD(quoteModel.getmICMSEstadoOriginMD() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISMD(quoteModel.getmPISMD() + itemTO.getmPIS());
							
						}else{
							quoteModel.setGrossPriceHW(quoteModel.getGrossPriceHW() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceHW(quoteModel.getNetPriceHW() + itemTO.getmNetPrice());
							quoteModel.setTaxesHW(quoteModel.getTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginHW(quoteModel.getNetMarginHW() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostHW(quoteModel.getNetPriceLessCostHW() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialHW(quoteModel.getmICMSMaterialHW() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualHW(quoteModel.getmICMSInterEstadualHW() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoHW(quoteModel.getmICMSinterestadualMatImportadoHW() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTHW(quoteModel.getmICMSSTHW() + itemTO.getmICMSST());
							quoteModel.setmIPIHW(quoteModel.getmIPIHW() + itemTO.getmIPI());
							quoteModel.setmISSHW(quoteModel.getmISSHW() + itemTO.getmISS());
							quoteModel.setmCOFINSHW(quoteModel.getmCOFINSHW() + itemTO.getmCOFINS());
							quoteModel.setmIRHW(quoteModel.getmIRHW() + itemTO.getmIR());
							quoteModel.setmCSSLHW(quoteModel.getmCSSLHW() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableHW(quoteModel.getmICMSRecoverableHW() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesHW(quoteModel.getmTotalTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoHW(quoteModel.getmICMSEstadoDestinoHW() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginHW(quoteModel.getmICMSEstadoOriginHW() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISHW(quoteModel.getmPISHW() + itemTO.getmPIS());
						}
						
					}else if(itemTO.getIdAdmOriginHW() == 5 && quoteModel.isfPartner() && quoteModel.isBlnfContrEst()){
						
						itemTO = calculationScenario4(itemTO);
						
						if(itemTO.getiTaxModel() == 5){
							quoteModel.setGrossPriceMD(quoteModel.getGrossPriceMD() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceMD(quoteModel.getNetPriceMD() + itemTO.getmNetPrice());
							quoteModel.setTaxesMD(quoteModel.getTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginMD(quoteModel.getNetMarginMD() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostMD(quoteModel.getNetPriceLessCostMD() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialMD(quoteModel.getmICMSMaterialMD() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualMD(quoteModel.getmICMSInterEstadualMD() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoMD(quoteModel.getmICMSinterestadualMatImportadoMD() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTMD(quoteModel.getmICMSSTMD() + itemTO.getmICMSST());
							quoteModel.setmIPIMD(quoteModel.getmIPIMD() + itemTO.getmIPI());
							quoteModel.setmISSMD(quoteModel.getmISSMD() + itemTO.getmISS());
							quoteModel.setmCOFINSMD(quoteModel.getmCOFINSMD() + itemTO.getmCOFINS());
							quoteModel.setmIRMD(quoteModel.getmIRMD() + itemTO.getmIR());
							quoteModel.setmCSSLMD(quoteModel.getmCSSLMD() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableMD(quoteModel.getmICMSRecoverableMD() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesMD(quoteModel.getmTotalTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoMD(quoteModel.getmICMSEstadoDestinoMD() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginMD(quoteModel.getmICMSEstadoOriginMD() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISMD(quoteModel.getmPISMD() + itemTO.getmPIS());
							
						}else{
							quoteModel.setGrossPriceHW(quoteModel.getGrossPriceHW() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceHW(quoteModel.getNetPriceHW() + itemTO.getmNetPrice());
							quoteModel.setTaxesHW(quoteModel.getTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginHW(quoteModel.getNetMarginHW() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostHW(quoteModel.getNetPriceLessCostHW() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialHW(quoteModel.getmICMSMaterialHW() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualHW(quoteModel.getmICMSInterEstadualHW() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoHW(quoteModel.getmICMSinterestadualMatImportadoHW() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTHW(quoteModel.getmICMSSTHW() + itemTO.getmICMSST());
							quoteModel.setmIPIHW(quoteModel.getmIPIHW() + itemTO.getmIPI());
							quoteModel.setmISSHW(quoteModel.getmISSHW() + itemTO.getmISS());
							quoteModel.setmCOFINSHW(quoteModel.getmCOFINSHW() + itemTO.getmCOFINS());
							quoteModel.setmIRHW(quoteModel.getmIRHW() + itemTO.getmIR());
							quoteModel.setmCSSLHW(quoteModel.getmCSSLHW() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableHW(quoteModel.getmICMSRecoverableHW() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesHW(quoteModel.getmTotalTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoHW(quoteModel.getmICMSEstadoDestinoHW() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginHW(quoteModel.getmICMSEstadoOriginHW() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISHW(quoteModel.getmPISHW() + itemTO.getmPIS());
						}
						
					}else if(itemTO.getIdAdmOriginHW() == 5 && itemTO.getIdAdmDestination() == 4 && !quoteModel.isfPartner() && quoteModel.isBlnfContrEst()){
						
						itemTO = calculationScenario6(itemTO);
										
						
						if(itemTO.getiTaxModel() == 5){
							quoteModel.setGrossPriceMD(quoteModel.getGrossPriceMD() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceMD(quoteModel.getNetPriceMD() + itemTO.getmNetPrice());
							quoteModel.setTaxesMD(quoteModel.getTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginMD(quoteModel.getNetMarginMD() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostMD(quoteModel.getNetPriceLessCostMD() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							quoteModel.setmICMSMaterialMD(quoteModel.getmICMSMaterialMD() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualMD(quoteModel.getmICMSInterEstadualMD() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoMD(quoteModel.getmICMSinterestadualMatImportadoMD() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTMD(quoteModel.getmICMSSTMD() + itemTO.getmICMSST());
							quoteModel.setmIPIMD(quoteModel.getmIPIMD() + itemTO.getmIPI());
							quoteModel.setmISSMD(quoteModel.getmISSMD() + itemTO.getmISS());
							quoteModel.setmCOFINSMD(quoteModel.getmCOFINSMD() + itemTO.getmCOFINS());
							quoteModel.setmIRMD(quoteModel.getmIRMD() + itemTO.getmIR());
							quoteModel.setmCSSLMD(quoteModel.getmCSSLMD() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableMD(quoteModel.getmICMSRecoverableMD() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesMD(quoteModel.getmTotalTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoMD(quoteModel.getmICMSEstadoDestinoMD() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginMD(quoteModel.getmICMSEstadoOriginMD() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISMD(quoteModel.getmPISMD() + itemTO.getmPIS());
							
						}else{
							quoteModel.setGrossPriceHW(quoteModel.getGrossPriceHW() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceHW(quoteModel.getNetPriceHW() + itemTO.getmNetPrice());
							quoteModel.setTaxesHW(quoteModel.getTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginHW(quoteModel.getNetMarginHW() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostHW(quoteModel.getNetPriceLessCostHW() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialHW(quoteModel.getmICMSMaterialHW() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualHW(quoteModel.getmICMSInterEstadualHW() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoHW(quoteModel.getmICMSinterestadualMatImportadoHW() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTHW(quoteModel.getmICMSSTHW() + itemTO.getmICMSST());
							quoteModel.setmIPIHW(quoteModel.getmIPIHW() + itemTO.getmIPI());
							quoteModel.setmISSHW(quoteModel.getmISSHW() + itemTO.getmISS());
							quoteModel.setmCOFINSHW(quoteModel.getmCOFINSHW() + itemTO.getmCOFINS());
							quoteModel.setmIRHW(quoteModel.getmIRHW() + itemTO.getmIR());
							quoteModel.setmCSSLHW(quoteModel.getmCSSLHW() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableHW(quoteModel.getmICMSRecoverableHW() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesHW(quoteModel.getmTotalTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoHW(quoteModel.getmICMSEstadoDestinoHW() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginHW(quoteModel.getmICMSEstadoOriginHW() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISHW(quoteModel.getmPISHW() + itemTO.getmPIS());
						}
						
					}/*else if(itemTO.getIdAdmOrigin() == 5 && quoteModel.isBlnfContrEst() && !quoteModel.isBlnfSuframa()){
						
						itemTO = calculationScenario11(itemTO);
					
						quoteModel.setrGrossPrice((float) (quoteModel.getrGrossPrice() + itemTO.getmGrossPrice()));
						quoteModel.setrNetPrice((float) (quoteModel.getrNetPrice() + itemTO.getmNetPrice()));
						
						if(itemTO.getiTaxModel() == 5){
							quoteModel.setGrossPriceMD(quoteModel.getGrossPriceMD() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceMD(quoteModel.getNetPriceMD() + itemTO.getmNetPrice());
							quoteModel.setTaxesMD(quoteModel.getTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginMD(quoteModel.getNetMarginMD() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostMD(quoteModel.getNetPriceLessCostMD() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							quoteModel.setmICMSMaterialMD(quoteModel.getmICMSMaterialMD() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualMD(quoteModel.getmICMSInterEstadualMD() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoMD(quoteModel.getmICMSinterestadualMatImportadoMD() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTMD(quoteModel.getmICMSSTMD() + itemTO.getmICMSST());
							quoteModel.setmIPIMD(quoteModel.getmIPIMD() + itemTO.getmIPI());
							quoteModel.setmISSMD(quoteModel.getmISSMD() + itemTO.getmISS());
							quoteModel.setmCOFINSMD(quoteModel.getmCOFINSMD() + itemTO.getmCOFINS());
							quoteModel.setmIRMD(quoteModel.getmIRMD() + itemTO.getmIR());
							quoteModel.setmCSSLMD(quoteModel.getmCSSLMD() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableMD(quoteModel.getmICMSRecoverableMD() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesMD(quoteModel.getmTotalTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoMD(quoteModel.getmICMSEstadoDestinoMD() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginMD(quoteModel.getmICMSEstadoOriginMD() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISMD(quoteModel.getmPISMD() + itemTO.getmPIS());
							
						}else{
							quoteModel.setGrossPriceHW(quoteModel.getGrossPriceHW() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceHW(quoteModel.getNetPriceHW() + itemTO.getmNetPrice());
							quoteModel.setTaxesHW(quoteModel.getTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginHW(quoteModel.getNetMarginHW() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostHW(quoteModel.getNetPriceLessCostHW() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialHW(quoteModel.getmICMSMaterialHW() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualHW(quoteModel.getmICMSInterEstadualHW() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoHW(quoteModel.getmICMSinterestadualMatImportadoHW() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTHW(quoteModel.getmICMSSTHW() + itemTO.getmICMSST());
							quoteModel.setmIPIHW(quoteModel.getmIPIHW() + itemTO.getmIPI());
							quoteModel.setmISSHW(quoteModel.getmISSHW() + itemTO.getmISS());
							quoteModel.setmCOFINSHW(quoteModel.getmCOFINSHW() + itemTO.getmCOFINS());
							quoteModel.setmIRHW(quoteModel.getmIRHW() + itemTO.getmIR());
							quoteModel.setmCSSLHW(quoteModel.getmCSSLHW() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableHW(quoteModel.getmICMSRecoverableHW() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesHW(quoteModel.getmTotalTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoHW(quoteModel.getmICMSEstadoDestinoHW() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginHW(quoteModel.getmICMSEstadoOriginHW() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISHW(quoteModel.getmPISHW() + itemTO.getmPIS());
							
						}					
					}*/					
					else{
					
						itemTO = calculationScenario99(itemTO);										
						
						if(itemTO.getiTaxModel() == 5){
							quoteModel.setGrossPriceMD(quoteModel.getGrossPriceMD() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceMD(quoteModel.getNetPriceMD() + itemTO.getmNetPrice());
							quoteModel.setTaxesMD(quoteModel.getTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginMD(quoteModel.getNetMarginMD() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostMD(quoteModel.getNetPriceLessCostMD() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							quoteModel.setmICMSMaterialMD(quoteModel.getmICMSMaterialMD() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualMD(quoteModel.getmICMSInterEstadualMD() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoMD(quoteModel.getmICMSinterestadualMatImportadoMD() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTMD(quoteModel.getmICMSSTMD() + itemTO.getmICMSST());
							quoteModel.setmIPIMD(quoteModel.getmIPIMD() + itemTO.getmIPI());
							quoteModel.setmISSMD(quoteModel.getmISSMD() + itemTO.getmISS());
							quoteModel.setmCOFINSMD(quoteModel.getmCOFINSMD() + itemTO.getmCOFINS());
							quoteModel.setmIRMD(quoteModel.getmIRMD() + itemTO.getmIR());
							quoteModel.setmCSSLMD(quoteModel.getmCSSLMD() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableMD(quoteModel.getmICMSRecoverableMD() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesMD(quoteModel.getmTotalTaxesMD() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoMD(quoteModel.getmICMSEstadoDestinoMD() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginMD(quoteModel.getmICMSEstadoOriginMD() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISMD(quoteModel.getmPISMD() + itemTO.getmPIS());
							
						}else{
							quoteModel.setGrossPriceHW(quoteModel.getGrossPriceHW() +  itemTO.getmGrossPrice());
							quoteModel.setNetPriceHW(quoteModel.getNetPriceHW() + itemTO.getmNetPrice());
							quoteModel.setTaxesHW(quoteModel.getTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setNetMarginHW(quoteModel.getNetMarginHW() + itemTO.getNetMargin());
							quoteModel.setNetPriceLessCostHW(quoteModel.getNetPriceLessCostHW() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
							
							quoteModel.setmICMSMaterialHW(quoteModel.getmICMSMaterialHW() + itemTO.getmICMSMaterialClass());
							quoteModel.setmICMSInterEstadualHW(quoteModel.getmICMSInterEstadualHW() + itemTO.getmICMSInterEstadual());
							quoteModel.setmICMSinterestadualMatImportadoHW(quoteModel.getmICMSinterestadualMatImportadoHW() + itemTO.getmICMSInterEstadualMaterialImportado());
							quoteModel.setmICMSSTHW(quoteModel.getmICMSSTHW() + itemTO.getmICMSST());
							quoteModel.setmIPIHW(quoteModel.getmIPIHW() + itemTO.getmIPI());
							quoteModel.setmISSHW(quoteModel.getmISSHW() + itemTO.getmISS());
							quoteModel.setmCOFINSHW(quoteModel.getmCOFINSHW() + itemTO.getmCOFINS());
							quoteModel.setmIRHW(quoteModel.getmIRHW() + itemTO.getmIR());
							quoteModel.setmCSSLHW(quoteModel.getmCSSLHW() + itemTO.getmCSSL());
							quoteModel.setmICMSRecoverableHW(quoteModel.getmICMSRecoverableHW() + itemTO.getmICMSARecuperar());
							quoteModel.setmTotalTaxesHW(quoteModel.getmTotalTaxesHW() + itemTO.getmTotalTaxes());
							quoteModel.setmICMSEstadoDestinoHW(quoteModel.getmICMSEstadoDestinoHW() + itemTO.getmICMSEstadoDestino());
							quoteModel.setmICMSEstadoOriginHW(quoteModel.getmICMSEstadoOriginHW() + itemTO.getmICMSEstadoOrigem());
							quoteModel.setmPISHW(quoteModel.getmPISHW() + itemTO.getmPIS());
						}
					}
				}								
			}			
		}
		
		double revTotal = 0;
		double netTotal = 0;
		double grossTotal = 0;
		double totalTaxes = 0;
		
		double revSubTotal = 0;
		double netSubTotal = 0;
		double grossSubTotal = 0;
		double totalSubTaxes = 0;
		
		int count = 1;
		
		for(int i = 0; i < quoteModel.getListaQuoteItemTO().size(); i++){
			
			try{
				
				if(quoteModel.getListaQuoteItemTO().get(i).getId() == quoteModel.getListaQuoteItemTO().get(i).getIdTaxQuoteItem()){
					continue;
				}
				
				if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 3){
					
					revTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					revSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalSubTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					SomaTotaisTO somaTotaisTO = new SomaTotaisTO();
					
					somaTotaisTO.setPosition(count);
					somaTotaisTO.setRevenue(revSubTotal);
					somaTotaisTO.setNetTotal(netSubTotal); 
					somaTotaisTO.setGrossTotal(grossSubTotal);
					somaTotaisTO.setTotalTaxes(totalSubTaxes);
					somaTotaisTO.setTexto("SubTotal");
					
					
					quoteModel.getListaQuoteItemTO().get(i).setListaTotais(new ArrayList<SomaTotaisTO>());
					
					quoteModel.getListaQuoteItemTO().get(i).getListaTotais().add(somaTotaisTO);
					
					revSubTotal = 0;
					netSubTotal = 0;
					grossSubTotal = 0;
					totalSubTaxes = 0;
					
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 3 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
					
					revSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalSubTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					revTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					quoteModel.getListaQuoteItemTO().get(i).setListaTotais(new ArrayList<SomaTotaisTO>());
					
					
					for(int s = 0 ; s < 2; s++){
						SomaTotaisTO somaTotaisTO = new SomaTotaisTO();
						
						if(s == 0){
							somaTotaisTO.setPosition(count);
							somaTotaisTO.setRevenue(revSubTotal);
							somaTotaisTO.setNetTotal(netSubTotal);
							somaTotaisTO.setGrossTotal(grossSubTotal);
							somaTotaisTO.setTotalTaxes(totalSubTaxes);
							somaTotaisTO.setTexto("SubTotal");
							
						}else{
							somaTotaisTO.setPosition(count);
							somaTotaisTO.setRevenue(revTotal);
							somaTotaisTO.setNetTotal(netTotal);
							somaTotaisTO.setGrossTotal(grossTotal);
							somaTotaisTO.setTotalTaxes(totalTaxes);
							somaTotaisTO.setTexto("Total");
						}
						
						quoteModel.getListaQuoteItemTO().get(i).getListaTotais().add(somaTotaisTO);
					}
					
					revSubTotal = 0;
					netSubTotal = 0;
					grossSubTotal = 0;
					totalSubTaxes = 0;
					
					revTotal = 0;
					netTotal = 0;
					grossTotal = 0;
					totalTaxes = 0;
					
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
					
					revSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalSubTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					revTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					quoteModel.getListaQuoteItemTO().get(i).setListaTotais(new ArrayList<SomaTotaisTO>());
					
					SomaTotaisTO somaTotaisTO = new SomaTotaisTO();
					
					somaTotaisTO.setPosition(count);
					somaTotaisTO.setRevenue(revTotal);
					somaTotaisTO.setNetTotal(netTotal);
					somaTotaisTO.setGrossTotal(grossTotal);
					somaTotaisTO.setTotalTaxes(totalTaxes);
					somaTotaisTO.setTexto("Total");
						
					quoteModel.getListaQuoteItemTO().get(i).getListaTotais().add(somaTotaisTO);
					
					
					revSubTotal = 0;
					netSubTotal = 0;
					grossSubTotal = 0;
					totalSubTaxes = 0;
					
					revTotal = 0;
					netTotal = 0;
					grossTotal = 0;
					totalTaxes = 0;
					
				}else{
					revTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					revSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalSubTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
				}
				
			}catch(Exception e){
				
				if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 1){
				
					SomaTotaisTO somaTotaisTO = new SomaTotaisTO();
					
					revTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					somaTotaisTO.setPosition(i);
					somaTotaisTO.setRevenue(revTotal);
					somaTotaisTO.setNetTotal(netTotal);
					somaTotaisTO.setGrossTotal(grossTotal);
					somaTotaisTO.setTotalTaxes(totalTaxes);
					somaTotaisTO.setTexto("Total");
					
					quoteModel.getListaQuoteItemTO().get(i).setListaTotais(new ArrayList<SomaTotaisTO>());
					
					quoteModel.getListaQuoteItemTO().get(i).getListaTotais().add(somaTotaisTO);
					
					revSubTotal = 0;
					netSubTotal = 0;
					grossSubTotal = 0;
					totalSubTaxes = 0;
					
					revTotal = 0;
					netTotal = 0;
					grossTotal = 0;
					totalTaxes = 0;
				
				}else{
					
					revSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalSubTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					revTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					quoteModel.getListaQuoteItemTO().get(i).setListaTotais(new ArrayList<SomaTotaisTO>());
					
					
					for(int s = 0 ; s < 2; s++){
						SomaTotaisTO somaTotaisTO = new SomaTotaisTO();
						
						if(s == 0){
							somaTotaisTO.setPosition(count);
							somaTotaisTO.setRevenue(revSubTotal);
							somaTotaisTO.setNetTotal(netSubTotal);
							somaTotaisTO.setGrossTotal(grossSubTotal);
							somaTotaisTO.setTotalTaxes(totalSubTaxes);
							somaTotaisTO.setTexto("SubTotal");
							
						}else{
							somaTotaisTO.setPosition(count);
							somaTotaisTO.setRevenue(revTotal);
							somaTotaisTO.setNetTotal(netTotal);
							somaTotaisTO.setGrossTotal(grossTotal);
							somaTotaisTO.setTotalTaxes(totalTaxes);
							somaTotaisTO.setTexto("Total");
						}
						
						quoteModel.getListaQuoteItemTO().get(i).getListaTotais().add(somaTotaisTO);
					}
					
					revSubTotal = 0;
					netSubTotal = 0;
					grossSubTotal = 0;
					totalSubTaxes = 0;
					
					revTotal = 0;
					netTotal = 0;
					grossTotal = 0;
					totalTaxes = 0;
					
				}
				
			}
			
		}
		
		return quoteModel;
	}
	
	@Override
	public QuoteModel somaCalculoQuoteRelease(QuoteModel quoteModel) {

		zerarTotais(quoteModel);
		
		for(QuoteItemTO itemTO : quoteModel.getListaQuoteItemTO()){
		
		
			if(itemTO.getiTaxModel() == 2){
				
				quoteModel.setGrossPriceSW(quoteModel.getGrossPriceSW() +  itemTO.getmGrossPrice());
				quoteModel.setNetPriceSW(quoteModel.getNetPriceSW() + itemTO.getmNetPrice());
				
				quoteModel.setTaxesSW(quoteModel.getTaxesSW() + itemTO.getmTotalTaxes());
				
				quoteModel.setNetMarginSW(quoteModel.getNetMarginSW() + itemTO.getNetMargin());
				quoteModel.setNetPriceLessCostSW(quoteModel.getNetPriceLessCostSW() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
				
				quoteModel.setmICMSMaterialSW(quoteModel.getmICMSMaterialSW() + itemTO.getmICMSMaterialClass());
				quoteModel.setmICMSInterEstadualSW(quoteModel.getmICMSInterEstadualSW() + itemTO.getmICMSInterEstadual());
				quoteModel.setmICMSinterestadualMatImportadoSW(quoteModel.getmICMSinterestadualMatImportadoSW() + itemTO.getmICMSInterEstadualMaterialImportado());
				quoteModel.setmICMSSTSW(quoteModel.getmICMSSTSW() + itemTO.getmICMSST());
				quoteModel.setmIPISW(quoteModel.getmIPISW() + itemTO.getmIPI());
				quoteModel.setmISSSW(quoteModel.getmISSSW() + itemTO.getmISS());
				quoteModel.setmCOFINSSW(quoteModel.getmCOFINSSW() + itemTO.getmCOFINS());
				quoteModel.setmIRSW(quoteModel.getmIRSW() + itemTO.getmIR());
				quoteModel.setmCSSLSW(quoteModel.getmCSSLSW() + itemTO.getmCSSL());
				quoteModel.setmICMSRecoverableSW(quoteModel.getmICMSRecoverableSW() + itemTO.getmICMSARecuperar());
				quoteModel.setmTotalTaxesSW(quoteModel.getmTotalTaxesSW() + itemTO.getmTotalTaxes());
				quoteModel.setmICMSEstadoDestinoSW(quoteModel.getmICMSEstadoDestinoSW() + itemTO.getmICMSEstadoDestino());
				quoteModel.setmICMSEstadoOriginSW(quoteModel.getmICMSEstadoOriginSW() + itemTO.getmICMSEstadoOrigem());
				quoteModel.setmPISSW(quoteModel.getmPISSW() + itemTO.getmPIS());
				
				
			}else if(itemTO.getiTaxModel() == 3){
								
				quoteModel.setGrossPriceCS(quoteModel.getGrossPriceCS() +  itemTO.getmGrossPrice());
				quoteModel.setNetPriceCS(quoteModel.getNetPriceCS() + itemTO.getmNetPrice());
				
				quoteModel.setTaxesCS(quoteModel.getTaxesCS() + itemTO.getmTotalTaxes());
				quoteModel.setNetMarginCS(quoteModel.getNetMarginCS() + itemTO.getNetMargin());
				quoteModel.setNetPriceLessCostCS(quoteModel.getNetPriceLessCostCS() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
				
				quoteModel.setmICMSMaterialCS(quoteModel.getmICMSMaterialCS() + itemTO.getmICMSMaterialClass());
				quoteModel.setmICMSInterEstadualCS(quoteModel.getmICMSInterEstadualCS() + itemTO.getmICMSInterEstadual());
				quoteModel.setmICMSinterestadualMatImportadoCS(quoteModel.getmICMSinterestadualMatImportadoCS() + itemTO.getmICMSInterEstadualMaterialImportado());
				quoteModel.setmICMSSTCS(quoteModel.getmICMSSTCS() + itemTO.getmICMSST());
				quoteModel.setmIPICS(quoteModel.getmIPICS() + itemTO.getmIPI());
				quoteModel.setmISSCS(quoteModel.getmISSCS() + itemTO.getmISS());
				quoteModel.setmCOFINSCS(quoteModel.getmCOFINSCS() + itemTO.getmCOFINS());
				quoteModel.setmIRCS(quoteModel.getmIRCS() + itemTO.getmIR());
				quoteModel.setmCSSLCS(quoteModel.getmCSSLCS() + itemTO.getmCSSL());
				quoteModel.setmICMSRecoverableCS(quoteModel.getmICMSRecoverableCS() + itemTO.getmICMSARecuperar());
				quoteModel.setmTotalTaxesCS(quoteModel.getmTotalTaxesCS() + itemTO.getmTotalTaxes());
				quoteModel.setmICMSEstadoDestinoCS(quoteModel.getmICMSEstadoDestinoCS() + itemTO.getmICMSEstadoDestino());
				quoteModel.setmICMSEstadoOriginCS(quoteModel.getmICMSEstadoOriginCS() + itemTO.getmICMSEstadoOrigem());
				quoteModel.setmPISCS(quoteModel.getmPISCS() + itemTO.getmPIS());
				
			}else if(itemTO.getiTaxModel() == 5){
				
				quoteModel.setGrossPriceMD(quoteModel.getGrossPriceMD() +  itemTO.getmGrossPrice());
				quoteModel.setNetPriceMD(quoteModel.getNetPriceMD() + itemTO.getmNetPrice());
				quoteModel.setTaxesMD(quoteModel.getTaxesMD() + itemTO.getmTotalTaxes());
				quoteModel.setNetMarginMD(quoteModel.getNetMarginMD() + itemTO.getNetMargin());
				quoteModel.setNetPriceLessCostMD(quoteModel.getNetPriceLessCostMD() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
				quoteModel.setmICMSMaterialMD(quoteModel.getmICMSMaterialMD() + itemTO.getmICMSMaterialClass());
				quoteModel.setmICMSInterEstadualMD(quoteModel.getmICMSInterEstadualMD() + itemTO.getmICMSInterEstadual());
				quoteModel.setmICMSinterestadualMatImportadoMD(quoteModel.getmICMSinterestadualMatImportadoMD() + itemTO.getmICMSInterEstadualMaterialImportado());
				quoteModel.setmICMSSTMD(quoteModel.getmICMSSTMD() + itemTO.getmICMSST());
				quoteModel.setmIPIMD(quoteModel.getmIPIMD() + itemTO.getmIPI());
				quoteModel.setmISSMD(quoteModel.getmISSMD() + itemTO.getmISS());
				quoteModel.setmCOFINSMD(quoteModel.getmCOFINSMD() + itemTO.getmCOFINS());
				quoteModel.setmIRMD(quoteModel.getmIRMD() + itemTO.getmIR());
				quoteModel.setmCSSLMD(quoteModel.getmCSSLMD() + itemTO.getmCSSL());
				quoteModel.setmICMSRecoverableMD(quoteModel.getmICMSRecoverableMD() + itemTO.getmICMSARecuperar());
				quoteModel.setmTotalTaxesMD(quoteModel.getmTotalTaxesMD() + itemTO.getmTotalTaxes());
				quoteModel.setmICMSEstadoDestinoMD(quoteModel.getmICMSEstadoDestinoMD() + itemTO.getmICMSEstadoDestino());
				quoteModel.setmICMSEstadoOriginMD(quoteModel.getmICMSEstadoOriginMD() + itemTO.getmICMSEstadoOrigem());
				quoteModel.setmPISMD(quoteModel.getmPISMD() + itemTO.getmPIS());
				
			}else{
				quoteModel.setGrossPriceHW(quoteModel.getGrossPriceHW() +  itemTO.getmGrossPrice());
				quoteModel.setNetPriceHW(quoteModel.getNetPriceHW() + itemTO.getmNetPrice());
				quoteModel.setTaxesHW(quoteModel.getTaxesHW() + itemTO.getmTotalTaxes());
				quoteModel.setNetMarginHW(quoteModel.getNetMarginHW() + itemTO.getNetMargin());
				quoteModel.setNetPriceLessCostHW(quoteModel.getNetPriceLessCostHW() + (itemTO.getmNetPrice() - itemTO.getmImportedCost()));
				
				quoteModel.setmICMSMaterialHW(quoteModel.getmICMSMaterialHW() + itemTO.getmICMSMaterialClass());
				quoteModel.setmICMSInterEstadualHW(quoteModel.getmICMSInterEstadualHW() + itemTO.getmICMSInterEstadual());
				quoteModel.setmICMSinterestadualMatImportadoHW(quoteModel.getmICMSinterestadualMatImportadoHW() + itemTO.getmICMSInterEstadualMaterialImportado());
				quoteModel.setmICMSSTHW(quoteModel.getmICMSSTHW() + itemTO.getmICMSST());
				quoteModel.setmIPIHW(quoteModel.getmIPIHW() + itemTO.getmIPI());
				quoteModel.setmISSHW(quoteModel.getmISSHW() + itemTO.getmISS());
				quoteModel.setmCOFINSHW(quoteModel.getmCOFINSHW() + itemTO.getmCOFINS());
				quoteModel.setmIRHW(quoteModel.getmIRHW() + itemTO.getmIR());
				quoteModel.setmCSSLHW(quoteModel.getmCSSLHW() + itemTO.getmCSSL());
				quoteModel.setmICMSRecoverableHW(quoteModel.getmICMSRecoverableHW() + itemTO.getmICMSARecuperar());
				quoteModel.setmTotalTaxesHW(quoteModel.getmTotalTaxesHW() + itemTO.getmTotalTaxes());
				quoteModel.setmICMSEstadoDestinoHW(quoteModel.getmICMSEstadoDestinoHW() + itemTO.getmICMSEstadoDestino());
				quoteModel.setmICMSEstadoOriginHW(quoteModel.getmICMSEstadoOriginHW() + itemTO.getmICMSEstadoOrigem());
				quoteModel.setmPISHW(quoteModel.getmPISHW() + itemTO.getmPIS());
			}
		}
		
		double revTotal = 0;
		double netTotal = 0;
		double grossTotal = 0;
		double totalTaxes = 0;
		
		double revSubTotal = 0;
		double netSubTotal = 0;
		double grossSubTotal = 0;
		double totalSubTaxes = 0;
		
		int count = 1;
		
		for(int i = 0; i < quoteModel.getListaQuoteItemTO().size(); i++){
			
			try{
				
				if(quoteModel.getListaQuoteItemTO().get(i).getId() == quoteModel.getListaQuoteItemTO().get(i).getIdTaxQuoteItem()){
					continue;
				}
				
				if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 3){
					
					revTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					revSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalSubTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					SomaTotaisTO somaTotaisTO = new SomaTotaisTO();
					
					somaTotaisTO.setPosition(count);
					somaTotaisTO.setRevenue(revSubTotal);
					somaTotaisTO.setNetTotal(netSubTotal); 
					somaTotaisTO.setGrossTotal(grossSubTotal);
					somaTotaisTO.setTotalTaxes(totalSubTaxes);
					somaTotaisTO.setTexto("SubTotal");
					
					
					quoteModel.getListaQuoteItemTO().get(i).setListaTotais(new ArrayList<SomaTotaisTO>());
					
					quoteModel.getListaQuoteItemTO().get(i).getListaTotais().add(somaTotaisTO);
					
					revSubTotal = 0;
					netSubTotal = 0;
					grossSubTotal = 0;
					totalSubTaxes = 0;
					
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 3 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
					
					revSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalSubTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					revTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					quoteModel.getListaQuoteItemTO().get(i).setListaTotais(new ArrayList<SomaTotaisTO>());
					
					
					for(int s = 0 ; s < 2; s++){
						SomaTotaisTO somaTotaisTO = new SomaTotaisTO();
						
						if(s == 0){
							somaTotaisTO.setPosition(count);
							somaTotaisTO.setRevenue(revSubTotal);
							somaTotaisTO.setNetTotal(netSubTotal);
							somaTotaisTO.setGrossTotal(grossSubTotal);
							somaTotaisTO.setTotalTaxes(totalSubTaxes);
							somaTotaisTO.setTexto("SubTotal");
							
						}else{
							somaTotaisTO.setPosition(count);
							somaTotaisTO.setRevenue(revTotal);
							somaTotaisTO.setNetTotal(netTotal);
							somaTotaisTO.setGrossTotal(grossTotal);
							somaTotaisTO.setTotalTaxes(totalTaxes);
							somaTotaisTO.setTexto("Total");
						}
						
						quoteModel.getListaQuoteItemTO().get(i).getListaTotais().add(somaTotaisTO);
					}
					
					revSubTotal = 0;
					netSubTotal = 0;
					grossSubTotal = 0;
					totalSubTaxes = 0;
					
					revTotal = 0;
					netTotal = 0;
					grossTotal = 0;
					totalTaxes = 0;
					
				}else if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 2 && quoteModel.getListaQuoteItemTO().get(i+1).getiFlagTypeLine() == 1){
					
					revSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalSubTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					revTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					quoteModel.getListaQuoteItemTO().get(i).setListaTotais(new ArrayList<SomaTotaisTO>());
					
					SomaTotaisTO somaTotaisTO = new SomaTotaisTO();
					
					somaTotaisTO.setPosition(count);
					somaTotaisTO.setRevenue(revTotal);
					somaTotaisTO.setNetTotal(netTotal);
					somaTotaisTO.setGrossTotal(grossTotal);
					somaTotaisTO.setTotalTaxes(totalTaxes);
					somaTotaisTO.setTexto("Total");
						
					quoteModel.getListaQuoteItemTO().get(i).getListaTotais().add(somaTotaisTO);
					
					
					revSubTotal = 0;
					netSubTotal = 0;
					grossSubTotal = 0;
					totalSubTaxes = 0;
					
					revTotal = 0;
					netTotal = 0;
					grossTotal = 0;
					totalTaxes = 0;
					
				}else{
					revTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					revSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalSubTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
				}
				
			}catch(Exception e){
				
				if(quoteModel.getListaQuoteItemTO().get(i).getiFlagTypeLine() == 1){
				
					SomaTotaisTO somaTotaisTO = new SomaTotaisTO();
					
					revTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					somaTotaisTO.setPosition(i);
					somaTotaisTO.setRevenue(revTotal);
					somaTotaisTO.setNetTotal(netTotal);
					somaTotaisTO.setGrossTotal(grossTotal);
					somaTotaisTO.setTotalTaxes(totalTaxes);
					somaTotaisTO.setTexto("Total");
					
					quoteModel.getListaQuoteItemTO().get(i).setListaTotais(new ArrayList<SomaTotaisTO>());
					
					quoteModel.getListaQuoteItemTO().get(i).getListaTotais().add(somaTotaisTO);
					
					revSubTotal = 0;
					netSubTotal = 0;
					grossSubTotal = 0;
					totalSubTaxes = 0;
					
					revTotal = 0;
					netTotal = 0;
					grossTotal = 0;
					totalTaxes = 0;
				
				}else{
					
					revSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossSubTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalSubTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					revTotal += quoteModel.getListaQuoteItemTO().get(i).getmImportedCost();
					netTotal += quoteModel.getListaQuoteItemTO().get(i).getmNetPrice();
					grossTotal += quoteModel.getListaQuoteItemTO().get(i).getmGrossPrice();
					totalTaxes += quoteModel.getListaQuoteItemTO().get(i).getmTotalTaxes();
					
					quoteModel.getListaQuoteItemTO().get(i).setListaTotais(new ArrayList<SomaTotaisTO>());
					
					
					for(int s = 0 ; s < 2; s++){
						SomaTotaisTO somaTotaisTO = new SomaTotaisTO();
						
						if(s == 0){
							somaTotaisTO.setPosition(count);
							somaTotaisTO.setRevenue(revSubTotal);
							somaTotaisTO.setNetTotal(netSubTotal);
							somaTotaisTO.setGrossTotal(grossSubTotal);
							somaTotaisTO.setTotalTaxes(totalSubTaxes);
							somaTotaisTO.setTexto("SubTotal");
							
						}else{
							somaTotaisTO.setPosition(count);
							somaTotaisTO.setRevenue(revTotal);
							somaTotaisTO.setNetTotal(netTotal);
							somaTotaisTO.setGrossTotal(grossTotal);
							somaTotaisTO.setTotalTaxes(totalTaxes);
							somaTotaisTO.setTexto("Total");
						}
						
						quoteModel.getListaQuoteItemTO().get(i).getListaTotais().add(somaTotaisTO);
					}
					
					revSubTotal = 0;
					netSubTotal = 0;
					grossSubTotal = 0;
					totalSubTaxes = 0;
					
					revTotal = 0;
					netTotal = 0;
					grossTotal = 0;
					totalTaxes = 0;
					
				}
				
			}
			
		}
		
		return quoteModel;		
	}
	
	
	public void zerarTotais(QuoteModel quoteModel){
		quoteModel.setrGrossPrice(0);
		quoteModel.setrNetPrice(0);
		quoteModel.setGrossPriceCS(0);
		quoteModel.setNetPriceCS(0);
		quoteModel.setGrossPriceHW(0);
		quoteModel.setNetPriceHW(0);
		quoteModel.setGrossPriceSW(0);
		quoteModel.setNetPriceSW(0);
		quoteModel.setGrossPriceMD(0);
		quoteModel.setNetPriceMD(0);
		quoteModel.setGrossPricePS(0);
		quoteModel.setNetPricePS(0);
		quoteModel.setTaxesSW(0);
		quoteModel.setTaxesCS(0);
		quoteModel.setTaxesHW(0);
		quoteModel.setTaxesMD(0);
		quoteModel.setTaxesPS(0);
		quoteModel.setNetMarginHW(0);
		quoteModel.setNetMarginSW(0);
		quoteModel.setNetMarginPS(0);
		quoteModel.setNetMarginCS(0);
		quoteModel.setNetMarginMD(0);
		quoteModel.setNetPriceLessCostHW(0);
		quoteModel.setNetPriceLessCostSW(0);
		quoteModel.setNetPriceLessCostPS(0);
		quoteModel.setNetPriceLessCostCS(0);
		quoteModel.setNetPriceLessCostMD(0);
		
		quoteModel.setmICMSMaterialMD(0);
		quoteModel.setmICMSInterEstadualMD(0);
		quoteModel.setmICMSinterestadualMatImportadoMD(0);
		quoteModel.setmICMSSTMD(0);
		quoteModel.setmIPIMD(0);
		quoteModel.setmISSMD(0);
		quoteModel.setmCOFINSMD(0);
		quoteModel.setmIRMD(0);
		quoteModel.setmCSSLMD(0);
		quoteModel.setmICMSRecoverableMD(0);
		quoteModel.setmICMSEstadoDestinoMD(0);
		quoteModel.setmICMSEstadoOriginMD(0);
		quoteModel.setmTotalTaxesMD(0);
		quoteModel.setmPISMD(0);
		
		quoteModel.setmICMSMaterialHW(0);
		quoteModel.setmICMSInterEstadualHW(0);
		quoteModel.setmICMSinterestadualMatImportadoHW(0);
		quoteModel.setmICMSSTHW(0);
		quoteModel.setmIPIHW(0);
		quoteModel.setmISSHW(0);
		quoteModel.setmCOFINSHW(0);
		quoteModel.setmIRHW(0);
		quoteModel.setmCSSLHW(0);
		quoteModel.setmICMSRecoverableHW(0);
		quoteModel.setmICMSEstadoDestinoHW(0);
		quoteModel.setmICMSEstadoOriginHW(0);
		quoteModel.setmTotalTaxesHW(0);
		quoteModel.setmPISHW(0);
		
		quoteModel.setmICMSMaterialSW(0);
		quoteModel.setmICMSInterEstadualSW(0);
		quoteModel.setmICMSinterestadualMatImportadoSW(0);
		quoteModel.setmICMSSTSW(0);
		quoteModel.setmIPISW(0);
		quoteModel.setmISSSW(0);
		quoteModel.setmCOFINSSW(0);
		quoteModel.setmIRSW(0);
		quoteModel.setmCSSLSW(0);
		quoteModel.setmICMSRecoverableSW(0);
		quoteModel.setmICMSEstadoDestinoSW(0);
		quoteModel.setmICMSEstadoOriginSW(0);
		quoteModel.setmTotalTaxesSW(0);
		quoteModel.setmPISSW(0);
		
		quoteModel.setmICMSMaterialPS(0);
		quoteModel.setmICMSInterEstadualPS(0);
		quoteModel.setmICMSinterestadualMatImportadoPS(0);
		quoteModel.setmICMSSTPS(0);
		quoteModel.setmIPIPS(0);
		quoteModel.setmISSPS(0);
		quoteModel.setmCOFINSPS(0);
		quoteModel.setmIRPS(0);
		quoteModel.setmCSSLPS(0);
		quoteModel.setmICMSRecoverablePS(0);
		quoteModel.setmICMSEstadoDestinoPS(0);
		quoteModel.setmICMSEstadoOriginPS(0);
		quoteModel.setmTotalTaxesPS(0);
		quoteModel.setmPISPS(0);
		
		quoteModel.setmICMSMaterialCS(0);
		quoteModel.setmICMSInterEstadualCS(0);
		quoteModel.setmICMSinterestadualMatImportadoCS(0);
		quoteModel.setmICMSSTCS(0);
		quoteModel.setmIPICS(0);
		quoteModel.setmISSCS(0);
		quoteModel.setmCOFINSCS(0);
		quoteModel.setmIRCS(0);
		quoteModel.setmCSSLCS(0);
		quoteModel.setmICMSRecoverableCS(0);
		quoteModel.setmICMSEstadoDestinoCS(0);
		quoteModel.setmICMSEstadoOriginCS(0);
		quoteModel.setmTotalTaxesCS(0);
		quoteModel.setmPISCS(0);
	}
	
	@Override
	public void salvarAtributos(QuoteModel quoteModel) {
		
		appQuoteDAO.salvarAtributos(quoteModel);
		
	}
	
	
	@Override
	public QuoteModel goalSeek(QuoteModel quoteModel, String typeValue) {
		
		double dblDiscountUpperBound;
		
		double dblLimitAux;
		
						
		dblDiscountUpperBound = 1;
		
		if(quoteModel.isGsTotal()){
			for(int i = 0; i < quoteModel.getListaQuoteItemTO().size(); i++){
				if(quoteModel.getListaQuoteItemTO().get(i).isChecked()){
					dblLimitAux = 1 - ( (quoteModel.getListaQuoteItemTO().get(i).getrDiscount() / 100) + quoteModel.getListaQuoteItemTO().get(i).getrDiscClassDiscount());
		
					if(dblLimitAux < dblDiscountUpperBound){
						
						dblDiscountUpperBound = dblLimitAux;
					}
				}
			}
			
			double dblTargetValue = typeValue.equals("netPrice") ? quoteModel.getGsTotalNet() : quoteModel.getGsTotalGross();
			
			prepararCalculoGS(quoteModel, typeValue, "Total", dblDiscountUpperBound, 0, dblTargetValue);
			
		}else{
			if(quoteModel.isGsHW()){
				for(int i = 0; i < quoteModel.getListaQuoteItemTO().size(); i++){
					if(quoteModel.getListaQuoteItemTO().get(i).isChecked() && quoteModel.getListaQuoteItemTO().get(i).getiTaxModel() == 1){
						dblLimitAux = 1 - ( (quoteModel.getListaQuoteItemTO().get(i).getrDiscount() / 100) + quoteModel.getListaQuoteItemTO().get(i).getrDiscClassDiscount());
			
						if(dblLimitAux < dblDiscountUpperBound){
							
							dblDiscountUpperBound = dblLimitAux;
						}
					}
				}
				
				double dblTargetValue = typeValue.equals("netPrice") ? quoteModel.getGsNetPriceHW() : quoteModel.getGsGrossPriceHW();
				
				prepararCalculoGS(quoteModel, typeValue, "HW", dblDiscountUpperBound, 1, dblTargetValue);
			}
			
			if(quoteModel.isGsSW()){
				for(int i = 0; i < quoteModel.getListaQuoteItemTO().size(); i++){
					if(quoteModel.getListaQuoteItemTO().get(i).isChecked() && quoteModel.getListaQuoteItemTO().get(i).getiTaxModel() == 2){
						dblLimitAux = 1 - ( (quoteModel.getListaQuoteItemTO().get(i).getrDiscount() / 100) + quoteModel.getListaQuoteItemTO().get(i).getrDiscClassDiscount());
			
						if(dblLimitAux < dblDiscountUpperBound){
							
							dblDiscountUpperBound = dblLimitAux;
						}
					}
				}
				
				double dblTargetValue = typeValue.equals("netPrice") ? quoteModel.getGsNetPriceSW() : quoteModel.getGsGrossPriceSW();
				
				prepararCalculoGS(quoteModel, typeValue, "SW", dblDiscountUpperBound, 2, dblTargetValue);
			}
			
			if(quoteModel.isGsCS()){
				for(int i = 0; i < quoteModel.getListaQuoteItemTO().size(); i++){
					if(quoteModel.getListaQuoteItemTO().get(i).isChecked() && quoteModel.getListaQuoteItemTO().get(i).getiTaxModel() == 3){
						dblLimitAux = 1 - ( (quoteModel.getListaQuoteItemTO().get(i).getrDiscount() / 100) + quoteModel.getListaQuoteItemTO().get(i).getrDiscClassDiscount());
			
						if(dblLimitAux < dblDiscountUpperBound){
							
							dblDiscountUpperBound = dblLimitAux;
						}
					}
				}
				
				double dblTargetValue = typeValue.equals("netPrice") ? quoteModel.getGsNetPriceCS() : quoteModel.getGsGrossPriceCS();
				
				prepararCalculoGS(quoteModel, typeValue, "SV", dblDiscountUpperBound, 3, dblTargetValue);
			}
			
			if(quoteModel.isGsMD()){
				for(int i = 0; i < quoteModel.getListaQuoteItemTO().size(); i++){
					if(quoteModel.getListaQuoteItemTO().get(i).isChecked() && quoteModel.getListaQuoteItemTO().get(i).getiTaxModel() == 5){
						dblLimitAux = 1 - ( (quoteModel.getListaQuoteItemTO().get(i).getrDiscount() / 100) + quoteModel.getListaQuoteItemTO().get(i).getrDiscClassDiscount());
			
						if(dblLimitAux < dblDiscountUpperBound){
							
							dblDiscountUpperBound = dblLimitAux;
						}
					}
				}
				
				double dblTargetValue = typeValue.equals("netPrice") ? quoteModel.getGsNetPriceMD() : quoteModel.getGsGrossPriceMD();
				
				prepararCalculoGS(quoteModel, typeValue, "MD", dblDiscountUpperBound, 5, dblTargetValue);
			}
		}
		
		
		
		return quoteModel;
	}
	
	
	public void prepararCalculoGS(QuoteModel quoteModel, String typeValue, String itemType, double dblDiscountUpperBound, int iTaxModel, double dblTargetValue){
		

		final Double TAX_DISCOUNT_LOWERBOUND = (Math.pow(10, 10)) * -1;
		
		double dblDiscountLowerBound = TAX_DISCOUNT_LOWERBOUND.longValue();
		
		double dblRoundedDiscount = 0;
		double dblDiscount = 0;
		double dblRealizado;
		
						
		while(dblDiscountUpperBound > TAX_DISCOUNT_LOWERBOUND){
					
			//Limite Inferior
			dblRealizado = calcularRealizado(quoteModel, dblDiscountLowerBound, itemType, typeValue, iTaxModel);
			
			if(dblTargetValue == dblRealizado){
				dblRoundedDiscount = dblDiscount;
				break;
			}
			
			if(dblTargetValue > dblRealizado){
				dblRoundedDiscount = 0;
				break;
			}
						
			//Limite Superior						
			dblRealizado = calcularRealizado(quoteModel, dblDiscountUpperBound, itemType, typeValue, iTaxModel);
			
			if(dblTargetValue == dblRealizado){
				break;
			}
			
			if(dblTargetValue < dblRealizado){
				dblDiscount = dblDiscountUpperBound;
				break;
			}
			
			dblDiscount = ((dblDiscountUpperBound - dblDiscountLowerBound) / 2) + dblDiscountLowerBound;
			int i = 90; //número de casas de precisão
			
			while(dblTargetValue != dblRealizado && i > 0){ //Logica de precisão
				
				i--;
				dblRealizado = calcularRealizado(quoteModel, dblDiscount, itemType, typeValue, iTaxModel);
				
				if(dblTargetValue < dblRealizado){
					/* Aumenta o desconto */
					dblDiscountLowerBound = dblDiscount;
					dblDiscount = ((dblDiscountUpperBound - dblDiscountLowerBound) / 2) + dblDiscountLowerBound;
					
				}else if(dblTargetValue > dblRealizado){
					/* Diminui o desconto */
					dblDiscountUpperBound = dblDiscount;
					dblDiscount = ((dblDiscountUpperBound - dblDiscountLowerBound) / 2) + dblDiscountLowerBound;
					
				}else{
					break;
				}
			}
			
			break;
		}
		
		// Aplica descontos arrendondados encontrados aos itens
		dblRoundedDiscount = dblDiscount;
		dblRealizado = calcularRealizado(quoteModel, dblRoundedDiscount, itemType, typeValue, iTaxModel);
		
		if(dblTargetValue != dblRealizado){
			
		}
		
	}
	
		
	public double calcularRealizado(QuoteModel quoteModel, double dblDiscount, String typeItem, String typeValue, int iTaxModel){
		
		double retorno = 0;
		
		for(int i = 0; i < quoteModel.getListaQuoteItemTO().size(); i++){
			if(quoteModel.getListaQuoteItemTO().get(i).isChecked() && (quoteModel.getListaQuoteItemTO().get(i).getiTaxModel() == iTaxModel || iTaxModel == 0)){
				quoteModel.getListaQuoteItemTO().get(i).setrDiscount((float) (dblDiscount * 100));
			}
		}
		
		quoteModel = CalcularQuote(quoteModel);
		
		if(typeItem.equals("Total")){
			if(typeValue.equals("netPrice")){
				retorno = quoteModel.getrNetPrice();
			}else{
				retorno = quoteModel.getrGrossPrice();
			}
		}else if(typeItem.equals("HW")){
			if(typeValue.equals("netPrice")){
				retorno = quoteModel.getNetPriceHW();
			}else{
				retorno = quoteModel.getGrossPriceHW();
			}
		}else if(typeItem.equals("SW")){
			if(typeValue.equals("netPrice")){
				retorno = quoteModel.getNetPriceSW();
			}else{
				retorno = quoteModel.getGrossPriceSW();
			}
		}else if(typeItem.equals("SV")){
			if(typeValue.equals("netPrice")){
				retorno = quoteModel.getNetPriceCS();
			}else{
				retorno = quoteModel.getGrossPriceCS();
			}
		}else if(typeItem.equals("MD")){
			if(typeValue.equals("netPrice")){
				retorno = quoteModel.getNetPriceMD();
			}else{
				retorno = quoteModel.getGrossPriceMD();
			}
		}
		
		
		return retorno;
	}
	
	
	/*public double deixarDuasCasasDecimais(double valor){
		
		DecimalFormat df = new DecimalFormat("0.##");
		
		return Double.parseDouble(df.format(valor));
	}
	
	public double deixarQuatroCasasDecimais(double valor){
		
		DecimalFormat df = new DecimalFormat("0.####");
		
		return Double.parseDouble(df.format(valor));
	}*/
	
	/**
	 * Recupera as maiores Alíquotas para calculo de materiais não classificados 
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 */
	@Override
	public QuoteModel biggestRateRecover(QuoteModel quoteModel) throws NumberFormatException, SQLException {

		quoteModel = appQuoteDAO.selectRate(quoteModel);
		
		quoteModel = appQuoteDAO.selectProductType(quoteModel);
		
		quoteModel = appQuoteDAO.selectMaterialClass(quoteModel);
		
		quoteModel = appQuoteDAO.selectProductType(quoteModel);
		
		quoteModel = appQuoteDAO.selectISS(quoteModel);
		
		quoteModel = appQuoteDAO.selectDefaultTaxes(quoteModel);
		
		quoteModel = appQuoteDAO.selectICMSIPI(quoteModel);
				
		
		try {

			String sqlQueryUpdateMaterialClassAndProductType = "MERGE INTO tbTaxQuoteItem qi "
					+ "USING (select qi.id, m.id AS idAdmMaterial, "
					+ "mc.id AS idAdmMaterialClass, pt.id AS idAdmProductType"
					+ " from tbTaxQuoteItem qi"
					+ " INNER JOIN tbAdmMaterial m"
					+ " ON qi.idAdmMaterial = m.id"
					+ " INNER JOIN tbAdmMaterialClass mc"
					+ " ON  "
					+ " CASE WHEN qi.idAdmMaterialClassOriginal is null"
					+ " THEN m.idAdmMaterialClass "
					+ " ELSE qi.idAdmMaterialClass "
					+ " END = mc.id"
					+ " INNER JOIN tbAdmProductType pt"
					+ " ON mc.idAdmProductType = pt.id"
					+ " where idTaxQuote = "+quoteModel.getIdTaxQuote()+") t "
					+ "ON (t.id = qi.id) "
					+ "WHEN MATCHED THEN UPDATE SET idAdmMaterialClass = t.idAdmMaterialClass, idAdmProductType = t.idAdmProductType";
			
			appQuoteDAO.updateQuery(sqlQueryUpdateMaterialClassAndProductType);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
				
		return quoteModel;
	}

	
	/**
	 * Cenário de cálculo para itens tipo Software
	 */
	@Override
	public QuoteItemTO calculationScenarioSW(QuoteItemTO quoteItemTO) {
		
		quoteItemTO.setiCalculationModel(0);
		
		quoteItemTO.setmICMSMaterialClass(0);
		
		StringBuilder calculation = new StringBuilder();
		
		BigDecimal accert = new BigDecimal(0);
		
		if(quoteItemTO.getIdTaxQuoteItem() == quoteItemTO.getId()){
			calculation.append("| Item Pai |\n id = "+quoteItemTO.getId()+" - idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n");
		}else if(quoteItemTO.getIdTaxQuoteItem() > 0){
			calculation.append("| Item Filho |\n idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n | idItemPai = "+quoteItemTO.getIdTaxQuoteItem()+" - idAdmMaterialClassPAi = "+quoteItemTO.getIdAdmMaterialClassPai()+" - sNcmMaterialClassPai = "+quoteItemTO.getsNCMMaterialClassPai()+"\n");
		}
		
		Float targetNetPrice = (float) (quoteItemTO.getmImportedCost() / (1 - quoteItemTO.getrSuggestedMargin()));
		accert = new BigDecimal(targetNetPrice).setScale(4, RoundingMode.HALF_EVEN); // usado para manter a precisao de 4 casas e o arredondamento correto;
		targetNetPrice = accert.floatValue();
		
		calculation.append(" | TargetNetPrice = "+ targetNetPrice +" => Cost:"+quoteItemTO.getmImportedCost()+" / (1 - SuggestedMargin:"+quoteItemTO.getrSuggestedMargin()+"\n");
		
		Float discount = targetNetPrice * (quoteItemTO.getrDiscount() / 100);
		accert = new BigDecimal(discount).setScale(4, RoundingMode.HALF_EVEN);
		discount = accert.floatValue();
		
		calculation.append(" | Discount = "+ discount +" => TargetNetPrice:"+targetNetPrice+" * (rDiscount:"+quoteItemTO.getrDiscount()+" / 100)\n");
		
		Float netPrice = targetNetPrice - discount;
		accert = new BigDecimal(netPrice).setScale(4, RoundingMode.HALF_EVEN);
		netPrice = accert.floatValue();
		quoteItemTO.setmNetPrice(accert.doubleValue());
		
		calculation.append(" | netPrice = "+netPrice+" => targetNetPrice:"+targetNetPrice+" - discount:"+discount+"\n");
		
		Float netMargin = (float) (netPrice - quoteItemTO.getmImportedCost());
		accert = new BigDecimal(netMargin).setScale(4, RoundingMode.HALF_EVEN);
		netMargin = accert.floatValue();
		quoteItemTO.setNetMargin(accert.floatValue() / 100);

		calculation.append(" | netMargin = "+netMargin+" => netPrice:"+netPrice+" - Cost:"+quoteItemTO.getmImportedCost()+"\n");
		
		Float factor = ( 1 - (quoteItemTO.getrPISProductType() + quoteItemTO.getrISSSoftOrigin() + quoteItemTO.getrCOFINSProductType() )); // * 100;
		accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
		factor = accert.floatValue();
		
		if(factor < 0){
			factor = ((quoteItemTO.getrPISProductType() + quoteItemTO.getrISSSoftOrigin() + quoteItemTO.getrCOFINSProductType()) - 1);
			accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
			factor = accert.floatValue();
			calculation.append(" | factor = "+factor+" => ((PIS:"+quoteItemTO.getrPISProductType()+" + ISS:"+quoteItemTO.getrISSSoftOrigin()+" + COFINS:"+quoteItemTO.getrCOFINSProductType()+") - 1)\n");
		}else{
			calculation.append(" | factor = "+factor+" => (1 - (PIS:"+quoteItemTO.getrPISProductType()+" + ISS:"+quoteItemTO.getrISSSoftOrigin()+" + COFINS:"+quoteItemTO.getrCOFINSProductType()+"))\n");
		}
		
		quoteItemTO.setrFatorSaida(factor);
		
		
		
		Float PIS = (netPrice / factor) * quoteItemTO.getrPISProductType();
		accert = new BigDecimal(PIS).setScale(4, RoundingMode.HALF_EVEN);
		PIS = accert.floatValue();
		quoteItemTO.setmPIS(accert.doubleValue());
		
		calculation.append(" | PIS = "+PIS+" => (netPrice:"+netPrice+" / factor:"+factor+") * PIS:"+quoteItemTO.getrPISProductType()+"\n");
		
		Float COFINS = (netPrice / factor) * quoteItemTO.getrCOFINSProductType();
		accert = new BigDecimal(COFINS).setScale(4, RoundingMode.HALF_EVEN);
		COFINS = accert.floatValue();
		quoteItemTO.setmCOFINS(accert.doubleValue());
		
		calculation.append(" | COFINS = "+COFINS+" => (netPrice:"+netPrice+" / factor:"+factor+") * COFINS:"+quoteItemTO.getrCOFINSProductType()+"\n");
		
		Float ISS = (netPrice / factor) * quoteItemTO.getrISSSoftOrigin();
		accert = new BigDecimal(ISS).setScale(4, RoundingMode.HALF_EVEN);
		ISS = accert.floatValue();
		quoteItemTO.setmISS(accert.doubleValue());
		
		calculation.append(" | ISS = "+ISS+" => (netPrice:"+netPrice+" / factor:"+factor+") * ISS:"+quoteItemTO.getrISSSoftOrigin()+"\n");
		
		Float TotalTaxes = PIS + COFINS + ISS;
		accert = new BigDecimal(TotalTaxes).setScale(4, RoundingMode.HALF_EVEN);
		TotalTaxes = accert.floatValue();
		quoteItemTO.setmTotalTaxes(accert.doubleValue());
		
		calculation.append(" | TotalTaxes = "+TotalTaxes+" => PIS:"+PIS+" + COFINS:"+COFINS+" + ISS:"+ISS+"\n");
		
		quoteItemTO.setmGrossPrice(new BigDecimal(netPrice + TotalTaxes).setScale(4, RoundingMode.HALF_EVEN).doubleValue());
		
		calculation.append(" | GrossPrice = "+(netPrice + TotalTaxes)+" => netPrice:"+netPrice+" + TotalTaxes:"+TotalTaxes+"\n");
		
		quoteItemTO.setrGrossMargin(new BigDecimal(quoteItemTO.getmGrossPrice() - quoteItemTO.getmImportedCost()).setScale(4, RoundingMode.HALF_EVEN).doubleValue());
		
		calculation.append(" | GrossMargin = "+(quoteItemTO.getmGrossPrice() - quoteItemTO.getmImportedCost())+" => GrossPrice:"+quoteItemTO.getmGrossPrice()+" - Cost:"+quoteItemTO.getmImportedCost()+"\n");
		
		
		quoteItemTO.setsCalculationMemoryMaterial(calculation.toString());
		
		
		return quoteItemTO;
	}

	
	/**
	 * Cenário de cálculo para itens tipo Serviço
	 */
	@Override
	public QuoteItemTO calculationScenarioSV(QuoteItemTO quoteItemTO) {
		
//		//MOCK
//		quoteItemTO.setmCostMaterial(100.0);
//		quoteItemTO.setmSuggestedMargin(0.0);
//		quoteItemTO.setrPIS(0.0065F);
//		quoteItemTO.setrCOFINS(0.03F);
//		quoteItemTO.setrISS(0.05F);		
//		//
		
		StringBuilder calculation = new StringBuilder();
		
		quoteItemTO.setiCalculationModel(0);
		
		quoteItemTO.setmICMSMaterialClass(0);
		
		if(quoteItemTO.getIdTaxQuoteItem() == quoteItemTO.getId()){
			calculation.append("| Item Pai |\n id = "+quoteItemTO.getId()+" - idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n");
		}else if(quoteItemTO.getIdTaxQuoteItem() > 0){
			calculation.append("| Item Filho |\n idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n | idItemPai = "+quoteItemTO.getIdTaxQuoteItem()+" - idAdmMaterialClassPAi = "+quoteItemTO.getIdAdmMaterialClassPai()+" - sNcmMaterialClassPai = "+quoteItemTO.getsNCMMaterialClassPai()+"\n");
		}
		
		BigDecimal accert = new BigDecimal(0);
		
		Float targetNetPrice = (float) (quoteItemTO.getmImportedCost() / (1 - quoteItemTO.getrSuggestedMargin()));
		accert = new BigDecimal(targetNetPrice).setScale(4, RoundingMode.HALF_EVEN);
		targetNetPrice = accert.floatValue();
		
		calculation.append(" | TargetNetPrice = "+ targetNetPrice +" => Cost:"+quoteItemTO.getmImportedCost()+" / (1 - SuggestedMargin:"+quoteItemTO.getrSuggestedMargin()+"\n");
		
		Float discount = targetNetPrice * (quoteItemTO.getrDiscount() / 100);
		accert = new BigDecimal(discount).setScale(4, RoundingMode.HALF_EVEN);
		discount = accert.floatValue();
		
		calculation.append(" | Discount = "+ discount +" => TargetNetPrice:"+targetNetPrice+" * (rDiscount:"+quoteItemTO.getrDiscount()+" / 100)\n");
		
		Float netPrice = targetNetPrice - discount;
		accert = new BigDecimal(netPrice).setScale(4, RoundingMode.HALF_EVEN);
		netPrice = accert.floatValue();
		quoteItemTO.setmNetPrice(accert.doubleValue());
		
		calculation.append(" | netPrice = "+netPrice+" => targetNetPrice:"+targetNetPrice+" - discount:"+discount+"\n");
		
		Float netMargin = (float) (netPrice - quoteItemTO.getmImportedCost());
		accert = new BigDecimal(netMargin).setScale(4, RoundingMode.HALF_EVEN);
		netMargin = accert.floatValue();
		quoteItemTO.setNetMargin(accert.floatValue() / 100);
		
		calculation.append(" | netMargin = "+netMargin+" => netPrice:"+netPrice+" - Cost:"+quoteItemTO.getmImportedCost()+"\n");
		
		Float factor = ( 1 - (quoteItemTO.getrPISProductType() + quoteItemTO.getrISSOriginServ() + quoteItemTO.getrCOFINSProductType())); // * 100;
		accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
		factor = accert.floatValue();
		
		if(factor < 0){
			factor = ((quoteItemTO.getrPISProductType() + quoteItemTO.getrISSOriginServ() + quoteItemTO.getrCOFINSProductType()) - 1);
			accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
			factor = accert.floatValue();
			calculation.append(" | factor = "+factor+" => ((PIS:"+quoteItemTO.getrPISProductType()+" + ISS:"+quoteItemTO.getrISSOriginServ()+" + COFINS:"+quoteItemTO.getrCOFINSProductType()+") - 1)\n");
		}else{
			calculation.append(" | factor = "+factor+" => (1 - (PIS:"+quoteItemTO.getrPISProductType()+" + ISS:"+quoteItemTO.getrISSOriginServ()+" + COFINS:"+quoteItemTO.getrCOFINSProductType()+"))\n");
		}
		
		quoteItemTO.setrFatorSaida(factor);
				
		Float PIS = (netPrice / factor) * quoteItemTO.getrPISProductType();
		accert = new BigDecimal(PIS).setScale(4, RoundingMode.HALF_EVEN);
		PIS = accert.floatValue();
		quoteItemTO.setmPIS(accert.doubleValue());
		
		calculation.append(" | PIS = "+PIS+" => (netPrice:"+netPrice+" / factor:"+factor+") * PIS:"+quoteItemTO.getrPISProductType()+"\n");
		
		Float COFINS = (netPrice / factor) * quoteItemTO.getrCOFINSProductType();
		accert = new BigDecimal(COFINS).setScale(4, RoundingMode.HALF_EVEN);
		COFINS = accert.floatValue();
		quoteItemTO.setmCOFINS(accert.doubleValue());
		
		calculation.append(" | COFINS = "+COFINS+" => (netPrice:"+netPrice+" / factor:"+factor+") * COFINS:"+quoteItemTO.getrCOFINSProductType()+"\n");
		
		Float ISS = (netPrice / factor) * quoteItemTO.getrISSOriginServ();
		accert = new BigDecimal(ISS).setScale(4, RoundingMode.HALF_EVEN);
		ISS = accert.floatValue();
		quoteItemTO.setmISS(accert.doubleValue());
		
		calculation.append(" | ISS = "+ISS+" => (netPrice:"+netPrice+" / factor:"+factor+") * ISS:"+ quoteItemTO.getrISSOriginServ()+"\n");
		
		Float TotalTaxes = PIS + COFINS + ISS;
		accert = new BigDecimal(TotalTaxes).setScale(4, RoundingMode.HALF_EVEN);
		TotalTaxes = accert.floatValue();
		quoteItemTO.setmTotalTaxes(accert.doubleValue());
		
		calculation.append(" | TotalTaxes = "+TotalTaxes+" => PIS:"+PIS+" + COFINS:"+COFINS+" + ISS:"+ISS+"\n");
		
		quoteItemTO.setmGrossPrice(new BigDecimal(netPrice + TotalTaxes).setScale(4, RoundingMode.HALF_EVEN).doubleValue());
		
		calculation.append(" | GrossPrice = "+(netPrice + TotalTaxes)+" => netPrice:"+netPrice+" + TotalTaxes"+TotalTaxes+"\n");
		
		quoteItemTO.setrGrossMargin(new BigDecimal(quoteItemTO.getmGrossPrice() - quoteItemTO.getmImportedCost()).setScale(4, RoundingMode.HALF_EVEN).doubleValue());
		
		calculation.append(" | GrossMargin = "+(quoteItemTO.getmGrossPrice() - quoteItemTO.getmImportedCost())+" => GrossPrice:"+quoteItemTO.getmGrossPrice()+" - Cost:"+quoteItemTO.getmImportedCost()+"\n");
		
		
		quoteItemTO.setsCalculationMemoryMaterial(calculation.toString());
		
		
		return quoteItemTO;
	}
	
	@Override
	public QuoteItemTO calculationScenario1(QuoteItemTO quoteItemTO) {
		
		//MOCK
//		quoteItemTO.setmCostMaterial(100.0);
//		quoteItemTO.setmSuggestedMargin(0.0);
//		quoteItemTO.setrPIS(0.0165F);
//		quoteItemTO.setrCOFINS(0.076F);
//		quoteItemTO.setrICMSEstadoDestino(0.12F);
//		quoteItemTO.setrIPI(0.15F);
//		quoteItemTO.setrICMSInterEstadual(0.04F);
//		quoteItemTO.setrIVAMaterialClassDestination(0.0F); // IVA - ST
//		quoteItemTO.setrIVAMaterialImportadoMaterialClassDestination(0.34F); // IVA - Ajustado
		//
		
		StringBuilder calculation = new StringBuilder();
		
		quoteItemTO.setiCalculationModel(1);
		
		quoteItemTO.setmICMSMaterialClass(0);
		
		if(quoteItemTO.getIdTaxQuoteItem() == quoteItemTO.getId()){
			calculation.append("| Item Pai |\n id = "+quoteItemTO.getId()+" - idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n");
		}else if(quoteItemTO.getIdTaxQuoteItem() > 0){
			calculation.append("| Item Filho |\n idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n | idItemPai = "+quoteItemTO.getIdTaxQuoteItem()+" - idAdmMaterialClassPAi = "+quoteItemTO.getIdAdmMaterialClassPai()+" - sNcmMaterialClassPai = "+quoteItemTO.getsNCMMaterialClassPai()+"\n");
		}
		
		BigDecimal accert = new BigDecimal(0);
		
		Float discount = (float) (quoteItemTO.getmImportedCost() * (quoteItemTO.getrDiscount() / 100));
		accert = new BigDecimal(discount).setScale(4, RoundingMode.HALF_EVEN);
		discount = accert.floatValue();
		calculation.append("Discount = "+discount+" =>  Cost:"+quoteItemTO.getmImportedCost()+" * (Discount:"+quoteItemTO.getrDiscount()+" / 100) \n");
		
		Float netPrice = (float) (quoteItemTO.getmImportedCost() - discount);
		accert = new BigDecimal(netPrice).setScale(4, RoundingMode.HALF_EVEN);
		netPrice = accert.floatValue();
		quoteItemTO.setmNetPrice(accert.doubleValue());
		
		calculation.append(" | NetPrice = "+netPrice+" => Cost:"+quoteItemTO.getmImportedCost()+" - Discount:"+discount+"\n");
		
		Float factor = 0F;
		if(quoteItemTO.getfMercadoriaImportada() == 0){
			factor = 1- ((quoteItemTO.getrICMSInterEstadualDestination() * quoteItemTO.getrIPIMaterialClass()) + quoteItemTO.getrICMSInterEstadualDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType());
			accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
			factor = accert.floatValue();
			
			if(factor < 0){
				factor = ((quoteItemTO.getrICMSInterEstadualDestination() * quoteItemTO.getrIPIMaterialClass()) + quoteItemTO.getrICMSInterEstadualDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType()) -1;
				accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
				factor = accert.floatValue();
				calculation.append(" | Factor = "+factor+" => ((ICMSInterEstadual:"+quoteItemTO.getrICMSInterEstadualDestination()+" * rIPI:"+quoteItemTO.getrIPIMaterialClass()+") + ICMSInterEstadual:"+quoteItemTO.getrICMSInterEstadualDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + COFINS:"+quoteItemTO.getrCOFINSProductType()+") - 1\n");
			}else{
				calculation.append(" | Factor = "+factor+" => 1 - ((ICMSInterEstadual:"+quoteItemTO.getrICMSInterEstadualDestination()+" * rIPI:"+quoteItemTO.getrIPIMaterialClass()+") + ICMSInterEstadual:"+quoteItemTO.getrICMSInterEstadualDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + COFINS:"+quoteItemTO.getrCOFINSProductType()+"\n");
			}
			
		}else{
			factor = 1- ((quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination() * quoteItemTO.getrIPIMaterialClass()) + quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType());
			accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
			factor = accert.floatValue();
			
			if(factor < 0){
				factor = ((quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination() * quoteItemTO.getrIPIMaterialClass()) + quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType()) - 1;
				accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
				factor = accert.floatValue();
				
				calculation.append(" | Factor = "+factor+" => ((ICMSInterEstadualMaterialImportado:"+quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination()+" * rIPI:"+quoteItemTO.getrIPIMaterialClass()+") + ICMSInterEstadualMaterialImportado:"+quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + COFINS:"+quoteItemTO.getrCOFINSProductType()+") - 1\n");
			}else{
				calculation.append(" | Factor = "+factor+" => 1 - ((ICMSInterEstadualMaterialImportado:"+quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination()+" * rIPI:"+quoteItemTO.getrIPIMaterialClass()+") + ICMSInterEstadualMaterialImportado:"+quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + COFINS:"+quoteItemTO.getrCOFINSProductType()+"\n");
			}
		}
		
		quoteItemTO.setrFatorSaida(factor);
		
		Float IPI = (netPrice / factor) * quoteItemTO.getrIPIMaterialClass();
		accert = new BigDecimal(IPI).setScale(4, RoundingMode.HALF_EVEN);
		IPI = accert.floatValue();
		quoteItemTO.setmIPI(accert.doubleValue());
		
		calculation.append(" | IPI = "+IPI+" => (netPrice:"+netPrice+" / Factor:"+factor+") * rIPI:"+ quoteItemTO.getrIPIMaterialClass()+"\n");
		
		Float ICMSEstadoDestino = (netPrice / factor) * quoteItemTO.getrICMSEstadoDestinoDestination();
		accert = new BigDecimal(ICMSEstadoDestino).setScale(4, RoundingMode.HALF_EVEN);
		ICMSEstadoDestino = accert.floatValue();
		quoteItemTO.setmICMSEstadoDestino(accert.doubleValue());
		
		calculation.append(" | ICMSEstadoDestino = "+ICMSEstadoDestino+" => (netPrice:"+netPrice+" / Factor:"+factor+") * rICMSEstadoDestino:"+ quoteItemTO.getrICMSEstadoDestinoDestination()+"\n");
		
		Float ICMSInterEstadual = 0F;
		if(quoteItemTO.getfMercadoriaImportada() == 0){
			ICMSInterEstadual = ((netPrice / factor) + IPI) * quoteItemTO.getrICMSInterEstadualDestination();
			accert = new BigDecimal(ICMSInterEstadual).setScale(4, RoundingMode.HALF_EVEN);
			ICMSInterEstadual = accert.floatValue();
			quoteItemTO.setmICMSInterEstadual(accert.doubleValue());
			
			calculation.append(" | ICMSInterEstadual = "+ICMSInterEstadual+" => ((netPrice:"+netPrice+" / Factor:"+factor+") + IPI:"+IPI+") * ICMSInterEstadual:"+ quoteItemTO.getrICMSInterEstadualDestination() +"\n");
			
		}else{
			ICMSInterEstadual = ((netPrice / factor) + IPI) * quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination();
			accert = new BigDecimal(ICMSInterEstadual).setScale(4, RoundingMode.HALF_EVEN);
			ICMSInterEstadual = accert.floatValue();
			quoteItemTO.setmICMSInterEstadualMaterialImportado(accert.doubleValue());
			
			calculation.append(" | ICMSInterEstadual = "+ICMSInterEstadual+" => ((netPrice:"+netPrice+" / Factor:"+factor+") + IPI:"+IPI+") * ICMSInterEstadualMaterialImportado:"+ quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination() +"\n");
		}
		
		Float PIS = (netPrice / factor) * quoteItemTO.getrPISProductType();
		accert = new BigDecimal(PIS).setScale(4, RoundingMode.HALF_EVEN);
		PIS = accert.floatValue();
		quoteItemTO.setmPIS(accert.doubleValue());
		
		calculation.append(" | PIS = "+PIS+" => ((netPrice:"+netPrice+" / Factor:"+factor+") + IPI:"+IPI+") * rPIS:"+ quoteItemTO.getrPISProductType() +"\n");
		
		Float COFINS = (netPrice / factor) * quoteItemTO.getrCOFINSProductType();
		accert = new BigDecimal(COFINS).setScale(4, RoundingMode.HALF_EVEN);
		COFINS = accert.floatValue();
		quoteItemTO.setmCOFINS(accert.doubleValue());
		
		calculation.append(" | COFINS = "+COFINS+" => ((netPrice:"+netPrice+" / Factor:"+factor+") + IPI:"+IPI+") * COFINS:"+ quoteItemTO.getrCOFINSProductType() +"\n");
		
		Float TotalTaxes = IPI + ICMSInterEstadual + PIS + COFINS;
		accert = new BigDecimal(TotalTaxes).setScale(4, RoundingMode.HALF_EVEN);
		TotalTaxes = accert.floatValue();
		quoteItemTO.setmTotalTaxes(accert.doubleValue());
		
		calculation.append(" | TotalTaxes = "+TotalTaxes+" => IPI:"+IPI+" + ICMSInterEstadual:"+ICMSInterEstadual+" + PIS:"+PIS+" + COFINS:"+ COFINS +"\n");
		
		Float baseICMSST = 0F;
		if(quoteItemTO.getfMercadoriaImportada() == 0){
			baseICMSST = (netPrice + TotalTaxes); //* (1 + quoteItemTO.getrIVAMaterialClassDestination());
			accert = new BigDecimal(baseICMSST).setScale(4, RoundingMode.HALF_EVEN);
			baseICMSST = accert.floatValue();
			quoteItemTO.setmBaseCalculoST(accert.doubleValue());
			calculation.append(" | baseICMSST = "+baseICMSST+" => (NetPrice:"+netPrice+" + TotalTaxes:"+TotalTaxes+")\n");
		}else{
			baseICMSST = (netPrice + TotalTaxes) ;//* (1 + quoteItemTO.getrIVAMaterialImportadoMaterialClassDestination());
			accert = new BigDecimal(baseICMSST).setScale(4, RoundingMode.HALF_EVEN);
			baseICMSST = accert.floatValue();
			quoteItemTO.setmBaseCalculoST(accert.doubleValue());
			calculation.append(" | baseICMSST = "+baseICMSST+" => (NetPrice:"+netPrice+" + TotalTaxes:"+TotalTaxes+")\n");
		}
		
		Float ICMSST = ( baseICMSST * quoteItemTO.getrICMSEstadoDestinoDestination()) - ICMSInterEstadual;
		accert = new BigDecimal(ICMSST).setScale(4, RoundingMode.HALF_EVEN);
		ICMSST = accert.floatValue();
		quoteItemTO.setmICMSST(accert.doubleValue());
		
		calculation.append(" | ICMSST = "+ICMSST+" => (baseICMSST:"+baseICMSST+" * ICMSEstadoDestino:"+quoteItemTO.getrICMSEstadoDestinoDestination()+") - ICMSInterEstadual:"+ICMSInterEstadual+"\n");
		
		Double grossPriceSt = new BigDecimal(netPrice + TotalTaxes + ICMSST).setScale(4, RoundingMode.HALF_EVEN).doubleValue();
		quoteItemTO.setmGrossPrice(grossPriceSt);
		
		calculation.append(" | grossPriceSt = "+grossPriceSt+" => (NetPrice:"+netPrice+" + TotalTaxes:"+TotalTaxes+" + ICMSST:"+ICMSST+"\n");
		
		quoteItemTO.setsCalculationMemoryMaterial(calculation.toString());
		
		return quoteItemTO;
	}


	@Override
	public QuoteItemTO calculationScenario4(QuoteItemTO quoteItemTO) {
		
		//MOCK
//		quoteItemTO.setmCostMaterial(100.0);
//		quoteItemTO.setmSuggestedMargin(0.0);
//		quoteItemTO.setrPIS(0.0165F);
//		quoteItemTO.setrCOFINS(0.076F);
//		quoteItemTO.setrIPI(0.15F);
//		quoteItemTO.setrICMSInterEstadual(0.04F);
		//
		
		quoteItemTO.setiCalculationModel(4);
		
		quoteItemTO.setmICMSMaterialClass(0);
		
		StringBuilder calculation = new StringBuilder();
		
		if(quoteItemTO.getIdTaxQuoteItem() == quoteItemTO.getId()){
			calculation.append("| Item Pai |\n id = "+quoteItemTO.getId()+" - idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n");
		}else if(quoteItemTO.getIdTaxQuoteItem() > 0){
			calculation.append("| Item Filho |\n idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n | idItemPai = "+quoteItemTO.getIdTaxQuoteItem()+" - idAdmMaterialClassPAi = "+quoteItemTO.getIdAdmMaterialClassPai()+" - sNcmMaterialClassPai = "+quoteItemTO.getsNCMMaterialClassPai()+"\n");
		}
		
		BigDecimal accert = new BigDecimal(0);
		
		Float discount = (float) (quoteItemTO.getmImportedCost() * (quoteItemTO.getrDiscount() / 100));
		accert = new BigDecimal(discount).setScale(4, RoundingMode.HALF_EVEN);
		discount = accert.floatValue();
		
		calculation.append("Discount = "+discount+" => Cost:"+quoteItemTO.getmImportedCost()+" * (rDicount:"+quoteItemTO.getrDiscount()+" / 100) \n");
		
		Float netPrice = (float) (quoteItemTO.getmImportedCost() - discount);
		accert = new BigDecimal(netPrice).setScale(4, RoundingMode.HALF_EVEN);
		netPrice = accert.floatValue();
		quoteItemTO.setmNetPrice(accert.doubleValue());
		
		calculation.append(" | NetPrice = "+ netPrice+" => Cost:"+ quoteItemTO.getmImportedCost()+" - Discount:"+discount+"\n");
		
		Float factor = 0F;
		if(quoteItemTO.getfMercadoriaImportada() == 0){
			factor = 1- (quoteItemTO.getrICMSInterEstadualDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType());
			accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
			factor = accert.floatValue();
			
			if(factor < 0){
				factor = (quoteItemTO.getrICMSInterEstadualDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType()) - 1;
				accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
				factor = accert.floatValue();
				
				calculation.append(" | Factor = "+factor+" => (rICMSInterEstadual:"+quoteItemTO.getrICMSInterEstadualDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+") - 1\n");
			}else{
				calculation.append(" | Factor = "+factor+" => 1 - (rICMSInterEstadual:"+quoteItemTO.getrICMSInterEstadualDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+")\n");
			}
			
		}else{
			factor = 1- (quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType());
			accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
			factor = accert.floatValue();
			
			if(factor < 0){
				factor = (quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType()) - 1;
				accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
				factor = accert.floatValue();
				
				calculation.append(" | Factor = "+factor+" => (rICMSInterEstadualMaterialImportado:"+quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+") - 1\n");
			}else{
				calculation.append(" | Factor = "+factor+" => 1 - (rICMSInterEstadualMaterialImportado:"+quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+")\n");
			}
		}
		quoteItemTO.setrFatorSaida(factor);
		
		Float factorValue = netPrice / factor;
		
		calculation.append(" | FactorValue = "+ factorValue +" => netPrice:"+netPrice+" / factor:"+factor+"\n");
		
		Float IPI = (netPrice / factor) * quoteItemTO.getrIPIMaterialClass();
		accert = new BigDecimal(IPI).setScale(4, RoundingMode.HALF_EVEN);
		IPI = accert.floatValue();
		quoteItemTO.setmIPI(accert.doubleValue());
		
		calculation.append(" | IPI = "+IPI+" => (netPrice:"+netPrice+" / factor:"+factor+") * rIPI:"+quoteItemTO.getrIPIMaterialClass()+"\n");
		
		Float ICMSInterEstadual = 0F;
		if(quoteItemTO.getfMercadoriaImportada() == 0){
			ICMSInterEstadual = (netPrice / factor) * quoteItemTO.getrICMSInterEstadualDestination();
			accert = new BigDecimal(ICMSInterEstadual).setScale(4, RoundingMode.HALF_EVEN);
			ICMSInterEstadual = accert.floatValue();
			quoteItemTO.setmICMSInterEstadual(accert.doubleValue());
			
			calculation.append(" | ICMSInterEstadual = "+ICMSInterEstadual+" => (netPrice:"+netPrice+" / factor:"+factor+") * rICMSInterEstadual:"+quoteItemTO.getrICMSInterEstadualDestination()+"\n");
			
		}else{
			ICMSInterEstadual = (netPrice / factor) * quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination();
			accert = new BigDecimal(ICMSInterEstadual).setScale(4, RoundingMode.HALF_EVEN);
			ICMSInterEstadual = accert.floatValue();
			quoteItemTO.setmICMSInterEstadualMaterialImportado(accert.doubleValue());
			
			calculation.append(" | ICMSInterEstadualMaterialImportado = "+ICMSInterEstadual+" => (netPrice:"+netPrice+" / factor:"+factor+") * rICMSInterEstadualMaterialImportado:"+quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination()+"\n");
			
		}
		
		Float PIS = (netPrice / factor) * quoteItemTO.getrPISProductType();
		accert = new BigDecimal(PIS).setScale(4, RoundingMode.HALF_EVEN);
		PIS = accert.floatValue();
		quoteItemTO.setmPIS(accert.doubleValue());
		
		calculation.append(" | PIS = "+PIS+" => (netPrice:"+netPrice+" / factor:"+factor+") * rPIS:"+quoteItemTO.getrPISProductType()+"\n");
		
		Float COFINS = (netPrice / factor) * quoteItemTO.getrCOFINSProductType();
		accert = new BigDecimal(COFINS).setScale(4, RoundingMode.HALF_EVEN);
		COFINS = accert.floatValue();
		quoteItemTO.setmCOFINS(accert.doubleValue());
		
		calculation.append(" | COFINS = "+COFINS+" => (netPrice:"+netPrice+" / factor:"+factor+") * rCOFINS:"+quoteItemTO.getrCOFINSProductType()+"\n");
		
		Float totalTaxes = IPI + ICMSInterEstadual + PIS + COFINS;
		accert = new BigDecimal(totalTaxes).setScale(4, RoundingMode.HALF_EVEN);
		totalTaxes = accert.floatValue();
		quoteItemTO.setmTotalTaxes(accert.doubleValue());
		
		calculation.append(" | TotalTaxes = "+totalTaxes+" => IPI:"+IPI+" + ICMSInterEStadual:"+ICMSInterEstadual+" + PIS:"+PIS+" + COFINS:"+COFINS+"\n");
		
		quoteItemTO.setmGrossPrice(new BigDecimal(netPrice + totalTaxes).setScale(4, RoundingMode.HALF_EVEN).doubleValue());
		
		calculation.append(" | GrossPrice = "+(netPrice + totalTaxes)+" => netPrice:"+netPrice+" + totalTaxes:"+totalTaxes+"\n");
		
		quoteItemTO.setsCalculationMemoryMaterial(calculation.toString());
		
		return quoteItemTO;
	}


	@Override
	public QuoteItemTO calculationScenario6(QuoteItemTO quoteItemTO) {
		
		//MOCK
//		quoteItemTO.setmCostMaterial(100.0);
//		quoteItemTO.setmSuggestedMargin(0.0);
//		quoteItemTO.setrPIS(0.0165F);
//		quoteItemTO.setrCOFINS(0.076F);
//		quoteItemTO.setrIPI(0.15F);
//		quoteItemTO.setrICMSEstadoDestino(0.12F);
		//
		
		quoteItemTO.setiCalculationModel(6);
		
		quoteItemTO.setmICMSMaterialClass(0);
		
		StringBuilder calculation = new StringBuilder();
		
		if(quoteItemTO.getIdTaxQuoteItem() == quoteItemTO.getId()){
			calculation.append("| Item Pai |\n id = "+quoteItemTO.getId()+" - idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n");
		}else if(quoteItemTO.getIdTaxQuoteItem() > 0){
			calculation.append("| Item Filho |\n idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n | idItemPai = "+quoteItemTO.getIdTaxQuoteItem()+" - idAdmMaterialClassPAi = "+quoteItemTO.getIdAdmMaterialClassPai()+" - sNcmMaterialClassPai = "+quoteItemTO.getsNCMMaterialClassPai()+"\n");
		}
		
		BigDecimal accert = new BigDecimal(0);
		
		Float discount = (float) (quoteItemTO.getmImportedCost() * (quoteItemTO.getrDiscount() / 100));
		accert = new BigDecimal(discount).setScale(4, RoundingMode.HALF_EVEN);
		discount = accert.floatValue();
		
		calculation.append("Discount = "+discount+" => Cost:"+quoteItemTO.getmImportedCost()+" * (rDicount:"+quoteItemTO.getrDiscount()+" / 100) \n");
		
		Float netPrice = (float) (quoteItemTO.getmImportedCost() - discount);
		accert = new BigDecimal(netPrice).setScale(4, RoundingMode.HALF_EVEN);
		netPrice = accert.floatValue();
		quoteItemTO.setmNetPrice(accert.doubleValue());
		
		calculation.append(" | NetPrice = "+ netPrice+" => Cost:"+ quoteItemTO.getmImportedCost()+" - Discount:"+discount+"\n");
		
		Float factor = 1 - ((quoteItemTO.getrICMSEstadoDestinoDestination() * quoteItemTO.getrIPIMaterialClass()) + quoteItemTO.getrICMSEstadoDestinoDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType());
		accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
		factor = accert.floatValue();
		
		
		if(factor < 0){
			factor = ((quoteItemTO.getrICMSEstadoDestinoDestination() * quoteItemTO.getrIPIMaterialClass()) + quoteItemTO.getrICMSEstadoDestinoDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType()) - 1;
			accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
			factor = accert.floatValue();
			
			calculation.append(" | Factor = "+factor+" => ((rICMSEstadoDestino:"+ quoteItemTO.getrICMSEstadoDestinoDestination()+" * rIPI:"+quoteItemTO.getrIPIMaterialClass()+") + rICMSEstadoDestino:"+quoteItemTO.getrICMSEstadoDestinoDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+
					" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+") - 1\n");
		}else{
			calculation.append(" | Factor = "+factor+" => 1 - ((rICMSEstadoDestino:"+ quoteItemTO.getrICMSEstadoDestinoDestination()+" * rIPI:"+quoteItemTO.getrIPIMaterialClass()+") + rICMSEstadoDestino:"+quoteItemTO.getrICMSEstadoDestinoDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+
					" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+")\n");
		}
		
		quoteItemTO.setrFatorSaida(factor);
		
		
		Float factorValue = netPrice / factor;
		
		calculation.append(" | FactorValue = "+factorValue+" => netPrice:"+netPrice+" / factor:"+factor+"\n");
		
		Float IPI = (netPrice / factor) * quoteItemTO.getrIPIMaterialClass();
		accert = new BigDecimal(IPI).setScale(4, RoundingMode.HALF_EVEN);
		IPI = accert.floatValue();
		quoteItemTO.setmIPI(accert.doubleValue());
		
		calculation.append(" | IPI = "+IPI+" => (netPrice:"+ netPrice +" / factor:"+factor+") * rIPI:"+quoteItemTO.getrIPIMaterialClass());
		
		Float ICMSEstadualDestino = ((netPrice / factor) + IPI) * quoteItemTO.getrICMSEstadoDestinoDestination();
		accert = new BigDecimal(ICMSEstadualDestino).setScale(4, RoundingMode.HALF_EVEN);
		ICMSEstadualDestino = accert.floatValue();
		quoteItemTO.setmICMSEstadoDestino(accert.doubleValue());
		
		calculation.append(" | ICMSEstadualDestino = "+ICMSEstadualDestino+" => ((netPrice:"+netPrice+" / factor:"+factor+") + IPI:"+IPI+") * rICMSEstadoDestino:"+quoteItemTO.getrICMSEstadoDestinoDestination()+"\n");
		
		Float PIS = (netPrice / factor) * quoteItemTO.getrPISProductType();
		accert = new BigDecimal(PIS).setScale(4, RoundingMode.HALF_EVEN);
		PIS = accert.floatValue();
		quoteItemTO.setmPIS(accert.doubleValue());
		
		calculation.append(" | PIS = "+PIS+" => (netPrice:"+netPrice+" / factor:"+factor+") * rPIS:"+quoteItemTO.getrPISProductType()+"\n");
		
		Float COFINS = (netPrice / factor) * quoteItemTO.getrCOFINSProductType();
		accert = new BigDecimal(COFINS).setScale(4, RoundingMode.HALF_EVEN);
		COFINS = accert.floatValue();
		quoteItemTO.setmCOFINS(accert.doubleValue());
		
		calculation.append(" | COFINS = "+COFINS+" => (netPrice:"+netPrice+" / factor:"+factor+") * rCOFINS:"+quoteItemTO.getrCOFINSProductType()+"\n");
		
		Float totalTaxes = IPI + ICMSEstadualDestino + PIS + COFINS;
		accert = new BigDecimal(totalTaxes).setScale(4, RoundingMode.HALF_EVEN);
		totalTaxes = accert.floatValue();
		quoteItemTO.setmTotalTaxes(accert.doubleValue());
		
		calculation.append(" | TotalTAxes = "+totalTaxes+" => IPI:"+IPI+" + ICMSEstadualDestino:"+ICMSEstadualDestino+" + PIS:"+ PIS +" + COFINS:"+COFINS+"\n");
		
		quoteItemTO.setmGrossPrice(new BigDecimal(netPrice + totalTaxes).setScale(4, RoundingMode.HALF_EVEN).doubleValue());
		
		calculation.append(" | GrossPrice = "+(netPrice + totalTaxes)+" => netPrice:"+netPrice+" + TotalTaxes:"+totalTaxes+"\n");
		
		quoteItemTO.setsCalculationMemoryMaterial(calculation.toString());
		
		return quoteItemTO;
	}

	@Override
	public QuoteItemTO calculationScenario7(QuoteItemTO quoteItemTO) {
		
		//MOCK
//		quoteItemTO.setmCostMaterial(100.0);
//		quoteItemTO.setmSuggestedMargin(0.0);
//		quoteItemTO.setrIPI(0.15F);
//		quoteItemTO.setrICMSMaterialClass(0.07F);
//		quoteItemTO.setrPIS(0.0165F);
//		quoteItemTO.setrCOFINS(0.076F);		
		//
		
		quoteItemTO.setiCalculationModel(7);
		
		quoteItemTO.setmICMSMaterialClass(0);
		
		StringBuilder calculation = new StringBuilder();
		
		if(quoteItemTO.getIdTaxQuoteItem() == quoteItemTO.getId()){
			calculation.append("| Item Pai |\n id = "+quoteItemTO.getId()+" - idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n");
		}else if(quoteItemTO.getIdTaxQuoteItem() > 0){
			calculation.append("| Item Filho |\n idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n | idItemPai = "+quoteItemTO.getIdTaxQuoteItem()+" - idAdmMaterialClassPAi = "+quoteItemTO.getIdAdmMaterialClassPai()+" - sNcmMaterialClassPai = "+quoteItemTO.getsNCMMaterialClassPai()+"\n");
		}
		
		BigDecimal accert = new BigDecimal(0);
		
		Float discount = (float) (quoteItemTO.getmImportedCost() * (quoteItemTO.getrDiscount() / 100));
		accert = new BigDecimal(discount).setScale(4, RoundingMode.HALF_EVEN);
		discount = accert.floatValue();
		
		calculation.append("Discount = "+discount+" => Cost:"+quoteItemTO.getmImportedCost()+" * (rDicount:"+quoteItemTO.getrDiscount()+" / 100) \n");
		
		Float netPrice = (float) (quoteItemTO.getmImportedCost() - discount);
		accert = new BigDecimal(netPrice).setScale(4, RoundingMode.HALF_EVEN);
		netPrice = accert.floatValue();
		quoteItemTO.setmNetPrice(accert.doubleValue());
		
		calculation.append(" | NetPrice = "+ netPrice+" => Cost:"+ quoteItemTO.getmImportedCost()+" - Discount:"+discount+"\n");
		
		// OLD: Float factor = 1 - (quoteItemTO.getrICMSMaterialClassMaterialClass() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType());
		Float factor = 1 - ((quoteItemTO.getrICMSMaterialClassMaterialClass() * quoteItemTO.getrIPIMaterialClass()) + quoteItemTO.getrICMSMaterialClassMaterialClass()
							+ quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType());
		
		accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
		factor = accert.floatValue();
		
		if(factor < 0){
			factor = ((quoteItemTO.getrICMSMaterialClassMaterialClass() * quoteItemTO.getrIPIMaterialClass()) + quoteItemTO.getrICMSMaterialClassMaterialClass()
					+ quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType()) - 1;
			accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
			factor = accert.floatValue();
			
			calculation.append(" | Factor = "+factor+" => (rICMSMaterialClass:"+quoteItemTO.getrICMSMaterialClassMaterialClass()+" * IPI:"+quoteItemTO.getrIPIMaterialClass()+") + rICMSMaterialClass:"+quoteItemTO.getrICMSMaterialClassMaterialClass()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+") - 1\n");
		}else{
			calculation.append(" | Factor = "+factor+" => 1 - (rICMSMaterialClass:"+quoteItemTO.getrICMSMaterialClassMaterialClass()+" * IPI:"+quoteItemTO.getrIPIMaterialClass()+") + rICMSMaterialClass:"+quoteItemTO.getrICMSMaterialClassMaterialClass()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+")\n");
		}
		
		quoteItemTO.setrFatorSaida(factor);
		
		
		
		Float factorValue = netPrice / factor;
		
		calculation.append(" | FactorValue = "+factorValue+" => netPrice:"+netPrice+" / factor:"+factor+"\n");
		
		Float IPI = (netPrice / factor) * quoteItemTO.getrIPIMaterialClass();
		accert = new BigDecimal(IPI).setScale(4, RoundingMode.HALF_EVEN);
		IPI = accert.floatValue();
		quoteItemTO.setmIPI(accert.doubleValue());
		
		calculation.append(" | IPI = "+IPI+" => (netPrice:"+ netPrice +" / factor:"+factor+") * rIPI:"+quoteItemTO.getrIPIMaterialClass()+"\n");
		
		Float ICMSMaterialClass = ((netPrice / factor) + IPI) * quoteItemTO.getrICMSMaterialClassMaterialClass();
		accert = new BigDecimal(ICMSMaterialClass).setScale(4, RoundingMode.HALF_EVEN);
		ICMSMaterialClass = accert.floatValue();
		quoteItemTO.setmICMSMaterialClass(accert.doubleValue());
		
		calculation.append(" | ICMSMaterialClass = "+ICMSMaterialClass+" => ((netPrice:"+ netPrice +" / factor:"+factor+") + IPI:"+IPI+") * rICMSMaterialClass:"+quoteItemTO.getrICMSMaterialClassMaterialClass()+"\n");
		
		/*Float ICMSMaterialClassInterno = (netPrice / factor) * quoteItemTO.getrICMSMaterialClassMaterialClass();
		accert = new BigDecimal(ICMSMaterialClassInterno).setScale(4, RoundingMode.HALF_EVEN);
		ICMSMaterialClassInterno = accert.floatValue();
		
		calculation.append(" | ICMSMaterialClassInterno = "+ICMSMaterialClassInterno+" => (netPrice:"+ netPrice +" / factor:"+factor+") * rICMSMaterialClass:"+ quoteItemTO.getrICMSMaterialClassMaterialClass()+"\n");*/
		
		Float PIS = (netPrice / factor) * quoteItemTO.getrPISProductType();
		accert = new BigDecimal(PIS).setScale(4, RoundingMode.HALF_EVEN);
		PIS = accert.floatValue();
		quoteItemTO.setmPIS(accert.doubleValue());
		
		calculation.append(" | PIS = "+PIS+" => (netPrice:"+netPrice+" / factor:"+factor+") * rPIS:"+quoteItemTO.getrPISProductType()+"\n");
		
		Float COFINS = (netPrice / factor) * quoteItemTO.getrCOFINSProductType();
		accert = new BigDecimal(COFINS).setScale(4, RoundingMode.HALF_EVEN);
		COFINS = accert.floatValue();
		quoteItemTO.setmCOFINS(accert.doubleValue());
		
		calculation.append(" | COFINS = "+COFINS+" => (netPrice:"+netPrice+" / factor:"+factor+") * rCOFINS:"+quoteItemTO.getrCOFINSProductType()+"\n");
		
		Float totalTaxes = IPI + ICMSMaterialClass + PIS + COFINS;
		accert = new BigDecimal(totalTaxes).setScale(4, RoundingMode.HALF_EVEN);
		totalTaxes = accert.floatValue();
		quoteItemTO.setmTotalTaxes(accert.doubleValue());
		
		calculation.append(" | TotalTAxes = "+totalTaxes+" => IPI:"+IPI+" + ICMSMaterialClass:"+ICMSMaterialClass+" + PIS:"+ PIS +" + COFINS:"+COFINS+"\n");
		
		quoteItemTO.setmGrossPrice(new BigDecimal(netPrice + totalTaxes).setScale(4, RoundingMode.HALF_EVEN).doubleValue());
		
		calculation.append(" | GrossPrice = "+(netPrice + totalTaxes)+" => netPrice:"+netPrice+" + TotalTaxes:"+totalTaxes+"\n");
		
		quoteItemTO.setsCalculationMemoryMaterial(calculation.toString());
		
		return quoteItemTO;
	}

	@Override
	public QuoteItemTO calculationScenario9(QuoteItemTO quoteItemTO) {
		
		//MOCK
//		quoteItemTO.setmCostMaterial(100.0);
//		quoteItemTO.setmSuggestedMargin(0.0);
//		quoteItemTO.setrIVAMaterialClassDestination(0.0F); // IVA - ST
//		quoteItemTO.setrIVAMaterialImportadoMaterialClassDestination(0.4054F); // IVA - Ajustado
//		quoteItemTO.setrICMSEstadoDestino(0.18F);
//		quoteItemTO.setrICMSInterEstadual(0.12F);
//		quoteItemTO.setrPIS(0.0165F);
//		quoteItemTO.setrIPI(0.0F);
//		quoteItemTO.setrCOFINS(0.076F);		
		//
		
		quoteItemTO.setiCalculationModel(9);
		
		quoteItemTO.setmICMSMaterialClass(0);
		
		StringBuilder calculation = new StringBuilder();
		
		if(quoteItemTO.getIdTaxQuoteItem() == quoteItemTO.getId()){
			calculation.append("| Item Pai |\n id = "+quoteItemTO.getId()+" - idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n");
		}else if(quoteItemTO.getIdTaxQuoteItem() > 0){
			calculation.append("| Item Filho |\n idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n | idItemPai = "+quoteItemTO.getIdTaxQuoteItem()+" - idAdmMaterialClassPAi = "+quoteItemTO.getIdAdmMaterialClassPai()+" - sNcmMaterialClassPai = "+quoteItemTO.getsNCMMaterialClassPai()+"\n");
		}
		
		BigDecimal accert = new BigDecimal(0);
		
		Float discount = (float) (quoteItemTO.getmImportedCost() * (quoteItemTO.getrDiscount() / 100));
		accert = new BigDecimal(discount).setScale(4, RoundingMode.HALF_EVEN);
		discount = accert.floatValue();
		
		calculation.append("Discount = "+discount+" => Cost:"+quoteItemTO.getmImportedCost()+" * (rDicount:"+quoteItemTO.getrDiscount()+" / 100) \n");
		
		Float netPrice = (float) (quoteItemTO.getmImportedCost() - discount);
		accert = new BigDecimal(netPrice).setScale(4, RoundingMode.HALF_EVEN);
		netPrice = accert.floatValue();
		quoteItemTO.setmNetPrice(accert.doubleValue());
		
		calculation.append(" | NetPrice = "+ netPrice+" => Cost:"+ quoteItemTO.getmImportedCost()+" - Discount:"+discount+"\n");
		
		
		Float factor = 0F;
		if(quoteItemTO.getfMercadoriaImportada() == 0){
			factor = 1- (quoteItemTO.getrICMSInterEstadualDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType());
			accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
			factor = accert.floatValue();
			
			if(factor < 0){
				factor = (quoteItemTO.getrICMSInterEstadualDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType()) - 1;
				accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
				factor = accert.floatValue();
				
				calculation.append(" | Factor = "+factor+" => (rICMSInterEstadual:"+quoteItemTO.getrICMSInterEstadualDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+") - 1\n");
			}else{
				calculation.append(" | Factor = "+factor+" => 1 - (rICMSInterEstadual:"+quoteItemTO.getrICMSInterEstadualDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+"\n");
			}
			
		}else{
			factor = 1- (new BigDecimal(quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination()).setScale(4, RoundingMode.HALF_EVEN).floatValue() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType());
			accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
			factor = accert.floatValue();			
			
			if(factor < 0){
				factor = (new BigDecimal(quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination()).setScale(4, RoundingMode.HALF_EVEN).floatValue()
							+ quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType()) - 1;
				accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
				factor = accert.floatValue();
				
				calculation.append(" | Factor = "+factor+" => (rICMSInterEstadualMaterialImportado:"+new BigDecimal(quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination()).setScale(4, RoundingMode.HALF_EVEN).floatValue()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+") - 1\n");
			}else{
				calculation.append(" | Factor = "+factor+" => 1 - (rICMSInterEstadualMaterialImportado:"+new BigDecimal(quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination()).setScale(4, RoundingMode.HALF_EVEN).floatValue()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+"\n");
			}
			
		}
		quoteItemTO.setrFatorSaida(factor);
		
		Float factorValue = netPrice / factor;
		
		calculation.append(" | FactorValue = "+factorValue+" => netPrice:"+netPrice+" / factor:"+factor+"\n");
		
		Float IPI = (netPrice / factor) * quoteItemTO.getrIPIMaterialClass();
		accert = new BigDecimal(IPI).setScale(4, RoundingMode.HALF_EVEN);
		IPI = accert.floatValue();
		quoteItemTO.setmIPI(accert.doubleValue());
		
		calculation.append(" | IPI = "+IPI+" => (netPrice:"+ netPrice +" / factor:"+factor+") * rIPI:"+quoteItemTO.getrIPIMaterialClass()+"\n");
		
		Float ICMSEstadoDestino = (netPrice / factor) * quoteItemTO.getrICMSEstadoDestinoDestination();
		accert = new BigDecimal(ICMSEstadoDestino).setScale(4, RoundingMode.HALF_EVEN);
		ICMSEstadoDestino = accert.floatValue();
		quoteItemTO.setmICMSEstadoDestino(accert.doubleValue());
		
		calculation.append(" | ICMSEstadoDestino = "+ICMSEstadoDestino+" => (netPrice:"+ netPrice +" / factor:"+factor+") * rICMSEstadoDestino:"+quoteItemTO.getrICMSEstadoDestinoDestination()+"\n");
		
		Float ICMSInterEstadual = 0F;
		if(quoteItemTO.getfMercadoriaImportada() == 0){
			ICMSInterEstadual = ((netPrice / factor)) * quoteItemTO.getrICMSInterEstadualDestination();
			accert = new BigDecimal(ICMSInterEstadual).setScale(4, RoundingMode.HALF_EVEN);
			ICMSInterEstadual = accert.floatValue();
			quoteItemTO.setmICMSInterEstadual(accert.doubleValue());
			
			calculation.append(" | ICMSInterEstadual = "+ICMSInterEstadual+" => (netPrice:"+netPrice+" / factor:"+factor+") * rICMSInterEstadual:"+quoteItemTO.getrICMSInterEstadualDestination()+"\n");
		}else{
			ICMSInterEstadual = ((netPrice / factor)) * quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination();
			accert = new BigDecimal(ICMSInterEstadual).setScale(4, RoundingMode.HALF_EVEN);
			ICMSInterEstadual = accert.floatValue();
			quoteItemTO.setmICMSInterEstadualMaterialImportado(accert.doubleValue());
			
			calculation.append(" | ICMSInterEstadualMaterialImportado = "+ICMSInterEstadual+" => (netPrice:"+netPrice+" / factor:"+factor+") * rICMSInterEstadualMaterialImportado:"+quoteItemTO.getrICMSInterEstadualMaterialImportadoDestination()+"\n");
		}
		
		Float PIS = (netPrice / factor) * quoteItemTO.getrPISProductType();
		accert = new BigDecimal(PIS).setScale(4, RoundingMode.HALF_EVEN);
		PIS = accert.floatValue();
		quoteItemTO.setmPIS(accert.doubleValue());
		
		calculation.append(" | PIS = "+PIS+" => (netPrice:"+netPrice+" / factor:"+factor+") * rPIS:"+quoteItemTO.getrPISProductType()+"\n");
		
		Float COFINS = (netPrice / factor) * quoteItemTO.getrCOFINSProductType();
		accert = new BigDecimal(COFINS).setScale(4, RoundingMode.HALF_EVEN);
		COFINS = accert.floatValue();
		quoteItemTO.setmCOFINS(accert.doubleValue());
		
		calculation.append(" | COFINS = "+COFINS+" => (netPrice:"+netPrice+" / factor:"+factor+") * rCOFINS:"+quoteItemTO.getrCOFINSProductType()+"\n");
		
		Float totalTaxes = IPI + ICMSInterEstadual + PIS + COFINS;
		accert = new BigDecimal(totalTaxes).setScale(4, RoundingMode.HALF_EVEN);
		totalTaxes = accert.floatValue();
		quoteItemTO.setmTotalTaxes(accert.doubleValue());
		
		calculation.append(" | totalTaxes = "+totalTaxes+" => IPI:"+IPI+" + ICMSInterEstadual:"+ICMSInterEstadual+" + PIS:"+PIS+" + COFINS:"+COFINS+"\n");
		
		Float baseICMSST = 0F;
		if(quoteItemTO.getfMercadoriaImportada() == 0){
			baseICMSST = (netPrice + totalTaxes) * (1 + quoteItemTO.getrIVAMaterialClassDestination());
			accert = new BigDecimal(baseICMSST).setScale(4, RoundingMode.HALF_EVEN);
			baseICMSST = accert.floatValue();
			quoteItemTO.setmBaseCalculoST(accert.doubleValue());
			
			calculation.append(" | baseICMSST = "+baseICMSST+" => (netPrice:"+netPrice+" + totalTaxes:"+totalTaxes+") * (1 + rIVAMaterialClassDestination:"+quoteItemTO.getrIVAMaterialClassDestination()+"\n");
		}else{
			baseICMSST = (netPrice + totalTaxes) * (1 + quoteItemTO.getrIVAMaterialImportadoMaterialClassDestination());
			accert = new BigDecimal(baseICMSST).setScale(4, RoundingMode.HALF_EVEN);
			baseICMSST = accert.floatValue();
			quoteItemTO.setmBaseCalculoST(accert.doubleValue());
			
			calculation.append(" | baseICMSST = "+baseICMSST+" => (netPrice:"+netPrice+" + totalTaxes:"+totalTaxes+") * (1 + rIVAMaterialImportadoMaterialClassDestination:"+quoteItemTO.getrIVAMaterialImportadoMaterialClassDestination()+"\n");
		}
		
		Float ICMSST = (baseICMSST * quoteItemTO.getrICMSEstadoDestinoDestination()) - ICMSInterEstadual;
		accert = new BigDecimal(ICMSST).setScale(4, RoundingMode.HALF_EVEN);
		ICMSST = accert.floatValue();
		quoteItemTO.setmICMSST(accert.doubleValue());
		
		calculation.append(" | ICMSST = "+ICMSST+" => (baseICMSST:"+baseICMSST+" * rICMSEstadoDestino:"+ quoteItemTO.getrICMSEstadoDestinoDestination()+") - ICMSInterEstadual:"+ICMSInterEstadual+"\n");
		
		Float grossPriceST = new BigDecimal(ICMSST + (netPrice + totalTaxes)).setScale(4, RoundingMode.HALF_EVEN).floatValue();
		
		calculation.append(" | grossPriceST = "+grossPriceST+" => (ICMSST:"+ICMSST+" + (netPrice:"+netPrice+" + totalTaxes:"+totalTaxes+"))\n");
		
		quoteItemTO.setmGrossPrice(new BigDecimal(grossPriceST).setScale(4, RoundingMode.HALF_EVEN).doubleValue());
		
		quoteItemTO.setsCalculationMemoryMaterial(calculation.toString());
		
		
		return quoteItemTO;
	}

	@Override
	public QuoteItemTO calculationScenario11(QuoteItemTO quoteItemTO) {
		
		//MOCK
//		quoteItemTO.setmCostMaterial(100.0);
//		quoteItemTO.setmSuggestedMargin(0.0);
//		quoteItemTO.setrIVAMaterialClassDestination(0.0F); // IVA - ST
//		quoteItemTO.setrIVAMaterialImportadoMaterialClassDestination(0.4054F); // IVA - Ajustado
//		quoteItemTO.setrICMSEstadoDestino(0.18F);
//		quoteItemTO.setrICMSInterEstadual(0.12F);
//		quoteItemTO.setrPIS(0.0165F);
//		quoteItemTO.setrCOFINS(0.076F);		
		//
		
		quoteItemTO.setiCalculationModel(11);
		
		quoteItemTO.setmICMSMaterialClass(0);
		
		StringBuilder calculation = new StringBuilder();
		
		if(quoteItemTO.getIdTaxQuoteItem() == quoteItemTO.getId()){
			calculation.append("| Item Pai |\n id = "+quoteItemTO.getId()+" - idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n");
		}else if(quoteItemTO.getIdTaxQuoteItem() > 0){
			calculation.append("| Item Filho |\n idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n | idItemPai = "+quoteItemTO.getIdTaxQuoteItem()+" - idAdmMaterialClassPAi = "+quoteItemTO.getIdAdmMaterialClassPai()+" - sNcmMaterialClassPai = "+quoteItemTO.getsNCMMaterialClassPai()+"\n");
		}
		
		BigDecimal accert = new BigDecimal(0);
		
		Float discount = (float) (quoteItemTO.getmImportedCost() * (quoteItemTO.getrDiscount() / 100));
		accert = new BigDecimal(discount).setScale(4, RoundingMode.HALF_EVEN); // usado para manter a precisao de 4 casas e o arredondamento correto;
		discount = accert.floatValue();
		
		calculation.append("Discount = "+discount+" => Cost:"+quoteItemTO.getmImportedCost()+" * (rDicount:"+quoteItemTO.getrDiscount()+" / 100) \n");
		
		Float netPrice = (float) (quoteItemTO.getmImportedCost() - discount);
		accert = new BigDecimal(netPrice).setScale(4, RoundingMode.HALF_EVEN);
		netPrice = accert.floatValue();
		quoteItemTO.setmNetPrice(accert.floatValue());
		
		calculation.append(" | NetPrice = "+ netPrice+" => Cost:"+ quoteItemTO.getmImportedCost()+" - Discount:"+discount+"\n");
		
		Float factor = 0F;
		factor = 1- (quoteItemTO.getrICMSInternoOrigemOrigin() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType());
		accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
		factor = accert.floatValue();
		
		if(factor < 0){
			factor = (quoteItemTO.getrICMSInternoOrigemOrigin() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType()) - 1;
			accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
			factor = accert.floatValue();
			
			calculation.append(" | Factor = "+factor+" => (rICMSInterno:"+quoteItemTO.getrICMSInternoOrigemOrigin()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+") - 1\n");
		}else{
			calculation.append(" | Factor = "+factor+" => 1 - (rICMSInterno:"+quoteItemTO.getrICMSInternoOrigemOrigin()+" + rPIS:"+quoteItemTO.getrPISProductType()+" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+"\n");
		}
		
		quoteItemTO.setrFatorSaida(factor);
		
		Float factorValue = netPrice / factor;
		accert = new BigDecimal(factorValue).setScale(4, RoundingMode.HALF_EVEN);
		factorValue = accert.floatValue();
		
		calculation.append(" | FactorValue = "+factorValue+" => netPrice:"+netPrice+" / factor:"+factor+"\n");
		
		Float IPI = (netPrice / factor) * quoteItemTO.getrIPIMaterialClass();
		accert = new BigDecimal(IPI).setScale(4, RoundingMode.HALF_EVEN);
		IPI = accert.floatValue();
		quoteItemTO.setmIPI(accert.doubleValue());
		
		calculation.append(" | IPI = "+IPI+" => (netPrice:"+ netPrice +" / factor:"+factor+") * rIPI:"+quoteItemTO.getrIPIMaterialClass());
		
		Float ICMSEstadoDestino = (netPrice / factor) * quoteItemTO.getrICMSInterEstadualOrigemOrigin();
		accert = new BigDecimal(ICMSEstadoDestino).setScale(4, RoundingMode.HALF_EVEN);
		ICMSEstadoDestino = accert.floatValue();
		quoteItemTO.setmICMSEstadoDestino(accert.doubleValue());
		
		calculation.append(" | ICMSEstadoDestino = "+ICMSEstadoDestino+" => (netPrice:"+ netPrice +" / factor:"+factor+") * rICMSEstadoDestino:"+quoteItemTO.getrICMSInterEstadualOrigemOrigin());
		
		Float ICMSInterEstadual = 0F;
		ICMSInterEstadual = (netPrice / factor) * quoteItemTO.getrICMSInternoOrigemOrigin();
		accert = new BigDecimal(ICMSInterEstadual).setScale(4, RoundingMode.HALF_EVEN);
		ICMSInterEstadual = accert.floatValue();
		quoteItemTO.setmICMSInterEstadual(accert.doubleValue());
		calculation.append(" | ICMSInterEstadual = "+ICMSInterEstadual+" => (netPrice:"+netPrice+" / factor:"+factor+") * rICMSInterEstadual:"+quoteItemTO.getrICMSInternoOrigemOrigin()+"\n");
		
		Float PIS = (netPrice / factor) * quoteItemTO.getrPISProductType();
		accert = new BigDecimal(PIS).setScale(4, RoundingMode.HALF_EVEN);
		PIS = accert.floatValue();
		quoteItemTO.setmPIS(accert.doubleValue());
		
		calculation.append(" | PIS = "+PIS+" => (netPrice:"+netPrice+" / factor:"+factor+") * rPIS:"+quoteItemTO.getrPISProductType()+"\n");
		
		Float COFINS = (netPrice / factor) * quoteItemTO.getrCOFINSProductType();
		accert = new BigDecimal(COFINS).setScale(4, RoundingMode.HALF_EVEN);
		COFINS = accert.floatValue();
		quoteItemTO.setmCOFINS(accert.doubleValue());
		
		calculation.append(" | COFINS = "+COFINS+" => (netPrice:"+netPrice+" / factor:"+factor+") * rCOFINS:"+quoteItemTO.getrCOFINSProductType()+"\n");
		
		Float totalTaxes = IPI + ICMSInterEstadual + PIS + COFINS;
		accert = new BigDecimal(totalTaxes).setScale(4, RoundingMode.HALF_EVEN);
		totalTaxes = accert.floatValue();
		quoteItemTO.setmTotalTaxes(accert.doubleValue());
		
		calculation.append(" | totalTaxes = "+totalTaxes+" => IPI:"+IPI+" + ICMSInterEstadual:"+ICMSInterEstadual+" + PIS:"+PIS+" + COFINS:"+COFINS+"\n");
		
		Float baseICMSST = 0F;
		if(quoteItemTO.getfMercadoriaImportada() == 0){
			baseICMSST = (netPrice + totalTaxes) * (1 + quoteItemTO.getrIVAMaterialClassDestination());
			accert = new BigDecimal(baseICMSST).setScale(4, RoundingMode.HALF_EVEN);
			baseICMSST = accert.floatValue();
			quoteItemTO.setmBaseCalculoST(accert.doubleValue());
			calculation.append(" | baseICMSST = "+baseICMSST+" => (netPrice:"+netPrice+" + totalTaxes:"+totalTaxes+") * (1 + rIVAMaterialClassDestination:"+quoteItemTO.getrIVAMaterialClassDestination()+"\n");
			
		}else{
			baseICMSST = (netPrice + totalTaxes) * (1 + quoteItemTO.getrIVAMaterialImportadoMaterialClassDestination());
			accert = new BigDecimal(baseICMSST).setScale(4, RoundingMode.HALF_EVEN);
			baseICMSST = accert.floatValue();
			quoteItemTO.setmBaseCalculoST(accert.doubleValue());
			calculation.append(" | baseICMSST = "+baseICMSST+" => (netPrice:"+netPrice+" + totalTaxes:"+totalTaxes+") * (1 + rIVAMaterialImportadoMaterialClassDestination:"+quoteItemTO.getrIVAMaterialImportadoMaterialClassDestination()+"\n");
		}
		
		Float ICMSST = (baseICMSST * quoteItemTO.getrICMSInterEstadualOrigemOrigin()) - ICMSInterEstadual;
		accert = new BigDecimal(ICMSST).setScale(4, RoundingMode.HALF_EVEN);
		ICMSST = accert.floatValue();
		quoteItemTO.setmICMSST(accert.doubleValue());
		
		calculation.append(" | ICMSST = "+ICMSST+" => (baseICMSST:"+baseICMSST+" * rICMSEstadoDestino:"+ quoteItemTO.getrICMSInterEstadualOrigemOrigin()+") - ICMSInterEstadual:"+ICMSInterEstadual+"\n");
		
		Float grossPriceST = (ICMSST + (netPrice + totalTaxes));
		accert = new BigDecimal(grossPriceST).setScale(4, RoundingMode.HALF_EVEN);
		grossPriceST = accert.floatValue();
		quoteItemTO.setmGrossPrice(accert.doubleValue());
		
		calculation.append(" | grossPriceST = "+grossPriceST+" => (ICMSST:"+ICMSST+" + (netPrice:"+netPrice+" + totalTaxes:"+totalTaxes+"))\n");
		
		quoteItemTO.setsCalculationMemoryMaterial(calculation.toString());
		
		return quoteItemTO;
	}
	
	/**
	 * Cenário de cálculo para escape - quando nenhum dos cenários atender será cálculado neste cenário.
	 * @param quoteItemTO
	 * @return
	 */
	@Override
	public QuoteItemTO calculationScenario99(QuoteItemTO quoteItemTO) {
		
		//MOCK
//		quoteItemTO.setmCostMaterial(100.0);
//		quoteItemTO.setmSuggestedMargin(0.0);
//		quoteItemTO.setrPIS(0.0165F);
//		quoteItemTO.setrCOFINS(0.076F);
//		quoteItemTO.setrIPI(0.15F);
//		quoteItemTO.setrICMSEstadoDestino(0.12F);
		//
		
		quoteItemTO.setiCalculationModel(99);
		
		quoteItemTO.setmICMSMaterialClass(0);
		
		StringBuilder calculation = new StringBuilder();
		
		if(quoteItemTO.getIdTaxQuoteItem() == quoteItemTO.getId()){
			calculation.append("| Item Pai |\n id = "+quoteItemTO.getId()+" - idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n");
		}else if(quoteItemTO.getIdTaxQuoteItem() > 0){
			calculation.append("| Item Filho |\n idAdmMaterialClass = "+quoteItemTO.getIdAdmMaterialClass()+" - sNcmMaterial = "+quoteItemTO.getsNCMMaterialClass()+"\n | idItemPai = "+quoteItemTO.getIdTaxQuoteItem()+" - idAdmMaterialClassPAi = "+quoteItemTO.getIdAdmMaterialClassPai()+" - sNcmMaterialClassPai = "+quoteItemTO.getsNCMMaterialClassPai()+"\n");
		}
		
		BigDecimal accert = new BigDecimal(0);
		
		Float discount = (float) (quoteItemTO.getmImportedCost() * (quoteItemTO.getrDiscount() / 100));
		accert = new BigDecimal(discount).setScale(4, RoundingMode.HALF_EVEN);
		discount = accert.floatValue();
		
		calculation.append("Discount = "+discount+" => Cost:"+quoteItemTO.getmImportedCost()+" * (rDicount:"+quoteItemTO.getrDiscount()+" / 100) \n");
		
		Float netPrice = (float) (quoteItemTO.getmImportedCost() - discount);
		accert = new BigDecimal(netPrice).setScale(4, RoundingMode.HALF_EVEN);
		netPrice = accert.floatValue();
		quoteItemTO.setmNetPrice(accert.doubleValue());
		
		calculation.append(" | NetPrice = "+ netPrice+" => Cost:"+ quoteItemTO.getmImportedCost()+" - Discount:"+discount+"\n");
		
		Float factor = 1 - ((quoteItemTO.getrICMSEstadoDestinoDestination() * quoteItemTO.getrIPIMaterialClass()) + quoteItemTO.getrICMSEstadoDestinoDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType());
		accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
		factor = accert.floatValue();
		
		
		if(factor < 0){
			factor = ((quoteItemTO.getrICMSEstadoDestinoDestination() * quoteItemTO.getrIPIMaterialClass()) + quoteItemTO.getrICMSEstadoDestinoDestination() + quoteItemTO.getrPISProductType() + quoteItemTO.getrCOFINSProductType()) - 1;
			accert = new BigDecimal(factor).setScale(4, RoundingMode.HALF_EVEN);
			factor = accert.floatValue();
			
			calculation.append(" | Factor = "+factor+" => ((rICMSEstadoDestino:"+ quoteItemTO.getrICMSEstadoDestinoDestination()+" * rIPI:"+quoteItemTO.getrIPIMaterialClass()+") + rICMSEstadoDestino:"+quoteItemTO.getrICMSEstadoDestinoDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+
					" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+") - 1\n");
		}else{
			calculation.append(" | Factor = "+factor+" => 1 - ((rICMSEstadoDestino:"+ quoteItemTO.getrICMSEstadoDestinoDestination()+" * rIPI:"+quoteItemTO.getrIPIMaterialClass()+") + rICMSEstadoDestino:"+quoteItemTO.getrICMSEstadoDestinoDestination()+" + rPIS:"+quoteItemTO.getrPISProductType()+
					" + rCOFINS:"+quoteItemTO.getrCOFINSProductType()+")\n");
		}
		
		quoteItemTO.setrFatorSaida(factor);
		
		
		Float factorValue = netPrice / factor;
		
		calculation.append(" | FactorValue = "+factorValue+" => netPrice:"+netPrice+" / factor:"+factor+"\n");
		
		Float IPI = (netPrice / factor) * quoteItemTO.getrIPIMaterialClass();
		accert = new BigDecimal(IPI).setScale(4, RoundingMode.HALF_EVEN);
		IPI = accert.floatValue();
		quoteItemTO.setmIPI(accert.doubleValue());
		
		calculation.append(" | IPI = "+IPI+" => (netPrice:"+ netPrice +" / factor:"+factor+") * rIPI:"+quoteItemTO.getrIPIMaterialClass());
		
		Float ICMSEstadualDestino = ((netPrice / factor) + IPI) * quoteItemTO.getrICMSEstadoDestinoDestination();
		accert = new BigDecimal(ICMSEstadualDestino).setScale(4, RoundingMode.HALF_EVEN);
		ICMSEstadualDestino = accert.floatValue();
		quoteItemTO.setmICMSEstadoDestino(accert.doubleValue());
		
		calculation.append(" | ICMSEstadualDestino = "+ICMSEstadualDestino+" => ((netPrice:"+netPrice+" / factor:"+factor+") + IPI:"+IPI+") * rICMSEstadoDestino:"+quoteItemTO.getrICMSEstadoDestinoDestination()+"\n");
		
		Float PIS = (netPrice / factor) * quoteItemTO.getrPISProductType();
		accert = new BigDecimal(PIS).setScale(4, RoundingMode.HALF_EVEN);
		PIS = accert.floatValue();
		quoteItemTO.setmPIS(accert.doubleValue());
		
		calculation.append(" | PIS = "+PIS+" => (netPrice:"+netPrice+" / factor:"+factor+") * rPIS:"+quoteItemTO.getrPISProductType()+"\n");
		
		Float COFINS = (netPrice / factor) * quoteItemTO.getrCOFINSProductType();
		accert = new BigDecimal(COFINS).setScale(4, RoundingMode.HALF_EVEN);
		COFINS = accert.floatValue();
		quoteItemTO.setmCOFINS(accert.doubleValue());
		
		calculation.append(" | COFINS = "+COFINS+" => (netPrice:"+netPrice+" / factor:"+factor+") * rCOFINS:"+quoteItemTO.getrCOFINSProductType()+"\n");
		
		Float totalTaxes = IPI + ICMSEstadualDestino + PIS + COFINS;
		accert = new BigDecimal(totalTaxes).setScale(4, RoundingMode.HALF_EVEN);
		totalTaxes = accert.floatValue();
		quoteItemTO.setmTotalTaxes(accert.doubleValue());
		
		calculation.append(" | TotalTAxes = "+totalTaxes+" => IPI:"+IPI+" + ICMSEstadualDestino:"+ICMSEstadualDestino+" + PIS:"+ PIS +" + COFINS:"+COFINS+"\n");
		
		quoteItemTO.setmGrossPrice(new BigDecimal(netPrice + totalTaxes).setScale(4, RoundingMode.HALF_EVEN).doubleValue());
		
		calculation.append(" | GrossPrice = "+(netPrice + totalTaxes)+" => netPrice:"+netPrice+" + TotalTaxes:"+totalTaxes+"\n");
		
		quoteItemTO.setsCalculationMemoryMaterial(calculation.toString());
		
		return quoteItemTO;
	}
	
	public void assumeValoresItemPai(QuoteItemTO itemPai, QuoteItemTO itemFilho){
		
		itemFilho.setiTaxModel(itemPai.getiTaxModel());
		
		itemFilho.setrCOFINSProductType(itemPai.getrCOFINSProductType());
		itemFilho.setrPISProductType(itemPai.getrPISProductType());
		itemFilho.setrIPIMaterialClass(itemPai.getrIPIMaterialClass()); 
		itemFilho.setrCSSLProductType(itemPai.getrCSSLProductType());
		itemFilho.setrIRProductType(itemPai.getrIRProductType());
		itemFilho.setrISSOriginServ(itemPai.getrISSOriginServ());
		itemFilho.setrISSSoftOrigin(itemPai.getrISSOriginServ());
		itemFilho.setrICMSEstadoDestinoDestination(itemPai.getrICMSEstadoDestinoDestination());
		itemFilho.setfISSEspecialMaterialClass(itemPai.getfISSEspecialMaterialClass());
		itemFilho.setrICMSEstadoOrigemInternoMaterialClassOrigin(itemPai.getrICMSEstadoOrigemInternoMaterialClassOrigin());
		itemFilho.setrICMSEstadoOrigemOrigin(itemPai.getrICMSEstadoOrigemOrigin());
		itemFilho.setrICMSInternoMaterialClassDestination(itemPai.getrICMSInternoMaterialClassDestination());
		itemFilho.setrICMSInternoOrigemOrigin(itemPai.getrICMSInternoOrigemOrigin());
		itemFilho.setrIVAMaterialClassDestination(itemPai.getrIVAMaterialClassDestination());
		itemFilho.setrIVAMaterialImportadoMaterialClassDestination(itemPai.getrIVAMaterialImportadoMaterialClassDestination());
		itemFilho.setrIVAMaterialImportadoOrigin(itemPai.getrIVAMaterialImportadoOrigin());
		itemFilho.setrICMSInterEstadualDestination(itemPai.getrICMSInterEstadualDestination());
		itemFilho.setrICMSInterEstadualMaterialImportadoDestination(itemPai.getrICMSInterEstadualMaterialImportadoDestination());
		itemFilho.setrICMSMaterialClassMaterialClass(itemPai.getrICMSMaterialClassMaterialClass());	
		
		itemFilho.setfSubstituicaoTributariaSaidaMaterialClass(itemPai.getfSubstituicaoTributariaSaidaMaterialClass());
		itemFilho.setfDestinationWithProtocol(itemPai.getfDestinationWithProtocol());
		
		
	}
	

}
