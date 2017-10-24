package com.example.android.frep;

/**
 * Created by Daniel Go on 23 Oct 2017.
 */

public class resepNusantara {

    private String id;
    private String keteranganResep;
    private String asalDaerah;
    private String bahan;
    private String cara;
    private String rating;
    private String favourite;
    private String nama;

    public resepNusantara() {
        //required
    }

    public resepNusantara(String nama, String id, String keteranganResep, String asalDaerah, String bahan, String cara, String rating, String favourite) {
        this.setNama(nama);
        this.id = id;
        this.keteranganResep = keteranganResep;
        this.asalDaerah = asalDaerah;
        this.bahan = bahan;
        this.cara = cara;
        this.rating = rating;
        this.favourite = favourite;
    }

    public void setId(String id) {
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

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
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

    public String getRating() {
        return rating;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
