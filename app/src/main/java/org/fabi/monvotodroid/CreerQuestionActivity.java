package org.fabi.monvotodroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.fabi.monvotodroid.dao.MaBD;
import org.fabi.monvotodroid.exceptions.ContenuIdentiqueException;
import org.fabi.monvotodroid.exceptions.IdNonNullException;
import org.fabi.monvotodroid.exceptions.QuestionIdentiqueException;
import org.fabi.monvotodroid.exceptions.QuestionNullException;
import org.fabi.monvotodroid.exceptions.QuestionTailleMauvaise;
import org.fabi.monvotodroid.impl.ServiceImplimentation;
import org.fabi.monvotodroid.interfaces.Service;
import org.fabi.monvotodroid.model.VDQuestion;

import java.util.ArrayList;
import java.util.List;

public class CreerQuestionActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creer_question_activity);
        EditText textContenu = (EditText) findViewById(R.id.textContenu);
        Button btnCreer = findViewById(R.id.btnCreerQuestion);
        btnCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textContenu.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Votre question est vide", Toast.LENGTH_LONG).show();
                }
                else
                {
                    MaBD bd = Room.databaseBuilder(getApplicationContext(), MaBD.class,"bdVotoDroid").allowMainThreadQueries().build();
                    Service service = new ServiceImplimentation(bd);
                    VDQuestion question = new VDQuestion();
                    question.setContenu(textContenu.getText().toString());
                    try {
                        service.ajoutQuestion(question);
                    } catch (IdNonNullException e) {
                        Toast.makeText(getApplicationContext(), "Erreur : Id déjà fixé", Toast.LENGTH_LONG).show();
                        return;
                    } catch (QuestionIdentiqueException e) {
                        Toast.makeText(getApplicationContext(), "Erreur : La question existe déjà", Toast.LENGTH_LONG).show();
                        return;
                    } catch (QuestionTailleMauvaise questionTailleMauvaise) {
                        Toast.makeText(getApplicationContext(), "Erreur : Taille doit être entre 5 et 255 caractères", Toast.LENGTH_LONG).show();
                        return;
                    } catch (QuestionNullException e) {
                        Toast.makeText(getApplicationContext(), "Erreur : La question est nulle", Toast.LENGTH_LONG).show();
                        return;
                    } catch (ContenuIdentiqueException e) {
                        Toast.makeText(getApplicationContext(), "Erreur : Le contenu de la question existe déjà", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(getApplicationContext(), "Question ajoutée", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(CreerQuestionActivity.this, ListeQuestionActivity.class);
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
