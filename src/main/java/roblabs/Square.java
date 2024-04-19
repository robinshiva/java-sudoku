package roblabs;

import java.io.Serializable;
import java.util.ArrayList;

public class Square implements Serializable {

    private int bigNumber;
    private ArrayList<Integer> smallNumbers;
    private boolean fixedNumber;
    private boolean isWrong;

    public Square(int bigNumber) {
        this.bigNumber = bigNumber;
        this.smallNumbers = new ArrayList<>();
        this.isWrong = false;
        if (this.bigNumber == 0) {
            this.fixedNumber = false;
        } else {
            this.fixedNumber = true;
        }
    }

    public int getBigNumber() {
        return this.bigNumber;
    }

    public void setBigNumber(int newNumber, boolean isWrong) {
        this.bigNumber = newNumber;
        this.smallNumbers.clear();
        this.isWrong = isWrong;
    }

    public ArrayList<Integer> getSmallNumbers() {
        return this.smallNumbers;
    }

    public void addSmallNumber(int newNumber) {
        this.bigNumber = 0;
        if (this.smallNumbers.contains(newNumber)) {
            for (int i = 0; i < this.smallNumbers.size(); i++) {
                if (this.smallNumbers.get(i) == newNumber) {
                    this.smallNumbers.remove(i);
                }
            }
        } else {
            this.smallNumbers.add(newNumber);
        }
    }

    public void clear() {
        this.bigNumber = 0;
        this.smallNumbers.clear();
        this.isWrong = false;
    }

    public boolean getIsWrong() {
        return this.isWrong;
    }

    public boolean getFixedNumber() {
        return this.fixedNumber;
    }

}
