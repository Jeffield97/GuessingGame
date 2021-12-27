package com.sox.guessinggame

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.sox.guessinggame.databinding.FragmentGameBinding

class GameViewModel :ViewModel(){

    val words= listOf<String>("Android","Kotlin","Fragment","Activity")

    val secretWord= words.random().uppercase()
    var secretWord_display=""
    var incorrectGuesses=""
    var correctGuesses=""
    var livesleft=3

    init {
        secretWord_display= deriveSecretWordDisplay()
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
                secretWord_display=deriveSecretWordDisplay()
            }
            else
            {
                incorrectGuesses+="$guess"
                livesleft--
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

    fun isWon()= secretWord.equals(secretWord_display,true)
    fun isLost()=livesleft<=0

    fun wonLostMessage():String
    {
        var message=""
        if(isWon()) message="Has ganado"
        if(isLost()) message="Has perdido"

        return  "$message la palabra era $secretWord"
    }
}