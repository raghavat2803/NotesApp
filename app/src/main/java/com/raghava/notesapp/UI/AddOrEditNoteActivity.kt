package com.raghava.notesapp.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raghava.notesapp.R

class AddOrEditNoteActivity : AppCompatActivity() {


    companion object {
        const val ADD_NOTE = 1
        const val EDIT_NOTE = 2
        const val NOTE_MODE = "NODE_MODE"
        const val NOTE_TITLE = "NOTE_TITLE"
        const val NOTE_DESCRIPTION = "NOTE_DESCRIPTION"
        const val PRIORITY = "PRIORITY"
        const val ID = "ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_activity)

        var addOrEditNoteFragment: AddOrEditNoteFragment? =
            supportFragmentManager.findFragmentByTag(AddOrEditNoteFragment::class.java.simpleName) as? AddOrEditNoteFragment
        if (addOrEditNoteFragment == null) {
            addOrEditNoteFragment = AddOrEditNoteFragment()
            val args = Bundle()
            val noteMode = intent.getIntExtra(NOTE_MODE, -1)
            if (noteMode == EDIT_NOTE) {
                args.putInt(ID, intent.getIntExtra(ID, -1))
                args.putString(
                    NOTE_TITLE, intent.getStringExtra(
                        NOTE_TITLE
                    )
                )
                args.putString(
                    NOTE_DESCRIPTION, intent.getStringExtra(
                        NOTE_DESCRIPTION
                    )
                )
                args.putInt(
                    PRIORITY, intent.getIntExtra(PRIORITY, -1)
                )
            }
            args.putInt(NOTE_MODE, noteMode)
            addOrEditNoteFragment.arguments = args
            supportFragmentManager.beginTransaction().add(
                R.id.fragment_container,
                addOrEditNoteFragment,
                AddOrEditNoteFragment::class.java.simpleName
            ).commit()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            title = if (noteMode == ADD_NOTE) {
                "Add Note"
            } else {
                "Edit Note"
            }
        }
    }


}