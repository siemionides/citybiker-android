package pl.citybikerandroid.stations;

import java.util.ArrayList;
import java.util.Collections;

import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.LogisticalMessage;
import pl.citybikerandroid.messages.Message;
import pl.citybikerandroid.messages.ServiceMessage;

public class BikeStation {
	
	public static final int MORE_THAN_FOUR = -500;
	
	private String stationName;
	
	private int stationId;
	
	private int nrBikes;
	
	ArrayList<InformativeMessage> informativeMessages = null;
	
	ArrayList<LogisticalMessage> logisticalMessages = null;
	
	ArrayList<ServiceMessage> serviceMessages = null;
	
	
	
	
	public BikeStation() {
		this.stationName = "";
		this.stationId = -1;
		this.nrBikes = -1;
	}

	public BikeStation(String stationName, int stationId) {
		this.stationName = stationName;
		this.stationId = stationId;
	}

	public BikeStation(String stationName, int stationId, int nrBikes) {
		this(stationName, stationId);
		this.nrBikes = nrBikes;
	}


	public void setName(String stationName) {
		this.stationName = stationName;
	}
	
	public void addInformativeMessage(InformativeMessage informativeMessage) {
		if(informativeMessages == null) informativeMessages = new ArrayList<InformativeMessage>();
		informativeMessages.add(informativeMessage);
		
	}
	
	public void addLogisticalMessage(LogisticalMessage logisticalMessage) {
		if(logisticalMessages == null) logisticalMessages = new ArrayList<LogisticalMessage>();
		logisticalMessages.add(logisticalMessage);
	}

	public void addServiceMessage(ServiceMessage serviceMessage) {
		if(serviceMessages == null) serviceMessages = new ArrayList<ServiceMessage>();
		serviceMessages.add(serviceMessage);
		
	}
	
	public String getName() {
		return this.stationName;
	}

	public int getId() {
		return this.stationId;
	}

	public int getNrBikes() {
		return this.nrBikes;
	}

	public ArrayList<InformativeMessage> getInformativeMessages() {
		return this.informativeMessages;
	}
	
	//public InformativeMessage get 

	public ArrayList<LogisticalMessage> getLogisticalMessages() {
		return this.logisticalMessages;
	}

	public ArrayList<ServiceMessage> getServicelMessages() {
		return this.serviceMessages;
	}
	
	public void setId(int id) {
		this.stationId = id;
		
	}

	public void setNrBikes(int nrBikes) {
		this.nrBikes = nrBikes;
	}

	public InformativeMessage getLastInformativeMessage() {
		
		//sorts the array and returns the message with newest timestamp
		//TODO could be optimed by having a structure (not ArrayList) that ensures natural order by Date
		
		ArrayList<InformativeMessage> list = new ArrayList<InformativeMessage>(this.informativeMessages);
		
		Collections.sort(list, Message.MessageDateComparator);
		
		//return frst
		return list.get(0);
	}

	public LogisticalMessage getLastLogisticalMessage() {
		
		ArrayList<LogisticalMessage> list = new ArrayList<LogisticalMessage>(this.logisticalMessages);
		Collections.sort(list, Message.MessageDateComparator);
		
		//return first - the biggest
		return list.get(0);
	}

	public ServiceMessage getLastServiceMessage() {
		ArrayList<ServiceMessage> list = new ArrayList<ServiceMessage>(this.serviceMessages);
		Collections.sort(list, Message.MessageDateComparator);
		
		//return first - the biggest
		return list.get(0);
		
	}

}
