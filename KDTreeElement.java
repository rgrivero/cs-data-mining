
package com.data.mining;

/**
 * @author Camaria Bevavy
 *
 */
public class KDTreeElement {

	private KDTreeElement parent; //an internal node
	private Instance self; //an instance
	private int dimension; //its level or splitting dimension
	private KDTreeElement leftchild; //its left child
	private KDTreeElement rightchild; //its right child
	
	//constructor
	public KDTreeElement(){
		
	}
	
	/**
	 * 
	 * @return an instance
	 */
	public Instance getSelf(){
		return self;
	}
	
	/**
	 * 
	 * @return an internal node
	 */
	public KDTreeElement getParent(){
		return parent;
	}
	
	/**
	 * 
	 * @return a left child
	 */
	public KDTreeElement Left(){
		return leftchild;
	}
	
	/**
	 * 
	 * @return a right child
	 */
	public KDTreeElement Right(){
		return rightchild;
	}
	
	/**
	 * 
	 * @return return dimension
	 */
	public int getDimension(){
		return dimension;
	}
	
	/**
	 * used to insert a new instance in a tree
	 * @param i: an instance to be inserted
	 * @param node: an internal node reference
	 * @param d: the current splitting dimension
	 */
    public void Insert(Instance i, KDTreeElement node, int d){
		int comparison = compare(node, i);
		
		if(comparison < 0 && node.leftchild == null){
			//go left
			KDTreeElement child = new KDTreeElement();
			child.parent = node;
			child.self = i;
			child.dimension = d;
			child.leftchild = null;
			child.rightchild = null;
			node.leftchild = child;
			
		}
		else if(comparison > 0 && node.rightchild == null){
			//go right
			KDTreeElement child = new KDTreeElement();
			child.parent = node;
			child.self = i;
			child.dimension = d;
			child.leftchild = null;
			child.rightchild = null;
			node.rightchild = child;
		}
		else if(comparison < 0 && node.leftchild != null){
			//node = node.leftchild;
			Insert(i, node.leftchild, d);
		}
		else if(comparison > 0 && node.rightchild != null){
			//node = node.rightchild;
			Insert(i, node.rightchild, d);
		}
		
	}
	
    /**
     * used to create a root node in the tree
     * @param i:  a new instance
     * @param dimension: its dimension
     * @return: a root for the tree
     */
	public KDTreeElement createNode(Instance i, int dimension){
		KDTreeElement root = new KDTreeElement();
		root.parent = null;
		root.leftchild = null;
		root.rightchild = null;
		root.self = i;
		root.dimension = dimension;
		
		return root;
		
	}
	
	/**
	 * compare a node and an instance based on the node's dimension
	 * @param node: an internal node
	 * @param i: a new instance
	 * @return: -1 if new instance is less or 1 if larger
	 */
	public int compare(KDTreeElement node, Instance i){
		int dimension = node.dimension;
		int largest = 0;
		Instance current = node.self;
		
		if(current.getFeature().get(dimension).getValue() > i.getFeature().get(dimension).getValue()){
			largest = -1;
		}
		else{
			largest = 1;
		}
		return largest;
	}

	/**
	 * calcualte the squared distance between a node and a new instance
	 * @param node: internal node
	 * @param i: new instance
	 * @return: their distance
	 */
	public double spaceDistance(KDTreeElement node, Instance i){
		double d = 0;
		
		int dim = node.getDimension();
		
		d = Math.pow(node.getSelf().getFeature().get(dim).getValue() - i.getFeature().get(dim).getValue(), 2);
		
		return d;
	}

	/**
	 * Calculate the euclidean distance of a node and an instance
	 * @param node: internal node
	 * @param i: new instance
	 * @return: their distance
	 */
	public double euclideanDistance(KDTreeElement node, Instance i){
		double d = 0;
		for(int x=0; x < i.getFeature().size(); x++){
			
			//distance = 0
			d += Math.pow(node.getSelf().getFeature().get(x).getValue() - i.getFeature().get(x).getValue(),2);
				
		}
		
		d = Math.sqrt(d); //for 1 instance
		return d;
	}

	/**
	 * Find the next path from an internal to its children
	 * @param node: internal node
	 * @param i: new instance
	 * @return next node to visit
	 */
	public KDTreeElement NextPath(KDTreeElement node, Instance i){
		int d = compare(node, i);
			
		if(d < 0){
			node = node.Left();
		}
		else{
			node = node.Right();
		}
		
		return node;
	}


}
