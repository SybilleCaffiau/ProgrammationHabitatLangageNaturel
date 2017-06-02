package core.objects.rule;

@SuppressWarnings("serial")
public class ServiceNotFoundException extends Exception 
{
	public ServiceNotFoundException(String message)
	{
		super(message);
	}
}
