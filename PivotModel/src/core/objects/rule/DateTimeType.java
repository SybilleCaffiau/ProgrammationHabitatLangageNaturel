package core.objects.rule;

public enum DateTimeType 
{
	/**
	 * Type par défaut
	 */
	NONE(0),
	/**
	 * Type de DateTime "seconde"
	 */
	SECONDS(1),
	/**
	 * Type de DateTime "minute"
	 */
	MINUTES(2),
	/**
	 * Type de DateTime "heure"
	 */
	HOURS(4);
	
	/**
	 * L'identifiant du type permettant de réaliser des opérations de bitwise
	 */
	private int id;
	
	private DateTimeType(int id) 
	{
		this.id = id;
	}
	
	/**
	 * Obtient l'identifiant du type permettant de réaliser des opérations de bitwise
	 * @return L'identifiant du type permettant de réaliser des opérations de bitwise
	 */
	public int getId()
	{
		return this.id;
	}
}
