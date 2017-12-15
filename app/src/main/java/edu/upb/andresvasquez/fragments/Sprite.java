package edu.upb.andresvasquez.fragments;

import com.google.gson.annotations.Expose;

/**
 * Created by andresvasquez on 12/15/17.
 */

public class Sprite {

    @Expose
    private String front_default;

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }
}
