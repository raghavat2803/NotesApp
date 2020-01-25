package com.raghava.notesapp.UI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raghava.notesapp.R
import com.raghava.notesapp.RoomDataBase.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NotesAdapter(
    private val context: Context,
    private val recyclerViewListener: RecyclerViewListener,
    private val diffCallBack: DiffUtil.ItemCallback<Note>
) :
    ListAdapter<Note, NotesViewHolder>(diffCallBack) {

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note: Note? = getItem(position)
        note?.title?.let {
            holder.itemView.title.text = it
        }

        note?.description?.let {
            holder.itemView.description.text = it
        }

        note?.priority?.let {
            holder.itemView.priority.text = it.toString()
        }

        holder.itemView.setOnClickListener {
            recyclerViewListener.onItemClicked(
                getItem(position)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.note_item,
                parent,
                false
            )
        )
    }

    fun getNoteAt(position: Int): Note? {
        return getItem(position)
    }
}

class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var title: TextView? = null
    private var description: TextView? = null
    private var priority: TextView? = null

    init {
        title = itemView.findViewById(R.id.title)
        description = itemView.findViewById(R.id.description)
        priority = itemView.findViewById(R.id.priority)
    }
}

interface RecyclerViewListener {
    fun onItemClicked(note: Note?)
}