package com.raghava.notesapp.RoomDataBase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note")
    fun deleteAllNotes()

    @Query("SELECT * FROM note ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>?
}