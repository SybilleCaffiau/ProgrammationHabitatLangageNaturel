package core.objects.rule;

import java.util.ArrayList;
import java.util.List;

import core.formats.openhab.OpenHabFormats;
/**
 * @author Clément Didier
 */
public class ExecutionBlock 
{
	private ConditionalTree conditionalTree;
	private List<EventLeaf> conjonctions;// TODO: EventLEaf seulement ?
	private List<Command> executions;
	
	/**
	 * Constructeur de bloc d'execution
	 * @param tree L'abre conditionnel correspondant à la condition du bloc
	 * @param commands Les instructions à executer du bloc, lorsque la condition est validée
	 */
	public ExecutionBlock(ConditionalTree tree, List<Command> commands)
	{
		this.conditionalTree = tree;
		this.conjonctions = new ArrayList<EventLeaf>();
		this.executions = commands;
	}
	
	/**
	 * Constructeur de bloc d'execution
	 * @param commands Les instructions à executer du bloc
	 */
	public ExecutionBlock(List<Command> commands)
	{
		this.conditionalTree = null;
		this.conjonctions = new ArrayList<EventLeaf>();
		this.executions = commands;
	}
	
	public void addConjonctionClauses(List<EventLeaf> leafs)
	{
		this.conjonctions.addAll(leafs);
	}
	
	public String toOpenHabString() throws ConditionParsingException
	{
		StringBuilder builder = new StringBuilder();
		StringBuilder conjonctionsBuilder = new StringBuilder();
		StringBuilder executionBuilder = new StringBuilder();
		
		this.executions.forEach(instruction -> executionBuilder.append(
				String.format(OpenHabFormats.COMMAND.getFormat(), 
						instruction.getOpenhabItemName(),
						instruction.getOpenhabCommand())));
		
		if(!this.conjonctions.isEmpty())
		{
			for(int i = 0; i <this.conjonctions.size(); i++)
			{
				conjonctionsBuilder.append(this.conjonctions.get(i).toOpenHabString(ParsingContext.EXECUTION));
				
				if(i != this.conjonctions.size() - 1)
					conjonctionsBuilder.append(OpenHabFormats.CONDITION_AND_EXECUTION_CONTEXT.getFormat());
			}
		}
		
		if(this.conditionalTree != null)
		{
			String condition = this.conjonctions.isEmpty() ? 
					this.conditionalTree.toOpenHabString(ParsingContext.EXECUTION) :
					String.format(OpenHabFormats.CONDITION_BLOCK.getFormat(), 
					this.conditionalTree.toOpenHabString(ParsingContext.EXECUTION)) + 
					OpenHabFormats.CONDITION_AND_EXECUTION_CONTEXT.getFormat();
			
			if(!this.conjonctions.isEmpty())
			{
				condition += /*String.format(OpenHabFormats.CONDITION_BLOCK.getFormat(), */
						conjonctionsBuilder.toString();//);
			}
			
			builder.append(String.format(OpenHabFormats.EXECUTION_CONDITION.getFormat(), condition, executionBuilder.toString()));
		}
		else
		{
			builder.append(executionBuilder.toString());
		}
		
		return builder.toString();
	}
}
