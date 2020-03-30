package com.example.cm_backup.ativities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.cm_backup.NotaViewModel;
import com.example.cm_backup.R;
import com.example.cm_backup.adapters.NotaListAdapter;
import com.example.cm_backup.Nota;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Nota> arrayNota;
    public static final int NOTAS_ACTIVITY_REQUEST_CODE = 1;
    private NotaViewModel mNotaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.id_lista);
        final NotaListAdapter adapter = new NotaListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNotaViewModel = ViewModelProviders.of(this).get(NotaViewModel.class);

        mNotaViewModel.getAllNotas().observe(this, new Observer<List<Nota>>() {
            @Override
            public void onChanged(@Nullable final List<Nota> notas) {
                // Update the cached copy of the words in the adapter.
                adapter.setNotas(notas);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Nota myNota = adapter.getNotaAtPosition(position);
                        Toast.makeText(MainActivity.this, "A apagar nota " +
                                myNota.getTitulo(), Toast.LENGTH_LONG).show();

                        // Apaga a minha nota
                        mNotaViewModel.deleteNota(myNota);
                    }
                });

        helper.attachToRecyclerView(recyclerView);
    }



    public void addNotas(View view) {
        Intent intent = new Intent(MainActivity.this, NotasActivity.class);
        startActivityForResult(intent, NOTAS_ACTIVITY_REQUEST_CODE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //Apaga todas as Notas
        if (id == R.id.clear_data) {
            Toast.makeText(this, "Notas apagadas com sucesso",
                    Toast.LENGTH_SHORT).show();

            mNotaViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOTAS_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String[] notas = data.getStringArrayExtra(NotasActivity.EXTRA_REPLY);
            Nota nota = new Nota(notas[0], notas[1], notas[2]);
            mNotaViewModel.insert(nota);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        Nota nota = arrayNota.get(index);

        switch (item.getItemId()) {
            case R.id.editar:
                Toast.makeText(this, "editado", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.remover:
                Toast.makeText(this, "removido", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }
}
