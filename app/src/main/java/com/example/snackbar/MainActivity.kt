package com.example.snackbar

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.snackbar.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter:ItemAdapter
    var listItems = ArrayList<String>()

    var undoOnClickListener: View.OnClickListener = View.OnClickListener {
        listItems.removeAt(listItems.size - 1)
        itemAdapter.notifyDataSetChanged()
        Snackbar.make(it, "Item removed", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        itemAdapter = ItemAdapter(listItems)
        binding.recyclerView.adapter = itemAdapter

        binding.fab.setOnClickListener { view ->
            addListItem()
            Snackbar.make(view, "Item added to list", Snackbar.LENGTH_LONG)
                .setAction("Undo", undoOnClickListener).show()
        }
    }

    private fun addListItem() {
        val dateformat: SimpleDateFormat = SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US)
        listItems.add(dateformat.format(Date()))
        itemAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}