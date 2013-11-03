package unal.datastructures;

import java.lang.reflect.*;

public class LBT<T> extends LinkedBinaryTree<T> implements BinaryTree<T>
{
	static Method maxElement;
	static Method minElement;
	
	static
	{
		try
		{
			Class<LBT> lbt = LBT.class;
			maxElement = lbt.getMethod("maxElement", BinaryTreeNode.class);
			minElement = lbt.getMethod("maxElement", BinaryTreeNode.class);
		}
		catch(Exception e)
		{
			
		}
	}
	
	public boolean isFull()
	{
		return (size( ) == ( int ) Math.pow( 2.0, ( double ) height( ) ) - 1 );
	}
	
	public void maxElement(Method visit)
	{
		LBT.visit = visit;
//		getMax(root);
	}

//	static <T> getMax(BinaryTreeNode<T> t)
	
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
		v.makeTree(new Integer ( 7 ), a, a );
		z.makeTree(new Integer ( 5 ), v, w );
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
	}
}
