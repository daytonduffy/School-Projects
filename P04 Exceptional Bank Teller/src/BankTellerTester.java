import java.io.File;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class BankTellerTester {

    public static void main(String[] args) {
        
        boolean test1 = testBankTellerConstructor();
        System.out.println(test1);
        
        boolean test2 = testBankTellerAddBankAccountUsedIdentifier();
        System.out.println(test2);
        
        boolean test3 = testBankTellerLoadTransactionsFileNotFound();
        System.out.println(test3);
    }
    
    //tests if the accounts arraylist is constructed correctly
    public static boolean testBankTellerConstructor() {
        BankTeller tester = new BankTeller();
        
        if(tester.getAccountsCount() == 0) {
            return true;
        }
        return false;
    }
 
    //tests if an error is thrown when an ID is repeated
    public static boolean testBankTellerAddBankAccountUsedIdentifier() {
        BankTeller tester = new BankTeller();
        BankAccount test1 = new BankAccount("test", 50);
        BankAccount test2 = new BankAccount("test", 40);
        
        
        try {
            tester.addBankAccount(test1);
            tester.addBankAccount(test2);
            
        }catch(IllegalStateException e) {
            return true;
        }
        
        return false;
    }
    
    //tests if an error is thrown when the file is invalid but the account is valid
    public static boolean testBankTellerLoadTransactionsFileNotFound() {
        
        try {
            
            BankTeller tester = new BankTeller();
            BankAccount testAc = new BankAccount("test", 50);
            File file = new File("fake");
            
            tester.loadTransactions(file, testAc);
            
        }catch(FileNotFoundException e) {
            return true;
        }catch(DataFormatException e) {
            return false;
        }
        
        return false;
    }
    
}
