package com.example.permissioncontacthelper.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.permissioncontacthelper.R
import com.example.permissioncontacthelper.databinding.ActivitySecondBinding
import com.example.permissioncontacthelper.model.Contact

class SecondActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySecondBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.text_sendSms)

        val contact = intent.getParcelableExtra<Contact>("contact")

        binding.apply {
            textContact.text = contact?.contact
            textNumber.text = contact?.number
        }
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finishAffinity()
        }


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 100)
        } else {
            binding.btnSend.setOnClickListener {
                val message = binding.editSms.text.toString().trim()
                val smsManager = SmsManager.getDefault()
                try {
                    smsManager.sendTextMessage("${contact?.number}", null, message, null, null)
                    Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, e.stackTraceToString(), Toast.LENGTH_SHORT).show()
                    Log.d("@@@", e.stackTraceToString())
                }
            }
        }
    }
}