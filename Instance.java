/**
 * @author Camaria Bevavy
 * An instance has a real class and an assigned class
 * Real class: from the dataset
 * Assigned class: a class we try attempt to assign to an instance which
 * may or may not be equal to the real class
 */
package com.data.mining;
import java.util.*;

public class Instance{
	
	private int id;
	private Category category; //real class of instance
	private Category assigned_category; //assigned class of instance
	private ArrayList<Feature> feature_list; //list containing values for each attribute of the data for this instance
	
	/**Create an "instance" object with id, its class, and features */
	 
	public Instance(int id, Category cat, ArrayList<Feature> f_list){
		this.id = id;
		category = cat;
		feature_list = f_list;
	}
	
	public int getID(){
		return this.id;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public String getCategory(){
		return this.category.getValue();
		
	}
	
	public void setCategory(String v){
		//this.category.setValue(v);
		category = new Category(v);
	}
	
	public String getAssignedCategory(){
		return this.assigned_category.getValue();
	}
	
	public void setAssignedCategory(String v){
		//this.assigned_category.setValue(v);
		assigned_category = new Category(v);
	}
	
	public ArrayList<Feature> getFeature(){
		if(feature_list.isEmpty()){
			System.out.println("Empty");
		}
		return feature_list;
	}
}
