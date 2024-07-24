import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Filename:   Graph.java
 * Project:    p4
 * Authors:    Dayton Duffy
 * 
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT {
	private LinkedList<String> verticies;
	private LinkedList<Edge> edges;
	
	
	private class Edge {
	    //Edge works SRC --> DST in graph structure
	    private String src;
	    private String dst;
	    
	    
	    private Edge(String source, String destination) {
	        setSrc(source);
	        setDst(destination);
	    }

	    //Basic setter and getter methods
        public String getDst() {
            return dst;
        }
        public void setDst(String dst) {
            this.dst = dst;
        }
        public String getSrc() {
            return src;
        }
        public void setSrc(String src) {
            this.src = src;
        }
	}
	
	
	
	/*
	 * Default no-argument constructor
	 */ 
	public Graph() {
		verticies = new LinkedList<String>();
		edges = new LinkedList<Edge>();
	}
	
    /**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists,
     * method ends without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     * 
     * @param vertex the vertex to be added
     */
    public void addVertex(String vertex) {
        if(vertex != null) {//if not null
            if(!verticies.contains(vertex)) {//if not in graph already
                verticies.add(vertex);//add vertex
            }
        }
        //if both conditions are met add new vertex
        //otherwise simply do nothing, no errors
    }
    
    /**
     * Remove a vertex and all associated 
     * edges from the graph.
     * 
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     *  
     * @param vertex the vertex to be removed
     */
    public void removeVertex(String vertex) {
        if(vertex != null) {//v isn't null
            if(verticies.contains(vertex)) {//and v exists
                verticies.remove(vertex);//take out vertex
                
                //method won't cause error if edge doesn't exist
                for(int i=0; i < verticies.size(); ++i) {
                    removeEdge(vertex, verticies.get(i));//remove it's edges  REMOVING --> IN GRAPH 
                    removeEdge(verticies.get(i), vertex);//remove edges to it  IN GRAPH --> REMOVING
                }
             
            }
        }
    }

    
    /**
     * Add the edge from vertex1 to vertex2
     * to this graph.  (edge is directed and unweighted)
     * 
     * If either vertex does not exist,
     * VERTEX IS ADDED and then edge is created.
     * No exception is thrown.
     *
     * If the edge exists in the graph,
     * no edge is added and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge is not in the graph
     *  
     * @param vertex1 the first vertex (src)
     * @param vertex2 the second vertex (dst)
     */
    public void addEdge(String vertex1, String vertex2) {
        Edge newEdge = new Edge(vertex1, vertex2);
        if(edgesContains(vertex1, vertex2) == -1) {//ignore if edge exists
            if(vertex1 != null && vertex2 != null) {//only continue if v's aren't null
                //if either V doesn't already exist create it
                if(!verticies.contains(vertex1)) {
                    addVertex(vertex1);
                }
                if(!verticies.contains(vertex2)) {
                    addVertex(vertex2);
                }
 
                edges.add(newEdge);
            }
        }
    }
    
    /**
     * Helper method for contains on edge list
     * 
     * @return index of edge in edges list
     */ 
     private int edgesContains(String src, String dst) {
        for(int i=0; i < edges.size(); ++i) {
            if(edges.get(i).getSrc().equals(src) && edges.get(i).getDst().equals(dst)) {
                return i;
            }
        }
        
        return -1;
    }

    
    /**
     * Remove the edge from vertex1 to vertex2
     * from this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * or if an edge from vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge from vertex1 to vertex2 is in the graph
     *  
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    public void removeEdge(String vertex1, String vertex2) {
        //if the edge isn't in the graph that means both that condition is not met
        //but that also the v's not existing is covered because if they don't exist
        //the edge also wont exist
        int index = edgesContains(vertex1, vertex2);
        if(index != -1) {//if the edge exists
            edges.remove(index);//remove it
            
        }
    }
    
        
    /**
     * Returns a Set that contains all the vertices
     * 
     * @return a Set<String> which contains all the vertices in the graph
     */
    public Set<String> getAllVertices(){ 
        Set<String> set = new HashSet<String>();
        for(int i=0; i < verticies.size(); ++i) {
            set.add(verticies.get(i));
        }//add all v's to the set 
        
        return set;
    }
    
    
    /**
     * Get all the neighbor (adjacent-dependencies) of a vertex
     * 
     * For the example graph, A->[B, C], D->[A, B] 
     *     getAdjacentVerticesOf(A) should return [B, C]. 
     * 
     * In terms of packages, this list contains the immediate 
     * dependencies of A and depending on your graph structure, 
     * this could be either the predecessors or successors of A.
     * 
     * @param vertex the specified vertex
     * @return an List<String> of all the adjacent vertices for specified vertex
     */
    public List<String> getAdjacentVerticesOf(String vertex){
        LinkedList<String> adjVertices = new LinkedList<String>();
        for(int i=0; i < edges.size(); ++i) {
            if(edges.get(i).getSrc().equals(vertex)) {//EDGE is SRC --> VERTEX
                //adjVertices.add(edges.get(i).getSrc());//add the dependency
                adjVertices.add(edges.get(i).getDst());//add the dependency
            }
        }
        //in package manager this is what I want more often
        //list of SRC's not DST's
        return adjVertices;
    }
    

    /**
     * Returns the number of edges in this graph.
     * @return number of edges in the graph.
     */
    public int size() {
        return edges.size();
    }
    
    
    /**
     * Returns the number of vertices in this graph.
     * @return number of vertices in graph.
     */
    public int order() {
        return verticies.size();
    }

}
