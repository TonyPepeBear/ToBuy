package com.tonypepe.tobuy

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tonypepe.tobuy.data.Item
import kotlinx.android.synthetic.main.tobuy_item.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val title = view.textView_title
    val count = view.textView_count
    val buttonLeft = view.button_left
    val buttonRight = view.button_right

    fun bindView(item: Item, action: ItemAction?) {
        title.text = item.name
        count.text = item.count.toString()
        action?.let {
            view.onClick { action.onClick(item) }
            buttonRight.onClick { action.rightButtonClick(item) }
            buttonLeft.onClick { action.leftButtonClick(item) }
            count.onClick { action.onCountClick(item) }
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
