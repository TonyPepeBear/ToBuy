package com.tonypepe.tobuy

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tonypepe.tobuy.data.Item
import kotlinx.android.synthetic.main.tobuy_item.view.*

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.textView_title
    val count = view.textView_count
    val buttonLeft = view.button_left
    val buttonRight = view.button_right

    fun bindView(item: Item) {
        title.text = item.name
        count.text = item.count.toString()
    }
}
