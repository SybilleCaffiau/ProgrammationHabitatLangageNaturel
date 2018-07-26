package core.objects.serializable.containers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import core.objects.serializable.Function;
/**
 * @author Clément Didier
 */
@XmlRootElement(name="functions")
public class Functions implements Container<Function>
{
	
	@XmlElement(name="function")
	/**
	 * Les fonctions du conteneur
	 */
	private List<Function> functions;
	
	/**
	 *  XML Constructor
	 */
	private Functions()
	{
		this.functions = new ArrayList<Function>();
	}
	
	/**
	 * Constructeur de conteneur de fonctions
	 * @param functions Les fonctions à integrer au nouveau conteneur
	 */
	public Functions(Function...functions)
	{
		this();
		for(Function function : functions)
			this.functions.add(function);
	}

	@Override
	public Functions getById(String id) {
		Functions functions = new Functions();
		
		for(Function function : this.functions)
			if(function.getId().equals(id))
				functions.add(function);
		
		return functions;
	}

	@Override
	public List<Function> getList() {
		return this.functions;
	}

	@Override
	public boolean isEmpty() {
		return this.functions.isEmpty();
	}

	@Override
	public void add(Function element) {
		this.functions.add(element);
	}
}
