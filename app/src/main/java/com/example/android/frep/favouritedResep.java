package com.example.android.frep;

/**
 * Created by Daniel Go on 16 Nov 2017.
 */

public class favouritedResep {

    private String email;
    private String resepId;

    public favouritedResep() {
    }

    public favouritedResep(String email, String key) {
        this.email = email;
        this.resepId = key;
    }

    public String getKey() {
        return resepId;
    }

    public void setKey(String key) {
        this.resepId = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
