package program;
import java.util.ArrayList;
import java.util.List;

import core.objects.rule.Command;
import core.objects.rule.ConditionParsingException;
import core.objects.rule.ConditionalNode;
import core.objects.rule.ConditionalTree;
import core.objects.rule.DeviceNotFoundException;
import core.objects.rule.EventLeaf;
import core.objects.rule.LocationNotFoundException;
import core.objects.rule.Rule;
import core.objects.rule.ServiceNotFoundException;
import core.objects.rule.TimeLeaf;
import core.objects.serializable.Device;
import core.utils.ConfigurationLoadingException;
import habitat.AmbiguityResolver;
import habitat.Habitat;

public class Program
{
	public static void main(String[] args) throws LocationNotFoundException, DeviceNotFoundException, ConditionParsingException, ServiceNotFoundException, ConfigurationLoadingException 
	{
		/* 		
		 * Exemple d'entrées pour :
		 * "S'il est 9h et que la lumière de la cuisine est allumée, alors éteins la télévision"
		 * 
		 * ------------------------------------------------------------------------------------------------------
		 * Il est necessaire de savoir la forme conditionnelle (représentée ici avec un arbre conditionnel)
		 * Egalement de savoir pour chaque feuille (condition unitaire), s'il s'agit d'un évènement 
		 * (objet qui change d'état) ou s'il s'agit d'un trigger temporel (temps système, démarrage systeme, ...)
		 * 
		 * Il est également nécessaire de connaître l'ensemble des commandes à executer (Appareil et Action)
		 * 
		 * Idéalement, le modèle se charge de Parser les trigger temporels, la recherche des appareils avec leur 
		 * localisation ou non et de générer dynamiquement des règles en fonctions des données entrées (Intégration
		 * de condition dans le block d'execution en cas de "AND" dans l'arbre conditionnel).
		 * ------------------------------------------------------------------------------------------------------
		 */
		
		if(args.length != 1)
			throw new ConfigurationLoadingException("Le programme attend une entrée correspondante au chemin vers le fichier de configurations.\nExemple : \"environments/config-simulation.xml\"");
		
		Habitat habitat = Habitat.load(args[0]);
		
		// Utilisation d'une classe d'appel pour résoudre les ambiguités
		AmbiguityResolver resolverExample = new AmbiguityResolverExample();
		habitat.setAmbiguityResolver(resolverExample);
		
		// CONDITION
		ConditionalNode node = new ConditionalNode("et",
				new EventLeaf("volet roulant", "arrêter"),
				new ConditionalNode("ou",
						new EventLeaf("disco", "allumer"),
						TimeLeaf.parse("midi"))
				);
		
		
		// COMMANDS
		List<Command> commands = new ArrayList<Command>();
		List<Device> devices = habitat.getDevices("camera", "cuisine");
		devices.forEach(device -> 
		{
			try { commands.addAll(device.getCommands("allumer")); }  catch (Exception e)  { System.err.println(e.getMessage()); }
		});
		
		ConditionalTree tree = new ConditionalTree(node);
		
		Rule rule = new Rule("RuleTest", tree, commands);
		System.out.println(rule.toOpenHabString());
		rule.toOpenHabRuleFile("test");
	}

}
