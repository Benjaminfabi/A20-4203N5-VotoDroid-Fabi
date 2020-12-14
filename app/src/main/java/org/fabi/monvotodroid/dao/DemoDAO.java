package org.fabi.monvotodroid.dao;


import android.content.Intent;

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
    @Query("SELECT * FROM VDVote WHERE questionId = :idparent")
    public abstract List<VDVote> getListeDeVoteParQuestion(Integer idparent);
    //Creer Question
    @Insert
    public abstract Long creerVDVote(VDVote vote);

    //nombreVotesQuestions
    @Query("SELECT * FROM VDVote WHERE questionId = :idparent")
    public abstract List<VDVote> nombredeVote(Integer idparent);

    @Query("SELECT * FROM VDQuestion WHERE Contenu = :contenu")
    public abstract VDQuestion QuestionParContenu(String contenu);
    @Query("DELETE FROM vdquestion WHERE id = :id")
    public abstract void SupprimeQuestionsSansVotes(Integer id);
    @Query("DELETE FROM VDVote")
    public abstract void SupprimeVotes();











}
