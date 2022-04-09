package com.example.livedatapractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
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
      var numberTxv = findViewById<TextView>(R.id.txvNumber)
      var questionTxv = findViewById<TextView>(R.id.tvQuestion)
      var hintTxv = findViewById<TextView>(R.id.txv_hint)
      var nextBtn = findViewById<Button>(R.id.btn_next)
      var backBtn = findViewById<Button>(R.id.btn_back)
      val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        var answer1Txv = findViewById<TextView>(R.id.txv_answer1)
        var answer2Txv = findViewById<TextView>(R.id.txv_answer2)
        var answer3Txv = findViewById<TextView>(R.id.txv_answer3)
        var answer4Txv = findViewById<TextView>(R.id.txv_answer4)
        val scoreTxv = findViewById<TextView>(R.id.txv_score)

        var answersTextViews = arrayListOf<TextView>(
            answer1Txv, answer2Txv, answer3Txv, answer4Txv
        )
        answerClick(answersTextViews)

        progressBar.max = vmodel.questionCount

        nextBtn.setOnClickListener {
            vmodel.nextClicked()
        }

        backBtn.setOnClickListener {
            vmodel.backClicked()
        }

        val numberObserver = Observer<Int> { number ->
            numberTxv.text = number.toString()
            progressBar.progress = number
        }

        val buttonEnabledObserver = Observer<Boolean>{  enabled ->
            nextBtn.isEnabled = enabled
        }

        val backBtnEnabledObserver = Observer<Boolean>{  enabled ->
            backBtn.isEnabled = enabled
        }

        val questionObserver = Observer<String>{ question ->
            questionTxv.text = question
        }

        val hintObserver = Observer<String>{ hint ->
            hintTxv.text = hint
        }

        val answerObserver = Observer<ArrayList<Int>> {
           for (i in 0 until answersTextViews.size) {
               answersTextViews[i].text = it[i].toString()
           }
        }

        vmodel.questionText.observe(this , questionObserver)
        vmodel.nextEnabledLiveData.observe(this , buttonEnabledObserver)
        vmodel.backEnabledLiveData.observe(this,backBtnEnabledObserver)
        vmodel.questionNumber.observe(this , numberObserver)
        vmodel.hintText.observe(this, hintObserver)
        vmodel.answerList.observe(this, answerObserver)
        vmodel.answerEnabledLiveData.observe(this) {
            for (answer in answersTextViews) {
                answer.isEnabled = it
            }
        }
        vmodel.score.observe(this){
            scoreTxv.text = it.toString()
        }

    }

    private fun answerClick(answers: ArrayList<TextView>) {
        for (i in 0 until answers.size){
            answers[i].setOnClickListener {
                vmodel.checkAnswer(answers[i].text.toString().toInt())
                Toast.makeText(this, "clicked" ,Toast.LENGTH_SHORT).show()
            }
        }

    }

}