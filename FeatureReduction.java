/**
 * 
 */
package com.data.mining;
import java.util.*;
import cern.colt.matrix.*;
import cern.colt.matrix.linalg.*;

/**
 * @author Camaria Bevavy
 *
 */
public class FeatureReduction {
	
	private ArrayList<Instance> dataset = new ArrayList<Instance>(); //dataset
	private ArrayList<Instance> adjusted_data = new ArrayList<Instance>(); //mean subtracted dataset
	private ArrayList<Instance> final_data = new ArrayList<Instance>(); //transformed dataset
	private DoubleMatrix2D covMatrix; //covariance matrix
	private DoubleMatrix2D eigenValues; //matrix for eigenvalues
	private DoubleMatrix2D eigenVectors; //matrix for eigenvectors
	int p = 0; //reduced dimension
	private int data_size; //size of dataset
	private int feature_size; //number of features
	
	/**
	 * constructor
	 * @param data: dataset list
	 * @param feat_size: number of features
	 * @param d_size: size of dataset
	 */
	public FeatureReduction(ArrayList<Instance> data, int feat_size, int d_size){
		dataset = data;
		feature_size = feat_size;
		data_size = d_size;
	}
	
	/**
	 * subtract mean from data
	 * @return list of adjusted data
	 */
	private ArrayList<Instance> centerData(){
		double[] mean = meanColumn();
		int x = 0;

		for(x = 0; x < data_size; x++){
			//add instance to adjusted data list
			adjusted_data.add(generateMeanInstance(mean, x));
		}
		
		return adjusted_data;
	}

	/**
	 * subtract mean from each feature value
	 * @param mean: column mean
	 * @param x: instance id
	 * @return: adjusted instance
	 */
	private Instance generateMeanInstance(double[] mean, int x){
		Instance adjusted;
		int y = 0;
		ArrayList<Feature> feature_list = new ArrayList<Feature>();
			
			for(y = 0; y < feature_size; y++){
		
				//get each m as value of instance					
				feature_list.add(new Feature(adjustInstance(dataset.get(x).getFeature().get(y).getValue(), mean[y]), y));				
			}
		
			adjusted = new Instance(dataset.get(x).getID(), new Category(dataset.get(x).getCategory()), feature_list);			
		
		return adjusted; //new instance
	}
	
	/**
	 * Generate new instance
	 * @param features: array of instance feature values
	 * @param x: instance id
	 * @return new instance
	 */
	private Instance generateInstance(double[] features, int x){
		Instance adjusted;
		int size = features.length;
		int y = 0;
		ArrayList<Feature> feature_list = new ArrayList<Feature>();
			
			for(y = 0; y < size; y++){
		
				//get each m as value of instance					
				feature_list.add(new Feature(features[y], y));				
			}
		
			adjusted = new Instance(dataset.get(x).getID(), new Category(dataset.get(x).getCategory()), feature_list);			
		
		return adjusted; //new instance
	}
	
	/**
	 * subtract mean from value 
	 * @param old_val: original data value
	 * @param mean: column mean
	 * @return difference when mean is subtracted
	 */
	private double adjustInstance(double old_val, double mean){
		double adjusted = old_val - mean;
				
		return adjusted;
	}
	
	
	/**
	 * calculate mean of each column
	 * @return array containing mean of each column
	 */
	private double[] meanColumn(){
		double[] mean = new double[data_size];
		int  x =0;
		for(Instance i : dataset){
			for(x = 0; x < feature_size; x++){
				mean[x] += i.getFeature().get(x).getValue();
			}
		}
		
		//divide by number of instances
		for(x = 0; x < feature_size; x++){
			mean[x] = mean[x] / data_size;
		}
		
		return mean;
	}
	
	/**
	 * calculate covariance matrix of size data_size x data_size
	 */
	private void covarianceMatrix(){
		int x = 0;
		int y = 0;
		int z = 0;
		double cov = 0;
		DoubleFactory2D matrixConst = DoubleFactory2D.dense;
		covMatrix = matrixConst.make(feature_size, feature_size, 0);
				
		for(y = 0; y < feature_size; y++){
			for(z = 0; z < feature_size; z++){
				for(x = 0; x < data_size; x++){
					cov += adjusted_data.get(x).getFeature().get(y).getValue() * adjusted_data.get(x).getFeature().get(z).getValue();
				}
				cov = cov / (data_size - 1);
				covMatrix.set(y, z, cov);
				cov = 0;
			}
		}
	}
	
	/**
	 * calculate eigenvalues and eigenvectors
	 */
	private void eigenValues(){
		
		EigenvalueDecomposition eigenDecomp = new EigenvalueDecomposition(covMatrix);
		
		eigenValues = eigenDecomp.getD();
		eigenVectors = eigenDecomp.getV();
	}
	
	/**
	 * select largest eigenvalues
	 */
	private void chooseVectors(){
		int x = 0;
		int y = 0;
		
		for(x = feature_size - 1; x >= 0; x--){
			for(y = feature_size - 1; y >= 0; y--){
				if(x == y){
					if(eigenValues.get(x, y) > 1){
					
					System.out.println("Eigenvalues: " + eigenValues.get(x, y));
				
						p += 1;
					}
				}
			}
		}
		System.out.println("Dimension reduced to " + p);
	}
	
	/**
	 * Compute the final dataset from eigenvectors
	 */
	private void FinalizeDataSet(){
		int x = 0;
		int y = 0;
		int z = 0;
		int d = feature_size;
		
		//multiply adjusted data with the chosen eigenvectors
		for(x = 0; x < data_size; x++){	
			double[] features = new double[p]; 
			for(z = 0; z < p; z++){
				for(y = 0; y < feature_size; y++){
					features[p - z - 1] += adjusted_data.get(x).getFeature().get(y).getValue()* eigenVectors.get(y,d - z - 1);
				}
			}
			//new instance
			final_data.add(generateInstance(features,x));
		}
	}

	/**
	 * public method to perform feature reduction
	 * @return: transformed dataset
	 */
	public ArrayList<Instance> runFeatureReduction(){
		
		//subtract mean
		centerData();
		
		//calculate covariance
		covarianceMatrix();
		
		//get eigenvalues and eigenvectors
		eigenValues();
		
		//choose vectors
		chooseVectors();
		
		//get final data
		FinalizeDataSet();
		
		return final_data;
	}
}
