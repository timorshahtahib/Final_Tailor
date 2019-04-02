package com.hmdapp.finaltailor.Models;

public class Cloth {

    private int id;
    private double qad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private double shana;
    private double baqal;
    private double daman;

    public double getQad() {
        return qad;
    }

    public void setQad(double qad) {
        this.qad = qad;
    }

    public double getShana() {
        return shana;
    }

    public void setShana(double shana) {
        this.shana = shana;
    }

    public double getBaqal() {
        return baqal;
    }

    public void setBaqal(double baqal) {
        this.baqal = baqal;
    }

    public double getDaman() {
        return daman;
    }

    public void setDaman(double daman) {
        this.daman = daman;
    }

    public double getShalwar() {
        return shalwar;
    }

    public void setShalwar(double shalwar) {
        this.shalwar = shalwar;
    }

    public double getPacha() {
        return pacha;
    }

    public void setPacha(double pacha) {
        this.pacha = pacha;
    }

    public double getBar_shalwar() {
        return bar_shalwar;
    }

    public void setBar_shalwar(double bar_shalwar) {
        this.bar_shalwar = bar_shalwar;
    }

    public double getAstin() {
        return astin;
    }

    public void setAstin(double astin) {
        this.astin = astin;
    }

    public double getYakhan() {
        return yakhan;
    }

    public void setYakhan(double yakhan) {
        this.yakhan = yakhan;
    }

    public double getDam_astin() {
        return dam_astin;
    }

    public void setDam_astin(double dam_astin) {
        this.dam_astin = dam_astin;
    }

    private double shalwar;
    private double pacha;
    private double bar_shalwar;
    private double astin;

    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private double yakhan;
    private String model, model_dam_astin, model_qot_astin, qad_paty, model_astin, model_yaqa;
    private double dam_astin;

    String des;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }



    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel_dam_astin() {
        return model_dam_astin;
    }

    public void setModel_dam_astin(String model_dam_astin) {
        this.model_dam_astin = model_dam_astin;
    }

    public String getModel_qot_astin() {
        return model_qot_astin;
    }

    public void setModel_qot_astin(String model_qot_astin) {
        this.model_qot_astin = model_qot_astin;
    }

    public String getQad_paty() {
        return qad_paty;
    }

    public void setQad_paty(String qad_paty) {
        this.qad_paty = qad_paty;
    }

    public String getModel_astin() {
        return model_astin;
    }

    public void setModel_astin(String model_astin) {
        this.model_astin = model_astin;
    }

    public String getModel_yaqa() {
        return model_yaqa;
    }

    public void setModel_yaqa(String model_yaqa) {
        this.model_yaqa = model_yaqa;
    }
}
