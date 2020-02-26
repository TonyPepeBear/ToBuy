package com.tonypepe.tobuy.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tonypepe.tobuy.*
import com.tonypepe.tobuy.data.Item
import com.tonypepe.tobuy.databinding.FragmentMainBinding
import com.tonypepe.tobuy.databinding.ItemAlertCountBinding
import com.tonypepe.tobuy.databinding.ItemAlertInputBinding
import com.tonypepe.tobuy.receiver.ItemNotificationReceiver
import org.jetbrains.anko.alert
import org.jetbrains.anko.support.v4.alert
import java.util.*

class MainFragment : Fragment(), ItemAction {
    companion object {
        const val REQUEST_CODE_NOTIFICATION = 3298
    }

    lateinit var binding: FragmentMainBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            val inputBinding = ItemAlertInputBinding.inflate(layoutInflater)
            activity?.alert {
                title = getString(R.string.add_item)
                customView = inputBinding.root
                positiveButton(getString(R.string.ok)) {
                    val text = inputBinding.inputText.text
                    viewModel.insertItem(Item(text.toString(), 1, 0))
                    logd("insert item $text")
                }
                negativeButton(R.string.cancel) {}
                isCancelable = false
                show()
            }
        }
        binding.recycler.apply {
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

    override fun onClick(item: Item) {
        logd("onClick $item")
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ItemNotificationReceiver::class.java).apply {
            putExtra(ItemNotificationReceiver.STRING_NAME, item.name)
            putExtra(ItemNotificationReceiver.STRING_TEXT, item.count.toString())
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_NOTIFICATION,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            Calendar.getInstance().timeInMillis,
            pendingIntent
        )
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
        val itemAlertCountBinding = ItemAlertCountBinding.inflate(layoutInflater)
        itemAlertCountBinding.inputNumber.text = SpannableStringBuilder(item.count.toString())
        alert {
            title = getString(R.string.set_number)
            customView = itemAlertCountBinding.root
            positiveButton(R.string.ok) {
                val number = itemAlertCountBinding.inputNumber.text.toString().toIntOrNull()
                if (number == null) {
                    binding.root.snackBar("Input Error")
                } else {
                    item.count = number
                    viewModel.updateItem(item)
                }
            }
            negativeButton(R.string.cancel) {}
            show()
        }
    }

    override fun onLongClick(item: Item) {
        logd("Long Click")
        viewModel.deleteItem(item)
        Snackbar.make(
            binding.fab,
            getString(R.string.delete_item_success, item.name),
            Snackbar.LENGTH_LONG
        )
            .apply {
                setAction(R.string.undo) {
                    viewModel.insertItem(item)
                }
                show()
            }
    }
}
