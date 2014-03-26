package com.makeurpicks.web.ws;

import info.makeyourpicks.model.Game;
import info.makeyourpicks.model.LeagueType;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	
	@POST
	@Path("/week")
//	@PreAuthorize("hasRole('ROLE_HF_USER')")
	public Response createWeek(Week week)
	{
		long profileId = getPlayerIdFromSecurityContext();
		try
		{			
			gameManager.insertWeek(week);
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
	@Path("/teams")
//	@PreAuthorize("hasRole('ROLE_HF_USER')")
	public Response getTeams()
	{
		try
		{			
			return buildSuccessResponse(teamManager.getTeamsByLeagueType(new LeagueType(1)));
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
			gameManager.createUpdateGame(game);
			return buildSuccessResponse(game);
		}
		catch (Exception exception)
		{
			return handleException(exception);
		}
	}
}
