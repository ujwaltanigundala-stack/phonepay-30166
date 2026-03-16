package p1;

import java.util.ArrayList;

public class User {

    private String name;
    private long mobile;
    private double balance;
    private Bank bank;
    private long accountNo;
    private String upiId;
    private ArrayList<Transaction> transactions;

    public User(long mobile, String name, double balance, Bank bank, long accountNo, String upiId) {
        this.mobile = mobile;
        this.name = name;
        this.balance = balance;
        this.bank = bank;
        this.accountNo = accountNo;
        this.upiId = upiId;
        this.transactions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public long getMobile() {
        return mobile;
    }

    public double getBalance() {
        return balance;
    }

    public Bank getBank() {
        return bank;
    }

    public long getAccountNo() {
        return accountNo;
    }

    public String getUpiId() {
        return upiId;
    }

    public void addBalance(double amount) {
        balance += amount;
    }

    public void deductBalance(double amount) {
        balance -= amount;
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}