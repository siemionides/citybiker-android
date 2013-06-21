package pl.citybikerandroid.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCollection {

	 private List<Message> results;

	 public List<Message> getResults() {
	    return results;
	 }

	 public void setResults(List<Message> results) {
	    this.results = results;
	 }
	
}
