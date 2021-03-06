package com.makeurpicks.web.ws;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.service.GameManager;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.service.PicksManager;
import info.makeyourpicks.service.PlayerManager;
import info.makeyourpicks.service.TeamManager;

import java.util.Date;
import java.util.StringTokenizer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.tz.DateTimeZoneBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

@Path("/admin")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class AdminWebService extends AbstractMYPWebService {

	@Autowired
	private PlayerManager playerManager;

	@Autowired
	private PicksManager picksManager;

	@Autowired
	private LeagueManager leagueManager;

	@Autowired
	private GameManager gameManager;

	@Autowired
	private TeamManager teamManager;

	@GET
	@Path("/valid")
	@PreAuthorize("hasRole('admin')")
	public Response validateAdmin() {
		return buildSuccessResponse(true);
	}
	
	@GET
	@Path("/leagues")
	@PreAuthorize("hasRole('admin')")
	public Response getLeagues() {
		try {
			Player player = new Player(getPlayerIdFromSecurityContext());
			return buildSuccessResponse(leagueManager.getLeaguesTX());
		} catch (Exception exception) {
			return handleException(exception);
		}
	}

	@POST
	@Path("/week")
	@PreAuthorize("hasRole('admin')")
	public Response createWeek(Week week) {
		long profileId = getPlayerIdFromSecurityContext();
		try {
			week.setWeekStart(new Date());
			gameManager.insertWeekTX(week);
			return buildSuccessResponse(true);
		}
		// catch (ValidationException validationException)
		// {
		// return handleValidationException(validationException);
		// }
		catch (Exception exception) {
			return handleException(exception);
		}
	}

	@GET
	@Path("/weeks/seasonid/{seasonid}")
	@PreAuthorize("hasRole('admin')")
	public Response getWeeksBySeason(@PathParam("seasonid") long seasonid) {
		try {

			Season season = new Season(seasonid);
			return buildSuccessResponse(gameManager.getWeeksBySeasonTX(season));
		} catch (Exception exception) {
			return handleException(exception);
		}
	}

	@GET
	@Path("/teams")
	@PreAuthorize("hasRole('admin')")
	public Response getTeams() {
		try {
			return buildSuccessResponse(teamManager
					.getTeamsByLeagueTypeTX(new LeagueType(1)));
		} catch (Exception exception) {
			return handleException(exception);
		}
	}

	@POST
	@Path("/game")
	@PreAuthorize("hasRole('admin')")
	public Response createGame(Game game) {
		try {
			
			DateTime dateTime = parseDateTime(game);
			
			game.setGameStart(dateTime.toDate());
			gameManager.insertGameTX(game);
			return buildSuccessResponse(game);
		} catch (Exception exception) {
			return handleException(exception);
		}
	}
	
	@PUT
	@Path("/game")
	@PreAuthorize("hasRole('admin')")
	public Response updateGame(Game game) {
		try {
			DateTime dateTime = parseDateTime(game);
			game.setGameStart(dateTime.toDate());
			
			gameManager.updateScore(game);
			return buildSuccessResponse(game);
		} catch (Exception exception) {
			return handleException(exception);
		}
	}
	
	private DateTime parseDateTime(Game game)
	{
		DateTime dateTime = new DateTime(DateTimeZone.forID("America/New_York"));

		StringTokenizer tokenizer = new StringTokenizer(
				game.getGameStartTime(), ":");
		String hour = (String) tokenizer.nextElement();
		String min = (String) tokenizer.nextElement();

		tokenizer = new StringTokenizer(game.getGameStartDate(), "/");
		String month = (String) tokenizer.nextElement();
		String day = (String) tokenizer.nextElement();
		String year = (String) tokenizer.nextElement();

		int h = Integer.parseInt(hour);
		if (h >= 1 && h <= 10)
			h += 12;
		
		dateTime = dateTime.withTime(h,
				Integer.parseInt(min), 0, 0).withDate(
				Integer.parseInt(year), Integer.parseInt(month),
				Integer.parseInt(day));
		return dateTime;
	}

	@GET
	@Path("/games/weekid/{weekid}")
	@PreAuthorize("hasRole('admin')")
	public Response createGame(@PathParam("weekid") long weekid) {
		try {
			Week week = new Week(weekid);
			return buildSuccessResponse(gameManager.getGamesByWeekTX(week));
		} catch (Exception exception) {
			return handleException(exception);
		}
	}
	
	@GET
	@Path("/cache")
	@PreAuthorize("hasRole('admin')")
	public Response clearCache() {
		try {
			picksManager.clearPickCache();
			return buildSuccessResponse(true);
		} catch (Exception exception) {
			return handleException(exception);
		}
	}
	
}
