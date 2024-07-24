import static org.junit.jupiter.api.Assertions.fail;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphTest {
    protected Graph graph;
    
    @Before
    public void setUp() throws Exception {
        graph = new Graph();//create new structure
    }

    
    
    @After
    public void tearDown() throws Exception {
        graph = null;//clear structure
    }
    
    
    @Test
    public void test00_add_null_v() {
        try {
            graph.addVertex(null);
            if(graph.size() != 0 || graph.order() != 0) {
                fail("No change should happen when null is added");
            }
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test01_add_few_vs() {
        try {
            if(graph.size() != 0 || graph.order() != 0) {
                fail("Nothing should be in structure");
            }
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            if(graph.size() != 0 || graph.order() != 7) {
                fail("Size should be 0 and Order should be 7, size: " + graph.size() + ", order: " + graph.order());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test02_add_duplicate_v() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            
            graph.addVertex("B");
            if(graph.size() != 0 || graph.order() != 4) {
                fail("Size should be 0 and Order should be 4, size: " + graph.size() + ", order: " + graph.order());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    
    @Test
    public void test03_remove_null_v() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            
            graph.removeVertex(null);
            
            if(graph.size() != 0 || graph.order() != 7) {
                fail("Size should be 0 and Order should be 7, size: " + graph.size() + ", order: " + graph.order());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test04_remove_few_vs() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            
            graph.removeVertex("B");
            graph.removeVertex("D");
            graph.removeVertex("E");
            
            if(graph.size() != 0 || graph.order() != 4) {
                fail("Size should be 0 and Order should be 4, size: " + graph.size() + ", order: " + graph.order());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test05_remove_nonexistent_v() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            
            graph.removeVertex("Q");
            
            if(graph.size() != 0 || graph.order() != 7) {
                fail("Size should be 0 and Order should be 7, size: " + graph.size() + ", order: " + graph.order());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    
    @Test
    public void test06_add_edge_null_v() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            
            graph.addEdge(null, "A");
            graph.addEdge("A", null);
            graph.addEdge(null, null);
            
            //no edges formed
            if(graph.size() != 0 || graph.order() != 7) {
                fail("Size should be 0 and Order should be 7, size: " + graph.size() + ", order: " + graph.order());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test07_add_edge_vs_dont_exist() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            
            graph.addEdge("W", "A");
            graph.addEdge("A", "X");
            graph.addEdge("Y", "Z");
            
            //creates 4 new V's(W, X, Y, Z) and 3 E's
            if(graph.size() != 3 || graph.order() != 11) {
                fail("Size should be 3 and Order should be 11, size: " + graph.size() + ", order: " + graph.order());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test08_add_edge_duplicate() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            
            graph.addEdge("G", "A");
            graph.addEdge("D", "C");
            graph.addEdge("G", "A");
            
            if(graph.size() != 2 || graph.order() != 7) {
                fail("Size should be 2 and Order should be 7, size: " + graph.size() + ", order: " + graph.order());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test09_add_edge_few() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            
            graph.addEdge("G", "A");
            graph.addEdge("A", "B");
            graph.addEdge("B", "C");
            graph.addEdge("B", "C");
            graph.addEdge("C", "X");
            graph.addEdge("X", "Y");
            graph.addEdge("Y", "Z");
            graph.addEdge("E", "Z");
            graph.addEdge("G", "A");
            
            if(graph.size() != 7 || graph.order() != 10) {
                fail("Size should be 7 and Order should be 10, size: " + graph.size() + ", order: " + graph.order());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }

    @Test
    public void test10_remove_edge_few() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            
            graph.addEdge("G", "A");
            graph.addEdge("A", "B");
            graph.addEdge("B", "C");
            graph.addEdge("B", "C");
            graph.addEdge("C", "X");
            graph.addEdge("X", "Y");
            graph.addEdge("Y", "Z");
            graph.addEdge("E", "Z");
            graph.addEdge("G", "A");
            
            
            graph.removeEdge("X", "Y");
            graph.removeEdge("G", "A");
            graph.removeEdge("B", "C");
            
            
            if(graph.size() != 4 || graph.order() != 10) {
                fail("Size should be 4 and Order should be 10, size: " + graph.size() + ", order: " + graph.order());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test11_remove_edge_not_exist() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            
            graph.addEdge("G", "A");
            graph.addEdge("A", "B");
            graph.addEdge("B", "C");
            graph.addEdge("B", "C");
            graph.addEdge("C", "X");
            graph.addEdge("X", "Y");
            graph.addEdge("Y", "Z");
            graph.addEdge("E", "Z");
            graph.addEdge("G", "A");
            
            //V's exist E doesn't
            graph.removeEdge("A", "Z");
            
            if(graph.size() != 7 || graph.order() != 10) {
                fail("Size should be 7 and Order should be 10, size: " + graph.size() + ", order: " + graph.order());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test12_remove_edge_vs_not_exist() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            
            graph.addEdge("G", "A");
            graph.addEdge("A", "B");
            graph.addEdge("B", "C");
            graph.addEdge("B", "C");
            graph.addEdge("C", "X");
            graph.addEdge("X", "Y");
            graph.addEdge("Y", "Z");
            graph.addEdge("E", "Z");
            graph.addEdge("G", "A");
            
            //V's don't exist
            graph.removeEdge("Q", "R");
            //V's null
            graph.removeEdge(null, "A");
            graph.removeEdge("A", null);
            graph.removeEdge(null, null);
            
            //should't change
            if(graph.size() != 7 || graph.order() != 10) {
                fail("Size should be 7 and Order should be 10, size: " + graph.size() + ", order: " + graph.order());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test13_getAllVs() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            
            graph.addEdge("G", "A");
            graph.addEdge("A", "B");
            graph.addEdge("B", "C");
            graph.addEdge("B", "C");
            graph.addEdge("C", "X");
            graph.addEdge("X", "Y");
            graph.addEdge("Y", "Z");
            graph.addEdge("E", "Z");
            graph.addEdge("G", "A");
                       
            Set<String> set = graph.getAllVertices();
            
            if(set.size() != 10) {
                fail("Set should contain all 10 vs, contains: " + set.size());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test14_getAllVs_empty() {
        try {          
            Set<String> set = graph.getAllVertices();
            
            if(set.size() != 0) {
                fail("Set should contain all no vs, contains: " + set.size());
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test15_getAdjVs() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            graph.addVertex("Y");
            graph.addVertex("X");
            graph.addVertex("Z");
            
            graph.addEdge("A", "B");
            graph.addEdge("A", "C");
            graph.addEdge("A", "D");
            graph.addEdge("B", "E");
            graph.addEdge("D", "F");
            graph.addEdge("D", "G");
            graph.addEdge("F", "X");
            graph.addEdge("F", "Y");
            graph.addEdge("F", "Z");
                   
            if(graph.size() != 9 || graph.order() != 10) {
                fail("Size should be 7 and Order should be 10, size: " + graph.size() + ", order: " + graph.order());
            }
            
            List<String> adjA = graph.getAdjacentVerticesOf("A");
            List<String> adjD = graph.getAdjacentVerticesOf("D");
            List<String> adjF = graph.getAdjacentVerticesOf("F");
            List<String> adjC = graph.getAdjacentVerticesOf("C");

            if(!adjA.contains("B") || !adjA.contains("C") || !adjA.contains("D") || adjA.size() != 3) {
                fail("A is adjacent to vs B, C, and D");
            }
            
            if(!adjD.contains("F") || !adjD.contains("G") || adjD.size() != 2) {
                fail("D is adjacent to vs F, and G");
            }
            
            if(!adjF.contains("X") || !adjF.contains("Y") || !adjF.contains("Z") || adjF.size() != 3) {
                fail("A is adjacent to vs B, C, and D");
            }
            
            if(adjC.size() != 0) {
                fail("C has no adjacencies");
            }
           
            
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test16_getAdjVs_v_doesnt_exist() {
        try {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");
            graph.addVertex("E");
            graph.addVertex("F");
            graph.addVertex("G");
            graph.addVertex("Y");
            graph.addVertex("X");
            graph.addVertex("Z");
            
            graph.addEdge("A", "B");
            graph.addEdge("A", "C");
            graph.addEdge("A", "D");
            graph.addEdge("B", "E");
            graph.addEdge("D", "F");
            graph.addEdge("D", "G");
            graph.addEdge("F", "X");
            graph.addEdge("F", "Y");
            graph.addEdge("F", "Z");
                       
            //v doesn't exist nothing happens
            List<String> list = graph.getAdjacentVerticesOf("Q");
            if(list.size() != 0) {
                fail("list should have no values");
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
}
