package com.raghava.notesapp.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.raghava.notesapp.RoomDataBase.Note
import com.raghava.notesapp.RoomDataBase.NoteRepository
import kotlinx.coroutines.launch

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {
    private var noteRepository: NoteRepository? = null

    init {
        noteRepository = NoteRepository(application)
    }

    fun insert(note: Note) {
        viewModelScope.launch {
            noteRepository?.insert(note)
        }
    }

    fun update(note: Note) {
        noteRepository?.update(note)
    }

    fun delete(note: Note) {
        viewModelScope.launch {
            noteRepository?.delete(note)
        }
    }
}