package org.fabi.monvotodroid.fabi.TestBD;

import android.app.Application;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.fabi.monvotodroid.exceptions.*;
import org.fabi.monvotodroid.impl.ServiceImplimentation;
import org.fabi.monvotodroid.interfaces.Service;
import org.fabi.monvotodroid.model.VDQuestion;
import org.fabi.monvotodroid.model.VDVote;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class TestDistribution
{
    Context mContext = ApplicationProvider.getApplicationContext();

    @Test
    //Je créé des votes avec différents indices pour ensuite calculer la distribution à partir d'une question
    public void TestDistribution() throws VoteDoubleException, VoteNullException, IndiceTailleException, QuestionNonTrouvableException, ContenuIdentiqueException, IdNonNullException, QuestionTailleMauvaise, QuestionNullException, QuestionIdentiqueException {
        

        Service service = new ServiceImplimentation(this.mContext);
        VDQuestion question = new VDQuestion("Quelle est la question?");
        service.ajoutQuestion(question);
        VDVote vote1 = new VDVote(0,"Maurice", 2);
        service.ajoutVote(vote1);
        VDVote vote2 = new VDVote(0,"Pedro", 4);
        service.ajoutVote(vote2);
        VDVote vote3 = new VDVote(0,"Caroline", 4);
        service.ajoutVote(vote3);
        VDVote vote4 = new VDVote(0,"Bastien", 1);
        service.ajoutVote(vote4);
        VDVote vote5 = new VDVote(0,"Chantal", 4);
        service.ajoutVote(vote5);
        VDVote vote6 = new VDVote(0,"Corneille", 2);
        service.ajoutVote(vote6);

        Map<Integer, Integer> map = service.distributionPour(question);

        Assert.assertEquals("1 pour 1/5 ; 2 pour 2/5 ; 0 pour 3/5 ; 3 pour 4/5 ; 0 pour 5/5 ; ", map.get(1) + " pour 1/5 ; " + map.get(2) + " pour 2/5 ; " + map.get(3) + " pour 3/5 ; " + map.get(4) + " pour 4/5 ; " + map.get(5) + " pour 5/5 ; ");

    }


}
