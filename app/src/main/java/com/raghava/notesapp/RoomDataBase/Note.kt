package com.raghava.notesapp.RoomDataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(val title: String, val description: String, val priority: Int) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}