package core.objects.rule;
/**
 * @author Clément Didier
 */
@SuppressWarnings("serial")
public class ServiceNotFoundException extends Exception 
{
	public ServiceNotFoundException(String message)
	{
		super(message);
	}
}
