package org.fabi.monvotodroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

//import com.github.mikephil.charting.charts.BarChart;

import org.fabi.monvotodroid.dao.MaBD;
import org.fabi.monvotodroid.exceptions.QuestionAucunVoteException;
import org.fabi.monvotodroid.impl.ServiceImplimentation;
import org.fabi.monvotodroid.interfaces.Service;
import org.fabi.monvotodroid.model.VDQuestion;

public class StatistiqueActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistique_activity);
        MaBD bd = Room.databaseBuilder(getApplicationContext(), MaBD.class,"bdVotoDroid").allowMainThreadQueries().build();
        Service service = new ServiceImplimentation(bd);
        Intent i = getIntent();
        VDQuestion question = new VDQuestion();
        question = bd.dao().QuestionParContenu( i.getStringExtra("Contenu"));

        //Moyenne
        TextView txtMoyenne = findViewById(R.id.txtMyenneValue);
        double d = new Double(0);
        try {
            d = service.moyennePour(question);

        } catch (QuestionAucunVoteException e) {
            Toast.makeText(getApplicationContext(), "La question pas de votes", Toast.LENGTH_LONG).show();
        }
        String resultat = String.valueOf(d);
        txtMoyenne.setText(resultat);


        //Écart type





        //Chart
        //BarChart barChart = findViewById(R.id.barChart);


    }
}
