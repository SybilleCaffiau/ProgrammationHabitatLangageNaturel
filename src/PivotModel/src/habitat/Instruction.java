package habitat;

import java.util.ArrayList;
import java.util.List;

import core.objects.rule.Command;
import core.objects.rule.DeviceNotFoundException;
import core.objects.rule.LocationNotFoundException;
import core.objects.serializable.Device;
/**
 * @author Clément Didier
 */
public class Instruction 
{
	private String device;
	private String room;
	private String action;
	
	public Instruction(String device, String action)
	{
		this.device = device;
		this.room = null;
		this.action = action;
	}
	
	public Instruction(String device, String room, String action)
	{
		this.device = device;
		this.room = room;
		this.action = action;
	}
	
	public List<Command> getCommands(Habitat habitat) throws DeviceNotFoundException, LocationNotFoundException
	{
		List<Command> commands = new ArrayList<Command>();
		List<Device> devices = (this.room != null) ? habitat.getDevices(this.device, this.room) : habitat.getDevices(this.device);
		devices.forEach(device -> 
		{
			try { commands.addAll(device.getCommands(this.action)); }  catch (Exception e)  { 
				/*System.err.println(e.getMessage());*/ }
		});
		
		if(commands.isEmpty())
			throw new DeviceNotFoundException("Aucun appareil \"" + 
					this.device + "\"" + (this.room != null ? " à la localisation \"" + this.room + "\"" : "") +
					" ne dispose de la fonction \"" + this.action + "\"");
		
		return commands;
	}
}
