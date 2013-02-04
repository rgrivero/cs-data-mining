/**
 * @author: Camaria Bevavy
 * Date: 16/01/2013
*/
package com.data.mining;

public class Feature {
	
	private int id;   // to identify the feature 
	
	private int f_value;
	
	private double c_value;
	
	public Feature(int v, int i){
		f_value = v;
		id = i;
	}
	
	public Feature(double value){
		c_value = value;
	}
	
	public int getValue(){
		return f_value;
	}
	
	public void setValue(int v){
		f_value = v;
	}
	
	public int getFeatureId(){
		return id;
	}
	
	public void setFeatureId(int i){
		id = i;
	}
	
	public double getCentroidValue(){
		return c_value;
	}
}

