package info.makeyourpicks.web.league.panels;

import info.makeyourpicks.model.League;
import info.makeyourpicks.web.layout.AbstractBasePanel;
import info.makeyourpicks.web.validators.PercentValidator;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class LeagueDetailsPanel extends AbstractBasePanel
{
	public LeagueDetailsPanel(String id, IModel<League> model)
	{
		super(id);
		

		final League activeLeague = model.getObject();
		//Iterator<Player> players = playerManager.getAllPlayers(getActiveLeagueInfo().getLeague()).iterator();
//		Iterator<Player> players = playerManager.getPlayersInLeague(activeLeague).iterator();
		
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackpanel");
		add(feedbackPanel);
		
		IModel<League> activeLeagueModel = new CompoundPropertyModel<League>(new LoadableDetachableModel<League>()
		{

			@Override
			protected League load()
			{
				return getActiveLeague();
			}
			
		});
		setDefaultModel(activeLeagueModel);
		
		//1st - 5th place percentages
		final RequiredTextField<Integer> firstPlacePercent = new RequiredTextField<Integer>("firstPlacePercent");
		firstPlacePercent.add(new RangeValidator<Integer>(1, 100));
		
		final RequiredTextField<Integer> secondPlacePercent = new RequiredTextField<Integer>("secondPlacePercent");
		secondPlacePercent.add(new RangeValidator<Integer>(0,99));
		
		final RequiredTextField<Integer> thirdPlacePercent = new RequiredTextField<Integer>("thirdPlacePercent");
		thirdPlacePercent.add(new RangeValidator<Integer>(0,98));
		
		final RequiredTextField<Integer> fourthPlacePercent = new RequiredTextField<Integer>("fourthPlacePercent");
		fourthPlacePercent.add(new RangeValidator<Integer>(0, 97));
		
		final RequiredTextField<Integer> fifthPlacePercent = new RequiredTextField<Integer>("fifthPlacePercent");
		fifthPlacePercent.add(new RangeValidator<Integer>(0,96));		
		
		Form<League> leagueUpdateForm = new Form<League>("leagueUpdateForm", activeLeagueModel)
		{

			@Override
			protected void onSubmit() {
				League league = getModelObject();
				leagueManager.createOrUpdateLeague(league);
			}
			
		};
		leagueUpdateForm.add(new PercentValidator(firstPlacePercent, secondPlacePercent, thirdPlacePercent, fourthPlacePercent, fifthPlacePercent));
		
		Button submitButton = new Button("submit");
		
		//max number of players
		RequiredTextField<Integer> maxNumberOfPlayers = new RequiredTextField<Integer>("maxSize", Integer.class);
		maxNumberOfPlayers.add(new RangeValidator<Integer>(1, 999999));
		
		RequiredTextField<Double> entryFee = new RequiredTextField<Double>("entryFee");
		
		RequiredTextField<Double> weeklyFee = new RequiredTextField<Double>("weeklyFee");
		
		//name of league
		RequiredTextField<String> leagueName = new RequiredTextField<String>("leagueName");
		leagueName.add(new StringValidator.LengthBetweenValidator(3, 50));
		
		//password for league
		TextField<String> password = new TextField<String>("password");
		password.add(new StringValidator.LengthBetweenValidator(3, 50));
		
		
		Label leagyeType = new Label("season.displaySeason");
		Label numberOfPeople = new Label("numberPeople", String.valueOf(leagueManager.getNumberOfPlayersInLeague(activeLeague)));
		Label admin = new Label("admin.username");
		Label spreads = new Label("spreads");
        final Label active = new Label("leagueActive", activeLeague.isActive() ? "Active" : "Not Active");
        
        PlayersInLeaguePanel playersInLeaguePanel = new PlayersInLeaguePanel("leaguePlayersPanel", new CompoundPropertyModel<League>(new LoadableDetachableModel<League>(){

			@Override
			protected League load()
			{
				return activeLeague;
			}
        	
        }));
        
        
        add(leagueUpdateForm);
        add(playersInLeaguePanel);
        
        leagueUpdateForm.add(firstPlacePercent);
        leagueUpdateForm.add(secondPlacePercent);
        leagueUpdateForm.add(thirdPlacePercent);
        leagueUpdateForm.add(fourthPlacePercent);
        leagueUpdateForm.add(fifthPlacePercent);
        leagueUpdateForm.add(maxNumberOfPlayers);
        leagueUpdateForm.add(entryFee);
        leagueUpdateForm.add(weeklyFee);
        leagueUpdateForm.add(leagueName);
        leagueUpdateForm.add(password);
        leagueUpdateForm.add(leagyeType);
        leagueUpdateForm.add(numberOfPeople);
        leagueUpdateForm.add(admin);
        leagueUpdateForm.add(spreads);
        leagueUpdateForm.add(active);
        leagueUpdateForm.add(submitButton);
        
        if (!getActiveLeague().getAdmin().equals(getPlayer()))
        {
        	firstPlacePercent.setEnabled(false);
        	secondPlacePercent.setEnabled(false);
        	thirdPlacePercent.setEnabled(false);
        	fourthPlacePercent.setEnabled(false);
        	fifthPlacePercent.setEnabled(false);
        	maxNumberOfPlayers.setEnabled(false);
        	weeklyFee.setEnabled(false);
        	entryFee.setEnabled(false);
        	leagueName.setEnabled(false);
        	password.setEnabled(false);
        	submitButton.setVisible(false);
        }

        
		
	
	}
}
