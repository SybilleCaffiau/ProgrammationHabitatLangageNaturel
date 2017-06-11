package core.objects.serializable.containers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import core.objects.serializable.Room;
/**
 * @author Clément Didier
 */
@XmlRootElement(name="rooms")
public class Rooms implements Container<Room>
{
	@XmlElement(name="room")
	/**
	 * Les localisations contenues par le conteneur
	 */
	private List<Room> rooms;
	
	/**
	 * XML Constructor
	 */
	private Rooms()
	{
		this.rooms = new ArrayList<Room>();
	}
	
	/**
	 * Constructeur de conteneur de localisations
	 * @param rooms Les localisations à intégrer au nouveau conteneur
	 */
	public Rooms(Room...rooms)
	{
		this();
		
		for(Room room : rooms)
			this.rooms.add(room);
	}
	
	@Override
	public List<Room> getList()
	{
		return this.rooms;
	}

	@Override
	public Rooms getById(String id) {
		Rooms rooms = new Rooms();
		
		for(Room room : this.rooms)
			if(room.getId().equals(id))
				rooms.add(room);
		
		return rooms;
	}

	@Override
	public boolean isEmpty() {
		return this.rooms.isEmpty();
	}

	@Override
	public void add(Room element) {
		this.rooms.add(element);
	}
}
