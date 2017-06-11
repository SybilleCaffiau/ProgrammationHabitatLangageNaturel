package core.objects.serializable.containers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import core.objects.serializable.AssociatedService;
/**
 * @author Clément Didier
 */
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
	 * Obtient les services associés disposants de l'identifiant id
	 * @param id L'identifiant des services associés recherchés
	 * @return Les services associés avec l'identifiant donné si existants
	 */
	public AssociatedServices getById(String id) 
	{
		AssociatedServices services = new AssociatedServices();
		
		for(AssociatedService service : this.services)
			if(service.getServiceId().equals(id))
				services.add(service);
		
		return services;
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
