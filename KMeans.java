/**
 * @author Camaria Bevavy
 */
package com.data.mining;

import java.util.*;

public class KMeans {
	private int K;
	private KDTree tree;
	private KDTreeElement nearest;
	private double radius;
	private int numFeature ;
	private Instance new_instance;
	private ArrayList<Instance> centroid_list = new ArrayList<Instance>();
	private ArrayList<Instance> training_list = new ArrayList<Instance>();
	
	public KMeans(ArrayList<Instance> t, int k, int f){
		K = k;		
		training_list = t;
		numFeature = f;
	}
	
	public Instance getnew(){
		return new_instance;
	}

	public void setNewInstance(Instance n){
		new_instance = n;
	}
	
	/**
	 * 
	 * @param kcluster
	 */
	public boolean trainDataset(){
		double d;
		boolean change = false;
		double[] distance = new double[K];
		int temp_class = 0;
		
		//calculate distance from each centroid to all instances	
		for(int z = 0; z < training_list.size(); z++){
			d = Double.MAX_VALUE;
			for(int x = 1; x < K + 1; x++){
				distance[x-1] = calculateDistance(training_list.get(z), centroid_list.get(x - 1));				
								
			}//endfor			
			
			//System.out.println("initial Category() " + i.getAssignedCategory());
			
			for(int y = 1; y < K + 1; y++){
				
				if(distance[y-1] < d) {
					d = distance[y-1];
					
					temp_class = y;
				}
			}
			if(temp_class != training_list.get(z).getAssignedCategory()){
				change =  true;
				training_list.get(z).setAssignedCategory(temp_class); //assign i to c's cluster
				
			}
			//System.out.println("finally i.getAssignedCategory() " + i.getAssignedCategory());
		}//endfor
		
		//assign cluster based on distance
		return change;
	}
	
	/**
	 * calculate all centroids
	 * create centroids from random id in the beginning
	 */
	public void getCentroids(){
		//first time choose randomly
		ArrayList<Instance> temp_list = new ArrayList<Instance>();
		//ArrayList<Feature> c_feature_list = new ArrayList<Feature>();
		int  i = 0;
		int center_id = 0;
		int n = training_list.size();
		int c_size = centroid_list.size();
		Instance a_center = null;
		double[] mean = null;
		
		if(centroid_list.isEmpty()){	
			
			//Initial cluster centers are random
			
			Random r = new Random(n);
			Set<Integer> centroid_set = new HashSet<Integer>();
			//System.out.println("random centroids");
			while(i < K){
				center_id = r.nextInt(n);
				while(centroid_set.contains(center_id)){
					center_id = r.nextInt(n);
				}
				centroid_set.add(center_id);				
				
				a_center = getInstance(center_id);
				if(a_center != null){
					centroid_list.add(a_center);
					i++;
				}
				
			}
		}
		else{
			//not random centroids
			
			centroid_list.clear(); //clear list first
			//System.out.println(c_size);
			//compute from all the instances	
			for(int x = 1; x < c_size + 1; x++){
				
				temp_list.clear();//reset temp_list 
				mean = null;	
				//c_feature_list.clear();
				
				for(i = 0; i < n; i++){
					
					if(training_list.get(i).getAssignedCategory() == x){

						//add to a temporary list to compute distance later
						temp_list.add(training_list.get(i));
												
					}
				}
				
				//calculate distance of instances inside temp_list
				//mean of each column			
				mean = computeCentroid(temp_list);
				centroid_list.add(generateCentroid(mean, x)); //add new centroid to list		
			}			
		}		
	}

