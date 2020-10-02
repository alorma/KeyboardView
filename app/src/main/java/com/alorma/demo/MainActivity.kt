package com.alorma.demo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentElevation = 8f
    set(value) {
        field = if (value <= 0f) {
            0f
        } else {
            value
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        savedInstanceState?.let { bundle ->
            currentElevation = bundle.getFloat(SAVED_ELEVATION, currentElevation)
        }

        updateElevations()
        updateTitle()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        currentElevation = when (item.itemId) {
            R.id.action_add_elevation -> currentElevation + 1f
            R.id.action_remove_elevation -> currentElevation - 1f
            R.id.action_reset_elevation -> 8f
            else -> currentElevation
        }

        updateElevations()
        updateTitle()

        return true
    }

    private fun updateTitle() {
        title = "Elevation: ${currentElevation.toInt()}dp"
    }

    private fun updateElevations() {
        listOf(
            keyboardDefault,
            keyboardPrimary,
            keyboardSecondary,
            keyboardError
        ).forEach {
            ViewCompat.setElevation(it, currentElevation)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putFloat(SAVED_ELEVATION, currentElevation)
    }

    companion object {
        private const val SAVED_ELEVATION = "saved_elevation"
    }
}