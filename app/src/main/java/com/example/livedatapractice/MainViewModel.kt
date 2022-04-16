package com.example.livedatapractice

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class MainViewModel(app:Application):AndroidViewModel(app) {

    private lateinit var  questionList : List<Question>
    lateinit var question  : LiveData<Question>
    lateinit var questionCount : LiveData<Int>
    val questionNumber = MutableLiveData<Int>(1)
    val hintText = Transformations.map(questionNumber) {
        questionCount.value?.let {
            if (questionNumber.value!! < it.div(2))
                "faster"
            else
                "you are dying"
        }
    }

    val questionText = MutableLiveData<String>(
        QuestionRepository.getQuestion(1)?.question
    )
    private val answerText = MutableLiveData<Int>(
        QuestionRepository.getQuestion(1)?.answer
    )
    val answerList = MutableLiveData<ArrayList<Int>>(
        QuestionRepository.getQuestion(1)?.let {
            arrayListOf(
                it.answer,
            fakeAnswer()[0],fakeAnswer()[1],fakeAnswer()[2])
        }
    )
    val score = MutableLiveData<Int>(0)
    val scoreColor = Transformations.map(score){
        when {
            it< (questionCount.value?.times(2) )?.div(3) ?: 3 -> {
                Color.Red
            }
            it == (questionCount.value?.times(2) )?.div(3)?.rangeTo((questionCount.value?.times(2) )?.div(2/3)!!) ?: 3 -> {
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

        QuestionRepository.initDB(app.applicationContext)

        questionList = QuestionRepository.getQuestions()!!
        question = QuestionRepository.getQuestionLiveData(1)!!

        questionCount = QuestionRepository.countAllQuestions()!!
        questionNumber.value?.let{ number ->
            questionText.value = QuestionRepository.getQuestion(number)?.question
            answerText.value = QuestionRepository.getQuestion(number)?.answer
        }
    }

    fun nextClicked() {
        if (questionNumber.value!! == questionCount.value?.minus(1) ) {
            questionNumber.value = questionNumber.value?.plus(1)
            nextEnabledLiveData.value = false
        }else{
            backEnabledLiveData.value = true
            questionNumber.value = questionNumber.value?.plus(1)
        }
        questionNumber.value?.let{ number ->
            questionText.value = QuestionRepository.getQuestion(number)?.question
            answerText.value = QuestionRepository.getQuestion(number)?.answer
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
            questionText.value = QuestionRepository.getQuestion(number)?.question
            answerText.value = QuestionRepository.getQuestion(number)?.answer
        }
        allAnswers()
        enableAnswers()
    }
    private fun fakeAnswer(): ArrayList<Int> {
        var fakes = arrayListOf<Int>()
        var fake = questionNumber.value?.let { QuestionRepository.getQuestion(it)?.answer }
        for (i in 0..2) {
            while (fake == questionNumber.value?.let { QuestionRepository.getQuestion(it)?.answer }
                || fakes.contains(fake)) {
                fake = ((0..180).random())
            }
            if (fake != null) {
                fakes.add(fake)
            }
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
            if (QuestionRepository.getQuestion(it)?.answered == true){
                answerEnabledLiveData.value = false
            }
        }
    }
    private fun enableAnswers(){
        questionNumber.value?.let {
            if (QuestionRepository.getQuestion(it)?.answered != null){
                answerEnabledLiveData.value = true
            }
        }
    }
    fun setAnswered(){
        questionNumber.value?.let {
            QuestionRepository.getQuestion(it)?.answered = true
        }
    }

    fun addRandomQuestion(){
        nextEnabledLiveData.value = true
        QuestionRepository.insertQuestionRandom()
    }

}