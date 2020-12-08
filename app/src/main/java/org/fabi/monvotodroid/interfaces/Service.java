package org.fabi.monvotodroid.interfaces;
import org.fabi.monvotodroid.exceptions.*;
import org.fabi.monvotodroid.model.VDQuestion;
import org.fabi.monvotodroid.model.VDVote;
import java.util.List;
import java.util.Map;

public interface Service {

    void ajoutQuestion(VDQuestion question) throws IdNonNullException, QuestionIdentiqueException, QuestionTailleMauvaise, QuestionNullException, ContenuIdentiqueException;

    void ajoutVote(VDVote vote) throws VoteNullException, IndiceTailleException, VoteDoubleException, QuestionNonTrouvableException;

    List<VDQuestion> questionsParNombreVotes(); //parcourir toute les questions et créer une liste de VDQuestion qui on x nombre de vote

    Map<Integer, Integer> distributionPour(VDQuestion question);

    double moyennePour(VDQuestion question) throws QuestionAucunVoteException;

    double ecartTypePour(VDQuestion question) throws QuestionAucunVoteException;

    String nomEtudiant();
}

