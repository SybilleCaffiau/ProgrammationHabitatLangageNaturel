package core.objects.rule;

import java.util.ArrayList;
import java.util.List;

import core.formats.openhab.OpenHabFormats;
import core.objects.serializable.Device;
import habitat.AmbiguityResolver;
import habitat.Habitat;
/**
 * @author Clément Didier
 */
public class EventLeaf extends Leaf 
{
	/**
	 * Les commandes de l'èvénement
	 */
	private List<Command> commands;
	
	/**
	 * Etat permettant d'indiquer que l'instance fait référence à plusieurs objets ou non après désambiguisation
	 * Permet de générer des conjonctions convenables dans les règles
	 */
	private boolean isMultipleCommands;
	
	private EventLeaf()
	{
		this.commands = new ArrayList<Command>();
	}
	
	/**
	 * Obtient le noeud de l'évènement conditionnel entre en ensemble d'appareils et une de leurs fonctions (CHECK)
	 * (OpenHab : "item.state == state" ou encore "Item item changed to state")
	 * @param deviceKeyword Le mot clé des appareils cibles
	 * @param functionKeyword Le mot clé de la fonction de référence des appareils cibles, les appareils correspondants ne disposant pas du service
	 * demandé sont ignorés
	 * @throws DeviceNotFoundException Remontée lorsqu'aucun appareil ne correspond au mot clé donné
	 * @throws ConditionParsingException Remontée lorsqu'une erreur de conversion est survenue lors de l'execution
	 * @throws ServiceNotFoundException Remontée lorsque les appareils ne disposent pas du service demandé
	 */
	public EventLeaf(String deviceKeyword, String functionKeyword) throws DeviceNotFoundException, ConditionParsingException, ServiceNotFoundException
	{
		this();
		
		List<Device> devices = Habitat.getInstance().getDevices(deviceKeyword);
		if(devices.isEmpty())
			throw new DeviceNotFoundException("L'appareil \"" + deviceKeyword + "\" est introuvable");
		this.initialize(devices, deviceKeyword, functionKeyword);
	}
	
	/**
	 * Obtient le noeud de l'évènement conditionnel entre un ensemble d'appareils d'une localisation et une de leurs fonctions (CHECK)
	 * (OpenHab : "item.state == state" ou encore "Item item changed to state")
	 * @param deviceKeyword Le mot clé des appareils cibles
	 * @param room La localisation des appareils cibles
	 * @param functionKeyword Le mot clé de la fonction de référence des appareils cibles, les appareils correspondants ne disposant pas du service
	 * demandé sont ignorés
	 * @throws LocationNotFoundException Remontée lorsque la localisation donnée n'a aucune correspondance
	 * @throws DeviceNotFoundException Remontée lorsqu'aucun appareil ne correspond au mot clé donné
	 * @throws ConditionParsingException Remontée lorsqu'une erreur de conversion est survenue lors de l'execution
	 * @throws ServiceNotFoundException Remontée lorsque les appareils ne disposent pas du service demandé
	 */
	public EventLeaf(String deviceKeyword, String room, String functionKeyword) throws LocationNotFoundException, DeviceNotFoundException, ConditionParsingException, ServiceNotFoundException
	{
		this();
		
		// Tous les appareils correspondants de l'habitat
		List<Device> devices = Habitat.getInstance().getDevices(deviceKeyword, room);
		if(devices.isEmpty())
			throw new DeviceNotFoundException("L'appareil \"" + deviceKeyword + "\" est introuvable à la localisation \"" + room +"\"");
		this.initialize(devices, deviceKeyword, functionKeyword);
	}
	
