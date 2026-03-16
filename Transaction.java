package p1;

import java.time.LocalDateTime;
import java.util.HashSet;

public class Transaction {

    private static HashSet<String> usedIds = new HashSet<>();

    private String transactionId;
    private long senderMobile;
    private long receiverMobile;
    private String senderBank;
    private String receiverBank;
    private double amount;
    private LocalDateTime time;

    public Transaction(long senderMobile, long receiverMobile, String senderBank, String receiverBank, double amount) {
        this.senderMobile = senderMobile;
        this.receiverMobile = receiverMobile;
        this.senderBank = senderBank;
        this.receiverBank = receiverBank;
        this.amount = amount;
        this.time = LocalDateTime.now();
        this.transactionId = generateTransactionId();
    }

    private String generateTransactionId() {

        String id;

        do {
            int hash = (senderMobile + "" + receiverMobile + amount + time).hashCode() & 0x7fffffff;
            id = "TXN" + hash;
        } while (usedIds.contains(id));

        usedIds.add(id);
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public long getSenderMobile() {
        return senderMobile;
    }

    public long getReceiverMobile() {
        return receiverMobile;
    }

    public String getSenderBank() {
        return senderBank;
    }

    public String getReceiverBank() {
        return receiverBank;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }
}