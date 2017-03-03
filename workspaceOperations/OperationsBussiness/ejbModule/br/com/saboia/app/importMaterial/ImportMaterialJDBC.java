package br.com.saboia.app.importMaterial;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.app.quote.jpa.Jpa;
import br.com.saboia.entity.TbAdmMaterialImportTemp;

@Stateless
public class ImportMaterialJDBC extends Jpa{
	
	public void insertListMaterials(List<TbAdmMaterialImportTemp> listaInsert){
		
		String sqlQuery = "Insert into TBADMMATERIAL_IMPORT_TEMP (ID,FISCALCLASSIFICATIONCODE,ITEMORIGIN,MCOST,"
				+ "MLISTPRICE,MREPFLOORPRICE,MSTANDARDCOST,SDESCRIPTION,SINTERNALMODEL,"
				+ "SMANUFACTURERMODEL,SPRIMARYUNITMEASURE,SLIFECYCLEPHASE,SITEMTYPE,SITEMSTATUS,"
				+ "SCOSTUMERORDERFLAG,SINTERNALORDERFLAG,STRANSACTIONCONDITIONCLASS,SITEMFISCALTYPE,"
				+ "IFEDERALTRIBUTARYSITUATION,ISTATETRIBUTARYSITUATION,DTITEMCREATIONDATE,"
				+ "DTLASTUPDATE,SLINEBUSINESS,FORIGEMDAMERCADORIA) values "
				
				+ "(SEQ_MATERIAL_IMPORT_TEMP.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		for(TbAdmMaterialImportTemp temp : listaInsert){
			
			Connection conn = super.getConnection();
			PreparedStatement stmt = null;
			
			try{
				
				stmt = conn.prepareStatement(sqlQuery);
				
				try{
					stmt.setString(1, temp.getFiscalClassificationCode());
				}catch(Exception e){
					stmt.setString(1, "");
				}
				
				try{
					stmt.setString(2, temp.getItemOrigin());
				}catch(Exception e){
					stmt.setString(2, "");
				}
				
				try{
					stmt.setDouble(3, temp.getmCost());
				}catch(Exception e){
					stmt.setDouble(3, 0D);
				}
				
				try{
					stmt.setDouble(4, temp.getmListPrice());
				}catch(Exception e){
					stmt.setDouble(4, 0D);
				}
				
				try{
					stmt.setDouble(5, temp.getmRepFloorPrice());
				}catch(Exception e){
					stmt.setDouble(5, 0D);
				}
				
				try{
					stmt.setDouble(6, temp.getmStandardCost());
				}catch(Exception e){
					stmt.setDouble(6, 0D);
				}
				
				try{
					stmt.setString(7, temp.getsDescription());
				}catch(Exception e){
					stmt.setString(7, "");
				}
				
				try{
					stmt.setString(8, temp.getsInternalModel());
				}catch(Exception e){
					stmt.setString(8, "");
				}
				
				try{
					stmt.setString(9, temp.getsManufacturerModel());
				}catch(Exception e){
					stmt.setString(9, "");
				}
				
				try{
					stmt.setString(10, temp.getsPrimaryUnitMeasure());
				}catch(Exception e){
					stmt.setString(10, "");
				}
				
				try{
					stmt.setString(11, temp.getsLifeCyclePhase());
				}catch(Exception e){
					stmt.setString(11, "");
				}
				
				try{
					stmt.setString(12, temp.getsItemType());
				}catch(Exception e){
					stmt.setString(12, "");
				}
				
				try{
					stmt.setString(13, temp.getsItemStatus());
				}catch(Exception e){
					stmt.setString(13, "");
				}
				
				try{
					stmt.setString(14, temp.getsCostumerOrderFlag());
				}catch(Exception e){
					stmt.setString(14, "");
				}
				
				try{
					stmt.setString(15, temp.getsInternalOrderFlag());
				}catch(Exception e){
					stmt.setString(15, "");
				}
				
				try{
					stmt.setString(16, temp.getsTransactionConditionClass());
				}catch(Exception e){
					stmt.setString(16, "");
				}
				
				try{
					stmt.setString(17, temp.getsItemFiscalType());
				}catch(Exception e){
					stmt.setString(17, "");
				}
				
				try{
					stmt.setInt(18, temp.getiFederalTributarySituation());
				}catch(Exception e){
					stmt.setInt(18, 0);
				}
				
				try{
					stmt.setInt(19, temp.getiStateTributarySituation());
				}catch(Exception e){
					stmt.setInt(19, 0);
				}
				
				try{
					stmt.setDate(20, (Date) temp.getDtItemCreationDate());
				}catch(Exception e){
					stmt.setDate(20, null);
				}
				
				try{
					stmt.setDate(21, (Date) temp.getDtLastUpdate());
				}catch(Exception e){
					stmt.setDate(21, null);
				}
				
				try{
					stmt.setString(22, temp.getsLineBusiness());
				}catch(Exception e){
					stmt.setString(22, "");
				}
				
				stmt.setInt(23, temp.getfOrigemDaMercadoria());
				
				stmt.executeUpdate();
				
			}catch(Exception e){
				e.printStackTrace();
				
			}finally{
				if(stmt != null){
					try{
						stmt.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
				if (conn != null) {
					try {
						conn.close();
						super.closeConnection();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
