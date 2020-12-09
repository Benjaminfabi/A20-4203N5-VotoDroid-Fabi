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

public class TestEcartType
{
    Context mContext = ApplicationProvider.getApplicationContext();

    //Assert Equals avec l'exemple de l'énoncé du TP
    @Test
    public void TestEcartType() throws VoteDoubleException, VoteNullException, IndiceTailleException, QuestionNonTrouvableException, ContenuIdentiqueException, IdNonNullException, QuestionTailleMauvaise, QuestionNullException, QuestionIdentiqueException, QuestionAucunVoteException
        {

            Context context = ApplicationProvider.getApplicationContext();
            MaBD bd = Room.inMemoryDatabaseBuilder(context, MaBD.class).build();
            Service service = new ServiceImplimentation(bd);
            VDQuestion question = new VDQuestion("Quelle est la question?");
            service.ajoutQuestion(question);
            VDVote vote1 = new VDVote(1, "Maurice", 2);
            service.ajoutVote(vote1);
            VDVote vote2 = new VDVote(1, "Pedro", 2);
            service.ajoutVote(vote2);
            VDVote vote3 = new VDVote(1, "Caroline", 5);
            service.ajoutVote(vote3);
            Assert.assertEquals(1.41, service.ecartTypePour(question), 1);
        }
    //Si la question n'a pas de vote, la fonction de l'écart type ne fonctionnera pas et lèvera une exception attendue
    @Test(expected = QuestionAucunVoteException.class)
    public void QuestionAucunVote() throws VoteDoubleException, VoteNullException, IndiceTailleException, QuestionNonTrouvableException, ContenuIdentiqueException, IdNonNullException, QuestionTailleMauvaise, QuestionNullException, QuestionIdentiqueException, QuestionAucunVoteException {
        Context context = ApplicationProvider.getApplicationContext();
        MaBD bd = Room.inMemoryDatabaseBuilder(context, MaBD.class).build();
        Service service = new ServiceImplimentation(bd);
        VDQuestion question = new VDQuestion("Quelle est la question?");
        service.ajoutQuestion(question);
        service.moyennePour(question);
    }
}
