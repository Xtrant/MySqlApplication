package com.example.mysqlapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    val id : String,
    val fname : String,
    val lname : String,
    val email : String,
    val major : String
) : Parcelable
