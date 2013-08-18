/**
 * 
 */
package com.data.mining;

/**
 * @author Camaria Bevavy
 *
 */

import java.util.*;

public class KDTree {

	private ArrayList<Instance> dataset = new ArrayList<Instance>(); //dataset
	int dimension = Integer.MAX_VALUE; //splitting dimension
	KDTreeElement root; //root of the tree
	KDTreeElement leaf; //leaf of the tree
	int feature_size; //number of features
	Queue<Integer> dim = new LinkedList<Integer>(); //queue storing unused dimensions
	
	//constructor
	public KDTree(){
		dimension = -1;
	}
	
	/**
	 * constructor
	 * @param data: the dataset
	 * @param f: number of features
	 */
	public KDTree(ArrayList<Instance> data, int f){
		dimension = -1;
		dataset = data;
		feature_size = f;
	}
	
	/**
	 * set the splitting dimension
	 * @param d: dimension
	 */
	public void setDimension(int d){
		dimension = d;
	}
	
	/**
	 * get the splitting dimension
	 * @return dimension
	 */
	public int getDimension(){
		return dimension;
	}
	
	/**
	 * get the root
	 * @return root
	 */
	public KDTreeElement getRoot(){
		return root;
	}
	
	/**
	 * insert new instance in the tree
	 * @param newinstance
	 */
	private void Insert(Instance newinstance){
		//check if tree is empty
		boolean isEmpty = isEmpty();
		
		if(isEmpty){
			//newinstance becomes root
			root = new KDTreeElement();
			root = root.createNode(newinstance, dimension);
			
		}
		else{
			KDTreeElement current = root;
			current.Insert(newinstance, current, dimension);
		}	
	}
	
	/**
	 * check if tree is empty or not
	 * @return true or false
	 */
	private boolean isEmpty(){
		
		 boolean isEmpty;

         if (root == null)
         {
             isEmpty = true;
         }
         else
         {
             isEmpty = false;
         }

         return isEmpty;
	}
	
	/**
	 * calculate mean of each column
	 * @param dataset
	 * @return mean array 
	 */
	private double[] calculateMean(ArrayList<Instance> dataset){
		
		int x = 0;
		int y = 0;
		int data_size = dataset.size();
		
		double[] mean = new double[feature_size];
		
		//get mean array
		
		for(y = 0; y < data_size; y++){
			for(x = 0; x < feature_size; x++){
				mean[x] += dataset.get(y).getFeature().get(x).getValue();
			}		
		}
		
		for(x = 0; x < feature_size; x++){
			mean[x] = mean[x] / data_size;
			
		}
		
		return mean;
	}

	/**
	 * calculate the variance to get the splitting dimension
	 * @param dataset
	 */
	private void getSplit(ArrayList<Instance> dataset){
		double[] mean = calculateMean(dataset);
		
		int d = 0;
		double highestVar = 0;
		int x = 0;
		
		chooseDimension();
		
		while(!dim.isEmpty()){
			x = dim.poll();
			double var = 0;
			for(Instance i : dataset){
				var += Math.pow(i.getFeature().get(x).getValue() - mean[x], 2);
			}

			if(highestVar < var){
				highestVar = var;
				d = x;
			}
		}
		
		setDimension(d);
		
	}
	
	//choose which dimension to split
	private void chooseDimension(){
		
		int d = getDimension();		
		int x = 0;
		
		while(x < feature_size){
			if(x != d){
				dim.add(x);
			}
			x++;
		}
	}
	
	/**
	 * calculate the median of the splitting column
	 * @param dataset
	 * @return instance median
	 */
	private Instance getMedian(ArrayList<Instance> dataset){
		Instance median = null;
		
		ArrayList<Instance> sorted = bubbleSortInstances(dataset);
		
		//find median
		int size = sorted.size();
		int medianPos = (int) Math.ceil(size / 2);
		
		median = dataset.get(medianPos);
		
		return median;
	}
	
