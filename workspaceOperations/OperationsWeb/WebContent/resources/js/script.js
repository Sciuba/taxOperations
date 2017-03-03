/**
 * 
 */

function populateVariable(id){
	
	
	if(id == 'form:printPanelFull'){
		document.getElementById("form:fileName").value = "TaxesReportFull";
	}else if(id == 'form:printPanel'){
		document.getElementById("form:fileName").value = "TaxesReport";
	}else if(id == 'form:printPanelInside'){
		document.getElementById("form:fileName").value = "TaxesAnalysis";
	}else{
		id = 'form:printPanelInside';
		document.getElementById("form:fileName").value = "TaxesAnalysisCalc";
	}
	
	var conteudo = document.getElementById(id).innerHTML;
	
	document.getElementById("form:htmlToExcel").value = conteudo;
	
}

function zerarFiltrosTable(){
	$('.ui-column-filter').val("");
}


function inputValueHidden(id, idValue){
	
	var value = document.getElementById(idValue).value;
	
	document.getElementById(id).value = value;
	
}

//Organization Adm
function inputHiddenValue(value, id){
	
	document.getElementById(id).value = value;
	
}

function disableComponents(id){
	
	if(id == 'true'){
		document.getElementById('rdoDef2:0').checked = false;
		document.getElementById('proDef').disabled = true;
	}else{
		document.getElementById('rdoDef1:0').checked = false;
		document.getElementById('proDef').disabled = false;
	}
	
}

function atualizaComponenteDefaultProfile(){
	
	var v = $('#proDef').val();
	
	$('#hiddenDef').val(v);
	
}
