package com.example.roomtutorial

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*

// Dao Is always an interface / Abstract class
// The main function is to take the Class object and do something with it
// Or do queries and return variables
@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)

    @Query("SELECT * FROM table_note ORDER BY priority" )
    fun getAllNotes(): LiveData<List<Note>>

    @Query("DELETE FROM table_note")
    fun deleteAllNotes()


}