	/**
	 * sort column to find the median using bubble sort
	 * @param dataset
	 * @return sorted column
	 */
	private ArrayList<Instance> bubbleSortInstances(ArrayList<Instance> dataset){
		int x = 0;
		int y = 0;
		int size = dataset.size();
		Instance temp = null;
		
		for(x = 0; x < size; x++){
			for(y = 0; y < size - 1; y++){
				if(dataset.get(y).getFeature().get(dimension).getValue() > dataset.get(y + 1).getFeature().get(dimension).getValue()){
					//swap
					temp = dataset.get(y);
					dataset.set(y, dataset.get(y + 1));
					dataset.set(y + 1, temp);
					
				}
			}
		}
	
		return dataset;
		
	}

	/**
	 * perform the data split by creating two sublists at the left and right of the median
	 * @param dataset
	 */
	public void splitDataset(ArrayList<Instance> dataset){
		int x = 0;
		int y = 0;
		int size = dataset.size();
		
		if(size > 1){
			getSplit(dataset);
		}
		
		if(size == 1){
			Insert(dataset.get(0));
		}
		else if(size > 1){
			ArrayList<Instance> left = new ArrayList<Instance>();
			ArrayList<Instance> right = new ArrayList<Instance>();
			
			//loop while left or right has more than 1 element
			Instance median = getMedian(dataset);
			
			//System.out.println("median " + median.getID());
			for(x = 0; x < size; x++){
				if(median.getID() != dataset.get(x).getID()){
					if(dataset.get(x).getFeature().get(dimension).getValue() < median.getFeature().get(dimension).getValue()){
						//go left
						//System.out.println("left " + dataset.get(x).getID());
						left.add(dataset.get(x));
					}
					else{
						//System.out.println("right " + dataset.get(x).getID());
						right.add(dataset.get(x));
					}
				}
			}
			
			Insert(median);			
			splitDataset(left);			
			splitDataset(right);
			
		}
		
		
	}
	
	/**
	 * Used to navigate the tree from a node given an instance to get to the leaf
	 * @param node
	 * @param i: instance
	 * @return leaf
	 */
	public KDTreeElement traverseTree(KDTreeElement node, Instance i){
		
		KDTreeElement current = node;
		
		int comparison = node.compare(node, i);
		
		if(current.Left() == null && current.Right() == null){
			leaf = current;
			
		}		
		else{
			if(comparison < 0){
				//go down left
					if(current.Left() != null){
					current = current.Left();
					traverseTree(current, i);
					}
					
				}
				else if(comparison > 0){
					//go down right
					if(current.Right() != null){
						current = current.Right();
						traverseTree(current, i);
					}
					
			}
			
		}
		
		return leaf;
	}
	
	//recursively print the tree
	public void Print()
     {
         //start from the root
		 KDTreeElement current = root;
		// System.out.println("root " + root.getSelf().getID());
         if (current.getParent() != null)
         {
             //print node and its parent
             System.out.println("Self " + current.getSelf().getID()+ "\t\t Parent " + current.getParent().getSelf().getID());
             //System.out.println("Self " + current.getDimension()+ "\t\t Parent " + current.getParent().getDimension());
         }
         else
         {
             //print only node if it has no parent
        	 System.out.println("Self " + current.getSelf().getID());
         }
         if (current.Left() != null && current.Right() != null)
         {
             //go to left child
             root = current.Left();
             Print();
            
             //go to right child
             root = current.Right();
             Print();
             
         }
         else if (current.Left() != null && current.Right() == null)
         {
             //go to left child 
             root = current.Left();
             Print();
             //no right child
         }
         else if (current.Left() == null && current.Right() != null)
         {
             //no left child
             //go to right child
             root = current.Right();
             Print();
         }
        
         
     }

	/**
	 * search for the leaf
	 * @param i: instance
	 * @return leaf node
	 */
	public KDTreeElement searchLeaf(Instance i){
		KDTreeElement leaf = null;
		KDTreeElement node = getRoot();
		
		
		while(leaf == null){
			
			leaf = traverseTree(node, i);
		}
		
		return leaf;
	}

	
	
	

}
