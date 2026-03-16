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
        user.addTransaction(new Transaction(0, user.getMobile(), "BANK", user.getBank().getName(), amt));

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

        Transaction t = new Transaction(
                sender.getMobile(),
                receiver.getMobile(),
                sender.getBank().getName(),
                receiver.getBank().getName(),
                amt
        );

        sender.addTransaction(t);
        receiver.addTransaction(t);

        System.out.println("Transfer successful!");
        System.out.println("Transaction ID: " + t.getTransactionId());
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
            System.out.println("From Mobile    : " + t.getSenderMobile());
            System.out.println("To Mobile      : " + t.getReceiverMobile());
            System.out.println("Amount         : ₹" + t.getAmount());
            System.out.println("Date-Time      : " + t.getTime().format(FMT));
        }

        System.out.printf("\nCurrent Balance : ₹%.2f%n", user.getBalance());
        System.out.println("===================================");
    }

    public static void searchTransaction(String id) {

        for (User u : Phonepay.users) {

            for (Transaction t : u.getTransactions()) {

                if (t.getTransactionId().equalsIgnoreCase(id)) {

                    System.out.println("\nTransaction Found");
                    System.out.println("--------------------------------");
                    System.out.println("Transaction ID : " + t.getTransactionId());
                    System.out.println("From Mobile    : " + t.getSenderMobile());
                    System.out.println("To Mobile      : " + t.getReceiverMobile());
                    System.out.println("From Bank      : " + t.getSenderBank());
                    System.out.println("To Bank        : " + t.getReceiverBank());
                    System.out.println("Amount         : ₹" + t.getAmount());
                    System.out.println("Date-Time      : " + t.getTime().format(FMT));
                    System.out.println("--------------------------------");
                    return;
                }
            }
        }

        System.out.println("Transaction not found!");
    }
}