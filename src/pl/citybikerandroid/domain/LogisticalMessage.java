package pl.citybikerandroid.domain;

import java.util.Date;

public class LogisticalMessage extends Message {

	private static final long serialVersionUID = -9055041264671786474L;

	public static final int GOING_TO = 1;
	
	public static int TIME_BETWEEN = 2;

	public LogisticalMessage(String text, int logMesType,
			Station stationFrom, Station stationTo, int time) {
		super(text);
	}
	
	public LogisticalMessage(String text, int logMesType,
			Station stationFrom, Station stationTo, int time, Date messageDate) {
		super(text, messageDate);
	}
	
	public LogisticalMessage(String text, String authorName,  int logMesType,
			Station stationFrom, Station stationTo, int time) {
		super(text, authorName);
	}


}
