package pl.citybikerandroid.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BikeCollection {

	 private List<Bike> results;

	 public List<Bike> getResults() {
	    return results;
	 }

	 public void setResults(List<Bike> results) {
	    this.results = results;
	 }
	
}
