//
// Name: Brown, Nathan 
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-f23
//
// Description:
// An implementation of a grapht ADT to map airports along with an alorgithm that finds the shortest path between said airports.
//
public final class PriorityQueue<T extends Comparable<? super T>> implements PriorityQueueInterface<T>
{
    private final T[] heap; 
    private int numberOfEntries; //also last index
    private boolean integrityOK = false;
    private static final int DEFAULT_CAPACITY = 100;
    private static final int MAX_CAPACITY = 10000;
    
    
    public PriorityQueue()
    {
        this(DEFAULT_CAPACITY);
    } // end default constructor
    
    /** Creates an empty queue having a given capacity.
        @param desiredCapacity  The integer capacity desired. */
    public PriorityQueue(int desiredCapacity)
    {
        if (desiredCapacity <= MAX_CAPACITY)
        {
            // The cast is safe because the new array contains null entries
            @SuppressWarnings("unchecked")
            T[] tempQueue = (T[])new Comparable<?>[desiredCapacity]; // Unchecked cast
            heap = tempQueue;
            numberOfEntries = 0;
            integrityOK = true;
            // Test that contents are nulls - OK
            //      for (int index = 0; index < desiredCapacity; index++) 
            //         System.out.print(queue[index] + " ");
            //      System.out.println();
        }
        else
            throw new IllegalStateException("Attempt to create a queue " + "whose capacity exceeds " + "allowed maximum.");
    } // end constructor
    
    
    /** Adds a new entry to the front/back of this priority queue.
        @param newEntry  An object to be added. */
    public void add(T newEntry)
    {
        checkIntegrity();
        int newIndex = numberOfEntries + 1;   // Index of next available array location
        int parentIndex = newIndex / 2; // Index of parent of available location
        while ((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) < 0)
        {
            // Move parent to available location
            heap[newIndex] = heap[parentIndex];
            // Update indices
            newIndex = parentIndex;
            parentIndex = newIndex / 2;
        } // end while
        heap[newIndex] = newEntry;  // Place new entry in correct location
        numberOfEntries++;
    } // end add
    
    
    /** Removes and returns the entry having the highest priority.
        @return Either the object having the highest priority or, if the
                priority queue is empty before the operation, null. */
    public T remove()
    {
        checkIntegrity();
        T root = null;
        if (!isEmpty())
        {
            root = heap[1];             // Return a value
            heap[1] = heap[numberOfEntries];  // Form a semiheap
            numberOfEntries--;                // Decrease size
            reheap(1);                  // Transform to a heap
        } /// end if
        return root;
    } // end remove
    private void reheap(int rootIndex)
    {
        boolean done = false;
        T orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex;
        while (!done && (leftChildIndex <= numberOfEntries))
        {
            int largerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;
            if ((rightChildIndex <= numberOfEntries) && 
                    heap[rightChildIndex].compareTo(heap[largerChildIndex]) < 0)
            {
                largerChildIndex = rightChildIndex;
            } // end if
            
            if (orphan.compareTo(heap[largerChildIndex]) > 0)
            {
                heap[rootIndex] = heap[largerChildIndex];
                rootIndex = largerChildIndex;
                leftChildIndex = 2 * rootIndex;
            }
            else
                done = true;
        } // end while
        heap[rootIndex] = orphan;
    } // end reheap
    /** Retrieves the entry having the highest priority.
        @return Either the object having the highest priority or, if the
                priority queue is empty, null. */
    public T peek()
    {
        T root = null;
        if (!isEmpty())
           root = heap[1];
        return root;
    } // end peek
    
    /** Detects whether this priority queue is empty.
       @return  True if the priority queue is empty, or false otherwise. */
    public boolean isEmpty()
    {
        return numberOfEntries < 1;
    } // end isEmpty
    
    /** Gets the size of this priority queue.
        @return  The number of entries currently in the priority queue. */
    public int getSize()
    {
        return numberOfEntries;
    } // end getSize

    /* Removes all entries from this priority queue. */
    public void clear()
    {
        while (numberOfEntries >= 0)
        {
           heap[numberOfEntries] = null;
           numberOfEntries--;
        } // end while
        numberOfEntries = 0;
    } // end clear
     // end clear
    
    // Removes and returns the entry at a given index within the array.
    // If no such entry exists, returns null.
    // Precondition: 0 <= givenIndex < numberOfEntries.
    // Precondition: checkInitialization has been called.
    private T removeEntry(int givenIndex)
    {
        T result = null;
      
        if (!isEmpty() && (givenIndex >= 0))
        {
            result = heap[givenIndex];          // Entry to remove
            int lastIndex = numberOfEntries - 1;
            heap[givenIndex] = heap[lastIndex];  // Replace entry to remove with last entry
            heap[lastIndex] = null;             // Remove reference to last entry
            numberOfEntries--;
        } // end if
      
        return result;
    } // end removeEntry
   
    // Throws an exception if this object is not initialized.
    private void checkIntegrity()
    {
        if (!integrityOK)
            throw new SecurityException("ArrayQueue object is corrupt.");
    } // end checkIntegrity
    
// Returns true if the array queue is full, or false if not.
    private boolean isArrayFull()
    {
        return numberOfEntries >= heap.length;
    } // end isArrayFull

    
} // end LinkedQueue
