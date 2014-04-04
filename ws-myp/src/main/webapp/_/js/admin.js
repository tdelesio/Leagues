var server = "http://localhost:8080/ws/rest/admin";
var league = new String();
var week = new Number();
var playerID = new Number();
var weekmap = {};
// var template = '';
// var seasonjson = '';
function getPage() {

	// CLEAR AND LOAD
	// document.getElementById("loader-page").style.visibility = "visible";

	$("#result").text("");
	$("#view").text("");
	$("#subnav").text("");

	var template = _.template($('#container').html());

	// load the subnav
	url = server + '/leagues';
	$.getJSON(url, function(json) {
		$("#subnav").append(template({
			data : json
		}));

		changeSeason();
	});

}

function changeSeason() {
	var page = $("#page").val();

	switch (page) {

	// this is the make pics page
	case 'setupweek':

		$("#view").load('setupWeek.html', function() {
			
			$("weeks").text("");
			
			var selectedSeason = $("#season").val();
			$("#seasonid").val(selectedSeason);

			url = server + '/weeks/seasonid/' + selectedSeason;

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
				$("#weeks").append(weekHTML);
				
				//set the week variable based on the selection
				week = $("#weeks").find("#week").val();
				
				//setup the game entry page
				var addgametemplate = _.template($('#addgame').html());
				// load the subnav
				url = server + '/teams';
				$.getJSON(url, function(json) {
					$("#view").append(addgametemplate({
						teams : json
					}));

					//set the hidden variable
					$("#weekid").val(week);
				});
				
		
				//get all the games
				var gamestemplate = _.template($('#games').html());
//				console.log($('#games').html());
				var gameUrl = server + '/games/weekid/'+week;
				$.getJSON(gameUrl,function(json){
					$("#view").append(gamestemplate({
						games : json
					}));
					
				});
			});

		});
		break;

	case 'createweek':

		$("#view").load('createWeek.html', function() {
			var selectedSeason = $("#season").val();
			$("#seasonid").val(selectedSeason);

		});
		// alert($("#view").find("#season").val());

		break;
	case 'creategame':

		// CRETE NEW SUBNAV

		break;
	}
}

function createWeek() {
	var url = server + "/week";
	$.ajax({
		type : "POST",
		url : url,
		contentType : "application/json",
		dataType : "json",
		data : $('form').serializeObject(),
		success : function(res) {
			$('#result').text(JSON.stringify(res));
		},
		error : function(res) {
			$('#result').text(JSON.stringify(res));
		}
	});
}

function addNewGame() {
	
	
	if( $("#favhome").is(":checked") == true ) {
		$("#favhome").attr('value','true');
        } else {
        	$("#favhome").prop('checked',true);
            //DONT' ITS JUST CHECK THE CHECKBOX TO SUBMIT FORM DATA    
        	$("#favhome").attr('value','false');
        }
	
	var url = server + "/game";
	$.ajax({
		type : "POST",
		url : url,
		contentType : "application/json",
		dataType : "json",
		data : $('form').serializeObject(),
		success : function(res) {
			$('#result').text(JSON.stringify(res));
		},
		error : function(res) {
			$('#result').text(JSON.stringify(res));
		}
	});
}

function showSpan(ID) {

	// TOGGLE NAV
	for (g = 0; g < 3; g++) {
		$("#button_" + g).removeClass("selected");
		$(".sg" + g).addClass("hide");
	}

	$("#button_" + ID).addClass("selected");
	$(".sg" + ID).removeClass("hide");

}

// $(function() {
// $('form').submit(function() {
// $('#result').text(JSON.stringify($('form').serializeObject()));
// return false;
// });
// });

// START THE APP

$(document).ready(function() {

	getPage();

});
