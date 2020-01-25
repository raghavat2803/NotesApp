package com.raghava.notesapp.UI

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.raghava.notesapp.Models.AddNoteViewModel
import com.raghava.notesapp.R
import com.raghava.notesapp.RoomDataBase.Note

class AddOrEditNoteFragment : Fragment() {

    private var title: EditText? = null
    private var descritpion: EditText? = null
    private var priorityNumberPicker: NumberPicker? = null
    private var addNoteViewModel: AddNoteViewModel? = null
    private var noteMode = -1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.note_fragment, container, false)
        title = view.findViewById(R.id.title)
        descritpion = view.findViewById(R.id.description)
        priorityNumberPicker = view.findViewById(R.id.number_picker_priority)

        priorityNumberPicker?.minValue = 1
        priorityNumberPicker?.maxValue = 10
        noteMode = arguments?.getInt(AddOrEditNoteActivity.NOTE_MODE, -1) ?: -1

        if (noteMode == AddOrEditNoteActivity.EDIT_NOTE){
            title?.setText(arguments?.getString(AddOrEditNoteActivity.NOTE_TITLE))
            descritpion?.setText(arguments?.getString(AddOrEditNoteActivity.NOTE_DESCRIPTION))
            priorityNumberPicker?.value = arguments?.getInt(AddOrEditNoteActivity.ID, 0) ?: 0
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addNoteViewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_note ->
                saveNote()

            else ->
                super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun saveNote() {
        val titleValue = title?.text?.toString() ?: ""
        val descriptionValue = descritpion?.text?.toString() ?: ""
        val priority = priorityNumberPicker?.value ?: 0

        context?.let {
            takeIf { !TextUtils.isEmpty(titleValue) && !TextUtils.isEmpty(descriptionValue) }?.apply {
                if (noteMode == AddOrEditNoteActivity.ADD_NOTE) {
                    addNoteViewModel?.insert(Note(titleValue, descriptionValue, priority))
                } else if(noteMode == AddOrEditNoteActivity.EDIT_NOTE) {
                    val priorityValue = arguments?.getInt(AddOrEditNoteActivity.ID, 0) ?: 0
                    val note = Note(titleValue, descriptionValue, priority)
                    note.id = priorityValue
                    addNoteViewModel?.update(note)
                }

                Toast.makeText(it, if (noteMode == AddOrEditNoteActivity.ADD_NOTE) {
                    "Note Added"
                } else {
                    "Note Edited"
                }, Toast.LENGTH_LONG).show()
                activity?.finish()
            } ?: kotlin.run {
                Toast.makeText(it, "Please add Title and Description", Toast.LENGTH_LONG).show()
            }
        }
    }
}