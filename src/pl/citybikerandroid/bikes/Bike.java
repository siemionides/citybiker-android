package pl.citybikerandroid.bikes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.LogisticalMessage;
import pl.citybikerandroid.messages.Message;
import pl.citybikerandroid.messages.ServiceMessage;

/**
 * Represents bike object in App.
 * 
 * @author Michal Siemionczyk michal.siemionczyk@gmail.com
 *
 */
public class Bike implements Serializable {
	
	public final static String SERIALIZABLE_NAME = "bike";

	/**
	 * Real bike ID (not any internal Mongo DB shit)
	 */
	private int bikeId;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	public static final int MORE_THAN_FOUR = -500;
	
	ArrayList<InformativeMessage> informativeMessages = null;
	
	ArrayList<ServiceMessage> serviceMessages = null;
	
	
	
	
	public Bike() {
	}

	public Bike(int bikeId) {
		this.bikeId = bikeId;
	}


	public void addInformativeMessage(InformativeMessage informativeMessage) {
		if(informativeMessages == null) informativeMessages = new ArrayList<InformativeMessage>();
		informativeMessages.add(informativeMessage);
		
	}
	
	public void addServiceMessage(ServiceMessage serviceMessage) {
		if(serviceMessages == null) serviceMessages = new ArrayList<ServiceMessage>();
		serviceMessages.add(serviceMessage);
		
	}
	

	public int getId() {
		return this.bikeId;
	}


	public ArrayList<InformativeMessage> getInformativeMessages() {
		return this.informativeMessages;
	}
	
	//public InformativeMessage get 


	public ArrayList<ServiceMessage> getServicelMessages() {
		return this.serviceMessages;
	}
	
	/**
	 * 
	 * @param id the real BIKE ID
	 */
	public void setId(int id) {
		this.bikeId = id;
		
	}

	public InformativeMessage getLastInformativeMessage() {
		
		//sorts the array and returns the message with newest timestamp
		//TODO could be optimed by having a structure (not ArrayList) that ensures natural order by Date
		
		ArrayList<InformativeMessage> list = new ArrayList<InformativeMessage>(this.informativeMessages);
		
		Collections.sort(list, Message.MessageDateComparator);
		
		//return frst
		return list.get(0);
	}

	public ServiceMessage getLastServiceMessage() {
		ArrayList<ServiceMessage> list = new ArrayList<ServiceMessage>(this.serviceMessages);
		Collections.sort(list, Message.MessageDateComparator);
		
		//return first - the biggest
		return list.get(0);
		
	}
}
