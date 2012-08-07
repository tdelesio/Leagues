package info.makeyourpicks.web.league.panels;

import info.makeyourpicks.LeagueTypesEnum;
import info.makeyourpicks.model.PickemTieBreakerEnum;
import info.makeyourpicks.model.League;
import info.makeyourpicks.model.LeagueType;
import info.makeyourpicks.model.Season;
import info.makeyourpicks.model.YesNoEnum;
import info.makeyourpicks.service.LeagueManager;
import info.makeyourpicks.web.layout.AbstractBasePanel;
import info.makeyourpicks.web.validators.PercentValidator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;

/**
 * @author PRC9041
 */
public abstract class CreateLeagueStep1Panel extends AbstractBasePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1476877036226178124L;
	
	@SpringBean(name="leagueManager")
	private LeagueManager leagueManager;
	
	private static Map<LeagueTypesEnum, String> pdMap;
	private static Map<PickemTieBreakerEnum, String> tieMap;
	
	public CreateLeagueStep1Panel(String id, final IModel<League> model)
	{
		super(id, model);
		setOutputMarkupId(true);
	
		//map for descriptions
		if (pdMap==null || tieMap == null)
		{
			 pdMap = new HashMap<LeagueTypesEnum, String>(5);
			 tieMap = new HashMap<PickemTieBreakerEnum, String>(5);
			 
			 pdMap.put(LeagueTypesEnum.PICKEM, "A pick'em league is where you choose every game every week. You can customize the pool to add a variety late in step 2.");
			 pdMap.put(LeagueTypesEnum.SUICIDE, "A sucide league is where you pick one team each week.  You can only pick a team once.  You get two loses...once you lose twice you are out of the pool.");
			 
			 tieMap.put(PickemTieBreakerEnum.FIFTY_FIFTY_SPLIT, "50% of the weeks winnings is split between the people that tied.  The other 50% is pushed to the following week.");
			 tieMap.put(PickemTieBreakerEnum.MONDAY_NIGHT_SCORE, "Everyone picks a monday night score of the combined teams and the closed to the score wins.");
			 tieMap.put(PickemTieBreakerEnum.PUSH_TO_NEXT_WEEK, "All the money is pushed to the following week if there are any ties.");
			 tieMap.put(PickemTieBreakerEnum.SPLIT, "The money is split between the people that tied.");
		}
			
		//css for page
		add(CSSPackageResource.getHeaderContribution("styles/createLeague.css"));
		
		//feedback panel
		add(new FeedbackPanel("feedbackPanel"));
		
		//container for step 2
		final WebMarkupContainer step2Container = new WebMarkupContainer("step2Container");
		step2Container.setVisible(false);
		step2Container.setOutputMarkupId(true);
		
		//description of the pool
		final Label poolDescription = new Label("poolDescription", "");
		poolDescription.setVisible(false);
		poolDescription.setOutputMarkupId(true);
		poolDescription.add(new SimpleAttributeModifier("clas", "description"));
		
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
		
		//container for percents
		final WebMarkupContainer percentContainer = new WebMarkupContainer("percentContiner");
		percentContainer.add(new SimpleAttributeModifier("class", "createRow"));
		
		//container for weekly fee
		final WebMarkupContainer weeklyFeeContainer = new WebMarkupContainer("weeklyFeeContainer");
		weeklyFeeContainer.add(new SimpleAttributeModifier("class", "createRow"));
		
		final WebMarkupContainer doubleContainer = new WebMarkupContainer("doubleContainer");
		doubleContainer.add(new SimpleAttributeModifier("class", "createRow"));
		
		final WebMarkupContainer tieBreakContainer = new WebMarkupContainer("tieBreakContainer");
		tieBreakContainer.add(new SimpleAttributeModifier("class", "createRow"));
		
		final WebMarkupContainer totalCostContainer = new WebMarkupContainer("totalCostContainer");
		totalCostContainer.add(new SimpleAttributeModifier("class", "createRow"));
		
		//drop down for league type selection
		List<Season> seasons = leagueManager.getCurrentSeasons();
		DropDownChoice<Season> leagueTypesDD = new DropDownChoice<Season>("season", seasons, new ChoiceRenderer("displaySeason", "id"));
		leagueTypesDD.setOutputMarkupId(true);
		leagueTypesDD.setRequired(true);
		leagueTypesDD.add(new AjaxFormComponentUpdatingBehavior("onChange")
		{

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				LeagueType leagueType = ((League)model.getObject()).getSeason().getLeagueType();
				poolDescription.setVisible(true);
				poolDescription.setDefaultModelObject(pdMap.get(LeagueTypesEnum.getByStatus(leagueType.getTypeOfLeague())));
				
				step2Container.setVisible(true);
				
				if (leagueType.getTypeOfLeague().equals(LeagueTypesEnum.SUICIDE.getLeagueType()))
				{
					percentContainer.setVisible(false);
					weeklyFeeContainer.setVisible(false);
					doubleContainer.setVisible(false);
					tieBreakContainer.setVisible(false);
					totalCostContainer.setVisible(false);
				}
				target.addComponent(getPage().get("step1Panel"));
			}
			
		});
		
		//max number of players
		RequiredTextField<Integer> maxNumberOfPlayers = new RequiredTextField<Integer>("maxSize", Integer.class);
		maxNumberOfPlayers.add(new RangeValidator<Integer>(1, 999999));
		
		//container for bank
		final WebMarkupContainer bankerContainer = new WebMarkupContainer("bankContainer");
		bankerContainer.setOutputMarkupId(true);
		bankerContainer.setVisible(false);
		
		//dropdown for banker
		DropDownChoice<YesNoEnum> banker = new DropDownChoice<YesNoEnum>("bankerEnum", Arrays.asList(YesNoEnum.values()));
		banker.add(new AjaxFormComponentUpdatingBehavior("onChange")
		{

			@Override
			protected void onUpdate(AjaxRequestTarget target) 
			{
				if (model.getObject().getBankerEnum().equals(YesNoEnum.YES))
				{
					bankerContainer.setVisible(true);
				}
				else
				{
					bankerContainer.setVisible(false);
				}
				
				target.addComponent(getPage().get("step1Panel"));
			}
			
		});
		
		//dropdown for double
		DropDownChoice<YesNoEnum> doubleEnabled = new DropDownChoice<YesNoEnum>("doubleEnabledEnum", Arrays.asList(YesNoEnum.values()));
		doubleEnabled.setRequired(true);
		
		//dropdown for spreads
		DropDownChoice<YesNoEnum> spreads = new DropDownChoice<YesNoEnum>("spreadsEnum", Arrays.asList(YesNoEnum.values()));
		spreads.setRequired(true);
		
		//display for week calculation
		double value = 5*17;
		final Label weekCalc = new Label("weekCalc", String.valueOf(value));
		weekCalc.setOutputMarkupId(true);
		
		//display for weekly fee
		double totalCostValue = (17*5)+35;
		final Label totalCost = new Label("totalCost", String.valueOf(totalCostValue));
		
		//entry fee
		RequiredTextField<Double> entryFee = new RequiredTextField<Double>("entryFee");
		entryFee.add(new AjaxFormComponentUpdatingBehavior("onBlur")
		{

			@Override
			protected void onUpdate(AjaxRequestTarget target) 
			{
				double weekFee = model.getObject().getWeeklyFee()*17;
				double entryFee = model.getObject().getEntryFee();
				double value = weekFee + entryFee;
				totalCost.setDefaultModelObject(String.valueOf(value));
				
				target.addComponent(getPage().get("step1Panel"));
			}
			
		});
		
		
		
		//input for weekly fee
		RequiredTextField<Double> weeklyFee = new RequiredTextField<Double>("weeklyFee");
		weeklyFee.add(new AjaxFormComponentUpdatingBehavior("onBlur")
		{

			@Override
			protected void onUpdate(AjaxRequestTarget target) 
			{
				double weekFee = model.getObject().getWeeklyFee()*17;
				double entryFee = model.getObject().getEntryFee();
				double value = weekFee + entryFee;
				totalCost.setDefaultModelObject(String.valueOf(value));
				weekCalc.setDefaultModelObject(String.valueOf(weekFee));
				
				target.addComponent(getPage().get("step1Panel"));
			}
			
		});
		
		//tie break rule description
		final Label tieBreakerDescription = new Label("tieBreakerDescription", tieMap.get(PickemTieBreakerEnum.SPLIT));
		tieBreakerDescription.setOutputMarkupId(true);
		tieBreakerDescription.add(new SimpleAttributeModifier("class", "description"));
		
		//tie break rule selectoin
		final DropDownChoice<PickemTieBreakerEnum> doubleType = new DropDownChoice<PickemTieBreakerEnum> ("doubleTypeEnum", Arrays.asList(PickemTieBreakerEnum.values()));
		doubleType.setRequired(true);
		doubleType.add(new AjaxFormComponentUpdatingBehavior("onChange")
		{

			@Override
			protected void onUpdate(AjaxRequestTarget target) 
			{
				tieBreakerDescription.setDefaultModelObject(tieMap.get(doubleType.getModelObject()));
				target.addComponent(getPage().get("step1Panel"));
			}
			
		});
		
		//name of league
		RequiredTextField<String> leagueName = new RequiredTextField<String>("leagueName");
		leagueName.add(new StringValidator.LengthBetweenValidator(3, 50));
		
		//password for league
		TextField<String> password = new TextField<String>("password");
		password.add(new StringValidator.LengthBetweenValidator(3, 50));
		
		//form for league creation
		Form<League> form = new Form<League>("step1Form", model)
		{

			@Override
			protected void onSubmit() {
//				setResponsePage(new CreateLeagueStep2Page(getModel()));
				
				League league = leagueManager.getLeague(getModelObject().getLeagueName());
				if (league != null)
				{
					error("League Name already exists.  Please choose another name");
				}
				else
				{				
					setResponsePage(getStep2Page(getModel()));
				}
			}

			@Override
			protected void onValidate() {
				League league = getModelObject();
				if (!league.doesPercentEqual100())
					error("1st, 2nd, 3rd, 4th, and 5th place percents must equal 100");
			}
		};
		form.add(new PercentValidator(firstPlacePercent, secondPlacePercent, thirdPlacePercent, fourthPlacePercent, fifthPlacePercent));
		
		//submit button
		Button submitButton = new Button("submit");
		
		/************************
		 * 
		 * Build page hierarchy
		 * 
		 ************************/
		
		//add form to page
		add(form);
		
		//add step2 to form
		form.add(step2Container);
		form.add(poolDescription);
		form.add(leagueTypesDD);
		
		//add step 2 components
		step2Container.add(bankerContainer);
		step2Container.add(maxNumberOfPlayers);
		step2Container.add(banker);
		step2Container.add(doubleContainer); 
		step2Container.add(spreads);
		step2Container.add(leagueName);
		step2Container.add(password);
		step2Container.add(submitButton);
		
		//add banker components
		bankerContainer.add(totalCostContainer);
		bankerContainer.add(weeklyFeeContainer);
		bankerContainer.add(entryFee);
		bankerContainer.add(tieBreakContainer);
		bankerContainer.add(percentContainer);
		
		totalCostContainer.add(totalCost);
		
		weeklyFeeContainer.add(weeklyFee);
		weeklyFeeContainer.add(weekCalc);
		
		doubleContainer.add(doubleEnabled);
		
		tieBreakContainer.add(doubleType);
		tieBreakContainer.add(tieBreakerDescription);
		
		percentContainer.add(firstPlacePercent);
		percentContainer.add(secondPlacePercent);
		percentContainer.add(thirdPlacePercent);
		percentContainer.add(fourthPlacePercent);
		percentContainer.add(fifthPlacePercent);
		
		
				
	}
	
	public abstract Page getStep2Page(IModel<League> leagueModel);
	
	
}

