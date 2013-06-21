package pl.citybikerandroid.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StationCollection {

	 private List<Station> results;

	 public List<Station> getResults() {
	    return results;
	 }

	 public void setResults(List<Station> results) {
	    this.results = results;
	 }
	
}
