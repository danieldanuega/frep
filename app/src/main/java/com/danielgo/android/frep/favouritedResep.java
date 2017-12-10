package com.danielgo.android.frep;

/**
 * Created by Daniel Go on 16 Nov 2017.
 */

public class favouritedResep {

    private String favId;
    private String email;
    private String resepId;

    public favouritedResep() {
    }

    public favouritedResep(String email, String favId, String resepId) {
        this.email = email;
        this.resepId = resepId;
        this.favId = favId;
    }

    public String getResepId() {
        return resepId;
    }

    public void setResepId(String key) {
        this.resepId = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFavId() {
        return favId;
    }

    public void setFavId(String favId) {
        this.favId = favId;
    }
}