	private void initialize(List<Device> devices, String deviceKeyword, String functionKeyword) throws ConditionParsingException, ServiceNotFoundException, DeviceNotFoundException
	{
		// Ambiguité sur le quantifieur
		if(devices.size() > 1)
		{
			AmbiguityResolver resolver = Habitat.getInstance().getAmbiguityResolver();
			if(resolver != null)
				devices = resolver.multipleDevicesFoundForOneFunctionAmbiguity(deviceKeyword, functionKeyword, devices);
		}
		
		boolean serviceNotFound = false;
		
		// Pour chacun d'eux
		for(Device device : devices)
		{
			try
			{
				// On tente l'ajout de toutes les commandes référentes que l'appareils prend en charge
				commands.addAll(device.getCommands(functionKeyword));
			}
			catch(ServiceNotFoundException e)
			{
				serviceNotFound = true;
				
				// On ignore l'appareil s'il ne dispose pas de la fonction recherchée, on affiche un message pour renseigner
				//System.err.println("L'objet \"" + device.getId() + "\" ne dispose pas de la fonction recherchée, il est donc ignoré");
			}
		}
		
		this.isMultipleCommands = commands.size() > 1;
		
		if(commands.isEmpty())
		{
			if(serviceNotFound)
				throw new ServiceNotFoundException("Aucun appareil \"" + deviceKeyword +
						"\" ne dispose d'une fonction \"" + functionKeyword + "\"");
			
			throw new DeviceNotFoundException("Aucun appareil ne correspond à \"" + deviceKeyword + "\"");
		}
	}

	/**
	 * Obtient la représentation textuelle de la condition au format OpenHab, selon un contexte donné
	 * @param context Le contexte OpenHab actuel, permettant d'obtenir le format adéquat
	 * @return La représentation textuelle de la condition au format OpenHab
	 */
	@Override
	public String toOpenHabString(ParsingContext context) throws ConditionParsingException
	{
		if(this.commands == null || this.commands.isEmpty())
			throw new ConditionParsingException("Tentative de conversion d'une condition sans commandes");
		
		switch(context)
		{
			// Cas CONDITION_BLOCK
			case TRIGGER:
				// TODO: Par défaut, à revoir => Etudier comment integrer les RECEIVED*, CHANGED*
				if(this.commands.size() == 1) // cas d'élément unique
				{
					return String.format(OpenHabFormats.EVENT_CHANGED_TO.getFormat(), this.commands.get(0).getOpenhabItemName(), this.commands.get(0).getOpenhabCommand());
				}
				else // Cas de plusieurs items pour un même keyword
				{
					StringBuilder builder = new StringBuilder();
					
					for(int i = 0; i < this.commands.size(); i++)
					{
						builder.append(String.format(OpenHabFormats.EVENT_CHANGED_TO.getFormat(),
								this.commands.get(i).getOpenhabItemName(),
								this.commands.get(i).getOpenhabCommand()));
						
						if(i < this.commands.size() - 1) // Ajout des "or" entre chaque élément conditionnel
							builder.append(OpenHabFormats.CONDITION_OR_TRIGGER_CONTEXT.getFormat());
					}
					
					return builder.toString();
				}
			// Cas EXECUTION_BLOCK
			default:
				if(this.commands.size() == 1) // cas d'élément unique
				{
					return String.format(OpenHabFormats.EVENT_EQUALS_STATE.getFormat(), this.commands.get(0).getOpenhabItemName(), this.commands.get(0).getOpenhabCommand()); 
				}
				else  // Cas de plusieurs appareils pour un même keyword
				{
					StringBuilder builder = new StringBuilder();
					
					for(int i = 0; i < this.commands.size(); i++)
					{
						builder.append(String.format(OpenHabFormats.EVENT_EQUALS_STATE.getFormat(),
								this.commands.get(i).getOpenhabItemName(),
								this.commands.get(i).getOpenhabCommand()));
						if(i < this.commands.size() - 1)
							builder.append(OpenHabFormats.CONDITION_AND_EXECUTION_CONTEXT.getFormat());
					}
					
					return String.format(OpenHabFormats.CONDITION_BLOCK.getFormat(), builder.toString());
				}
		}
	}
	
	/**
	 * Définit si l'évènement correspond à plusieurs commandes
	 * Permet de générer convenablement des conjonctions dans les règles
	 * @return Vrai si l'évènement définit plsuieurs commandes, False dans le cas contraire
	 */
	public boolean isMutlipleCommands()
	{
		return this.isMultipleCommands;
	}
}
