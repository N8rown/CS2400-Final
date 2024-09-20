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
public class DirectedGraph<T> implements GraphInterface<T>
{
    private DictionaryInterface<T, VertexInterface<T>> vertices;
    private int edgeCount;
    public DirectedGraph()
    {
        vertices = new Dictionary<>();
        edgeCount = 0;
    } // end default constructor

    public boolean addVertex(T vertexLabel)
    {
        VertexInterface<T> addOutcome =
        vertices.add(vertexLabel, new Vertex<>(vertexLabel));
        return addOutcome == null; // Was addition to
        // dictionary successful?
    } // end addVertex
    public boolean addEdge(T begin, T end)
    {
        return addEdge(begin, end, 0);
    } // end addEdge
    public boolean addEdge(T begin, T end, double edgeWeight)
    {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ( (beginVertex != null) && (endVertex != null) )
            result = beginVertex.connect(endVertex, edgeWeight);
        if (result)
            edgeCount++;
        return result;
    } // end addEdge

    public boolean hasEdge(T begin, T end)
    {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ( (beginVertex != null) && (endVertex != null) )
        {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    found = true;
            } // end while
        } // end if
        return found;
    } // end hasEdge

    public boolean isEmpty()
    {
        return vertices.isEmpty();
    } // end isEmpty
    public void clear()
    {
        vertices.clear();
        edgeCount = 0;
    } // end clear
    public int getNumberOfVertices()
    {
        return vertices.getSize();
    } // end getNumberOfVertices
    public int getNumberOfEdges()
    {
        return edgeCount;
    } // end getNumberOfEdges
    protected void resetVertices()
    {
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext())
        {
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredecessor(null);
        } // end while
    } // end resetVertices

    public double getCheapestPath(T origin, T end, StackInterface<T> path) 
    {
        resetVertices();
        boolean done = false;
        VertexInterface<T> startVertex = vertices.getValue(origin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        PriorityQueueInterface<EntryPQ> pq = new PriorityQueue<>();
        pq.add(new EntryPQ(startVertex)); //pq.add(originVertex);
        while(!done && !(pq.isEmpty()))
        {
            EntryPQ frontEntry = pq.remove();
            VertexInterface<T> frontVertex = frontEntry.getVertex(); 
            if (!frontVertex.isVisited())
            {
                frontVertex.visit();
                frontVertex.setCost(frontEntry.getWeight()); 
                frontVertex.setPredecessor(frontEntry.getPrevious());
                if (frontVertex.equals(endVertex))
                {
                    done = true;
                    pq.add(frontEntry); //makes it run for 2nd and 3rd case
                }
                else
                {
                    Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
                    while(!done && neighbors.hasNext())
                    {
                        VertexInterface<T> nextNeighbor = neighbors.next();
                        double tempWeight = nextNeighbor.getCost();
                        if(!nextNeighbor.isVisited())
                        {
                            double nextCost = tempWeight + frontVertex.getCost();
                            pq.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
                        }
                    }
                }
            }
        }
        EntryPQ finalEntry = pq.remove();
        double pathCost;
        if (finalEntry != null)
        {
            pathCost = finalEntry.getWeight();
            path.push(endVertex.getLabel());
            VertexInterface<T> vertex = endVertex;
            while (vertex.hasPredecessor())
            {
                vertex = vertex.getPredecessor();
                path.push(vertex.getLabel());
            } // end while
        }
        else
            {pathCost = -1.0;}
        return pathCost;
    }

    
    private class EntryPQ implements Comparable<EntryPQ>
    {
        VertexInterface<T> currentVertex;
        double cost; // cost of the path to this vertex from origin vertex
        VertexInterface<T> previousVertex;  // previous vertex on that path
        
        public EntryPQ(VertexInterface<T> newVertex, double totalCost, VertexInterface<T> vertexFrom)
        {
            currentVertex = newVertex;
            cost = totalCost;
            previousVertex = vertexFrom;    
        } // end  constructor
        public EntryPQ(VertexInterface<T> newVertex)
        {
            currentVertex = newVertex;
            cost = 0.0;
            previousVertex = null;  
        }
        
        public double getWeight()
        {
            return cost;
        } // end getCost
        
        public VertexInterface<T> getVertex()
        {
            return currentVertex;
        } // end getVertex
        
        public VertexInterface<T> getPrevious()
        {
            return previousVertex;
        } // end getPrevious
        
        public int compareTo(EntryPQ other)
        {
            int result = (int) (cost - other.getWeight());
            return result;
        } // end compareTo
    } // end EntryPQ

    
}
