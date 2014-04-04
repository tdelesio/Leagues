package com.makeurpicks.web.ws;

import java.util.Date;
import java.util.StringTokenizer;

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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/admin")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
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
	@Path("/leagues")
//	@PreAuthorize("hasRole('ROLE_HF_USER')")
	public Response getLeagues()
	{
		try
		{
			Player player = new Player(getPlayerIdFromSecurityContext());
			return buildSuccessResponse(leagueManager.getLeaguesTX());
		}
		catch (Exception exception)
		{
			return handleException(exception);
		}
	}
	
	@POST
	@Path("/week")
//	@PreAuthorize("hasRole('ROLE_HF_USER')")
	public Response createWeek(Week week)
	{
		long profileId = getPlayerIdFromSecurityContext();
		try
		{			
			week.setWeekStart(new Date());
			gameManager.insertWeekTX(week);
			return buildSuccessResponse(true);
		}
//		catch (ValidationException validationException)
//		{
//			return handleValidationException(validationException);
//		}
		catch (Exception exception)
		{
			return handleException(exception);
		}
	}
	
	@GET
	@Path("/weeks/seasonid/{seasonid}")
//	@PreAuthorize("hasRole('ROLE_HF_USER')")
	public Response getWeeksBySeason(@PathParam("seasonid")long seasonid)
	{
		try
		{
			
			Season season = new Season(seasonid);
			return buildSuccessResponse(gameManager.getWeeksBySeasonTX(season));
		}
		catch (Exception exception)
		{
			return handleException(exception);
		}
	}
	
	@GET
	@Path("/teams")
//	@PreAuthorize("hasRole('ROLE_HF_USER')")
	public Response getTeams()
	{
		try
		{			
			return buildSuccessResponse(teamManager.getTeamsByLeagueTypeTX(new LeagueType(1)));
		}
		catch (Exception exception)
		{
			return handleException(exception);
		}
	}
	
	@POST
	@Path("/game")
//	@PreAuthorize("hasRole('ROLE_HF_USER')")
	public Response createGame(Game game)
	{
		try
		{		
			DateTime dateTime = new DateTime();
			
			StringTokenizer tokenizer = new StringTokenizer(game.getGameStartTime(), ":");
			String hour = (String)tokenizer.nextElement();
			String min = (String)tokenizer.nextElement();
			
			tokenizer = new StringTokenizer(game.getGameStartDate(), "/");
			String month = (String)tokenizer.nextElement();
			String day = (String)tokenizer.nextElement();
			String year = (String)tokenizer.nextElement();
			
			dateTime = dateTime.withTime(Integer.parseInt(hour), Integer.parseInt(min), 0, 0).withDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
			game.setGameStart(dateTime.toDate());
			gameManager.insertGameTX(game);
			return buildSuccessResponse(game);
		}
		catch (Exception exception)
		{
			return handleException(exception);
		}
	}
	
	@GET
	@Path("/games/weekid/{weekid}")
//	@PreAuthorize("hasRole('ROLE_HF_USER')")
	public Response createGame(@PathParam("weekid")long weekid)
	{
		try
		{			
			Week week = new Week(weekid);
			return buildSuccessResponse(gameManager.getGamesByWeekTX(week));
		}
		catch (Exception exception)
		{
			return handleException(exception);
		}
	}
}
