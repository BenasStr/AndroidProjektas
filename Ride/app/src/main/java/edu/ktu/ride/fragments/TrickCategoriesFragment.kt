package edu.ktu.ride.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import edu.ktu.ride.R
import edu.ktu.ride.adapters.TrickAdapter
import edu.ktu.ride.database.TrickDatabase
import edu.ktu.ride.databinding.FragmentTrickCategoriesBinding
import edu.ktu.ride.viewmodels.TrickViewModel
import edu.ktu.ride.viewmodels.TrickViewModelFactory

class TrickCategoriesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTrickCategoriesBinding.inflate(inflater)

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
            val action = TrickCategoriesFragmentDirections
                .actionTrickCategoriesFragmentToTrickTutorialFragment(it.trickId)

            binding.root.findNavController().navigate(action)
        })

        binding.learnNextRecyclerView.adapter = adapter

        viewModel.learn_next.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.navCategoryFlatBtn.setOnClickListener(){
            binding.root.findNavController().navigate(R.id.action_trickCategoriesFragment_to_flatGroundTricksFragment)
        }

        binding.navCategorySgBtn.setOnClickListener{
            binding.root.findNavController().navigate(R.id.action_trickCategoriesFragment_to_slidesAndGrindsFragment)
        }

        binding.navCategoryRampBtn.setOnClickListener{
            binding.root.findNavController().navigate(R.id.action_trickCategoriesFragment_to_rampTricksFragment)
        }

        return binding.root
    }
}