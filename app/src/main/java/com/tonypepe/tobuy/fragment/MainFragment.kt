package com.tonypepe.tobuy.fragment

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tonypepe.tobuy.*
import com.tonypepe.tobuy.data.Item
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.item_alert_count.view.*
import kotlinx.android.synthetic.main.item_alert_input.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.support.v4.alert

class MainFragment : Fragment(R.layout.fragment_main), ItemAction {
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.item_alert_input, null)
            activity?.alert {
                title = getString(R.string.add_item)
                customView = view
                positiveButton(getString(R.string.ok)) {
                    val text = view.input_text.text
                    viewModel.insertItem(Item(text.toString(), 1, 0))
                    logd("insert item $text")
                }
                cancelButton {

                }
                isCancelable = false
                show()
            }
        }
        recycler.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            val itemAdapter = ItemAdapter().apply {
                action = this@MainFragment
            }
            adapter = itemAdapter
            viewModel.getAllLiveData().observe(this@MainFragment) {
                itemAdapter.submitList(it)
            }
        }
        logd(findNavController())
    }

    override fun rightButtonClick(item: Item) {
        logd("Right Button Click")
        item.count++
        viewModel.updateItem(item)
    }

    override fun leftButtonClick(item: Item) {
        logd("Left Button Click")
        if (item.count > 0) {
            item.count--
        }
        viewModel.updateItem(item)
    }

    override fun onCountClick(item: Item) {
        logd("On Count Click")
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

    override fun onLongClick(item: Item) {
        logd("Long Click")
        viewModel.deleteItem(item)
        Snackbar.make(fab, getString(R.string.delete_item_success, item.name), Snackbar.LENGTH_LONG)
            .apply {
                setAction(R.string.undo) {
                    viewModel.insertItem(item)
                }
                show()
            }
    }
}
