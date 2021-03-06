/** a queue class that uses a one-dimensional array */

package unal.datastructures;

public class ArrayQueue<T> implements Queue<T>
{
   // fields
   int front;  // one counterclockwise from first element
   int rear;   // position of rear element of queue
   T[] queue;  // element array

   // constructors
   /** create a queue with the given initial capacity */
   @SuppressWarnings( "unchecked" )
   public ArrayQueue( int initialCapacity )
   {
      if( initialCapacity < 1 )
         throw new IllegalArgumentException
            ( "initialCapacity must be >= 1" );
      queue = ( T[] ) new Object[ initialCapacity + 1 ];
      front = rear = 0;
   }

   /** create a queue with initial capacity 10 */
   public ArrayQueue( )
   {
      this( 10 );
   }

   // methods
   /** @return true iff queue is empty */
   public boolean isEmpty( )
   {
      return front == rear;
   }

   /** @return front element of queue
    * @return null if queue is empty */
   public T getFrontElement( )
   {
      if( isEmpty( ) ) return null;
      else return queue[ ( front + 1 ) % queue.length ];
   }

   /** @return rear element of queue
    * @return null if the queue is empty */
   public T getRearElement( )
   {
      if( isEmpty( ) ) return null;
      else return queue[ rear ];
   }

   /** insert theElement at the rear of the queue */
   @SuppressWarnings( "unchecked" )
   public void put( T theElement )
   {
      if( ( rear + 1 ) % queue.length == front )
      {  // double array size
         // allocate a new array
         T[] newQueue = ( T[] ) new Object [ 2 * queue.length ];

         // copy elements into new array
         int start = ( front + 1 ) % queue.length;
         if( start < 2 )
            // no wrap around
            System.arraycopy( queue, start, newQueue, 0, queue.length - 1 );
         else
         {  // queue wraps around
            System.arraycopy( queue, start, newQueue, 0, queue.length - start );
            System.arraycopy( queue, 0, newQueue, queue.length - start, rear + 1 );
         }

         // switch to newQueue and set front and rear
         front = newQueue.length - 1;
         rear = queue.length - 2;   // queue size is queue.length - 1
         queue = newQueue;
      }

      // put theElement at the rear of the queue
      rear = ( rear + 1 ) % queue.length;
      queue[ rear ] = theElement;
   }

   /** remove an element from the front of the queue
    * @return removed element
    * @return null if the queue is empty */
   public T remove( )
   {
      if( isEmpty( ) ) return null;
      front = ( front + 1 ) % queue.length;
      T frontElement = queue[ front ];
      queue[ front ] = null; // enable garbage collection
      return frontElement;
   }
}