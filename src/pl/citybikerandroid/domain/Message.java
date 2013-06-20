package pl.citybikerandroid.domain;

public class Message {
	
	private String type;
	private String subtype;
	private String text;
	private String arrival;
	private Integer between;
	private String bikeId;
	private String stationId;
	private String photo_thumbnail_url;
	private String photo_large_url;
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getBetween() {
		return between;
	}
	public void setBetween(int between) {
		this.between = between;
	}
	public String getBikeId() {
		return bikeId;
	}
	public void setBikeId(String bikeId) {
		this.bikeId = bikeId;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getPhoto_thumbnail_url() {
		return photo_thumbnail_url;
	}
	public void setPhoto_thumbnail_url(String photo_thumbnail_url) {
		this.photo_thumbnail_url = photo_thumbnail_url;
	}
	public String getPhoto_large_url() {
		return photo_large_url;
	}
	public void setPhoto_large_url(String photo_large_url) {
		this.photo_large_url = photo_large_url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
		

	
}
