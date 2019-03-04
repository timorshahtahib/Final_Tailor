package com.hmdapp.finaltailor.Models;

public class Payment {


    private int id;
    private String Date;
    private int amount;
    private char cr_db;
    private Order order;
    private String des;
    private int total, reminder;
    private int pish_pardakht;

    public int getPish_pardakht() {
        return pish_pardakht;
    }

    public void setPish_pardakht(int pish_pardakht) {
        this.pish_pardakht = pish_pardakht;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }




    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getReminder() {
        return reminder;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public char getCr_db() {
        return cr_db;
    }

    public void setCr_db(char cr_db) {
        this.cr_db = cr_db;
    }


    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }


    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", Date='" + Date + '\'' +
                ", amount=" + amount +
                ", cr_db=" + cr_db +

                ", des='" + des + '\'' +
                ", total=" + total +
                ", reminder=" + reminder +
                ", pish_pardakht=" + pish_pardakht +
                '}';
    }
}
