package org.fabi.monvotodroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import org.fabi.monvotodroid.dao.MaBD;
import org.fabi.monvotodroid.exceptions.IndiceTailleException;
import org.fabi.monvotodroid.exceptions.QuestionNonTrouvableException;
import org.fabi.monvotodroid.exceptions.VoteDoubleException;
import org.fabi.monvotodroid.exceptions.VoteNullException;
import org.fabi.monvotodroid.impl.ServiceImplimentation;
import org.fabi.monvotodroid.interfaces.Service;
import org.fabi.monvotodroid.model.VDQuestion;
import org.fabi.monvotodroid.model.VDVote;

import java.util.ArrayList;
import java.util.List;

public class CreerVoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creer_vote_activity);
        MaBD bd = Room.databaseBuilder(getApplicationContext(), MaBD.class,"bdVotoDroid").allowMainThreadQueries().build();
        Intent i = getIntent();
        Integer idQuestion = bd.dao().QuestionParContenu(i.getStringExtra("Contenu")).getId();
        TextView textView = findViewById(R.id.txtQuestionAVoter);
        textView.setText(i.getStringExtra("Contenu"));
        VDVote vote = new VDVote();
        vote.setIndice(0);
        vote.setQuestionId(idQuestion);
        //OnclickListeners pour les views RatingBar et EditText
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vote.setIndice((int)rating);
            }
        });


        EditText editText = (EditText) findViewById(R.id.textNomVoteur);

        Button button = findViewById(R.id.btnAjouterVote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vote.setNomVoteur(editText.getText().toString());
                Service service = new ServiceImplimentation(bd);
                if (editText.getText().toString().isEmpty() || editText.getText().toString() == "Nom du voteur")
                {
                    Toast.makeText(getApplicationContext(), "Le nom voteur est obligatoire", Toast.LENGTH_LONG).show();
                }
                else{
                    try {
                        service.ajoutVote(vote);
                    } catch (VoteNullException e) {
                        Toast.makeText(getApplicationContext(), "Erreur : Le vote est null", Toast.LENGTH_LONG).show();
                        return;
                    } catch (IndiceTailleException e) {
                        Toast.makeText(getApplicationContext(), "Erreur : L'indice fixé doit être entre 0 et 5", Toast.LENGTH_LONG).show();
                        return;
                    } catch (VoteDoubleException e) {
                        Toast.makeText(getApplicationContext(), "Erreur : Ce vote existe déjà", Toast.LENGTH_LONG).show();
                        return;
                    } catch (QuestionNonTrouvableException e) {
                        Toast.makeText(getApplicationContext(), "Erreur : La question reliée à votre vote est introuvable", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Intent i = new Intent(CreerVoteActivity.this, ListeQuestionActivity.class);
                    List<String> listeDeContenu = new ArrayList<>();
                    for (VDQuestion questions :service.questionsParNombreVotes())
                    {
                        listeDeContenu.add(questions.getContenu());
                    }
                    i.putStringArrayListExtra("liste", (ArrayList<String>) listeDeContenu);
                    startActivity(i);
                }

            }
        });


    }
}
