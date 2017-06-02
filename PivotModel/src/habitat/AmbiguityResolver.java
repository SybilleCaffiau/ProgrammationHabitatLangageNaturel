package habitat;

import java.util.List;

import core.objects.rule.Command;
import core.objects.serializable.Device;

/**
 * Interface permettant de donner la main à un élément externe au modèle, afin de résoudre une ambiguité et de retourner à la position de traitement actuelle
 */
public interface AmbiguityResolver 
{
	/**
	 * Fonction de rappel lorsqu'une ambiguité liée à des instances multiples pour une même fonction, pour un même appareil survient
	 * @param device L'appareil demandé
	 * @param functionKeyword Le mot clé de la fonction demandée
	 * @param currentResultToDisambiguate Le résultat courant à désambiguiser
	 * @return Le résultat désambiguisé
	 */
	public List<Command> multipleAssociatedServiceForSameFunctionAmbiguity(Device device, String functionKeyword, List<Command> currentResultToDisambiguate);

	/**
	 * Fonction de rappel lorqu'une ambiguité liée à la quantification des objets de la requête survient
	 * @param deviceKeyword Le mot clé de/des appareils recherchés
	 * @param functionKeyword Le mot clé de la fonction demandée
	 * @param currentResultToDisambiguate Les appareils trouvés, à selectionner pour désambiguisation
	 * @return Le résultat désambiguisé
	 */
	public List<Device> multipleDevicesFoundForOneFunctionAmbiguity(String deviceKeyword, String functionKeyword, List<Device> currentResultToDisambiguate);
}
