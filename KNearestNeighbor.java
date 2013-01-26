/**
 * 
 */
package com.data.mining;
import java.util.*;

/**
 * @author Camaria
 *
 */
public class KNearestNeighbor {
	private int K;
	private ArrayList<Map.Entry<Integer, Double>> distance_list;
	private Queue<String> neighbor_list;
	private Instance new_instance;
	private ArrayList<Instance> training_list;
	
	public KNearestNeighbor(int k, ArrayList<Instance> t){
		K = k;
		training_list = t;
	}
	
	public int getK(){
		return K;
	}
	
	public void setK(int k){
		K = k;
	}
	/**
	 * calculate Minkowski distance norm=1 of a new instance to training instance 
	 */
	public ArrayList<Instance> getTraining(){
		return training_list;
	}
	
	public Instance getnew(){
		return new_instance;
	}
	
	public void setNewInstance(Instance n){
		new_instance = n;
	}
	public void calculateDistance(){
		
		Hashtable<Integer,Double> distance = new Hashtable<Integer,Double>();
		double temp_d = 0.0;
		
		for (Instance i : training_list){
			//compare individual feature
			temp_d = 0.0;
			for(int x=0; x < i.getFeature().size(); x++){
				
				//distance = 0
				if(new_instance.getFeature().get(x).getValue().equals(i.getFeature().get(x).getValue())){
					//distance = 0					
						//distance = 0
					temp_d += 0;
				}
				else{
					//distance = 1
					temp_d += 1;
				}
			}
			
			//temp_d = Math.sqrt(temp_d); //for 1 instance
			distance.put(i.getID(),temp_d);
		}
		sortDistance(distance);
				
	}
	
	/**
	 * sort distance between new instance and training set
	 * @param distance
	 */
	private void sortDistance(Hashtable<Integer,Double> distance){
		ArrayList<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer,Double>>(distance.entrySet());
		
		System.out.println(list);
		Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>(){
			public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2){
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		
		distance_list = list;
	}
	/**
	 * Find neighbor of new instance
	 */
	public void getKNeighbors(){
		int id = 0;
		String c = " ";
		for (int i=0; i < K; i++){
			//System.out.println(distance_list.get(i));
			id = distance_list.get(i).getKey();
			System.out.println(id);
				
			for (Instance is : training_list){
				if(is.getID() == id){
					//c = is.getCategory();
					//neighbor_list.offer(c);
					System.out.println(is.getCategory());
					break;
				}
			}
			
		}
		
	}
	
	/**
	 * Assign class to the new instance
	 */
	public void assignCategory(){
		int majority = 0;
		int temp_majority = 0;
		String cat_majority = ""; //final class
		String cat_temp; //temporary class
		int i = neighbor_list.size();
		int y = 0;		
		while(!neighbor_list.isEmpty()){
			cat_temp = neighbor_list.poll();
			temp_majority = 1;
			//start round
			i = neighbor_list.size();
			while(y < i){
				String curr_cat = neighbor_list.poll();
				
				if(cat_temp.equals(curr_cat)){
					temp_majority += 1;
				}
				else{
					neighbor_list.offer(curr_cat);
				}
			}
			if(majority < temp_majority){
				cat_majority = cat_temp;
			}
			
		}
		
		new_instance.setAssignedCategory(cat_majority);
		
	}
	
	/**
	 * Assign class to training data set
	 */
	public void trainDataset(){
		for(Instance i : training_list){
			i.setAssignedCategory(i.getCategory());
		}
	}
	
	public void runKNN(){
		trainDataset();
		calculateDistance();
		getKNeighbors();
		assignCategory();
	}

}
