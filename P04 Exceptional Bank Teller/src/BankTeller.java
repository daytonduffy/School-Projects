import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class BankTeller {
    private ArrayList<BankAccount> accounts;

    //BankTeller constructor
    public BankTeller() {
        accounts = new ArrayList<BankAccount>();
    }
    
    //adds a new bank account to the accounts ArrayList
    public void addBankAccount(BankAccount newAccount) {
        
        for(int i=0; i < accounts.size(); ++i) {
            if(accounts.get(i).getID().equals(newAccount.getID())) {
                throw new IllegalStateException("ID already in use '" + newAccount.getID() + "'");
            }
        }
        if(newAccount == null) {
            throw new IllegalArgumentException("New Account is null");
        }
        
        accounts.add(newAccount);
    }
    
    //finds account matching the given id
    public BankAccount findAccount(String id) {
        int index = 0;//index of the bank account
        
        for(int i=0; i < accounts.size(); ++i) {//check all the accounts
            if(accounts.get(i).getID().equals(id)) {
                index = i;
                break;
            }
        }
        
        if(index == -1) {
            throw new NoSuchElementException("No matching account to ID: '" + id + "'");
        }
        
        return accounts.get(index);
    }
    
    //adds a single transaction to the account
    public void addTransaction(String transaction, BankAccount account) throws DataFormatException {
        String[] parts = transaction.split(" ");//splits the transaction
        parts[0] = parts[0].trim();
        parts[1] = parts[1].trim();
        
        
        if(parts.length != 2) {
            throw new DataFormatException("Format incorrect '" + transaction + "'");
        }else if(account == null) {
            throw new NullPointerException("Account is null");
        }else if(!(parts[0].equals("0") || parts[0].equals("1"))) {
            throw new DataFormatException("Format incorrect '" + transaction + "'");
        }
        
        String code = parts[0];
        int ammount = Integer.parseInt(parts[1]);
        
        if(code.equals("0")) {//apply transaction
            account.withdraw(ammount);
        }else if(code.equals("1")) {
            account.deposit(ammount);
        }else {
            throw new DataFormatException("Format incorrect '" + transaction + "'");
        }
    }
    
    //reads a file and adds transactions to account
    public void loadTransactions(File file, BankAccount account) throws FileNotFoundException, DataFormatException {
        if(account == null) {
            throw new NullPointerException("Account is null");
        }else if(!file.canRead()) {
            throw new FileNotFoundException("The file object does not correspond to an actual file within the file system");
        }
        
        Scanner scnr = new Scanner(file);// open file
        
        while(scnr.hasNextLine()) {//load transactions until no more lines
            String transaction = scnr.nextLine().trim();
            addTransaction(transaction, account);
        }
        scnr.close();
    }
    
    //returns the number of accounts so far
    public int getAccountsCount() {
        return accounts.size();
    }
}
