package org.fabi.monvotodroid;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import org.fabi.monvotodroid.dao.MaBD;
import org.fabi.monvotodroid.impl.ServiceImplimentation;
import org.fabi.monvotodroid.interfaces.Service;
import org.fabi.monvotodroid.model.VDQuestion;

import java.util.ArrayList;
import java.util.List;

public class VDQuestionAdapteur extends RecyclerView.Adapter<VDQuestionAdapteur.MyViewHolder>
{
    public List<RecyclerItem> list;
    private OnItemClickListener mListener;
    public interface OnItemClickListener
    {
        void onItemClick(int position);
        void onStatClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvContenu;
        public ImageView imgStats;
        public MyViewHolder(LinearLayout v, OnItemClickListener listener) {
            super(v);
            tvContenu = v.findViewById(R.id.contenuTextView);
            imgStats = v.findViewById(R.id.iconStats);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION);
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            imgStats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION);
                        {
                            listener.onStatClick(position);
                        }
                    }
                }
            });

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public VDQuestionAdapteur() {
        list = new ArrayList<RecyclerItem>();
    }
    // Create new views (invoked by the layout manager)
    @Override
    public VDQuestionAdapteur.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vdqestion_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mListener);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        RecyclerItem itemCourant = list.get(position);
        String questionCourante = itemCourant.getText();
        holder.tvContenu.setText(questionCourante);
        holder.imgStats.setImageResource(itemCourant.getImageResource());
    }
    // renvoie la taille de la liste
    @Override
    public int getItemCount() {
        return list.size();
    }
}