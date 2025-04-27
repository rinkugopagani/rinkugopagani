import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Account {
    private static int idCounter = 1;
    private int accountId;
    private String name;
    private double balance;
    private double loan;

    public Account(String name, double deposit) {
        this.accountId = idCounter++;
        this.name = name;
        this.balance = deposit;
        this.loan = 0;
    }

    public int getAccountId() { return accountId; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public double getLoan() { return loan; }

    public void deposit(double amount) { balance += amount; }
    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void applyLoan(double amount) {
        loan += amount;
        balance += amount;
    }

    public boolean repayLoan(double amount) {
        double withInterest = amount * 1.05; // 5% interest
        if (withInterest <= balance && amount <= loan) {
            loan -= amount;
            balance -= withInterest;
            return true;
        }
        return false;
    }

    public boolean transferTo(Account target, double amount) {
        if (amount <= balance) {
            balance -= amount;
            target.balance += amount;
            return true;
        }
        return false;
    }

    public String getDetails() {
        return "Account ID: " + accountId + "\nName: " + name +
                "\nBalance: ₹" + balance + "\nLoan: ₹" + loan;
    }
}

public class BankingGUI extends JFrame {
    private final Map<Integer, Account> accounts = new HashMap<>();

    public BankingGUI() {
        setTitle("Banking System");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

        addButton("Create Account", e -> createAccount());
        addButton("View Account", e -> viewAccount());
        addButton("Deposit", e -> deposit());
        addButton("Withdraw", e -> withdraw());
        addButton("Transfer", e -> transfer());
        addButton("Apply Loan", e -> applyLoan());
        addButton("Repay Loan", e -> repayLoan());

        setVisible(true);
    }

    private void addButton(String title, ActionListener action) {
        JButton button = new JButton(title);
        button.addActionListener(action);
        add(button);
    }

    private void createAccount() {
        String name = JOptionPane.showInputDialog(this, "Enter name:");
        String depositStr = JOptionPane.showInputDialog(this, "Initial deposit:");
        try {
            double deposit = Double.parseDouble(depositStr);
            Account acc = new Account(name, deposit);
            accounts.put(acc.getAccountId(), acc);
            JOptionPane.showMessageDialog(this, "Account created. ID: " + acc.getAccountId());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid deposit amount.");
        }
    }

    private Account getAccount(String prompt) {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(this, prompt));
            return accounts.get(id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid account ID.");
            return null;
        }
    }

    private void viewAccount() {
        Account acc = getAccount("Enter account ID:");
        if (acc != null) {
            JOptionPane.showMessageDialog(this, acc.getDetails());
        } else {
            JOptionPane.showMessageDialog(this, "Account not found.");
        }
    }

    private void deposit() {
        Account acc = getAccount("Enter account ID:");
        if (acc != null) {
            String amtStr = JOptionPane.showInputDialog(this, "Amount to deposit:");
            try {
                double amt = Double.parseDouble(amtStr);
                acc.deposit(amt);
                JOptionPane.showMessageDialog(this, "Deposited ₹" + amt);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        }
    }

    private void withdraw() {
        Account acc = getAccount("Enter account ID:");
        if (acc != null) {
            String amtStr = JOptionPane.showInputDialog(this, "Amount to withdraw:");
            try {
                double amt = Double.parseDouble(amtStr);
                if (acc.withdraw(amt)) {
                    JOptionPane.showMessageDialog(this, "Withdraw ₹" + amt);
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        }
    }

    private void transfer() {
        Account from = getAccount("Sender Account ID:");
        Account to = getAccount("Receiver Account ID:");
        if (from != null && to != null) {
            String amtStr = JOptionPane.showInputDialog(this, "Amount to transfer:");
            try {
                double amt = Double.parseDouble(amtStr);
                if (from.transferTo(to, amt)) {
                    JOptionPane.showMessageDialog(this, "Transferred ₹" + amt);
                } else {
                    JOptionPane.showMessageDialog(this, "Transfer failed.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        }
    }

    private void applyLoan() {
        Account acc = getAccount("Enter account ID:");
        if (acc != null) {
            String amtStr = JOptionPane.showInputDialog(this, "Loan amount:");
            try {
                double amt = Double.parseDouble(amtStr);
                acc.applyLoan(amt);
                JOptionPane.showMessageDialog(this, "Loan of ₹" + amt + " approved.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        }
    }

    private void repayLoan() {
        Account acc = getAccount("Enter account ID:");
        if (acc != null) {
            String amtStr = JOptionPane.showInputDialog(this, "Repayment amount:");
            try {
                double amt = Double.parseDouble(amtStr);
                if (acc.repayLoan(amt)) {
                    JOptionPane.showMessageDialog(this, "Repaid ₹" + amt + " + 5% interest.");
                } else {
                    JOptionPane.showMessageDialog(this, "Repayment failed.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        }
    }


}
