package com.example.permissioncontacthelper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.permissioncontacthelper.databinding.ItemLayoutBinding
import com.example.permissioncontacthelper.model.Contact

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.ContactViewHolder>(){
    var contactList:MutableList<Contact> = mutableListOf()
    lateinit var callPhone: (Contact) -> Unit
    lateinit var sendSms:(Contact) -> Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position])
    }

    override fun getItemCount(): Int = contactList.size

    inner class ContactViewHolder(private val binding:ItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(contact: Contact){
            binding.apply {
                textContact.text = contact.contact
                textNumber.text = contact.number

                btnCall.setOnClickListener {
                    callPhone(contact)
                }

                btnSms.setOnClickListener {
                    sendSms(contact)
                }
            }
        }
    }
}