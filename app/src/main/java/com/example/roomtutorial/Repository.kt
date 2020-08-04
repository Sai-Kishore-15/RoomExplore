package com.example.roomtutorial

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData


// Repository is not in the architecture components
// It is used as a layer of abstraction.
// It can get data from different sources, Room , Retrofit , APIsetc.
// The View model only calls this for data and this takes care of getting data.
// All methods for accessing any data from db is mentioned here

class Repository(application: Application) {
    var noteDatabase = NoteDatabase.getInstance(application)
    var noteDao: NoteDao = noteDatabase.noteDao()
    var allnotes: LiveData<List<Note>> = noteDao.getAllNotes()

    fun insert(note: Note) {
        InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note: Note) {
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun delete(note: Note) {
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllNoteAsyncTask(noteDao).execute()
    }
    fun getAllNotes(): LiveData<List<Note>> {
        return allnotes
    }

    companion object {

        class InsertNoteAsyncTask(private var noteDao: NoteDao) : AsyncTask<Note, Void, Void>() {

            override fun doInBackground(vararg params: Note?): Void? {
                noteDao.insert(params[0]!!)
                return null
            }
        }

        class DeleteNoteAsyncTask(private var  noteDao: NoteDao) : AsyncTask<Note, Void, Void>() {

            override fun doInBackground(vararg params: Note?): Void? {
                noteDao.delete(params[0]!!)
                return null
            }
        }

        class UpdateNoteAsyncTask(private var  noteDao: NoteDao) : AsyncTask<Note, Void, Void>() {
            override fun doInBackground(vararg params: Note?): Void? {
                noteDao.update(params[0]!!)
                return null
            }
        }

        class DeleteAllNoteAsyncTask(private var noteDao: NoteDao) : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                noteDao.deleteAllNotes()
                return null
            }
        }

    }


}