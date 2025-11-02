package com.example.notekeeperapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class EditNoteActivity : AppCompatActivity() {

    private lateinit var dbHelper: NoteDbHelper
    private var noteId: Long = -1  // ✅ Changed to Long

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        dbHelper = NoteDbHelper(this)

        val titleEditText = findViewById<TextInputEditText>(R.id.titleEditText)
        val contentEditText = findViewById<TextInputEditText>(R.id.contentEditText)
        val saveButton = findViewById<MaterialButton>(R.id.saveButton)

        // ✅ Retrieve note data from Intent
        noteId = intent.getLongExtra("note_id", -1)
        val noteTitle = intent.getStringExtra("note_title")
        val noteContent = intent.getStringExtra("note_content")

        // ✅ Prefill if editing existing note
        if (noteId != -1L) {
            titleEditText.setText(noteTitle ?: "")
            contentEditText.setText(noteContent ?: "")
            saveButton.text = "Update Note"
        } else {
            saveButton.text = "Save Note"
        }

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val content = contentEditText.text.toString().trim()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (noteId == -1L) {
                // ✅ Create new note
                dbHelper.insertNote(title, content)
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
            } else {
                // ✅ Update existing note
                val rowsUpdated = dbHelper.updateNote(noteId, title, content)
                if (rowsUpdated > 0) {
                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to update note", Toast.LENGTH_SHORT).show()
                }
            }

            finish() // Return to MainActivity
        }
    }
}
