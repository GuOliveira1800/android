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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    //private ArrayList<Note> notesList;
    //private ArrayAdapter<Note> adapter;

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private List<Note> notesList = new ArrayList<>();

    private ListView listView;
    private Button createButton;
    private SearchView searchView;
    private TextView emptyListText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        createButton = findViewById(R.id.createButton);
        searchView = findViewById(R.id.searchView);
        emptyListText = findViewById(R.id.emptyListText);

        // Abrir ou criar o banco de dados
        database = openOrCreateDatabase("notesDB", MODE_PRIVATE, null);

        // Criar a tabela se não existir
        database.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, content VARCHAR);");

        recyclerView = findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(notesList, new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                showNoteDialog(note);
            }
        });
        recyclerView.setAdapter(adapter);

        // Carregar as notas existentes
        loadNotes();

        // Lidar com o clique no botão Criar
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateNoteDialog();
            }
        });

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

    private void loadNotes() {
        Cursor cursor = database.rawQuery("SELECT * FROM notes", null);

        if (cursor.moveToFirst()) {
            notesList.clear();
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
                Note note = new Note(id, title, content);
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        adapter.notifyDataSetChanged();
        updateEmptyListText();

        cursor.close();
    }

    private void searchNotes(String query) {
        Cursor cursor = database.rawQuery("SELECT * FROM notes WHERE title LIKE ?", new String[]{"%" + query + "%"});

        if (cursor.moveToFirst()) {
            notesList.clear();
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
                Note note = new Note(id, title, content);
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        adapter.notifyDataSetChanged();
        updateEmptyListText();

        cursor.close();
    }

    private void showCreateNoteDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Comida");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_note, null);
        builder.setView(dialogView);

        final TextView titleEditText = dialogView.findViewById(R.id.titleEditText);
        final TextView contentEditText = dialogView.findViewById(R.id.contentEditText);

        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String title = titleEditText.getText().toString().trim();
                String content = contentEditText.getText().toString().trim();

                if (!title.isEmpty() && !content.isEmpty()) {
                    saveNoteToDatabase(title, content);
                } else {
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", null);

        builder.show();
    }

    private void showNoteDialog(final Note note) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(note.getTitulo());

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_note, null);
        builder.setView(dialogView);

        final EditText TituloEditText = dialogView.findViewById(R.id.titleEditText);
        final EditText contentEditText = dialogView.findViewById(R.id.contentEditText);

        TituloEditText.setText(note.getTitulo());
        contentEditText.setText(note.getTexto());

        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newContent = contentEditText.getText().toString().trim();
                String newTitulo = TituloEditText.getText().toString().trim();
                updateNoteInDatabase(note.getCodigo(), newContent, newTitulo);
            }
        });

        builder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteNoteFromDatabase(note.getCodigo());
            }
        });

        builder.show();
    }

    private void saveNoteToDatabase(String title, String content) {
        String insertQuery = "INSERT INTO notes (title, content) VALUES (?, ?)";
        Object[] insertParams = new Object[]{title, content};
        database.execSQL(insertQuery, insertParams);
        Toast.makeText(this, "Nota criada com sucesso", Toast.LENGTH_SHORT).show();
        loadNotes();
    }

    private void updateNoteInDatabase(int noteId, String content, String titulo) {

        String updateQuery = "UPDATE notes SET content = ?, title = ? WHERE id = ?";
        Object[] updateParams = new Object[]{content, titulo, noteId};
        database.execSQL(updateQuery, updateParams);
        Toast.makeText(this, "Nota atualizada", Toast.LENGTH_SHORT).show();
        loadNotes();
    }

    private void deleteNoteFromDatabase(int noteId) {
        String deleteQuery = "DELETE FROM notes WHERE id = ?";
        Object[] deleteParams = new Object[]{noteId};
        database.execSQL(deleteQuery, deleteParams);
        Toast.makeText(this, "Nota excluída", Toast.LENGTH_SHORT).show();
        loadNotes();
    }

    private void updateEmptyListText() {
        if (notesList.isEmpty()) {
            emptyListText.setVisibility(View.VISIBLE);
        } else {
            emptyListText.setVisibility(View.GONE);
        }
    }
}
