import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Task3 {
    private static Map<Integer, Account> accounts = new HashMap<>();
    private static int currentUserId;

    public static void main(String[] args) {
        // Create dummy accounts for demo
        accounts.put(1234, new Account(1234, 5678, 1000.0));
        accounts.put(5678, new Account(5678, 1234, 2000.0));

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the ATM system!");

        while (true) {
            System.out.print("Enter User ID: ");
            int userId = scanner.nextInt();
            System.out.print("Enter PIN: ");
            int pin = scanner.nextInt();

            if (authenticateUser(userId, pin)) {
                currentUserId = userId;
                System.out.println("Authentication successful!");
                break;
            } else {
                System.out.println("Invalid User ID or PIN. Please try again.");
            }
        }

        showMainMenu(scanner);
    }

    private static boolean authenticateUser(int userId, int pin) {
        Account account = accounts.get(userId);
        return account != null && account.getPin() == pin;
    }

    private static void showMainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showTransactionsHistory();
                    break;
                case 2:
                    performWithdraw(scanner);
                    break;
                case 3:
                    performDeposit(scanner);
                    break;
                case 4:
                    performTransfer(scanner);
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showTransactionsHistory() {
        Account account = accounts.get(currentUserId);
        System.out.println("\n--- Transactions History ---");
        for (String transaction : account.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    private static void performWithdraw(Scanner scanner) {
        Account account = accounts.get(currentUserId);
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();

        if (account.withdraw(amount)) {
            System.out.println("Withdrawal successful. Please take your cash.");
        } else {
            System.out.println("Insufficient balance. Withdrawal failed.");
        }
    }

    private static void performDeposit(Scanner scanner) {
        Account account = accounts.get(currentUserId);
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();

        account.deposit(amount);
        System.out.println("Deposit successful.");
    }

    private static void performTransfer(Scanner scanner) {
        Account account = accounts.get(currentUserId);
        System.out.print("Enter the recipient's User ID: ");
        int recipientId = scanner.nextInt();

        Account recipient = accounts.get(recipientId);
        if (recipient == null) {
            System.out.println("Recipient User ID not found. Transfer failed.");
            return;
        }

        System.out.print("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();

        if (account.transfer(recipient, amount)) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient balance. Transfer failed.");
        }
    }
}

class Account {
    private int userId;
    private int pin;
    private double balance;
    private StringBuilder transactionHistory;

    public Account(int userId, int pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new StringBuilder();
    }

    public int getUserId() {
        return userId;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public String[] getTransactionHistory() {
        return transactionHistory.toString().split("\n");
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            transactionHistory.append("Withdrawal: -$").append(amount).append("\n");
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.append("Deposit: +$").append(amount).append("\n");
        }
    }

    public boolean transfer(Account recipient, double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.append("Transfer to User ID ")
                    .append(recipient.getUserId())
                    .append(": -$")
                    .append(amount)
                    .append("\n");
            return true;
        }
        return false;
    }
}
