package com.raghava.notesapp.UI

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.raghava.notesapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val mainFragment = supportFragmentManager.findFragmentByTag(MainFragment::class.java.name)
        if (mainFragment == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment(), MainFragment::class.java.name)
                .commit()
        }
    }
}