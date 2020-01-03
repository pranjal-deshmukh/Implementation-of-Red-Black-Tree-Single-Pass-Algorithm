
/** @author 
 *  Pranjal Deshmukh : psd180000
 *  Aniket Pathak : adp170003
 *  SP7 : Red Black Tree
 **/

package psd180000;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * This is RedBlackTree class:
 * Insertion is done by Single Pass 
 */
public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Entry<T> header;//Sentinel Node used such that header.right will be set as the root
    private Entry<T> nil = new Entry<T>(null, null, null);//Sentinel Nodes used for easy Remove() operation
    Entry<T> root = (Entry<T>) super.root;
    Entry<T> current; 
	Entry<T> parent;
	Entry<T> grand_parent;
	Entry<T> great_grand_parent;
   
	/**
     * This is static class Entry
     * @param color : Color of the node : either red or black
     * Inherits other properties from BinarySearchTree.Entry<T>
     **/
    static class Entry<T> extends BinarySearchTree.Entry<T> {
        boolean color;
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            color = BLACK;
        }

        boolean isRed() {
	    return color == RED;
        }

        boolean isBlack() {
	    return color == BLACK;
        }
    }
    
    /**
     * Constructor for RedBlackTree
     */
    RedBlackTree() {
    	super();
    	nil.color = BLACK;
    	header = new Entry(Integer.MIN_VALUE, null, null);//To represent negative infinity, we have used Integer.MIN_VALUE
   	 	header.left = nil;
   	 	header.right = nil;
    }
    
    /**
     * rotateLeft() function takes
     * @param x : the node on which we wish to perform the left rotation
     */
    public void rotateLeft(Entry<T> x) {
    	Entry<T> y = (Entry<T>) x.right;
    	x.right = y.left;
    	if(y.left != nil && y.left != null) {
    		y.left.parent = x;
    	}
    	y.parent = x.parent;
    	if(x.parent==null || x.parent == header)
    	{
    		this.root = y;
    	}
    	else
    	{
    		if(x == x.parent.left)
    		{
    			x.parent.left = y;
    		}
    		else
    		{
    			x.parent.right = y;
    		}
    	}
    	y.left = x;
		x.parent = y;
    }
    
    /**
     * rotateRight() function takes
     * @param x : the node on which we wish to perform the right rotation
     */
    
    public void rotateRight(Entry<T> x) {

    	Entry<T> y = (Entry<T>) x.left;
    	x.left = y.right;
    	if(y.right != nil && y.right != null) {
    		y.right.parent = x;
    	}
    	y.parent = x.parent;
    	if(x.parent==null || x.parent == header)
    	{
    		this.root = y;
    	}
    	else
    	{
    		if(x == x.parent.right)
    		{
    			x.parent.right = y;
    		}
    		else
    		{
    			x.parent.left = y;
    		}
    		
    	}
    	y.right = x;
		x.parent = y;
    }
    
    /**
     * This method is used to Add x to tree using Single Pass
     * @param x1 : Input element to be added to the tree 
     * @return true : if x is a new element added to tree, and false otherwise.
     */
    public boolean add(T x1) {
    	 current = (Entry<T>) header;
    	 parent = (Entry<T>) header;
    	 grand_parent = (Entry<T>) header;
    	
    	//Handle the case when size is equal to 0
    	if(size==0)
    	{
    		root = new Entry(x1,null,null);
    		size = 1;
    		root.left = nil;
    		root.right = nil;
    		root.color = BLACK;
    		current = root;
    		header.right = root;
    		root.parent = header;
    		super.root = root;
    		return true;
    	}
    	
    	Entry<T> tempNode = new Entry(x1,null,null);
    	while(current.element.compareTo(x1) != 0)
    	{
    		great_grand_parent = grand_parent;
    		grand_parent = parent;
    		parent = current;
    		
    		current = x1.compareTo(current.element) < 0 ? (Entry<T>) current.left : (Entry<T>) current.right;
    		if(current == tempNode || current == nil)
    		{
    			break;
    		}
    		if(((Entry<T>)current.left).color == RED && ((Entry<T>)current.right).color == RED)
			{
				reStructureTree(x1);
			}    	   				    	
    	}
    	//If the tree already contains the element
    	if(current!=nil) 
    	{
    		return false;
    	}
    	
    	current = new Entry<T>(x1,null,null);
    	current.left = nil;
    	current.right = nil;
    	
    	if(x1.compareTo(parent.element) < 0)
    	{
    		parent.left = current;
    	}
    	else
    	{
    		parent.right = current;
    	}
    	current.parent = parent;
    	
    	reStructureTree(x1);
    	super.root = root;
    	root.parent = header;
    	size++;
    	return true;
    	
    }
    
    /**
     * Internal routine that is called during an insertion
     * If a node has two red children. Performs flip and rotations.
     * @param x1 the item being inserted.
     */
    private void reStructureTree(T x1) {
	  current.color = RED;
	  ((Entry<T>)current.left).color = BLACK;
	  ((Entry<T>)current.right).color = BLACK;
		
	  if(parent.color == RED)
	  {
		  grand_parent.color = RED;
		  if((x1.compareTo(grand_parent.element) < 0) != (x1.compareTo(parent.element) < 0))
		  {
			  parent = rotate(x1,grand_parent);
		  }
		  current = rotate(x1,great_grand_parent);
		  current.color = BLACK;
	  }
	  root = (Entry<T>)header.right;
	  ((Entry<T>)root).color = BLACK;
	  
	}

    
    /**
     * Internal routine that performs rotation
     * Because the result is attached to the parent, there are four different cases possible :
     * RR, RL, LR and LL respectively
     * @param item the item in reStructureTree
     * @param parent the parent of the root of the rotated subtree.
     * @return the root of the rotated subtree.
     */
	private Entry<T> rotate(T item, Entry<T> parent) {
		if( item.compareTo( parent.element ) < 0 )
		{
			if(item.compareTo(parent.left.element)<0)
			{
				parent.left = rotateWithLeftChild((Entry<T>) parent.left);
				parent.left.parent = parent;
				return (Entry<T>) (parent.left);
			}
			else
			{
				parent.left = rotateWithRightChild((Entry<T>) parent.left);
				parent.left.parent = parent;
				return (Entry<T>) (parent.left);
				
			}
		}
		else
		{
			if(item.compareTo(parent.right.element)<0)
			{
				parent.right = rotateWithLeftChild((Entry<T>) parent.right);
				parent.right.parent = parent;
				return (Entry<T>) (parent.right);
				
			}
			else
			{
				parent.right = rotateWithRightChild((Entry<T>) parent.right);
				parent.right.parent = parent;
				return (Entry<T>) (parent.right);
				
			}
		}        	
	}
	
	 /**
     * Rotate binary tree node with left child.
     */
	private Entry<T> rotateWithLeftChild( Entry<T> node_y )
    {
    	Entry<T> node_x = (Entry<T>) node_y.left;
        node_y.left = node_x.right;
        node_x.right = node_y;
        node_y.parent = node_x;
        node_y.left.parent = node_y;
        return node_x;
    }

    /**
     * Rotate binary tree node with right child.
     */
	private Entry<T> rotateWithRightChild( Entry<T> node_x )
    {
    	Entry<T> node_y = (Entry<T>) node_x.right;
        node_x.right = node_y.left;
        node_y.left = node_x;
        node_x.parent = node_y;
        node_x.right.parent = node_x;
        return node_y;
    }

	/**
     * Remove from the tree
     * @param v the item to remove
     */
	public T remove(T v)
    {
		
		if(root == null || root == nil) {
			return null;
		}
		
		
    	psd180000.BinarySearchTree.Entry<T> v1 = super.find(v);
    	if(v1.element != v || size == 0) {
    		System.out.println("Element "+v+" not present");
    		return null;
    	}
    	if(size == 1 && root.element == v) {
			header.right = nil;
			root = null;
			size = 0;
			return v;
		}
    	RedBlackTree.Entry<T> x = nil;
        RedBlackTree.Entry<T> y = nil;
        RedBlackTree.Entry<T> z=( RedBlackTree.Entry<T>)v1;
       
        
        if(z.left == nil || z.right==nil)
        {
        	y = z;
        }      
        else
        {
        	st.push(z);
        	psd180000.BinarySearchTree.Entry<T> min = super.find(z.right, z.element);
        	RedBlackTree.Entry<T> minRight=( RedBlackTree.Entry<T>)min;
    		y = minRight;

        }
        
        if(y.left!=nil)
        {
        	x = (Entry<T>) y.left;        	
        }
        else
        {
        	x = (Entry<T>) y.right;
        }
        
        x.parent = y.parent;
        if(y.parent == header || y.parent == nil || y.parent == null)
        {
        	root = x; 
        }
        else if(y.parent.left!=nil && y.parent.left==y)
        {
        	y.parent.left = x;
        }
        else if(y.parent.right!=nil && y.parent.right==y)
        {
        	y.parent.right = x;
        }
        if( y != z) {
        	z.element = y.element;
        }
    	if(y.color == BLACK) {
    		fixUp(x);
    	}
    	
    	size--;
    	return v;
    	
    }
	/**
	  * printTree() function used to print the elements of the tree in printTree (Left, Root, Right) way.
	  */
	public void printTree() {
		printTree(root);
	}
	
	/**
	  * printTree() function used to print the elements of the tree level by level
	  * BFS Approach
	  */
	public void levelorder() {
		if(root == null || root == nil) {
			return;
		}
		Queue<Entry<T>> queue = new LinkedList<>();
		queue.add(root);
		while(!queue.isEmpty()) {
			int size = queue.size();
			for(int i = 0;i < size;i++) {
				Entry<T> node = queue.remove();
				System.out.print(node.element + " " + (node.color == true ? "RED" : "BLACK")+" ");
				if(node.left != nil ) {
					queue.add((Entry<T>) node.left);
				}
				if(node.right != nil) {
					queue.add((Entry<T>) node.right);
				}
			}
			System.out.println();
			System.out.println("-------------");
		}
	}
	 public void printTree(Entry<T> root_node) {
			System.out.print("[" + size + "]");
			inorder(root_node);
			System.out.println();
		 }

		   
	/**
	  * printTree() function used to print the elements of the tree in printTree (Left, Root, Right) way
	  * @param : root_node
	  * prints the printTree traversal of the tree
	  */
	public void inorder(Entry<T> root_node) {		
		if(root_node == nil) {
			return;
		}
		if(root_node != null && root_node != nil ) {
			inorder((Entry<T>)root_node.left);
			System.out.print(root_node.element+ (root_node.color == true ? "(RED) " : "(BLACK) "));
			inorder((Entry<T>)root_node.right);
		}
	}
	
	/**
	  * fixUp() function used to perform the fixUp in case of remove operation
	  * Called in order to fix the Black-Height of the tree
	  * @param x: the node on which we need to perform the fixup
	  */
    private void fixUp(Entry<T> node_x) {
    	
		while(node_x != this.root && node_x.color == BLACK) {
	        RedBlackTree.Entry<T> xparent=( RedBlackTree.Entry<T>)node_x.parent;
			if(node_x.parent.left ==  node_x) {
				Entry<T> w = (Entry<T>)xparent.right;
				if(w.color == RED) {
					w.color = BLACK;
					xparent.color = RED;
					rotateLeft(xparent);
					w = (Entry<T>)xparent.right;
				}
				if(((Entry<T>)w.left).color == BLACK && ((Entry<T>)w.right).color == BLACK) 
				{
					w.color = RED;
					node_x = (Entry<T>)node_x.parent;
				}
				else if(((Entry<T>)w.right).color == BLACK) {
					((Entry<T>)w.left).color = BLACK;
					w.color = RED;
					rotateRight(w);
					w = (Entry<T>)node_x.parent.right;
				}
				else {
					w.color = ((Entry<T>)node_x.parent).color;
					((Entry<T>)node_x.parent).color = BLACK;
					((Entry<T>)w.right).color = BLACK;
					rotateLeft((Entry<T>)node_x.parent);
					node_x = (Entry<T>)root;
				}
				
			}
			else {
				Entry<T> w = (Entry<T>)node_x.parent.left;
				if(w.color == RED) {
					w.color = BLACK;
					((Entry<T>)node_x.parent).color = RED;
					rotateRight((Entry<T>)node_x.parent);
					w = (Entry<T>)node_x.parent.left;
				}
				if(((Entry<T>)w.left).color == BLACK && ((Entry<T>)w.right).color == BLACK) 
				{
					w.color = RED;
					node_x = (Entry<T>)node_x.parent;
				}
				else if(((Entry<T>)w.left).color == BLACK) {
					((Entry<T>)w.right).color = BLACK;
					w.color = RED;
					rotateLeft(w);
					w = (Entry<T>)node_x.parent.left;
				}
				else {
					w.color = ((Entry<T>)node_x.parent).color;
					((Entry<T>)node_x.parent).color = BLACK;
					((Entry<T>)w.left).color = BLACK;
					rotateRight((Entry<T>)node_x.parent);
					node_x = (Entry<T>)root;
				}
				
			}
			
		}
		node_x.color = BLACK;
	}
    
    public static void main(String[] args) {
    	RedBlackTree<Integer> t = new RedBlackTree<>();
    	Scanner in = new Scanner(System.in);
           while(in.hasNext()) {
               int x = in.nextInt();
               if(x > 0) {
                   System.out.print("Add " + x + " : ");
                   t.add(x);
                   t.levelorder();
               } else if(x < 0) {
                   System.out.print("Remove " + x + " : ");
                   t.remove(-x);
                   t.levelorder();
               }  
               else {
            	   System.out.println("You've entered 0. Please enter either positive or negative values");
            	   
               }
           }

        }
}
