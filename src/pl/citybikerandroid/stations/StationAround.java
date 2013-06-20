package pl.citybikerandroid.stations;


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
