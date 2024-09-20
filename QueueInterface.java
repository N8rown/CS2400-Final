
//
// Name: Brown, Nathan 
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-f23
//
// Description:
// An implementation of a grapht ADT to map airports along with an alorgithm that finds the shortest path between said airports.
//
public interface QueueInterface<T> {
    /** Adds a new entry to the back of this queue.
       @param newEntry  An object to be added. */
       public void enqueue(T newEntry);
    
       /** Removes and returns the entry at the front of this queue.
          @return  The object at the front of the queue.
          @throws  EmptyQueueException if the queue is empty before the operation. */
       public T dequeue();
       
       /** Retrieves the entry at the front of this queue.
          @return  The object at the front of the queue.
          @throws  EmptyQueueException if the queue is empty. */
       public T getFront();
       
       /** Detects whether this queue is empty.
          @return  True if the queue is empty, or false otherwise. */
       public boolean isEmpty();
      
       /** Removes all entries from this queue. */
       public void clear(); 

}
