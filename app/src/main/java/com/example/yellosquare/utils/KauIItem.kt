package com.example.yellosquare.utils

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mikepenz.fastadapter.IClickable
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.items.AbstractItem

open class KauIItem<Item, VH : RecyclerView.ViewHolder>(
    @param:LayoutRes private val layoutRes: Int,
    private val viewHolder: (v: View) -> VH,
    private val type: Int = layoutRes
) : AbstractItem<Item, VH>() where Item : IItem<*, *>, Item : IClickable<*> {

    @SuppressLint("ResourceType")

    final override fun getType(): Int = type

    final override fun getViewHolder(v: View): VH = viewHolder(v)
    final override fun getLayoutRes(): Int = layoutRes

    protected open fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}