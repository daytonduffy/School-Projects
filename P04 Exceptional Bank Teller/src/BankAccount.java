import java.util.ArrayList;
import java.util.zip.DataFormatException;

public class BankAccount {
    private String id; //unique id for each account
    private int balance; // account's balance
    private ArrayList<String> transactions = new ArrayList<String>(); // account's transaction history
    
    public BankAccount(String id, int balance) {//creates new account
        if(balance < 10) {
            throw new IllegalArgumentException("Balance Amount Negative " + balance);
        }
        
        
        this.id = id;
        this.balance = balance;
        
        transactions.add("1 " + this.balance);//adds first transaction 
    }
    
    public String getID() {//returns unique account ID
        return id;
    }
    public int getBalance() {//returns current balance
        return balance;
    }
    
    //returns true if the other account has the same ID as this one
    public boolean equals(BankAccount other) {
        boolean equal = false;
        if(id.equals(other.getID())) {
            equal = true;
        }
        return equal;
    }
    //manages what needs to happen with a deposit
    public void deposit(int depositAmount) throws IllegalArgumentException{
        if(depositAmount < 0) {//catch error
            throw new IllegalArgumentException("Deposit Amount Negative " + depositAmount);
        }
        
        balance = balance + depositAmount;//deposits amount into account
        transactions.add(0, "1 " + depositAmount);//adds transaction to history
        
    }
    //handles withdraws from the account
    public void withdraw(int withdrawAmount) throws DataFormatException {
        if(withdrawAmount < 0) {//catch errors
            throw new DataFormatException("Negative Withdraw Amount" + withdrawAmount);
        }else if(withdrawAmount %10 != 0) {
            throw new DataFormatException("Withdraw Amount Not Multipul Of 10" + withdrawAmount);
        }else if(withdrawAmount > balance) {
            throw new IllegalStateException("Withdraw Amount Greater Than Account Balance" + withdrawAmount);
        }
                
        balance = balance - withdrawAmount;// takes out withdraw amount
        transactions.add(0, "0 " + withdrawAmount);// adds transaction to history
    }
    
    
    public String[] getMostRecentTransactions() {
        String[] recent;//new String Array to be returned
        
        int number = transactions.size();//simplifies later code
        int countHelp = 5;//needed for proper index of transactions

        if(number == 0) {
            recent = new String[5];
            for(int i=0; i < 5; ++i) {
                recent[i] = null;
            }
        }else if(number == 1) {
            recent = new String[5];
            recent[0] = transactions.get(0);
            for(int i=1; i < 5; ++i) {
                recent[i] = null;
            }
        }else if(number == 2) {
            recent = new String[5];
            recent[0] = transactions.get(0);
            recent[1] = transactions.get(1);
            for(int i=2; i < 5; ++i) {
                recent[i] = null;
            }
        }else if (number == 3) {
            recent = new String[5];
            for(int i=0; i < 3; ++i) {
                recent[i] = transactions.get(i);
            }
            for(int i=3; i < 5; ++i) {
                recent[i] = null;
            }
        }else if(number == 4) {
            recent = new String[5];
            recent[4] = null;
            for(int i=0; i < 4; ++i) {
                recent[i] = transactions.get(i);
            }
        }else {
            recent = new String[5];
            for(int i=0; i < 5 ; ++i) {
                recent[i] = transactions.get(i);
            }
        }
        
        return recent;
    }
    
    public int getTransactionsCount() {//returns the number of transactions on the account
        int count = transactions.size();
        return count;
    }
}
