package pl.citybikerandroid.domain;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BikeCollection extends ArrayList<Bike> {

	private static final long serialVersionUID = 5447921541750616729L;
	
}
