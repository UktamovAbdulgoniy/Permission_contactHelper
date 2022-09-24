package com.example.permissioncontacthelper.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    var contact:String? = null,
    var number: String? = null
):Parcelable
