package habitat;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import core.objects.rule.Command;
import core.objects.rule.ConditionParsingException;
import core.objects.rule.ConditionalTree;
import core.objects.rule.DeviceNotFoundException;
import core.objects.rule.LocationNotFoundException;
import core.objects.rule.Rule;
import core.objects.serializable.Device;
import core.objects.serializable.Function;
import core.objects.serializable.Room;
import core.objects.serializable.Service;
import core.objects.serializable.containers.Devices;
import core.objects.serializable.containers.Rooms;
import core.objects.serializable.containers.Services;
import core.utils.ConfigurationManager;
/**
 * @author Clément Didier
 */
@XmlRootElement(name="habitat")
public class Habitat
{
	private final static String DEFAULT_CONFIG_FILE_PATH = "PivotModel/environnements/config-simulation.xml";
	private static Habitat instance;
	private static ConfigurationManager configurationManager = new ConfigurationManager(DEFAULT_CONFIG_FILE_PATH);
	
	private AmbiguityResolver resolver;
	
	@XmlElement(name="rooms")
	/**
	 * Le conteneur de localisations de l'habitat (pièces)
	 */
	private Rooms rooms;
	
	@XmlElement(name="services")
	/**
	 * Le conteneur de services génériques de l'habitat
	 */
	private Services services;
	
	@XmlElement(name="devices")
	/**
	 * Le conteneur d'appareils de l'habitat
	 */
	private Devices devices;
	
	/**
	 *  XML Constructor
	 */
	private Habitat()
	{
		this.rooms = new Rooms();
		this.services = new Services();
		this.devices = new Devices();
		this.resolver = null;
	}
	
	/**
	 * Obtient l'instance chargée de l'habitat
	 * @return L'instance de l'habitat peuplé
	 */
	public static Habitat getInstance()
	{
		if(instance == null)
		{
			try 
			{
				instance = configurationManager.unmarshall();
			} catch (JAXBException e) {
				System.err.println("Erreur fatale : Impossible de lire ou de charger le fichier de configurations\nInformations :\n");
				e.printStackTrace();
				System.exit(1);
			}
		}
		return instance;
	}
	
	/**
	 * Obtient l'instance chargée de l'habitat
	 * @param configFileName Le fichier de configuration de l'habitat à charger
	 * @return L'instance peuplé de l'habitat
	 */
	public static Habitat load(String configFileName)
	{
		configurationManager = new ConfigurationManager(configFileName);
		instance = null;
		
		return getInstance();
	}

	/**
	 * Obtient la liste des pièces de l'habitat
	 * @return La liste des pièces
	 */
	public Rooms getRooms() {
		return rooms;
	}
	
	
	/**
	 * Obtient la liste des pièces de l'habitat correspondantes au mot clé donné
	 * @param roomKeyword Le mot clé des/de la pièce(s) à obtenir
	 * @return La liste des pièce
	 */
	public List<Room> getRooms(String roomKeyword)
	{
		List<Room> results = new ArrayList<Room>();
		
		for(Room room : this.rooms.getList())
		{
			if(room.hasKeyword(roomKeyword))
			{
				results.add(room);
			}
		}
		
		return results;
	}
	
	/**
	 * Obtient la liste des services génériques (sans appareil associé)
	 * @return La liste des services génériques
	 */
	public Services getServices() {
		return services;
	}
	
	/**
	 * Obtient la liste des services génériques diposant du mot clé donné, avec prise en compte des mots clés des fonctions du service
	 * @param keyword Le mot clé des services et fonctions des services recherchés
	 * @return La liste des services génériques correspondants
	 */
	public Services getServices(String keyword) 
	{
		Services services = new Services();
		for(Service service : this.services.getList())
		{
			boolean hasFunctionKeyword = false;
			
			for(Function function : service.getFunctions().getList())
			{
				if(function.hasKeyword(keyword))
				{
					hasFunctionKeyword = true;
					break;
				}
			}
			
			if(service.hasKeyword(keyword) || hasFunctionKeyword)
			{
				services.add(service);
			}
		}
		
		return services;
	}

	/**
	 * Obtient la liste des appareils de l'habitat
	 * @return La liste des appareils
	 */
	public Devices getDevices() {
		return devices;
	}

	/**
	 * Obtient la liste des appareils de l'habitat correspondants au mot clé donné et situés dans la pièce identifiée par le second mot clé donné
	 * @param deviceKeyword Le mot clé de/des appareils à selectionner
	 * @param roomKeyword Le mot clé de/des pièces à selectionner
	 * @return La liste filtrée des appareils
	 * @throws LocationNotFoundException Remontée lorsque la pièce donnée n'a pas été trouvée
	 */
	public List<Device> getDevices(String deviceKeyword, String roomKeyword) throws LocationNotFoundException 
	{
		if(this.getRooms(roomKeyword).isEmpty()) 
			throw new LocationNotFoundException("La localisation \"" + roomKeyword + "\" est introuvable");
		
		List<Device> result = new ArrayList<Device>();
		
		for(Device device : this.devices.getList())
		{
			Rooms rooms = this.rooms.getById(device.getRoomId());
			
			if(rooms != null)
			{
				for(Room room : rooms.getList())
				{
					if(device.hasKeyword(deviceKeyword) && room != null && room.hasKeyword(roomKeyword))
					{
						result.add(device);
					}
				}
			}
		}
		
		return result;
	}

	/**
	 * Obtient la liste des appareils correspondants au mot clé donné
	 * @param deviceKeyword Le mot clé de/des appareils à selectionner
	 * @return La liste filtrée des appareils
	 * @throws DeviceNotFoundException Remontée lorsque les/l'appareil n'a pas été trouvé
	 */
	public List<Device> getDevices(String deviceKeyword) throws DeviceNotFoundException 
	{
		List<Device> result = new ArrayList<Device>();
		
		for(Device device : this.devices.getList())
		{
			if(device.hasKeyword(deviceKeyword))
			{
				result.add(device);
			}
		}
		
		if(result.isEmpty())
			throw new DeviceNotFoundException("L'appareil \"" + deviceKeyword + "\" n'a pas été trouvé");
		
		return result;
	}
	
	/**
	 * Modifie le resolveur d'ambiguité
	 * @param resolver La référence vers le nouveau resolveur
	 */
	public void setAmbiguityResolver(AmbiguityResolver resolver)
	{
		this.resolver = resolver;
	}
	
	
	/**
	 * Obtient la référence du resolveur d'ambiguité
	 * @return Le resolveur d'ambiguité, null si non définit
	 */
	@XmlTransient
	public AmbiguityResolver getAmbiguityResolver()
	{
		return this.resolver;
	}
	
	public String generateRule(String ruleName, ConditionalTree trigger, Instruction...instructions) throws ConditionParsingException, DeviceNotFoundException, LocationNotFoundException
	{
		List<Command> commands = new ArrayList<Command>();
		for(Instruction instruction : instructions)
		{
			commands.addAll(instruction.getCommands(this));
		}
		Rule rule = new Rule(ruleName, trigger, commands);
		return rule.toOpenHabString();
	}
}
