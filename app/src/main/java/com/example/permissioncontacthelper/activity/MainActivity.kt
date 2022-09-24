package com.example.permissioncontacthelper.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.permissioncontacthelper.R
import com.example.permissioncontacthelper.adapter.RecyclerViewAdapter
import com.example.permissioncontacthelper.databinding.ActivityMainBinding
import com.example.permissioncontacthelper.model.Contact


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private var contactList1:MutableList<Contact> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.text_activity)

        setupRv()
        permission()
        action()

    }



    private fun action(){
        recyclerViewAdapter.callPhone = {
            val intent = Intent(Intent.ACTION_CALL)
            val uri = Uri.parse("tel:${it.number}")
            intent.data = uri
            startActivity(intent)
        }
        recyclerViewAdapter.sendSms = {
            val bundle = bundleOf("contact" to it)
            val intent = Intent(this,SecondActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)

        }
    }

    private fun setupRv() = binding.recyclerView.apply {
        recyclerViewAdapter = RecyclerViewAdapter()
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = recyclerViewAdapter
        recyclerViewAdapter.contactList = contactList1
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            readContacts()
        }
    }
    private fun permission(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS,Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS),100)
        }else{
            readContacts()
        }
    }
    private fun readContacts(){
        val contact = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        while (contact!!.moveToNext()){
            val name =
                contact.getString(contact.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
                contact.getString(contact.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            contactList1.add(Contact(name,number))
            recyclerViewAdapter.notifyDataSetChanged()
        }
        contact.close()
    }

}