package org.bank_management_system;

import java.sql.*;

public class AccountService {
    private Connection connection;

    public AccountService() {
        connection = DatabaseConnection.getConnection();
    }

    public void createAccount(String name, String email) {
        String query = "INSERT INTO accounts (name, email) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("Account created successfully!");
        } catch (SQLException e) {
            System.out.println("Error creating account!");
            e.printStackTrace();
        }
    }

    public void deposit(int accountId, double amount) {
        try {
            connection.setAutoCommit(false);

            // Update balance
            String updateBalance = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateBalance)) {
                stmt.setDouble(1, amount);
                stmt.setInt(2, accountId);
                stmt.executeUpdate();
            }

            // Record transaction
            String insertTransaction = "INSERT INTO transactions (account_id, amount, type) VALUES (?, ?, 'DEPOSIT')";
            try (PreparedStatement stmt = connection.prepareStatement(insertTransaction)) {
                stmt.setInt(1, accountId);
                stmt.setDouble(2, amount);
                stmt.executeUpdate();
            }

            connection.commit();
            System.out.println("Deposit successful!");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            System.out.println("Error during deposit!");
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void checkBalance(int accountId) {
        String query = "SELECT balance FROM accounts WHERE account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Current Balance: $" + rs.getDouble("balance"));
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving balance!");
            e.printStackTrace();
        }
    }

    // Other methods: withdraw(), viewTransactionHistory(), etc.
}

