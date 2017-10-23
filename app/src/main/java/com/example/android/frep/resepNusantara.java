package com.example.android.frep;

/**
 * Created by Daniel Go on 23 Oct 2017.
 */

public class resepNusantara {

    private int id;
    private String keteranganResep;
    private String asalDaerah;
    private String bahan;
    private String cara;
    private int rating;

    public resepNusantara(int id, String keteranganResep, String asalDaerah, String bahan, String cara, int rating) {
        this.id = id;
        this.keteranganResep = keteranganResep;
        this.asalDaerah = asalDaerah;
        this.bahan = bahan;
        this.cara = cara;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getKeteranganResep() {
        return keteranganResep;
    }

    public String getAsalDaerah() {
        return asalDaerah;
    }

    public String getBahan() {
        return bahan;
    }

    public String getCara() {
        return cara;
    }

    public int getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKeteranganResep(String keteranganResep) {
        this.keteranganResep = keteranganResep;
    }

    public void setAsalDaerah(String asalDaerah) {
        this.asalDaerah = asalDaerah;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    public void setCara(String cara) {
        this.cara = cara;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
