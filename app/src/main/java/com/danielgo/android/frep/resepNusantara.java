package com.danielgo.android.frep;

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
    private Long rating2;
    private Long rating3;
    private String id;
    private String nama;
    private String imgUrl;

    public resepNusantara() {
        //required for Firebase
    }



    public resepNusantara(String asalDaerah, String bahan, String cara, String id, String imgUrl, String keteranganResep, String nama, Long rating, Long rating2, Long rating3) {
        this.setNama(nama);
        this.keteranganResep = keteranganResep;
        this.asalDaerah = asalDaerah;
        this.bahan = bahan;
        this.cara = cara;
        this.rating = rating;
        this.setId(id);
        this.rating2 = rating2;
        this.rating3 = rating3;
        this.imgUrl = imgUrl;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRating2() {
        return rating2;
    }

    public void setRating2(Long rating2) {
        this.rating2 = rating2;
    }

    public Long getRating3() {
        return rating3;
    }

    public void setRating3(Long rating3) {
        this.rating3 = rating3;
    }
}
