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
import edu.ktu.ride.databinding.FragmentSearchBinding
import edu.ktu.ride.viewmodels.TrickViewModel
import edu.ktu.ride.viewmodels.TrickViewModelFactory

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater)

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
            val action = SearchFragmentDirections
                .actionSearchFragmentToTrickTutorialFragment(it.trickId)

            binding.root.findNavController().navigate(action)
        })

        binding.searchedRecyclerView.adapter = adapter

        viewModel.searched.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.searchBtn.setOnClickListener {
            viewModel.getSearchedTricks(
                binding.searchTrickText.text.toString()
            )
        }

        return binding.root
    }
}