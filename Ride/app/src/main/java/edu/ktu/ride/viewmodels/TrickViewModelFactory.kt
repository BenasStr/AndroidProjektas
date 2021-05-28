package edu.ktu.ride.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.ktu.ride.database.TrickDatabase
import java.lang.IllegalArgumentException

class TrickViewModelFactory(private val db: TrickDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TrickViewModel::class.java)) {
            return TrickViewModel(db) as T
        }
        throw IllegalArgumentException()
    }
}