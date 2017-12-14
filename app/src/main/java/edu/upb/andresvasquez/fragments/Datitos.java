package edu.upb.andresvasquez.fragments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by andresvasquez on 12/14/17.
 */

public class Datitos {

    @Expose
    private String name;

    @SerializedName("progress")
    private int progreso;

    @Expose
    private boolean sucess;

    @Expose
    private long timestamp;

    @Expose
    private List<Datitos> datitos;


    public Datitos(String name, int progreso) {
        this.name = name;
        this.progreso = progreso;
        this.sucess = false;
        this.setTimestamp(Calendar.getInstance().getTimeInMillis());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Datitos> getDatitos() {
        return datitos;
    }

    public void setDatitos(List<Datitos> datitos) {
        this.datitos = datitos;
    }
}
