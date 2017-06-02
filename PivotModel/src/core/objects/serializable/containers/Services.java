package core.objects.serializable.containers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import core.objects.serializable.Service;

@XmlRootElement(name="services")
public class Services implements Container<Service>
{
	@XmlElement(name="service")
	/**
	 * Les services génériques contenus dans le conteneur
	 */
	private List<Service> services;
	
	/**
	 *  XML Constructor
	 */
	private Services()
	{
		this.services = new ArrayList<Service>();
	}
	
	/**
	 * Constructeur de conteneur de services génériques
	 * @param services Les services génériques à intégrer au nouveau conteneur
	 */
	public Services(Service...services)
	{
		this();
		for(Service service : services)
			this.services.add(service);
	}
	
	@Override
	public void add(Service service)
	{
		if(getById(service.getId()) != null)
			this.services.add(service);
	}
	
	@Override
	public Service getById(String id) {
		for(Service service : this.services)
			if(service.getId().equals(id))
				return service;
		return null;
	}

	@Override
	public List<Service> getList() {
		return this.services;
	}

	@Override
	public boolean isEmpty() {
		return this.services.isEmpty();
	}
}
