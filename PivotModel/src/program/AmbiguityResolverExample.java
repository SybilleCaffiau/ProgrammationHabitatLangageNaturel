package program;

import java.util.ArrayList;
import java.util.List;

import core.objects.rule.Command;
import core.objects.serializable.Device;
import habitat.AmbiguityResolver;

public class AmbiguityResolverExample implements AmbiguityResolver
{
	/**
	 * PREMIER CALLBACK
	 */
	@Override
	// Ambiguity resolver (ici, n'affiche que l'ambiguité et selectionne par défaut un élément pour la résoudre)
	public List<Command> multipleAssociatedServiceForSameFunctionAmbiguity(Device device, String functionKeyword, List<Command> currentResultToDisambiguate) 
	{
		List<Command> ambiguityResolverResult = new ArrayList<Command>();
		
		// TODO: Selection par défaut
		ambiguityResolverResult.add(currentResultToDisambiguate.get(0));
		
		System.err.println("==== Cas ambiguïté remontée au résolveur ====\n" +
			"Device : " + device.getId() + "\tfonction : " + functionKeyword + 
			"\nListe des items OpenHab de l'appareil, répondant à la fonction demandée :");
		currentResultToDisambiguate.forEach(cmd -> System.err.println("\t" + cmd.getOpenhabItemName()));
		System.err.println("Item OpenHab selectionné par défaut par le résolveur : " + currentResultToDisambiguate.get(0).getOpenhabItemName() + "\n=======================\n");
		
		return ambiguityResolverResult;
	}

	/**
	 * SECOND CALLBACK
	 */
	@Override
	public List<Device> multipleDevicesFoundForOneFunctionAmbiguity(String deviceKeyword, String functionKeyword, List<Device> currentResultToDisambiguate) 
	{
		System.err.println("==== Cas ambiguïté remontée au résolveur ====\nListe des appareils \"" + deviceKeyword + "\" répondants à la fonction demandée (" + functionKeyword + ") :");
		currentResultToDisambiguate.forEach(device -> System.err.println("\t" + device.getId()));
		System.err.println("Tous les appareils ont étés selectionnés par défaut par le résolveur\n=======================\n");
		
		return currentResultToDisambiguate;
	}

}
