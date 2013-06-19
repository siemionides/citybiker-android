package pl.citybikerandroid.stations;

import pl.citybikerandroid.messages.InformativeMessage;
import pl.citybikerandroid.messages.LogisticalMessage;
import pl.citybikerandroid.messages.ServiceMessage;

public class StationAround extends BikeStation {
	
	private double distance;

	public StationAround(String stationName, int stationId) {
		super(stationName, stationId);
		// TODO Auto-generated constructor stub
	}

	public StationAround(String stationName, int stationId, int nrBikes) {
		super(stationName, stationId, nrBikes);
		// TODO Auto-generated constructor stub
	}

	public StationAround() {
		super();
	}


	public void setDistanceTo(double distance) {
		this.distance = distance;
	}


	public Double getDistanceTo() {
		return this.distance;
	}

	

	

	

}
