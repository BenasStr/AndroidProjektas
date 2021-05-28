package edu.ktu.ride.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import edu.ktu.ride.R

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        view.findViewById<Button>(R.id.nav_search_btn).setOnClickListener(){
            view.findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }

        view.findViewById<Button>(R.id.nav_learn_tricks_btn).setOnClickListener{
            view.findNavController().navigate(R.id.action_mainFragment_to_trickCategoriesFragment)
        }

        return view
    }
}