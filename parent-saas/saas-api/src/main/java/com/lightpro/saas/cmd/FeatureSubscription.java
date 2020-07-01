package com.lightpro.saas.cmd;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FeatureSubscription {
	
	private final List<String> featuresToAdd;
	private final List<String> featuresToDelete;
	
	public FeatureSubscription(){
		throw new UnsupportedOperationException("#FeatureSubscription()");
	}
	
	@JsonCreator
	public FeatureSubscription( @JsonProperty("featuresToAdd") final List<String> featuresToAdd,
						  @JsonProperty("featuresToDelete") final List<String> featuresToDelete){
		
		this.featuresToAdd = featuresToAdd;
		this.featuresToDelete = featuresToDelete;
	}
	
	public List<String> featuresToAdd(){
		return featuresToAdd;
	}
	
	public List<String> featuresToDelete(){
		return featuresToDelete;
	}
}
