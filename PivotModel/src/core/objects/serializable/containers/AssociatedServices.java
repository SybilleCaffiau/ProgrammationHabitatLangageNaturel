package core.objects.serializable.containers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import core.objects.serializable.AssociatedService;

public class AssociatedServices implements Container<AssociatedService> 
{
	
	@XmlElement(name="service")
	/**
	 * Les services associés du conteneur
	 */
	private List<AssociatedService> services;
	
	/**
	 * XML Constructor
	 */
	private AssociatedServices()
	{
		this.services = new ArrayList<AssociatedService>();
	}
	
	/**
	 * Constructeur de conteneur de services associés
	 * @param services Les services associés à intégrer au nouveau conteneur
	 */
	public AssociatedServices(AssociatedService...services)
	{
		this();
		for(AssociatedService service : services)
			this.services.add(service);
	}
	
	
	@Override
	/**
	 * Obtient la liste des services associés, contenu dans le conteneur
	 */
	public List<AssociatedService> getList() {
		return this.services;
	}

	
	@Override
	/**
	 * Obtient le service associé disposant de l'identifiant id
	 * @param id L'identifiant du service associé recherché
	 * @return Le service associé avec l'identifiant donné si existant, null dans le cas contraire
	 */
	public AssociatedService getById(String id) 
	{
		for(AssociatedService service : this.services)
			if(service.getServiceId().equals(id))
				return service;
		return null;
	}

	
	@Override
	/**
	 * Obtient l'état de remplissage du conteneur
	 * @return True si le contenur ne contient aucun élément, False dans le cas contraire
	 */
	public boolean isEmpty() {
		return this.services.isEmpty();
	}

	
	@Override
	/**
	 * Ajoute un nouvel élément dans le conteneur
	 * @param element Le nouvel élément à ajouter au conteneur
	 */
	public void add(AssociatedService element) {
		this.services.add(element);
	}

}
