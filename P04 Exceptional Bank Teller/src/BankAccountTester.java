import java.util.Arrays;
import java.util.zip.DataFormatException;

public class BankAccountTester {
    public static void main(String[] args) throws DataFormatException {
        boolean test1 = testBankAccountConstructorValidInitialBalance();
        System.out.println(test1);
        
        boolean test2 = testBankAccountConstructorNotValidInitialBalance();
        System.out.println(test2);
        
        boolean test3 = testBankAccountEquals();
        System.out.println(test3);
        
        boolean test4 = testBankAccountWithdrawInvalidAmount();
        System.out.println(test4);
    
        boolean test5 = testBankAccountWithdrawLargerOfBalanceAmount();
        System.out.println(test5);

        boolean test6 = testBankAccountWithdrawValidAmount();
        System.out.println(test6);

        boolean test7 = testBankAccountDepositNegativeAmount();
        System.out.println(test7);
        
        boolean test8 = testBankAccountDepositValidAmount();
        System.out.println(test8);
        
        
        BankAccount test = new BankAccount("Test", 50);
        test.withdraw(30);
        test.deposit(100);
        
        System.out.println(Arrays.toString(test.getMostRecentTransactions()));
        
    }
    
    //tests weather BankAccount objects are being constructed correctly
    public static boolean testBankAccountConstructorValidInitialBalance() {
        
        BankAccount tester = new BankAccount("test", 10);//create new account
        
        if(tester.getBalance() != 10) {
            return false;
        }else if(!tester.getID().equals("test")) {
            return false;
        }else if(tester.getTransactionsCount() != 1) {
            return false;
        }else if(!tester.getMostRecentTransactions()[0].equals("1 10")) {
            return false;
        }

        return true;
    }
    
    //tests weather the constructor will catch an error in the initial balance entered
    public static boolean testBankAccountConstructorNotValidInitialBalance() {
        try {
            BankAccount tester = new BankAccount("test", -10);// invalid amount
            
        }catch(IllegalArgumentException e) {
            return true;
        } 
        
        return false;
    }
    
    //tests if the equals method will detect identical ID's
    public static boolean testBankAccountEquals() {
        
        BankAccount tester1 = new BankAccount("test", 10);// account 1
        BankAccount tester2 = new BankAccount("test", 30);// identical account 2

        if(tester1.equals(tester2)) {//returns true if they're equal
            return true;
        }
        return false;
    }
    
    
    //tests that withdraw method throws errors when it needs to
    public static boolean testBankAccountWithdrawInvalidAmount() {
        boolean part1 = false;//helps keep things clean
        boolean part2 = false;// ^
        
        try {//first block for when it's passed a negative
            BankAccount tester1 = new BankAccount("test", 10);
            tester1.withdraw(-10);
            
        }catch(DataFormatException e) {
            part1 = true;
        }
        
        try {//second block for when it's passed a non multiple of 10
            BankAccount tester2 = new BankAccount("test", 10);
            tester2.withdraw(5);
            
        }catch(DataFormatException e) {
            part2 = true;
        }
        
        if(part1 && part2) {
            return true;
        }
        return false;
    }
    
    //tests if withdraw will throw an IllegalStateException when given to large an input
    public static boolean testBankAccountWithdrawLargerOfBalanceAmount() {
        try {
            BankAccount tester = new BankAccount("test", 10);
            tester.withdraw(20);// valid amount despite size
        
        }catch(IllegalStateException e) {//actual error I'm trying to catch
            return true;
        }catch(DataFormatException e) {//not the type thrown but needs to be here
            return true;
        } 
        
        return false;
    }
    
    //tests if account balance is updated correctly with correct input
    public static boolean testBankAccountWithdrawValidAmount() {
        
        try {
            BankAccount tester = new BankAccount("test", 50);
            tester.withdraw(20);// valid amount 
        
            if(tester.getBalance() == 30) {
                return true;
            }
            
        }catch(IllegalStateException e) {//potential error catches
            return false;
        }catch(DataFormatException e) {
            return false;
        }
        
        return false;
    }
    
    //tests if an error is thrown when a negative is passed into deposit
    public static boolean testBankAccountDepositNegativeAmount() {
        
        try {
            BankAccount tester = new BankAccount("test", 50);
            tester.deposit(-20);// invalid amount 
            
        }catch(IllegalArgumentException e) {//error catche
            return true;
        }
        
        return false;
    }
    
    //tests if balance updates properly upon deposit 
    public static boolean testBankAccountDepositValidAmount() {
        
        try {
            BankAccount tester = new BankAccount("test", 50);
            tester.deposit(20);// valid amount 
            
            if(tester.getBalance() == 70) {
                return true;
            }
            
        }catch(IllegalArgumentException e) {//potential error catch
            return false;
        }
        
        return false;
    }
    
}
