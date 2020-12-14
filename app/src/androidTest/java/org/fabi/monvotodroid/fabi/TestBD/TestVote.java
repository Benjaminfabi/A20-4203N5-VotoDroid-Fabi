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

public class TestVote
{
    Context mContext = ApplicationProvider.getApplicationContext();
    MaBD bd =  Room.databaseBuilder(mContext, MaBD.class, "TestVote").allowMainThreadQueries().build();
    //TESTS POUR VDVOTE
    @Test(expected = VoteNullException.class)
    public void VoteNull() throws VoteNullException, IndiceTailleException, VoteDoubleException, QuestionNonTrouvableException {
        Context context = ApplicationProvider.getApplicationContext();
        MaBD bd = Room.inMemoryDatabaseBuilder(context, MaBD.class).build();
        Service service = new ServiceImplimentation(bd);
        service.ajoutVote(null);
    }
    @Test(expected = IndiceTailleException.class)
    public void IndiceTailleTropCourt() throws VoteNullException, IndiceTailleException, VoteDoubleException, QuestionNonTrouvableException, ContenuIdentiqueException, IdNonNullException, QuestionTailleMauvaise, QuestionNullException, QuestionIdentiqueException {
        VDQuestion question = new VDQuestion("jaimelesvaches");

        Context context = ApplicationProvider.getApplicationContext();
        MaBD bd = Room.inMemoryDatabaseBuilder(context, MaBD.class).build();
        Service service = new ServiceImplimentation(bd);
        service.ajoutQuestion(question);
        VDVote vote = new VDVote(1,"allllalo",-1);
        service.ajoutVote(vote);
    }
    @Test(expected = IndiceTailleException.class)
    public void IndiceTailleTropLong() throws VoteNullException, IndiceTailleException, VoteDoubleException, QuestionNonTrouvableException {
        Context context = ApplicationProvider.getApplicationContext();
        MaBD bd = Room.inMemoryDatabaseBuilder(context, MaBD.class).build();
        Service service = new ServiceImplimentation(bd);
        service.ajoutVote(new VDVote(3,"allllalo",6));
    }
    @Test(expected = VoteDoubleException.class)
    public void DoubleVote() throws VoteNullException, IndiceTailleException, VoteDoubleException, QuestionNonTrouvableException, ContenuIdentiqueException, IdNonNullException, QuestionTailleMauvaise, QuestionNullException, QuestionIdentiqueException {
        Context context = ApplicationProvider.getApplicationContext();
        MaBD bd = Room.inMemoryDatabaseBuilder(context, MaBD.class).build();
        Service service = new ServiceImplimentation(bd);
        VDQuestion question = new VDQuestion("contenu");
        service.ajoutQuestion(question);
        VDVote voteMaude = new VDVote(question.getId(), "Maude",3);
        VDVote votemaude = new VDVote(question.getId(),"maude",3);
        service.ajoutVote(voteMaude);
        service.ajoutVote(votemaude);
    }
    @Test(expected = QuestionNonTrouvableException.class)
    public void QuestionNonTrouvable() throws VoteNullException, IndiceTailleException, VoteDoubleException, QuestionNonTrouvableException, ContenuIdentiqueException, IdNonNullException, QuestionTailleMauvaise, QuestionNullException, QuestionIdentiqueException { //Le vote n'est pas rattaché à une question!
        Context context = ApplicationProvider.getApplicationContext();
        MaBD bd = Room.inMemoryDatabaseBuilder(context, MaBD.class).build();
        Service service = new ServiceImplimentation(bd);
        service.ajoutQuestion(new VDQuestion("TESTQUESTION"));
        VDVote vote = new VDVote(2,"maude",3); //QUESITON ID : pour voir si il va trouver la question avec l'id 2
        service.ajoutVote(vote);
    }
    @Test
    public void AjouVoteAQuestion() throws VoteNullException, IndiceTailleException, VoteDoubleException, QuestionNonTrouvableException, ContenuIdentiqueException, IdNonNullException, QuestionTailleMauvaise, QuestionNullException, QuestionIdentiqueException { //Le vote n'est pas rattaché à une question!
        Context context = ApplicationProvider.getApplicationContext();
        MaBD bd = Room.inMemoryDatabaseBuilder(context, MaBD.class).build();
        Service service = new ServiceImplimentation(bd);
        VDQuestion question = new VDQuestion("TESTQUESTION");
        service.ajoutQuestion(question);
        VDVote vote = new VDVote(question.getId(), "maude",3);
        service.ajoutVote(vote);
        Assert.assertEquals(1, bd.dao().getListeDeVoteParQuestion(question.getId()).size());
    }
}
