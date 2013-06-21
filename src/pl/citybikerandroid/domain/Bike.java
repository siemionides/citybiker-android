package pl.citybikerandroid.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.Message;
import pl.citybikerandroid.messages.ServiceMessage;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class Bike implements Serializable {
	
	private String id;
	private String number;
	@JsonIgnore
	public final static String SERIALIZABLE_NAME = "bike";
	@JsonIgnore
	private static final long serialVersionUID = -3058330628500720941L;
	@JsonIgnore
	ArrayList<InformativeMessage> informativeMessages = null;
	@JsonIgnore
	ArrayList<ServiceMessage> serviceMessages = null;
	
	public Bike(int number) {
		this.number = Integer.toString(number);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@JsonIgnore
	public void addInformativeMessage(InformativeMessage informativeMessage) {
		if(informativeMessages == null) informativeMessages = new ArrayList<InformativeMessage>();
		informativeMessages.add(informativeMessage);
		
	}
	@JsonIgnore
	public void addServiceMessage(ServiceMessage serviceMessage) {
		if(serviceMessages == null) serviceMessages = new ArrayList<ServiceMessage>();
		serviceMessages.add(serviceMessage);
		
	}
	@JsonIgnore
	public ArrayList<InformativeMessage> getInformativeMessages() {
		return this.informativeMessages;
	}
	@JsonIgnore
	public ArrayList<ServiceMessage> getServicelMessages() {
		return this.serviceMessages;
	}
	@JsonIgnore
	public InformativeMessage getLastInformativeMessage() {
		//sorts the array and returns the message with newest timestamp
		//TODO could be optimed by having a structure (not ArrayList) that ensures natural order by Date
		ArrayList<InformativeMessage> list = new ArrayList<InformativeMessage>(this.informativeMessages);		
		Collections.sort(list, Message.MessageDateComparator);
		return list.get(0);
	}
	@JsonIgnore
	public ServiceMessage getLastServiceMessage() {
		ArrayList<ServiceMessage> list = new ArrayList<ServiceMessage>(this.serviceMessages);
		Collections.sort(list, Message.MessageDateComparator);
		return list.get(0);
	}
	
}
