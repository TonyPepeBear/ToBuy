package com.tonypepe.tobuy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tonypepe.tobuy.data.Item

class ItemAdapter : PagedListAdapter<Item, ItemViewHolder>(
    object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.tobuy_item, parent, true)
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
