package com.example.livedatapractice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {

    val questionCount = QuestionRepository.questionList.size-1

    val questionNumber = MutableLiveData<Int>(0)


    val questionText = MutableLiveData<String>(
        QuestionRepository.questionList[0]
    )


    var nextEnabledLiveData = MutableLiveData<Boolean>(true)

    fun nextClicked() {
        if (questionNumber.value!! == questionCount) {
            nextEnabledLiveData.value = false
        }else{
            questionNumber.value = questionNumber.value?.plus(1)
        }
        questionNumber.value?.let{ number ->
            questionText.value = QuestionRepository.questionList[number]
        }
    }

}