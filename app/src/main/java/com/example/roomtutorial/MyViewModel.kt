package com.example.roomtutorial

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    lateinit var allNotes: MutableLiveData<List<Note>>

    fun getAllNotes(): LiveData<List<Note>>{
        return allNotes
    }
}