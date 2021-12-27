package com.sox.guessinggame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.sox.guessinggame.databinding.FragmentGameBinding


class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    val words= listOf<String>("Android","Kotlin","Fragment","Activity")

    val secretWord= words.random().uppercase()
    var secretWord_display=""
    var incorrectGuesses=""
    var correctGuesses=""
    var livesleft=3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        secretWord_display=deriveSecretWordDisplay()
        updateScreen()
        binding.btnGuess.setOnClickListener {
            makeGuess(binding.etxtGuess.text.toString().uppercase())
            binding.etxtGuess.text=null
            updateScreen()
            if(isWon() ||isLost())
            {
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(wonLostMessage())
                view.findNavController().navigate(action)
            }

        }
        return view
    }

    @SuppressLint("SetTextI18n")
    fun updateScreen()
    {
        binding.word.text = secretWord_display
        binding.lives.text = "Tienes $livesleft vidas restantes"
        binding.incorrect.text = "Letras incorrectas $incorrectGuesses"
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}