package core.objects.serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="service")
public class AssociatedService 
{
	@XmlAttribute(name="id")
	/**
	 * L'identifiant du service associé
	 */
	private String serviceId;

	@XmlAttribute(name="item")
	/**
	 * Le nom de l'objet openHab référent
	 */
	private String openHabItemName;
	
	/**
	 *  XML Constructor
	 */
	private AssociatedService()
	{
		this.serviceId = null;
		this.openHabItemName = null;
	}
	
	/**
	 * Constructeur de service associé à un appareil
	 * @param serviceId L'identifiant du service générique référent
	 * @param item L'entité OpenHab référente
	 */
	public AssociatedService(String serviceId, String item)
	{
		this();
		this.serviceId = serviceId;
		this.openHabItemName = item;
	}
	
	/**
	 * Obtient l'identifiant du service générique référent
	 * @return L'identifiant du service générique
	 */
	public String getServiceId() 
	{
		return serviceId;
	}

	/**
	 * Obtient le nom de l'entité OpenHab référente
	 * @return Le nom de l'entité OpenHab
	 */
	public String getOpenHabItemName() 
	{
		return openHabItemName;
	}
}
