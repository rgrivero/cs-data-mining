
package com.data.mining;

/**
 * @author Camaria Bevavy
 * 
 * Category is the class of an instance 
 * 
 */

public class Category {
	//default value of a class
	private int cat_value = 0;
	
	/**
	 * 
	 * @param v: class of an instance
	 */
	public Category(int v){
		cat_value = v;
	}
	
	/**
	 * 
	 * @return class of an instance
	 */
	public int getValue(){
		return cat_value;
	}
	/**
	 * 
	 * @param v class of an instance
	 */
	public void setValue(int v){
		cat_value = v;
		
	}
}
