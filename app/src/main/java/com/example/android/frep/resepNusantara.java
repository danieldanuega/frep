package com.example.android.frep;

import java.io.Serializable;

/**
 * Created by Daniel Go on 23 Oct 2017.
 */

public class resepNusantara implements Serializable {

    private String keteranganResep;
    private String asalDaerah;
    private String bahan;
    private String cara;
    private Long rating;
    private Long id;
    private boolean favourite;
    private String nama;

    public resepNusantara() {
        //required
    }

    public resepNusantara(String asalDaerah, String bahan, String cara, boolean favourite, Long id, String keteranganResep, String nama, Long rating) {
        this.setNama(nama);
        this.keteranganResep = keteranganResep;
        this.asalDaerah = asalDaerah;
        this.bahan = bahan;
        this.cara = cara;
        this.rating = rating;
        this.favourite = favourite;
        this.setId(id);

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

    public void setRating(Long rating) {
        this.rating = rating;
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

    public Long getRating() {
        return rating;
    }

    public boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
