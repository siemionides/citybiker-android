package pl.citybikerandroid.stations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.LogisticalMessage;
import pl.citybikerandroid.messages.Message;
import pl.citybikerandroid.messages.ServiceMessage;

/**
 * Implements serializable, as it's supposed to be sent from / to activities;
 * eg. from #01 *(welcome) to #04 (bike station)
 * @author Michal Siemionczyk michal.siemionczyk@gmail.com
 *
 */
public class BikeStation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final static String SERIALIZABLE_NAME = "bikeStation";


	public static final int MORE_THAN_FOUR = -500;
	
	private String stationName;
	
	private String stationId;
	
	private int nrBikes;
	
	ArrayList<InformativeMessage> informativeMessages = null;
	
	ArrayList<LogisticalMessage> logisticalMessages = null;
	
	ArrayList<ServiceMessage> serviceMessages = null;
	
	
	
	
	public BikeStation() {
		initialize("","",-1);
	}

	public BikeStation(String stationName, String stationId) {
		initialize(stationName,stationId,-1);
	}

	public BikeStation(String stationName, String stationId, int nrBikes) {
		initialize(stationName, stationId, nrBikes);
	}
	
	private void initialize(String stationName, String stationId, int nrBikes) {
		this.stationName = stationName;
		this.stationId = stationId;
		this.nrBikes = nrBikes;
		informativeMessages = new ArrayList<InformativeMessage>();
		logisticalMessages = new ArrayList<LogisticalMessage>();
		serviceMessages = new ArrayList<ServiceMessage>();
	}
	
	public void addInformativeMessage(InformativeMessage informativeMessage) {
		informativeMessages.add(informativeMessage);
		
	}
	
	public void addLogisticalMessage(LogisticalMessage logisticalMessage) {
		logisticalMessages.add(logisticalMessage);
	}

	public void addServiceMessage(ServiceMessage serviceMessage) {
		serviceMessages.add(serviceMessage);
		
	}
	
	public String getName() {
		return this.stationName;
	}

	public String getId() {
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
	
	public void setName(String stationName) {
		this.stationName = stationName;
	}
	
	public void setId(String id) {
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
		return list.isEmpty() ? null : list.get(0);
	}

	public LogisticalMessage getLastLogisticalMessage() {
		
		ArrayList<LogisticalMessage> list = new ArrayList<LogisticalMessage>(this.logisticalMessages);
		Collections.sort(list, Message.MessageDateComparator);
		return list.isEmpty() ? null : list.get(0);
	}

	public ServiceMessage getLastServiceMessage() {
		ArrayList<ServiceMessage> list = new ArrayList<ServiceMessage>(this.serviceMessages);
		Collections.sort(list, Message.MessageDateComparator);
		return list.isEmpty() ? null : list.get(0);
	}

}
