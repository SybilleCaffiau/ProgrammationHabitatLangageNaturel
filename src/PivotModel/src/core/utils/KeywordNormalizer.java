package core.utils;

import java.text.Normalizer;
/**
 * @author Clément Didier
 */
public class KeywordNormalizer 
{
	/**
	 * Obtient un nouveau mot clé par suppression des accents du mot clé donné
	 * @param keyword Le mot clé disposant d'accents
	 * @return Le mot clé sans les accents
	 */
	public static String removeAccents(String keyword) 
	{
		return Normalizer.normalize(keyword, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
	}
	
	/**
	 * Obtient le mot clé normalisé (Suppression des accents et lettres en minuscules)
	 * @param keyword Le mot clé à transformer
	 * @return Le mot clé normalisé
	 */
	public static String getNormalizedKeyword(String keyword)
	{
		return removeAccents(keyword).toLowerCase();
	}
}
