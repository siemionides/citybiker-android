package pl.citybikerandroid.domain;

import java.util.Calendar;

public class Location {
	private Result result;
	
	public class Result {
		private Integer accuracy;
		private Float latitude;
		private Float longitude;
		private Calendar timestamp;
		private Float altitude;
		
		public Integer getAccuracy() {
			return accuracy;
		}
		public void setAccuracy(Integer accuracy) {
			this.accuracy = accuracy;
		}
		public Float getLatitude() {
			return latitude;
		}
		public void setLatitude(Float latitude) {
			this.latitude = latitude;
		}
		public Float getLongitude() {
			return longitude;
		}
		public void setLongitude(Float longitude) {
			this.longitude = longitude;
		}
		public Calendar getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(Calendar timestamp) {
			this.timestamp = timestamp;
		}
		public Float getAltitude() {
			return altitude;
		}
		public void setAltitude(Float altitude) {
			this.altitude = altitude;
		}
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
