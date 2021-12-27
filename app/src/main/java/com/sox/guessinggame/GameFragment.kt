package com.sox.guessinggame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sox.guessinggame.databinding.FragmentGameBinding


class GameFragment : Fragment() {
    lateinit var viewModel: GameViewModel
    private var _binding: FragmentGameBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel=ViewModelProvider(this).get(GameViewModel::class.java)
        //updateScreen()


            viewModel.makeGuess(binding.etxtGuess.text.toString().uppercase())
            binding.etxtGuess.text=null
            //updateScreen()
            viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer {
                binding.txtIncorrect.text="Letras incorrectas $it"
            })

            viewModel.livesleft.observe(viewLifecycleOwner, Observer {
                binding.lives.text="Tienes $it vidas restantes"
            })

            viewModel.secretWord_display.observe(viewLifecycleOwner, Observer {
                binding.word.text = it
            })
        binding.btnGuess.setOnClickListener {
            viewModel.makeGuess(binding.etxtGuess.text.toString().uppercase())
            binding.etxtGuess.text=null
            if(viewModel.isWon() ||viewModel.isLost())
            {
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }

        }
        return view
    }

    /*@SuppressLint("SetTextI18n")
    fun updateScreen()
    {
        binding.word.text = viewModel.secretWord_display
        binding.lives.text = "Tienes ${viewModel.livesleft} vidas restantes"
        binding.incorrect.text = "Letras incorrectas ${viewModel.incorrectGuesses}"
    }*/



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}