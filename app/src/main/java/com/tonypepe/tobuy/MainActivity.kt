package com.tonypepe.tobuy

import android.os.Bundle
import android.text.SpannableStringBuilder
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
import kotlinx.android.synthetic.main.item_alert_count.view.*
import kotlinx.android.synthetic.main.item_alert_input.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton

class MainActivity : AppCompatActivity(), ItemAction {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.item_alert_input, null)
            alert {
                title = getString(R.string.add_item)
                customView = view
                positiveButton(getString(R.string.ok)) {
                    val text = view.input_text.text
                    viewModel.insertItem(Item(text.toString(), 1))
                    logd("insert item $text")
                }
                cancelButton { }
                show()
            }
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
        logd("rightButtonClick")
        item.count++
        viewModel.updateItem(item)
    }

    override fun leftButtonClick(item: Item) {
        logd("leftButtonClick")
        if (item.count > 0) {
            item.count--
            viewModel.updateItem(item)
        }
    }

    override fun onCountClick(item: Item) {
        logd("onCountClick")
        val view = layoutInflater.inflate(R.layout.item_alert_count, null)
        view.input_number.text = SpannableStringBuilder(item.count.toString())
        alert {
            title = getString(R.string.set_number)
            customView = view
            positiveButton(R.string.ok) {
                val number = view.input_number.text.toString().toIntOrNull()
                if (number == null) {
                    view.snackBar("Input Error")
                } else {
                    item.count = number
                    viewModel.updateItem(item)
                }
            }
            cancelButton {}
            show()
        }
    }

    override fun longClick(item: Item) {
        logd("longClick")
        viewModel.deleteItem(item)
        Snackbar.make(
            recycler,
            getString(R.string.delete_item_success, item.name),
            Snackbar.LENGTH_LONG
        )
            .setAction(R.string.undo) { viewModel.insertItem(item) }
            .show()
    }
}
