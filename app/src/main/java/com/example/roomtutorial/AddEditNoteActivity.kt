package com.example.roomtutorial

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*

class AddEditNoteActivity : AppCompatActivity() {
    companion object{
        val EXTRA_ID = "com.example.roomtutorial.EXTRA_ID"
        val EXTRA_TITLE = "com.example.roomtutorial.EXTRA_TITLE"
        val EXTRA_DESCRIPTION = "com.example.roomtutorial.EXTRA_DESCRIPTION"
        val EXTRA_PRIORITY = "com.example.roomtutorial.EXTRA_PRIORITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        np_priority.minValue = 0
        np_priority.maxValue = 10

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)
        val intent = intent
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note")
            et_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            et_titile.setText(intent.getStringExtra(EXTRA_TITLE))
            np_priority.value = intent.getIntExtra(EXTRA_PRIORITY,1)
        }
        else{
            setTitle("Add Note")
        }

    }

    fun savenote(){
        val description: String = et_description.text.toString()
        val title: String = et_titile.text.toString()
        val priority: Int = np_priority.value

        if(description.trim().isEmpty() || title.trim().isEmpty()){
            Toast.makeText( this,"Fill all Fields", Toast.LENGTH_SHORT).show()
            return
        }
        val data = Intent()
        data.putExtra(EXTRA_TITLE,title)
        data.putExtra(EXTRA_DESCRIPTION,description)
        data.putExtra(EXTRA_PRIORITY,priority)

        val id = intent.getIntExtra(EXTRA_ID,-1)
        if(id !=-1){
            // We dont need the id here, we are just grabbing it from the note object we got
            // And passing it back so that it uniquely identifies the coloumn and replaces things
            data.putExtra(EXTRA_ID, id)
        }

        setResult(Activity.RESULT_OK,data)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // When Creating options menu what do I inflate it with
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_note_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // When an Item is selected what do I do with it
        when(item.itemId){
            R.id.menu_save_note -> {
                savenote()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}