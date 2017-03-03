package br.com.operations.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.operations.dao.MaterialImportTempDAO;
import br.com.operations.entity.TbAdmMaterialImportTemp;
import br.com.operations.facade.MaterialImportTempFacade;

@Stateless
public class MaterialImportTempFacadeImpl implements MaterialImportTempFacade {

	@EJB
	private MaterialImportTempDAO tempDAO;
	
	@Override
	public List<TbAdmMaterialImportTemp> findAll() {
		return tempDAO.findAll();
	}

	@Override
	public void save(TbAdmMaterialImportTemp importTemp) {
		tempDAO.save(importTemp);
	}

	@Override
	public void deleteAll() {
		
		String sqlQuery = "delete from TbAdmMaterialImportTemp";
		
		tempDAO.deleteAll(sqlQuery);
		
	}

	@Override
	public TbAdmMaterialImportTemp saveReturn(TbAdmMaterialImportTemp importTemp) {
		return tempDAO.saveReturn(importTemp);
	}

	@Override
	public void alter(TbAdmMaterialImportTemp importTemp) {
		tempDAO.alter(importTemp);
	}

	@Override
	public void deleteDuplicates() {
		
		String sqlQuery = "DELETE FROM TBADMMATERIAL_IMPORT_TEMP D"+
						  " WHERE ID IN( "+
				              "SELECT I.ID "+
				              "FROM TBADMMATERIAL_IMPORT_TEMP I "+
				              "INNER JOIN "+
				              "("+
				                    "SELECT MIN(IMP.ID) AS ID, "+
				                    "IMP.SINTERNALMODEL "+
				                    "FROM TBADMMATERIAL_IMPORT_TEMP IMP "+
				                    "INNER JOIN "+
				                    "("+
				                      "SELECT T.SINTERNALMODEL "+ 
				                      "FROM TBADMMATERIAL_IMPORT_TEMP T "+ 
				                      "GROUP BY T.SINTERNALMODEL "+ 
				                      "HAVING COUNT(T.ID) > 1 "+
				                    ") REP "+
				                    "ON IMP.SINTERNALMODEL = REP.SINTERNALMODEL "+
				                    "GROUP BY IMP.SINTERNALMODEL "+
				              ") IDS "+
				              "ON I.SINTERNALMODEL = IDS.SINTERNALMODEL "+
				              "AND I.ID <> IDS.ID "+
				            ")";
		
		tempDAO.deleteDuplicates(sqlQuery);		
	}

	@Override
	public void delete(TbAdmMaterialImportTemp importTemp) {
		tempDAO.delete(importTemp);
	}
	
	@Override
	public void validaLocalizaNcmImportado() {
		
		//Update que remove todos os NCM inválidos da Tabela de importação
		String sqlQueryRemoveNcmError = "update TBADMMATERIAL_IMPORT_TEMP p set p.FISCALCLASSIFICATIONCODE = NULL "
				+ "where p.FISCALCLASSIFICATIONCODE = 'DEFAULT' or length(p.FISCALCLASSIFICATIONCODE) < 8";
		
		/*Original
		 * String sqlQueryRemoveNcmError = "update TBADMMATERIAL_IMPORT_TEMP p set p.FISCALCLASSIFICATIONCODE = NULL "
				+ "where p.FISCALCLASSIFICATIONCODE = 'DEFAULT' or p.FISCALCLASSIFICATIONCODE like '%X' or length(p.FISCALCLASSIFICATIONCODE) < 8";
		 */
		
		tempDAO.validaLocalizaNcmImportado(sqlQueryRemoveNcmError);
		
		
		//Merge para vincular os IDADMMaterialClass conforme equivalencia do NCM
		String sqlQueryAddIdClassForNcmTbImport = "MERGE INTO TBADMMATERIAL_IMPORT_TEMP t "
												+ "USING (SELECT id, sNCM FROM tbADMMaterialClass)mc "
												+ "ON (mc.sNCM = t.FISCALCLASSIFICATIONCODE) "
												+ "WHEN MATCHED THEN UPDATE SET  t.idADMMaterialClass = mc.id";
		
		tempDAO.validaLocalizaNcmImportado(sqlQueryAddIdClassForNcmTbImport);
		
		
		//Insert dos novos NCM ( que não existem ainda na tbADMMaterialClass
		String sqlQueryInsertNewNcm = "insert into tbADMMaterialClass (ID, SNCM) "
										+ "SELECT SEQ_TBADMMATERIALCLASS.NEXTVAL , FISCALCLASSIFICATIONCODE FROM "
										+ "(SELECT t.FISCALCLASSIFICATIONCODE FROM TBADMMATERIAL_IMPORT_TEMP t "
										+ "LEFT OUTER JOIN tbADMMaterialClass mc ON mc.sNCM = t.FISCALCLASSIFICATIONCODE "
										+ "WHERE t.idADMMaterialClass is null AND Not(t.FISCALCLASSIFICATIONCODE is null) "
										+ "GROUP BY t.FISCALCLASSIFICATIONCODE)";
		
		tempDAO.validaLocalizaNcmImportado(sqlQueryInsertNewNcm);
		
		
		//Merge para vincular os Novos IDADMMaterialClass conforme equivalencia do NCM
		String sqlQueryAddIdClassForNcmTbImportNew = "MERGE INTO TBADMMATERIAL_IMPORT_TEMP t "
														+ "USING (SELECT id, sNCM FROM tbADMMaterialClass)mc "
														+ "ON (mc.sNCM = t.FISCALCLASSIFICATIONCODE) "
														+ "WHEN MATCHED THEN UPDATE SET  t.idADMMaterialClass = mc.id "
														+ "WHERE t.idADMMaterialClass is null";
		
		tempDAO.validaLocalizaNcmImportado(sqlQueryAddIdClassForNcmTbImportNew);
		
	}

