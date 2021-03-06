package core.objects.serializable.containers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import core.objects.serializable.Device;
/**
 * @author Clément Didier
 */
@XmlRootElement(name="devices")
public class Devices implements Container<Device>
{
	
	@XmlElement(name="device")
	/**
	 * Les appareils du conteneur
	 */
	private List<Device> devices;
	
	/**
	 *  XML Constructor
	 */
	private Devices()
	{
		this.devices = new ArrayList<Device>();
	}
	
	/**
	 * Constructeur de conteneur d'appareils
	 * @param devices Les appareils à intégrer au nouveau conteneur
	 */
	public Devices(Device...devices)
	{
		this();
		
		for(Device device : devices)
			this.devices.add(device);
	}
	
	@Override
	public List<Device> getList()
	{
		return this.devices;
	}

	@Override
	public Devices getById(String id) {
		Devices devices = new Devices();
		
		for(Device device : this.devices)
			if(device.getId().equals(id))
				devices.add(device);
		
		return devices;
	}

	@Override
	public boolean isEmpty() {
		return this.devices.isEmpty();
	}

	@Override
	public void add(Device element) {
		this.devices.add(element);
	}
}
