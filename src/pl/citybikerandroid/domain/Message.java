package pl.citybikerandroid.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class Message implements Serializable {

	public enum Types {
		logistic, info, service
	}
	
	private String id;
	private String type;
	private String subtype;
	private String text;
	private String author;
	private String arrival;
	private Integer between;
	private String bikeId;
	private String stationId;
	private byte[] photo;
	private String photo_thumbnail_url;
	private String photo_large_url;
	private String createdAt;
	private String updatedAt;
	
	@JsonIgnore
	private static final long serialVersionUID = -3187493645327129805L;
	@JsonIgnore
	public static final String SERIALIZABLE_NAME = "Message";
	@JsonIgnore
	public static final String AUTHOR_ANONYMOUS = "anonymous";
	
	public Message() {
	}
	public Message(String text, String authorName, Date date){
		this.text = text;
		this.author = authorName;
		this.createdAt = date.toString();
	}
	public Message(String text, Date date){
		this(text, Message.AUTHOR_ANONYMOUS, date);
	}
	public Message(String text) {
		this(text, Message.AUTHOR_ANONYMOUS, new Date());
	}
	public Message(String text, String authorName) {
		this(text, authorName, new Date());
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAuthor() {
		return this.author;
	}
	public String setAuthor(String author) {
		return this.author = author;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
		
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	/** Provides data in DESCENDING ORDER */
	@JsonIgnore
	public static Comparator<Message> MessageDateComparator = new Comparator<Message>() {

		public int compare(Message msg1, Message msg2) {
		
		String date1 = msg1.getCreatedAt();
		String date2 = msg2.getCreatedAt();
		
		//descending order
		return date2.compareTo(date1);
		
		//descending order
		//return fruitName2.compareTo(fruitName1);
		}
	};
	
}