	@Override
	public void validaLocalizaMaterialImportado() {
		
		//identificando se o material já existe
		String sqlQueryValidaMaterialExistente = "MERGE INTO TBADMMATERIAL_IMPORT_TEMP t "
												+ "USING (SELECT id, SINTERNALMODEL FROM tbADMMaterial)m "
												+ "ON (m.SINTERNALMODEL = t.SINTERNALMODEL) WHEN MATCHED THEN "
												+ "UPDATE SET  t.idADMMaterial = m.id";
		
		tempDAO.validaProcessoImportMaterial(sqlQueryValidaMaterialExistente);
		
		
		//inserindo novos materiais
		String sqlQueryInsertNovosMateriais = "INSERT INTO tbADMMaterial (ID, SINTERNALMODEL) "
											+ "SELECT SEQ_TBADMMATERIAL.NEXTVAL AS ID , SINTERNALMODEL "
											+ "FROM (SELECT t.SINTERNALMODEL  FROM TBADMMATERIAL_IMPORT_TEMP t "
											+ "WHERE t.idADMMaterial is null "
											+ "AND Not(t.SINTERNALMODEL is null) "
											+ "GROUP BY t.SINTERNALMODEL)";
		
		tempDAO.validaProcessoImportMaterial(sqlQueryInsertNovosMateriais);
		
		//atualizando o material inserido (novo)
		String sqlQueryAtualizaNovoMaterialInserido = "MERGE INTO TBADMMATERIAL_IMPORT_TEMP t "
													+ "USING (SELECT id, SINTERNALMODEL FROM tbADMMaterial)m "
													+ "ON (m.SINTERNALMODEL = t.SINTERNALMODEL) "
													+ "WHEN MATCHED THEN "
													+ "UPDATE SET t.idADMMaterial = m.id "
													+ "WHERE t.idADMMaterial is null";
		
		tempDAO.validaProcessoImportMaterial(sqlQueryAtualizaNovoMaterialInserido);
		
		
		/*atualizando a tabela de materiais com os dados importados desde que o 
		material class ou a descrição tenham diferenças entre o importado 
		e o que já existe na tabela de materiais*/
		String sqlQueryAtualizaTotalNovoMaterialInserido = "MERGE INTO tbADMMaterial m "
															+ "USING (SELECT idADMMaterial, idADMMaterialClass, SINTERNALMODEL, sDescription "
															+ "FROM TBADMMATERIAL_IMPORT_TEMP)t ON (m.id = t.idADMMaterial) "
															+ "WHEN MATCHED THEN "
															+ "UPDATE SET  m.idADMMaterialClass = t.idADMMaterialClass, m.sDescription = t.sDescription, m.FAVAILABLE = 1 "
															+ "WHERE NVL(m.sDescription,'Descricao::operations') <> t.sDescription "
															+ "OR m.idADMMaterialClass <> t.idADMMaterialClass "
															+ "Or Not (t.idADMMaterialClass is null)";
		
		tempDAO.validaProcessoImportMaterial(sqlQueryAtualizaTotalNovoMaterialInserido);
		

		String sqlQuerySetManufacturerOracle = "UPDATE TbAdmMaterial material set material.IDADMMATERIALMANUFACTURER = 21, material.FAVAILABLE = 1";
		
		tempDAO.validaProcessoImportMaterial(sqlQuerySetManufacturerOracle);
		
	}

	@Override
	public void insertQuery(List<TbAdmMaterialImportTemp> listaInsert) {
		
		for(int i = 0; i < listaInsert.size(); i++){
			
			tempDAO.saveNew(listaInsert.get(i));
			
		}
		
	}
}
