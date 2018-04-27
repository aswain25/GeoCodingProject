package com.example.admin.geocodingproject.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Location{

	@SerializedName("results")
	private List<ResultsItem> results;

	@SerializedName("status")
	private String status;

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Location{" + 
			"results = '" + results + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}