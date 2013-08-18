
package com.data.mining;


/**
 * @author Camaria Bevavy
 *
 */
import java.util.*;
import java.io.*;

public class Filehandler {
	private String filepath; //filename
	Set<Integer> cluster_set = new HashSet<Integer>(); //set of class labels
	Map<Integer, Integer> cluster_map = new TreeMap<Integer, Integer>(); //map of class labels and cluster number
	
	/**
	 * 
	 * @param filename
	 */
	public Filehandler(String filename){
		filepath = filename;
	}
	
	/**
	 * 
	 * @return filename
	 */
	public String getFilename(){
		return filepath;
	}
	
	/**
	 * 
	 * @param file
	 */
	public void setFilename(String file){
		filepath = file;
	}
	
	/** @return list of instances*/
	public ArrayList<Instance> readDataFile(){
		String[] data_line;
		int id = 1;
		Category c;
		boolean text = false;
		//Feature feature;
		
		ArrayList<Instance> instance_list = new ArrayList<Instance>();
		
		int i = 0;
		try{
			FileReader reader = new FileReader(filepath);
			BufferedReader br = new BufferedReader (reader);
			
			String line = null;
			while((line = br.readLine()) != null){
				text = false;
				ArrayList<Feature> feature_list = new ArrayList<Feature>();
				//process line
				data_line = line.split("[;,]");	
				
				//length of line
				int size = data_line.length;
				
				//extract features
				for(i=0; i < size - 1; i++){
					
					//System.out.println(data_line[i]);
					try{
						feature_list.add(new Feature(Double.parseDouble(data_line[i].trim()), i));
					}
					catch(Exception e){
						text = true;
						break;
					}
				}								
					if(!text){
						//instance class
						int cluster = Integer.parseInt(data_line[size - 1].trim());
						c = new Category(cluster);
						
						//add instance to list of instance
						instance_list.add(new Instance(id, c, feature_list));
						
						//add to set of clusters
						if(!cluster_set.add(cluster)){
							cluster_set.add(cluster);
						}
						//increment instance id
						id++;
					}
			}
		
			br.close();
			reader.close();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		
		return instance_list;
	}

	/**
	 * get the actual class of instances
	 * @return map of class and cluster number
	 */
	public Map<Integer, Integer> countClasses(){
		Object[] clusters = cluster_set.toArray();
		Arrays.sort(clusters);
		
		for(int x = 0; x < clusters.length; x++){
			int a = (Integer) clusters[x];
			cluster_map.put(a, x);
		}
		return cluster_map;
	}
}
