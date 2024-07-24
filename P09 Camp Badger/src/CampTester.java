import java.util.Iterator;

public class CampTester {
    public static void main(String[] args){
        boolean testInsert = testInsert();
        if(testInsert) {
            System.out.println("Insert - Hero");
        }else {
            System.out.println("Insert - Zero");
        }
        
        boolean testDelete = testDelete();
        if(testDelete) {
            System.out.println("Delete - Hero");
        }else{
            System.out.println("Delete - Zero");
        }
        
        boolean testTraverse = testTraverse();
        if(testTraverse) {
            System.out.println("Traverse - Hero");
        }else {
            System.out.println("Traverse - Zero");
        }
    }
    
    public static boolean testInsert() {
        Camper billy = new Camper("Billy", "Costa", 8);
        Camper roger = new Camper("Roger", "Parslow", 9);
        Camper cade = new Camper("Cade", "Idiot", 12);
        Camper dayton = new Camper("Dayton", "Duffy", 12);
        Camper az = new Camper("Azboi", "Riley", 13);
        
        CamperBST roster = new CamperBST();
        roster.insert(dayton);
        
        Camper test1 = roster.root.getData();
        if(test1.getAge() != 12) {
            return false;
        }

        
        roster.insert(billy);
        
        Camper test2 = roster.root.getLeftNode().getData();
        if(test2.getAge() != 8) {
            return false;
        }

        
        roster.insert(roger);
        roster.insert(cade);
        roster.insert(az);
        if(roster.size() != 5) {
            return false;
        }
        Camper test3 = roster.root.getRightNode().getRightNode().getData();
        if(test3.getAge() != 13) {
            return false;
        }
        Camper test4 = roster.root.getRightNode().getLeftNode().getData();
        if(test4.getAge() != 12) {
            return false;
        }
                
        
        return true;
    }
    
    public static boolean testDelete() {
        Camper billy = new Camper("Billy", "Costa", 8);
        Camper roger = new Camper("Roger", "Parslow", 9);
        Camper cade = new Camper("Cade", "Idiot", 12);
        Camper dayton = new Camper("Dayton", "Duffy", 12);
        Camper az = new Camper("Azboi", "Riley", 13);
        
        CamperBST single = new CamperBST();
        single.insert(billy);
        single.delete(billy);
        if(single.root != null) {
            return false;
        }
        System.out.println(single.size() + " single");
        
        
        CamperBST roster = new CamperBST();
        roster.insert(dayton);
        roster.insert(billy);
        roster.insert(roger);
        roster.insert(cade);
        roster.insert(az);
        
        roster.delete(cade);
        if(roster.root.getRightNode().getLeftNode() != null) {
            return false;
        }
        roster.delete(roger);
        if(roster.root.getRightNode().getLeftNode() != null) {
            if(roster.root.getRightNode().getLeftNode() != null) {
                return false;
            }
        }
        Camper hold = roster.root.getRightNode().getData(); 
        if(hold.getAge() != 13) {
            return false;
        }
        System.out.println(roster.size() + " roster");
        
        CamperBST Hogwarts = new CamperBST();
        Hogwarts.insert(dayton);
        Hogwarts.insert(billy);
        Hogwarts.insert(roger);
        Hogwarts.insert(cade);
        Hogwarts.insert(az);
        
        Hogwarts.delete(roger);
        if(!Hogwarts.root.getData().getFirstName().equals("Dayton")) {
            return false;
        }
        if(!Hogwarts.root.getLeftNode().getData().getFirstName().equals("Billy")) {
            return false;
        }
        if(!Hogwarts.root.getRightNode().getData().getFirstName().equals("Azboi")) {
            return false;
        }
        if(Hogwarts.root.getRightNode().getRightNode() != null) {
            return false;
        }
        if(!Hogwarts.root.getRightNode().getLeftNode().getData().getFirstName().equals("Cade")) {
            return false;
        }
        System.out.println(Hogwarts.size() + " Hogwarts");
        
        CamperBST bitchCity = new CamperBST();
        bitchCity.insert(dayton);
        bitchCity.insert(billy);
        bitchCity.insert(roger);
        bitchCity.insert(cade);
        bitchCity.insert(az);
        
        bitchCity.delete(dayton);
        
        Iterator<Camper> spinBoy = bitchCity.traverse("PREORDER");
        System.out.println(bitchCity.size());
        while(spinBoy.hasNext()) {
            System.out.println(spinBoy.next().getFirstName());
        }
        
        //REMOVING THE ROOT MAKES A BITCH GO CRAZY
        
        Camper A = new Camper("A", "A", 8);
        Camper B = new Camper("B", "B", 9);
        Camper C = new Camper("C", "C", 12);
        Camper D = new Camper("D", "D", 12);
        Camper E = new Camper("E", "E", 13);
        Camper F = new Camper("F", "F", 8);
        Camper G = new Camper("G", "G", 9);
        CamperBST gradeScope = new CamperBST();
        gradeScope.insert(D);
        gradeScope.insert(B);
        gradeScope.insert(A);
        gradeScope.insert(C);
        gradeScope.insert(F);
        gradeScope.insert(E);
        gradeScope.insert(G);
        
        gradeScope.delete(D);
        Iterator<Camper> spin = gradeScope.traverse("PREORDER");
        
        while(spin.hasNext()) {
            System.out.println(spin.next().getFirstName());
        }
        
        return true;
    }
    
    public static boolean testTraverse() {
        Camper billy = new Camper("Billy", "Costa", 8);
        Camper roger = new Camper("Roger", "Parslow", 9);
        Camper cade = new Camper("Cade", "Idiot", 12);
        Camper dayton = new Camper("Dayton", "Duffy", 12);
        Camper az = new Camper("Azboi", "Riley", 13);
        
        CamperBST roster = new CamperBST();
        roster.insert(dayton);
        roster.insert(billy);
        roster.insert(roger);
        roster.insert(cade);
        roster.insert(az);
        
        String line1 = "PREORDER";
        String line2 = "POSTORDER";
        String line3 = "INORDER";
        
        Iterator<Camper> spinCycle = roster.traverse(line1);
        System.out.println();
        while(spinCycle.hasNext()) {
            System.out.println(spinCycle.next().getName());
        }
        System.out.println();
        return true;
    }
}
