package com.example.notekeeperapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: NoteDbHelper
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var emptyView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar setup
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "NoteKeeperApp"

        // Initialize database helper
        dbHelper = NoteDbHelper(this)

        // RecyclerView setup
        notesRecyclerView = findViewById(R.id.notesRecyclerView)
        emptyView = findViewById(R.id.emptyView)
        notesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter with Edit and Delete click events
        notesAdapter = NotesAdapter(
            notes = listOf(),
            onEditClick = { note ->
                // Launch EditNoteActivity with existing note data
                val intent = Intent(this, EditNoteActivity::class.java).apply {
                    putExtra("note_id", note.id)
                    putExtra("note_title", note.title)
                    putExtra("note_content", note.content)
                }
                startActivity(intent)
            },
            onDeleteClick = { note ->
                // Show confirmation dialog
                MaterialAlertDialogBuilder(this)
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Delete") { _, _ ->
                        dbHelper.deleteNote(note.id)
                        loadNotes()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        )

        notesRecyclerView.adapter = notesAdapter

        // Floating Action Button to add a new note
        val fabAddNote: FloatingActionButton = findViewById(R.id.fabAddNote)
        fabAddNote.setOnClickListener {
            startActivity(Intent(this, EditNoteActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    private fun loadNotes() {
        val notes = dbHelper.getAllNotes()
        notesAdapter.updateData(notes)

        if (notes.isEmpty()) {
            notesRecyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            notesRecyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }
    }
}
