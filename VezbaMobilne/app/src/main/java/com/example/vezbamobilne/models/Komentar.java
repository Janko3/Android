package com.example.vezbamobilne.models;

public class Komentar {
    private int id;
    private String username;
    private String komentar;
    private Knjiga knjiga;

    public Komentar(int id, String username, String komentar, Knjiga knjiga) {
        this.id = id;
        this.username = username;
        this.komentar = komentar;
        this.knjiga = knjiga;
    }

    public Komentar(String komentar, String username) {
        this.komentar = komentar;
        this.username = username;

    }

    public Komentar() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    @Override
    public String toString() {
        return "Komentar{" +
                "username='" + username + '\'' +
                ", komentar='" + komentar + '\'' +
                '}';
    }
}
