package com.example.roomtutorial

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

// The Database that couples all the table classes
// This is singleton, Create one Synchronised instance
// Build the instance of the same class with App context.
// abstract function is used by Room and returns a NoteDao object on the repo

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    companion object {
        var instance: NoteDatabase? = null

        @Synchronized // Does not happen parallely.
        fun getInstance(context: Context): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database")
                    .addCallback(roomCallBack)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }

        private val roomCallBack = object : RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
               PopulateDbAsyncTast(instance!!).execute()
            }
        }
        class PopulateDbAsyncTast(private var db: NoteDatabase): AsyncTask<Void, Void, Void>() {
            // This is possible only because onCreate is called only after the Db is created by room
            private var noteDao = db.noteDao()
            override fun doInBackground(vararg params: Void?): Void? {
                noteDao.insert(Note("Title 1","Description 1",1))
                noteDao.insert(Note("Title 2","Description 2",2))
                noteDao.insert(Note("Title 3","Description 3",3))
                return null
            }

        }
    }

    abstract fun noteDao(): NoteDao // When user Creates an Instance of this class he has to Implement this


}