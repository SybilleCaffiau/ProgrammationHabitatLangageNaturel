package core.objects.serializable.containers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import core.utils.KeywordNormalizer;
/**
 * @author Clément Didier
 */
@XmlRootElement(name="keywords")
public class Keywords
{
	@XmlElement(name="keyword")
	/**
	 * Les mots clés contenus
	 */
	private List<String> keywords;
	
	/**
	 *  XML Constructor
	 */
	private Keywords()
	{
		this.keywords = new ArrayList<String>();
	}
	
	/**
	 * Constructeur de conteneur de mots clés
	 * @param keywords Les mots clés à intégrer au nouveau conteneur
	 */
	public Keywords(String...keywords)
	{
		this();
		for(String keyword : keywords)
			this.keywords.add(keyword);
		
	}
	
	/**
	 * Ajoute un nouveau mot clé dans le conteneur (prend en compte les doublons)
	 * @param keyword Le mot clé à ajouter
	 */
	public void add(String keyword)
	{
		if(!this.exists(keyword))
			this.keywords.add(keyword);
	}
	
	/**
	 * Obtient l'état d'existance d'un mot clé dans le conteneur (normalise le mot clé donné)
	 * @param keyword Le mot clé à rechercher
	 * @return True si le mot clé existe, False dans le cas contraire
	 */
	public boolean exists(String keyword)
	{
		String keywordNormalized = KeywordNormalizer.getNormalizedKeyword(keyword);
		
		for(String key : this.keywords)
		{
			if(KeywordNormalizer.getNormalizedKeyword(key).equals(keywordNormalized))
				return true;
		}
		return false;
	}
}
