package edu.ktu.ride.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import edu.ktu.ride.adapters.TrickAdapter
import edu.ktu.ride.database.TrickDatabase
import edu.ktu.ride.databinding.FragmentFlatGroundTricksBinding
import edu.ktu.ride.viewmodels.TrickViewModel
import edu.ktu.ride.viewmodels.TrickViewModelFactory

class FlatGroundTricksFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFlatGroundTricksBinding.inflate(inflater)

        val viewModel: TrickViewModel by viewModels {
            TrickViewModelFactory(
                TrickDatabase.getInstance(
                    requireContext()
                )
            )
        }

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = TrickAdapter(TrickAdapter.TrickClickListener {
            val action = FlatGroundTricksFragmentDirections
                .actionFlatGroundTricksFragmentToTrickTutorialFragment(it.trickId)

            binding.root.findNavController().navigate(action)
        })

        viewModel.tricks.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }


        binding.tricksLearnedText.text = binding.tricksLearnedText.text.toString()
            .plus(" ")
            .plus(viewModel.learned.toString())

        binding.tricksRecycler.adapter = adapter

        return binding.root
    }
}