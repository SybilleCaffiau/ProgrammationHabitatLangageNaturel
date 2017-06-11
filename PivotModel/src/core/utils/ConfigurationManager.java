package core.utils;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import habitat.Habitat;

/**
 * @author Clément Didier
 * Manageur de fichier de configuration
 */
public class ConfigurationManager 
{	
	/**
	 * Le chemin vers le fichier de configuration
	 */
	private String xmlFileName;
	
	/**
	 * Constructeur de manageur de fichier de configuration du modèle
	 * @param xmlFileName Le chemin vers le fichier de configuration
	 */
	public ConfigurationManager(String xmlFileName)
	{
		this.xmlFileName = xmlFileName;
	}
	
	/**
	 * Obtient l'habitat représenté par le fichier de configuration, par déserialisation
	 * @return L'habitat représenté par le fichier de configuration
	 * @throws JAXBException Remontée lorsqu'une erreur de deserialisation survient
	 */
	public Habitat unmarshall() throws JAXBException
	{
		JAXBContext jc = JAXBContext.newInstance(Habitat.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Habitat home = (Habitat) unmarshaller.unmarshal(new File(this.xmlFileName));
        
        return home;
	}
	
	/***
	 * Enregitre la représentation de l'habitat dans un fichier, par serialisation
	 * @param habitat L'habitat à enregistrer
	 * @param filename Le fichier de sauvegarde
	 * @throws JAXBException Remontée lorsqu'une erreur de serialisation survient
	 */
	@Deprecated
	public void marshall(Habitat habitat, String filename) throws JAXBException
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(Habitat.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	    jaxbMarshaller.marshal(habitat, new File(filename));
	}
}
