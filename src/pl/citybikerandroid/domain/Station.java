package pl.citybikerandroid.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.LogisticalMessage;
import pl.citybikerandroid.messages.Message;
import pl.citybikerandroid.messages.ServiceMessage;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class Station implements Serializable {

	private String id;
	private String number;
	private String location;
	private int bicycles;
	private int stands;
	private String operator;
	private String district;
	private double x;
	private double y;
	
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	public final static String SERIALIZABLE_NAME = "bikeStation";
	@JsonIgnore
	public static final int MORE_THAN_FOUR = -500;
	@JsonIgnore
	ArrayList<InformativeMessage> informativeMessages = null;
	@JsonIgnore
	ArrayList<LogisticalMessage> logisticalMessages = null;
	@JsonIgnore
	ArrayList<ServiceMessage> serviceMessages = null;
	
	public Station() {
		initialize("", "", -1);
	}

	public Station(String location, String id) {
		initialize(location, id, -1);
	}

	public Station(String location, String id, int bicycles) {
		initialize(location, id, bicycles);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getBicycles() {
		return bicycles;
	}

	public void setBicycles(int bicycles) {
		this.bicycles = bicycles;
	}

	public int getStands() {
		return stands;
	}

	public void setStands(int stands) {
		this.stands = stands;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private void initialize(String location, String id, int bicycles) {
		this.location = location;
		this.id = id;
		this.bicycles = bicycles;
		informativeMessages = new ArrayList<InformativeMessage>();
		logisticalMessages = new ArrayList<LogisticalMessage>();
		serviceMessages = new ArrayList<ServiceMessage>();
	}

	@JsonIgnore
	public void addInformativeMessage(InformativeMessage informativeMessage) {
		informativeMessages.add(informativeMessage);
	}
	@JsonIgnore
	public void addLogisticalMessage(LogisticalMessage logisticalMessage) {
		logisticalMessages.add(logisticalMessage);
	}
	@JsonIgnore
	public void addServiceMessage(ServiceMessage serviceMessage) {
		serviceMessages.add(serviceMessage);

	}
	@JsonIgnore
	public ArrayList<InformativeMessage> getInformativeMessages() {
		return this.informativeMessages;
	}
	@JsonIgnore
	public ArrayList<LogisticalMessage> getLogisticalMessages() {
		return this.logisticalMessages;
	}
	@JsonIgnore
	public ArrayList<ServiceMessage> getServicelMessages() {
		return this.serviceMessages;
	}
	@JsonIgnore
	public InformativeMessage getLastInformativeMessage() {
		// TODO could be optimed by having a structure (not ArrayList) that
		// ensures natural order by Date
		ArrayList<InformativeMessage> list = new ArrayList<InformativeMessage>(
				this.informativeMessages);
		Collections.sort(list, Message.MessageDateComparator);
		return list.isEmpty() ? null : list.get(0);
	}
	@JsonIgnore
	public LogisticalMessage getLastLogisticalMessage() {

		ArrayList<LogisticalMessage> list = new ArrayList<LogisticalMessage>(
				this.logisticalMessages);
		Collections.sort(list, Message.MessageDateComparator);
		return list.isEmpty() ? null : list.get(0);
	}
	@JsonIgnore
	public ServiceMessage getLastServiceMessage() {
		ArrayList<ServiceMessage> list = new ArrayList<ServiceMessage>(
				this.serviceMessages);
		Collections.sort(list, Message.MessageDateComparator);
		return list.isEmpty() ? null : list.get(0);
	}

}
