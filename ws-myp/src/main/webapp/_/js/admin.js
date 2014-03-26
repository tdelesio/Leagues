var server = "http://localhost:8080/ws/rest/admin";
var league = new String();
var week = new Number();
var playerID = new Number();
var weekmap = {};

function getPage(){
	
	// CLEAR AND LOAD
	document.getElementById("loader-page").style.visibility = "visible";
		
	$("#results").text("");	
	$("#sn-view").text("");
	
	var myHTML = "";
	var page = $("#page").val();
	
	switch(page){
		
		//this is the make pics page
		case 'setupweek':
			
			//create the variables
			var seasonHTML = "";
	
			break;
			
			
		case 'createweek':

			break;
		case 'creategame':
		
			
			// CRETE NEW SUBNAV 
			
			break;
	}
	
	
}




function showSpan(ID){
	
	// TOGGLE NAV
	for(g=0; g<3; g++){
		$("#button_"+g).removeClass("selected");
		$(".sg"+g).addClass("hide");
	}
	
	$("#button_"+ID).addClass("selected");
	$(".sg"+ID).removeClass("hide");
	
			
}


// START THE APP

$(document).ready(function(){
	getPage();
});