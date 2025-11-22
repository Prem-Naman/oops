import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankService bankService = new BankService();
        
        while (true) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║   WELCOME TO SIMPLE BANKING SYSTEM     ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("1. Create New Account");
            System.out.println("2. Login to Existing Account");
            System.out.println("3. Exit");
            System.out.print("\nEnter your choice: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    bankService.createAccount();
                    break;
                    
                case "2":
                    Account loggedInAccount = bankService.login();
                    if (loggedInAccount != null) {
                        accountMenu(loggedInAccount, bankService, scanner);
                    }
                    break;
                    
                case "3":
                    System.out.println("\nThank you for using Simple Banking System!");
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("\n Invalid choice! Please enter 1, 2, or 3.");
            }
        }
    }
    
    private static void accountMenu(Account account, BankService bankService, Scanner scanner) {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║         ACCOUNT MENU                   ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Transaction History");
            System.out.println("6. Logout");
            System.out.print("\nEnter your choice: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    bankService.checkBalance(account);
                    break;
                case "2":
                    bankService.deposit(account);
                    break;
                case "3":
                    bankService.withdraw(account);
                    break;
                case "4":
                    bankService.transfer(account);
                    break;
                case "5":
                    bankService.viewHistory(account);
                    break;
                case "6":
                    System.out.println("\nLogged out successfully!");
                    return;
                default:
                    System.out.println("\nInvalid choice! Please enter 1-6.");
            }
        }
    }
}
