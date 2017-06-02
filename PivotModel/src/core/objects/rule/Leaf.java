package core.objects.rule;

/**
 * Représente une feuille d'arbre conditionnel
 */
public abstract class Leaf extends Node 
{
	/**
	 * Obtient la représentation textuelle en règle OpenHab de l'élément 
	 * @param context Le contexte de génération de la représentation textuelle
	 * @return La représentation textuelle en règle OpenHab
	 * @throws ConditionParsingException Remontée lorsqu'une erreur de conversion survient
	 */
	public abstract String toOpenHabString(ParsingContext context) throws ConditionParsingException;
}
