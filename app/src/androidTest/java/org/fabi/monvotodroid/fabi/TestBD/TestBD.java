package org.fabi.monvotodroid.fabi.TestBD;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.fabi.monvotodroid.dao.MaBD;
import org.fabi.monvotodroid.exceptions.ContenuIdentiqueException;
import org.fabi.monvotodroid.exceptions.IdNonNullException;
import org.fabi.monvotodroid.exceptions.QuestionIdentiqueException;
import org.fabi.monvotodroid.exceptions.QuestionNullException;
import org.fabi.monvotodroid.exceptions.QuestionTailleMauvaise;
import org.fabi.monvotodroid.impl.ServiceImplimentation;
import org.fabi.monvotodroid.interfaces.Service;
import org.fabi.monvotodroid.model.VDQuestion;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestBD {

    @Test
    public void testPeu() throws ContenuIdentiqueException, IdNonNullException, QuestionTailleMauvaise, QuestionNullException, QuestionIdentiqueException {
        Context context = ApplicationProvider.getApplicationContext();
        MaBD bd = Room.inMemoryDatabaseBuilder(context, MaBD.class).build();
        Service service = new ServiceImplimentation(bd);
        for (int i = 0 ; i < 10 ; i++ ) {
            VDQuestion a = new VDQuestion();
            a.setContenu("Testcontenu"+i);
            service.ajoutQuestion(a);

        }
        List<VDQuestion> albums = bd.dao().toutesLesQuestion();
        Assert.assertEquals(10, albums.size());
        bd.close();
    }

    @Test
    public void textBeaucoup() {
        Context context = ApplicationProvider.getApplicationContext();
        MaBD bd = Room.inMemoryDatabaseBuilder(context, MaBD.class).build();
        Service service = new ServiceImplimentation(bd);
        for (int i = 0 ; i < 100 ; i++ ) {
            VDQuestion a = new VDQuestion();
            a.setContenu("Testcontenu");
            bd.dao().creerVDQuestion(a);
        }
        List<VDQuestion> albums = bd.dao().toutesLesQuestion();
        assertEquals(100, albums.size());
        bd.close();
    }
}
