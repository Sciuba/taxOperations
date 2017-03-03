package br.com.saboia.enums;

public enum MaterialEnum {
	
	ITEM("Item", 1),
	DESCRIPTION("Description", 2),
	PRIMARYUNITMEASURE("Primary Unit of Measure", 3),
	LIFECYCLEPHASE("Life Cycle Phase", 4),
	ITEMTYPE("Item Type", 5),
	ITEMSTATUS("Item Status", 6),
	CUSTOMERORDERFLAG("Customer Order Flag", 7),
	INTERNALORDERFLAG("Internal Order Flag", 8),
	FISCALCLASSIFICATIONCODE("Fiscal Classification Code", 9),
	TRANSACTIONCONDITIONCLASS("Transaction Condition Class", 10),
	ITEMORIGIN("Item Origin", 11),
	ITEMFISCALTYPE("Item Fiscal Type", 12),
	FEDERALTRIBUTARYSITUATION("Federal Tributary Situation", 13),
	STATETRIBUTARYSITUATION("State Tributary Situation", 14),
	ITEMCREATIONDATE("Item Creation Date", 15),
	LASTUPDATEDATE("Last Update Date", 16);
	
	public String nome;
	public int code;
	
	MaterialEnum(String nome, int code){
		this.nome = nome;
		this.code = code;
	}

	public static boolean isColumnCorrect(String nome){
		
		String[] n = nome.split(" ");
		
		try{
			if(nome.contains("Origin")){
				nome = n[0].substring(0, 4)+" "+n[1];
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(nome.equals(PRIMARYUNITMEASURE.getNome())){
			return true;
		}else if(nome.equals(ITEM.getNome())){
			return true;
		}else if(nome.equals(DESCRIPTION.getNome())){
			return true;
		}else if(nome.equals(ITEMORIGIN.getNome())){
			return true;
		}else if(nome.equals(FISCALCLASSIFICATIONCODE.getNome())){
			return true;
		}else if(nome.equals(LIFECYCLEPHASE.getNome())){
			return true;
		}else if(nome.equals(ITEMTYPE.getNome())){
			return true;
		}else if(nome.equals(ITEMSTATUS.getNome())){
			return true;
		}else if(nome.equals(CUSTOMERORDERFLAG.getNome())){
			return true;
		}else if(nome.equals(INTERNALORDERFLAG.getNome())){
			return true;
		}else if(nome.equals(TRANSACTIONCONDITIONCLASS.getNome())){
			return true;
		}else if(nome.equals(ITEMFISCALTYPE.getNome())){
			return true;
		}else if(nome.equals(FEDERALTRIBUTARYSITUATION.getNome())){
			return true;
		}else if(nome.equals(STATETRIBUTARYSITUATION.getNome())){
			return true;
		}else if(nome.equals(ITEMCREATIONDATE.getNome())){
			return true;
		}else if(nome.equals(LASTUPDATEDATE.getNome())){
			return true;
		}else{
			return false;
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
	
}
