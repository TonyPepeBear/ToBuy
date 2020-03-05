package com.tonypepe.tobuy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tonypepe.tobuy.data.Item
import com.tonypepe.tobuy.databinding.TobuyItemBinding

class ItemAdapter : PagedListAdapter<Item, ItemViewHolder>(
    object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            logd("diff: $oldItem\n$newItem")
            return false
        }
    }
) {
    var action: ItemAction? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder.createView(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindView(it, action)
        }
    }
}
