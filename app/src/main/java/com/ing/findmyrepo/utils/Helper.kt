package com.ing.findmyrepo.utils

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.LiveData

object Helper {

    fun Activity.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}