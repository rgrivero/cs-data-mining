/**
 * @author: Camaria Bevavy
 * Date: 16/01/2013
*/
package com.data.mining;

public class Feature {
	
	private int id;   // to identify the feature 
	
	private double f_value;
	
	
	public Feature(double v, int i){
		f_value = v;
		id = i;
	}
	
	public Double getValue(){
		return f_value;
	}
	
	public void setValue(double v){
		f_value = v;
	}
	
	public int getFeatureId(){
		return id;
	}
	
	public void setFeatureId(int i){
		id = i;
	}
	

}

