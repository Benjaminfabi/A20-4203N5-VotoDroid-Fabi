package org.fabi.monvotodroid.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import org.fabi.monvotodroid.model.VDQuestion;
import org.fabi.monvotodroid.model.VDVote;

import java.util.List;

@Dao
public abstract class DemoDAO
{
    //GET ALL QUESTIONS
    @Query("SELECT * FROM VDQuestion")
    public abstract List<VDQuestion> toutesLesQuestion();

    //CREATE A QUESTION
    @Insert
    public abstract Long creerVDQuestion(VDQuestion question);
    //GET ALL VOTES FOR QUESTION
    @Query("SELECT * FROM VDQuestion WHERE id = :idparent")
    public abstract List<VDVote> getListeDeVoteParQuestion(Integer idparent);
    //Creer Question
    @Insert
    public abstract Long creerVDote(VDVote vote);
    //questionsParNombreVotes
    @Query("SELECT * FROM VDQuestion ORDER BY ")
    public abstract List<VDQuestion> questionsParNombreVotes(Integer idparent);







}
