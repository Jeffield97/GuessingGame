package com.sox.guessinggame

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sox.guessinggame.databinding.FragmentGameBinding

class GameViewModel :ViewModel(){

    val words= listOf<String>("Computadora","GTA5","Celular","Pasta","Naranja","Zeref","Franklin","Dino","Brown")

    val secretWord= words.random().uppercase()
    var secretWord_display=MutableLiveData("")
    var incorrectGuesses=MutableLiveData("")
    var correctGuesses=""
    var livesleft=MutableLiveData(3)

    init {
        secretWord_display.value = deriveSecretWordDisplay()
    }



    fun checkLetter(string: String) = when(correctGuesses.contains(string))
    {
        true->string
        false->"_"
    }

    fun makeGuess(guess:String)
    {
        if(guess.length==1)
        {
            if (secretWord.contains(guess))
            {
                correctGuesses+=guess
                secretWord_display.value=deriveSecretWordDisplay()
            }
            else
            {
                incorrectGuesses.value+="$guess"
                livesleft.value = livesleft.value?.minus(1)
            }
        }
    }

    fun deriveSecretWordDisplay():String
    {
        var display=""
        secretWord.forEach {
            display+=checkLetter(it.toString())
        }
        return  display
    }

    fun isWon()= secretWord.equals(secretWord_display.value,true)
    fun isLost()= livesleft.value!! <=0

    fun wonLostMessage():String
    {
        var message=""
        if(isWon()) message="Has ganado"
        if(isLost()) message="Has perdido"

        return  "$message la palabra era $secretWord"
    }
}