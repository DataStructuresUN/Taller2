package unal.datastructures;

import java.lang.reflect.*;
import java.util.*;

public class LBT<T extends Comparable<T>> extends LinkedBinaryTree<T> implements BinaryTree<T>
{
	static Method maxElement;
	static Method minElement;
	static Method reverse;
	static Method numLeaves;
	static int leavesCount;

	public ArrayList<Integer> arr = new ArrayList<>(); //auxiliar array
	public ArrayList<BinaryTreeNode<T>> btnArr = new ArrayList<>(); //auxiliar BTN array 

	static
	{
		try
		{
			Class<LBT> lbt = LBT.class;
			maxElement = lbt.getMethod("maxElement", BinaryTreeNode.class);
			minElement = lbt.getMethod("maxElement", BinaryTreeNode.class);
			reverse = lbt.getMethod("reverse", BinaryTreeNode.class);
		}
		catch(Exception e)
		{

		}
	}

	public boolean isFull()
	{
		return ( size( ) == ( int ) Math.pow( 2.0, ( double ) height( ) ) - 1 );
	}

	public T maxElement()
	{
		return (root == null) ? null : getMax(root);		
	}

	static <T extends Comparable<T>> T getMax(BinaryTreeNode<T> b)
	{
		T max = ( T ) b.element;
		T leftNode = max;
		T rightNode = max;

		if(!(b.leftChild == null))
			leftNode = getMax(b.leftChild);
		if(!(b.rightChild == null))
			rightNode = getMax(b.rightChild);

		if(leftNode.compareTo(rightNode) > 0)
		{
			if(leftNode.compareTo(max) > 0)
				max = leftNode;	
		}
		else
			if(rightNode.compareTo(max) > 0)
				max = rightNode;

		return max;
	}

	public T minElement()
	{
		return (root == null) ? null : getMin(root);		
	}

	static <T extends Comparable<T>> T getMin(BinaryTreeNode<T> b)
	{
		T min = ( T ) b.element;
		T leftNode = min;
		T rightNode = min;

		if(!(b.leftChild == null))
			leftNode = getMin(b.leftChild);
		if(!(b.rightChild == null))
			rightNode = getMin(b.rightChild);

		if(leftNode.compareTo(rightNode) < 0)
		{
			if(leftNode.compareTo(min) < 0)
				min = leftNode;	
		}
		else
			if(rightNode.compareTo(min) < 0)
				min = rightNode;

		return min;
	}

	public void reverse()
	{
		theReverse( root );
	}

	static <T> void theReverse(BinaryTreeNode<T> n)
	{
		BinaryTreeNode<T> tmp = n.leftChild;

		if(n.leftChild == null || n.rightChild == null)
			return;
		else
		{
			n.leftChild = n.rightChild;
			n.rightChild = tmp;

			theReverse(n.leftChild);
			theReverse(n.rightChild);
		}
	}

	public int numLeaves() throws NoSuchMethodException
	{
		count=0;
		preOrder(LBT.class.getMethod("getLeaves", BinaryTreeNode.class));
		return count;
	}

	public static <T> void getLeaves(BinaryTreeNode<T> t)
	{ 
		if(isLeave(t))
			count++;                   
	}

	public static boolean isLeave(BinaryTreeNode t)
	{
		if (t.rightChild == null && t.leftChild == null)
			return true;
		return false; 
	}

	public void createTree( T[] preorder, T[] inorder) 
	{
		btnArr.clear(); 
		count=-1;  

		if(preorder.length != inorder.length) 
			return;    

		for(int i = 0; i < inorder.length - 1; i++)
		{
			int k = 0;
			for(int j = 0; j < inorder.length - 1; j++)
			{            
				if(preorder[i].equals(inorder[j]))
					k++;                      
			}
			if(k != 1)            
				return;        
		}

		for(int i = 0; i < inorder.length; i++)
		{
			btnArr.add(new BTN(preorder[0]));
		}    
		try
		{
			if(this.theCreate(preorder,inorder)==null);        
		}
		catch(Exception e)
		{
			System.out.println("Tree creation unsuccessfull.");
			return; 
		}

		this.root = btnArr.get(0);

	}

