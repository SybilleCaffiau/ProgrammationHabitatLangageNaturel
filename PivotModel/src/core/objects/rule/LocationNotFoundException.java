package core.objects.rule;
/**
 * @author Clément Didier
 */
@SuppressWarnings("serial")
public class LocationNotFoundException extends Exception 
{
	public LocationNotFoundException(String message)
	{
		super(message);
	}
}
