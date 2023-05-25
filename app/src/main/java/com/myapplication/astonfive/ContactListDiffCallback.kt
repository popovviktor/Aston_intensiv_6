package com.myapplication.astonfive

import androidx.recyclerview.widget.DiffUtil

class ContactListDiffCallback(
    private val oldList:List<Contact>,
    private val newList:List<Contact>
):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val olditem = oldList.get(oldItemPosition)
        val newItem = newList.get(newItemPosition)
        return olditem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val olditem = oldList.get(oldItemPosition)
        val newItem = newList.get(newItemPosition)
        return olditem == newItem
    }
}