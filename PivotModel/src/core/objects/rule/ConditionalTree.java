package core.objects.rule;

import java.util.ArrayList;
import java.util.List;

import core.formats.openhab.OpenHabFormats;

public class ConditionalTree 
{
	/**
	 * Le noeud racine
	 */
	private Node root;
	
	/**
	 * Constructeur d'arbre conditionnel
	 * @param root Le noeud racine de l'arbre
	 */
	public ConditionalTree(Node root)
	{
		this.root = root;
	}
	
	/**
	 * Obtient le noeud racine de l'arbre
	 * @return Le noeud racine de l'arbre
	 */
	public Node getRoot()
	{
		return this.root;
	}
	
	/**
	 * Réalise un échange des deux enfants du noeud racine
	 * L'enfant de gauche devient celui de droite et inversement
	 * @return Le resultat du swap, False s'il s'agit d'une feuille, 
	 * True s'il s'agit d'un noeud et que le swap s'est bien effectué
	 */
	public boolean trySwapChildsOfRoot()
	{
		if(this.root instanceof ConditionalNode)
		{
			ConditionalNode node = (ConditionalNode) this.root;
			node.setChilds(node.getRightChild(), node.getLeftChild());
			return true;
		}
		return false;
	}
	
	/**
	 * Obtient l'arbre conditionnel diposant des noeuds enfants échangés du noeud racine (fils gauche étant devenu le fils droit et inversement)
	 * @return L'arbre conditionnel avec les noeuds enfants échangés du noeud racine
	 */
	public ConditionalTree swappedRootChildsTree()
	{
		ConditionalTree result = new ConditionalTree(this.root);
		result.trySwapChildsOfRoot();
		
		return result;
	}
	
	/**
	 * Obtient la représentation textuelle de l'arbre conditionnel dans le context donné
	 * @param context Le contexte de génération de la représentation
	 * @return La représentation textuelle de l'arbre
	 * @throws ConditionParsingException Remontée lorsqu'une limite du modèle n'a pas été respectée, ou qu'une erreur de conversion est survenue
	 */
	public String toOpenHabString(ParsingContext context) throws ConditionParsingException
	{	
		StringBuilder builder = new StringBuilder();
		
		// Génération de la disjonction
		List<Leaf> leafs = getRecursivelyLeafsOfNode(this.getRoot());
		for(int i = 0; i < leafs.size(); i++)
		{
			builder.append(leafs.get(i).toOpenHabString(context));
			
			if(i < leafs.size() - 1)
				builder.append(context == ParsingContext.TRIGGER ? 
					OpenHabFormats.CONDITION_OR_TRIGGER_CONTEXT.getFormat() :
					OpenHabFormats.CONDITION_OR_EXECUTION_CONTEXT.getFormat());
				
		}
		
		return builder.toString();
	}
	
	/**
	 * Obtient récursivement les feuilles du sous-arbre determiné par le noeud donné 
	 * @param node Le noeud determinant le sous-arbre à explorer
	 * @return Les feuilles du sous-arbre
	 * @throws ConditionParsingException Remontée lorsqu'une limite du modèle n'est pas respectée
	 */
	private List<Leaf> getRecursivelyLeafsOfNode(Node node) throws ConditionParsingException
	{
		List<Leaf> result = new ArrayList<Leaf>();
		
		if(node instanceof Leaf)
		{
			result.add((Leaf) node);
			return result;
		}
		
		ConditionalNode cnode = (ConditionalNode) node;
		if(cnode.getType() == ConditionalType.AND)
			throw new ConditionParsingException("Limites du modèle non respectées : Utilisation d'un \"and\" dans l'un des noeuds fils de l'arbre");
		
		result.addAll(this.getRecursivelyLeafsOfNode(cnode.getLeftChild()));
		result.addAll(this.getRecursivelyLeafsOfNode(cnode.getRightChild()));
		
		return result;
	}
}
