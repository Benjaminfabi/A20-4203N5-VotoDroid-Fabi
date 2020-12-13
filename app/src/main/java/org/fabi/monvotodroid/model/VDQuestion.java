package org.fabi.monvotodroid.model;

import android.service.controls.templates.ControlTemplate;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.fabi.monvotodroid.model.VDVote;

import java.util.*;
@Entity
public class VDQuestion {


    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(name = "Contenu")
    private String contenu;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public VDQuestion() {

    }
    public VDQuestion(String Contenu) {
    setContenu(Contenu);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VDQuestion that = (VDQuestion) o;
        return contenu.equals(that.contenu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contenu);
    }

}