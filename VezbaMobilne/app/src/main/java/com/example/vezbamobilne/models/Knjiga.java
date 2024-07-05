package com.example.vezbamobilne.models;

public class Knjiga {
    private int ID;
    private String naslov;
    private int brStranica;
    private String povez;


    public Knjiga() {
    }

    public Knjiga(int ID, String naslov, int brStranica, String povez) {
        this.ID = ID;
        this.naslov = naslov;
        this.brStranica = brStranica;
        this.povez = povez;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public int getBrStranica() {
        return brStranica;
    }

    public void setBrStranica(int brStranica) {
        this.brStranica = brStranica;
    }

    public String getPovez() {
        return povez;
    }

    public void setPovez(String povez) {
        this.povez = povez;
    }
}
