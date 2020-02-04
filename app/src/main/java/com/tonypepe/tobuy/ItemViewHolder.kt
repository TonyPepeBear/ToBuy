package com.tonypepe.tobuy

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tonypepe.tobuy.data.Item
import kotlinx.android.synthetic.main.tobuy_item.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.textView_title
    val count = view.textView_count
    val buttonLeft = view.button_left
    val buttonRight = view.button_right

    fun bindView(item: Item, action: ItemAction?) {
        title.text = item.name
        count.text = item.count.toString()
        action?.let {
            buttonRight.onClick { action.rightButtonClick(item) }
            buttonLeft.onClick { action.leftButtonClick(item) }
        }
    }
}

interface ItemAction {
    fun rightButtonClick(item: Item)
    fun leftButtonClick(item: Item)
}
