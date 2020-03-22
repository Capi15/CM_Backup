package com.example.cm_backup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cm_backup.R;
import com.example.cm_backup.entities.Nota;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<Nota> {

    public CustomArrayAdapter(Context context, ArrayList<Nota> nota){
        super(context, 0 , nota);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Nota nota = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout , parent, false);
        }
        ((TextView) convertView.findViewById(R.id.titulo_id)).setText(nota.getTitulo());
        ((TextView) convertView.findViewById(R.id.descricao_id)).setText(nota.getDescricao());
        ((TextView) convertView.findViewById(R.id.data_id)).setText(nota.getData());

        return convertView;
    }
}
