package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.objects.rule.ConditionParsingException;
import core.objects.rule.ConditionalTree;
import core.objects.rule.DeviceNotFoundException;
import core.objects.rule.EventLeaf;
import core.objects.rule.LocationNotFoundException;
import core.objects.rule.ServiceNotFoundException;
import core.objects.rule.TimeLeaf;
import habitat.Habitat;
import habitat.Instruction;
/**
 * @author Clément Didier
 */
public class RulesGenerationTests 
{
	private Habitat habitat;
	
	@Before
	public void initialize()
	{
		this.habitat = Habitat.load("environnements/config-simulation.xml");
	}
	
	@Test
	public void sampleRule1Test() throws ConditionParsingException, DeviceNotFoundException, LocationNotFoundException, ServiceNotFoundException
	{
		ConditionalTree tree = new ConditionalTree(new EventLeaf("tv", "cuisine", "allumer"));
		String result = this.habitat.generateRule("test1", tree, new Instruction("lampe", "éteindre"));

		String resultatAttendu = 
				"rule test1"
				+ "\nwhen"
				+ "\nItem tv_cuisine_power changed to ON"
				+ "\nthen\n"
				+ "sendCommand(lampe_cuisine, OFF)"
				+ "\nend";
		
		resultatAttendu = resultatAttendu.replaceAll("[ ()\t]", "");
		result = result.replaceAll("[ ()\t]", "");
		
		assertEquals("Règle générée non valide", resultatAttendu.replaceAll("\n", ""), result.replaceAll("\n", ""));
	}
	
	@Test(expected=DeviceNotFoundException.class)
	public void deviceNotFoundAttemptTest() throws ConditionParsingException, DeviceNotFoundException, LocationNotFoundException, ServiceNotFoundException
	{
		ConditionalTree tree = new ConditionalTree(new EventLeaf("volet", "allumer"));
		this.habitat.generateRule("test", tree, new Instruction("lampe", "éteindre"));
		
		fail("Exception non retournée");
	}
	
	@Test(expected=ServiceNotFoundException.class)
	public void serviceNotFoundAttemptTest() throws ConditionParsingException, DeviceNotFoundException, LocationNotFoundException, ServiceNotFoundException
	{
		ConditionalTree tree = new ConditionalTree(new EventLeaf("tv", "changer"));
		this.habitat.generateRule("test", tree, new Instruction("lampe", "éteindre"));
		
		fail("Exception non retournée");
	}
	
	@Test(expected=LocationNotFoundException.class)
	public void LocationNotFoundAttemptTest() throws ConditionParsingException, DeviceNotFoundException, LocationNotFoundException, ServiceNotFoundException
	{
		ConditionalTree tree = new ConditionalTree(new EventLeaf("tv", "toilettes", "changer"));
		this.habitat.generateRule("test", tree, new Instruction("lampe", "éteindre"));
		
		fail("Exception non retournée");
	}
	
	@Test(expected=ConditionParsingException.class)
	public void ConditionParsingAttemptTest() throws ConditionParsingException, DeviceNotFoundException, LocationNotFoundException, ServiceNotFoundException
	{
		ConditionalTree tree = new ConditionalTree(TimeLeaf.parse("1356s"));
		this.habitat.generateRule("test", tree, new Instruction("lampe", "éteindre"));
		
		fail("Exception non retournée");
	}

	@Test
	public void rule2Test() throws ConditionParsingException, DeviceNotFoundException, LocationNotFoundException, ServiceNotFoundException
	{
		ConditionalTree tree = new ConditionalTree(new EventLeaf("tv", "allumer"));
		String result = this.habitat.generateRule("test2", tree, new Instruction("lampe", "éteindre"));

		String resultatAttendu = 
				"rule test2"
				+ "\nwhen"
				+ "\nItem tv_cuisine_power changed to ON or"
				+ "\nItem tv_power changed to ON or"
				+ "\nItem tv_desert changed to ON"
				+ "\nthen\n"
				+ "\nif(tv_cuisine_power.state == ON && tv_power.state == ON && tv_desert.state == ON) {"
				+ "sendCommand(lampe_cuisine, OFF)\n}"
				+ "\nend";
		
		resultatAttendu = resultatAttendu.replaceAll("[ ()\t]", "");
		result = result.replaceAll("[ ()\t]", "");
		
		assertEquals("Règle générée non valide", resultatAttendu.replaceAll("\n", ""), result.replaceAll("\n", ""));
	}
	
	@Test
	public void rule3Test() throws ConditionParsingException, DeviceNotFoundException, LocationNotFoundException, ServiceNotFoundException
	{
		ConditionalTree tree = new ConditionalTree(new EventLeaf("tv", "allumer"));
		String result = this.habitat.generateRule("test3", tree, new Instruction("tv", "monter"));

		String resultatAttendu = 
				"rule test3"
				+ "\nwhen"
				+ "\nItem tv_cuisine_power changed to ON or"
				+ "\nItem tv_power changed to ON or"
				+ "\nItem tv_desert changed to ON"
				+ "\nthen\n"
				+ "\nif(tv_cuisine_power.state == ON && tv_power.state == ON && tv_desert.state == ON) {"
				+ "sendCommand(tv_volume, UP)\n}"
				+ "\nend";
		
		resultatAttendu = resultatAttendu.replaceAll("[ ()\t]", "");
		result = result.replaceAll("[ ()\t]", "");
		
		assertEquals("Règle générée non valide", resultatAttendu.replaceAll("\n", ""), result.replaceAll("\n", ""));
	}
	
	@Test
	public void rule4Test() throws ConditionParsingException, DeviceNotFoundException, LocationNotFoundException, ServiceNotFoundException
	{
		ConditionalTree tree = new ConditionalTree(new EventLeaf("tv", "salon", "allumer"));
		String result = this.habitat.generateRule("test4", tree, new Instruction("tv", "salon", "monter"));

		String resultatAttendu = 
				"rule test4"
				+ "\nwhen"
				+ "\nItem tv_power changed to ON or"
				+ "\nItem tv_desert changed to ON"
				+ "\nthen\n"
				+ "if(tv_power.state == ON && tv_desert.state == ON) {"
				+ "sendCommand(tv_volume, UP)\n}"
				+ "\nend";
		
		resultatAttendu = resultatAttendu.replaceAll("[ ()\t]", "");
		result = result.replaceAll("[ ()\t]", "");
		
		assertEquals("Règle générée non valide", resultatAttendu.replaceAll("\n", ""), result.replaceAll("\n", ""));
	}
}
