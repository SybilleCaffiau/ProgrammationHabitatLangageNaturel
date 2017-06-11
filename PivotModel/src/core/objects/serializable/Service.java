package core.objects.serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import core.objects.serializable.containers.Functions;
import core.objects.serializable.containers.Keywords;
/**
 * @author Clément Didier
 */
@XmlRootElement(name="service")
public class Service extends Identifiable
{
	
	@XmlElement(name="functions")
	/**
	 * Les fonctions prises en charge par le service
	 */
	private Functions functions;
	
	/**
	 *  XML Constructor
	 */
	private Service()
	{
		super(null, null);
	}
	
	/**
	 * Constructeur de service générique (non associé à un appareil) 
	 * @param id L'identifiant unique du service
	 * @param keywords La liste de mots clés du service
	 */
	public Service(String id, Keywords keywords)
	{
		super(id, keywords);
		this.functions = new Functions();
		this.keywords.add(id);
	}
	
	public Service(String id, Keywords keywords, Functions functions)
	{
		this(id, keywords);
		this.functions = functions;
	}
	
	/**
	 * Obtient la liste des fonctions du service
	 * @return La liste des fonctions du service
	 */
	public Functions getFunctions()
	{
		return this.functions;
	}
	
	/**
	 * Obtient l'état d'existance du mot clé pour le service, ou les fonctions du service
	 * @param keyword Le mot clé recherché
	 */
	@Override
	public boolean hasKeyword(String keyword)
	{
		boolean functionsHasKeywords = false;
		for(Function function : this.functions.getList())
		{
			if(function.hasKeyword(keyword))
			{
				functionsHasKeywords = true;
				break;
			}
		}
		
		return this.keywords.exists(keyword) || functionsHasKeywords;
	}
}
