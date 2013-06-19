package pl.citybikerandroid.messages;

import java.util.Date;

import pl.citybikerandroid.stations.BikeStationAround;
import pl.citybikerandroid.stations.BikeStation;

public class LogisticalMessage extends Message {

	public static final int GOING_TO = 1;
	
	public static int TIME_BETWEEN = 2;

//	public LogisticalMessage(String text, int logMesType,
//			BikeStation stationFrom, BikeStation stationTo) {
//		
//		super(text);
//		// TODO Auto-generated constructor stub
//	}

	/**
	 * 
	 * @param text
	 * @param logMesType
	 * @param bikeStation
	 * @param station
	 * @param time [in secs]
	 */
	public LogisticalMessage(String text, int logMesType,
			BikeStation stationFrom, BikeStation stationTo, int time) {
		
		super(text);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param text
	 * @param logMesType
	 * @param stationFrom
	 * @param stationTo
	 * @param time
	 * @param messageDate date of message / a timestamp
	 */
	public LogisticalMessage(String text, int logMesType,
			BikeStation stationFrom, BikeStation stationTo, int time, Date messageDate) {
		
		super(text, messageDate);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * 
	 * @param text
	 * @param authorName
	 * @param logMesType
	 * @param stationFrom
	 * @param stationTo
	 * @param time [in seconds]
	 */
	public LogisticalMessage(String text, String authorName,  int logMesType,
			BikeStation stationFrom, BikeStation stationTo, int time) {
		
		super(text, authorName);
		// TODO Auto-generated constructor stub
	}


}
