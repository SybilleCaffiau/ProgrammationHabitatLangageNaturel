package core.objects.rule;

public class ConditionalNode extends Node
{
	/**
	 * Le noeud fils de gauche
	 */
	private Node childLeft;
	/**
	 * Le noeud fils de droite
	 */
	private Node childRight;
	/**
	 * Le type de condition { or, and }
	 */
	private ConditionalType type;
	
	/**
	 * Constructeur de noeud conditionnel
	 * @param type Le type de condition
	 * @param left Le noeud fils de gauche
	 * @param right Le noeud fils de droite
	 */
	public ConditionalNode(ConditionalType type, Node left, Node right)
	{
		this.childLeft = left;
		this.childRight = right;
		this.type = type;
	}
	
	/**
	 * Constructeur de noeud conditionnel
	 * @param type La représentation textuelle du type de condition
	 * @param left Le noeud fils de gauche
	 * @param right Le noeud fils de droite
	 */
	public ConditionalNode(String type, Node left, Node right)
	{
		this.childLeft = left;
		this.childRight = right;
		this.type = ConditionalType.parse(type);
	}
	
	/**
	 * Obtient le noeud enfant de gauche
	 * @return Le noeud enfant de gauche
	 */
	public Node getLeftChild()
	{
		return this.childLeft;
	}
	
	/**
	 * Obtient le noeud enfant de droite
	 * @return Le noeud enfant de droite
	 */
	public Node getRightChild()
	{
		return this.childRight;
	}
	
	/**
	 * Modifie/remplace les fils du noeud
	 * @param left Le futur noeud gauche
	 * @param right Le futur noeud droit
	 */
	public void setChilds(Node left, Node right)
	{
		this.childLeft = left;
		this.childRight = right;
	}
	
	/**
	 * Obtient le type du noeud
	 * @return Le type du noeud
	 */
	public ConditionalType getType()
	{
		return this.type;
	}
}
