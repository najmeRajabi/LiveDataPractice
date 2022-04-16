package com.example.livedatapractice

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question (
    @PrimaryKey(autoGenerate = true) val number: Int =1,
    val question :String,
    val answer: Int,
    var answered : Boolean=false,
    var correct: Boolean= false)  {
}