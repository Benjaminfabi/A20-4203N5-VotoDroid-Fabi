package org.fabi.monvotodroid.impl;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.fabi.monvotodroid.dao.MaBD;
import org.fabi.monvotodroid.exceptions.*;
import org.fabi.monvotodroid.interfaces.Service;
import org.fabi.monvotodroid.model.VDQuestion;
import org.fabi.monvotodroid.model.VDVote;
import java.util.*;
public class ServiceImplimentation implements Service {
    Context context;

    MaBD bd;

    public ServiceImplimentation(MaBD bd)
    {
        this.bd = bd;
    }




    //Implementations
    public void ajoutQuestion(VDQuestion question) throws IdNonNullException, QuestionIdentiqueException, QuestionTailleMauvaise, QuestionNullException, ContenuIdentiqueException {
        if (question == null || question.getContenu() == null)
        {
            throw new QuestionNullException();
        }
        if (question.getId() != null) throw new IdNonNullException(); //si il possède déjà un id
        if (question.getContenu().length() < 5 || question.getContenu().length() > 255)
        {
            throw new QuestionTailleMauvaise();
        }
        for (VDQuestion q : bd.dao().toutesLesQuestion())
        {
            if (q.getContenu() == question.getContenu())
            {
                throw new QuestionIdentiqueException();
            }
            if (q.getContenu().toLowerCase().equals(question.getContenu().toLowerCase()))
            {
                throw new ContenuIdentiqueException();
            }

        }
        //l'id est donné par la méthode en commençant par un id de 0 et ensuite ++
        Long id = bd.dao().creerVDQuestion(question);
        question.setId(id.intValue());

        //bd.dao().toutesLesQuestion().add(question.getId(), question); // je rajoute la question à l'index de son id

    }

    public void ajoutVote(VDVote vote) throws VoteNullException, IndiceTailleException, VoteDoubleException, QuestionNonTrouvableException {
        if (vote == null || vote.getNomVoteur() == null)
        {
            throw new VoteNullException();
        }
        if (vote.getIndice() > 5 || vote.getIndice() < -1)
        {
            throw new IndiceTailleException();
        }
        int i = 0;
        for ( i = 0; i < bd.dao().toutesLesQuestion().size(); i++ )
        {
            if (bd.dao().toutesLesQuestion().get(i).getId() == vote.getQuestionId())
            {
                break;
            }
        }
        if (i >= bd.dao().toutesLesQuestion().size()){
            throw new QuestionNonTrouvableException();
        }
        VDQuestion question = bd.dao().toutesLesQuestion().get(i);

        for (VDVote v : bd.dao().getListeDeVoteParQuestion(question.getId()))
        {
            if (v.getNomVoteur().toLowerCase().compareTo(vote.getNomVoteur().toLowerCase()) == 0)
            {
                throw new VoteDoubleException();
            }
        }
        Long id = bd.dao().creerVDVote(vote);
        vote.setId(id.intValue());



    }
    public List<VDQuestion> questionsParNombreVotes()
    {
        List<VDQuestion> listeQuestionCopie = bd.dao().toutesLesQuestion();
        Collections.sort(listeQuestionCopie, new Comparator<VDQuestion>() {
            @Override
            public int compare(VDQuestion o1, VDQuestion o2) {
                int nb1 = nombreVotesPour(o1);
                int nb2 = nombreVotesPour(o2);
                if (nb1 > nb2) return -1;
                return 0;
            }
        });
        return listeQuestionCopie;
    }

    private int nombreVotesPour(VDQuestion o1) {
        return bd.dao().nombredeVote(o1.getId()).size();
    }

    public Map<Integer, Integer> distributionPour(VDQuestion question) {
        Map<Integer, Integer> distributionMap = new HashMap<Integer, Integer>();
        Integer noteMax = 5;

        Integer[] tab = new Integer[6];
        tab[0] = 0;
        tab[1] = 0;
        tab[2] = 0;
        tab[3] = 0;
        tab[4] = 0;
        tab[5] = 0;
        for (VDVote v : bd.dao().getListeDeVoteParQuestion(question.getId())) {
            tab[v.getIndice()]++;
        }
        for (int i = 0; i <= noteMax; i++) {
            distributionMap.put(i, tab[i]); //Ajouter les donnés dans le HashMap
        }
        return distributionMap;
    }

    public double moyennePour(VDQuestion question) throws QuestionAucunVoteException {
        if (bd.dao().getListeDeVoteParQuestion(question.getId()).size() <= 0 )
        {
            throw new QuestionAucunVoteException();
        }
        Map<Integer, Integer> map = distributionPour(question);
        Integer somme =  1 * map.get(1) + 2 * map.get(2) + 3 * map.get(3) + 4 *  map.get(4) + 5 * map.get(5);
        if (somme > 0)
        {
            return ((double) somme / bd.dao().getListeDeVoteParQuestion(question.getId()).size());
        }
        return 0;
    }

    public double ecartTypePour(VDQuestion question) throws QuestionAucunVoteException {
        if (bd.dao().getListeDeVoteParQuestion(question.getId()).size() <= 0 )
        {
            throw new QuestionAucunVoteException();
        }
        double[] tab = new double[bd.dao().getListeDeVoteParQuestion(question.getId()).size()];
        Integer index = 0;
        for (VDVote v : bd.dao().getListeDeVoteParQuestion(question.getId()))
        {
            tab[index] = v.getIndice();
            index++;
        }

        return calculET(tab);

    }

    private double calculET(double tab[]) //Elle est privée car je l'ai inventé pour que ça soit plus simple pour moi
    {
        double somme = 0.0, ecartType = 0.0;
        int taille = tab.length;

        for(double nb : tab) {
            somme += nb;
        }

        double div = somme/taille;

        for(double nb: tab) {
            ecartType += Math.pow(nb - div, 2);
        }

        return Math.sqrt(ecartType/taille);

    }
    public String nomEtudiant() {
        return "Fabi Benjamin";
    }
}
