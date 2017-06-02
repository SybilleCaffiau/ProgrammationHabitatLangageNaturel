package core.objects.serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import core.objects.serializable.containers.Keywords;

@XmlRootElement(name="function")
public class Function extends Identifiable 
{
	/**
	 * La représentation textuelle de la commande OpenHab
	 */
	@XmlAttribute(name="command")
	private String openhabCommand;
	
	/**
	 *  XML Constructor
	 */
	private Function()
	{
		super(null, null);
	}
	
	/**
	 * Constructeur de fonction
	 * @param id L'identifiant de la fonction
	 * @param command La commande OpenHab prise en charge par la fonction
	 * @param keywords Les mots clés de la fonction
	 */
	public Function(String id, String command, Keywords keywords)
	{
		super(id, keywords);
		this.keywords.add(id);
		this.openhabCommand = command;
	}
	
	/**
	 * Obtient la commande textuelle OpenHab référente
	 * @return La commande textuelle OpenHab
	 */
	public String getOpenHabCommand()
	{
		return this.openhabCommand;
	}
}
