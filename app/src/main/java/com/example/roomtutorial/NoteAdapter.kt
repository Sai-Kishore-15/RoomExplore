package com.example.roomtutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_card.view.*

class NoteAdapter() : ListAdapter<Note, NoteAdapter.myViewHolder>(DIFF_CALLBACK) {
    var mylistener: onItemClickListener? = null
    companion object {
        var DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.title.equals(newItem.title) and
                        oldItem.description.equals(newItem.description) and
                        oldItem.priority.equals(newItem.priority)
            }
        }
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var currentObject: Note? = null
        var currenPos = 0

        init {
            itemView.setOnClickListener {
                // When the item is clicked we have to set the interface call object with the note
                // This note is accessed in the main activity
                val position = adapterPosition
                if ((mylistener != null) and (position != RecyclerView.NO_POSITION)) {
                    mylistener!!.onItemClick(getItem(position))
                }
            }
        }

        fun setData(singleObject: Note, position: Int) {
            this.currentObject = singleObject
            this.currenPos = position
            itemView.tv_note_title.text = singleObject.title
            itemView.tv_note_priority.text = singleObject.priority.toString()
            itemView.tv_note_description.text = singleObject.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.note_card, parent, false)
        return myViewHolder(inflater)
    }



    fun getNoteAt(pos: Int): Note {
        return getItem(pos)
    }

//    fun setNotes(notes: List<Note>) {
//        // Neve use NotifyDataSetChanged  for actual projects
//        this.myNoteList = notes
//        notifyDataSetChanged()
////        val diffUtilCallBack = NoteDiffUtilCallBack(myNoteList,notes)
////        val diffResult = DiffUtil.calculateDiff(diffUtilCallBack)
//
//    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val singleObject = getItem(position)
        holder.setData(singleObject, position)
    }

    interface onItemClickListener {
        // Whenever this function is implemented we give the note to the
        // User for him to do stuffs with it
        // we can use var callback = context as onItemClickInterface at top and use the variable
        // callback.onItemClick(note) This way also we can set the value to the interface
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        // The listener from the main activity is sent here
        // We get the click position and assign the note to it
        this.mylistener = listener
    }

}