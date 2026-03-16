package p1;

import java.time.LocalDateTime;

public class Transaction {

    private long otherMobile;
    private double amount;
    private String type;
    private LocalDateTime time;

    public Transaction(long otherMobile, double amount, String type) {
        this.otherMobile = otherMobile;
        this.amount = amount;
        this.type = type;
        this.time = LocalDateTime.now();
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