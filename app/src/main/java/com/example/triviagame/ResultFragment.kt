package com.example.triviagame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.triviagame.databinding.FragmentResultBinding

class ResultFragment: Fragment() {
    private lateinit var binding: FragmentResultBinding
    private var points:Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)

        points = requireArguments().getInt(Constatnt.SCORE_KEY)

        showResult()

        binding.buttonResetGame.setOnClickListener {
            restartGame()
        }

        return binding.root
    }

    private fun restartGame() {
        val questionFragment = QuestionFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,questionFragment)
            .commit()
    }

    private fun showResult() {
        if (points > 4) {
            "This Nice, You Win! 😍".also { binding.textResult.text = it }
            binding.textPoints.text = points.toString()
        } else {
            "Ops! Sorry, You Lost! 😥".also { binding.textResult.text = it }
            binding.textPoints.text = points.toString()
        }
    }
}