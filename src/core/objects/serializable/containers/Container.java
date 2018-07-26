package core.objects.serializable.containers;

import java.util.List;
/**
 * @author Clément Didier
 */
public interface Container<E>
{	
	/**
	 * Obtient la liste des éléments du conteneur
	 * @return La liste des éléments contenu
	 */
	public List<E> getList();
	
	/**
	 * Obtient les éléments du conteneur ayant l'identifiant id
	 * @param id L'identifiant des éléments recherchés
	 * @return Les éléments recherchés si existants
	 */
	public Container<E> getById(String id);
	
	/**
	 * Obtient l'état de remplissage du conteneur
	 * @return True si le conteneur ne contient aucun élément, False dans le cas contraire
	 */
	public boolean isEmpty();
	
	/**
	 * Ajoute un nouvel élément au conteneur
	 * @param element Le nouvel élément à ajouter
	 */
	public void add(E element);
}
