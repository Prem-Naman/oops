import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BankService {
    private static final String ACCOUNTS_FILE = "banking/data/accounts.csv";
    private Scanner scanner;
    
    public BankService() {
        scanner = new Scanner(System.in);
    }
    
    private String generateAccountNumber() {
        ArrayList<Account> accounts = CSVHandler.readAccounts(ACCOUNTS_FILE);
        
        if (accounts.isEmpty()) {
            return "1001";
        }
        
        String lastAccountNumber = accounts.get(accounts.size() - 1).getAccountNumber();
        int newNumber = Integer.parseInt(lastAccountNumber) + 1;
        return String.valueOf(newNumber);
    }
    
    public void createAccount() {
        System.out.println("\n========== CREATE NEW ACCOUNT ==========");
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        String pin;
        while (true) {
            System.out.print("Enter 4-digit PIN: ");
            pin = scanner.nextLine();
            
            if (pin.length() == 4 && pin.matches("[0-9]+")) {
                break;
            } else {
                System.out.println("Invalid PIN! Please enter exactly 4 digits.");
            }
        }
        
        double initialDeposit;
        while (true) {
            System.out.print("Enter initial deposit amount (minimum Rs. 500): ");
            try {
                initialDeposit = Double.parseDouble(scanner.nextLine());
                
                if (initialDeposit >= 500) {
                    break;
                } else {
                    System.out.println("Minimum deposit is Rs. 500!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount! Please enter a valid number.");
            }
        }
        
        String accountNumber = generateAccountNumber();
        Account newAccount = new Account(accountNumber, name, pin, initialDeposit);
        CSVHandler.writeAccount(ACCOUNTS_FILE, newAccount);
        
        String date = getCurrentDateTime();
        CSVHandler.writeTransaction(accountNumber, date, "Initial Deposit", 
                                   initialDeposit, initialDeposit);
        
        System.out.println("\n✓ Account created successfully!");
        System.out.println("Your Account Number: " + accountNumber);
        System.out.println("Please remember your account number and PIN.");
        System.out.println("=========================================\n");
    }
    
    public Account login() {
        System.out.println("\n========== LOGIN ==========");
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        
        ArrayList<Account> accounts = CSVHandler.readAccounts(ACCOUNTS_FILE);
        
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber) && 
                account.getPin().equals(pin)) {
                System.out.println("\n✓ Login successful!");
                System.out.println("Welcome, " + account.getName() + "!");
                return account;
            }
        }
        
        System.out.println("\n✗ Invalid account number or PIN!");
        return null;
    }
    
    public void checkBalance(Account account) {
        Account updatedAccount = getUpdatedAccount(account.getAccountNumber());
        
        if (updatedAccount != null) {
            System.out.println("\n========== BALANCE INQUIRY ==========");
            System.out.println("Account Number: " + updatedAccount.getAccountNumber());
            System.out.println("Account Holder: " + updatedAccount.getName());
            System.out.println("Current Balance: Rs. " + updatedAccount.getBalance());
            System.out.println("=====================================\n");
            
            account.setBalance(updatedAccount.getBalance());
        }
    }
    
    public void deposit(Account account) {
        System.out.println("\n========== DEPOSIT MONEY ==========");
        
        System.out.print("Enter amount to deposit: Rs. ");
        double amount;
        
        try {
            amount = Double.parseDouble(scanner.nextLine());
            
            if (amount <= 0) {
                System.out.println("Invalid amount! Amount must be greater than zero.");
                return;
            }
            
            double newBalance = account.getBalance() + amount;
            account.setBalance(newBalance);
            updateAccountBalance(account);
            
            String date = getCurrentDateTime();
            CSVHandler.writeTransaction(account.getAccountNumber(), date, 
                                       "Deposit", amount, newBalance);
            
            System.out.println("\n✓ Deposit successful!");
            System.out.println("Amount Deposited: Rs. " + amount);
            System.out.println("New Balance: Rs. " + newBalance);
            System.out.println("===================================\n");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount! Please enter a valid number.");
        }
    }
    
    public void withdraw(Account account) {
        System.out.println("\n========== WITHDRAW MONEY ==========");
        
        System.out.print("Enter amount to withdraw: Rs. ");
        double amount;
        
        try {
            amount = Double.parseDouble(scanner.nextLine());
            
            if (amount <= 0) {
                System.out.println("Invalid amount! Amount must be greater than zero.");
                return;
            }
            
            if (amount > account.getBalance()) {
                System.out.println("\n✗ Insufficient balance!");
                System.out.println("Your current balance: Rs. " + account.getBalance());
                return;
            }
            
            double newBalance = account.getBalance() - amount;
            account.setBalance(newBalance);
            updateAccountBalance(account);
            
            String date = getCurrentDateTime();
            CSVHandler.writeTransaction(account.getAccountNumber(), date, 
                                       "Withdrawal", amount, newBalance);
            
            System.out.println("\n✓ Withdrawal successful!");
            System.out.println("Amount Withdrawn: Rs. " + amount);
            System.out.println("New Balance: Rs. " + newBalance);
            System.out.println("====================================\n");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount! Please enter a valid number.");
        }
    }
    
    public void transfer(Account senderAccount) {
        System.out.println("\n========== TRANSFER MONEY ==========");
        
        System.out.print("Enter receiver's account number: ");
        String receiverAccountNumber = scanner.nextLine();
        
        if (receiverAccountNumber.equals(senderAccount.getAccountNumber())) {
            System.out.println("Cannot transfer to the same account!");
            return;
        }
        
        Account receiverAccount = getUpdatedAccount(receiverAccountNumber);
        
        if (receiverAccount == null) {
            System.out.println("\n✗ Receiver account not found!");
            return;
        }
        
        System.out.println("Receiver Name: " + receiverAccount.getName());
        System.out.print("Enter amount to transfer: Rs. ");
        
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
            
            if (amount <= 0) {
                System.out.println("Invalid amount! Amount must be greater than zero.");
                return;
            }
            
            if (amount > senderAccount.getBalance()) {
                System.out.println("\n✗ Insufficient balance!");
                System.out.println("Your current balance: Rs. " + senderAccount.getBalance());
                return;
            }
            
            double senderNewBalance = senderAccount.getBalance() - amount;
            senderAccount.setBalance(senderNewBalance);
            
            double receiverNewBalance = receiverAccount.getBalance() + amount;
            receiverAccount.setBalance(receiverNewBalance);
            
            updateAccountBalance(senderAccount);
            updateAccountBalance(receiverAccount);
            
            String date = getCurrentDateTime();
            CSVHandler.writeTransaction(senderAccount.getAccountNumber(), date, 
                                       "Transfer to " + receiverAccountNumber, 
                                       amount, senderNewBalance);
            CSVHandler.writeTransaction(receiverAccount.getAccountNumber(), date, 
                                       "Transfer from " + senderAccount.getAccountNumber(), 
                                       amount, receiverNewBalance);
            
            System.out.println("\n✓ Transfer successful!");
            System.out.println("Amount Transferred: Rs. " + amount);
            System.out.println("Transferred to: " + receiverAccount.getName());
            System.out.println("Your New Balance: Rs. " + senderNewBalance);
            System.out.println("====================================\n");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount! Please enter a valid number.");
        }
    }
    
    public void viewHistory(Account account) {
        CSVHandler.readTransactions(account.getAccountNumber());
    }
    
    private void updateAccountBalance(Account updatedAccount) {
        ArrayList<Account> accounts = CSVHandler.readAccounts(ACCOUNTS_FILE);
        
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(updatedAccount.getAccountNumber())) {
                accounts.set(i, updatedAccount);
                break;
            }
        }
        
        CSVHandler.updateAccounts(ACCOUNTS_FILE, accounts);
    }
    
    private Account getUpdatedAccount(String accountNumber) {
        ArrayList<Account> accounts = CSVHandler.readAccounts(ACCOUNTS_FILE);
        
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        
        return null;
    }
    
    private String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}
