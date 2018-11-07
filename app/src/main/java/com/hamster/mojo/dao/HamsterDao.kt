package com.hamster.mojo.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.hamster.mojo.model.Hamster

@Dao
interface HamsterDao {

    @Query("SELECT * FROM hamster")
    fun getAll(): List<Hamster>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(hamsters: List<Hamster>?)
}