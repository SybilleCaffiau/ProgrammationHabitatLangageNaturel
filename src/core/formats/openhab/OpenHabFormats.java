package core.formats.openhab;

/**
 * @author Clément Didier
 */
public enum OpenHabFormats 
{
	RULE_FORMAT						("rule %s\nwhen\n\t%s\nthen\n%s\nend"),
	COMPLEMENTARY_RULE_NAME			("%s_complementary"),
	CONDITION_OR_TRIGGER_CONTEXT	(" or\n\t"),
	CONDITION_OR_EXECUTION_CONTEXT	(" || "),
	CONDITION_AND_EXECUTION_CONTEXT (" && "),
	EVENT_CHANGED_TO				("Item %s changed to %s"),
	EVENT_EQUALS_STATE				("%s.state == %s"),
	EXECUTION_CONDITION				("if(%s){\n%s}"),
	CONDITION_BLOCK					("(%s)"),
	COMMAND							("\tsendCommand(%s, %s)\n"),
	TIME_CRON						("Time Cron \"%s %s %s %s %s %s %s\""), 
	FILE_NAME						("%s.rules"),
	TIME_MIDNIGHT					("Time is midnight"),
	TIME_NOON						("Time is noon"),
	TIME_MONTH_OF_YEAR				("now.getMonthOfYear() == %s"),
	TIME_DAY_OF_MONTH				("now.getDayOfMonth() == %s"),
	TIME_HOUR_OF_DAY				("now.getHourOfDay() == %s"),
	TIME_MINUTE_OF_HOUR				("now.getMinuteOfHour() == %s"),
	TIME_SECOND_OF_MINUTE			("now.getSecondOfMinute() == %s");
	
	private String format;
	
	OpenHabFormats(String format)
	{
		this.format = format;
	}
	
	public String getFormat()
	{
		return this.format;
	}
}
