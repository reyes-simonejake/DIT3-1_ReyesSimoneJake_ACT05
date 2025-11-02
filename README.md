# ▪️ How did you implement CRUD using SQLite?

I implemented CRUD (Create, Read, Update, Delete) functionality using a custom SQLite helper class named NoteDbHelper.

Create: New notes are inserted into the database using insertNote().

Read: Notes are retrieved with getAllNotes() and displayed in a RecyclerView using a custom adapter.

Update: Existing notes are modified in the EditNoteActivity using updateNote().

Delete: Notes are removed from the database using deleteNote() after user confirmation.
This approach ensures efficient data handling with proper lifecycle awareness in MainActivity and EditNoteActivity.

# ▪️ What challenges did you face in maintaining data persistence?

One main challenge was ensuring that data persisted correctly after configuration changes and app restarts. Managing the SQLiteDatabase lifecycle and preventing memory leaks required careful handling of database opening and closing operations. Additionally, keeping the RecyclerView synchronized with database updates (especially after editing or deleting notes) required implementing proper refresh logic using onResume() in MainActivity.

# ▪️ How could you improve performance or UI design in future versions?

For performance, I could migrate from SQLite to Room Database, which provides a more robust abstraction layer and built-in support for LiveData and coroutines.
In terms of UI, I plan to:

Add search and filter functionality for notes.

Implement categories or tags for better organization.

Integrate animations and Material transitions to enhance the user experience.

Use dark mode support and adaptive layouts for improved accessibility.
