import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Filename:   PackageManager.java
 * Project:    p4
 * Authors:    Dayton Duffy
 * 
 * PackageManager is used to process json package dependency files
 * and provide function that makes that information available to other users.
 * 
 * Each package that depends upon other packages has its own
 * entry in the json file.  
 * 
 * Package dependencies are important when building software, 
 * as you must install packages in an order such that each package 
 * is installed after all of the packages that it depends on 
 * have been installed.
 * 
 * For example: package A depends upon package B,
 * then package B must be installed before package A.
 * 
 * This program will read package information and 
 * provide information about the packages that must be 
 * installed before any given package can be installed.
 * all of the packages in
 * 
 * You may add a main method, but we will test all methods with
 * our own Test classes.
 */

public class PackageManager {
    
    private Graph graph;
    
    /*
     * Package Manager default no-argument constructor.
     */
    public PackageManager() {
        graph = new Graph();
    }
    
    /**
     * Takes in a file path for a json file and builds the
     * package dependency graph from it. 
     * 
     * @param jsonFilepath the name of json data file with package dependency information
     * @throws FileNotFoundException if file path is incorrect
     * @throws IOException if the give file cannot be read
     * @throws ParseException if the given json cannot be parsed 
     */
    public void constructGraph(String jsonFilepath) throws FileNotFoundException, IOException, ParseException {
        //parsing file "JSONExample.json" 
        Object obj = new JSONParser().parse(new FileReader(jsonFilepath)); 
        
        //typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj;        
        
        Object packageObject = jo.get("packages");
        JSONArray packJArray = (JSONArray) packageObject;
        
        //Package[] packageArray = new Package[packJArray.size()];
        for(int i=0; i < packJArray.size(); ++i) {
            //parse file for package name add it as vertex
            JSONObject jsonPackage = (JSONObject) packJArray.get(i);
            String packageName = (String) jsonPackage.get("name");
            
            graph.addVertex(packageName);

            //parse file for package dependencies
            //add as edges
            JSONArray dependArray = (JSONArray) jsonPackage.get("dependencies");
            //String[] packageDependencies = new String[dependArray.size()];
            for(int j=0; j < dependArray.size(); ++j) {
                //packageDependencies[j] = (String) dependArray.get(j);
                String dependency = (String) dependArray.get(j);
                //graph.addEdge(dependency, packageName);
                graph.addEdge(packageName, dependency);
            }
            //packageArray[i] = new Package(packageName, packageDependencies);
        }
 
    }
    
    /**
     * Helper method to get all packages in the graph.
     * 
     * @return Set<String> of all the packages
     */
    public Set<String> getAllPackages() {
        return graph.getAllVertices();
    }
    
