package com.example.roomtutorial

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


// Creating a basic Table Frame

@Entity(tableName = "table_note")
class Note(var title: String, var description: String, var priority: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Ignore
    private var ignoreTest: String = ""

    @ColumnInfo(name = "change_name_test") @Ignore // Will be saved as specified name
    private var changeNameTest: String = ""


//    Composite Primary keys
//    @Entity(primaryKeys = arrayOf("firstName", "lastName"))
}