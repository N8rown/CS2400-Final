//
// Name: Brown, Nathan 
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-f23
//
// Description:
// An implementation of a grapht ADT to map airports along with an alorgithm that finds the shortest path between said airports.
//
public interface StackInterface<T>
{
    /**Adds a new entry to the stack
     * @param newEntry - object to pushed to the top of the stack */
    public void push(T newEntry);

    /**Removes top entry from stack
     * @return  Returns the top entry that was removed from stack*/
    public T pop();

    /**Previews the top entry in the stack without removing it
     * @return  Returns the top entry of the stack*/
    public T peek();

    /**Checks if the stack is empty
     * @return  Returns true if teh stack is empty, false if not*/
    public boolean isEmpty();

    /**Removes all entries from stack*/
    public void clear();
    public void printStack();
}
