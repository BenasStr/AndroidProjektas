package edu.ktu.ride.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.viewModels
import edu.ktu.ride.database.TrickDatabase
import edu.ktu.ride.databinding.FragmentTrickTutorialBinding
import edu.ktu.ride.viewmodels.TrickViewModel
import edu.ktu.ride.viewmodels.TrickViewModelFactory

class TrickTutorialFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = TrickTutorialFragmentArgs.fromBundle(requireArguments())

        val binding = FragmentTrickTutorialBinding.inflate(inflater)

        val viewModel: TrickViewModel by viewModels {
            TrickViewModelFactory(
                TrickDatabase.getInstance(
                    requireContext()
                )
            )
        }

        var trick = viewModel.getTrickById(args.id)

        binding.tutorialTrickNameText.text = trick.position + ' ' + trick.name
        binding.tutorialDescriptionText.text = trick.description

        var uri = Uri.parse(trick.tutorial_vid)

        binding.tutorialVideo.setVideoURI(uri)

        var mediaController = MediaController(context)
        binding.tutorialVideo.setMediaController(mediaController)
        mediaController.setAnchorView(binding.tutorialVideo)

        binding.progressBtn.setOnClickListener{
            if (trick.learning_status == 0) {
                binding.progressBtn.text = "Start learning"
                viewModel.updateTrickStatus(
                    trick.trickId,
                    trick.learning_status + 1)
            } else if (trick.learning_status == 1) {
                binding.progressBtn.text = "Done"
                viewModel.updateTrickStatus(
                    trick.trickId,
                    trick.learning_status + 1)

                viewModel.updateTrickDoneStatus(
                    trick.trickId,
                    trick.done + 1
                )
            } else {
                binding.progressBtn.text = "Relearn"
                viewModel.updateTrickStatus(
                    trick.trickId, 0)
                viewModel.updateTrickDoneStatus(trick.trickId, 0)
            }
        }

        return binding.root
    }
}