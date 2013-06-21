package pl.citybikerandroid.domain;

import java.util.Date;

public class InformativeMessage extends Message {

	private static final long serialVersionUID = 7658522951030098355L;

	public InformativeMessage(String text, String authorName) {
		super(text, authorName);
	}
	
	public InformativeMessage(String text, Date messageDate) {
		super(text, messageDate);
	}

}
