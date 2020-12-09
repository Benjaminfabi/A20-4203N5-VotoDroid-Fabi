package org.fabi.monvotodroid.fabi.TestBD;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.fabi.monvotodroid.exceptions.*;
import org.fabi.monvotodroid.impl.ServiceImplimentation;
import org.fabi.monvotodroid.interfaces.Service;
import org.fabi.monvotodroid.model.VDQuestion;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestQuestion
{
    Context mContext = ApplicationProvider.getApplicationContext();
    //TESTS POUR VDQUESTION
    @Test(expected = QuestionNullException.class)
    public void QuesitonEstNull() throws IdNonNullException, QuestionTailleMauvaise, QuestionNullException, QuestionIdentiqueException, ContenuIdentiqueException {

        Service service = new ServiceImplimentation(this.mContext);
        service.ajoutQuestion(null);
    }
    @Test(expected = IdNonNullException.class)
    public void IdNonNull() throws IdNonNullException, QuestionIdentiqueException, QuestionTailleMauvaise, QuestionNullException, ContenuIdentiqueException {
        //On set l'id pour qu'il soit non null lors de sa création. Expected : IdNonNullException()
        Service service = new ServiceImplimentation(this.mContext);
        VDQuestion question = new VDQuestion("Allo");
        question.setId(12);
        service.ajoutQuestion(question);

    }
    @Test(expected = QuestionTailleMauvaise.class)
    public void TailleQuestionTropCourte() throws QuestionTailleMauvaise, QuestionIdentiqueException, IdNonNullException, QuestionNullException, ContenuIdentiqueException {
        Service service = new ServiceImplimentation(this.mContext);
        service.ajoutQuestion(new VDQuestion("allo"));
    }
    @Test(expected = QuestionTailleMauvaise.class)
    public void TailleQuestionTropLongue() throws QuestionTailleMauvaise, QuestionIdentiqueException, IdNonNullException, QuestionNullException, ContenuIdentiqueException {
        Service service = new ServiceImplimentation(this.mContext);
        service.ajoutQuestion(new VDQuestion("V0ywCnSdfh4o70ehGg8VJrzfT0Ye36Rme8WSbK84vDckO6sV8sbIN8XQFe5yxTYbkA4P6wvL6Sg3vvtPgOQnZTiKO844HAuZ6WtzbB82tFl5wIQmXmiXPUBamjPzFFj9iI3YGmsem3kmv5uoLtDLoYw3EKMab8SZ5JlrQuudCEbVhxkITJc4ZVe8RqVvzMWg9dGdD37JAzG9DWC0BKlPDB2c4BdrEzimjryoD0Q8vWUKZNe9tkqP7iObtuXyuknG"));
    }
    @Test(expected = ContenuIdentiqueException.class)
    public void ContenuSimilaire() throws ContenuIdentiqueException, IdNonNullException, QuestionTailleMauvaise, QuestionNullException, QuestionIdentiqueException {
        Service service = new ServiceImplimentation(this.mContext);
        VDQuestion questionbbb = new VDQuestion("bbbbb");
        VDQuestion questionBBB = new VDQuestion("BBBBB");
        service.ajoutQuestion(questionbbb);
        service.ajoutQuestion(questionBBB);
    }









}
