/** @author 
 *  Pranjal Deshmukh : psd180000
 *  Aniket Pathak : adp170003
 *  SP6 : Binary search tree
 **/


package psd180000;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

/**
 * This is BinarySearchTree class 
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
	 /**
     * This is static class Entry
     * @param element : Element to be processed.
     * @param left :  Left child
     * @param right : Right child
     **/
	static class Entry<T> {
        T element;
        Entry<T> left, right, parent;
        public Entry(T x, Entry<T> left, Entry<T> right) {
         this.element = x;
         this.left = left;
	     this.right = right;
	     this.parent = null;
        }
    }
    
    Entry<T> root;
    int size;
    Stack<Entry<T>> st = new Stack<>();

    public BinarySearchTree() {
	root = null;
	size = 0;
    }

    /** Driver method for Find
     */
    public Entry<T> find(T x) 
    {
    	st = new Stack<Entry<T>> ();
    	st.push(null);
    	return find(this.root, x);    	
    }
    
    /** Find x within subtree rooted at X
     */
    public Entry<T> find (Entry<T> t, T x) 
    {
    	if (t == null || t.element == x) 
    	{
    		return t;
    	}
    	while(true) 
    	{
//    		System.out.println("x: "+x);
//    		System.out.println("t: "+t.element);
    		if (x.compareTo(t.element) == 0)
    		{
    			break;
    		}
    		else if (x.compareTo(t.element) < 0) 
    		{
    			if (t.left == null || t.left.element == null) 
    			{
       				break;
    			}
    			else 
    			{
    				st.push(t);
    				t = t.left;
    			}
    		}
    		else if (t.right == null || t.right.element == null) {    			
    			break;
    		}
    		else {
    			st.push(t);
    			t = t.right;
    		}
    	}
    	return t;
    }

    /**
     * This method is used to detect if element is present in the given
     * BST or not.
     * @param x : Input element. 
     * @return True : If present.
     * @return False : If absent.
     */
    public boolean contains(T x) {
    	Entry<T> t = find(x);
    	if( t == null || t.element.compareTo(x) != 0) {
    		return false;
    	}
    	return true;
	
    }
    
   
    /**
     * This method is used to detect if there is an 
     * element that is equal to x in the tree
     * @param x : Input element. 
     * @return Element in tree that is equal to x
     * @return null otherwise
     */
    public T get(T x) {
    	Entry<T> t = find(x);
    	if( t == null || t.element.compareTo(x) != 0) {
    		return null;
    	}
    	return (T) t;
    }
    

    /**
     * This method is used to Add x to tree
     * @param x : Input element. 
     * @return true : if x is a new element added to tree
     */
    public boolean add(T x) {
    	if(size == 0) {
    		root = new Entry(x, null, null);
    		size++;
    		return true;
    	}
    	else {
    		Entry<T> t = find(x);
    		if( t.element.compareTo(x) == 0 ) {
    			t.element = x;//replace
    			return false;
    		}
    		else if(t.element.compareTo(x) == -1) {
    			t.right =  new Entry(x, null, null);
    		}
    		else {
    			t.left =  new Entry(x, null, null);
    		}
    		size++;
    		return true;
    		
    	}
    	

    }
    
    

    /**
     * This method is used to Remove x from tree. 
     * @return x : Return x if found, otherwise return 
     * @return null : for all other cases.
     * */
    public T remove(T x) {
    	if(root == null) {
    		return null;
    	}
    	Entry<T> t = find(x);
    	if(t.element.compareTo(x) != 0) {
    		return null;
    	}
    	if(t.left == null || t.right == null) {
    		splice(t);
    	}
    	else {
    		st.push(t);
    		Entry<T> minRight = find(t.right, x);
    		t.element = minRight.element;
    		splice(minRight);
    	}
    	size--;
    	return x;
    }
    
    /**
     * This method is used to link parent and child node of the node to be removed
     * This method has the requirement that the node to be removed has one child (atmost)
     * */
    public void splice(Entry<T> t) {
    	Entry<T> parent = st.peek();
    	Entry<T> child = t.left != null ? t.left : t.right;
    	if(parent == null) {
    		root = child;
    	}
    	else if(parent.left == t) {
    		parent.left = child;
    	}
    	else {
    		parent.right = child;
    	}
    }
    
    /**
     * This method is used to find min element from BST. 
     * (Leftmost Node)
     * @return min : Minimum element 
     * */
    public T min() {
      Entry<T> t = root;
       while(t.left != null) {
    	   t = t.left;
       }
       return (T) t;
       
       //Another Approach:
       /*
       return (T) this.toArray()[0];
       */
    }
    /**
     * This method is used to find max element from BST.
     * (Rightmost Node) 
     * @return max : Maximum element 
     * */
    public T max() {
    	Entry<T> t = root;
        while(t.right != null) {
     	   t = t.right;
        }
        return (T) t;
    	//Another Approach:
        /*
        return (T) this.toArray()[size -1];
        */
    }

    /**
     * This method is used to Create an array with 
     * the elements using in-order traversal of tree.
     * @return Array : Array with elements in Inorder. 
     * */
    public Comparable[] toArray() {
    	Comparable[] arr = new Comparable[size];
    	int counter = 0;
    	if(this.root == null) {
    		return arr;
    	}
    	Stack<Entry<T>> st = new Stack<>();
    	while(true) {
    		if(root == null) {
    			if(st.isEmpty()) {
    				break;
    			}
    			root =  st.pop();
    			arr[counter] = (Comparable) root.element;
    			root = root.right;
    			counter++;
    		}
    		else {
    			st.add(root);
    			root = root.left;
    		}
    	}
	
	
	return arr;
    }
    


// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
	Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
	return null;
    }

    

    public static void main(String[] args) {
	BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i=0; i<t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }           
        }
    }


    public void printTree() {
	System.out.print("[" + size + "]");
	printTree(root);
	System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
	if(node != null) {
	    printTree(node.left);
	    System.out.print(" " + node.element);
	    printTree(node.right);
	}
    }

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/
