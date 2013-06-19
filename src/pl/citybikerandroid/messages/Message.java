package pl.citybikerandroid.messages;

public abstract class Message {
	
	public static final String AUTHOR_ANONYMOUS = "anonymous";
	
	private String text;
	
	private String authorName;

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


}
