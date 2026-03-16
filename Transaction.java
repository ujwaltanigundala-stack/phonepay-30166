package p1;

import java.time.LocalDateTime;
import java.util.HashSet;

public class Transaction {

    private static HashSet<String> usedIds = new HashSet<>();

    private String transactionId;
    private long otherMobile;
    private double amount;
    private String type;
    private LocalDateTime time;

    public Transaction(long otherMobile, double amount, String type) {
        this.otherMobile = otherMobile;
        this.amount = amount;
        this.type = type;
        this.time = LocalDateTime.now();
        this.transactionId = generateTransactionId();
    }

    private String generateTransactionId() {

        String id;

        do {
            int hash = (otherMobile + "" + amount + type + time).hashCode() & 0x7fffffff;
            id = "TXN" + hash;
        } while (usedIds.contains(id));

        usedIds.add(id);
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public long getOtherMobile() {
        return otherMobile;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getTime() {
        return time;
    }
}