package pl.citybikerandroid.stations;


public class BikeStationAround extends BikeStation {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8480942044793914986L;
	private double distance;

	public BikeStationAround(String stationName, String stationId) {
		super(stationName, stationId);
		// TODO Auto-generated constructor stub
	}

	public BikeStationAround(String stationName, String stationId, int nrBikes) {
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
