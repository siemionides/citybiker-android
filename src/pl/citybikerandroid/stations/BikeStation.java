package pl.citybikerandroid.stations;

import java.util.ArrayList;

import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.LogisticalMessage;
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

	public Iterable<InformativeMessage> getInformativeMessages() {
		return this.informativeMessages;
	}

	public Iterable<LogisticalMessage> getLogisticalMessages() {
		return this.logisticalMessages;
	}

	public Iterable<ServiceMessage> getServicelMessages() {
		return this.serviceMessages;
	}
	
	public void setId(int id) {
		this.stationId = id;
		
	}

	public void setNrBikes(int nrBikes) {
		this.nrBikes = nrBikes;
	}

}
