package com.myapplication.astonfive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapplication.astonfive.databinding.ItemBinding
import com.squareup.picasso.Picasso

class AdapterForContactList:RecyclerView.Adapter<AdapterForContactList.ViewHolderForCtontactList>(){
    var contact_list = listOf<Contact>()
        set(value){
            val callback = ContactListDiffCallback(contact_list,value)
            val dif_result = DiffUtil.calculateDiff(callback)
            dif_result.dispatchUpdatesTo(this)
            field = value
        }
    var onclickListener:onClickListener? = null
    var onlongClickListener:onLongClickListener? = null
    class ViewHolderForCtontactList(item: View):RecyclerView.ViewHolder(item) {
        private val binding = ItemBinding.bind(item)
        fun bind(item:Contact) = with(binding){
            itemName.setText(item.name)
            itemSurname.setText(item.surname)
            itemPhoneNumber.setText(item.phone_number)
            loadAndSetImageToImageView(item.photo_url,imageView)
        }
        fun loadAndSetImageToImageView(path:String,imageView:ImageView) {
            Picasso.get().load(path).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderForCtontactList {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolderForCtontactList(view)
    }

    override fun onBindViewHolder(holder: ViewHolderForCtontactList, position: Int) {
        holder.itemView.setOnClickListener {
            onclickListener?.clickListener(contact_list.get(position))
        }
        holder.itemView.setOnLongClickListener {
            onlongClickListener?.longClickListener(contact_list.get(position))
            true
        }
        return holder.bind(contact_list.get(position))
    }
    override fun getItemCount(): Int {
        return contact_list.size
    }
    interface onClickListener{
        fun clickListener(contact: Contact)
    }
    interface onLongClickListener{
        fun longClickListener(contact: Contact)
    }

}