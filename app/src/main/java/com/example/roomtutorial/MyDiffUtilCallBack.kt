package com.example.roomtutorial

import androidx.recyclerview.widget.DiffUtil

class NoteDiffUtilCallBack(private val oldList: List<Note>,
                        private val newList: List<Note>): DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title.equals(newList[newItemPosition].title) and
                oldList[oldItemPosition].description.equals(newList[newItemPosition].description) and
                oldList[oldItemPosition].priority.equals(newList[newItemPosition].priority)

    }


}
//var DIFF_CALLBACK = object: DiffUtil.ItemCallback<Note>(){
//    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
//        return oldItem.title.equals(newItem.title) and
//                oldItem.description.equals(newItem.description) and
//                oldItem.priority.equals(newItem.priority)
//    }
//}