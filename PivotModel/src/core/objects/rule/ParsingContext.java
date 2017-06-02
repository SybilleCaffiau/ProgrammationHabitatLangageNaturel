package core.objects.rule;

/**
 * Le contexte de convertion de données au format OpenHab
 */
public enum ParsingContext {
	/**
	 * Contexte conditionnel (clause "WHEN", correspondant au block de condition des règles OpenHab)
	 */
	TRIGGER,
	/**
	 * Contexte d'execution (clause "THEN", correspondant au block d'execution des règles OpenHab)
	 */
	EXECUTION
}
