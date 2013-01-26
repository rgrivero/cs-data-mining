/**
 * @author: Camaria Bevavy
 * Date: 16/01/2013
*/
package com.data.mining;

public class Feature {
	
	private int id;   // to identify the feature 
	
	private String f_value;
	
	public Feature(String v, int i){
		f_value = v;
		id = i;
	}
	
	public String getValue(){
		return f_value;
	}
	
	public void setValue(String v){
		f_value = v;
	}
	
	public int getFeatureId(){
		return id;
	}
	
	public void setFeatureId(int i){
		id = i;
	}
}

