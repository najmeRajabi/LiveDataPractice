package com.example.livedatapractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    val vmodel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    fun initViews(){
      var numberTxv = findViewById<TextView>(R.id.tvNumber)
      var questionTxv = findViewById<TextView>(R.id.tvQuestion)
      var nextBtn = findViewById<Button>(R.id.button1)
      var backBtn = findViewById<Button>(R.id.button)
      var progressBar = findViewById<ProgressBar>(R.id.progressBar)

        progressBar.max = vmodel.questionCount

        nextBtn.setOnClickListener {
            vmodel.nextClicked()
        }

        val numberObserver = Observer<Int> { number ->
            numberTxv.text = number.toString()
            progressBar.progress = number
        }

        val buttonEnabledObserver = Observer<Boolean>{  enabled ->
            nextBtn.isEnabled = enabled
        }

        val questionObserver = Observer<String>{ question ->
            questionTxv.text = question
        }

        vmodel.questionText.observe(this , questionObserver)
        vmodel.nextEnabledLiveData.observe(this , buttonEnabledObserver)
        vmodel.questionNumber.observe(this , numberObserver)

    }
}