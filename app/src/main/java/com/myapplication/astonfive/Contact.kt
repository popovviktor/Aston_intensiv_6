package com.myapplication.astonfive

data class Contact(
    val id:Int,
    val name:String,
    val surname:String,
    val phone_number:String,
    val photo_url:String
)
object Constants{
    const val LANDSCAPE_ORIENTATION = 2
    const val MIN_DP_FOR_TABLE = 600
    const val ID_KEY = "iditem"
}