    /**
     * Given a package name, returns a list of packages in a
     * valid installation order.  
     * 
     * Valid installation order means that each package is listed 
     * before any packages that depend upon that package.
     * 
     * @return List<String>, order in which the packages have to be installed
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the installation order for a particular package. Tip: Cycles in some other
     * part of the graph that do not affect the installation order for the 
     * specified package, should not throw this exception.
     * 
     * @throws PackageNotFoundException if the package passed does not exist in the 
     * dependency graph.
     */
    public List<String> getInstallationOrder(String pkg) throws CycleException, PackageNotFoundException {        
        Set<String> vs = getAllPackages();
        if(!vs.contains(pkg) || pkg == null) {
            throw new PackageNotFoundException();
        }
       

        //fill array list with package names
        ArrayList<String> pkgs = new ArrayList<String>();
        Iterator<String> it = vs.iterator();
        while(it.hasNext()) {
            pkgs.add(it.next());
        }
        
        //check for relevant cycles
        boolean[] v = new boolean[pkgs.size()];
        boolean[] s = new boolean[pkgs.size()];
        if(cycle(pkgs.indexOf(pkg), pkgs, v, s)) {
            throw new CycleException();
        }        
        
        //Fill arrays for visited and tags
        boolean[] visit = new boolean[pkgs.size()];
        
        LinkedList<String> order = new LinkedList<String>();
        //Algorithm for setting up order of dependence
        String c;
        Stack<String> stack = new Stack<String>();//create stack
        int index = pkgs.indexOf(pkg);//get value we're looking for
        visit[index] = true;//mark as visited
        stack.push(pkg);//push first v to stack
        
        while(!stack.isEmpty()) {
            c = stack.peek();
            String unvisited = unvisited(visit, pkgs, c);
            if(unvisited == null) {
                c = stack.pop();
                order.addLast(c);
            }else {
                index = pkgs.indexOf(unvisited);
                visit[index] = true;
                stack.push(unvisited);
            }
        }
        
        return order;
    }
    
    
    /**
     * Given two packages - one to be installed and the other installed, 
     * return a List of the packages that need to be newly installed. 
     * 
     * For example, refer to shared_dependecies.json - toInstall("A","B") 
     * If package A needs to be installed and packageB is already installed, 
     * return the list ["A", "C"] since D will have been installed when 
     * B was previously installed.
     * 
     * @return List<String>, packages that need to be newly installed.
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the dependencies of the given packages. If there is a cycle in some other
     * part of the graph that doesn't affect the parsing of these dependencies, 
     * cycle exception should not be thrown.
     * 
     * @throws PackageNotFoundException if any of the packages passed 
     * do not exist in the dependency graph.
     */
    public List<String> toInstall(String newPkg, String installedPkg) throws CycleException, PackageNotFoundException {
        Set<String> vs = getAllPackages();
        if(!vs.contains(newPkg) || newPkg == null || !vs.contains(installedPkg) || installedPkg == null) {
            throw new PackageNotFoundException();
        }
        //covers cycle exceptions and gets regular list for installation
        List<String> total = getInstallationOrder(newPkg);
        List<String> removal = getInstallationOrder(installedPkg);
        
        
        if(total.contains(installedPkg)) {
            //Iterate through list starting after installed package, 
            for(int i = 0; i < removal.size(); ++i) {
                total.remove(removal.get(i));
            }
            //new list formed with only path from installed to new
        }else {//must be separate, just regular installation order then
            return total;
        }
       
        return total;
    }
    
    /**
     * Return a valid global installation order of all the packages in the 
     * dependency graph.
     * 
     * assumes: no package has been installed and you are required to install 
     * all the packages
     * 
     * returns a valid installation order that will not violate any dependencies
     * 
     * @return List<String>, order in which all the packages have to be installed
     * @throws CycleException if you encounter a cycle in the graph
     * @throws PackageNotFoundException 
     */
    public List<String> getInstallationOrderForAllPackages() throws CycleException, PackageNotFoundException {
        Set<String> vs = getAllPackages();

        //fill array list with package names
        ArrayList<String> pkgs = new ArrayList<String>();
        Iterator<String> it = vs.iterator();
        while(it.hasNext()) {
            pkgs.add(it.next());
        }
        
        if(fullCycleCheck(pkgs)) {
            throw new CycleException();
        }
        
        //Fill arrays for visited and tags
        boolean[] visit = new boolean[pkgs.size()];
        boolean[] check = new boolean[pkgs.size()];
        
        LinkedList<String> order = new LinkedList<String>();
        //Algorithm for setting up order of dependence
        String c;
        Stack<String> stack = new Stack<String>();//create stack
        int index = -1;
        //find all pkgs with no dependencies
        for(int i=0; i < pkgs.size(); ++i) {
            List<String> preds = graph.getAdjacentVerticesOf(pkgs.get(i));
            
            for(int j=0; j < preds.size(); ++j) {
                index = pkgs.indexOf(preds.get(j));
                check[index] = true;
            }   
        }
        //push those pkgs to the stack to begin
        for(int i=0; i < pkgs.size(); ++i) {
            if(!check[i]) {
                stack.push(pkgs.get(i));
                visit[i] = true;
            }
        }
        
        //otherwise go to loop algorithm
        while(!stack.isEmpty()) {
            c = stack.peek();
            String unvisited = unvisited(visit, pkgs, c);
            if(unvisited == null) {
                c = stack.pop();
                order.add(c);
            }else {
                index = pkgs.indexOf(unvisited);
                visit[index] = true;
                stack.push(unvisited);
            }
        }
        
        return order;
    }
    
