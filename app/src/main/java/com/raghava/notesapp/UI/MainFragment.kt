package com.raghava.notesapp.UI

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.raghava.notesapp.Models.NoteViewModel
import com.raghava.notesapp.R
import com.raghava.notesapp.RoomDataBase.Note

class MainFragment : Fragment(), RecyclerViewListener {

    private var noteViewModel: NoteViewModel? = null
    private var recyclerView: RecyclerView? = null
    private var notes: List<Note>? = null
    private var notesAdapter: NotesAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_frament, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        val floatingButton: FloatingActionButton = view.findViewById(R.id.button_add_note)
        floatingButton.setOnClickListener {
            context?.apply {
                val intent = Intent(this, AddOrEditNoteActivity::class.java)
                intent.putExtra(AddOrEditNoteActivity.NOTE_MODE, AddOrEditNoteActivity.ADD_NOTE)
                startActivity(intent)
            }
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_activity_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.delete_all -> {
                noteViewModel?.deleteAll()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRecyclerView()

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        noteViewModel?.getAllNotes()?.observe(this, Observer { notes ->
            context?.apply {
                notes?.let {
                    notesAdapter?.submitList(it)
                }
            }
        })
    }

    private fun setRecyclerView() {
        context?.apply {

            val diffCallback: ItemCallback<Note> = object: ItemCallback<Note>() {
                override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                    return oldItem.title == newItem.title && oldItem.description == newItem.description && oldItem.priority == newItem.priority
                }

            }
            notesAdapter = NotesAdapter(this, this@MainFragment, diffCallback)
            recyclerView?.layoutManager = LinearLayoutManager(this)
            recyclerView?.adapter = notesAdapter
        }

        val itemTouchHelper = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                notesAdapter?.getNoteAt(viewHolder.adapterPosition)?.let {
                    noteViewModel?.delete(it)
                }
                context?.apply {
                    Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView)
    }

    override fun onItemClicked(note: Note?) {
        note?.apply {
            context?.let {
                val intent = Intent(it, AddOrEditNoteActivity::class.java)
                intent.putExtra(AddOrEditNoteActivity.NOTE_MODE, AddOrEditNoteActivity.EDIT_NOTE)
                intent.putExtra(AddOrEditNoteActivity.ID, this.id)
                intent.putExtra(AddOrEditNoteActivity.NOTE_TITLE, this.title)
                intent.putExtra(AddOrEditNoteActivity.NOTE_DESCRIPTION, this.description)
                intent.putExtra(AddOrEditNoteActivity.PRIORITY, this.priority)
                startActivity(intent)
            }
        }
    }

}
