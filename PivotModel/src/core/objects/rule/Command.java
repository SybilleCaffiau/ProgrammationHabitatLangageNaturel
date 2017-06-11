package core.objects.rule;

import core.formats.openhab.OpenHabFormats;

/**
 * @author Clément Didier
 */
public class Command 
{
	/**
	 * Mot clé de commande item OpenHab
	 */
	private String openhabCommand;
	
	/**
	 * Mot clé correspondant au nom de l'item OpenHab cible
	 */
	private String openhabItemName;
	
	/**
	 * Constructeur de nouvelle instance de Command
	 * @param itemName Le nom de l'item OpenHab
	 * @param command La commande OpenHab correspondante
	 */
	public Command(String itemName, String command)
	{
		this.openhabItemName = itemName;
		this.openhabCommand = command;
	}
	
	/**
	 * Obtient la représentation textuelle de la commande OpenHab
	 * @return La commande textuelle OpenHab
	 */
	public String getOpenhabCommand() {
		return openhabCommand;
	}

	/**
	 * Obtient le nom de l'item OpenHab cible
	 * @return Le nom de l'item OpenHab
	 */
	public String getOpenhabItemName() {
		return openhabItemName;
	}
	
	/**
	 * Obtient la représentation textuelle de la Command, au format OpenHab
	 * @return La représentation textuelle au format OpenHab
	 */
	public String toOpenHabString()
	{
		return String.format(OpenHabFormats.COMMAND.getFormat(), this.openhabItemName, this.openhabCommand); 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((openhabCommand == null) ? 0 : openhabCommand.hashCode());
		result = prime * result + ((openhabItemName == null) ? 0 : openhabItemName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		System.err.println("CALLED");
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Command other = (Command) obj;
		if (openhabCommand == null && other.openhabCommand != null)
				return false;
		else if (!openhabCommand.equals(other.openhabCommand))
			return false;
		if (openhabItemName == null && other.openhabItemName != null)
				return false;
		else if (!openhabItemName.equals(other.openhabItemName))
			return false;
		
		System.err.println("TESTED : " + this.toOpenHabString() + " with " + other.toOpenHabString());
		return true;
	}
	
	
}
