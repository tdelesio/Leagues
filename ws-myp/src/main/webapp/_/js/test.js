// Testing GITHUB again
// var server = "http://localhost:8080/ws/rest";
var league = new String();
var week = new Number();
var playerID = new Number();
var weekmap = {};
function getNav() {
	// CLEAR AND LOAD
	document.getElementById("loader-page").style.visibility = "visible";
	// $("#results").text("");
	$("#sn-view").text("");
	var myHTML = "";
	var seasonHTML = "";
	// POINTS
	myHTML += '<div class="points"><div id="point-total"></div></div>';
	// create the variables
	// call to get the leagues that the player is in
	// url = server + '/leagues';
	url = getUrl('/leagues');
	$
			.getJSON(
					url,
					function(json) {
						seasonHTML += '<div class="season-select"><select name="Season" id="season" onchange="changeSeason();">';
						// build the option list for the seasons
						$.each(json, function(i, data) {
							weekmap[data.id] = data.season.id;
							seasonHTML += '<option value="';
							seasonHTML += data.id;
							seasonHTML += '"';
							if (i == 0) {
								seasonHTML += 'selected="selected"';
							}
							seasonHTML += '>';
							seasonHTML += data.leagueName;
							seasonHTML += '</option>';
						});
						seasonHTML += '</select></div>';
						// append the html to the source
						$("#season-view").html(seasonHTML);
						changeSeason();
					}).error(function() {
				logout();
			});
}
function changeSeason() {
	// get the selected season number
	season = $("#settings-panel").find("#season").val();
	$("#player-container").remove();
	// clear the old div
	$("#week-container").remove();
	// build the server url for retrieving the weeks based on the season
	// url = server + '/weeks/seasonid/'+season;
	url = getUrl('/weeks/seasonid/' + weekmap[season]);
	var weekHTML = "";
	$
			.getJSON(
					url,
					function(json) {
						weekHTML += localStorage['username'];
						weekHTML += "&nbsp;<div id='week-container'><select name='Week' id='week' onchange='getCurrentPage();'>";
						// loop over the weeks
						$.each(json, function(i, data) {
							weekHTML += '<option value="';
							weekHTML += data.id;
							weekHTML += '"';
							if (i == 0) {
								weekHTML += 'selected="selected"';
							}
							weekHTML += '>Week ';
							weekHTML += data.weekNumber;
							weekHTML += '</option>';
						});
						weekHTML += '</select></div>';
						// add the week select to the sub nav
						$("#mn-view").append(weekHTML);
						getPage('make');
						// if (callback)
						// {
						// callback();
						// }
					});
}
function getSettings() {
	/* CLOSE PANEL */
	if ($("#settings-panel").css("margin-top") == "0px") {
		$("#settings-panel").css("margin-top", "-150px");
		$(".nav").css("margin-top", "0px");
		$("#results").css("padding-top", "90px");
		$("li#nav-settings a").css("color", "#FFB415");
		/* OPEN PANEL */
	} else {
		$("#settings-panel").css("margin-top", "0px");
		$(".nav").css("margin-top", "150px");
		$("#results").css("padding-top", "240px");
		$("li#nav-settings a").css("color", "#FFFFFF");
	}
}
function getCurrentPage() {
	if ($("#nav-make").hasClass("active")) {
		getPage("make");
	}
	if ($("#nav-view").hasClass("active")) {
		getPage("view");
	}
	if ($("#nav-standings").hasClass("active")) {
		getPage("standings");
	}
}
function getPage(page) {
	$("#results").text("");
	$("#sn-view").html("");
	$("#player-container").remove();
	season = $("#settings-panel").find("#season").val();
	// var week = $("#sn-view").find("#week").val();
	$("#navrow li").each(function() {
		$(this).removeClass('active');
	});
	p = "#nav-" + page;
	$(p).addClass("active");
	switch (page) {
	// this is the make pics page
	case 'make':
		// build the make picks
		makePicks();
		break;
	case 'view':
		viewPicks();
		break;
	case 'standings':
		viewStandings();
		break;
	}
}
function makePicks() {
	var week = $("#week").val();
	var season = $("#season").val();
//	league = weekmap[season];
	// url = server + '/picks/leagueid/'+league+'/weekid/'+week;
	url = getUrl('/picks/leagueid/' + season + '/weekid/' + week);
	$("#results").text("");
	$("#point-total").text('searching...');
	var myHTML = "";
	var gamemap = {};
	// get the json file
	$.getJSON(url, function(picks) {
		// var gamesurl = server + '/games/weeked/'+week;
		var gamesurl = getUrl('/games/weeked/' + week);
		$.getJSON(gamesurl, function(games) {
			var template = _.template($('#container').html());
			$("#results").append(template({
				games : games,
				picks : picks,
				update : true
			}));
			$("#loader-page").css("visibility", "hidden");
		});
	});
}
function viewPicks() {
	// hide loader
	$("#loader-page").css("visibility", "hidden");
	// clear the old div
	// $("#player-container").text("");
	var week = $("#week").val();
	var season = $("#season").val();
	league = weekmap[season];
	// build the server url for retrieving the weeks based on the season
	// url = server + '/player/leagueid/'+league+'/weekid/'+week;
	url = getUrl('/player/leagueid/' + season + '/weekid/' + week);
	var html = "";
	$
			.getJSON(
					url,
					function(json) {
						html += '<div id="player-container"><select name="Player" id="player" onchange="changePlayer();">';
						// loop over the weeks
						$.each(json, function(i, data) {
							html += '<option value="';
							html += data.id;
							html += '"';
							if (i == 0) {
								html += 'selected="selected"';
							}
							html += '>';
							html += data.username;
							html += '(';
							html += data.wins;
							html += ')';
							html += '</option>';
						});
						html += '</select></div>';
						// add the week select to the sub nav
//						$("#settings-panel").append(html);
						$("#mn-view").append(html);
						changePlayer();
					});
}
function changePlayer() {
	$("#results").text('');
	var player = $("#player").val();
	var week = $("#week").val();
	league = weekmap[season];
	// url = server +
	// '/picks/leagueid/'+league+'/weekid/'+week+'/player/'+player;
	url = getUrl('/picks/leagueid/' + season + '/weekid/' + week + '/player/'
			+ player);
	$.getJSON(url, function(picks) {
		// var gamesurl = server + '/games/weeked/'+week;
		var gamesurl = getUrl('/games/weeked/' + week);
		$.getJSON(gamesurl, function(games) {
			var template = _.template($('#container').html());
			$("#results").append(template({
				games : games,
				picks : picks,
				update : false
			}));
			//document.getElementById("loader-page").style.visibility = "hidden";
		});
	});
}
function updatePick(pickid, gameid, teamid, otherteamid, s_shortname,
		uns_shortname, fav_dog, index) {
	// var url = server + "/pick";
	var url = getUrl("/pick");
	var season = $("#season").val();
//	league = weekmap[season];
	var submitData = {};
	submitData['team'] = teamid;
	submitData['league'] = season;
	submitData['week'] = $('#week').val();
	submitData['game'] = gameid;
submitData['id'] = pickid;
$.ajax({
		type : "PUT",
		url : url,
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(submitData),
		success : function(res) {
			$("." + index + " .pick-selection i").addClass("hide");
			$("." + fav_dog + "." + index + " .pick-selection i").removeClass(
					"hide");
			//pickbox.removeClass(uns_shortname);
			//pickbox.addClass(s_shortname);
			var favscorerow = $(".scorerow.fav." + index);
			favscorerow.attr('onclick', '');
			var dogscorerow = $(".scorerow.dog." + index);
			dogscorerow.attr("onclick", '');
			if (fav_dog == 'fav') {
				dogscorerow.attr("onclick", "updatePick(" + res.id + ", "
						+ gameid + ", " + otherteamid + ", " + teamid + ", '"
						+ uns_shortname + "', '" + s_shortname + "', 'dog',"
						+ index + ")");
				favscorerow.attr("onclick", "doublePick("+res.id+", "+index+", 'fav')");
			} else {
				favscorerow.attr("onclick", "updatePick(" + res.id + ", "
						+ gameid + ", " + otherteamid + ", " + teamid + ", '"
						+ uns_shortname + "', '" + s_shortname + "', 'fav',"
						+ index + ")");
				dogscorerow.attr("onclick", "doublePick("+res.id+", "+index+", 'dog')");
			}
		},
		error : function(res) {
			$('#result').text(JSON.stringify(res));
		}
	});
}
function makePick(gameid, teamid, otherteamid, s_shortname, uns_shortname,
		fav_dog, index) {
	
	// var url = server + "/pick";
	var url = getUrl("/pick");
	var season = $("#season").val();
	league = weekmap[season];
	var submitData = {};
	submitData['team'] = teamid;
	submitData['league'] = season;
	submitData['week'] = $('#week').val();
submitData['game'] = gameid;

$.ajax({
		type : "POST",
		url : url,
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(submitData),
		success : function(res) {
			var pickbox = $(".pick." + index);
			// pickbox.removeClass(uns_shortname);
			pickbox.addClass(s_shortname);
			var favscorerow = $(".scorerow.fav." + index);
			favscorerow.attr('onclick', '');
			favscorerow.removeClass("pick-selection");

			var dogscorerow = $(".scorerow.dog." + index);
			dogscorerow.removeClass("pick-selection");
			dogscorerow.attr("onclick", '');
			
			//pick was a fav
			if (fav_dog == 'fav') {
				//add the update pick to the dog then
				dogscorerow.attr("onclick", "updatePick(" + res.id + ", "
						+ gameid + ", " + otherteamid + ", " + teamid + ", '"
						+ uns_shortname + "', '" + s_shortname + "', 'dog',"
						+ index + ")");
				
				//update the ui to show the checkbox to show pic was made
				favscorerow.children(":first").children("i").removeClass("hide");

				//change url to double
				favscorerow.attr("onclick", "doublePick("+res.id+", "+index+", 'fav')");
			} else {
				//pick was a dog
				//add the update link to change back to a fav
				favscorerow.attr("onclick", "updatePick(" + res.id + ", "
						+ gameid + ", " + otherteamid + ", " + teamid + ", '"
						+ uns_shortname + "', '" + s_shortname + "', 'fav',"
						+ index + ")");
				//mark the ui to show the checkbox to show the pic was made
				dogscorerow.children(":first").children("i").removeClass("hide");
				
				//add the url to double the pick
				dogscorerow.attr("onclick", "doublePick("+res.id+", "+index+", 'dog')");
			}
		},
		error : function(res) {
			$('#result').text(JSON.stringify(res));
		}
	});
}

