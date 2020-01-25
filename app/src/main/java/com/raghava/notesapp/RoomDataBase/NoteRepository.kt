package com.raghava.notesapp.RoomDataBase

import android.app.Application
import android.os.AsyncTask
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class NoteRepository(private val application: Application) {

    private var noteDao: NoteDao? = null
    private var allNotes: LiveData<List<Note>>? = null

    init {
        noteDao = NoteDatabase.getInstance(application.applicationContext)?.noteDao()
        allNotes = noteDao?.getAllNotes()
    }

    suspend fun insert(note: Note) {
        withContext(Dispatchers.IO){
            if (Looper.getMainLooper().getThread() == Thread.currentThread()){
                Log.d("Raghava", "Main Thread")
            } else {
                Log.d("Raghava", "Background Thread")
            }

            noteDao?.insert(note)
        }
        /*val observable: Observable<Unit?> =
            Observable.fromCallable { noteDao?.insert(note) }
                .subscribeOn(Schedulers.io())
                .doOnTerminate { }
                .doOnCompleted { }
                .observeOn(AndroidSchedulers.mainThread())
        observable.subscribe(object : Observer<Unit?> {
            override fun onError(e: Throwable?) {
                Log.d("Raghava", "Failed to Add Note")
            }

            override fun onNext(t: Unit?) {
            }

            override fun onCompleted() {
                Log.d("Raghava", "Note Added")
            }

        })*/
    }

    fun update(note: Note) {
        val observable: Observable<Unit?> =
            Observable.fromCallable { noteDao?.update(note) }
                .subscribeOn(Schedulers.io())
                .doOnTerminate { }
                .doOnCompleted { }
                .observeOn(AndroidSchedulers.mainThread())
        observable.subscribe(object : Observer<Unit?> {
            override fun onError(e: Throwable?) {
                Log.d("Raghava", "Failed to Update Note")
            }

            override fun onNext(t: Unit?) {
            }

            override fun onCompleted() {
                Log.d("Raghava", "Note Updated")
            }

        })
    }

    suspend fun delete(note: Note) {
        withContext(Dispatchers.IO){
            noteDao?.delete(note)
        }
        /*val observable: Observable<Unit?> =
            Observable.fromCallable { noteDao?.delete(note) }
                .subscribeOn(Schedulers.io())
                .doOnTerminate { }
                .doOnCompleted { }
                .observeOn(AndroidSchedulers.mainThread())
        observable.subscribe(object : Observer<Unit?> {
            override fun onError(e: Throwable?) {
                Log.d("Raghava", "Failed to Delete Note")
            }

            override fun onNext(t: Unit?) {
            }

            override fun onCompleted() {
                Log.d("Raghava", "Note Deleted")
            }

        })*/
    }

    suspend fun deleteAllNotes() {
        withContext(Dispatchers.IO){
            noteDao?.deleteAllNotes()
        }
        /*val observable: Observable<Unit?> =
            Observable.fromCallable { noteDao?.deleteAllNotes() }
                .subscribeOn(Schedulers.io())
                .doOnTerminate { }
                .doOnCompleted { }
                .observeOn(AndroidSchedulers.mainThread())
        observable.subscribe(object : Observer<Unit?> {
            override fun onError(e: Throwable?) {
                Log.d("Raghava", "Failed to Delete All Note")
            }

            override fun onNext(t: Unit?) {
            }

            override fun onCompleted() {
                Log.d("Raghava", "All Notes Deleted")
            }

        })*/
    }

    fun getAllNotes(): LiveData<List<Note>>? {
        return allNotes
    }
}
