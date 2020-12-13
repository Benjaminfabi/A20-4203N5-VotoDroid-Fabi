package org.fabi.monvotodroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.fabi.monvotodroid.dao.MaBD;

import java.util.ArrayList;
import java.util.List;

public class ListeQuestionActivity extends AppCompatActivity
{
    VDQuestionAdapteur adapteur;
    MaBD bd = Room.databaseBuilder(getApplicationContext(), MaBD.class,"bdVotoDroid").allowMainThreadQueries().build();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_question_activity);
        this.initRecycler();
        this.remplirRecycler();
        Button button = findViewById(R.id.btnAjouterQuestion2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListeQuestionActivity.this, CreerQuestionActivity.class);
                startActivity(i);
            }
        });
    }

    private void remplirRecycler()
    {
        List<String> listeDeContenu = new ArrayList<>();
        RecyclerItem recyclerItem = null;
        Intent i = getIntent();
        for (String string: i.getStringArrayListExtra("liste"))
        {
            recyclerItem = new RecyclerItem(R.drawable.ic_statistique, string);
            adapteur.list.add(recyclerItem);
        }

        
        adapteur.notifyDataSetChanged();
    }


    private void initRecycler()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapteur = new VDQuestionAdapteur();
        recyclerView.setAdapter(adapteur);
        adapteur.setOnItemClickListener(new VDQuestionAdapteur.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(ListeQuestionActivity.this, CreerVoteActivity.class);
                i.putExtra("Contenu", adapteur.list.get(position).getText());
                startActivity(i);
            }

            @Override
            public void onStatClick(int position) {
                if (bd.dao().getListeDeVoteParQuestion(bd.dao().QuestionParContenu(adapteur.list.get(position).getText()).getId()).isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "La question n'a pas de votes", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    Intent i = new Intent(ListeQuestionActivity.this, StatistiqueActivity.class);
                    i.putExtra("Contenu", adapteur.list.get(position).getText());
                    startActivity(i);
                }

            }
        });
    }
}
