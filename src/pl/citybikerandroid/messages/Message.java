package pl.citybikerandroid.messages;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public abstract class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String AUTHOR_ANONYMOUS = "anonymous";
	
	/** Has to be provide*/
	private String text;
	
	/** If not given - {@link Message#AUTHOR_ANONYMOUS}*/
	private String authorName;
	
	/**
	 * If not given - the current system date 
	 */
	/** mesage timestamp */
	private Date messageDate;
	
	
	/**
	 * Main contructor, others are calling it and stting necessary elements
	 * to appropirate tyeps (even if not given)
	 * @param text
	 * @param authorName
	 * @param date
	 */
	public Message(String text, String authorName, Date date){
		this.text = text;
		this.authorName = authorName;
		this.messageDate = date;
		
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
