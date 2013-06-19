package pl.citybikerandroid.messages;

import java.util.Comparator;
import java.util.Date;

public abstract class Message {
	
	public static final String AUTHOR_ANONYMOUS = "anonymous";
	
	private String text;
	
	private String authorName;
	
	/** mesage timestamp */
	private Date messageDate;
	

	public Message(String text, Date date){
		this.messageDate = date;
	}
	
	public Message(String text) {
		this.text = text;
		this.authorName = Message.AUTHOR_ANONYMOUS;
	}

	public Message(String text, String authorName) {
		this.text = text;
		this.authorName = authorName;
	}
	
	public boolean hasPhoto(){
		return false;
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getAuthorName() {
		return this.authorName;
	}

	public Date getDate() {
		return messageDate;
	}

	public void setDate(Date messageDate) {
		this.messageDate = messageDate;
	}
	
	/** Provides data in DESCENDING ORDER */
	public static Comparator<Message> MessageDateComparator = new Comparator<Message>() {

		public int compare(Message msg1, Message msg2) {
		
		Date date1 = msg1.getDate();
		Date date2 = msg2.getDate();
		
		//descending order
		return date2.compareTo(date1);
		
		//descending order
		//return fruitName2.compareTo(fruitName1);
		}
	};


}
