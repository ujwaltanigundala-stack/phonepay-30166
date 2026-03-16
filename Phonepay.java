package p1;

import java.util.*;

public class Phonepay {

    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Bank> banks = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        banks.add(new SBI());
        banks.add(new HDFC());
        banks.add(new ICICI());

        while (true) {
            System.out.println("\n1. Add User");
            System.out.println("2. Add Money");
            System.out.println("3. Transfer Money");
            System.out.println("4. Display All Users");
            System.out.println("5. Display User Details");
            System.out.println("6. Show Transactions");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1: UPIRegistration.registerUser(sc); break;
                case 2: addMoney(); break;
                case 3: transferMoney(); break;
                case 4: displayAllUsers(); break;
                case 5: displayUserDetails(); break;
                case 6: showTransactions(); break;
                case 7:
                    System.out.println("Thank you for using PhonePe!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static User findUser(long mobile) {
        for (User u : users)
            if (u.getMobile() == mobile)
                return u;
        return null;
    }

    static User findUserByUpi(String upi) {
        for (User u : users)
            if (u.getUpiId().equalsIgnoreCase(upi))
                return u;
        return null;
    }

    static User findUserByAccount(long accNo) {
        for (User u : users)
            if (u.getAccountNo() == accNo)
                return u;
        return null;
    }

    static void addMoney() {

        System.out.print("Enter Mobile Number: ");
        long mobile = sc.nextLong();

        User user = findUser(mobile);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        TransactionService.addMoney(user, sc);
    }

    static void transferMoney() {

        sc.nextLine();
        System.out.print("Enter Sender UPI ID: ");
        String senderUpi = sc.nextLine();

        User sender = findUserByUpi(senderUpi);

        if (sender == null) {
            System.out.println("Sender not found!");
            return;
        }

        System.out.print("Enter Receiver UPI ID: ");
        String receiverUpi = sc.nextLine();

        User receiver = findUserByUpi(receiverUpi);

        if (receiver == null) {
            System.out.println("Receiver not found!");
            return;
        }

        if (sender == receiver) {
            System.out.println("Cannot transfer to same account!");
            return;
        }

        TransactionService.transferMoney(sender, receiver, sc);
    }

    static void displayAllUsers() {

        if (users.isEmpty()) {
            System.out.println("No users available.");
            return;
        }

        System.out.println("\n======= ALL USERS =======");

        for (User u : users) {

            String acc = String.valueOf(u.getAccountNo());
            String last4 = acc.substring(acc.length() - 4);

            System.out.println("----------------------------");
            System.out.println("Name   : " + u.getName());
            System.out.println("Mobile : " + u.getMobile());
            System.out.println("UPI ID : " + u.getUpiId());
            System.out.println("Bank   : " + u.getBank().getName());
            System.out.println("Acc No : XXXX" + last4);
        }

        System.out.println("==========================");
    }

    static void displayUserDetails() {

        System.out.print("Enter Mobile Number: ");
        long mobile = sc.nextLong();

        User user = findUser(mobile);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        String acc = String.valueOf(user.getAccountNo());
        String last4 = acc.substring(acc.length() - 4);

        System.out.println("\n===== USER DETAILS =====");
        System.out.println("Name      : " + user.getName());
        System.out.println("Mobile    : " + user.getMobile());
        System.out.println("UPI ID    : " + user.getUpiId());
        System.out.println("Bank      : " + user.getBank().getName());
        System.out.println("Account   : XXXX" + last4);
        System.out.printf("Balance   : ₹%.2f%n", user.getBalance());
    }

    static void showTransactions() {

        System.out.print("Enter Mobile Number: ");
        long mobile = sc.nextLong();

        User user = findUser(mobile);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        TransactionService.showTransactions(user);
    }
}