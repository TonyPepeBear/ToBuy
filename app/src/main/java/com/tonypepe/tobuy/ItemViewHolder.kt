package com.tonypepe.tobuy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tonypepe.tobuy.data.Item
import com.tonypepe.tobuy.databinding.TobuyItemBinding
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        fun createView(parent: ViewGroup) : ItemViewHolder =
            ItemViewHolder(
                TobuyItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ).root
            )
    }
    val binding = TobuyItemBinding.bind(view)

    fun bindView(item: Item, action: ItemAction?) {
        binding.textViewTitle.text = item.name
        binding.textViewCount.text = item.count.toString()
        action?.let {
            view.onClick { action.onClick(item) }
            binding.buttonRight.onClick { action.rightButtonClick(item) }
            binding.buttonLeft.onClick { action.leftButtonClick(item) }
            binding.textViewCount.onClick { action.onCountClick(item) }
            view.onLongClick { action.onLongClick(item) }
        }
    }
}

interface ItemAction {
    fun onClick(item: Item)
    fun rightButtonClick(item: Item)
    fun leftButtonClick(item: Item)
    fun onCountClick(item: Item)
    fun onLongClick(item: Item)
}
