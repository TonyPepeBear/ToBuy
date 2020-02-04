package com.tonypepe.tobuy

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.tonypepe.tobuy.data.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), ItemAction {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            viewModel.insert(Item("Egg", 1))
            // TODO: add item
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
        } else {
            item.count--
            viewModel.updateItem(item)
        }
    }
}
