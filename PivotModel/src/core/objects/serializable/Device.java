package core.objects.serializable;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import core.objects.rule.Command;
import core.objects.rule.ConditionParsingException;
import core.objects.rule.ServiceNotFoundException;
import core.objects.serializable.containers.AssociatedServices;
import core.objects.serializable.containers.Keywords;
import core.objects.serializable.containers.Services;
import habitat.AmbiguityResolver;
import habitat.Habitat;
/**
 * @author Clément Didier Sybille Caffiau
 */
@XmlRootElement(name="device")
public class Device extends Identifiable 
{
	
	@XmlElement(name="services")
	/**
	 * Les services associés à l'appareil
	 */
	private AssociatedServices services;
	
	
	@XmlAttribute(name="room")
	/**
	 * La localisation de l'appareil dans l'habitat
	 */
	private String roomId;
	
	/**
	 *  XML Constructor
	 */
	private Device()
	{
		super(null, null);
	}
	
	/***
	 * Constructeur d'appareil
	 * @param id L'identifiant de l'appareil
	 * @param roomId L'identifiant de la localisation de l'appareil
	 * @param keywords Les mots clés de l'appareil
	 * @param services Les services associés à l'appareil
	 */
	public Device(String id, String roomId, Keywords keywords, AssociatedServices services)
	{
		super(id, keywords);
		this.services = services;
		this.keywords.add(id);
		this.roomId = roomId;
	}
	
	/**
	 * Obtient les services associés à l'appareil
	 * @return Les services associés
	 */
	public AssociatedServices getServices()
	{
		return this.services;
	}
	
	/**
	 * Obtient les commandes et les items OpenHab référents, relatifs au mot clé de fonction donné
	 * @param functionKeyword Le mot clé de la fonction associé à l'appareil
	 * @return Les commandes et les items OpenHab référents, relatifs au mot clé de la fonction
	 * @throws ServiceNotFoundException Remontée lorsque la fonction donnée n'est pas prise en charge par l'appareil
	 * @throws ConditionParsingException Remontée lorsqu'une erreur de conversion des données est survenue lors de l'execution
	 */
	public List<Command> getCommands(String functionKeyword) throws ServiceNotFoundException, ConditionParsingException
	{	
		if(this.services.isEmpty()) 
			throw new ServiceNotFoundException("L'appareil \"" + this.getId() + "\" ne possède aucun service");
		
		List<Command> commands = new ArrayList<Command>();
		
		for(AssociatedService dservice : services.getList())
		{
			// Obtention du service générique si existant
			Services services = Habitat.getInstance().getServices().getById(dservice.getServiceId());
			
			for(Service service : services.getList())
			{
				for(Function function : service.getFunctions().getList())
				{
					if(function.hasKeyword(functionKeyword))
					{
						// Le service générique connaît la command OpenHab mais n'est pas lié à un item (service générique)
						// Ainsi on obtient l'item OpenHab référent avec l'AssociatedService (correspondant au service associé à l'appareil)
						commands.add(new Command(dservice.getOpenHabItemName(), function.getOpenHabCommand()));
					}
				}
			}
		}
		
		if(commands.isEmpty())
			throw new ServiceNotFoundException("L'appareil \"" + this.getId() + "\" ne dispose pas d'un service ayant la fonction \"" + functionKeyword + "\"");
		
		// Désambiguisation : appel d'un resolveur externe au modèle si existant
		if(commands.size() > 1)
		{
			AmbiguityResolver resolver = Habitat.getInstance().getAmbiguityResolver();
			if(resolver != null) 
				commands = resolver.multipleAssociatedServiceForSameFunctionAmbiguity(this, functionKeyword, commands);
		}
			
		return commands;
	}
	
	/**
	 * Obtient l'identifiant de la localisation de l'appareil
	 * @return L'identifiant de la localisation
	 */
	public String getRoomId()
	{
		return this.roomId;
	}
	
	public String toString(){
		return (this.getId());
	}
}
