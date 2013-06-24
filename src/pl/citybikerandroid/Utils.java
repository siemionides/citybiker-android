package pl.citybikerandroid;

public class Utils {

	private static final double degreesToRadians = Math.PI / 180;
	private static final double radiansToKm = 6371;

	/**
	 * longitude - west-east latitude - north-south
	 * 
	 * @param lon1
	 *            in degrees
	 * @param lat1
	 *            in degrees
	 * @param lon2
	 *            in degrees
	 * @param lat2
	 *            in degrees
	 * @return distance in km
	 */
	public static double countDistance(double lon1, double lat1, double lon2, double lat2) {

		double phi1 = (90 - lat1) * degreesToRadians;
		double phi2 = (90 - lat2) * degreesToRadians;

		double theta1 = lon1 * degreesToRadians;
		double theta2 = lon2 * degreesToRadians;

		double resultInRadians = Math.acos(Math.sin(phi1) * Math.sin(phi2) * Math.cos(theta1 - theta2) + 
				Math.cos(phi1) * Math.cos(phi2));

		return resultInRadians * radiansToKm;
	}
}
