var server = "http://localhost:8080/ws/rest";
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
		case 'make':
			
			//create the variables
			var seasonHTML = "";
			
	
			// POINTS
			myHTML += '<div class="points"><div id="point-total"></div></div>';
			
			//call to get the leagues that the player is in
			url = server + '/leagues';
			$.getJSON(url,function(json){
				seasonHTML += '<div class="season-select"><select name="Season" id="season" onchange="changeSeason();">';
				
				//build the option list for the seasons
				$.each(json, function(i,data){
					weekmap[data.season.id] = data.id;
					
					seasonHTML += '<option value="';
					seasonHTML += data.season.id;
					seasonHTML += '"';
					if (i==0) {
						seasonHTML += 'selected="selected"';
					}
					seasonHTML += '>';
					seasonHTML += data.leagueName;
					seasonHTML += '</option>';
				});
				
				seasonHTML += '</select></div>';
				
				//append the html to the source
				$("#sn-view").append(seasonHTML);
				
				changeSeason();
				
				
				
			});
						
	
			$("#sn-view").append(myHTML);
			
			
			
			break;
			
			
		case 'view':
			viewPicks();
			break;
		case 'standings':
		
			
			// CRETE NEW SUBNAV 
			myHTML += '<div class="standingToggle"><div class="standingButton selected" id="button_0" onmousedown="showSpan(0);">1 - 6</div><div class="standingButton" id="button_1" onmousedown="showSpan(1);">7 - 12</div><div class="standingButton" id="button_2" onmousedown="showSpan(2);">13 - 17</div></div>';
			$("#sn-view").append(myHTML);
		
			viewStandings();
			break;
	}
	
	
}



function changeSeason(){
	
	//get the selected season number
	season = $("#sn-view").find("#season").val();
	
	//clear the old div
	$("#week-container").remove();
	
	//build the server url for retrieving the weeks based on the season
	url = server + '/weeks/seasonid/'+season;				
	
	var weekHTML = "";
	$.getJSON(url,function(json){
		weekHTML += '<div id="week-container"><select name="Week" id="week" onchange="makePicks();">';
		
		//loop over the weeks
		$.each(json, function(i,data){
			weekHTML += '<option value="';
			weekHTML += data.id;
			weekHTML += '"';
			if (i == 0)	
			{
				weekHTML += 'selected="selected"';
			}
			weekHTML += '>Week ';
			weekHTML += data.weekNumber;
			weekHTML += '</option>';
							
		});
		
		weekHTML += '</select></div>';
		
		//add the week select to the sub nav
		$("#sn-view").append(weekHTML);
		
		//set the week variable based on the selection
		week = $("#sn-view").find("#week").val();
		
		//build the make picks
		makePicks();
	});
}

function makePicks(){
		
	var week = $("#week").val();
	league = weekmap[season];
	url = server + '/picks/leagueid/'+league+'/weekid/'+week;
	$("#results").text("");
	$("#point-total").text('searching...');
	
	var myHTML = "";
	var points = 0;

	// get the json file
	$.getJSON(url,function(json){
		if ( json.length == 0 ) {
			//no picks for the week
		}
		else
		{
		$.each(json, function(i,data){
			
			
			 myHTML += '<div class="gamebox"><div class="gametime">'+data.game.gameStartDisplay+'</div>'	;
			 
				 
			 // DETERMINE IF WIN OR NOT
			 if(data.winnerStatus == 1){
				  
				  points += data.winValue;
				  
				  if(data.weight == 2){
				 	myHTML += '<div class="scoreboard shadow win double">';
			 	  } else {
					myHTML += '<div class="scoreboard shadow win">';  
				  }
				  
			 } else {
				 
				if(data.weight == 2){
					myHTML += '<div class="scoreboard shadow loss double">';
				} else {
					myHTML += '<div class="scoreboard shadow loss">';
				}
				
			 }
			 
			 // CURRENT PICK
			 
			 if(data.fav){
				myHTML += '<div class="pickbox"><div class="pick '+data.game.fav.shortName+'"></div></div>'; 
			 } else {
				myHTML += '<div class="pickbox"><div class="pick '+data.game.dog.shortName+'"></div></div>'; 
			 }
			 
			 var winner = "";
			 
			 if(data.fav && (data.winnerStatus == 1) ){
				 winner = "f";
			 } else if (data.dog && (data.winnerStatus == 2) ) {
				winner = "f";
			 } else {
				 winner = "d"; 
			 }
			 
			 var favRow = "";
			 var dogRow = "";
			 
			 // FAVORITE
			 if(winner == "f"){
				 favRow += '<div class="scorerow">';
			 } else {
				 favRow += '<div class="scorerow loser">';
			 }
			 
			 favRow += '<div class="teamname">' + data.game.fav.fullTeamName + '<span class="spread">(+' + data.game.spread + ')</span></div><div class="score">' + data.game.favScore + '</div></div>';
			 
			 // UNDERDOG
			 if(winner == "d"){
				 dogRow += '<div class="scorerow">';
			 } else {
				 dogRow += '<div class="scorerow loser">';
			 }
			 
			 dogRow += '<div class="teamname">' + data.game.dog.fullTeamName + '</div><div class="score">' + data.game.dogScore + '</div></div>';
			 
			 if(data.game.favHome){
				 myHTML += dogRow;
				 myHTML += favRow;
			 } else { 
				 myHTML += favRow;
  			 	 myHTML += dogRow;
			 }
			 
			 
			 
			 // CLOSE BOARD AND BOX
			 myHTML += '<div style="clear:both;"></div></div></div>';
		});
		}
		
		$("#results").append(myHTML);
		$("#point-total").text('+'+points+' points');
		document.getElementById("loader-page").style.visibility = "hidden";
	});
	
}



function viewPicks(){
	
	
	
	
	// hide loader
	document.getElementById("loader-page").style.visibility = "hidden";
}

function viewStandings(){
	
	url = server + '/standing/leagueid/'+league;
	var myHTML = "";
	
	// get the json file
	$.getJSON(url,function(json){
		
		$.each(json, function(i,data){
			
			myHTML += '<div class="standingsRow shadow">';
			myHTML += '<div class="standingsName">' + data.player.username + '</div>';
			myHTML += '<div class="standingsScore">' + data.numberOfWins + '</div>';
			
				myHTML += '<div class="standingWeeks"><div class="standingGroup sg0">'
				for(g=1; g<7; g++){
					points = data.weekTotal[g].wins;
					if(data.weekTotal[g].winner){
						myHTML+= '<div class="weekScore winner">'+points+'</div>';
					} else {
						myHTML+= '<div class="weekScore">'+points+'</div>';
					}
					
				}
				
				myHTML += '</div><div class="standingGroup sg1 hide">';
				for(g=7; g<13; g++){
					points = data.weekTotal[g].wins;
					if(data.weekTotal[g].winner){
						myHTML+= '<div class="weekScore winner">'+points+'</div>';
					} else {
						myHTML+= '<div class="weekScore">'+points+'</div>';
					}
				}
				
				myHTML += '</div><div class="standingGroup sg2 hide">';
				
				for(g=13; g<18; g++){
					points = data.weekTotal[g].wins;
					if(data.weekTotal[g].winner){
						myHTML+= '<div class="weekScore winner">'+points+'</div>';
					} else {
						myHTML+= '<div class="weekScore">'+points+'</div>';
					}
				}
				
				myHTML += '</div></div><div style="clear:both;"></div>';
			
			
			myHTML += '</div>';
			
			
			
		});
		$("#results").append(myHTML);
	});
	
	
	
	// hide loader
	document.getElementById("loader-page").style.visibility = "hidden";
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