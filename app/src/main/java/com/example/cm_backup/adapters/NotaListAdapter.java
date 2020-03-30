package com.example.cm_backup.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cm_backup.R;
import com.example.cm_backup.Nota;

import java.util.List;

public class NotaListAdapter extends RecyclerView.Adapter<NotaListAdapter.NotaViewHolder> {
    private final LayoutInflater mInflater;
    private List<Nota> mNotas; // Cached copy of words

    public NotaListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public NotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.row_layout, parent, false);
        return new NotaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotaViewHolder holder, int position) {

        if (mNotas != null) {
            Nota current = mNotas.get(position);
            holder.rowTituloView.setText(current.getTitulo());
            holder.rowDescricaoView.setText(current.getDescricao());
            holder.rowDataView.setText(current.getData());
            if(position % 2 == 0){
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.FirstColor));
            }else{
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.SecondColor));
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.rowTituloView.setText("Não há notas");
        }
    }

    public void setNotas(List<Nota> notas){
        mNotas = notas;
        notifyDataSetChanged();
    }

    public Nota getNotaAtPosition (int position) {
        return mNotas.get(position);
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mNotas != null)
            return mNotas.size();
        else return 0;
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {
        private final TextView rowTituloView;
        private final TextView rowDescricaoView;
        private final TextView rowDataView;


        private NotaViewHolder(View itemView) {
            super(itemView);
            rowTituloView = itemView.findViewById(R.id.titulo_id);
            rowDescricaoView = itemView.findViewById(R.id.descricao_id);
            rowDataView = itemView.findViewById(R.id.data_id);
        }
    }
}
