package org.bank_management_system;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccountService accountService = new AccountService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Bank Management System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Email: ");
                    String email = scanner.next();
                    accountService.createAccount(name, email);
                    break;

                case 2:
                    System.out.print("Enter Account ID: ");
                    int accountId = scanner.nextInt();
                    System.out.print("Enter Amount: ");
                    double amount = scanner.nextDouble();
                    accountService.deposit(accountId, amount);
                    break;

                case 3:
                    System.out.print("Enter Account ID: ");
                    int accId = scanner.nextInt();
                    accountService.checkBalance(accId);
                    break;

                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}