	public T theCreate(T[] preorder, T[] inorder)
	{
		int rootIndex = 0; 

		for(int i = 0; i < preorder.length; i++)
		{
			if(preorder[0].equals(inorder[i]))
			{
				rootIndex = i;            
			}                
		}
		ArrayList<T> leftPre = new ArrayList<>(), leftIn = new ArrayList<>(), rightPre = new ArrayList<>(), rightIn = new ArrayList<>();

		for(int i = 0; i < rootIndex; i++)
		{
			leftPre.add(inorder[i]);         
		}

		for(int i = rootIndex + 1; i < inorder.length; i++)
		{
			leftIn.add(inorder[i]);
		}

		for(int i = 1; i < rootIndex + 1; i++)
		{
			rightPre.add(preorder[i]);         
		}

		for(int i = rootIndex + 1; i < inorder.length; i++)
		{
			rightIn.add(preorder[i]);        
		}

		T[] lpArr= (T[]) leftPre.toArray(new Comparable[leftPre.size()]);
		T[] rpArr= (T[]) rightPre.toArray(new Comparable[rightPre.size()]);
		T[] liArr= (T[]) leftIn.toArray(new Comparable[leftIn.size()]);
		T[] riArr= (T[]) rightIn.toArray(new Comparable[rightIn.size()]);

		T t=null, y=null; 

		btnArr.get(++count).setElement(preorder[0]);

		if(rpArr.length>0)                        
			y=theCreate(rpArr,lpArr);

		if(riArr.length>0)
			t=theCreate(riArr,liArr);  

		if(y!=null)    
			btnArr.get(btnArr.indexOf(new BTN(preorder[0]))).setLeftChild(btnArr.get(btnArr.indexOf(new BTN(y))));

		if(t!=null)      
			btnArr.get(btnArr.indexOf(new BTN(preorder[0]))).setRightChild(btnArr.get(btnArr.indexOf(new BTN(t))));

		return preorder[0];
	}

	public boolean compare(LBT<T> other)
	{
		return equals(this.root, other.root);
	}

	public boolean equals(BinaryTreeNode<T> r1, BinaryTreeNode<T> r2)
	{
		if(r1 == r2)
			return true;

		if(r1 == null || r2 == null)
			return false;

		return r1.element.equals(r2.element) &&
				equals(r1.leftChild, r2.leftChild) &&
				equals(r1.rightChild, r2.rightChild);
	}

	public boolean isBalanced()
	{
		return auxBal(root);    //se hace uso de la recursividad
	}

	static<T extends Comparable<? super T>> boolean auxBal(BinaryTreeNode t)
	{

		if(!(Math.abs(theHeight(t.leftChild) - theHeight(t.rightChild)) <= 2))
		{
			return false;
		}
		if(t.leftChild != null)
			return auxBal(t.leftChild);
		if(t.rightChild != null)
			return auxBal(t.rightChild);

		return true;
	}
	
	public boolean isComplete()
	{
		arr.clear();
		int k = 0; 
		count = 0;
		if(isEmpty()) 
			return false;        
		
		ArrayList<Integer> a = arrComplete(root);
		
		for(int i = 0; i < a.size() - 1; i++)
		{            
			if(a.get(i).equals(0))             
				return false; 
			if(a.get(i) > a.get(i+1))          
				return false;
			if(!(a.get(i).equals(a.get(i+1))))
			{
				k++;
			}
		}    
		if(k > 1)
			return false;   
		
		return true;                           

	}

