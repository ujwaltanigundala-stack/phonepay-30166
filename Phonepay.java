package p1;

import java.util.*;

public class Phonepay {

    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Bank> banks = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        banks.add(new Bank("SBI", 11));
        banks.add(new Bank("HDFC", 12));
        banks.add(new Bank("ICICI", 12));

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
                case 1: addUser(); break;
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

    static User findUserByAccount(long accNo) {
        for (User u : users)
            if (u.getAccountNo() == accNo)
                return u;
        return null;
    }

    static void addUser() {
        System.out.print("Enter Mobile Number: ");
        long mobile = sc.nextLong();

        if (findUser(mobile) != null) {
            System.out.println("User already exists!");
            return;
        }

        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.println("Select Bank:");
        for (int i = 0; i < banks.size(); i++)
            System.out.println((i + 1) + ". " + banks.get(i).getName());

        int choice = sc.nextInt();
        Bank bank = banks.get(choice - 1);

        System.out.print("Enter Account Number (" +
                bank.getAccLength() + " digits): ");
        long accNo = sc.nextLong();

        System.out.print("Enter Initial Balance: ");
        double bal = sc.nextDouble();

        users.add(new User(mobile, name, bal, bank, accNo));
        System.out.println("User added successfully!");
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

        System.out.println("Select sender by:");
        System.out.println("1. Mobile Number");
        System.out.println("2. Account Number");
        int fromChoice = sc.nextInt();

        User sender = null;

        if (fromChoice == 1) {
            System.out.print("Enter Sender Mobile: ");
            long mob = sc.nextLong();
            sender = findUser(mob);
        } 
        else if (fromChoice == 2) {
            System.out.print("Enter Sender Account No: ");
            long acc = sc.nextLong();
            sender = findUserByAccount(acc);
        }

        if (sender == null) {
            System.out.println("Sender not found!");
            return;
        }

        System.out.print("Enter Receiver Mobile/Account: ");
        long recInput = sc.nextLong();

        User receiver = findUser(recInput);

        if (receiver == null)
            receiver = findUserByAccount(recInput);

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
        System.out.println("Bank      : " + user.getBank().getName());
        System.out.println("Account   : XXXX" + last4);
        System.out.printf("Balance   : ₹%.2f%n", user.getBalance());

        System.out.println("\nLast 5 Transactions:");

        List<Transaction> tx = user.getTransactions();

        if (tx.isEmpty()) {
            System.out.println("No transactions.");
        } else {

            int start = Math.max(0, tx.size() - 5);
            int count = 1;

            for (int i = tx.size() - 1; i >= start; i--) {
                Transaction t = tx.get(i);

                System.out.println("-----------------------");
                System.out.println("Tx #" + count++);
                System.out.println("Type  : " + t.getType());
                System.out.println("With  : " +
                        (t.getOtherMobile() == 0 ? "BANK" : t.getOtherMobile()));
                System.out.printf("Amt   : ₹%.2f%n", t.getAmount());
                System.out.println("Time  : " +
                        t.getTime().format(TransactionService.FMT));
            }
        }

        System.out.println("=======================");
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