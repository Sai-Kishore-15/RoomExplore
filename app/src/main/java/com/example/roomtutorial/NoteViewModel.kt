package com.example.roomtutorial

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/* Important notes
// Always pass application context to viewmodel.
 This is because if you pass a reference to an activity context, then once your activity gets
 pushed back, this reference still would exist in ViewModel, This would create a MEMORY LEAK
 View Model's function is to preserve the data until the activity is FINISHED.
*/
class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    var repository: Repository = Repository(application)
    var allNotes: LiveData<List<Note>> = repository.getAllNotes()


    // Users can use ViewModel to Put in data and get data back
    // View Model puts the data to the repository
    // Repo performs an async funtion to perform these operations on noteDao

    fun insert(note: Note){
        repository.insert(note)
    }
    fun delete(note:Note){
        repository.delete(note)
    }
    fun update(note:Note){
        repository.update(note)
    }
    fun deleteAllNotes(){
        repository.deleteAllNotes()
    }


}