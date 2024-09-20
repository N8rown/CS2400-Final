//
// Name: Brown, Nathan 
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-f23
//
// Description:
// An implementation of a grapht ADT to map airports along with an alorgithm that finds the shortest path between said airports.
//
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;

public class Vertex<T> implements VertexInterface<T>
{
    private T label;
    private List<Edge> edgeList; // Edges to neighbors
    private boolean visited;                          // True if visited
    private VertexInterface<T> previousVertex;        // On path to this vertex
    private double cost;

    public Vertex(T vertexLabel)
    {
        label = vertexLabel;
        edgeList = new ArrayList<>();
        visited = false;
        previousVertex = null;
        cost = 0;
    } // end constructor
    /** Gets this vertex's label.
    @return  The object that labels the vertex. */
    public T getLabel()
    {
        return label;
    }
    /** Marks this vertex as visited. */
    public void visit()
    {
        visited = true;
    }

    /** Removes this vertex's visited mark. */
    public void unvisit()
    {
        visited = false;
    }
 
    /** Sees whether this vertex is marked as visited.
        @return  True if the vertex is visited. */
    public boolean isVisited()
    {
        return visited;
    }
    
    /** Connects this vertex and a given vertex with a weighted edge. 
        The two vertices cannot be the same, and must not already
        have this edge between them. In a directed graph, the edge
        points toward the given vertex.
        @param endVertex   A vertex in the graph that ends the edge. 
        @param edgeWeight  A real-valued edge weight, if any.
        @return  True if the edge is added, or false if not. */ 
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight)
    {
        boolean result = false;
            if (!this.equals(endVertex))
            {  // Vertices are distinct                   
                Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
               boolean duplicateEdge = false;
               while (!duplicateEdge && neighbors.hasNext())
               {
                    VertexInterface<T> nextNeighbor = neighbors.next();
                    if (endVertex.equals(nextNeighbor))
                        duplicateEdge = true;
                } // end while
                if (!duplicateEdge)
                {
                    edgeList.add(new Edge(endVertex, edgeWeight));
                    result = true;
                }  // end if
            } // end if
        return result;
    }
    
    /** Connects this vertex and a given vertex with an unweighted edge.
        The two vertices cannot be the same, and must not already
        have this edge between them. In a directed graph, the edge 
        points toward the given vertex.
        @param endVertex   A vertex in the graph that ends the edge.
        @return  True if the edge is added, or false if not. */
    public boolean connect(VertexInterface<T> endVertex)
    {
        return connect(endVertex, 0);
    }
     
    /** Creates an iterator of this vertex's neighbors by following 
        all edges that begin at this vertex.
        @return  An iterator of the neighboring vertices of this vertex. */
    public Iterator<VertexInterface<T>> getNeighborIterator()
    {
        return new NeighborIterator();
    }
     
    /** Creates an iterator of the weights of the edges to this
        vertex's neighbors. 
        @return  An iterator of edge weights for edges to neighbors of this 
                vertex. */ 
    public Iterator<Double> getWeightIterator()
    {
        return null;
    }
     
    /** Sees whether this vertex has at least one neighbor. 
        @return  True if the vertex has a neighbor. */
    public boolean hasNeighbor()
    {
        return !edgeList.isEmpty();
    }
     
    /** Gets an unvisited neighbor, if any, of this vertex. 
        @return  Either a vertex that is an unvisited neighbor or null 
                if no such neighbor exists. */
    public VertexInterface<T> getUnvisitedNeighbor()
    {
        VertexInterface<T> result = null;
        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while ( neighbors.hasNext() && (result == null) )
        {
              VertexInterface<T> nextNeighbor = neighbors.next();
            if (!nextNeighbor.isVisited())
                result = nextNeighbor;
        } // end while
        return result;
    }
     
    /** Records the previous vertex on a path to this vertex.
        @param predecessor  The vertex previous to this one along a path. */ 
    public void setPredecessor(VertexInterface<T> predecessor)
    {
        previousVertex = predecessor;
    }
     
    /** Gets the recorded predecessor of this vertex.
        @return  Either this vertex's predecessor or null if no predecessor
                was recorded. */
    public VertexInterface<T> getPredecessor()
    {
        return previousVertex;
    }

    /** Sees whether a predecessor was recorded for this vertex.
        @return  True if a predecessor was recorded. */
    public boolean hasPredecessor()
    {
        return previousVertex != null;
    }
  
    /** Records the cost of a path to this vertex.
        @param newCost  The cost of the path. */
    public void setCost(double newCost)
    {
        cost = newCost;
    }
  
    /** Gets the recorded cost of the path to this vertex.
        @return  The cost of the path. */
    public double getCost()
    {
        return cost;
    }

    public boolean equals(Object other)
    {
        boolean result;
        if ((other == null) || (getClass() != other.getClass()))
            result = false;
        else
        {  // The cast is safe within this else clause
             @SuppressWarnings("unchecked")
            Vertex<T> otherVertex = (Vertex<T>)other;
            result = label.equals(otherVertex.label);
        } // end if
    return result;
    } // end equals 






    protected class Edge
    {
        private VertexInterface<T> vertex; // Vertex at end of edge
        private double weight;
  
        protected Edge(VertexInterface<T> endVertex, double edgeWeight)
        {
            vertex = endVertex;
            weight = edgeWeight;
        } // end constructor

        protected Edge(VertexInterface<T> endVertex)
        {
            vertex = endVertex;
            weight = 0;
        } // end constructor

        protected VertexInterface<T> getEndVertex()
        {
            return vertex;
        } // end getEndVertex

        protected double getWeight()
        {
            return weight;
        } // end getWeight
    } // end Edge



    private class NeighborIterator implements Iterator<VertexInterface<T>>
    {
        private Iterator<Edge> edges;
      
        private NeighborIterator()
        {
            edges = edgeList.iterator();
        } // end default constructor
      
        public boolean hasNext()
       {
           return edges.hasNext();
       } // end hasNext
     
        public VertexInterface<T> next()
        {
           VertexInterface<T> nextNeighbor = null;
           if (edges.hasNext())
           {
              Edge edgeToNextNeighbor = edges.next();
              nextNeighbor = edgeToNextNeighbor.getEndVertex();
              nextNeighbor.setCost(edgeToNextNeighbor.getWeight());
           }
           else
              throw new NoSuchElementException();
           return nextNeighbor;
        } // end next
   
        public void remove()
        {
           throw new UnsupportedOperationException();
        } // end remove 
    } // end NeighborIterator 
}
