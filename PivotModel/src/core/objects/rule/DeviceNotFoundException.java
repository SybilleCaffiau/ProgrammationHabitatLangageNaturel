package core.objects.rule;

@SuppressWarnings("serial")
public class DeviceNotFoundException extends Exception 
{
	public DeviceNotFoundException(String message)
	{
		super(message);
	}
}
