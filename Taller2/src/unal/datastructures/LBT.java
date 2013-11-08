package unal.datastructures;

import java.lang.reflect.*;
import java.util.*;

public class LBT<T> extends LinkedBinaryTree<T> implements BinaryTree<T>
{
	static Method maxElement;
	static Method minElement;
	static Method reverse;
	
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
	
	public void maxElement(Method visit)
	{
		ArrayList<T> a = new ArrayList<>();
		BinaryTreeNode<T> b = root;
		
		while(b != null)
		{
			try
			{
				visit.invoke(null, b);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			
			if(b.leftChild != null)
				a.add(b.leftChild.element);
			if(b.rightChild != null)
				a.add(b.rightChild.element);
			
			Collections.sort(a);
		}
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
		w.makeTree(new Integer ( 6 ), a, a );
		//v.makeTree(new Integer ( 7 ), a, a );
		z.makeTree(new Integer ( 5 ), a, w );
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
	}
}

class ExtendedBTN<T extends Comparable<T>> extends BinaryTreeNode<T>
{
	
}
