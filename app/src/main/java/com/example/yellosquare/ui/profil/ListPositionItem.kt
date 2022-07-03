package com.example.yellosquare.ui.profil

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yellosquare.R
import com.example.yellosquare.model.Square
import com.example.yellosquare.utils.KauIItem

class ListPositionItem(var data: Square) : KauIItem<ListPositionItem, ListPositionItem.ViewHolder>(
    R.layout.list_position_item, { ViewHolder(it) }, R.id.fastadapter_listitem_id
) {

    @SuppressLint("SetTextI18n")
    override fun bindView(holder: ViewHolder, payloads: MutableList<Any>) {
        super.bindView(holder, payloads)

        holder.textView.text = "X : ${data.positionX} - Y : ${data.positionY}"
    }

    override fun unbindView(holder: ViewHolder) {}

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView = itemView.findViewById(R.id.position) as TextView
    }
}
