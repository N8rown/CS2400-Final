//
// Name: Brown, Nathan 
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-f23
//
// Description:
// An implementation of a grapht ADT to map airports along with an alorgithm that finds the shortest path between said airports.
//
import java.util.EmptyStackException;

public class Stack<T> implements StackInterface<T> {
    private class Node
    {
        private T data;
        private Node next;
        public Node(T entry)
        {
            data = entry;
            next = null;
        }
        public Node(T entry, Node next)
        {
            data = entry;
            this.next = next;
        }
    }
    private Node topNode;
    private Node currentNode;
    public Stack()
    {
        topNode = null;
    }
    public void push(T newEntry)
    {
        Node newNode = new Node(newEntry, topNode);
        topNode = newNode;
    }
    public T pop()
    {
        T top = peek();
        topNode = topNode.next;
        return top;
    }
    public T peek()
    {
        if(isEmpty())
            throw new EmptyStackException();
        else
        {
            return topNode.data;
        }
    }
    public boolean isEmpty()
    {
        return topNode == null;
    }
    public void clear()
    {
        topNode = null;
    }
    public void printStack()
    {
        System.out.print("Route: ");
        while(isEmpty() == false)
        {
            System.out.print(pop() + " ");
        }
        System.out.println();
    }
}
