package edu.ktu.ride.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TrickDAO {
    @Query("SELECT * FROM Trick")
    suspend fun getAllTricks(): List<Trick>

    @Query("SELECT * FROM Trick WHERE trickId = :id")
    suspend fun getTrickById(id: Int): Trick

    @Query("SELECT COUNT(*) FROM Trick WHERE done = 1")
    suspend fun getDoneTricksCount(): Int

    @Query("SELECT * FROM Trick WHERE done = 0")
    suspend fun getNotDoneTricks(): List<Trick>

    @Query("SELECT name FROM Trick WHERE done = 1")
    suspend fun getDoneTricksNames(): List<String>

    @Query("UPDATE Trick SET learning_status = :status WHERE trickId = :id")
    suspend fun updateTrickStatus(status: Int, id: Int)

    @Query("UPDATE Trick SET done = :done WHERE trickId = :id")
    suspend fun updateTrickDoneStatus(done: Int, id: Int)
}