    /**
     * Find and return the name of the package with the maximum number of dependencies.
     * 
     * Tip: it's not just the number of dependencies given in the json file.  
     * The number of dependencies includes the dependencies of its dependencies.  
     * But, if a package is listed in multiple places, it is only counted once.
     * 
     * Example: if A depends on B and C, and B depends on C, and C depends on D.  
     * Then,  A has 3 dependencies - B,C and D.
     * 
     * @return String, name of the package with most dependencies.
     * @throws CycleException if you encounter a cycle in the graph
     * @throws PackageNotFoundException 
     */
    public String getPackageWithMaxDependencies() throws CycleException, PackageNotFoundException {
        //do all the same set up and cycle checks as before
        Set<String> vs = getAllPackages();
        ArrayList<String> pkgs = new ArrayList<String>();
        Iterator<String> it = vs.iterator();
        while(it.hasNext()) {
            pkgs.add(it.next());
        }
        if(fullCycleCheck(pkgs)) {
            throw new CycleException();
        }
        
        //look through all pkgs if one has more dependencies than max
        //change max and set maxPkg
        int max = -1;
        String maxPkg = null;
        Set<String> packages = getAllPackages();
        Iterator<String> pkgIt = packages.iterator();
        while(pkgIt.hasNext()) {
            String pkg = pkgIt.next();
            List<String> list = getInstallationOrder(pkg);
            if(list.size() > max) {
                max = list.size();
                maxPkg = pkg;
            }
        }
  
        return maxPkg;
    }

    public static void main (String [] args) {
        System.out.println("PackageManager.main()");
    }
    
    /**
     * Helper method to find unvisited package.
     * 
     * @return unvisited package
     */
    private String unvisited(boolean[] visit, ArrayList<String> pkgs, String c) {
        List<String> adjPkgs = graph.getAdjacentVerticesOf(c);
        for(int i=0; i < adjPkgs.size(); ++i) {
            int index = pkgs.indexOf(adjPkgs.get(i));
            if(!visit[index]) {
                return pkgs.get(index);
            }   
        }        
        return null;
    }
    
    /**
     * Helper method to find cycle in full structure.
     * 
     * @return true if cycle found
     */
    private boolean fullCycleCheck(ArrayList<String> pkgs) {
        //Cycle detection
        boolean[] v = new boolean[pkgs.size()];
        boolean[] s = new boolean[pkgs.size()];
        for(int i=0; i < pkgs.size(); ++i) {
            if(cycle(i, pkgs, v, s)) {
                return true;
            }
        }//no cycle found at any v in the graph
        return false;
    }
    
    /**
     * Helper method to find cycle including given package.
     * 
     * @return true if cycle is found
     */
    private boolean cycle(int index, ArrayList<String> pkgs, boolean[] visited, boolean[] list) { 

        //check if already in list 
        //base case checks
        if(list[index]) { 
            return true; 
        }
        if(visited[index]) { 
            return false; 
        }
        
        //mark package as visited 
        visited[index] = true; 
        //add to list
        list[index] = true;
        
        //get adj vertices of package at index index
        List<String> adj = graph.getAdjacentVerticesOf(pkgs.get(index)); 
        
        //recursive loop
        for(int i=0; i < adj.size(); ++i) { 
            if(cycle(pkgs.indexOf(adj.get(i)), pkgs, visited, list)) { 
                return true; 
            }
        }
        //remove from list after use
        list[index] = false; 
        
        //no cycle was found
        return false; 
    }
}
