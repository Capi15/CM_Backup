package com.example.cm_backup.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.cm_backup.R;
import com.example.cm_backup.db.Contrato;

public class CustomCursorAdapter extends CursorAdapter {
    private Context mContext;
    private int id;
    private Cursor mCursor;

    public CustomCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
        mContext = context;
        mCursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView titulo = (TextView) view.findViewById(R.id.titulo_id);
        TextView descricao = (TextView) view.findViewById(R.id.descricao_id);
        TextView data = (TextView) view.findViewById(R.id.data_id);
        titulo.setText(mCursor.getString(cursor.getColumnIndexOrThrow(Contrato.Nota.COLUMN_TITULO)));
        descricao.setText(mCursor.getString(cursor.getColumnIndexOrThrow(Contrato.Nota.COLUMN_DESCRICAO)));
        data.setText(mCursor.getString(cursor.getColumnIndexOrThrow(Contrato.Nota.COLUMN_DATA)));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View row = super.getView(position, convertView, parent);
        if (position % 2 == 0)
            row.setBackgroundColor(Color.parseColor("#3C9EBC"));
        else
            row.setBackgroundColor(Color.parseColor("#347FBA"));
        return row;

    }
}
