package p1;

import java.util.HashSet;
import java.util.Scanner;

public class UPIRegistration {

    private static HashSet<String> usedUpiIds = new HashSet<>();

    public static void registerUser(Scanner sc) {

        System.out.print("Enter Mobile Number: ");
        long mobile = sc.nextLong();

        if (Phonepay.findUser(mobile) != null) {
            System.out.println("User already exists!");
            return;
        }

        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.println("Select Bank:");
        for (int i = 0; i < Phonepay.banks.size(); i++)
            System.out.println((i + 1) + ". " + Phonepay.banks.get(i).getName());

        int choice = sc.nextInt();
        Bank bank = Phonepay.banks.get(choice - 1);

        System.out.print("Enter Account Number (" + bank.getAccLength() + " digits): ");
        long accNo = sc.nextLong();

        System.out.print("Enter Initial Balance: ");
        double bal = sc.nextDouble();

        String upiId = generateUpiId(mobile, bank);

        User user = new User(mobile, name, bal, bank, accNo, upiId);
        Phonepay.users.add(user);

        System.out.println("UPI Registration Successful!");
        System.out.println("UPI ID : " + upiId);
    }

    private static String generateUpiId(long mobile, Bank bank) {

        String id = mobile + "@" + bank.getName().toLowerCase();

        if (!usedUpiIds.contains(id))
            usedUpiIds.add(id);

        return id;
    }
}