package com.example.roomtutorial

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

/*
    NOTES:
    View model's live data are created and observed using the first two lines of *_Vm_*


 */

class MainActivity : AppCompatActivity() {

    val ADD_NOTE_REQUESTCODE = 1
    val EDIT_NOTE_REQUESTCODE = 2

    lateinit var noteViewModel: NoteViewModel
    lateinit var myViewModel: MyViewModel
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_mainPage.layoutManager = layoutManager
        rv_mainPage.setHasFixedSize(true)

        adapter = NoteAdapter()
        rv_mainPage.adapter = adapter

        initNoteViewModel()


        fab_add_note.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUESTCODE)
        }

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(rv_mainPage)

        adapter.setOnItemClickListener(object : NoteAdapter.onItemClickListener{
            override fun onItemClick(note: Note) {
                // This note that we got is the current note that is clicked
                val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE,note.title)
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION,note.description)
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY,note.priority)
                intent.putExtra(AddEditNoteActivity.EXTRA_ID,note.id)
                startActivityForResult(intent,EDIT_NOTE_REQUESTCODE)
            }
        })

    }

    private fun initNoteViewModel() {
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        noteViewModel.allNotes.observe(this, object : Observer<List<Note>> {
            override fun onChanged(t: List<Note>?) {
                adapter.setNotes(t!!)
                Toast.makeText(this@MainActivity, "DataChanged", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_REQUESTCODE && resultCode == Activity.RESULT_OK) {

            val title = data!!.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
            val priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

            val note = Note(title!!, description!!, priority)
            noteViewModel.insert(note)
        }
        else if (requestCode == EDIT_NOTE_REQUESTCODE && resultCode == Activity.RESULT_OK){
            val id = data!!.getIntExtra(AddEditNoteActivity.EXTRA_ID,-1)
            if(id==-1){
                Toast.makeText(this, "Cannot update this ID", Toast.LENGTH_SHORT).show()
                return
            }

            val title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
            val priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

            val note = Note(title!!,description!!,priority)
            note.id = id
            noteViewModel.update(note)
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
        }
        else {
            // If cancel Button is pressed
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.delete_all_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_main_delete->{
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "Deleted All notes", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

