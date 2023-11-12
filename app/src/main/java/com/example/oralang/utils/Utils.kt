package com.example.oralang.utils

import android.content.Context
import android.widget.Toast

fun toast(context: Context, message:String){
    try {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }catch (ex:Exception){
        ex.printStackTrace()
    }
}