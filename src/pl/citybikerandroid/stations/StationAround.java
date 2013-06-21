package pl.citybikerandroid.stations;

import pl.citybikerandroid.domain.Station;


public class StationAround extends Station {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5965357394451725387L;
	private double distance;

	public StationAround(String stationName, String stationId) {
		super(stationName, stationId);
		// TODO Auto-generated constructor stub
	}

	public StationAround(String stationName, String stationId, int nrBikes) {
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
