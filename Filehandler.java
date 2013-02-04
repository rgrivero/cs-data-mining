/**
 * 
 */
package com.data.mining;


/**
 * @author Camaria Bevavy
 *
 */
import java.util.*;
import java.io.*;

public class Filehandler {
	private String filepath;
	
	/**
	 * 
	 * @param filename
	 */
	public Filehandler(String filename){
		filepath = filename;
	}
	
	public String getFilename(){
		return filepath;
	}
	
	public void setFilename(String file){
		filepath = file;
	}
	
	/** @return list of instances*/
	public ArrayList<Instance> readDataFile(){
		String[] data_line;
		int id = 1;
		Category c;
		//Feature feature;
		
		ArrayList<Instance> instance_list = new ArrayList<Instance>();
		
		int i = 0;
		try{
			FileReader reader = new FileReader(filepath);
			BufferedReader br = new BufferedReader (reader);
			
			String line = null;
			while((line = br.readLine()) != null){
				
				ArrayList<Feature> feature_list = new ArrayList<Feature>();
				//process line
				data_line = line.split(" ");	
				
				//length of line
				int size = data_line.length;
				
				//System.out.println(data_line.length);
				
				//extract features
				for(i=0; i < size - 1; i++){
					
					//System.out.println(data_line[i]);
					feature_list.add(new Feature(Integer.parseInt(data_line[i].trim()), i));
				}								
					
				//instance class
				c = new Category(Integer.parseInt(data_line[size - 1].trim()));
				
				//add instance to list of instance
				instance_list.add(new Instance(id, c, feature_list));
				
				//increment instance id
				id++;
				//System.out.println(feature_list.size());
				//System.out.println(instance_list.size());
				
			}
			//System.out.println(feature_list.size());
			//System.out.println(instance_list.size());
			
			br.close();
			reader.close();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		
		return instance_list;
	}
}
