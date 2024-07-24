import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


public class CampEnrollmentApp {
    
    public static void main(String[] args) {
        
        try {
            List <String> fileLines = Files.readAllLines(Paths.get("sim.txt"));
            CampManager chad = new CampManager();
 
            
            for(int i=0; i < fileLines.size(); ++i) {
                String line = new String();
                line = fileLines.get(i);
                String[] splitted = line.split(" ");
                
                if(splitted[0].equals("E")) {
                    try {
                        enroll(splitted, chad);
                    }catch (IllegalArgumentException e) {
                        System.out.println(e);
                    }
                }
                if(splitted[0].equals("R")) {
                    try {
                        unenroll(splitted, chad);
                    }catch (NoSuchElementException e){
                        System.out.println(e);
                    }
                }
                if(splitted[0].equals("T")) {
                    traverse(splitted, chad);
                }
                if(splitted[0].equals("S")) {
                    statistics(splitted, chad);
                }
            }
            
        
        
        
        } catch (IOException e) {
            System.out.println(e);
        }

    }
    
    //Creates a new camper from the line provided
    //enrolls that camper into the BST
    public static void enroll(String[] line, CampManager chad) throws IllegalArgumentException{
        String firstName = new String(line[2]);
        String lastName = new String(line[1]);
        Integer age = new Integer(Integer.parseInt(line[3]));
        
        Camper person = new Camper(firstName, lastName, age);
        chad.enrollCamper(person);
        System.out.println("Enrollment of " + firstName + " " + lastName + " Successful!");
    }
    
    //Creates a new camper with the name provided, age isn't a factor in the tree removal
    //unenrolls the camper from the BST 
    public static void unenroll(String[] line, CampManager chad) throws NoSuchElementException{
        String firstName = new String(line[2]);
        String lastName = new String(line[1]);
        
        Camper person = new Camper(firstName, lastName, 10);//AGE DOES NOT MATTER FOR DELETION
        chad.unenrollCamper(person);
        System.out.println("Unenrollment of " + firstName + " " + lastName + " Successful!");
    }
    
    //Creates an iterator and prints in the order given 
    //use hasNext and next to print
    public static void traverse(String[] line, CampManager chad) {
        Iterator<Camper> spin = chad.traverse(line[1]);
        System.out.println("--- " + line[1] + " Traversal ---");
        while(spin.hasNext()) {
            Camper hero = spin.next();
            System.out.print(hero.getName());
            System.out.print(" Age: " + hero.getAge() + " ");
            System.out.println("Cabin: " + hero.getCabin());
        }
        System.out.println("---------------------------");
    }
    
    //WE NEED TO JUST FINISH UP THE PRINTING OF THESE THINGS
    public static void statistics(String[] line, CampManager chad) {
        System.out.println("--- Camp Statistics ---");
        chad.printStatistics();
        System.out.println("------------------------");
    }
}
