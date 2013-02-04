/**
 * @author Camaria Bevavy
 */
package com.data.mining;

import java.util.*;

public class KMeans {
	private int K = 7;
	private ArrayList<Instance> centroid_list = new ArrayList<Instance>();
	private ArrayList<Instance> training_list = new ArrayList<Instance>();
	
	public KMeans(int k, ArrayList<Instance> t){
		K = k;		
		training_list = t;
	}
	/**
	 * 
	 * @param kcluster
	 */
	private boolean trainDataset(){
		double d;
		double temp_d = 0;
		boolean change = false;
		
		//calculate distance from each centroid to all instances	
		for(Instance  i : training_list){
			d = Double.MAX_VALUE;
			for(int x = 1; x < K + 1; x++){
				//calculate min distance from each c to i
				temp_d = calculateDistance(i, centroid_list.get(x - 1));
				if(temp_d < d){
					d = temp_d;
					if(x != i.getAssignedCategory()){
						change =  true;
						i.setAssignedCategory(x); //assign i to c's cluster
					}
					
				}//endif
				
				
			}//endfor
			
		}//endfor
		
		//assign cluster based on distance
		return change;
	}
	
	/**
	 * calculate all centroids
	 * create centroids from random id in the beginning
	 */
	private void getCentroids(){
		//first time choose randomly
		ArrayList<Instance> temp_list = new ArrayList<Instance>();
		ArrayList<Feature> c_feature_list = new ArrayList<Feature>();
		int  i = 0;
		int bogus = 0;
		int center_id = 0;
		//int curr_cluster = 0;
		int n = training_list.size();
		int c_size = centroid_list.size();
		Instance a_center = null;
		double[] mean = null;
		
		if(centroid_list.isEmpty()){			
			Random r = new Random(n);
			//System.out.println("random centroids");
			while(i < K){
				center_id = r.nextInt(n)+ 1;
				//System.out.println("centroid: " + center_id);
				//get instance from id
				a_center = getInstance(center_id);
				//System.out.println("instance " + a_center);
				centroid_list.add(a_center);
				i++;
			}
		}
		else{
			//System.out.println("not random centroids");
			
			centroid_list.clear(); //clear list first
			//compute from all the instances	
			for(int x = 0; x <c_size; x++){
				for(i = 0; i < n; i++){
					//System.out.println("compute centroid within each clusters");
					bogus = x + 1;
					//System.out.println(training_list.get(i).getAssignedCategory() + " == " + bogus);
					
					if(training_list.get(i).getAssignedCategory() == x + 1){
						//add to a temporary list to compute distance later
						temp_list.add(training_list.get(i));
												
					}
				}
				
				//calculate distance of instances inside temp_list
				//mean of each column
				
				System.out.println("Calculating mean");
				
				mean = computeCentroid(temp_list);
				for(double m : mean){
					//get each m as value of instance
					//this feature is float not integer
					System.out.print(m + ", ");
					//there is a constructor just for float value features
					c_feature_list.add(new Feature(m));				
				}
				//one of our precious centroid is also an instance				
				//so treating it like an instance
				Instance c_new = new Instance(x+1, new Category(x+1), c_feature_list);
				//centroid_list.add(new Instance(x, new Category(x), c_feature_list));
				System.out.println("new centroid " + c_new.getID());
				centroid_list.add(c_new);
			}
			
		}
		
		
	}
	
	/**
	 * 
	 * @param cluster contains a list of instances in the same cluster
	 * @return float array of mean of each column
	 * compute the centroid of instances inside the same cluster
	 */
	private double[] computeCentroid(ArrayList<Instance> cluster){
		int feature_size = cluster.get(0).getFeature().size();
		int num_instance = cluster.size();
		double[] mean = new double[feature_size];
		int x = 0;
		for(Instance i : cluster){
			//mean of each column
			for(x = 0; x < feature_size; x++){
				mean[x] += i.getFeature().get(x).getValue();
			}
		}
		
		//calculate mean
		for(x = 0; x < feature_size; x++ ){
			mean[x] = mean[x] / num_instance;
		}
		return mean;
	}
	
	/**
	 * 
	 * @param id instance id
	 * @return instance with id = id
	 * I need to search instance by id often
	 */
	private Instance getInstance(int id){
		Instance curr = null;
		for(Instance i : training_list){
			if(i.getID() == id){
				curr = i;
				break;
			}
		}
		return curr;
	}
	
	/**
	 * 
	 * @param i one instance
	 * @param y another instance
	 * @return float value of their distance
	 * Calculate distance between two instances
	 */
	private double calculateDistance(Instance i, Instance y){
		double d = 0;
		
		for(int x=0; x < i.getFeature().size(); x++){
			
			//distance = 0
			d += Math.pow(i.getFeature().get(x).getValue() - y.getFeature().get(x).getValue(),2);
				
		}
		d = Math.sqrt(d); 
		return d;
	}
	
	public void runKMeans(){
		//loop trainDataset until convergence
		boolean change = true;
		while(change){
			System.out.println(change);
			//choose centroids
			getCentroids();
			//System.out.println(centroid_list.size());
			
			/*for(Instance i : centroid_list){
				System.out.println(i.getID());
			}*/
			
			change = trainDataset();
			
			
		}
		System.out.println("finally ...");
		for(Instance i : centroid_list){
			for(int x = 0; x < 9; x++){
				System.out.print(i.getID());
			}
			System.out.println();
		}
		
		for(Instance i : training_list){
			System.out.println(i.getAssignedCategory() + " " + i.getCategory());
		}
	}
}
