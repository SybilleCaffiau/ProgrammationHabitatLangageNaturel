package core.objects.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.formats.openhab.OpenHabFormats;
import core.utils.KeywordNormalizer;

/**
 * @author Clément Didier
 * Représente une condition temporelle simple
 * Se limite actuellement à Heure:Minute:Seconde
 */
public class TimeLeaf extends Leaf 
{
	// TODO: (BAD) HARD-CODE
	private final static String MIDNIGHT = "minuit";
	private final static String NOON = "midi";
	
	/** @author Lorrie */
	private final static String HOUR_PATTERN = "^(2[0-3]|[01]?[0-9])(\\s)*(:|h(eure)?s?)$"; // pattern heure
	/** @author Lorrie */
	private final static String HOUR_MIN_PATTERN = "^(2[0-3]|[01]?[0-9])(\\s)*(:|h(eure)?s?)([0-5]?[0-9])(\\s)*(:|m(n|in|inute)?s?)?$"; // pattern heure-minute
	/** @author Lorrie */
	private final static String HOUR_MIN_SEC_PATTERN = "^(2[0-3]|[01]?[0-9])(\\s)*(:|h(eure)?s?)([0-5]?[0-9])(\\s)*(:|m(n|in|inute(s)?)?)?([0-5]?[0-9])(\\s)*(s(ec|econde(s)?)?)?$"; // pattern heure-minute-sec
	
	private final static String DEFAULT_VALUE = "0";
	
	private int type; // DateTimeType bitwise
	private String time; // Cas MIDNIGHT ou NOON
	private String seconds, minutes, hours, day, month, dayOfWeek, year;
	
	private TimeLeaf()
	{
		this(DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE);
	}
	
	public TimeLeaf(String seconds, String minutes, String hours, String day, String month, String dayOfWeek, String year)
	{
		this.type = DateTimeType.NONE.getId();
		this.time = null;
		this.seconds = seconds;
		this.minutes = minutes;
		this.hours = hours;
		this.day = day;
		this.month = month;
		this.dayOfWeek = dayOfWeek;
		this.year = year;
	}
	
	/** 
	 * Converti une chaîne de caractères représentant une date ou un temps, en un format temporel OpenHab
	 * @param time La date ou temps à convertir
	 * @return Le noeud conditionnel correspondant au temps donné
	 * @throws ConditionParsingException Remontée lorsque la conversion a échouée
	 */
	public static TimeLeaf parse(String time) throws ConditionParsingException
	{
		TimeLeaf leaf = new TimeLeaf();

		if(KeywordNormalizer.getNormalizedKeyword(time).equals(MIDNIGHT))
		{
			leaf.time = OpenHabFormats.TIME_MIDNIGHT.getFormat();
			leaf.type = DateTimeType.HOURS.getId() | DateTimeType.MINUTES.getId();
			return leaf;
		}
		else if(KeywordNormalizer.getNormalizedKeyword(time).equals(NOON))
		{
			leaf.time = OpenHabFormats.TIME_NOON.getFormat();
			leaf.hours = "12"; // TODO: Valeur magique
			leaf.type = DateTimeType.HOURS.getId() | DateTimeType.MINUTES.getId();
			return leaf;
		}
		
		/** @author Lorrie */
		//expression heure de type HH
		Matcher matcher = Pattern.compile(HOUR_PATTERN).matcher(time);
		if(matcher.matches())
		{
			leaf.hours = matcher.group(1); // TODO: (BAD) Valeurs magiques
			leaf.type = DateTimeType.HOURS.getId();
			return leaf;
		}
		
		/** @author Lorrie */
		//expression heure de type HHMM
		matcher = Pattern.compile(HOUR_MIN_PATTERN).matcher(time);
		if(matcher.matches())
		{
			leaf.hours = matcher.group(1); // TODO: (BAD) Valeurs magiques
			leaf.minutes = matcher.group(5); // TODO: (BAD) Valeurs magiques
			leaf.type = DateTimeType.HOURS.getId() | DateTimeType.MINUTES.getId();
			return leaf;
		}

		/** @author Lorrie */
		//expression heure de type HHMMSS
		matcher = Pattern.compile(HOUR_MIN_SEC_PATTERN).matcher(time);
		if(matcher.matches())
		{
			leaf.hours = matcher.group(1); // TODO: (BAD) Valeurs magiques
			leaf.minutes = matcher.group(5); // TODO: (BAD) Valeurs magiques
			leaf.seconds = matcher.group(10); // TODO: (BAD) Valeurs magiques
			leaf.type = DateTimeType.HOURS.getId() | DateTimeType.MINUTES.getId() | DateTimeType.SECONDS.getId();
			return leaf;
		}
		
		throw new ConditionParsingException("La donnée \"" + time + "\" n'a pas été reconnu, conversion impossible");
	}

	/**
	 * Obtient la représentation textuelle OpenHab du noeud conditionnel, dans un contexte donné
	 * @param context Le contexte de convertion permettant d'obtenir le format OpenHab adéquat
	 * @return La représentation textuelle OpenHab du noeud conditionnel
	 */
	//TODO: Ne prend en compte que les Heures, Minutes et Secondes !
	@Override
	public String toOpenHabString(ParsingContext context) 
	{
		
		switch(context)
		{
			//******* Dans la clause conditionnelle de la règle (TRIGGER) *******
			case TRIGGER:
				if(this.time != null)
					return this.time; // Cas MIDNIGHT ou NOON
				
				// Cas de TimeCron
				return String.format(OpenHabFormats.TIME_CRON.getFormat(),
						seconds, 
						minutes, 
						hours, 
						day == DEFAULT_VALUE ? "*" : day, 
						month == DEFAULT_VALUE ? "*" : month, 
						dayOfWeek == DEFAULT_VALUE ? "?" : dayOfWeek, 
						year == DEFAULT_VALUE ? "" : year);
				
			//******* Dans la clause d'execution de la règle (EXECUTION_BLOCK) *******	
			default:
				List<String> result = new ArrayList<String>();
				String condition = new String();
				
				if((this.type & DateTimeType.HOURS.getId()) != 0)
					result.add(String.format(OpenHabFormats.TIME_HOUR_OF_DAY.getFormat(), this.hours));
				if((this.type & DateTimeType.MINUTES.getId()) != 0)
					result.add(String.format(OpenHabFormats.TIME_MINUTE_OF_HOUR.getFormat(), this.minutes));
				if((this.type & DateTimeType.SECONDS.getId()) != 0)
					result.add(String.format(OpenHabFormats.TIME_SECOND_OF_MINUTE.getFormat(), this.seconds));
				
				for(int i = 0; i < result.size(); i++)
				{
					condition += result.get(i);
					
					if(i < result.size() - 1)
						condition += OpenHabFormats.CONDITION_AND_EXECUTION_CONTEXT.getFormat();
				}
				
				return String.format(OpenHabFormats.CONDITION_BLOCK.getFormat(), condition);
		}
	}
}
