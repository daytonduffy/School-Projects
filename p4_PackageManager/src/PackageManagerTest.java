import static org.junit.jupiter.api.Assertions.fail;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PackageManagerTest {
    protected PackageManager manager;
    
    @Before
    public void setUp() throws Exception {
        manager = new PackageManager();//create new structure
    }

    
    
    @After
    public void tearDown() throws Exception {
        manager = null;//clear structure
    }
    
    
    @Test
    public void test00_getInstallationOrder_correct() {
        try {
            manager.constructGraph("valid.json");
            
            List<String> B = manager.getInstallationOrder("B");
            
            if(B.size() != 3) {
                fail("Should have three packages, size: " + B.size());
            }else if(!(B.contains("C") && B.contains("D") && B.contains("B"))) {
                fail("The two should be B, C, and D");
            }
            
            //System.out.println("2");

            
            List<String> E = manager.getInstallationOrder("E");
            if(E.size() != 4) {
                fail("Should have four packages, size: " + E.size());
            }else if(!(E.contains("C") && E.contains("D") && E.contains("B") && E.contains("E"))) {
                fail("The dependencies should be C, D, E, and B");
            }
            
            //System.out.println("3");

            
            List<String> C = manager.getInstallationOrder("C");
            if(C.size() != 1) {
                fail("Should have one package, size: " + C.size());
            }else if(!(C.contains("C"))) {
                fail("The dependencies should be just C itself");
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test01_getInstallationOrder_irreleventCycle() {
        try {
            manager.constructGraph("t0.json");
            
            List<String> J = manager.getInstallationOrder("J");
            
            if(J.size() != 4) {
                fail("Should have four packages, size: " + J.size());
            }else if(!(J.contains("I") && J.contains("G") && J.contains("K") && J.contains("J"))) {
                fail("The four should be I, J, K, and G");
            }
            //if it makes it through no cycle was found
           
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }

    @Test
    public void test02_getInstallationOrder_releventCycle() {
        try {
            manager.constructGraph("t0.json");
            
            manager.getInstallationOrder("B");
            
            fail("Cycle should be detected, exception should be thrown");
        }catch(CycleException e) {
            //expected result
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test03_getInstallationOrder_nullPkg() {
        try {
            manager.constructGraph("t0.json");
            
            manager.getInstallationOrder(null);
            
            fail("Exception should be thrown");
        }catch(PackageNotFoundException e) {
            //expected result
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test04_getInstallationOrder_badPkg() {
        try {
            manager.constructGraph("t0.json");
            
            manager.getInstallationOrder("r");
            
            fail("Exception should be thrown");
        }catch(PackageNotFoundException e) {
            //expected result
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test05_toInstall_correct() {
        try {
            manager.constructGraph("valid.json");
            
            List<String> AD = manager.toInstall("A", "D");
            
            if(AD.size() != 3) {
                fail("Should have three packages, size: " + AD.size());
            }else if(!(AD.contains("A") && AD.contains("B") && AD.contains("C"))) {
                fail("The four should be A, B, and C");
            }
            
            //System.out.println("2");

            
            List<String> EB = manager.toInstall("E", "B");
            if(EB.size() != 1) {
                fail("Should have one package, size: " + EB.size());
            }else if(!(EB.contains("E"))) {
                fail("The dependencies should be E");
            }
            
            //System.out.println("3");

            
            List<String> CD = manager.toInstall("C", "D");
            if(CD.size() != 1) {
                fail("Should have one package, size: " + CD.size());
            }else if(!(CD.contains("C"))) {
                fail("The dependencies should be just C itself");
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test06_toInstall_irreleventCycle() {
        try {
            manager.constructGraph("t0.json");
            
            List<String> JG = manager.toInstall("J", "G");
            
            if(JG.size() != 3) {
                fail("Should have three packages, size: " + JG.size());
            }else if(!(JG.contains("I") && JG.contains("K") && JG.contains("J"))) {
                fail("The four should be I, J, K, and G");
            }
            //if it makes it through no cycle was found
           
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test07_toInstall_releventCycle() {
        try {
            manager.constructGraph("t0.json");
            
            manager.toInstall("B", "E");
            
            fail("Cycle exception should be thrown");
            //if it makes it through no cycle was found
           
        }catch(CycleException e) {
            //expected result
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test08_toInstall_nullPkg() {
        try {
            manager.constructGraph("t0.json");
            
            manager.toInstall(null, null);
            
            fail("Exception should be thrown");
            //if it makes it through no cycle was found
           
        }catch(PackageNotFoundException e) {
            //expected result
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test09_toInstall_badPkg() {
        try {
            manager.constructGraph("t0.json");
            
            manager.toInstall("X", "Y");
            
            fail("Exception should be thrown");
            //if it makes it through no cycle was found
           
        }catch(PackageNotFoundException e) {
            //expected result
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test10_getInstallationOrderForAllPackages_correct() {
        try {
            manager.constructGraph("valid.json");
            
            List<String> total = manager.getInstallationOrderForAllPackages();
            
            
            if(total.size() != 5) {
                fail("Should have five packages, size: " + total.size());
            }else if(!(total.contains("C") && total.contains("D") && total.contains("B") && total.contains("A") && total.contains("E"))) {
                fail("The five should be A, B, C, D, and E");
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }

    @Test
    public void test11_getInstallationOrderForAllPackages_cycle() {
        try {
            manager.constructGraph("t0.json");
            
            manager.getInstallationOrderForAllPackages();
            
            fail("should through cycle exception");
        }catch(CycleException e) {    
            //Expected action
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }

    @Test
    public void test12_getPackageWithMaxDependencies_cycle() {
        try {
            manager.constructGraph("t0.json");
            
            manager.getPackageWithMaxDependencies();
            
            fail("should through cycle exception");
        }catch(CycleException e) {    
            //Expected action
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test13_getPackageWithMaxDependencies_correct() {
        try {
            manager.constructGraph("valid.json");
            
            String pkg = manager.getPackageWithMaxDependencies();
            
            if(manager.getInstallationOrder(pkg).size() != 4) {
                fail("size should be four");
            }
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
    
    @Test
    public void test14_t1() {
        try {
            manager.constructGraph("t1.json");
            
            List<String> total = manager.getInstallationOrderForAllPackages();
            
            if(total.size() != 6) {
                fail("Should have six packages, size: " + total.size());
            }else if(!(total.contains("C") && total.contains("D") && total.contains("B") && total.contains("A") && total.contains("F") && total.contains("E"))) {
                fail("The five should be A, B, C, D, E, and F");
            }
            
            
            List<String> C = manager.getInstallationOrder("C");
            
            if(C.size() != 3) {
                fail("Should have three packages, size: " + C.size());
            }else if(!(C.contains("C") && C.contains("A") && C.contains("B"))) {
                fail("The three should be B, C, and A");
            }
            
            List<String> F = manager.getInstallationOrder("F");
            
            if(F.size() != 2) {
                fail("Should have two packages, size: " + F.size());
            }else if(!(F.contains("D") && F.contains("F"))) {
                fail("The two should be F, and D");
            }
            
            List<String> CF = manager.toInstall("C", "F");
            
            if(CF.size() != 3) {
                fail("Should have three packages, size: " + CF.size());
            }else if(!(CF.contains("A") && CF.contains("B") && CF.contains("C"))) {
                fail("The three should be A, B, and C");
            }
            
            
        }catch(Exception e) {
            fail("No exceptions should be thrown: " + e);
        }
    }
}
