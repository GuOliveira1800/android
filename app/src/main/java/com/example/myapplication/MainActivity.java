package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    private DataInsert dataInsert;
    private List<Note> notesList = new ArrayList<>();
    private NoteAdapter adapter;
    private TextView emptyListText;
    private SearchView searchView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataInsert = new DataInsert();
        // Abrir ou criar o banco de dados
        database = openOrCreateDatabase("notesDB", MODE_PRIVATE, null);

        // Criar a tabela se não existir
        database.execSQL("CREATE TABLE IF NOT EXISTS taco_4___edicao (\n" +
            "  id int DEFAULT NULL, \n" +
            "  Caterogia varchar(37) DEFAULT NULL,\n" +
            "  Alimento varchar(64) DEFAULT NULL,\n" +
            "  Umidade varchar(4) DEFAULT NULL,\n" +
            "  Energiakcal varchar(3) DEFAULT NULL,\n" +
            "  kJ varchar(4) DEFAULT NULL,\n" +
            "  Proteonag varchar(4) DEFAULT NULL,\n" +
            "  Lipodeosg varchar(5) DEFAULT NULL,\n" +
            "  Colesterolmg varchar(4) DEFAULT NULL,\n" +
            "  Carboidratosg varchar(4) DEFAULT NULL,\n" +
            "  FibraAlimentarg varchar(4) DEFAULT NULL,\n" +
            "  Cinzasg varchar(4) DEFAULT NULL,\n" +
            "  Calciomg varchar(4) DEFAULT NULL,\n" +
            "  Magnesiomg varchar(3) DEFAULT NULL,\n" +
            "  Manganesmg varchar(5) DEFAULT NULL,\n" +
            "  Fósforomg varchar(4) DEFAULT NULL,\n" +
            "  Ferromg varchar(4) DEFAULT NULL,\n" +
            "  Sódiomg varchar(5) DEFAULT NULL,\n" +
            "  Potassiomg varchar(5) DEFAULT NULL,\n" +
            "  Cobremg varchar(5) DEFAULT NULL,\n" +
            "  Zincomg varchar(4) DEFAULT NULL,\n" +
            "  Retinolmcg varchar(5) DEFAULT NULL,\n" +
            "  REmcg varchar(5) DEFAULT NULL,\n" +
            "  RAEmcg varchar(5) DEFAULT NULL,\n" +
            "  Tiaminamg varchar(4) DEFAULT NULL,\n" +
            "  Riboflavinamg varchar(4) DEFAULT NULL,\n" +
            "  Piridoxinamg varchar(5) DEFAULT NULL,\n" +
            "  Niacinamg varchar(5) DEFAULT NULL,\n" +
            "  VitaminaCmg varchar(5) DEFAULT NULL )");

        database.execSQL(dataInsert.insert01);
        database.execSQL(dataInsert.insert02);
        database.execSQL(dataInsert.insert03);

        recyclerView = findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(notesList, new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                showNoteDialog(note);
            }
        });
        recyclerView.setAdapter(adapter);

        searchView = findViewById(R.id.searchView);
        // Lidar com a pesquisa de notas
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchNotes(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchNotes(newText);
                return true;
            }
        });
    }

    private void searchNotes(String query) {
        Cursor cursor = database.rawQuery("SELECT * FROM taco_4___edicao WHERE Alimento LIKE ?", new String[]{"%" + query + "%"});

        if (cursor.moveToFirst()) {
            notesList.clear();
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("Alimento"));
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("Caterogia"));
                Note note = new Note(id, title, content);
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        adapter.notifyDataSetChanged();
        cursor.close();
    }

    private void showNoteDialog(final Note note) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_note, null);
        builder.setView(dialogView);

        final TextView TituloEditText = dialogView.findViewById(R.id.titleEditText);
        final TextView contentEditText = dialogView.findViewById(R.id.contentEditText);

        Cursor cursor = database.rawQuery("SELECT \n" +
                "  ('Caterogia: ' || Caterogia || ', ' ||\n" +
                "  'Alimento: ' || Alimento || ', ' ||\n" +
                "  'Umidade: ' || Umidade || ', ' ||\n" +
                "  'Energiakcal: ' || Energiakcal || ', ' ||\n" +
                "  'kJ: ' || kJ || ', ' ||\n" +
                "  'Proteonag: ' || Proteonag || ', ' ||\n" +
                "  'Lipodeosg: ' || Lipodeosg || ', ' ||\n" +
                "  'Colesterolmg: ' || Colesterolmg || ', ' ||\n" +
                "  'Carboidratosg: ' || Carboidratosg || ', ' ||\n" +
                "  'FibraAlimentarg: ' || FibraAlimentarg || ', ' ||\n" +
                "  'Cinzasg: ' || Cinzasg || ', ' ||\n" +
                "  'Calciomg: ' || Calciomg || ', ' ||\n" +
                "  'Magnesiomg: ' || Magnesiomg || ', ' ||\n" +
                "  'Manganesmg: ' || Manganesmg || ', ' ||\n" +
                "  'Fósforomg: ' || Fósforomg || ', ' ||\n" +
                "  'Ferromg: ' || Ferromg || ', ' ||\n" +
                "  'Sódiomg: ' || Sódiomg || ', ' ||\n" +
                "  'Potassiomg: ' || Potassiomg || ', ' ||\n" +
                "  'Cobremg: ' || Cobremg || ', ' ||\n" +
                "  'Zincomg: ' || Zincomg || ', ' ||\n" +
                "  'Retinolmcg: ' || Retinolmcg || ', ' ||\n" +
                "  'REmcg: ' || REmcg || ', ' ||\n" +
                "  'RAEmcg: ' || RAEmcg || ', ' ||\n" +
                "  'VitaminaCmg: ' || VitaminaCmg || ', ' ||\n" +
                "  'Tiaminamg: ' || Tiaminamg || ', ' ||\n" +
                "  'Riboflavinamg: ' || Riboflavinamg || ', ' ||\n" +
                "  'Piridoxinamg: ' || Piridoxinamg || ', ' ||\n" +
                "  'Niacinamg: ' || Niacinamg) as texto\n" +
                "FROM taco_4___edicao where id = "+note.getCodigo(),null);

        TituloEditText.setText(note.getTitulo());

        if (cursor.moveToFirst()) {
            contentEditText.setText( cursor.getString(0) );
        }else{
            contentEditText.setText(note.getTexto());
        }
        builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();
    }
}
