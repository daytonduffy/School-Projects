import java.util.Iterator;

public class CampManager {
    private CamperBST campers;
    private final static String [] CABIN_NAMES = new String[]{
    "Otter Overpass", "Wolverine Woodland", "Badger Bunkhouse"};
    
    //constructor initializes campers field
    public CampManager() {
        campers = new CamperBST();
    }
    
    //Enrolls a camper by determining their cabin and adding 
    //them to the tree.
    public void enrollCamper(Camper newCamper) {
        int age = newCamper.getAge();
        if(age == 9 || age == 8) {
            newCamper.assignCabin(CABIN_NAMES[0]);
        }else if(age >= 10 && age <= 12) {
            newCamper.assignCabin(CABIN_NAMES[1]);
        }else if(age == 13 || age == 14) {
            newCamper.assignCabin(CABIN_NAMES[2]);
        }
        campers.insert(newCamper);
    }
    
    //Unenrolls a camper by removing them from the tree.
    public void unenrollCamper(Camper delCamper) throws java.util.NoSuchElementException{
        campers.delete(delCamper);
    }
    
    public void printStatistics() {
        System.out.println("Number of Campers: " + campers.size());
    }
    
    //returns an Iterator in the given order
    public Iterator<Camper> traverse(String order){
        Iterator<Camper> spin = campers.traverse(order);
        return spin;
    }
}
