package core.objects.serializable;

import javax.xml.bind.annotation.XmlRootElement;

import core.objects.serializable.containers.Keywords;
/**
 * @author Clément Didier
 */
@XmlRootElement(name="room")
public class Room extends Identifiable {
	
	/**
	 *  XML Constructor
	 */
	private Room()
	{
		super(null, null);
	}
	
	/**
	 * Constructeur de localisation dans l'habitat
	 * @param id L'identifiant de la localisation
	 * @param keywords Les mots clés de la localisation
	 */
	public Room(String id, Keywords keywords)
	{
		super(id, keywords);
		this.keywords.add(id);
	}
}
