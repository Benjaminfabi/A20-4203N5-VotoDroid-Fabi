package org.fabi.monvotodroid.fabi.TestBD;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.fabi.monvotodroid.dao.MaBD;
import org.fabi.monvotodroid.exceptions.*;
import org.fabi.monvotodroid.impl.ServiceImplimentation;
import org.fabi.monvotodroid.interfaces.Service;
import org.fabi.monvotodroid.model.VDQuestion;
import org.fabi.monvotodroid.model.VDVote;
import org.junit.Assert;
import org.junit.Test;


public class TestQuestionParNbDeVote
{

    Context mContext = ApplicationProvider.getApplicationContext();
    MaBD bd =  Room.databaseBuilder(mContext, MaBD.class, "BdTestQuestionParNbDeVote").allowMainThreadQueries().build();
    @Test
    public void TestListe() throws ContenuIdentiqueException, IdNonNullException, QuestionTailleMauvaise, QuestionNullException, QuestionIdentiqueException, VoteDoubleException, VoteNullException, IndiceTailleException, QuestionNonTrouvableException {
        Service service = new ServiceImplimentation(this.mContext);
        VDQuestion question1 = new VDQuestion("AAAAA");
        VDQuestion question2 = new VDQuestion("JJJJJ");
        VDQuestion question3 = new VDQuestion("kkkkk");
        service.ajoutQuestion(question1);
        service.ajoutQuestion(question2);
        service.ajoutQuestion(question3);
        VDVote voteKevin = new VDVote(0,"Kevin", 3);
        service.ajoutVote(voteKevin);
        VDVote voteJessica = new VDVote(1, "Jessica", 5);
        service.ajoutVote(voteJessica);
        VDVote voteBen = new VDVote(1, "Ben", 5);
        service.ajoutVote(voteBen);
        VDVote voteTristan = new VDVote(2, "Tristan", 5);
        service.ajoutVote(voteTristan);
        VDVote voteJacques = new VDVote(2, "Jacques", 5);
        service.ajoutVote(voteJacques);
        VDVote votePrestbite = new VDVote(0, "Prestbite", 5);
        service.ajoutVote(votePrestbite);
        VDVote voteRomi = new VDVote(0, "Romi", 5);
        service.ajoutVote(voteRomi);
        String resultat = "";
        for (VDQuestion q : service.questionsParNombreVotes())
        {
            resultat += "Nombre de votes : " + bd.dao().getListeDeVoteParQuestion(q.getId()).size() + " " + q.getContenu() + " ";
        }
        Assert.assertEquals("Nombre de votes : 3 AAAAA Nombre de votes : 2 JJJJJ Nombre de votes : 2 kkkkk ", resultat);
    }
}
