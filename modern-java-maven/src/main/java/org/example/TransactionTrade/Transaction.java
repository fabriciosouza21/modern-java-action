package org.example.TransactionTrade;

public class Transaction {

    private final Trader trader;
    private final int year;
    private final int value;

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return this.trader;
    }

    public int getYear() {
        return this.year;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return "{" + this.trader + ", " +
                "year: " + this.year + ", " +
                "value:" + this.value + "}";
    }

    boolean isYear(int year) {
        return this.year == year;
    }

    public String getCity() {
        return this.trader.getCity();
    }


    public boolean isCity(String city) {
        return this.trader.getCity().equalsIgnoreCase(city);
    }
}
