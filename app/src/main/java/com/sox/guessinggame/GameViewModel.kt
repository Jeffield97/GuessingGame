package com.sox.guessinggame

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sox.guessinggame.databinding.FragmentGameBinding

class GameViewModel :ViewModel(){

    val words= listOf<String>("Computadora","GTA5","Celular","Pasta","Naranja","Zeref","Franklin","Dino","Brown")

    private val secretWord= words.random().uppercase()
    private var _secretWord_display=MutableLiveData("")
    val secretWord_display:LiveData<String>
    get()=_secretWord_display

    private var _incorrectGuesses=MutableLiveData("")
    val incorrectGuesses:LiveData<String>
    get()=_incorrectGuesses

    private var correctGuesses=""
    private var _livesleft=MutableLiveData(3)
    val livesleft:LiveData<Int>
    get()=_livesleft



    init {
        _secretWord_display.value = deriveSecretWordDisplay()
    }



    private  fun checkLetter(string: String) = when(correctGuesses.contains(string))
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
                _secretWord_display.value=deriveSecretWordDisplay()
            }
            else
            {
                _incorrectGuesses.value+="$guess"
                _livesleft.value = livesleft.value?.minus(1)
            }
        }
    }
    private fun deriveSecretWordDisplay():String
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