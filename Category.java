/**
 * 
 */
package com.data.mining;

/**
 * @author Camaria Bevavy
 * date 16/01/2013
 * Category is class of an instance 
 * for the mushroom example, category can be either e (edible) or p (poisonous)
 */

public class Category {
	private String cat_value;
	
	/**class of instance with value */
	public Category(String v){
		cat_value = v;
	}
	
	public String getValue(){
		return cat_value;
	}
	
	public void setValue(String v){
		cat_value = v;
		
	}
}
