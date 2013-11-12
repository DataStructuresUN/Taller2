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

	//TODO: Pendiente
	static <T extends Comparable<T>> LBT<T> createTree(T[] preOrder, T[] inOrder)
	{
		LBT<T> newTree = new LBT<>(),
				a = new LBT<>(),
				b = new LBT<>();
				
		newTree.makeTree(preOrder[0], a, a);
				
		return newTree;
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
	
	/** test program */
	public static void main( String[] args )
	{
		LBT<Integer> a = new LBT<>( ),
		v = new LBT<>( ),
		w = new LBT<>( ),
		x = new LBT<>( ),
		y = new LBT<>( ),
		z = new LBT<>( );
		y.makeTree( new Integer( 1 ), a, a );
		z.makeTree( new Integer( 2 ), a, a );
		x.makeTree( new Integer( 3 ), y, z );
		w.makeTree( new Integer( 6 ), a, a );
		v.makeTree( new Integer( 7 ), a, a );
		z.makeTree( new Integer( 5 ), v, w );
		y.makeTree( new Integer( 4 ), x, z );

		System.out.println( "Preorder sequence is " );
		y.preOrderOutput( );
		System.out.println( );
		System.out.println( "Inorder sequence is " );
		y.inOrderOutput( );
		System.out.println( );

		System.out.println( "Postorder sequence is " );
		y.postOrderOutput( );
		System.out.println( );

		System.out.println( "Level order sequence is " );
		y.levelOrderOutput( );
		System.out.println( ); 

		System.out.println( "Number of nodes = " + y.size( ) );

		System.out.println( "Height = " + y.height( ) );

		if(y.isFull( ))
			System.out.println("The tree is full");
		else
			System.out.println("The tree is not full");

		System.out.println( "Level order sequence is " );
		y.levelOrderOutput( );
		y.reverse();
		System.out.println();
		System.out.println( "Level order sequence is " );
		y.levelOrderOutput( );
		System.out.println();

		System.out.println(y.maxElement());
		System.out.println(y.minElement());

		try 
		{
			System.out.println(y.numLeaves());
		}
		catch (NoSuchMethodException e) 
		{
			e.printStackTrace();
		}
		
		LBT<Integer> q = y;
		
		q.levelOrderOutput();
		System.out.println(y.compare(q));
	}
}