	public ArrayList arrComplete(BinaryTreeNode t)
	{
		count++;                  
		if(isLeave(t))
		{        
			arr.add(0, count);    
		}
		else
		{
			if(t.leftChild != null)
			{ 
				arrComplete(t.leftChild);
			}
			else
			{
				arr.add(0, 0);                                   
			}                     
			if(t.rightChild!=null)
			{          
				arrComplete(t.rightChild);              
			}
			else
			{
				if(t.leftChild != null && !(isLeave(t.leftChild)))
					arr.add(0, 0);                           
			}
		}
		count--;
		
		return arr;
	}
	
	static <T extends Comparable<T>> void test(LBT<T> s, LBT<T> s1)
	{
		System.out.println("Preorder for tree 1 is: ");
		s.preOrderOutput();
		System.out.println();
		System.out.println("Preorder for tree 2 is: ");
		s1.preOrderOutput();
		
		System.out.println();
		
		System.out.println("Inorder for tree 1 is: ");
		s.inOrderOutput();
		System.out.println();
		System.out.println("Inorder for tree 2 is: ");
		s1.inOrderOutput();
		
		System.out.println();
		
		System.out.println("Postorder for tree 1 is: ");
		s.postOrderOutput();
		System.out.println();
		System.out.println("Postorder for tree 2 is: ");
		s1.postOrderOutput();
		
		System.out.println();
		
		System.out.println("Level order for tree 1 is: ");
		s.levelOrderOutput();
		System.out.println();
		System.out.println("Level order for tree 2 is: ");
		s1.levelOrderOutput();
		
		System.out.println();
		
		System.out.println("Max element in tree 1 is: " + s.maxElement());
		System.out.println("Max element in tree 2 is: " + s1.maxElement());
		System.out.println("Min element in tree 1 is: " + s.minElement());
		System.out.println("Min element in tree 2 is: " + s1.minElement());
		
		try
		{
			System.out.println("1's leaves number: " + s.numLeaves());
			System.out.println("2's leaves number: " + s.numLeaves());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Is 1 balanced? : " + s.isBalanced());
		System.out.println("Is 2 balanced? : " + s1.isBalanced());
		
		System.out.println("Is 1 full? : " + s.isFull());
		System.out.println("Is 2 full? : " + s1.isFull());
		
		System.out.println("Is 1 complete? : " + s.isComplete());
		System.out.println("Is 2 complete? : " + s1.isComplete());
		
		System.out.println("Are 1 & 2 equal? : " + s.compare(s1));
	}
	
	/** test program */
	public static void main( String[] args )
	{

		String[] preorder1 = {"A","B","D","C","E","G","H","F","I","J"};
		String[] inorder1 =  {"D","B","A","G","E","H","C","F","I","J"};
		String[] preorder2={"A","B","D","H","I","E","J","C","F","G"};
		String[] inorder2={"H","D","I","B","J","E","A","F","C","G"};

		LBT<String> s = new LBT<>();
		s.createTree(preorder1, inorder1);
		LBT<String> s1 = new LBT<>();
		s1.createTree(preorder2, inorder2);
		
		test(s, s1);
		
		Integer[] pre = {15, 5, 3, 12, 10, 6, 7, 13, 16, 20, 18, 23};
		Integer[] in = {3, 5, 6, 7, 10, 12, 13, 15, 16, 18, 20, 23};
		
		System.out.println();
		
		LBT<Integer> i = new LBT<>();
		i.createTree(pre, in);
		LBT<Integer> i1 = i;
		
		test(i, i1);
	}
}

class BTN<T> extends BinaryTreeNode<T> 
{

	public BTN( ) 
	{ 
		super();
	}

	public BTN( T theElement )
	{
		super(theElement);
	}

	public BTN( T theElement,BinaryTreeNode<T> theleftChild,BinaryTreeNode<T> therightChild )
	{
		super(theElement, theleftChild, therightChild);      
	}

	@Override
	public boolean equals(Object obj)
	{
		BTN t = (BTN)obj;
		if(t.element == null && element == null)
			return true;
		if(t.element == null)
			return false;      
		return t.element.equals(element);
	}
}