package org.fabi.monvotodroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.fabi.monvotodroid.dao.MaBD;
import org.fabi.monvotodroid.exceptions.QuestionAucunVoteException;
import org.fabi.monvotodroid.impl.ServiceImplimentation;
import org.fabi.monvotodroid.interfaces.Service;
import org.fabi.monvotodroid.model.VDQuestion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatistiqueActivity extends AppCompatActivity
{
    BarChart chart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistique_activity);
        MaBD bd = Room.databaseBuilder(getApplicationContext(), MaBD.class,"bdVotoDroid").allowMainThreadQueries().build();
        Service service = new ServiceImplimentation(bd);
        Intent i = getIntent();
        VDQuestion question = new VDQuestion();
        question = bd.dao().QuestionParContenu( i.getStringExtra("Contenu"));
        //Titre
        TextView txtTitle = findViewById(R.id.questionStat);
        txtTitle.setText(question.getContenu());
        //Moyenne
        TextView txtMoyenne = findViewById(R.id.txtMyenneValue);
        double d = new Double(0);
        try {
            d = service.moyennePour(question);

        } catch (QuestionAucunVoteException e) {
            Toast.makeText(getApplicationContext(), "La question pas de votes", Toast.LENGTH_LONG).show();
        }
        txtMoyenne.setText(new DecimalFormat("##.##").format(d));
        //Écart type
        TextView txtEcart = findViewById(R.id.txtEcartValue);
        double ecart = new Double(0);
        try {
            ecart = service.ecartTypePour(question);
        } catch (QuestionAucunVoteException e) {
            Toast.makeText(getApplicationContext(), "La question pas de votes", Toast.LENGTH_LONG).show();
        }
        txtEcart.setText(new DecimalFormat("##.##").format(ecart));
        //Chart
        chart = findViewById(R.id.barChart);
        chart.setMaxVisibleValueCount(6);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new DefaultAxisValueFormatter(0));
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setGranularity(1);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setValueFormatter(new DefaultAxisValueFormatter(0));
        chart.getDescription().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        setData(service.distributionPour(question));
    }
    private void setData(Map<Integer, Integer> datas) {
        ArrayList<BarEntry> values = new ArrayList<>();
        for (Map.Entry<Integer, Integer> key : datas.entrySet()){
            values.add(new BarEntry(key.getKey() , key.getValue()));
        }
        BarDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Distribution de vote");
            set1.setDrawIcons(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(.9f);
            chart.setData(data);
        }
    }
}
