package com.example.practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    val vmodel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }



    fun initViews(){
      val numberTxv = findViewById<TextView>(R.id.txvNumber)
      val questionTxv = findViewById<TextView>(R.id.tvQuestion)
      val hintTxv = findViewById<TextView>(R.id.txv_hint)
      val nextBtn = findViewById<Button>(R.id.btn_next)
      val backBtn = findViewById<Button>(R.id.btn_back)
      val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val answer1Txv = findViewById<TextView>(R.id.txv_answer1)
        val answer2Txv = findViewById<TextView>(R.id.txv_answer2)
        val answer3Txv = findViewById<TextView>(R.id.txv_answer3)
        val answer4Txv = findViewById<TextView>(R.id.txv_answer4)
        val scoreTxv = findViewById<TextView>(R.id.txv_score)
        val scoreText =findViewById<TextView>(R.id.txv_score_txt)
        scoreTxv.setTextColor(ContextCompat.getColor(this,R.color.red))

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
        vmodel.scoreColor.observe(this){
            scoreTxv.setTextColor(ContextCompat.getColor(
                this,
                chooseColor(it))
            )
        }

    }

    private fun chooseColor(it: Color?): Int {

        return  when (it) {
            Color.Green -> R.color.green
            Color.Yellow -> R.color.yellow
            else -> R.color.red
        }
    }


    private fun answerClick(answers: ArrayList<TextView>) {
        for (i in 0 until answers.size){
            answers[i].setOnClickListener {
                vmodel.checkAnswer(answers[i].text.toString().toInt())
            }
        }

    }

}