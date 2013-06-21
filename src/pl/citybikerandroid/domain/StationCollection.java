package pl.citybikerandroid.domain;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StationCollection extends ArrayList<Station> {
	private static final long serialVersionUID = 8873787258914798005L;
}
