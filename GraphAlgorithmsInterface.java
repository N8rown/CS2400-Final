//
// Name: Brown, Nathan 
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-f23
//
// Description:
// An implementation of a grapht ADT to map airports along with an alorgithm that finds the shortest path between said airports.
//
public interface GraphAlgorithmsInterface<T>
{ 

/** Finds the least-cost path between two given vertices in this graph.
@param begin An object that labels the path's origin vertex.
@param end An object that labels the path's destination vertex.
@param path A stack of labels that is empty initially;
at the completion of the method, this stack contains
the labels of the vertices along the cheapest path;
the label of the origin vertex is at the top, and
the label of the destination vertex is at the bottom
@return The cost of the cheapest path. */
public double getCheapestPath(T begin, T end, StackInterface<T> path);
} // end GraphAlgorithmsInterface
