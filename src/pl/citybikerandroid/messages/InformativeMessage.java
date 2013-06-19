package pl.citybikerandroid.messages;

import java.util.Date;

public class InformativeMessage extends Message {

	public InformativeMessage(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public InformativeMessage(String text, String authorName) {
		super(text, authorName);
	}
	
	public InformativeMessage(String text, Date messageDate) {
		super(text, messageDate);
	}

	
	
	

}
