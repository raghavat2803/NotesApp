package com.raghava.notesapp.Models

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.raghava.notesapp.RoomDataBase.Note
import com.raghava.notesapp.RoomDataBase.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var noteRepository: NoteRepository? = null
    private var allNotes: LiveData<List<Note>>? = null

    init {
        noteRepository = NoteRepository(application)
        allNotes = noteRepository?.getAllNotes()
    }

    fun deleteAll(){
        viewModelScope.launch {
            noteRepository?.deleteAllNotes()
        }
    }

    fun getAllNotes(): LiveData<List<Note>>?{
        return allNotes
    }

    fun delete(note: Note) {
        viewModelScope.launch {
            noteRepository?.delete(note)
        }
    }

}