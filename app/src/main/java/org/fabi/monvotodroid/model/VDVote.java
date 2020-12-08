package org.fabi.monvotodroid.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = VDQuestion.class ,
        parentColumns = "id",
        childColumns = "questionId"),
        indices = {@Index("questionId")})

public class VDVote
{
    public Integer getId() {
        return id;
    }

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo
    private String nomVoteur;
    @ColumnInfo
    private Integer questionId;
    @ColumnInfo
    private Integer indice;
    public VDVote()
    {

    }

    public VDVote(Integer questionId, String nomVoteur, Integer indice)
    {
        setIndice(indice);
        setNomVoteur(nomVoteur);
        setQuestionId(questionId);

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
    public Integer getQuestionId() {
        return questionId;
    }

    public String getNomVoteur() {
        return nomVoteur;
    }
    public void setNomVoteur(String nomVoteur) {
        this.nomVoteur = nomVoteur;
    }

    public Integer getIndice() {
        return indice;
    }
    public void setIndice(Integer indice) {
        this.indice = indice;
    }



}
