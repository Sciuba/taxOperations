package br.com.saboia.app.quote.jpa;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;



public class Jpa {
	
	private final static String UNIT_NAME = "OperationsBussiness";
	 
//    @PersistenceContext(unitName = UNIT_NAME)
//	protected EntityManager em;
	
	Connection conn = null;
	
    
	protected Connection getConnection(){
		
		try {
			 
			Class.forName("oracle.jdbc.driver.OracleDriver");
 		 
			this.conn = null;
			
			InitialContext contexto = new InitialContext();
	     
	        DataSource ds = (DataSource) contexto.lookup("jdbc/Operations"); //prod jdbc/Saboia //dev jdbc/Operations
			
			this.conn = ds.getConnection();
						 
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.conn;
	}
	
    protected void closeConnection() throws SQLException{
    	
    	this.conn.close();
		
	}
    
}