function doublePick(pickid, index, fav_dog) {
	
	var url = getUrl("/double/"+pickid);
	
	$.ajax({
		type : "PUT",
		url : url,
		contentType : "application/json",
		dataType : "json",
		//data : JSON.stringify(submitData),
		success : function(res) {
			
			$("i").removeClass("doubled");
			
			var favscorerow = $(".scorerow.fav." + index);
			var dogscorerow = $(".scorerow.dog." + index);
			
			if (fav_dog == 'fav') {
//				favscorerow.children(":first").prepend("<span class='hasdouble'>D</span>");
//				favscorerow.children(":first").children("i").removeClass("fa-check-circle-o");
				favscorerow.children(":first").children("i").addClass("doubled");
//				favscorerow.children(":first").children("i").html("X2");
			}
			else
			{
//				dogscorerow.children(":first").prepend("<span class='hasdouble'>D</span>");
//				dogscorerow.children(":first").children("i").removeClass("fa-check-circle-o");
				dogscorerow.children(":first").children("i").addClass("doubled");
//				dogscorerow.children(":first").children("i").html("X2");
			}
	//		hasdouble
		},
		error : function(res) {
			$('#result').text(JSON.stringify(res));
		}
	});
}

function viewStandings() {
//	league = weekmap[season];
	// url = server + '/winsummary/leagueid/'+league;
	url = getUrl('/winsummary/leagueid/' + season);
	var myHTML = "<div id='standings'>";
	// get the json file
	$.getJSON(url, function(json) {
		$.each(json, function(i, data) {
			myHTML += '<div class="row">';
			myHTML += '<div class="col-md-2 col-xs-6 username">'
					+ data.player.username + '</div>';
			myHTML += '<div class="col-md-1 col-xs-6 wins">'
					+ data.numberOfWins + '</div>';
			myHTML += '<div class="col-xs-12 col-md-6">';
			for ( var g = 1; g < 18; g++) {
				var weekTotal = data.weekTotal[g];
				points = data.weekTotal[g].wins;
				if (data.weekTotal[g].winner) {
					myHTML += '<div class="weekScore winner">' + points
							+ '</div>';
				} else {
					myHTML += '<div class="weekScore">' + points + '</div>';
				}
			}
			myHTML += '</div><div style="clear:both;"></div>';
			myHTML += '</div>';
		});
		myHTML += "</div>";
		$("#results").append(myHTML);
	});
	// hide loader
	document.getElementById("loader-page").style.visibility = "hidden";
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
// START THE APP
function logout() {
	localStorage['tgt'] = null;
	window.location.replace('login.html');
}
$(document).ready(function() {
	$(".autofill").each(function() {
		v = $(this).attr("name");
		$(this).attr("value", v);
	});
	if (window.localStorage['tgt']) {
		getNav();
	} else {
		window.location.replace('login.html');
	}
});
