package core.objects.rule;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import core.formats.openhab.OpenHabFormats;

/**
 * @author Clément Didier
 * Représente un constructeur de règles (orienté OpenHab)
 */
public class Rule 
{
	/**
	 * L'arbre conditionnel de la règle
	 */
	private ConditionalTree tree;
	
	/**
	 * Les commandes d'execution de la règle
	 */
	private List<Command> commands;
	
	/**
	 * Le nom de la règle
	 */
	private String name;
	
	/**
	 * Constructeur de règle
	 * @param name Le nom de la règle
	 * @param tree L'arbre conditionnel de la règle
	 * @param commands Les commandes d'execution de la règle
	 */
	public Rule(String name, ConditionalTree tree, List<Command> commands)
	{
		this.name = name;
		this.tree = tree;
		this.commands = commands;
	}
	
	/**
	 * Génere un nouveau fichier de règles OpenHab avec les données obtenues
	 * @param filename Le nom du nouveau fichier
	 * @throws ConditionParsingException Remontée lorsqu'une erreur de conversion survient
	 */
	public void toOpenHabRuleFile(String filename) throws ConditionParsingException
	{
		try(Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(String.format(OpenHabFormats.FILE_NAME.getFormat(), filename)), "utf-8"))) {
		   writer.write(this.toOpenHabString());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Génere les/la règle(s) OpenHab
	 * @return La représentation textuelle des/de la règle(s) OpenHab
	 * @throws ConditionParsingException Remontée lorsqu'une erreur de conversion de condition survient
	 */
	public String toOpenHabString() throws ConditionParsingException
	{
		List<EventLeaf> mulitpleObjectsEvents = this.getRecursivelyConjonctionClausesForExecutionBlock(tree.getRoot());
		
		if(tree.getRoot() instanceof ConditionalNode)
		{
			// Cas de AND en tant que "root"
			ConditionalNode node = (ConditionalNode) tree.getRoot();
			if(node.getType() == ConditionalType.AND)
			{
				ConditionalTree leftSubTree = new ConditionalTree(node.getLeftChild());
				ConditionalTree rightSubTree = new ConditionalTree(node.getRightChild());
				
				// Génére la règle utilisant le sous-arbre gauche comme condition
				ExecutionBlock executionBlock = new ExecutionBlock(rightSubTree, this.commands);
				executionBlock.addConjonctionClauses(this.getRecursivelyConjonctionClausesForExecutionBlock(leftSubTree.getRoot()));
				String result = generateStringRule(this.name, leftSubTree, executionBlock);
				
				// Ajoute la règle complémentaire utilisant le sous-arbre droit comme condition
				String name = String.format(OpenHabFormats.COMPLEMENTARY_RULE_NAME.getFormat(), this.name);
				executionBlock = new ExecutionBlock(leftSubTree, this.commands);
				executionBlock.addConjonctionClauses(this.getRecursivelyConjonctionClausesForExecutionBlock(rightSubTree.getRoot()));
				result += "\n\n" + generateStringRule(name, rightSubTree, executionBlock);
				
				// Retourne les deux règles générées
				return result;
			}
		}
		
		
		// Cas de "OR" en tant que root
		if(!mulitpleObjectsEvents.isEmpty())
		{
			return generateStringRule(this.name, tree, new ExecutionBlock(tree, this.commands));
		}
		
		ExecutionBlock executionBlock = new ExecutionBlock(this.commands);
		executionBlock.addConjonctionClauses(this.getRecursivelyConjonctionClausesForExecutionBlock(tree.getRoot()));
		return generateStringRule(this.name, tree, executionBlock);
	}
	
	/**
	 * Génére et obtient la représentation textuelle de la règle
	 * @param ruleName Le nom de la règle
	 * @param tree L'arbre conditionnel de la règle
	 * @param commands Les commandes d'execution Openhab de la règle
	 * @return La représentation textuelle de la règle
	 * @throws ConditionParsingException Remontée lorsqu'une erreur de conversion survient
	 */
	private String generateStringRule(String ruleName, ConditionalTree tree, ExecutionBlock executionBlock) throws ConditionParsingException
	{
		return String.format(OpenHabFormats.RULE_FORMAT.getFormat(), 
				ruleName,
				tree.toOpenHabString(ParsingContext.TRIGGER),
				executionBlock.toOpenHabString());
	}
	
	/**
	 * Obtient récursivement la liste des feuilles du sous-arbre determiné par le noeud donné, de type EventLeaf manipulant plusieurs appareils
	 * @param node Le noeud parent à explorer
	 * @return La liste des feuilles correspondantes à la recherche pour le noeud donné
	 */
	private List<EventLeaf> getRecursivelyConjonctionClausesForExecutionBlock(Node node)
	{
		List<EventLeaf> localResult = new ArrayList<EventLeaf>();
		
		if(node instanceof EventLeaf)
		{
			EventLeaf leaf = (EventLeaf)node;
			if(leaf.isMutlipleCommands())
			{
				localResult.add(leaf);
			}
		}
		else if(node instanceof ConditionalNode)
		{
			ConditionalNode cnode = (ConditionalNode)node;
			
			// Exploration du sous-arbre gauche
			localResult.addAll(this.getRecursivelyConjonctionClausesForExecutionBlock(cnode.getLeftChild()));
			
			// Exploration du sous-arbre droit
			localResult.addAll(this.getRecursivelyConjonctionClausesForExecutionBlock(cnode.getRightChild()));
		}
		
		return localResult;
	}
}
