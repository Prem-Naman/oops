import java.io.*;
import java.util.ArrayList;

public class CSVHandler {
    
    public static ArrayList<Account> readAccounts(String filePath) {
        ArrayList<Account> accounts = new ArrayList<>();
        
        try {
            File file = new File(filePath);
            
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write("accountNumber,name,pin,balance");
                writer.newLine();
                writer.close();
                return accounts;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                
                String[] data = line.split(",");
                if (data.length == 4) {
                    String accountNumber = data[0];
                    String name = data[1];
                    String pin = data[2];
                    double balance = Double.parseDouble(data[3]);
                    
                    Account account = new Account(accountNumber, name, pin, balance);
                    accounts.add(account);
                }
            }
            reader.close();
            
        } catch (IOException e) {
            System.out.println("Error reading accounts file: " + e.getMessage());
        }
        
        return accounts;
    }
    
    public static void writeAccount(String filePath, Account account) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            String line = account.getAccountNumber() + "," + 
                         account.getName() + "," + 
                         account.getPin() + "," + 
                         account.getBalance();
            writer.write(line);
            writer.newLine();
            writer.close();
            
        } catch (IOException e) {
            System.out.println("Error writing account: " + e.getMessage());
        }
    }
    
    public static void updateAccounts(String filePath, ArrayList<Account> accounts) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("accountNumber,name,pin,balance");
            writer.newLine();
            
            for (Account account : accounts) {
                String line = account.getAccountNumber() + "," + 
                             account.getName() + "," + 
                             account.getPin() + "," + 
                             account.getBalance();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            
        } catch (IOException e) {
            System.out.println("Error updating accounts: " + e.getMessage());
        }
    }
    
    public static void writeTransaction(String accountNumber, String date, String type, 
                                       double amount, double balance) {
        String filePath = "banking/data/transactions_" + accountNumber + ".csv";
        
        try {
            File file = new File(filePath);
            boolean isNewFile = !file.exists();
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            
            if (isNewFile) {
                writer.write("date,type,amount,balance");
                writer.newLine();
            }
            
            String line = date + "," + type + "," + amount + "," + balance;
            writer.write(line);
            writer.newLine();
            writer.close();
            
        } catch (IOException e) {
            System.out.println("Error writing transaction: " + e.getMessage());
        }
    }
    
    public static void readTransactions(String accountNumber) {
        String filePath = "banking/data/transactions_" + accountNumber + ".csv";
        
        try {
            File file = new File(filePath);
            
            if (!file.exists()) {
                System.out.println("No transaction history found.");
                return;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean firstLine = true;
            
            System.out.println("\n========== TRANSACTION HISTORY ==========");
            System.out.printf("%-20s %-15s %-12s %-12s\n", "Date", "Type", "Amount", "Balance");
            System.out.println("----------------------------------------------------------");
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                
                String[] data = line.split(",");
                if (data.length == 4) {
                    System.out.printf("%-20s %-15s Rs. %-8s Rs. %-8s\n", 
                                    data[0], data[1], data[2], data[3]);
                }
            }
            System.out.println("----------------------------------------------------------\n");
            reader.close();
            
        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }
    }
}
