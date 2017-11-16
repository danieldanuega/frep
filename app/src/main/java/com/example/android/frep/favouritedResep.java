package com.example.android.frep;

/**
 * Created by Daniel Go on 16 Nov 2017.
 */

public class favouritedResep {

    private String email;
    private Long key;

    public favouritedResep() {
    }

    public favouritedResep(String email, Long key) {
        this.email = email;
        this.key = key;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
