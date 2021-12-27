package com.sox.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.sox.guessinggame.databinding.FragmentResultBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ResultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.txtMessage.text=ResultFragmentArgs.fromBundle(requireArguments()).result
        binding.btnNewGame.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }
        return view
    }


}