package edu.upb.andresvasquez.fragments;

import com.google.gson.annotations.Expose;

/**
 * Created by andresvasquez on 12/15/17.
 */

public class Pokemon {

    @Expose
    private int id;

    @Expose
    private String name;

    @Expose
    private Sprite sprites;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sprite getSprites() {
        return sprites;
    }

    public void setSprites(Sprite sprites) {
        this.sprites = sprites;
    }
}
