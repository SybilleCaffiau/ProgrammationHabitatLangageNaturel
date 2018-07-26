package core.objects.rule;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Clément Didier
 */
public enum ConditionalType {
	/**
	 * La condition "OU" permettant des disjonctions
	 */
	OR("or", "ou"),
	/**
	 * La condition "ET" permettant des conjonctions
	 */
	AND("and", "et");
	
	/**
	 * Les mots clés du type conditionnel
	 */
	private List<String> keywords;
	
	ConditionalType(String... keywords)
	{
		this.keywords = new ArrayList<String>();
		for(String keyword : keywords)
			this.keywords.add(keyword);
	}
	
	/**
	 * Reconnait et converti un mot clé au type conditionnel relatif
	 * @param keyword Le mot clé conditionnel à reconaître et convertir
	 * @return OR si mot clé de disjonction reconnu, AND si mot clé de conjonction reconnu, null dans les autres cas
	 */
	public static ConditionalType parse(String keyword)
	{
		if(OR.keywords.contains(keyword.toLowerCase()))
		{
			return OR;
		}
		else if(AND.keywords.contains(keyword.toLowerCase()))
		{
			return AND;
		}
		
		return null;
	}
}
