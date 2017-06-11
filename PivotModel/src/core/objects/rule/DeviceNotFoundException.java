package core.objects.rule;
/**
 * @author Clément Didier
 */
@SuppressWarnings("serial")
public class DeviceNotFoundException extends Exception 
{
	public DeviceNotFoundException(String message)
	{
		super(message);
	}
}
