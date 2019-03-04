package com.hmdapp.finaltailor.Models;

public class Cloth {

    private  int id;
    private int qad;
    private int shana;
    private int baqal;
    private int daman;
    private int shalwar;
    private int pacha;
    private int bar_shalwar;
    private int astin;

    private  Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private int yakhan;
    private  String model,model_dam_astin,model_qot_astin,qad_paty,model_astin,model_yaqa;
    private int dam_astin;

String des;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }




    public int getAstin() {
        return astin;
    }

    public void setAstin(int astin) {
        this.astin = astin;
    }

    public int getDam_astin() {
        return dam_astin;
    }

    public void setDam_astin(int dam_astin) {
        this.dam_astin = dam_astin;
    }



    public int getYakhan() {
        return yakhan;
    }

    public void setYakhan(int yakhan) {
        this.yakhan = yakhan;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQad() {
        return qad;
    }

    public void setQad(int qad) {
        this.qad = qad;
    }

    public int getShana() {
        return shana;
    }

    public void setShana(int shana) {
        this.shana = shana;
    }

    public int getBaqal() {
        return baqal;
    }

    public void setBaqal(int baqal) {
        this.baqal = baqal;
    }

    public int getDaman() {
        return daman;
    }

    public void setDaman(int daman) {
        this.daman = daman;
    }

    public int getShalwar() {
        return shalwar;
    }

    public void setShalwar(int shalwar) {
        this.shalwar = shalwar;
    }

    public int getPacha() {
        return pacha;
    }

    public void setPacha(int pacha) {
        this.pacha = pacha;
    }

    public int getBar_shalwar() {
        return bar_shalwar;
    }

    public void setBar_shalwar(int bar_shalwar) {
        this.bar_shalwar = bar_shalwar;
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
