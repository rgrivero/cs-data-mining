/**
 * 
 */
package com.data.mining;

/**
 * @author Camaria
 *
 */
import java.util.*;

public class DataMiningMain {

	/**
	 * @param args
	 */
	private static ArrayList<Instance> training_list = new ArrayList<Instance>();
	private static ArrayList<Instance> test_list = new ArrayList<Instance>();
	
	//get data
	//call feature selection (only for second part of program)
	//divide training and test
	 private static void divideDataset(ArrayList<Instance> dataset){
		//	get and store training instances (about 100)in a list
		//	get test instance in a list
		 int i = 0;
		 
		 int training_size = (int)(dataset.size() * 3)/ 4;
		 
		 for(i=0; i<training_size; i++){
			 training_list.add(dataset.get(i));
		 }
		 
		 for(i=i;  i < dataset.size(); i++){
			 test_list.add(dataset.get(i));
		 }
		 
	 }
	/*
	•	call for k-nearest with different k
	•	call for k-means
	•	call feature selection
	•	call for k-nearest with different k
	•	call for k-means*/

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = "resources/test.txt";
		Filehandler f = new Filehandler(filename);
		
		ArrayList<Instance> instance_list = f.readDataFile();
		
		/*
		 for(int i=0; i < my_instance.size(); i++){
			System.out.println("id: " + my_instance.get(i).getID());
			System.out.println("class: " + my_instance.get(i).getCategory());
			//System.out.println(my_instance.get(i).feature_list.);
			ArrayList<Feature> list = my_instance.get(i).getFeature();
			//System.out.println(list.size());
			
			for(int y=0; y < list.size(); y++){
				System.out.println("feature " + y +": " + list.get(y).getValue());
			
			}
		}
		*/
			
		divideDataset(instance_list);
		//System.out.println(training_list.size());
		//System.out.println(test_list.size());
		
		/*
		 * Run KNN with K=2 and training list
		 */
		//KNearestNeighbor classifier = new KNearestNeighbor(2, training_list);
		/*
		 * Send new instance to KNN 
		 */
		/*for (Instance i : test_list){
			
			//use KNN to classify i
			classifier.setNewInstance(i);
			classifier.runKNN();
			System.out.println(i.getAssignedCategory());
		}*/
		
		
		/*for(Instance i : classifier.getTraining()){
			for(int x=0;x<i.getFeature().size(); x++){
				//if(classifier.getnew().getFeature().get(x).getValue().equals(i.getFeature().get(x).getValue())){
					System.out.println(i.getFeature().get(x).getValue());
				//}
				//else{
					System.out.println(classifier.getnew().getFeature().get(x).getValue());
					//System.out.println(classifier.getnew());
				//}
			}
		}
		*/
		
		KMeans classifier = new KMeans(2, training_list);
		
		classifier.runKMeans();
		
		
		/*
		 * Evaluating classifier
		 */
		//evaluateClassifier("KNN");
	
	}

	private static void evaluateClassifier(String method){
		float success_percentage; //percentage of correct classification
		float fail_percentage; //percentage of wrong classification
		int success = 0; //number of correct classification
		int fail = 0; //number of wrong classification
		int test_size = test_list.size(); //size of test list
		
		//compare assigned class to real class
		for (Instance i : test_list){
			if(i.getAssignedCategory() == i.getCategory()){
				success += 1;
			}
			else{
				fail += 1;
			}
		}
		
		//calculate percentage
		try{
			success_percentage = success / test_size;
		}
		catch(Exception e){
			success_percentage = 0;
			System.err.println(e);
		}
		
		try{
			fail_percentage = fail / test_size;
		}
		catch (Exception e){
			fail_percentage = 0;
			System.err.println(e);
		}
		
		System.out.println("Method" + method);
		System.out.println("Correct: " + success_percentage);
		System.out.println("Incorrect: " + fail_percentage);
	}
}
