package p1;

import java.io.Serializable;

public class Bank implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int accLength;

    public Bank(String name, int accLength) {
        this.name = name;
        this.accLength = accLength;
    }

    public String getName() {
        return name;
    }

    public int getAccLength() {
        return accLength;
    }
}