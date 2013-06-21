package pl.citybikerandroid.domain;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCollection extends ArrayList<Message> {

	private static final long serialVersionUID = -7836686162767859163L;
	
}
