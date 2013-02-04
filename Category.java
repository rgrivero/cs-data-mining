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
	private int cat_value = 0;
	
	/**class of instance with value */
	public Category(int v){
		cat_value = v;
	}
	
	public int getValue(){
		return cat_value;
	}
	
	public void setValue(int v){
		cat_value = v;
		
	}
}
