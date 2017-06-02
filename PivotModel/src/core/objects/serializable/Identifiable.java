package core.objects.serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import core.objects.serializable.containers.Keywords;

public abstract class Identifiable 
{
	/**
	 * L'identifiant de l'élément
	 */
	@XmlAttribute(name="id")
	private String id;
	
	/**
	 * Les mots clés de l'élément
	 */
	@XmlElement(name="keywords")
	protected Keywords keywords;
	
	/**
	 * Constructeur d'objets identifiables
	 * @param id L'identifiant de l'objet
	 * @param keywords Les mots clés de l'objet
	 */
	public Identifiable(String id, Keywords keywords)
	{
		this.id = id;
		this.keywords = keywords;
	}
	
	/**
	 * Obtient l'identifiant unique de l'élément
	 * @return L'identifiant unique de l'élément
	 */
	public String getId()
	{
		return this.id;
	}
	
	/**
	 * Obtient la liste de mots clés de l'élément
	 * @return La liste de mots clés de l'élément
	 */
	public Keywords getKeywords()
	{
		return this.keywords;
	}
	
	/**
	 * Obtient le résultat d'appartenance du mot clé donné par rapport à la liste de mots clés de l'élément
	 * @param keyword Le mot clé à tester
	 * @return True si le mot clé appartient à la liste, False dans le cas contraire
	 */
	public boolean hasKeyword(String keyword)
	{
		return this.keywords.exists(keyword);
	}
}
