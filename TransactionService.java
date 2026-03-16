package p1;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TransactionService {

    static DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd MMM yyyy   hh:mm a");

    public static void addMoney(User user, Scanner sc) {

        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();

        if (amt <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        user.addBalance(amt);
        user.addTransaction(new Transaction(0, amt, "ADD_MONEY"));

        System.out.println("Money added successfully!");
    }

    public static void transferMoney(User sender, User receiver, Scanner sc) {

        System.out.print("Amount: ");
        double amt = sc.nextDouble();

        if (amt <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        if (sender.getBalance() < amt) {
            System.out.println("Insufficient balance!");
            return;
        }

        sender.deductBalance(amt);
        receiver.addBalance(amt);

        sender.addTransaction(new Transaction(receiver.getMobile(), amt, "DEBIT"));
        receiver.addTransaction(new Transaction(sender.getMobile(), amt, "CREDIT"));

        System.out.println("Transfer successful!");
    }

    public static void showTransactions(User user) {

        List<Transaction> tx = user.getTransactions();

        if (tx.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\n======= TRANSACTION HISTORY =======");

        for (Transaction t : tx) {

            System.out.println("-------------------------------");
            System.out.println("Transaction ID : " + t.getTransactionId());
            System.out.println("Type      : " + t.getType());

            if (t.getOtherMobile() == 0)
                System.out.println("With      : BANK");
            else
                System.out.println("With      : " + t.getOtherMobile());

            System.out.printf("Amount    : ₹%.2f%n", t.getAmount());
            System.out.println("Date-Time : " + t.getTime().format(FMT));
        }

        System.out.printf("\nCurrent Balance : ₹%.2f%n", user.getBalance());
        System.out.println("===================================");
    }
}