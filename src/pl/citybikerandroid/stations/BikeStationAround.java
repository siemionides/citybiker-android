package pl.citybikerandroid.stations;


public class BikeStationAround extends BikeStation {
	
	private double distance;

	public BikeStationAround(String stationName, int stationId) {
		super(stationName, stationId);
		// TODO Auto-generated constructor stub
	}

	public BikeStationAround(String stationName, int stationId, int nrBikes) {
		super(stationName, stationId, nrBikes);
		// TODO Auto-generated constructor stub
	}

	public BikeStationAround() {
		super();
	}


	public void setDistanceTo(double distance) {
		this.distance = distance;
	}


	public Double getDistanceTo() {
		return this.distance;
	}

	

	

	

}