	public Instance generateCentroid(double[] mean, int x){
		Instance c;
		ArrayList<Feature> c_feature_list = new ArrayList<Feature>();
		for(int y = 0; y < mean.length; y++){
			
			//get each m as value of instance					
			c_feature_list.add(new Feature(mean[y], y));				
		}
		
		c = new Instance(x, new Category(x), c_feature_list);
		
		return c;
	}
	/**
	 * 
	 * @param cluster contains a list of instances in the same cluster
	 * @return float array of mean of each column
	 * compute the centroid of instances inside the same cluster
	 */
	public double[] computeCentroid(ArrayList<Instance> cluster){
		
		int num_instance = cluster.size();
		double[] mean = new double[numFeature];
		int x = 0;
		for(Instance i : cluster){
			//mean of each column
			for(x = 0; x < numFeature; x++){
				mean[x] += i.getFeature().get(x).getValue();
			}
		}
		
		//calculate mean
		for(x = 0; x < numFeature; x++ ){
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
	public Instance getInstance(int id){
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
	public double calculateDistance(Instance i, Instance y){
		double d = 0;
		
		for(int x=0; x < numFeature; x++){
			
			//distance = 0
			d += Math.pow(i.getFeature().get(x).getValue() - y.getFeature().get(x).getValue(),2);
				
		}
		d = Math.sqrt(d);
		//System.out.println(d);
		return d;
	}
	
	public void runKMeans(){
		//loop trainDataset until convergence
		boolean change = true;
		while(change){
			getCentroids();
			
			change = trainDataset();
			
		}
		for(int x = 0; x < centroid_list.size(); x++){
			//System.out.println(centroid_list.get(x).getID());
		}
	}

	public void runKMeansTest(){
		
		double d = Double.MAX_VALUE;
		double temp_d;
		int cluster = 0;
		//compute distance to centroids
		for(int x = 0; x < centroid_list.size(); x++){
			temp_d = calculateDistance(centroid_list.get(x),new_instance);
			
			if(temp_d < d){
				d = temp_d;
				cluster = x;
			}			
		}
		new_instance.setAssignedCategory(cluster);
		
	}

	public void trainTreeKMeans(){
		tree = new KDTree(centroid_list, numFeature);
		
		tree.splitDataset(centroid_list);
		
		KDTreeElement root = tree.getRoot();
		System.out.println("centroid root " + root);
	}
	
	public void testTreeKMeans(){
		nearest = tree.searchLeaf(new_instance);
		
		System.out.println(nearest.getSelf().getID());
		
		searchNeighbor(nearest);
		
		treeAssignCategory();
	}

	public void treeAssignCategory(){
		Instance i = nearest.getSelf();
		int cat = i.getID() - 1;
		new_instance.setAssignedCategory(cat);
		
	}
	
	public void searchNeighbor(KDTreeElement next){
	  	
		radius = next.spaceDistance(next, new_instance);
		
		System.out.println("radius " + radius);
		System.out.println("next " + next.getSelf().getID());
		KDTreeElement prev = next;
		
		next = next.getParent();
		
		while(next != null){
			
			double r = next.spaceDistance(next, new_instance);
			
			System.out.println("r " + r);
			System.out.println("next " + next.getSelf().getID());
			
			if( r < radius){
				nearest = next;	
				radius = r;
				nearest = searchBest(next);
				prev = next;
				next = next.getParent();
			}
			else{
				prev = next;
				next = next.getParent();
			}
			
		}
		//encounter a root
		if(next == null){
			System.out.println("prev root " + prev.getSelf().getID());
			next = prev;
			double r = next.spaceDistance(next, new_instance);
			
			System.out.println("r " + r);
			System.out.println("next " + next.getSelf().getID());
			System.out.println("radius " + radius);			
			
			if( r < radius){
				nearest = next;	
				radius = r;
				nearest = searchBest(next);
				prev = next;
				next = next.getParent();
			}
		}
		
	}
	
public KDTreeElement searchBest(KDTreeElement best){
		
		double r = best.spaceDistance(best, new_instance);
		
		if(r < radius){
			radius = r;
			nearest = best;
		}
		
		if(best.Right() == null && best.Left() == null){
			//do nothing
		}
		else if(best.Left() != null && best.Right() == null){
			searchBest(best.Left());
		}
		else if(best.Left() == null && best.Right() != null){
			searchBest(best.Right());
		}
		else if(best.Left() != null && best.Right() != null){
			searchBest(best.Left());
			searchBest(best.Right());
		}
		
		return nearest;
	}

}

