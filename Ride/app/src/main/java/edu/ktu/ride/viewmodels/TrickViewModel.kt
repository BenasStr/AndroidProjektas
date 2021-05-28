package edu.ktu.ride.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import edu.ktu.ride.database.Trick
import edu.ktu.ride.database.TrickDatabase
import kotlinx.coroutines.launch

class TrickViewModel(private val db: TrickDatabase) : ViewModel() {
    private var _tricks = MutableLiveData<List<Trick>>()
    val tricks: LiveData<List<Trick>>
        get() = _tricks

    private var _learned = 0
    val learned: Int
        get() = _learned

    private var _learn_next = MutableLiveData<List<Trick>>()
    val learn_next: LiveData<List<Trick>>
        get() = _learn_next

    private var _searched = MutableLiveData<List<Trick>>()
    val searched: LiveData<List<Trick>>
        get() = _searched

    private var _trick = Trick()
    val trick: Trick
        get() = _trick

    init {
        getAllTricks()
        getDoneTricksCount()
        getNotDoneTricks()
    }

    private fun getAllTricks() {
        viewModelScope.launch {
            _tricks.postValue(db.trickDao().getAllTricks())
        }
    }

    private fun getDoneTricksCount() {
        viewModelScope.launch {
            _learned = db.trickDao().getDoneTricksCount()
        }
    }

    fun getSearchedTricks(search: String) {
        viewModelScope.launch {
            var list = db.trickDao().getAllTricks()
            var temp: MutableList<Trick> = arrayListOf()

            if (search.length != 0) {
                val pattern = search.toLowerCase()
                for (trick in list) {
                    if (trick.name.toLowerCase().contains(pattern)) {
                        temp.add(trick)
                    }
                }
                _searched.postValue(temp)
            }
        }
    }

    fun getTrickById(id: Int) :Trick{
        viewModelScope.launch {
            _trick = db.trickDao().getTrickById(id)
        }
        return _trick
    }

    fun updateTrickStatus(id: Int, status: Int) {
        viewModelScope.launch {
            db.trickDao().updateTrickStatus(status, id)
        }
    }

    fun updateTrickDoneStatus(id: Int, done: Int) {
        viewModelScope.launch {
            db.trickDao().updateTrickDoneStatus(done, id)
        }
    }

    private fun getNotDoneTricks() {
        viewModelScope.launch {
            val not_done = db.trickDao().getNotDoneTricks()
            val done = db.trickDao().getDoneTricksNames()
            var count = 0
            var list: MutableList<Trick> = arrayListOf()

            loop@ for (i in not_done) {
                if(count == 3) break@loop

                if (i.requirements == " ") {
                    list.add(i)
                    count++
                } else {
                    loop@ for (j in done) {
                        if (i.requirements == j) {
                            list.toMutableList().add(i)
                            count++
                            if(count == 3) break@loop
                        }
                    }
                }
            }

            _learn_next.postValue(list)
        }
    }
}