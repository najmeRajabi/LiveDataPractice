package com.example.practice

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class MainViewModel(app:Application):AndroidViewModel(app) {

    val questionCount = QuestionRepository.questionList.size-1
    val questionNumber = MutableLiveData<Int>(0)
    val hintText = Transformations.map(questionNumber) {
        if (it < questionCount / 2)
            "faster"
        else
            "you are dying"
    }

    val questionText = MutableLiveData<String>(
        QuestionRepository.questionList[0].question
    )
    private val answerText = MutableLiveData<Int>(
        QuestionRepository.questionList[0].answer
    )
    val answerList = MutableLiveData<ArrayList<Int>>(
        arrayListOf(QuestionRepository.questionList[0].answer,
        fakeAnswer()[0],fakeAnswer()[1],fakeAnswer()[2])
    )
    val score = MutableLiveData<Int>(0)
    val scoreColor = Transformations.map(score){
        when {
            it<3 -> {
                Color.Red
            }
            it in 3..6 -> {
                Color.Yellow
            }
            else -> {
                Color.Green
            }
        }
    }
    var nextEnabledLiveData = MutableLiveData<Boolean>(true)
    var backEnabledLiveData = MutableLiveData<Boolean>(false)
    var answerEnabledLiveData = MutableLiveData<Boolean>(true)

    init {

    }

    fun nextClicked() {
        if (questionNumber.value!! == questionCount-1) {
            questionNumber.value = questionNumber.value?.plus(1)
            nextEnabledLiveData.value = false
        }else{
            backEnabledLiveData.value = true
            questionNumber.value = questionNumber.value?.plus(1)
        }
        questionNumber.value?.let{ number ->
            questionText.value = QuestionRepository.questionList[number].question
            answerText.value = QuestionRepository.questionList[number].answer
        }
        allAnswers()
        enableAnswers()

    }
    fun backClicked() {
        if (questionNumber.value!! == 1) {
            questionNumber.value = questionNumber.value?.minus(1)
            backEnabledLiveData.value = false
        }else{
            nextEnabledLiveData.value = true
            questionNumber.value = questionNumber.value?.minus(1)
        }
        questionNumber.value?.let{ number ->
            questionText.value = QuestionRepository.questionList[number].question
            answerText.value = QuestionRepository.questionList[number].answer
        }
        allAnswers()
        enableAnswers()
    }
    private fun fakeAnswer(): ArrayList<Int> {
        var fakes = arrayListOf<Int>()
        var fake =QuestionRepository.questionList[questionNumber.value!!].answer
        for (i in 0..2) {
            while (fake == QuestionRepository.questionList[questionNumber.value!!].answer
                || fakes.contains(fake)) {
                fake = ((0..180).random())
            }
            fakes.add(fake)
        }
        return fakes
    }

    private fun allAnswers() {
        var answers = fakeAnswer()
        answerText.value?.let { answers.add(it) }
        answers.shuffle()
        answerList.value = answers
    }

    fun checkAnswer(answer:Int){
        if (answer == answerText.value){
            score.value=score.value?.plus(2)

            Log.d("TAG", "correct "+score.value.toString())

        }
        else {
            score.value=score.value?.minus(1)
            Log.d("TAG", "incorrect "+score.value.toString())
        }
        setAnswered()
        disableAnswers()
    }
    private fun disableAnswers(){
        questionNumber.value?.let {
            if (QuestionRepository.questionList[it].answered){
                answerEnabledLiveData.value = false
            }
        }
    }
    private fun enableAnswers(){
        questionNumber.value?.let {
            if (!QuestionRepository.questionList[it].answered){
                answerEnabledLiveData.value = true
            }
        }
    }
    fun setAnswered(){
        questionNumber.value?.let {
            QuestionRepository.questionList[it].answered = true
        }
    }

}