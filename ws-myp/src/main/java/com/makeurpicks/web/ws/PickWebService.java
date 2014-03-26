package com.makeurpicks.web.ws;

import info.makeyourpicks.model.League;
import info.makeyourpicks.model.PickUI;
import info.makeyourpicks.model.Picks;
import info.makeyourpicks.model.Player;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.Week;
import info.makeyourpicks.service.GameManager;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.service.PicksManager;
import info.makeyourpicks.service.PlayerManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.delesio.exception.ValidationException;
import com.wordnik.swagger.annotations.Api;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Api(value="/", description="These are web service that are assiocated with tasks")
public class PickWebService extends AbstractMYPWebService {

	@Autowired
	private PlayerManager playerManager;
	
	@Autowired
	private PicksManager picksManager;
	
	@Autowired
	private LeagueManager leagueManager;
	
	@Autowired 
	private GameManager gameManager;
	
	@GET
	@Path("/leagues")
//	@PreAuthorize("hasRole('ROLE_HF_USER')")
	public Response getLeagues()
	{
		try
		{
			Player player = new Player(getPlayerIdFromSecurityContext());
			return buildSuccessResponse(leagueManager.getLeaguesForPlayerTX(player));
		}
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
	@Path("/picks/leagueid/{leagueid}/weekid/{weekid}")
//	@PreAuthorize("hasRole('ROLE_HF_USER')")
	public Response getPicksByLeagueAndWeek(@PathParam("leagueid")long leagueid, @PathParam("weekid")long weekid)
	{
		long profileId = getPlayerIdFromSecurityContext();
		try
		{
			League league = new League();
			league.setId(leagueid);
			Week week = new Week();
			week.setId(weekid);
			Player player = new Player(profileId); 
			
			return buildSuccessResponse(picksManager.getPicksByPlayerLeagueAndWeekTX(player, league, week));
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
	@Path("/standing/leagueid/{leagueid}")
//	@PreAuthorize("hasRole('ROLE_HF_USER')")
	public Response getWinSummary(@PathParam("leagueid")long leagueid)
	{
//		String profileId = getEmailFromSecurityContext();
		try
		{
			League league = new League();
			league.setId(leagueid);

			
			return buildSuccessResponse(picksManager.getWinSummaryTX(league));
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
	
	@POST
	@Path("/pick")
	public Response makePick(PickUI pickUI)
	{
		try
		{
			long profileId = getPlayerIdFromSecurityContext();
			Picks picks = new Picks(profileId, pickUI);
			
//			picksManager.insertPlayerPickTX(picks, profileId);
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
	
	@PUT
	@Path("/pick")
	public Response updatePick(PickUI pickUI)
	{
		try
		{
			long profileId = getPlayerIdFromSecurityContext();
			Picks picks = new Picks(profileId, pickUI);
			
			picksManager.updatePlayerPickTX(picks, profileId);
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
	
	
	@PUT
	@Path("/double/{pickid}")
	public Response makePick(long pickId)
	{
		try
		{
			long profileId = getPlayerIdFromSecurityContext();
			Picks picks = new Picks(pickId, profileId);
			
//			picksManager.updateDoublePick(picks, false);
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
	
}
