package com.tonypepe.tobuy

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tonypepe.tobuy.data.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.input_item.view.*
import org.jetbrains.anko.alert

class MainActivity : AppCompatActivity(), ItemAction {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.input_item, null)
            alert {
                title = getString(R.string.add_item)
                customView = view
                positiveButton(getString(R.string.ok)) {
                    val text = view.input_text.text
                    viewModel.insertItem(Item(text.toString(), 1))
                    logd("insert item $text")
                }
            }.show()
            viewModel.insertItem(Item("Egg", 1))
        }
        // recycler
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            val itemAdapter = ItemAdapter()
            adapter = itemAdapter
            itemAdapter.action = this@MainActivity
        }
        viewModel.getAllLiveData().observe(this) {
            val itemAdapter = recycler.adapter as ItemAdapter
            itemAdapter.submitList(it)
        }
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

    override fun rightButtonClick(item: Item) {
        item.count++
        viewModel.updateItem(item)
    }

    override fun leftButtonClick(item: Item) {
        if (item.count < 2) {
            viewModel.deleteItem(item)
            Snackbar.make(recycler, "Deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO") {
                    viewModel.insertItem(item)
                }.show()
        } else {
            item.count--
            viewModel.updateItem(item)
        }
    }
}
