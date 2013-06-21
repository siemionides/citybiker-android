package pl.citybikerandroid.messages;

import java.util.Date;

public class ServiceMessage extends Message {

	private static final long serialVersionUID = 4362593283393297268L;

	public ServiceMessage(String text) {
		super(text);
	}

	public ServiceMessage(String text, String authorName) {
		super(text, authorName);
	}

	public ServiceMessage(String text, Date messageDate) {
		super(text, messageDate);
	}


}
