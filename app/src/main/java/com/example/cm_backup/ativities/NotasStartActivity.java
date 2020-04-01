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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.cm_backup.MapsActivity;
import com.example.cm_backup.NotaViewModel;
import com.example.cm_backup.R;
import com.example.cm_backup.adapters.NotaListAdapter;
import com.example.cm_backup.Nota;
import com.example.cm_backup.dto.EditNoteDto;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotasStartActivity extends AppCompatActivity{

    ArrayList<Nota> arrayNota;
    public static final int NOTAS_ACTIVITY_REQUEST_CODE = 1;
    public static final int NOTAS_ACTIVITYUPDATE_REQUEST_CODE = 2;
    public static final String EXTRA_DATA_FOR_UPDATE = "extra_data_for_update";
    private NotaViewModel mNotaViewModel;
    NotaListAdapter adapter;
    Calendar calendar = Calendar.getInstance();
    final String data_nota = DateFormat.getDateInstance().format(calendar.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_start);
        RecyclerView recyclerView = findViewById(R.id.id_lista);
        adapter = new NotaListAdapter(this);
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
                        Toast.makeText(NotasStartActivity.this, "A apagar nota " +
                                myNota.getTitulo(), Toast.LENGTH_LONG).show();

                        // Apaga a minha nota
                        mNotaViewModel.deleteNota(myNota);
                    }
                });

        helper.attachToRecyclerView(recyclerView);
    }

    public void addNotas(View view) {
        Intent intent = new Intent(NotasStartActivity.this, NotasActivity.class);
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
        final EditNoteDto notas = (EditNoteDto) data.getSerializableExtra(NotasActivity.EXTRA_REPLY);
        if (requestCode == NOTAS_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Nota nota = new Nota(notas.titulo, notas.descricao, data_nota);
            if(nota.getTitulo() != "" && nota.getDescricao() != ""){
                mNotaViewModel.insert(nota);
            }
            Toast.makeText(this, R.string.NotaVazia, Toast.LENGTH_SHORT).show();
        }else if (requestCode == NOTAS_ACTIVITYUPDATE_REQUEST_CODE && resultCode == RESULT_OK){
            mNotaViewModel.getById(notas.id).observe(this, new Observer<Nota>() {
                @Override
                public void onChanged(Nota nota) {
                    if (nota != null && nota.getTitulo() != "" && nota.getDescricao() != "") {
                        nota.setTitulo(notas.titulo);
                        nota.setDescricao(notas.descricao);
                        nota.setData(data_nota);
                        mNotaViewModel.update(nota);
                        mNotaViewModel.getById(notas.id).removeObserver(this);
                    }
                }
            });
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Toast.makeText(this, "clicado", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.editar:
                int index = item.getGroupId();
                Nota nota = adapter.getNotaAtPosition(index);
                goToUpdate(nota);
                Toast.makeText(this, "editado", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void goToUpdate(Nota nota) {
        Intent intent = new Intent(this, NotasActivity.class);
        intent.putExtra(EXTRA_DATA_FOR_UPDATE,
                new EditNoteDto(
                    nota.getId(),
                    nota.getTitulo(),
                    nota.getDescricao(),
                    nota.getData()
                )
        );
        startActivityForResult(intent, NOTAS_ACTIVITYUPDATE_REQUEST_CODE);
    }

    public void goToMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
